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
package it.csi.comonl.comonlweb.ejb.util.cache.rollingpolicy;

import java.util.Date;

/**
 * Rolling policy for cached data.
 */
public interface CacheRollingPolicy {
	
	/**
	 * Checks whether the cached item is stale.
	 *
	 * @param cacheDate the date in which the item was cached
	 * @param hitCount  the number of times the cached item was used
	 * 
	 * @return true if the policy considers the cached value as stale
	 */
	boolean isExpired(Date cacheDate, int hitCount);

}
