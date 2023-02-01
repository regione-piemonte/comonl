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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRDepCommaxComunicDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComRDepCommaxComunic;
import it.csi.comonl.comonlweb.ejb.entity.ComRDepCommaxComunicPK;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComRDepCommaxComunic
 */
@ApplicationScoped
public class ComRDepCommaxComunicDaoImpl extends BaseEntityDaoImpl<ComRDepCommaxComunicPK, ComRDepCommaxComunic> implements ComRDepCommaxComunicDao {

	@Override
	public List<ComRDepCommaxComunic> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRDepCommaxComunic t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<ComRDepCommaxComunic> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	@Override
	public List<ComRDepCommaxComunic> getDepCommaxComunicaByIdUplDocumenti(Long idComDUplDocumenti) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRDepCommaxComunic t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDUplDocumenti.idComDUplDocumenti", "idComDUplDocumenti", idComDUplDocumenti);
		
		jpql.append(" ORDER BY t.id ");
		
		TypedQuery<ComRDepCommaxComunic> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

}
