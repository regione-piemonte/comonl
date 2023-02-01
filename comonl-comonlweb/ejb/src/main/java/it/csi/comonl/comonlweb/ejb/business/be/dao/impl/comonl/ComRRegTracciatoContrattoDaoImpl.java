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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRRegTracciatoContrattoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComRRegTracciatoContratto;
import it.csi.comonl.comonlweb.ejb.entity.ComRRegTracciatoContrattoPK;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComRRegTracciatoContratto
 */
@ApplicationScoped
public class ComRRegTracciatoContrattoDaoImpl extends BaseEntityDaoImpl<ComRRegTracciatoContrattoPK, ComRRegTracciatoContratto> implements ComRRegTracciatoContrattoDao {

	@Override
	public List<ComRRegTracciatoContratto> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRRegTracciatoContratto t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<ComRRegTracciatoContratto> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@Override
	public List<ComRRegTracciatoContratto> findByCodContrattoTipoTracciatoTipoCo(String codContratto, String tipoTracciato, String tipoComunicazione) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRRegTracciatoContratto t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comTTipoComunicazione.idComTTipoComunicazione", "tipoComunicazione", tipoComunicazione);
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comTTipoContratti.codTipoContrattoMin", "codContratto", codContratto);
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comTTipoTracciato.idComTTipoTracciato", "tipoTracciato", tipoTracciato);
		
		TypedQuery<ComRRegTracciatoContratto> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

}
