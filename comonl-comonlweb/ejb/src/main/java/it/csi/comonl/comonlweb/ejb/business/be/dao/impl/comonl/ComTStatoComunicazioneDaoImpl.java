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

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTStatoComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTStatoComunicazione;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * Data Access Object implementor for the entity ComTStatoComunicazione
 */
@ApplicationScoped
public class ComTStatoComunicazioneDaoImpl extends BaseEntityDaoImpl<Long, ComTStatoComunicazione> implements ComTStatoComunicazioneDao {

	@Override
	public List<ComTStatoComunicazione> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTStatoComunicazione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTStatoComunicazione ");

		TypedQuery<ComTStatoComunicazione> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@Override
	public List<ComTStatoComunicazione> findAllByFlg(Boolean flgRicerca) {
		Map<String, Object> params = new HashMap<String, Object>();

		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTStatoComunicazione t ");
		jpql.append(" WHERE 1=1 ");
		
		
		if(flgRicerca != null && flgRicerca) {
			Long controllata = ComonlConstants.STATO_COMUNICAZIONE_CONTROLLATA_ID;
			Long daFirmare = ComonlConstants.STATO_COMUNICAZIONE_DA_FIRMARE_ID;
			params.put("controllata",controllata);
			params.put("daFirmare",daFirmare);
			jpql.append("   AND (t.idComTStatoComunicazione <> :controllata AND t.idComTStatoComunicazione <> :daFirmare) ");
		}
		

		TypedQuery<ComTStatoComunicazione> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

}
