/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.util.cache.impl;

import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import it.csi.comonl.comonlweb.ejb.util.cache.CacheKey;

/**
 * Helper for the cache
 */
@Startup
@Singleton
public class CacheHelper {
	
	/** The cache implementation */
	private final Cache<CacheKey, Object> cache = new CacheImpl<>();
	
	/**
	 * After construction
	 */
	@PostConstruct
	private void postConstruct() {
		((CacheImpl<CacheKey, Object>)cache).postConstruct();
	}
	/**
	 * Before destruction
	 */
	@PreDestroy
	private void preDestroy() {
		((CacheImpl<CacheKey, Object>)cache).preDestroy();
	}

	/**
	 * Gets the cached value
	 * @param <T> the return type
	 * @param key the cache key
	 * @return the cached value
	 */
	@Lock(LockType.READ)
	@SuppressWarnings("unchecked")
	public <T> T getCachedValue(CacheKey key) {
		return (T) cache.get(key);
	}

	/**
	 * Gets the cached value, with initializer if the value is not present
	 * @param <T> the return type
	 * @param key the cache key
	 * @param initializer the initializer
	 * @return the cached value
	 */
	@Lock(LockType.READ)
	@SuppressWarnings("unchecked")
	public <T> T getCachedValue(CacheKey key, Supplier<T> initializer) {
		T result = (T) cache.get(key);
		if(result == null) {
			synchronized(key) {
				result = (T) cache.get(key);
				if(result == null) {
					result = initializer.get();
					cache.put(key, result);
				}
			}
		}
		return result;
	}
	
	/**
	 * Removes an element from cache
	 * @param key the cache key
	 * @return whether the element was removed
	 */
	@Lock(LockType.WRITE)
	public boolean remove(CacheKey key) {
		return cache.remove(key);
	}
	
	/**
	 * Clears the cache
	 */
	@Lock(LockType.WRITE)
	public void clear() {
		cache.clear();
	}
}
