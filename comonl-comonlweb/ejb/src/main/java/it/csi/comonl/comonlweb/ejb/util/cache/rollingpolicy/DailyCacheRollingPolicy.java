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
package it.csi.comonl.comonlweb.ejb.util.cache.rollingpolicy;

import java.util.Calendar;
import java.util.Date;

/**
 * A CacheRollingPolicy which marks data as stale daily.
 * 
 * @author Domenico
 * @version 1.0.0 - 01/10/2014
 */
public class DailyCacheRollingPolicy extends CalendarCacheRollingPolicy {

	@Override
	public boolean isExpired(Date data, int hitCount) {
		return isExpiredDate(data, Calendar.YEAR, Calendar.DAY_OF_YEAR);

	}

}
