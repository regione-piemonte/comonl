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
package it.csi.comonl.comonlweb.ejb.business.be.dao.impl.comonl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoSoggettoAbilitatoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTTipoSoggettoAbilitato
 */
@ApplicationScoped
public class ComTTipoSoggettoAbilitatoDaoImpl extends BaseEntityDaoImpl<Long, ComTTipoSoggettoAbilitato> implements ComTTipoSoggettoAbilitatoDao {

	@Override
	public List<ComTTipoSoggettoAbilitato> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoSoggettoAbilitato t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTipoSoggettoAbilitato ");

		TypedQuery<ComTTipoSoggettoAbilitato> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<ComTTipoSoggettoAbilitato> findValidDate() {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("now", now);
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoSoggettoAbilitato t ");
		jpql.append(" WHERE 1=1 ");
		jpql.append(" AND (t.dtInizio IS NULL OR (t.dtInizio IS NOT NULL AND t.dtInizio <= :now)) ");
		jpql.append(" AND (t.dtFine IS NULL OR (t.dtFine IS NOT NULL AND t.dtFine >= :now)) ");

		TypedQuery<ComTTipoSoggettoAbilitato> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@Override
	public List<ComTTipoSoggettoAbilitato> findTipoStudioProfessionales() {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("now", now);
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT DISTINCT t ");
		jpql.append(" FROM ComTTipoSoggettoAbilitato t JOIN ComDSoggettoAbilitato sa ON sa.comTTipoSoggettoAbilitato.idComTipoSoggettoAbilitato = t.idComTipoSoggettoAbilitato ");
		jpql.append(" WHERE 1=1 ");
		jpql.append(" AND (t.dtInizio IS NULL OR (t.dtInizio IS NOT NULL AND t.dtInizio <= :now)) ");
		jpql.append(" AND (t.dtFine IS NULL OR (t.dtFine IS NOT NULL AND t.dtFine >= :now)) ");

		TypedQuery<ComTTipoSoggettoAbilitato> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

}