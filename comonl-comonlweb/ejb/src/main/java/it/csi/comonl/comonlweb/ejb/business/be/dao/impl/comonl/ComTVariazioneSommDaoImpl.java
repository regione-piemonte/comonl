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

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTVariazioneSommDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTVariazioneSomm;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * Data Access Object implementor for the entity ComTVariazioneSomm
 */
@ApplicationScoped
public class ComTVariazioneSommDaoImpl extends BaseEntityDaoImpl<Long, ComTVariazioneSomm> implements ComTVariazioneSommDao {

	@Override
	public List<ComTVariazioneSomm> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTVariazioneSomm t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTVariazioneSomm ");

		TypedQuery<ComTVariazioneSomm> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		 
		sql.append(" SELECT  DISTINCT  ID_COM_T_VARIAZIONE_SOMM, DS_VARIAZIONE "); 
	    sql.append(" FROM  COM_T_VARIAZIONE_SOMM  WHERE 1=1 ");
	    if(filtro != null) {
	    	if (filtro.getIdDecodifica() != null ) {
		    	 params.put("idDecod", filtro.getIdDecodifica().toString());
		    	 sql.append(" AND ID_COM_T_VARIAZIONE_SOMM = :idDecod");
		    } else {
		    	if (filtro.getCodDecodifica() != null) {
		    		params.put("codDecod",  "%" + filtro.getCodDecodifica().toUpperCase() + "%");
		    		sql.append(" AND upper(COD_TIPO_VARIAZIONE_MIN) like :codDecod");
		    	}  
		    	if (filtro.getDsDecodifica() != null) {
		    		params.put("dsDecod", "%" + filtro.getDsDecodifica().toUpperCase() + "%");
		    		sql.append(" AND upper(DS_VARIAZIONE) like :dsDecod");
		    	}   
		    } 
		    if (!filtro.isFlgAncheNonValidi()) {
		    	Date now = new Date();
				params.put("now", now);
			    sql.append(" AND (DATA_INIZIO IS NULL OR (DATA_INIZIO IS NOT NULL and DATA_INIZIO <= :now ) ) ");
			    sql.append(" AND (DATA_FINE   IS NULL OR (DATA_FINE   IS NOT NULL and DATA_FINE   >= :now ) ) ");
		    }
	    }
	    
	    Query query = composeNativeQuery(sql.toString(), params);
	    List<Object []> elenco = query.getResultList();
	    List<DecodificaGenerica> decList = new ArrayList<DecodificaGenerica>();
	    
	    for(Object []  obj : elenco) {
	    	DecodificaGenerica dec = new DecodificaGenerica();
	    	if (obj[0] != null) {
	    		dec.setIdDecodifica(((BigDecimal) obj[0]).longValue());
	    	} 
	    	if (obj[1] != null) {
	       	   dec.setDsDecodifica((String) obj[1]);
	    	}
	    	dec.setIdComTipoTracciato(ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID);
	    	decList.add(dec);
		}
		return decList;
	}
	
	
	public ComTVariazioneSomm findTipoVariazioneBySommComm(Long tipoSomministrazione, String tipoComunicazione) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTVariazioneSomm t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.comTTipoComunicazione.id", "comTTipoComunicazione", tipoComunicazione);
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comTTipoSomministrazione.id", "comTTipoSomministrazione", tipoSomministrazione);

		jpql.append(" ORDER BY t.idComTVariazioneSomm ");

		TypedQuery<ComTVariazioneSomm> query = composeTypedQuery(jpql, params);
		
		if (query.getResultList() != null && query.getResultList().size()>0) {
			return query.getResultList().get(0);
		}
		return null;
	}

}
