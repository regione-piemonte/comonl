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
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDUplDocumentiDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComDUplDocumenti;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComDUplDocumenti
 */
@ApplicationScoped
public class ComDUplDocumentiDaoImpl extends BaseEntityDaoImpl<Long, ComDUplDocumenti> implements ComDUplDocumentiDao {

	@Override
	public List<ComDUplDocumenti> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDUplDocumenti t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComDUplDocumenti ");

		TypedQuery<ComDUplDocumenti> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	
	@Override
	public Long existCRCUplDocumenti(long crcCalcolato) {

		Map<String, Object> params = new HashMap<>();
		String flagVerifica = "N";
		
		StringBuilder jpql = new StringBuilder();
		jpql.append(" select COUNT(t.idComDUplDocumenti) ");
		jpql.append(" FROM ComDUplDocumenti t ");
		jpql.append(" WHERE 1=1 ");
		

		JpaQueryHelper.andFieldEquals(jpql, params, "t.crcUpload", "crcUpload", new BigDecimal(crcCalcolato));
		JpaQueryHelper.andFieldEquals(jpql, params, "t.flgVerifica", "flgVerifica", flagVerifica);

		Query query = composeQuery(jpql, params);

		return (Long) query.getSingleResult();
	}
	@Override
	public Date getDatRicezioneByIdUplDocumenti(Long idComDUplDocumenti) {
		
		Map<String, Object> params = new HashMap<>();
		
		StringBuilder jpql = new StringBuilder();
		jpql.append(" select t.dataRicezione ");
		jpql.append(" FROM ComDUplDocumenti t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.idComDUplDocumenti", "idComDUplDocumenti", idComDUplDocumenti);
		
		Query query = composeQuery(jpql, params);
		
		return (Date) query.getSingleResult();
	}
	
	@Override
	public List<ComDUplDocumenti> gestisciUploadNonCompletati(String minutiAttesaPrimaDiRitrasmettere) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime ldataRichiestaElab = now.minus(Long.parseLong(minutiAttesaPrimaDiRitrasmettere), ChronoUnit.MINUTES);
		java.util.Date dataRichiestaElab = Date.from(ldataRichiestaElab.atZone(ZoneId.systemDefault()).toInstant());
		
		List<String> idComStatoElaborazione = new ArrayList<String>();
		idComStatoElaborazione.add(UtilConstant.STATO_ELABORAZIONE_DA_ELABORARE);
		idComStatoElaborazione.add(UtilConstant.STATO_ELABORAZIONE_REGISTRATE);
		idComStatoElaborazione.add(UtilConstant.STATO_ELABORAZIONE_INVIATE);
		
	    params.put("idComStatoElaborazione", idComStatoElaborazione);
		params.put("dataRichiestaElab", dataRichiestaElab);
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT t  ");
		sql.append(" FROM ComDUplDocumenti t ");
		sql.append(" WHERE 1=1 ");
	    sql.append(" AND t.statoElaborazione IN :idComStatoElaborazione ");
	    sql.append(" AND t.dtRichiestaElaborazione < :dataRichiestaElab ");
	    
		TypedQuery<ComDUplDocumenti> query = composeTypedQuery(sql.toString(), params);
		return query.getResultList();
	}
}
