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
package it.csi.comonl.comonlweb.ejb.business.be.dao.impl.comonl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTEntePrevidenzialeDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTCcnl;
import it.csi.comonl.comonlweb.ejb.entity.ComTEntePrevidenziale;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTEntePrevidenziale
 */
@ApplicationScoped
public class ComTEntePrevidenzialeDaoImpl extends BaseEntityDaoImpl<Long, ComTEntePrevidenziale> implements ComTEntePrevidenzialeDao {

	@Override
	public List<ComTEntePrevidenziale> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTEntePrevidenziale t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTEntePrevidenziale ");

		TypedQuery<ComTEntePrevidenziale> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<ComTEntePrevidenziale> findValidDate() {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("now", now);
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTEntePrevidenziale t ");
		jpql.append(" WHERE 1=1 ");
		jpql.append(" AND (t.dtInizio IS NULL OR (t.dtInizio IS NOT NULL AND t.dtInizio <= :now)) ");
		jpql.append(" AND (t.dtFine IS NULL OR (t.dtFine IS NOT NULL AND t.dtFine >= :now)) ");
		jpql.append(" ORDER BY t.idComTEntePrevidenziale ");

		TypedQuery<ComTEntePrevidenziale> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	public 	Optional<ComTEntePrevidenziale> findValid(ComTEntePrevidenziale comTEntePrevidenziale, Date date)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTEntePrevidenziale t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id", "id", comTEntePrevidenziale.getId());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codEntePrevidenzialeMin", "codEntePrevidenzialeMin", comTEntePrevidenziale.getCodEntePrevidenzialeMin());
		JpaQueryHelper.andCheckDateValidita(jpql, params, "t", date);
		
		params.put("now", date);
		
		jpql.append(" ORDER BY t.idComTEntePrevidenziale ");

		TypedQuery<ComTEntePrevidenziale> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}

}
