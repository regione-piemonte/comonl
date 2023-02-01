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
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityPersonalizzazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTCittadinanza;
import it.csi.comonl.comonlweb.ejb.entity.EntityPersonalizzazione;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity EntityPersonalizzazione
 */
@ApplicationScoped
public class EntityPersonalizzazioneDaoImpl extends BaseEntityDaoImpl<String, EntityPersonalizzazione> implements EntityPersonalizzazioneDao {

	@Override
	public List<EntityPersonalizzazione> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityPersonalizzazione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.pv ");

		TypedQuery<EntityPersonalizzazione> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public Optional<EntityPersonalizzazione> findByPv(String pv) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityPersonalizzazione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.pv", "pv", pv);

		TypedQuery<EntityPersonalizzazione> query = composeTypedQuery(jpql, params);
		
		List<EntityPersonalizzazione> list = query.getResultList();

		return list.stream().findFirst();
	}

}
