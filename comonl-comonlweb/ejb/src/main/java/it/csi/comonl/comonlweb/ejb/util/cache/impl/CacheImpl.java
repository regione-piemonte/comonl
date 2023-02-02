/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.util.cache.impl;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;
import javax.cache.processor.MutableEntry;

import org.apache.commons.lang3.tuple.ImmutablePair;

import it.csi.comonl.comonlweb.ejb.util.cache.CacheItem;
import it.csi.comonl.comonlweb.ejb.util.cache.CacheMutableEntry;
import it.csi.comonl.comonlweb.ejb.util.cache.ReclaimedCacheLoggingListener;
import it.csi.comonl.comonlweb.ejb.util.cache.rollingpolicy.CacheRollingPolicy;
import it.csi.comonl.comonlweb.ejb.util.cache.rollingpolicy.DailyCacheRollingPolicy;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

/**
 * Base cache implementor
 * @param <K> the caching key type
 * @param <V> the caching value type
 */
public class CacheImpl<K, V> implements Cache<K, V> {
	
	private final LogUtil log = new LogUtil(this.getClass());
	/** The application-level cache */
	private final Map<K, Reference<CacheItem<V>>> cache = new ConcurrentHashMap<>();
	/** The locks map */
	private final Map<K, Lock> locks = new WeakHashMap<>();
	
	/** The executor used to keep track of <abbr title="Garbage Collector">GC</abbr>-reclaimed cached values */
	private final ExecutorService reclaimedCacheLoggingListenerExecutor = Executors.newFixedThreadPool(1);
	/** Counter for <abbr title="Garbage Collector">GC</abbr>-reclaimed cached values */
	private final AtomicLong gcCounter = new AtomicLong();
	/** The reference queue for <abbr title="Garbage Collector">GC</abbr>-reclaimed cached values */
	protected final ReferenceQueue<V> referenceQueue = new ReferenceQueue<>();
	
	private CacheRollingPolicy rollingPolicy;
	
	/**
	 * Initialization for the cache.
	 */
	public void postConstruct() {
		final String methodName = "init";
		log.info(methodName, "CachedServiceExecutor created...");
		
		reclaimedCacheLoggingListenerExecutor.execute(new ReclaimedCacheLoggingListener<>(gcCounter, referenceQueue));
		rollingPolicy = new DailyCacheRollingPolicy();
	}
	
	/**
	 * Destruction of the cache.
	 */
	public void preDestroy() {
		final String methodName = "preDestroy";
		log.info(methodName, "CachedServiceExecutor destroyed. Shutting down the ReclaimedCacheLoggingListenerExecutor...");
		reclaimedCacheLoggingListenerExecutor.shutdownNow();
		log.info(methodName, "CachedServiceExecutor shut down");
	}

	@Override
	public V get(K key) {
		CacheItem<V> cachedItem = getCachedItem(key);
		return cachedItem != null ? cachedItem.getCachedItem() : null;
	}

	@Override
	public Map<K, V> getAll(Set<? extends K> keys) {
		return keys == null
			? new HashMap<>()
			: keys.stream()
				.map(key -> new ImmutablePair<>(key, getCachedItem(key)))
				.filter(pair -> pair.right != null)
				.collect(Collectors.toMap(p -> p.left, p -> p.right.getCachedItem()));
	}

	@Override
	public boolean containsKey(K key) {
		return getCachedItem(key) != null;
	}

	@Override
	public void loadAll(Set<? extends K> keys, boolean replaceExistingValues, CompletionListener completionListener) {
		throw new UnsupportedOperationException("Unsupported");
	}

	@Override
	public void put(K key, V value) {
		inLock(key, () -> cache.put(key, toReference(value)));
	}

	@Override
	public V getAndPut(K key, V value) {
		return inLock(key, () -> {
			Reference<CacheItem<V>> oldReference = cache.put(key, toReference(value));
			if(oldReference == null) {
				return null;
			}
			return getCachedItem(oldReference);
		});
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		if(map == null) {
			return;
		}
		map.entrySet()
			.stream()
			.forEach(entry -> put(entry.getKey(), entry.getValue()));
	}

	@Override
	public boolean putIfAbsent(K key, V value) {
		return inLock(key, () -> {
			if(containsKey(key)) {
				return Boolean.FALSE;
			}
			cache.put(key, toReference(value));
			return Boolean.TRUE;
		}).booleanValue();
	}

	@Override
	public boolean remove(K key) {
		Reference<CacheItem<V>> oldValue = cache.remove(key);
		return oldValue != null && oldValue.get() != null;
	}

	@Override
	public boolean remove(K key, V oldValue) {
		return inLock(key, () -> {
			V cachedValue = get(key);
			if(cachedValue == null || !cachedValue.equals(oldValue)) {
				return Boolean.FALSE;
			}
			cache.remove(key);
			return Boolean.TRUE;
		}).booleanValue();
	}

	@Override
	public V getAndRemove(K key) {
		Reference<CacheItem<V>> oldReference = cache.remove(key);
		return getCachedItem(oldReference);
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return inLock(key, () -> {
			V cachedValue = get(key);
			if(cachedValue == null || !cachedValue.equals(oldValue)) {
				return Boolean.FALSE;
			}
			cache.put(key, toReference(newValue));
			return Boolean.TRUE;
		}).booleanValue();
	}

