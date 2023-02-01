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

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDAnagraficaSedeLavoroDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComDAnagraficaSedeLavoro;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComDAnagraficaSedeLavoro
 */
@ApplicationScoped
public class ComDAnagraficaSedeLavoroDaoImpl extends BaseEntityDaoImpl<Long, ComDAnagraficaSedeLavoro> implements ComDAnagraficaSedeLavoroDao {

	@Override
	public List<ComDAnagraficaSedeLavoro> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDAnagraficaSedeLavoro t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComDAnagraficSedeLavoro ");

		TypedQuery<ComDAnagraficaSedeLavoro> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

}
