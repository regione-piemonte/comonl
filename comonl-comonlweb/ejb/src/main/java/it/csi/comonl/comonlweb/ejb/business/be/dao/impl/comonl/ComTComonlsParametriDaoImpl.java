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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTComonlsParametriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTComonlsParametri;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTComonlsParametri
 */
@ApplicationScoped
public class ComTComonlsParametriDaoImpl extends BaseEntityDaoImpl<Long, ComTComonlsParametri>
		implements ComTComonlsParametriDao {

	@Override
	public List<ComTComonlsParametri> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTComonlsParametri t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idParametro ");

		TypedQuery<ComTComonlsParametri> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public Optional<ComTComonlsParametri> findByDescrizioneParametro(String descrizione) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTComonlsParametri t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.dsParametro", "dsParametro", descrizione);

		jpql.append(" ORDER BY t.idParametro ");

		TypedQuery<ComTComonlsParametri> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}

}
