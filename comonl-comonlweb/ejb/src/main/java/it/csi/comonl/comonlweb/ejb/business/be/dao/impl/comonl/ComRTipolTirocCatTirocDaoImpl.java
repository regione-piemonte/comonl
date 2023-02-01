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
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRTipolTirocCatTirocDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComRTipolTirocCatTiroc;
import it.csi.comonl.comonlweb.ejb.entity.ComRTipolTirocCatTirocPK;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComRTipolTirocCatTiroc
 */
@ApplicationScoped
public class ComRTipolTirocCatTirocDaoImpl extends BaseEntityDaoImpl<ComRTipolTirocCatTirocPK, ComRTipolTirocCatTiroc> implements ComRTipolTirocCatTirocDao {

	@Override
	public List<ComRTipolTirocCatTiroc> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRTipolTirocCatTiroc t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<ComRTipolTirocCatTiroc> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public ComRTipolTirocCatTiroc findCombinazioneTipologiaTirocinioCategoriaTirocinante(Long idTipologiaTirocinio, Long idcodiceCategTirocinante) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		Date dataRiferimento = new Date();

		sql.append(" FROM ComRTipolTirocCatTiroc t ");
		sql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(sql, params, "t.comTTipologiaTirocinio.idComTTipologiaTirocinio", "idComTTipologiaTirocinio", idTipologiaTirocinio);
		JpaQueryHelper.andFieldEquals(sql, params, "t.comTCategTirocinante.idComTCategTirocinante", "idComTCategTirocinante", idcodiceCategTirocinante);
		sql.append(" AND (t.dInizio IS NULL OR (t.dInizio IS NOT NULL and t.dInizio <= :dataRif ) ) ");
		sql.append(" AND (t.dFine   IS NULL OR (t.dFine   IS NOT NULL and t.dFine   >= :dataRif ) ) ");
		params.put("dataRif", dataRiferimento);

		TypedQuery<ComRTipolTirocCatTiroc> query = composeTypedQuery(sql, params);
		
		Optional<ComRTipolTirocCatTiroc> opt = query.getResultList().stream().findFirst();
		ComRTipolTirocCatTiroc ret = opt.isPresent() ? opt.get() : null;
		return ret;		
	
	}

}
