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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCittadinanzaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTCittadinanza;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTCittadinanza
 */
@ApplicationScoped
public class ComTCittadinanzaDaoImpl extends BaseEntityDaoImpl<Long, ComTCittadinanza> implements ComTCittadinanzaDao {

	@Override
	public List<ComTCittadinanza> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTCittadinanza t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTCittadinanza ");

		TypedQuery<ComTCittadinanza> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<ComTCittadinanza> findValidDate() {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("now", now);
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTCittadinanza t ");
		jpql.append(" WHERE 1=1 ");
		jpql.append(" AND (t.dtInizio IS NULL OR (t.dtInizio IS NOT NULL AND t.dtInizio <= :now)) ");
		jpql.append(" AND (t.dtFine IS NULL OR (t.dtFine IS NOT NULL AND t.dtFine >= :now)) ");
		jpql.append(" ORDER BY t.dsComTCittadinanza ");

		TypedQuery<ComTCittadinanza> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public Optional<ComTCittadinanza> findByCodMin(String codCittadinanzaMin) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTCittadinanza t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.codCittadinanzaMin", "codMin", codCittadinanzaMin);

		TypedQuery<ComTCittadinanza> query = composeTypedQuery(jpql, params);

		return query.getResultList().stream().findFirst();
	}
	@Override
	public Optional<ComTCittadinanza> findByCodMf(String codMf) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTCittadinanza t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codMf", "codMf", codMf);
		
		Date date = new Date();
		JpaQueryHelper.andCheckDateValidita(jpql, params, "t", date);
		params.put("now", date);
		
		
		TypedQuery<ComTCittadinanza> query = composeTypedQuery(jpql, params);
		
		return query.getResultList().stream().findFirst();
	}

}
