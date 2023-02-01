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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityParametriTipoComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.EntityParametriTipoComunicazione;
import it.csi.comonl.comonlweb.ejb.entity.EntityParametriTipoComunicazionePK;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity EntityParametriTipoComunicazione
 */
@ApplicationScoped
public class EntityParametriTipoComunicazioneDaoImpl extends BaseEntityDaoImpl<EntityParametriTipoComunicazionePK, EntityParametriTipoComunicazione> implements EntityParametriTipoComunicazioneDao {

	@Override
	public List<EntityParametriTipoComunicazione> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityParametriTipoComunicazione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<EntityParametriTipoComunicazione> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

}
