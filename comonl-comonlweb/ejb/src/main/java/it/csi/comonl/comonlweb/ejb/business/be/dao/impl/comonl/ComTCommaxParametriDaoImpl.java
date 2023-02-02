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

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCommaxParametriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTCommaxParametri;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTCommaxParametri
 */
@ApplicationScoped
public class ComTCommaxParametriDaoImpl extends BaseEntityDaoImpl<Long, ComTCommaxParametri> implements ComTCommaxParametriDao {

	@Override
	public List<ComTCommaxParametri> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTCommaxParametri t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idParametro ");

		TypedQuery<ComTCommaxParametri> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@Override
	public Timestamp getNowFromDB() {

		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();

		sql.append("select SYSDATE FROM DUAL");
		

		Query query = composeNativeQuery(sql.toString(), params);
		Timestamp resultCheck = ((Timestamp) query.getSingleResult());

		return resultCheck;
	}
	
	public boolean tryLockSemaforo() {
		int updattedRows = entityManager.createNativeQuery("UPDATE COM_T_COMMAX_PARAMETRI "
				+ "SET VALORE_PARAMETRO = '0' "
				+ "WHERE ID_PARAMETRO = 9 AND VALORE_PARAMETRO = '1'")
		.executeUpdate();
		entityManager.flush();
		
		return updattedRows > 0;
		
	}
	
	public boolean unLockSemaforo() {
		int updattedRows = entityManager.createNativeQuery("UPDATE COM_T_COMMAX_PARAMETRI "
				+ "SET VALORE_PARAMETRO = '1' "
				+ "WHERE ID_PARAMETRO = 9 AND VALORE_PARAMETRO = '0'")
		.executeUpdate();
		entityManager.flush();
		
		return updattedRows > 0;		
	}

}
