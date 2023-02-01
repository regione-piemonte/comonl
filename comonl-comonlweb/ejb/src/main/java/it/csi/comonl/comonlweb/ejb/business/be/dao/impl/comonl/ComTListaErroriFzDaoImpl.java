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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTListaErroriFzDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTComonlsParametri;
import it.csi.comonl.comonlweb.ejb.entity.ComTListaErroriFz;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTCommaxParametri
 */
@ApplicationScoped
public class ComTListaErroriFzDaoImpl extends BaseEntityDaoImpl<Long, ComTListaErroriFz> implements ComTListaErroriFzDao {

	@Override
	public List<ComTListaErroriFz> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTListaErroriFz t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idParametro ");

		TypedQuery<ComTListaErroriFz> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@Override
	public String getErrroById(long id) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTListaErroriFz t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.idErrore", "idErrore", id);


		TypedQuery<ComTListaErroriFz> query = composeTypedQuery(jpql, params);
		return query.getSingleResult().getDsErrore();
	}
}
