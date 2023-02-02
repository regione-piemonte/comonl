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

import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseAuditedEntity;

/**
 * Base Data Access Object (DAO) interface
 * @param <K> the id type
 * @param <T> the entity type
 */
public interface BaseAuditedEntityDao<K, T extends BaseAuditedEntity<K>> extends BaseEntityDao<K, T> {

//	/**
//	 * Logically deletes the entity
//	 * @param key the key of the entity
//	 */
//	void deleteLogically(K key);
	
	
	/**
	 * Finds an entity by its key
	 * @param key the key
	 * @return the entity
	 */
	Optional<T> findById(K key);
}
