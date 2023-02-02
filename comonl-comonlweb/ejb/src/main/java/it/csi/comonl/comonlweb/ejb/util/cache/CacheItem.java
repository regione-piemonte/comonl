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

import java.io.Serializable;
import java.util.Date;

/**
 * Base class for a cached item.
 * @param <T> the item type
 */
public class CacheItem<T> implements Serializable {

	private static final long serialVersionUID = 752382703503805613L;

	private final Date cacheDate;
	private final transient T cachedItem;
	private int hitCount;

	/**
	 * Default constructor. Sets the hitCount to 1 and the date to now.
	 * @param cachedItem the cached item
	 */
	public CacheItem(T cachedItem) {
		this(cachedItem, new Date(), 1);
	}

	/**
	 * Default constructor. Sets the hitCount to 1.
	 * @param cachedItem the cached item
	 * @param cacheDate the cache date
	 */
	public CacheItem(T cachedItem, Date cacheDate) {
		this(cachedItem, cacheDate, 1);
	}

	/**
	 * Constructs a cacheItem with specified cacheDate and hitCount.
	 *
	 * @param cachedItem the cached item
	 * @param cacheDate the cache date
	 * @param hitCount  the hit count
	 */
	public CacheItem(T cachedItem, Date cacheDate, int hitCount) {
		super();
		this.cachedItem = cachedItem;
		this.cacheDate = cacheDate != null ? new Date(cacheDate.getTime()) : null;
		this.hitCount = hitCount;
	}

	/**
	 * @return the cacheDate
	 */
	public Date getCacheDate() {
		return cacheDate != null ? new Date(cacheDate.getTime()) : null;
	}
	/**
	 * @return the hitCount
	 */
	public int getHitCount() {
		return hitCount;
	}

	/**
	 * @return the cachedItem
	 */
	public T getCachedItem() {
		return cachedItem;
	}

	/**
	 * Hits the cached element, incrementing the hitCount.
	 * <br>
	 * This method is not thread-safe, therefore a lock or a synchronized block should be use if thread-safety is to be ensured.
	 *
	 * @return the incremented hitCount
	 */
	public int hit() {
		return ++hitCount;
	}

}
