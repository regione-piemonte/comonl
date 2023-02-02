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
package it.csi.comonl.comonlweb.ejb.util.cache;

import javax.cache.processor.MutableEntry;

/**
 * Mutable cache entry
 * @param <K> the key type
 * @param <V> the value type
 */
public class CacheMutableEntry<K, V> implements MutableEntry<K, V> {

	private final K key;
	private V value;

	/**
	 * Constructor with null value
	 * @param key the key
	 */
	public CacheMutableEntry(K key) {
		this(key, null);
	}
	/**
	 * Constructor
	 * @param key the key
	 * @param value the value
	 */
	public CacheMutableEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public <T> T unwrap(Class<T> clazz) {
		throw new UnsupportedOperationException("Unsupported operation");
	}

	@Override
	public boolean exists() {
		return value != null;
	}

	@Override
	public void remove() {
		// Nothing to do
	}

	@Override
	public void setValue(V value) {
		this.value = value;
	}

}
