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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRComunicazioneDatoreDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComRComunicazioneDatore;
import it.csi.comonl.comonlweb.ejb.entity.ComRComunicazioneDatorePK;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComRComunicazioneDatore
 */
@ApplicationScoped
public class ComRComunicazioneDatoreDaoImpl extends BaseEntityDaoImpl<ComRComunicazioneDatorePK, ComRComunicazioneDatore> implements ComRComunicazioneDatoreDao {

	@Override
	public List<ComRComunicazioneDatore> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRComunicazioneDatore t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<ComRComunicazioneDatore> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public Optional<ComRComunicazioneDatore> findDatoreByIdComunicazione(Long idComunicazione) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRComunicazioneDatore t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDComunicazione.idComDComunicazione", "idComDComunicazione", idComunicazione);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<ComRComunicazioneDatore> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}
	@Override
	public List<ComRComunicazioneDatore> findLegameCoDatoreByIdComunicazione(Long idComunicazione) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRComunicazioneDatore t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDComunicazione.idComDComunicazione", "idComDComunicazione", idComunicazione);
		
		jpql.append(" ORDER BY t.id ");
		
		TypedQuery<ComRComunicazioneDatore> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

}
