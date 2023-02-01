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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTGradoContrattualeDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTGradoContrattuale;
import it.csi.comonl.comonlweb.ejb.entity.ComTIstat2001livello5;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTGradoContrattuale
 */
@ApplicationScoped
public class ComTGradoContrattualeDaoImpl extends BaseEntityDaoImpl<Long, ComTGradoContrattuale> implements ComTGradoContrattualeDao {

	@Override
	public List<ComTGradoContrattuale> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTGradoContrattuale t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTGradoContrattuale ");

		TypedQuery<ComTGradoContrattuale> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<ComTGradoContrattuale> findValid() {
		
		Date now = new Date();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("now", now);
		
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTGradoContrattuale t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andCheckDateValidita(jpql, params,"t",now);
		
		jpql.append(" ORDER BY t.idComTGradoContrattuale ");
		
		TypedQuery<ComTGradoContrattuale> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	public Optional<ComTGradoContrattuale> findByCodiceDescrizione(String codice, String descrizione){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTGradoContrattuale t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codGradoContrattuale", "codGradoContrattuale", codice);
		JpaQueryHelper.andFieldEquals(jpql, params, "t.dsComTGradoContrattuale", "dsComTGradoContrattuale", descrizione);
		
		jpql.append(" ORDER BY t.idComTGradoContrattuale ");
		
		TypedQuery<ComTGradoContrattuale> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}

}
