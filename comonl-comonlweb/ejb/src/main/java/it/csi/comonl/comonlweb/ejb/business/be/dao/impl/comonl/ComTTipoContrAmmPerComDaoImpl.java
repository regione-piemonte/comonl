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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoContrAmmPerComDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContrAmmPerCom;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTTipoContrAmmPerCom
 */
@ApplicationScoped
public class ComTTipoContrAmmPerComDaoImpl extends BaseEntityDaoImpl<Long, ComTTipoContrAmmPerCom> implements ComTTipoContrAmmPerComDao {

	@Override
	public List<ComTTipoContrAmmPerCom> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoContrAmmPerCom t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTTipoContrAmmCom ");

		TypedQuery<ComTTipoContrAmmPerCom> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@Override
	public Optional<ComTTipoContrAmmPerCom> findByTipoContratto(Long idComTTipoContratto) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoContrAmmPerCom t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.comTTipoContratti.id", "idComTTipoContratto", idComTTipoContratto);

		jpql.append(" ORDER BY t.idComTTipoContrAmmCom ");

		TypedQuery<ComTTipoContrAmmPerCom> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}

}
