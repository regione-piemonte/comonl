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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoComunicazione;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * Data Access Object implementor for the entity ComTTipoComunicazione
 */
@ApplicationScoped
public class ComTTipoComunicazioneDaoImpl extends BaseEntityDaoImpl<String, ComTTipoComunicazione> implements ComTTipoComunicazioneDao {

	@Override
	public List<ComTTipoComunicazione> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoComunicazione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTTipoComunicazione ");

		TypedQuery<ComTTipoComunicazione> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		 
		sql.append(" SELECT  ID_COM_T_TIPO_COMUNICAZIONE, DS_COM_T_TIPO_COMUNICAZIONE "); 
	    sql.append(" FROM  COM_T_TIPO_COMUNICAZIONE  WHERE 1=1 ");
	    List<String> idComTipoComunicaziones = new ArrayList<String>();
	    idComTipoComunicaziones.add(ComonlConstants.TIPO_COMUNICAZIONE_URGENZA_ID);
	    idComTipoComunicaziones.add(ComonlConstants.TIPO_COMUNICAZIONE_VARIAZIONE_DATORE_ID);
	    params.put("idComTipoComunicaziones", idComTipoComunicaziones);
   	 	sql.append(" AND ID_COM_T_TIPO_COMUNICAZIONE NOT IN :idComTipoComunicaziones ");
	    if(filtro != null) {
	    	if (filtro.getIdDecodifica() != null ) {
		    	 params.put("idDecod", filtro.getIdDecodifica());
		    	 sql.append(" AND ID_COM_T_TIPO_COMUNICAZIONE = :idDecod");
		    } 
	    }
	    sql.append(" ORDER BY DS_COM_T_TIPO_COMUNICAZIONE ");
	    Query query = composeNativeQuery(sql.toString(), params);
	    List<Object []> elenco = query.getResultList();
	    List<DecodificaGenerica> decList = new ArrayList<DecodificaGenerica>();
	    
	    for(Object []  obj : elenco) {
	    	DecodificaGenerica dec = new DecodificaGenerica();
	    	if (obj[0] != null) {
	    		dec.setIdComTipoComunicazione((String) obj[0]);
	    	} 
	    	if (obj[1] != null) {
	       	   dec.setDsDecodifica((String) obj[1]);
	    	}
	    	dec.setIdComTipoTracciato(ComonlConstants.TIPO_TRACCIATO_UNILAV_ID);
	    	decList.add(dec);
		}
		return decList;
	}

}
