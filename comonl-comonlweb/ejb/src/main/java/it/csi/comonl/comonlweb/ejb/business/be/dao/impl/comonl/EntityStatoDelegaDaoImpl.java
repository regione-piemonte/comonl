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

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityStatoDelegaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTStatoComunicazione;
import it.csi.comonl.comonlweb.ejb.entity.EntityStatoDelega;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity EntityStatoDelega
 */
@ApplicationScoped
public class EntityStatoDelegaDaoImpl extends BaseEntityDaoImpl<String, EntityStatoDelega> implements EntityStatoDelegaDao {

	@Override
	public List<EntityStatoDelega> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityStatoDelega t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idStatoDelega ");

		TypedQuery<EntityStatoDelega> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<EntityStatoDelega> findAllByFlg(Boolean flgRicerca) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<EntityStatoDelega> findAllByFlg(Boolean flgRicerca) {
//		Map<String, Object> params = new HashMap<String, Object>();
//
//		StringBuilder jpql = new StringBuilder();
//		jpql.append(" FROM ComTStatoComunicazione t ");
//		jpql.append(" WHERE 1=1 ");
//		
//		
//		if(flgRicerca != null && flgRicerca) {
//			Long controllata = ComonlConstants.STATO_DELEGA_CONTROLLATA_ID;
//			Long daFirmare = ComonlConstants.STATO_DELEGA_DA_FIRMARE_ID;
//			params.put("controllata",controllata);
//			params.put("daFirmare",daFirmare);
//			jpql.append("   AND (t.idComTStatoDelega <> :controllata AND t.idComTStatoComunicazione <> :daFirmare) ");
//		}
//		
//
//		TypedQuery<EntityStatoDelega> query = composeTypedQuery(jpql, params);
//		return query.getResultList();
//	}

}
