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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityDelegatoImpresaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.EntityDelegatoImpresa;
import it.csi.comonl.comonlweb.ejb.entity.EntityDelegatoImpresaPK;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

/**
 * Data Access Object implementor for the entity EntityDelegatoImpresa
 */
@ApplicationScoped
public class EntityDelegatoImpresaDaoImpl extends BaseEntityDaoImpl<EntityDelegatoImpresaPK, EntityDelegatoImpresa>
		implements EntityDelegatoImpresaDao {

	@Override
	public List<EntityDelegatoImpresa> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityDelegatoImpresa t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<EntityDelegatoImpresa> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<EntityDelegatoImpresa> findByIdAnagraficaDelegato(String cfDelegato, String tipoAnagrafica) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityDelegatoImpresa t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id.cfDelegato", "cfDelegato", cfDelegato);
		if (ComonlUtility.isNotVoid(tipoAnagrafica)) {
			JpaQueryHelper.andFieldEquals(jpql, params, "t.id.tipoAnagrafica", "tipoAnagrafica", tipoAnagrafica);
		}

		TypedQuery<EntityDelegatoImpresa> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<EntityDelegatoImpresa> findByCfImpresa(String cfImpresa) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityDelegatoImpresa t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id.cfImpresa", "cfImpresa", cfImpresa);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<EntityDelegatoImpresa> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<EntityDelegatoImpresa> findByCfImpresaValidOrNotValid(String cfImpresa,
			boolean flgDataAnnullamentoNull) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityDelegatoImpresa t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id.cfImpresa", "cfImpresa", cfImpresa);
		if (flgDataAnnullamentoNull) {
			// se flgDataAnnullamentoNull == true si cercano i DelegatoImpresaValidi, quindi
			// quelli da annullare
			JpaQueryHelper.andFieldNull(jpql, "t.dataAnnullamento");
		} else {
			JpaQueryHelper.andFieldNotNull(jpql, "t.dataAnnullamento");
		}

		jpql.append(" ORDER BY t.id ");

		TypedQuery<EntityDelegatoImpresa> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

}
