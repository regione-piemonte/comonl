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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringEscapeUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTAtecofinDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTAtecofin;
import it.csi.comonl.comonlweb.ejb.entity.ComTTrasformazionerl;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Data Access Object implementor for the entity ComTAtecofin
 */
@ApplicationScoped
public class ComTAtecofinDaoImpl extends BaseEntityDaoImpl<Long, ComTAtecofin> implements ComTAtecofinDao {

	@Override
	public List<ComTAtecofin> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTAtecofin t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTAtecofin ");

		TypedQuery<ComTAtecofin> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();

		sql.append(
				" SELECT  DISTINCT  ID_COM_T_ATECOFIN id_dec , COD_ATECOFIN_MIN cod_dec,  DS_COM_T_ATECOFIN des_dec ");
		sql.append(" FROM  Com_T_Atecofin WHERE 1=1 ");
		if (filtro.getIdDecodifica() != null) {
			params.put("idDecod", filtro.getIdDecodifica().toString());
			sql.append(" AND ID_T_ATECOFIN = :idDecod");
		} else {
			if (filtro.getCodDecodifica() != null) {
				params.put("codDecod", "%" + filtro.getCodDecodifica().toUpperCase() + "%");
				sql.append(" AND upper(COD_ATECOFIN_MIN) like :codDecod");
			}
			if (filtro.getDsDecodifica() != null) {
				params.put("dsDecod", "%" + StringEscapeUtils.escapeSql(filtro.getDsDecodifica().toUpperCase()) + "%");
				sql.append(" AND upper(DS_COM_T_ATECOFIN) like :dsDecod");
			}
		}
		if (!filtro.isFlgAncheNonValidi()) {
			Date now = new Date();
			params.put("now", now);
			sql.append(" AND (dt_Inizio IS NULL OR (dt_Inizio IS NOT NULL and dt_Inizio <= :now ) ) ");
			sql.append(" AND (dt_Fine   IS NULL OR (dt_Fine   IS NOT NULL and dt_Fine   >= :now ) ) ");
		}
		sql.append(" ORDER BY des_dec ");

		Query query = composeNativeQuery(sql.toString(), params);
		List<Object[]> elenco = query.getResultList();
		List<DecodificaGenerica> decList = new ArrayList<DecodificaGenerica>();

		for (Object[] obj : elenco) {
			DecodificaGenerica dec = new DecodificaGenerica();
			if (obj[0] != null) {
				dec.setIdDecodifica(((BigDecimal) obj[0]).longValue());
			}
			if (obj[2] != null) {
				dec.setDsDecodifica((String) obj[2]);
			}
			if (obj[1] != null) {
				dec.setCodDecodifica((String) obj[1]);
			}
			decList.add(dec);
		}
		return decList;
	}

	@Override
	public Optional<ComTAtecofin> findByCodAteco(String codAteco) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTAtecofin t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.codAtecofinMin", "codAtecofinMin", codAteco);

		TypedQuery<ComTAtecofin> query = composeTypedQuery(jpql, params);

		return query.getResultList().stream().findFirst();
	}
	
	public 	Optional<ComTAtecofin> findValid(ComTAtecofin entity, Date date)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTAtecofin t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id", "id", entity.getId());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codAtecofinMin", "codAtecofinMin", entity.getCodAtecofinMin());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.dsComTAtecofin", "dsComTAtecofin", entity.getDsComTAtecofin());
		JpaQueryHelper.andCheckDateValidita(jpql, params, "t", date);
		
		params.put("now", date);

		TypedQuery<ComTAtecofin> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}	
}
