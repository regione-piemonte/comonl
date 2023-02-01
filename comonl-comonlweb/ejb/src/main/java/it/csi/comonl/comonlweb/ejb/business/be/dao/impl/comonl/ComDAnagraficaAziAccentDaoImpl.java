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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDAnagraficaAziAccentDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComDAnagraficaAziAccent;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComDAnagraficaAziAccent
 */
@ApplicationScoped
public class ComDAnagraficaAziAccentDaoImpl extends BaseEntityDaoImpl<Long, ComDAnagraficaAziAccent>
		implements ComDAnagraficaAziAccentDao {

	@Override
	public List<ComDAnagraficaAziAccent> findByField(String field) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDAnagraficaAziAccent t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComDAnagraficaAziAccent ");

		TypedQuery<ComDAnagraficaAziAccent> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public Optional<ComDAnagraficaAziAccent> findByCodiceFiscale(String codiceFiscale) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDAnagraficaAziAccent t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.codiceFiscale", "codiceFiscale", codiceFiscale);

		jpql.append(" ORDER BY t.idComDAnagraficaAziAccent ");

		TypedQuery<ComDAnagraficaAziAccent> query = composeTypedQuery(jpql, params);

		return query.getResultList().stream().findFirst();
	}

}
