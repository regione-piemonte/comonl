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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDLegaleRapprDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseAuditedEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComDLegaleRappr;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComDLegaleRappr
 */
@ApplicationScoped
public class ComDLegaleRapprDaoImpl extends BaseAuditedEntityDaoImpl<Long, ComDLegaleRappr>
		implements ComDLegaleRapprDao {

	@Override
	public List<ComDLegaleRappr> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDLegaleRappr t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComDDatore ");

		TypedQuery<ComDLegaleRappr> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

}
