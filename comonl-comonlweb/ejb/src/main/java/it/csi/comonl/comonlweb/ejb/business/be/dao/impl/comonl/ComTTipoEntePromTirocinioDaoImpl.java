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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoEntePromTirocinioDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoEntePromTirocinio;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTTipoEntePromTirocinio
 */
@ApplicationScoped
public class ComTTipoEntePromTirocinioDaoImpl extends BaseEntityDaoImpl<Long, ComTTipoEntePromTirocinio> implements ComTTipoEntePromTirocinioDao {

	@Override
	public List<ComTTipoEntePromTirocinio> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoEntePromTirocinio t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTTipoEntePromTir ");

		TypedQuery<ComTTipoEntePromTirocinio> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<ComTTipoEntePromTirocinio> findValidDate() {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("now", now);
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoEntePromTirocinio t ");
		jpql.append(" WHERE 1=1 ");
		jpql.append(" AND (t.dtInizio IS NULL OR (t.dtInizio IS NOT NULL AND t.dtInizio <= :now)) ");
		jpql.append(" AND (t.dtFine IS NULL OR (t.dtFine IS NOT NULL AND t.dtFine >= :now)) ");
		jpql.append(" ORDER BY t.idComTTipoEntePromTir ");

		TypedQuery<ComTTipoEntePromTirocinio> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
}
