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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CacheSupplier<K,V> implements Supplier<Map<K,V>>{

	@Override
	public Map<K, V> get() {
		return new HashMap<K, V>();
	}
	

}
