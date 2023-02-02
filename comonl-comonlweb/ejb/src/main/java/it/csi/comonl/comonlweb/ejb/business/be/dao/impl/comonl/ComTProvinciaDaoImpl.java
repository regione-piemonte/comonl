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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTProvinciaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTProvincia;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComTProvincia
 */
@ApplicationScoped
public class ComTProvinciaDaoImpl extends BaseEntityDaoImpl<Long, ComTProvincia> implements ComTProvinciaDao {

	@Override
	public List<ComTProvincia> findByRegione(String codRegioneMin) {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("now", now);

		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTProvincia t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.comTRegione.codRegioneMin", "codRegioneMin", codRegioneMin);

		jpql.append("    AND (t.dtInizio IS NULL OR (t.dtInizio IS NOT NULL and t.dtInizio <= :now ) ) ");
		jpql.append("    AND (t.dtFine   IS NULL OR (t.dtFine   IS NOT NULL and t.dtFine   >= :now ) ) ");

		jpql.append(" ORDER BY t.dsComTProvincia ");

		TypedQuery<ComTProvincia> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public ComTProvincia findByDsTarga(String dsTarga) {
		Map<String, Object> params = new HashMap<String, Object>();
//		Date now = new Date();
//		params.put("now", now);

		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTProvincia t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.dsTarga", "dsTarga", dsTarga);

		TypedQuery<ComTProvincia> query = composeTypedQuery(jpql, params);
		if (null != query.getResultList() && query.getResultList().size() > 0) {
			return (ComTProvincia) query.getResultList().get(0);
		}
		return null;
	}
	
	@Override
	public Long getProtocolloDaSequence(String targaProvincia) {
		String sb = "SELECT SEQ_PIEMONTE_" + targaProvincia + ".nextVal from dual";
		Query query = composeNativeQuery(sb, null);
		BigDecimal result=(BigDecimal)query.getSingleResult();
		return result.longValue();
	}	

}
