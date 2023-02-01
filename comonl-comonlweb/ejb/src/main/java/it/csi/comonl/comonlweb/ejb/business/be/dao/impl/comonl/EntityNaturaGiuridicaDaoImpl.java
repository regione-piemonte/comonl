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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityNaturaGiuridicaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.EntityNaturaGiuridica;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTTipoComunicazione
 */
@ApplicationScoped
public class EntityNaturaGiuridicaDaoImpl extends BaseEntityDaoImpl<String, EntityNaturaGiuridica> implements EntityNaturaGiuridicaDao {

	@Override
	public List<EntityNaturaGiuridica> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityNaturaGiuridica t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.cNaturaGiuridica ");

		TypedQuery<EntityNaturaGiuridica> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<EntityNaturaGiuridica> findValidDate() {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("now", now);
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityNaturaGiuridica t ");
		jpql.append(" WHERE 1=1 ");
		jpql.append(" AND (t.dtInizioVldt IS NULL OR (t.dtInizioVldt IS NOT NULL AND t.dtInizioVldt <= :now)) ");
		jpql.append(" AND (t.dtFineVldt IS NULL OR (t.dtFineVldt IS NOT NULL AND t.dtFineVldt >= :now)) ");
		jpql.append(" ORDER BY t.naturaGiuridica ");

		TypedQuery<EntityNaturaGiuridica> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

}
