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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDElaboratiOkDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComDElaboratiOk;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ElaboratiOk;

/**
 * Data Access Object implementor for the entity ComDElaboratiOk
 */
@ApplicationScoped
public class ComDElaboratiOkDaoImpl extends BaseEntityDaoImpl<Long, ComDElaboratiOk> implements ComDElaboratiOkDao {

	@Override
	public List<ComDElaboratiOk> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDElaboratiOk t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.progCom ");

		TypedQuery<ComDElaboratiOk> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public ComDElaboratiOk esisteEsitoInComDElaboratiOk(ElaboratiOk elaboratiOkDto) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDElaboratiOk t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.annoProtCom", "annoProtCom", elaboratiOkDto.getAnnoProtCom());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDUplDocumenti.id", "id", elaboratiOkDto.getUplDocumenti().getId());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.nroProtCom", "nroProtCom", elaboratiOkDto.getNroProtCom());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.nomeFileXml", "nomeFileXml", elaboratiOkDto.getNomeFileXml());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.cfImpresa", "cfImpresa", elaboratiOkDto.getCfImpresa());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.cfLavoratore", "cfLavoratore", elaboratiOkDto.getCfLavoratore());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.pv", "pv", elaboratiOkDto.getPv());


		TypedQuery<ComDElaboratiOk> query = composeTypedQuery(jpql, params);
		return query.getSingleResult();
	}
	@Override
	public List<ComDElaboratiOk> getEsitoComDElaboratiOkByIdUplDoc(Long idComDUplDocumenti) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDElaboratiOk t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDUplDocumenti.idComDUplDocumenti", "idComDUplDocumenti", idComDUplDocumenti);
		
		
		TypedQuery<ComDElaboratiOk> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
}
