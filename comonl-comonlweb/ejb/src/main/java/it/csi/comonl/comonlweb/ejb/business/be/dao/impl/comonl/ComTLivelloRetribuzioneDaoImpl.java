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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringEscapeUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTLivelloRetribuzioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTCcnl;
import it.csi.comonl.comonlweb.ejb.entity.ComTLivelloRetribuzione;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

/**
 * Data Access Object implementor for the entity ComTLivelloRetribuzione
 */
@ApplicationScoped
public class ComTLivelloRetribuzioneDaoImpl extends BaseEntityDaoImpl<Long, ComTLivelloRetribuzione>
		implements ComTLivelloRetribuzioneDao {

	@Override
	public List<ComTLivelloRetribuzione> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTLivelloRetribuzione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTLivelloRetribuzione ");

		TypedQuery<ComTLivelloRetribuzione> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();

		sql.append(
				" SELECT  DISTINCT  ID_COM_T_LIVELLO_RETRIBUZIONE id_dec , COD_LIVELLO cod_dec,  DES_LIVELLO des_dec ");
		sql.append(" FROM  COM_T_LIVELLO_RETRIBUZIONE WHERE 1=1 ");
		if (filtro.getIdDecodifica() != null) {
			params.put("idDecod", filtro.getIdDecodifica().toString());
			sql.append(" AND ID_COM_T_LIVELLO_RETRIBUZIONE = :idDecod");
		} else {
			if (filtro.getCodDecodifica() != null) {
				params.put("codDecod", "%" + filtro.getCodDecodifica().toUpperCase() + "%");
				sql.append(" AND upper(COD_LIVELLO) like :codDecod");
			}
			if (filtro.getDsDecodifica() != null) {
				params.put("dsDecod", "%" + StringEscapeUtils.escapeSql(filtro.getDsDecodifica().toUpperCase()) + "%");
				sql.append(" AND upper(DES_LIVELLO) like :dsDecod");
			}
		}
		if (!filtro.isFlgAncheNonValidi()) {
			Date now = new Date();
			params.put("now", now);
			sql.append(" AND (dt_Inizio IS NULL OR (dt_Inizio IS NOT NULL and dt_Inizio <= :now ) ) ");
			sql.append(" AND (dt_Fine   IS NULL OR (dt_Fine   IS NOT NULL and dt_Fine   >= :now ) ) ");
		}
		if (ComonlUtility.isNotVoid(filtro.getIdCcnl())) {
			params.put("idCcnl", filtro.getIdCcnl());
			sql.append(" AND ID_COM_T_CCNL = :idCcnl ");
		}
		sql.append(" ORDER BY des_dec ");

		Query query = composeNativeQuery(sql.toString(), params);
		List<Object[]> elenco = query.getResultList();
		List<DecodificaGenerica> decList = new ArrayList<DecodificaGenerica>();

		for (Object[] obj : elenco) {
			DecodificaGenerica dec = new DecodificaGenerica();
			if (obj[0] != null) {
				dec.setIdDecodifica(((BigDecimal) obj[0]).longValue());
			}
			if (obj[2] != null) {
				dec.setDsDecodifica((String) obj[2]);
			}
			if (obj[1] != null) {
				dec.setCodDecodifica((String) obj[1]);
			}
			decList.add(dec);
		}
		return decList;
	}
	
	public Optional<ComTLivelloRetribuzione> findByCcnlLivello(long idCcnl, long idLivelloRetribuzione){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTLivelloRetribuzione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.comTCcnl.id", "idCcnl", idCcnl);
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comTTipoLivelloRetrib.id", "idLivelloRetribuzione", idLivelloRetribuzione);
		
		Date now = new Date();
		JpaQueryHelper.andCheckDateValidita(jpql, params, "t", now);
		params.put("now", now);
		

		TypedQuery<ComTLivelloRetribuzione> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
		
	}
	
	public ComTLivelloRetribuzione findByIdCcnlAndCodLivello(long idCcnl, String codLivelloRetribuzione){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTLivelloRetribuzione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.comTCcnl.idComTCcnl", "idCcnl", idCcnl);
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codLivello", "codLivelloRetribuzione", codLivelloRetribuzione);
		
		
		Query query = composeTypedQuery(jpql, params);
		Optional opt = query.getResultList().stream().findFirst();
		if (opt.isPresent()) {
			return (ComTLivelloRetribuzione)opt.get();
		}
		return null;
	}
	
	public Optional<ComTLivelloRetribuzione> findValid(ComTLivelloRetribuzione comTLivelloRetribuzione, Date date) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTLivelloRetribuzione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id", "id", comTLivelloRetribuzione.getId());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codLivello", "codLivello", comTLivelloRetribuzione.getCodLivello());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.desLivello", "desLivello", comTLivelloRetribuzione.getDesLivello());
		JpaQueryHelper.andCheckDateValidita(jpql, params, "t", date);

		params.put("now", date);

		TypedQuery<ComTLivelloRetribuzione> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}
}
