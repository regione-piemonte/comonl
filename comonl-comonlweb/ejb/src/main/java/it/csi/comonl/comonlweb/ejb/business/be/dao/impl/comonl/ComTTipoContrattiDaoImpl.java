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
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoContrattiDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContratti;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoOrario;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * Data Access Object implementor for the entity ComTTipoContratti
 */
@ApplicationScoped
public class ComTTipoContrattiDaoImpl extends BaseEntityDaoImpl<Long, ComTTipoContratti> implements ComTTipoContrattiDao {

	@Override
	public List<ComTTipoContratti> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoContratti t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTTipoContratto ");

		TypedQuery<ComTTipoContratti> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@Override
	public List<ComTTipoContratti> findValidDate() {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("now", now);
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoContratti t ");
		jpql.append(" WHERE 1=1 ");
		jpql.append(" AND (t.dtInizio IS NULL OR (t.dtInizio IS NOT NULL AND t.dtInizio <= :now)) ");
		jpql.append(" AND (t.dtFine IS NULL OR (t.dtFine IS NOT NULL AND t.dtFine >= :now)) ");
		jpql.append(" ORDER BY t.idComTTipoContratto ");

		TypedQuery<ComTTipoContratti> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<ComTTipoContratti> GetTipoContrattiByTracciatoAndTipoComunicazioneService(String tipoTracciato,
			String tipoCmunicazione) {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("now", now);
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT tc FROM ComTTipoContratti tc JOIN ComTTipoContrAmmPerCom tca ON tca.comTTipoContratti.idComTTipoContratto = tc.idComTTipoContratto ");
		jpql.append(" WHERE 1=1 ");
		if(tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_UNILAV_ID)) {
			jpql.append(" AND tca.flgVldUl = 'S' ");
		}else if(tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID)) {
			jpql.append(" AND tca.flgVldSom = 'S' ");
		}else if(tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_VARDATORI_ID)) {
			jpql.append(" AND tca.flgVldVd = 'S' ");
		}
		
		if(tipoCmunicazione.equals(ComonlConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			jpql.append(" AND tca.flgVldCes = 'S' ");
		}else if(tipoCmunicazione.equals(ComonlConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			jpql.append(" AND tca.flgVldPro = 'S' ");
		}else if(tipoCmunicazione.equals(ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE)) {
			jpql.append(" AND tca.flgVldTrs = 'S' ");
		}else if(tipoCmunicazione.equals(ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO)) {
			jpql.append(" AND tca.flgVldTra = 'S' ");
		}
		
		jpql.append(" AND (tc.dtInizio IS NULL OR (tc.dtInizio IS NOT NULL AND tc.dtInizio <= :now)) ");
		jpql.append(" AND (tc.dtFine IS NULL OR (tc.dtFine IS NOT NULL AND tc.dtFine >= :now)) ");
		jpql.append(" ORDER BY tc.idComTTipoContratto ");

		TypedQuery<ComTTipoContratti> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	public Optional<ComTTipoContratti> findValid(ComTTipoContratti comTTipoContratti, Date date){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTTipoContratti t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id", "id", comTTipoContratti.getId());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codTipoContrattoMin", "codTipoContrattoMin", comTTipoContratti.getCodTipoContrattoMin());
		JpaQueryHelper.andCheckDateValidita(jpql, params, "t", date);
		params.put("now", date);

		TypedQuery<ComTTipoContratti> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}

}
