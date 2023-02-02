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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTrasformazionerlDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTEntePrevidenziale;
import it.csi.comonl.comonlweb.ejb.entity.ComTTrasformazionerl;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTTrasformazionerl
 */
@ApplicationScoped
public class ComTTrasformazionerlDaoImpl extends BaseEntityDaoImpl<Long, ComTTrasformazionerl> implements ComTTrasformazionerlDao {

	@Override
	public List<ComTTrasformazionerl> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTrasformazionerl t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTTrasformazionerl ");

		TypedQuery<ComTTrasformazionerl> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@Override
	public List<ComTTrasformazionerl> findByTipo(String tipo) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTrasformazionerl t ");
		jpql.append(" WHERE 1=1 ");
		jpql.append(" AND t.dtFine IS NULL ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.tipo", "tipo", tipo);

//		jpql.append(" ORDER BY t.idComTTrasformazionerl ");

		TypedQuery<ComTTrasformazionerl> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	public 	Optional<ComTTrasformazionerl> findValid(ComTTrasformazionerl comTTrasformazionerl, Date date)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTrasformazionerl t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id", "id", comTTrasformazionerl.getId());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codTrasformazionirlMin", "codTrasformazionirlMin", comTTrasformazionerl.getCodTrasformazionirlMin());
		JpaQueryHelper.andCheckDateValidita(jpql, params, "t", date);
		
		params.put("now", date);
		
		jpql.append(" ORDER BY t.idComTTrasformazionerl ");

		TypedQuery<ComTTrasformazionerl> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}	
}
