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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTIstat2001livello5Dao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTIstat2001livello5;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Data Access Object implementor for the entity ComTIstat2001livello5
 */
@ApplicationScoped
public class ComTIstat2001livello5DaoImpl extends BaseEntityDaoImpl<Long, ComTIstat2001livello5> implements ComTIstat2001livello5Dao {

	@Override
	public List<ComTIstat2001livello5> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTIstat2001livello5 t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComIstat2001livello5 ");

		TypedQuery<ComTIstat2001livello5> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuilder sql = new StringBuilder();
		 
		sql.append(" SELECT  DISTINCT  ID_COM_ISTAT2001LIVELLO5 id_dec, COD_ISTAT2001LIVELLO5_MIN cod_dec, DS_COM_ISTAT2001LIVELLO5 des_dec "); 
	    sql.append(" FROM  COM_T_ISTAT2001LIVELLO5  WHERE 1=1 ");
	    if (filtro.getIdDecodifica() != null ) {
	    	 params.put("idDecod", filtro.getIdDecodifica().toString());
	    	 sql.append(" AND ID_COM_ISTAT2001LIVELLO5 = :idDecod");
        } else {
	    	if (filtro.getCodDecodifica() != null) {
	    		params.put("codDecod",  "%" + filtro.getCodDecodifica().toUpperCase() + "%");
	    		sql.append(" AND upper(COD_ISTAT2001LIVELLO5_MIN) like :codDecod");
	    	}  
	    	if (filtro.getDsDecodifica() != null) {
	    		params.put("dsDecod", "%" + filtro.getDsDecodifica().toUpperCase() + "%");
	    		sql.append(" AND upper(DS_COM_ISTAT2001LIVELLO5) like :dsDecod");
	    	}  
  
	    } 
	    if (!filtro.isFlgAncheNonValidi()) {
	    	Date now = new Date();
			params.put("now", now);
	       sql.append(" AND (DT_INIZIO IS NULL OR (DT_INIZIO IS NOT NULL and DT_INIZIO <= :now ) ) ");
	       sql.append(" AND (DT_FINE   IS NULL OR (DT_FINE   IS NOT NULL and DT_FINE   >= :now ) ) ");
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

	    	decList.add(dec);
		}
		return decList;
	}
	
	public Optional<ComTIstat2001livello5> findValid(ComTIstat2001livello5 comTIstat2001livello5, Date date){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTIstat2001livello5 t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id", "id", comTIstat2001livello5.getId());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codIstat2001livello5Min", "codIstat2001livello5Min", comTIstat2001livello5.getCodIstat2001livello5Min());
		JpaQueryHelper.andCheckDateValidita(jpql, params, "t", date);
		params.put("now", date);
		
		jpql.append(" ORDER BY t.idComIstat2001livello5 ");

		TypedQuery<ComTIstat2001livello5> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}
	
	public Optional<ComTIstat2001livello5> findByCodiceDescrizione(String codice, String descrizione){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTIstat2001livello5 t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codIstat2001livello5Min", "codIstat2001livello5Min", codice);
		JpaQueryHelper.andFieldEquals(jpql, params, "t.dsComIstat2001livello5", "dsComIstat2001livello5", descrizione);
		
		jpql.append(" ORDER BY t.idComIstat2001livello5 ");
		
		TypedQuery<ComTIstat2001livello5> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}
	
}
