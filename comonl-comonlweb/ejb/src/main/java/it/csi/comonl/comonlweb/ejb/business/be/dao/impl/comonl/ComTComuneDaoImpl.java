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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTComuneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTComune;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Data Access Object implementor for the entity ComTComune
 */
@ApplicationScoped
public class ComTComuneDaoImpl extends BaseEntityDaoImpl<Long, ComTComune> implements ComTComuneDao {

	@Override
	public Optional<ComTComune> findByCodComuneMin(String codComuneMin) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTComune t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.codComuneMin", "codMin", codComuneMin);

		//jpql.append(" ORDER BY t.idComTComune ");

		TypedQuery<ComTComune> query = composeTypedQuery(jpql, params);
		
		return query.getResultList().stream().findFirst();
	}
	
	@Override
	public Optional<ComTComune> findValid(ComTComune entity, Date date) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTComune t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id", "id", entity.getId());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codComuneMin", "codMin", entity.getCodComuneMin());
		
		JpaQueryHelper.andCheckDateValidita(jpql, params, "t", date);
		params.put("now", date);

		TypedQuery<ComTComune> query = composeTypedQuery(jpql, params);
		
		return query.getResultList().stream().findFirst();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DecodificaGenerica> findGenereciaByFilter(DecodificaGenerica filtro) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		 
		sql.append(" SELECT  DISTINCT   ID_COM_T_COMUNE id_dec, COD_COMUNE_MIN cod_dec, DS_COM_T_COMUNE des_dec, ID_COM_T_PROVINCIA id_opt ");
	    sql.append(" FROM  Com_T_Comune  WHERE 1=1 ");
	    if (filtro.getIdDecodifica() != null ) {
	    	 params.put("idDecod", filtro.getIdDecodifica().toString());
	    	 sql.append(" AND ID_COM_T_COMUNE = :idDecod");
	    } else {
	    	if (filtro.getCodDecodifica() != null) {
	    		params.put("codDecod",  "%" + filtro.getCodDecodifica().toUpperCase().trim() + "%");
	    		sql.append(" AND upper(COD_COMUNE_MIN) like :codDecod");
	    	}  
	    	if (filtro.getDsDecodifica() != null) {
	    		params.put("dsDecod", "%" + filtro.getDsDecodifica().toUpperCase().trim() + "%");
	    		sql.append(" AND upper(DS_COM_T_COMUNE) like :dsDecod");
	    	}
	    	if (filtro.getIdFiltroFacoltativo() != null) {
	    		params.put("idFiltroFac", filtro.getIdFiltroFacoltativo().toString());
	    		sql.append(" AND ID_COM_T_PROVINCIA = :idFiltroFac");
	    	}
	    } 
	    
	    if(filtro.getDataValidita() != null) {
	    	Date dataV = filtro.getDataValidita();
			params.put("now", dataV);
		    sql.append(" AND (dt_Inizio IS NULL OR (dt_Inizio IS NOT NULL and dt_Inizio <= :now ) ) ");
		    sql.append(" AND (dt_Fine   IS NULL OR (dt_Fine   IS NOT NULL and dt_Fine   >= :now ) ) ");
	    }else if (!filtro.isFlgAncheNonValidi()) {
	    	Date now = new Date();
			params.put("now", now);
		    sql.append(" AND (dt_Inizio IS NULL OR (dt_Inizio IS NOT NULL and dt_Inizio <= :now ) ) ");
		    sql.append(" AND (dt_Fine   IS NULL OR (dt_Fine   IS NOT NULL and dt_Fine   >= :now ) ) ");
	    }
	    sql.append(" ORDER BY des_dec ");
	    
	    Query query = composeNativeQuery(sql.toString(), params);
	    List<Object []> elenco = query.getResultList();
	    List<DecodificaGenerica> decList = new ArrayList<DecodificaGenerica>();
	    
	    for(Object []  obj : elenco) {
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
	    	if (obj[3] != null) {
	    		dec.setIdFiltroFacoltativo(((BigDecimal) obj[3]).longValue());
		    }
	    	decList.add(dec);
		}
		return decList;
	}
	
}
