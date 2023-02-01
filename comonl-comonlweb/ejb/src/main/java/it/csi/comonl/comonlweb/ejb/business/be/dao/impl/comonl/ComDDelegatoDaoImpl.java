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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDDelegatoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComDDelegato;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComDDelegato
 */
@ApplicationScoped
public class ComDDelegatoDaoImpl extends BaseEntityDaoImpl<Long, ComDDelegato> implements ComDDelegatoDao {

	@Override
	public List<ComDDelegato> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDDelegato t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComDDelegato ");

		TypedQuery<ComDDelegato> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@Override
	public Optional<ComDDelegato> findLastByCfDelegato(String cfDelegato) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDDelegato t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.cfDelegato", "cfDelegato", cfDelegato);
		
		jpql.append(" ORDER BY t.idComDDelegato DESC ");
		
		TypedQuery<ComDDelegato> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}
}
