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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDRapportoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComDRapporto;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComDRapporto
 */
@ApplicationScoped
public class ComDRapportoDaoImpl extends BaseEntityDaoImpl<Long, ComDRapporto> implements ComDRapportoDao {

	@Override
	public List<ComDRapporto> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDRapporto t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComDRapporto ");

		TypedQuery<ComDRapporto> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	
	@Override
	public Optional<ComDRapporto> findByIdComunicazione(Long idComunicazione) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDRapporto t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDComunicazione.idComDComunicazione", "field", idComunicazione);


		TypedQuery<ComDRapporto> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}
	@Override
	public List<ComDRapporto> findRapportiByIdTutore(Long idTutore) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDRapporto t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDTutore.idComDTutore", "field", idTutore);
		
		
		TypedQuery<ComDRapporto> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	@Override
	public List<ComDRapporto> findRapportiByIdComunicazione(Long idComunicazione) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDRapporto t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDComunicazione.idComDComunicazione", "idComDComunicazione", idComunicazione);
		
		
		TypedQuery<ComDRapporto> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@Override
	public Optional<ComDRapporto> findByTipoRapportoAzienda(Long idComunicazione, String tipoRapportoAzienda) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDRapporto t ");
		jpql.append(" WHERE 1=1 ");
		jpql.append(" AND EXISTS ( ");
		jpql.append(" FROM ComRRapportoLavoratore crrl ");
		jpql.append(" where crrl.comDRapporto = t ");
		jpql.append(" AND crrl.comTTipoRapportoAzienda.idComTTipoRapportoAzienda = :tipoRapportoAzienda ");
		jpql.append(" ) ");
		params.put("tipoRapportoAzienda", tipoRapportoAzienda);

		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDComunicazione.idComDComunicazione", "field", idComunicazione);

		TypedQuery<ComDRapporto> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}

}
