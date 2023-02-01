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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCcnlDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComTCcnl;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Data Access Object implementor for the entity ComTCcnl
 */
@ApplicationScoped
public class ComTCcnlDaoImpl extends BaseEntityDaoImpl<Long, ComTCcnl> implements ComTCcnlDao {

	@Override
	public List<ComTCcnl> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTCcnl t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComTCcnl ");

		TypedQuery<ComTCcnl> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro) {
		Map<String, Object> params = new HashMap<String, Object>();

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT DISTINCT ID_COM_T_CCNL id_dec, COD_CCNL_MIN cod_dec,  DS_COM_T_CCNL des_dec ");
		sql.append(" FROM   COM_T_CCNL  WHERE 1=1 ");
		if (filtro.getIdDecodifica() != null) {
			params.put("idDecod", filtro.getIdDecodifica().toString());
			sql.append(" AND ID_COM_T_CCNL = :idDecod");
		} else {
			if (filtro.getCodDecodifica() != null) {
				params.put("codDecod", "%" + filtro.getCodDecodifica().toUpperCase() + "%");
				sql.append(" AND upper(COD_CCNL_MIN) like :codDecod");
			}
			if (filtro.getDsDecodifica() != null) {
				params.put("dsDecod", "%" + filtro.getDsDecodifica().toUpperCase() + "%");
				sql.append(" AND upper(DS_COM_T_CCNL) like :dsDecod");
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

	public Optional<ComTCcnl> findValid(ComTCcnl comTCcnl, Date date) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTCcnl t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id", "id", comTCcnl.getId());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.codCcnlMin", "codCcnlMin", comTCcnl.getCodCcnlMin());
		JpaQueryHelper.andFieldEquals(jpql, params, "t.dsComTCcnl", "dsComTCcnl", comTCcnl.getDsComTCcnl());
		JpaQueryHelper.andCheckDateValidita(jpql, params, "t", date);

		params.put("now", date);

		jpql.append(" ORDER BY t.idComTCcnl ");

		TypedQuery<ComTCcnl> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}

	@Override
	public Optional<ComTCcnl> findByCodCcnl(String codCcnl) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComTCcnl t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.codCcnlMin", "codCcnlMin", codCcnl);

		TypedQuery<ComTCcnl> query = composeTypedQuery(jpql, params);

		return query.getResultList().stream().findFirst();
	}

}
