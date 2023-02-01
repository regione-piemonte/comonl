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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRSedeLavoroLavoratoreDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComRSedeLavoroLavoratore;
import it.csi.comonl.comonlweb.ejb.entity.ComRSedeLavoroLavoratorePK;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComRRapportoLavoratore
 */
@ApplicationScoped
public class ComRSedeLavoroLavoratoreDaoImpl extends BaseEntityDaoImpl<ComRSedeLavoroLavoratorePK, ComRSedeLavoroLavoratore> implements ComRSedeLavoroLavoratoreDao {

	@Override
	public List<ComRSedeLavoroLavoratore> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRSedeLavoroLavoratore t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<ComRSedeLavoroLavoratore> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	
	@Override
	public ComRSedeLavoroLavoratore getSedeByIdLavoratore(Long idLavoratore) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRSedeLavoroLavoratore t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDLavoratore.idComDLavoratore", "idComDLavoratore", idLavoratore);
		
		jpql.append(" ORDER BY t.id ");
		
		TypedQuery<ComRSedeLavoroLavoratore> query = composeTypedQuery(jpql, params);
		return query.getSingleResult();
	}
	
	@Override
	public void deleteByIdLavoratore(Long idLavoratore) {
		StringBuilder sb = new StringBuilder()
				.append("DELETE FROM ComRSedeLavoroLavoratore")
				.append(" WHERE ")
				.append(" comDLavoratore.id = :idLavoratore");
		Map<String, Object> params = new HashMap<>();
		params.put("idLavoratore", idLavoratore);
		Query query = composeQuery(sb, params);
		query.executeUpdate();
	}
}
