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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoComunicazioneTuDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoComunicazioneTu;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * Data Access Object implementor for the entity ComTTipoComunicazioneTu
 */
@ApplicationScoped
public class ComTTipoComunicazioneTuDaoImpl extends BaseEntityDaoImpl<Long, ComTTipoComunicazioneTu> implements ComTTipoComunicazioneTuDao {

	@Override
	public List<ComTTipoComunicazioneTu> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoComunicazioneTu t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTTipoComunicazioneTu ");

		TypedQuery<ComTTipoComunicazioneTu> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@Override
	public List<ComTTipoComunicazioneTu> findAllByFlg(Boolean flgRicerca) {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("now", now);

		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoComunicazioneTu t ");
		jpql.append(" WHERE 1=1 ");
		
		if(flgRicerca != null && flgRicerca) {
			String codAnnullamento = ComonlConstants.TIPO_COMUNICAZIONE_TU_ANNULLAMENTO_COD_MIN;
			String codRettifica = ComonlConstants.TIPO_COMUNICAZIONE_TU_RETTIFICA_COD_MIN;
			params.put("codAnnullamento",codAnnullamento);
			params.put("codRettifica",codRettifica);
			jpql.append("   AND (t.codTipoComunicazioneMin = :codAnnullamento OR t.codTipoComunicazioneMin = :codRettifica) ");
		}
		

		jpql.append("    AND (t.dtInizio IS NULL OR (t.dtInizio IS NOT NULL and t.dtInizio <= :now ) ) ");
		jpql.append("    AND (t.dtFine   IS NULL OR (t.dtFine   IS NOT NULL and t.dtFine   >= :now ) ) ");

		TypedQuery<ComTTipoComunicazioneTu> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

}
