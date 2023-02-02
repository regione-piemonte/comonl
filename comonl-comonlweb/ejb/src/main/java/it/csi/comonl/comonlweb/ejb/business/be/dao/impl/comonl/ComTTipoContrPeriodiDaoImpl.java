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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoContrPeriodiDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContrPeriodi;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTTipoContrPeriodi
 */
@ApplicationScoped
public class ComTTipoContrPeriodiDaoImpl extends BaseEntityDaoImpl<Long, ComTTipoContrPeriodi> implements ComTTipoContrPeriodiDao {

	@Override
	public List<ComTTipoContrPeriodi> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoContrPeriodi t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTTipoContrPeriodi ");

		TypedQuery<ComTTipoContrPeriodi> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	@Override
	public Optional<ComTTipoContrPeriodi> findByTipoContrattoInData(Long idComTTipoContratto, Date inData) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoContrPeriodi t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comTTipoContratti.id", "idComTTipoContratto", idComTTipoContratto);
		if (inData!=null) {
			jpql.append(" AND ( t.dtNonValidDa is null or (t.dtNonValidDa is not null and t.dtNonValidDa >= :inData) ) ");
			jpql.append(" AND ( t.dtNonValidA is null or (t.dtNonValidA is not null and t.dtNonValidA <= :inData) ) ");
			params.put("inData", inData);
		}
		
		jpql.append(" ORDER BY t.idComTTipoContrPeriodi ");
		
		TypedQuery<ComTTipoContrPeriodi> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}

}
