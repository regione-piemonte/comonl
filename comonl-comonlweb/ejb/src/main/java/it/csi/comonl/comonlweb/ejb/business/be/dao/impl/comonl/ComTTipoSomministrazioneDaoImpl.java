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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoSomministrazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoSomministrazione;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * Data Access Object implementor for the entity ComTTipoSomministrazione
 */
@ApplicationScoped
public class ComTTipoSomministrazioneDaoImpl extends BaseEntityDaoImpl<Long, ComTTipoSomministrazione> implements ComTTipoSomministrazioneDao {

	@Override
	public List<ComTTipoSomministrazione> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoSomministrazione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTTipoSomministrazione ");

		TypedQuery<ComTTipoSomministrazione> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		 
		sql.append(" SELECT  DISTINCT s.ID_COM_T_TIPO_SOMMINISTRAZIONE,s.DS_COM_T_TIPO_SOMMINISTRAZIONE "); 
	    sql.append(" FROM  COM_T_TIPO_SOMMINISTRAZIONE s  WHERE 1=1 ");
	    if(filtro != null) {
	    	if (filtro.getIdDecodifica() != null ) {
		    	 params.put("idDecod", filtro.getIdDecodifica());
		    	 sql.append(" AND s.ID_COM_T_TIPO_SOMMINISTRAZIONE <> :idDecod");
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
}
