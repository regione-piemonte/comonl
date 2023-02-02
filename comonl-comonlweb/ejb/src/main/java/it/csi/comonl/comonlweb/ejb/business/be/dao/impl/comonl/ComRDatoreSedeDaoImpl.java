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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRDatoreSedeDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComRDatoreSede;
import it.csi.comonl.comonlweb.ejb.entity.ComRDatoreSedePK;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComRDatoreSede
 */
@ApplicationScoped
public class ComRDatoreSedeDaoImpl extends BaseEntityDaoImpl<ComRDatoreSedePK, ComRDatoreSede> implements ComRDatoreSedeDao {

	@Override
	public List<ComRDatoreSede> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRDatoreSede t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<ComRDatoreSede> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public Optional<ComRDatoreSede> findSedeLavoroByIdDatore(Long idDatore) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRDatoreSede t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDDatore.idComDDatore", "idComDDatore", idDatore);

		TypedQuery<ComRDatoreSede> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}
	@Override
	public List<ComRDatoreSede> findSediLavoroByIdDatore(Long idDatore) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRDatoreSede t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDDatore.idComDDatore", "idComDDatore", idDatore);
		
		TypedQuery<ComRDatoreSede> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public void delete (ComRDatoreSedePK key) {
		StringBuilder sb = new StringBuilder()
				.append("DELETE FROM ComRDatoreSede")
				.append(" WHERE ")
				.append(" id.idComDDatore = :idComDDatore")
				.append(" AND  id.idComDSedeLavoro = :idComDSedeLavoro");
		
		Map<String, Object> params = new HashMap<>();
		params.put("idComDDatore", key.getIdComDDatore());
		params.put("idComDSedeLavoro", key.getIdComDSedeLavoro());
		Query query = composeQuery(sb, params);
		query.executeUpdate();
	}
}
