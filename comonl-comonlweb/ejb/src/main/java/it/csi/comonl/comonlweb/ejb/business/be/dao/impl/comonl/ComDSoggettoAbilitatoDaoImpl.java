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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDSoggettoAbilitatoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseAuditedEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComDSoggettoAbilitato;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComDSoggettoAbilitato
 */
@ApplicationScoped
public class ComDSoggettoAbilitatoDaoImpl extends BaseAuditedEntityDaoImpl<Long, ComDSoggettoAbilitato>
		implements ComDSoggettoAbilitatoDao {

	@Override
	public List<ComDSoggettoAbilitato> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDSoggettoAbilitato t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComDSoggettoAbilitato ");

		TypedQuery<ComDSoggettoAbilitato> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public Optional<ComDSoggettoAbilitato> findByCfSoggetto(String cfSoggetto) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDSoggettoAbilitato t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.cfSoggetto", "cfSoggetto", cfSoggetto);
		
		//TODO controllare che non sia annullato una volta aggiunta la data di cancellazione

		// jpql.append(" ORDER BY t.idComTComune ");

		TypedQuery<ComDSoggettoAbilitato> query = composeTypedQuery(jpql, params);

		return query.getResultList().stream().findFirst();
	}

}
