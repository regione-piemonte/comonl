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
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTStatusStranieroDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTStatusStraniero;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTStatusStraniero
 */
@ApplicationScoped
public class ComTStatusStranieroDaoImpl extends BaseEntityDaoImpl<Long, ComTStatusStraniero>
		implements ComTStatusStranieroDao {

	@Override
	public List<ComTStatusStraniero> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTStatusStraniero t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTStatusStraniero ");

		TypedQuery<ComTStatusStraniero> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<ComTStatusStraniero> findValidDate() {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("now", now);
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTStatusStraniero t ");
		jpql.append(" WHERE 1=1 ");
		jpql.append(" AND (t.dtInizio IS NULL OR (t.dtInizio IS NOT NULL AND t.dtInizio <= :now)) ");
		jpql.append(" AND (t.dtFine IS NULL OR (t.dtFine IS NOT NULL AND t.dtFine >= :now)) ");
		jpql.append(" ORDER BY t.idComTStatusStraniero ");

		TypedQuery<ComTStatusStraniero> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public Optional<ComTStatusStraniero> findByCodMin(String codStatusMin) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTStatusStraniero t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.codStatusMin", "codMin", codStatusMin);

		TypedQuery<ComTStatusStraniero> query = composeTypedQuery(jpql, params);

		return query.getResultList().stream().findFirst();
	}
	
	@Override
	public Optional<ComTStatusStraniero> findValid(ComTStatusStraniero comTStatusStraniero, Date date){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTStatusStraniero t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id", "id", comTStatusStraniero.getId());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codStatusMin", "codStatusMin", comTStatusStraniero.getCodStatusMin());
		JpaQueryHelper.andCheckDateValidita(jpql, params, "t", date);
		params.put("now", date);

		TypedQuery<ComTStatusStraniero> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}

}
