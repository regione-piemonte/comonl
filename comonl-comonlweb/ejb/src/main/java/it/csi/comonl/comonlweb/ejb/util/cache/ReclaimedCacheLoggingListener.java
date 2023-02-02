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

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.atomic.AtomicLong;

import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

/**
 * {@link Runnable} implementor logging eventually <abbr title="Garbage Collector">GC</abbr>-reclaimed references from the referenceQueue.
 * @param <V> the underlying value type
 */
public class ReclaimedCacheLoggingListener<V> implements Runnable {

	private static final LogUtil LOG = new LogUtil(ReclaimedCacheLoggingListener.class);

	private final AtomicLong garbageCollectorCounter;
	private final ReferenceQueue<V> cacheReferenceQueue;

	/**
	 * Default constructor
	 * @param garbageCollectorCounter the GC counter
	 * @param cacheReferenceQueue the cache referenceQueue
	 */
	public ReclaimedCacheLoggingListener(AtomicLong garbageCollectorCounter, ReferenceQueue<V> cacheReferenceQueue) {
		this.garbageCollectorCounter = garbageCollectorCounter;
		this.cacheReferenceQueue = cacheReferenceQueue;
	}

	@Override
	public void run() {
		final String methodName = "run";
		LOG.info(methodName, "Listening for items removed from the cache");
		try {
			while(true) {
				Reference<? extends V> removed = cacheReferenceQueue.remove();
				long count = garbageCollectorCounter.incrementAndGet();
				LOG.debug(methodName, "Removed item " + removed + " from cache (" + count + " references reaped since thread started)");
			}
		} catch(InterruptedException ie) {
			LOG.info(methodName, "Shutting down the thread...");
		}
		LOG.info(methodName, "Thread shut down");
	}
}
