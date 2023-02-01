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
package it.csi.comonl.comonlweb.ejb.business.be.dao.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseAuditedEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.base.BaseAuditedEntity;
import it.csi.comonl.comonlweb.ejb.entity.base.OptlockEntity;
import it.csi.comonl.comonlweb.ejb.exception.BusinessException;
import it.csi.comonl.comonlweb.ejb.util.ComonlThreadLocalContainer;
import it.csi.comonl.comonlweb.lib.dto.Utente;

/**
 * Base Data Access Object (DAO) implementor
 * 
 * @param <K> the key type
 * @param <T> the entity type
 */
public abstract class BaseAuditedEntityDaoImpl<K, T extends BaseAuditedEntity<K>> extends BaseEntityDaoImpl<K, T>
		implements BaseAuditedEntityDao<K, T> {

	/**
	 * Restituisce l'oggetto se valido
	 */
	@Override
	public Optional<T> findOne(K key) {
//		return super.findOne(key).filter(el -> el.getDataCancellazione() == null);
		return super.findOne(key);
	}

	/**
	 * Restituisce l'oggetto per id
	 */
	public Optional<T> findById(K key) {
		return super.findOne(key);
	}

//	@Override
//	public void deleteLogically(K key) {
//		Date now = new Date();
//		Utente utente = getUtente();
//
//		StringBuilder sb = new StringBuilder();
//		sb.append("FROM ");
//		sb.append(clazz.getName());
//		sb.append(" WHERE ").append(idField.getName()).append(" = :id");
//
//		Map<String, Object> params = new HashMap<>();
//		params.put("id", key);
//
//		TypedQuery<T> typedQuery = composeTypedQuery(sb, params);
//		typedQuery.getResultList().stream().forEach(entity -> {
////			entity.setComonlTUtenteUltimaModifica(comonlTUtente);
////			entity.setDataCancellazione(now);
////			entity.setDataUltimaModifica(now);
//		});
//	}

	@Override
	public T insert(T entity) {
		Date now = new Date();
		Utente utente = getUtente();

		if (entity instanceof OptlockEntity) {
			OptlockEntity optlockEntity = (OptlockEntity) entity;
			optlockEntity.setOptlock(UUID.randomUUID());
		}
		entity.setDtInsert(now);
		entity.setDtUltMod(now);
		entity.setIdUserInsert(utente.getCodiceFiscale());
		entity.setIdUserUltMod(utente.getCodiceFiscale());
		return super.insert(entity);
	}

	@Override
	public T update(T entity) {
		Date now = new Date();
		Utente utente = getUtente();
		// entity non trovato
		T current = findOne(entity.getId()).orElseThrow(() -> new RuntimeException("Richiesta vecchia riprovare"));

		if (entity instanceof OptlockEntity) {
			OptlockEntity optlockEntity = (OptlockEntity) entity;
			OptlockEntity optlockCurrent = (OptlockEntity) current;

			if (!optlockCurrent.getOptlock().equals(optlockEntity.getOptlock())) {
				throw new BusinessException("Dati vecchi");
			}
			if (optlockEntity.getOptlock() == null) {
				throw new BusinessException("optLock non passato in richiesta dal chiamante");
			}
			optlockEntity.setOptlock(UUID.randomUUID());
		}
		
		entity.setDtUltMod(now);
		entity.setIdUserUltMod(utente.getCodiceFiscale());
		
		return super.update(entity);
	}

	private Utente getUtente() {
		Utente utente = null;
		if (ComonlThreadLocalContainer.UTENTE_CONNESSO.get() != null) {
			utente = ComonlThreadLocalContainer.UTENTE_CONNESSO.get();
		}
		return utente;
	}

}