	@Override
	public boolean replace(K key, V value) {
		return inLock(key, () -> {
			V cachedValue = get(key);
			if(cachedValue == null) {
				return Boolean.FALSE;
			}
			cache.put(key, toReference(value));
			return Boolean.TRUE;
		}).booleanValue();
	}

	@Override
	public V getAndReplace(K key, V value) {
		return inLock(key, () -> {
			V cachedValue = get(key);
			if(cachedValue == null) {
				return null;
			}
			Reference<CacheItem<V>> oldValue = cache.put(key, toReference(value));
			return getCachedItem(oldValue);
		});
	}

	@Override
	public void removeAll(Set<? extends K> keys) {
		if(keys == null) {
			return;
		}
		keys.stream()
			.forEach(key -> remove(key));
	}

	@Override
	public void removeAll() {
		cache.keySet()
			.stream()
			.forEach(key -> remove(key));
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public <C extends Configuration<K, V>> C getConfiguration(Class<C> clazz) {
		throw new UnsupportedOperationException("Unsupported operation");
	}

	@Override
	public <T> T invoke(K key, EntryProcessor<K, V, T> entryProcessor, Object... arguments) throws EntryProcessorException {
		MutableEntry<K, V> entry = new CacheMutableEntry<>(key, get(key));
		return entryProcessor.process(entry, arguments);
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public CacheManager getCacheManager() {
		return null;
	}

	@Override
	public void close() {
		// Nothing
	}

	@Override
	public boolean isClosed() {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> clazz) {
		return null;
	}

	@Override
	public void registerCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
		throw new UnsupportedOperationException("Unsupported operation");
	}

	@Override
	public void deregisterCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
		throw new UnsupportedOperationException("Unsupported operation");
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		throw new UnsupportedOperationException("Unsupported operation");
	}

	/**
	 * Retrieves the cachedItem
	 * @param key the key of the item
	 * @return the cached item, if exists and is not stale
	 */
	private CacheItem<V> getCachedItem(K key) {
		Reference<CacheItem<V>> reference = cache.get(key);
		if(reference == null) {
			return null;
		}
		CacheItem<V> item = reference.get();
		if(item == null || rollingPolicy.isExpired(item.getCacheDate(), item.getHitCount())) {
			return null;
		}
		return item;
	}
	
	/**
	 * Retrieves the cachedItem
	 * @param reference the reference to the cached item
	 * @return the cached item, if exists and is not stale
	 */
	private V getCachedItem(Reference<CacheItem<V>> reference) {
		if(reference == null) {
			return null;
		}
		CacheItem<V> item = reference.get();
		if(item == null || rollingPolicy.isExpired(item.getCacheDate(), item.getHitCount())) {
			return null;
		}
		return item.getCachedItem();
	}
	
	/**
	 * Retrieves a lock by its key
	 * @param key the key
	 * @return the lock
	 */
	private Lock retrieveLock(K key) {
		// Obtains the lock from the lock map
		Lock lock = locks.get(key);
		if (lock == null) {
			synchronized (locks) {
				lock = locks.get(key);
				if (lock == null) {
					lock = new ReentrantLock();
					/*
					 * We create a new String by copying the previous one used as key.
					 * 
					 * By doing so, we avoid using a strong reference to an otherwise un-garbage-collactable object
					 * (the cacheKey String, which is persisted in-memory in the String pool), thus making
					 * the lock we just created eligible to be garbage-collected via the mechanics of the WeakHashMap.
					 * 
					 * This will obviously not work if the string was interned (or will be interned prior to the GC-cycle which
					 * will clean the map). But (we hope that) this case should be rare enough not to cause serious
					 * heap pollution.
					 */
					locks.put(key, lock);
				}
			}
		}
		return lock;
	}
	
	/**
	 * Executes an operation in lock
	 * @param <T> the return type
	 * @param key the key
	 * @param supplier the supplier for the operation
	 * @return the result
	 */
	private <T> T inLock(K key, Supplier<T> supplier) {
		Lock lock = retrieveLock(key);
		lock.lock();
		try {
			return supplier.get();
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * Converts the value to a reference
	 * @param value the value
	 * @return the reference
	 */
	@SuppressWarnings("unchecked")
	protected Reference<CacheItem<V>> toReference(V value) {
		return new SoftReference<>(new CacheItem<>(value), (ReferenceQueue<? super CacheItem<V>>) referenceQueue);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> Map<K, EntryProcessorResult<T>> invokeAll(Set<? extends K> keys, EntryProcessor<K, V, T> entryProcessor, Object... arguments) {
		if(keys == null) {
			return null;
		}
		return keys.stream()
			.map(key -> new CacheMutableEntry<>(key, get(key)))
			.collect(Collectors.toMap(cme -> cme.getKey(), cme -> new SuccessEntryProcessorResult<>(entryProcessor.process((MutableEntry<K, V>)cme, arguments))));
	}
}
