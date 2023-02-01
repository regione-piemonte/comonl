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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTStatiEsteriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTStatiEsteri;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Data Access Object implementor for the entity ComTStatiEsteri
 */
@ApplicationScoped
public class ComTStatiEsteriDaoImpl extends BaseEntityDaoImpl<Long, ComTStatiEsteri> implements ComTStatiEsteriDao {

	@Override
	public List<ComTStatiEsteri> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTStatiEsteri t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTStatiEsteri ");

		TypedQuery<ComTStatiEsteri> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro) {
		Map<String, Object> params = new HashMap<String, Object>();

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT  DISTINCT  ID_COM_T_STATI_ESTERI id_dec, COD_NAZIONE_MIN cod_dec, DS_COM_T_STATI_ESTERI des_dec, SIGLA_NAZIONE sigla_naz ");
        sql.append(" FROM  COM_T_STATI_ESTERI  WHERE 1=1 ");
		if (filtro.getIdDecodifica() != null) {
	    	 params.put("idDecod", filtro.getIdDecodifica().toString());
	    	 sql.append(" AND ID_COM_T_STATI_ESTERI = :idDecod");
		} else {
			if (filtro.getCodDecodifica() != null) {
	    		params.put("codDecod",  "%" + filtro.getCodDecodifica().toUpperCase() + "%");
	    		sql.append(" AND upper(COD_NAZIONE_MIN) like :codDecod");
			}
			if (filtro.getDsDecodifica() != null) {
	    		params.put("dsDecod", "%" + filtro.getDsDecodifica().toUpperCase() + "%");
	    		sql.append(" AND upper(DS_COM_T_STATI_ESTERI) like :dsDecod");
			}
			if (filtro.getFlgStatiUe() != null) {
				params.put("flgUE", filtro.getFlgStatiUe());
		    	sql.append(" AND FLG_UE = :flgUE");
			}
		}
		if (filtro.getDataValidita() != null) {
			Date dataV = filtro.getDataValidita();
			params.put("now", dataV);
			sql.append(" AND (dt_Inizio IS NULL OR (dt_Inizio IS NOT NULL and dt_Inizio <= :now ) ) ");
			sql.append(" AND (dt_Fine   IS NULL OR (dt_Fine   IS NOT NULL and dt_Fine   >= :now ) ) ");
		} else if (!filtro.isFlgAncheNonValidi()) {
			Date now = new Date();
			params.put("now", now);
			sql.append(" AND (dt_Inizio IS NULL OR (dt_Inizio IS NOT NULL and dt_Inizio <= :now ) ) ");
			sql.append(" AND (dt_Fine   IS NULL OR (dt_Fine   IS NOT NULL and dt_Fine   >= :now ) ) ");
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
			if (obj[1] != null) {
				dec.setCodDecodifica((String) obj[1]);
			}
			if (obj[2] != null) {
				dec.setDsDecodifica((String) obj[2]);
			}
			if (obj[3] != null) {
				dec.setSiglaNazione((String) obj[3]);
			}
			decList.add(dec);
		}
		return decList;
	}


	@Override
	public List<ComTStatiEsteri> findValidDate() {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("now", now);
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTStatiEsteri t ");
		jpql.append(" WHERE 1=1 ");
		jpql.append(" AND (t.dtInizio IS NULL OR (t.dtInizio IS NOT NULL AND t.dtInizio <= :now)) ");
		jpql.append(" AND (t.dtFine IS NULL OR (t.dtFine IS NOT NULL AND t.dtFine >= :now)) ");
		jpql.append(" ORDER BY t.idComTStatiEsteri ");

		TypedQuery<ComTStatiEsteri> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public Optional<ComTStatiEsteri> findByCodStatoMin(String codNazioneMin) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTStatiEsteri t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.codNazioneMin", "codNazioneMin", codNazioneMin);


		TypedQuery<ComTStatiEsteri> query = composeTypedQuery(jpql, params);
		
		return query.getResultList().stream().findFirst();
	}
	@Override
	public  Optional<ComTStatiEsteri> findBySiglaNazione(String siglaNazione) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTStatiEsteri t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.siglaNazione", "siglaNazione", siglaNazione);
		
		
		TypedQuery<ComTStatiEsteri> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}

	public Optional<ComTStatiEsteri> findValid(ComTStatiEsteri entity, Date date) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTStatiEsteri t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.id", "id", entity.getId());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codNazioneMin", "codNazioneMin", entity.getCodNazioneMin());
		
		JpaQueryHelper.andCheckDateValidita(jpql, params, "t", date);
		params.put("now", date);
		
		TypedQuery<ComTStatiEsteri> query = composeTypedQuery(jpql, params);
		
		return query.getResultList().stream().findFirst();
	}
}
