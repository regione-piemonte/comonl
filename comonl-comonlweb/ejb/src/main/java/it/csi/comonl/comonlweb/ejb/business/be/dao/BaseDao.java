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
package it.csi.comonl.comonlweb.ejb.business.be.dao;

/**
 * Base Data Access Object (DAO) interface
 * @param <T> the type
 */
public interface BaseDao<T> {

	/**
	 * Flushes the entity manager
	 */
	void flush();
	/**
	 * Clears the entity manager
	 */
	void clear();
	/**
	 * Flushes and clears the entity manager
	 */
	void flushAndClear();
}
