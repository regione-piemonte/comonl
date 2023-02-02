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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDImportErroreDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComDImportErrore;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;


/**
 * Data Access Object implementor for the entity ProDImportErrori
 */
@ApplicationScoped
public class ComDImportErroreDaoImpl extends BaseEntityDaoImpl<Long, ComDImportErrore> implements ComDImportErroreDao {

	@Override
	public List<ComDImportErrore> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDImportErrore t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idErrore ");

		TypedQuery<ComDImportErrore> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	
	@Override
	public void deleteErroriComunicazione(String codiceComunicazioneRegionale) {
		StringBuilder sb = new StringBuilder()
				.append("DELETE FROM ")
				.append(clazz.getName())
				.append(" WHERE ")
				.append("codComunicazioneReg")
				.append(" = :codiceComunicazioneRegionale");
		Map<String, Object> params = new HashMap<>();
		params.put("codiceComunicazioneRegionale", codiceComunicazioneRegionale);
		Query query = composeQuery(sb, params);
		query.executeUpdate();
	}
	
	

}
