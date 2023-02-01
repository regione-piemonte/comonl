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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityDelegaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseAuditedEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.EntityDelega;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.ejb.util.jpa.Page;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaDelega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.StatoDelega;

/**
 * Data Access Object implementor for the entity EntityDelega
 */
@ApplicationScoped
public class EntityDelegaDaoImpl extends BaseAuditedEntityDaoImpl<Long, EntityDelega> implements EntityDelegaDao {

	@Override
	public List<EntityDelega> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityDelega t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idDelega ");

		TypedQuery<EntityDelega> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public Long countRicercaDelega(FormRicercaDelega ricercaDelega) {
		log.debug("countRicerca----------->", "Entro nel metodo countRicerca");
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		composeQueryRicerca(ricercaDelega, params, jpql);
		Query qn = composeQuery(getCountQuery(jpql), params);
		long count = ((Number) qn.getSingleResult()).longValue();
		return count;

	}

	@Override
	public Page<EntityDelega> findPaginated(int page, int size, String sortField, String sortDirection,
			FormRicercaDelega ricercaDelega) {
		Map<String, Object> params = new HashMap<>();

		StringBuilder jpql = new StringBuilder();

		composeQueryRicerca(ricercaDelega, params, jpql);

		/*
		 * jpql.append(" ORDER BY "); jpql.
		 * append("   NVL(t.dataRiferimentoProspetto, TO_DATE('31/12/2999', 'DD/MM/YYYY')) DESC, "
		 * ); jpql.append("   t.cfComunicazione, ");
		 */
		// is not sufficient to give a deterministic answer, you do need to add more
		// columns. The most obvious choice would be a primary key column

		return getPagedResult(jpql, params, page, size);
	}

	private void composeQueryRicerca(FormRicercaDelega ricercaDelega, Map<String, Object> params, StringBuilder jpql) {

		jpql.append(" FROM ");
		jpql.append(" EntityDelega t ");
		jpql.append(" WHERE 1=1 ");

		if (ricercaDelega != null) {

			if (!StringUtils.isEmpty(ricercaDelega.getCodiceFiscaleDelegato())) {
				jpql.append(" AND UPPER(t.cfDelegato) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :codiceFiscaleDelegato),'%'))");
				params.put("codiceFiscaleDelegato", ricercaDelega.getCodiceFiscaleDelegato().trim());
			}
			if (!StringUtils.isEmpty(ricercaDelega.getCognomeDelegato())) {
				jpql.append(" AND UPPER(t.cognomeDelegato) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :cognomeDelegato),'%'))");
				params.put("cognomeDelegato", ricercaDelega.getCognomeDelegato().trim());
			}
			if (ricercaDelega.getNomeDelegato() != null) {
				jpql.append(" AND UPPER(t.nomeDelegato) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :nomeDelegato),'%'))");
				params.put("nomeDelegato", ricercaDelega.getNomeDelegato().trim());
			}
			if (!StringUtils.isEmpty(ricercaDelega.getCodiceFiscaleDelegante())) {
				jpql.append(" AND UPPER(t.cfDelegante) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :codiceFiscaleDelegante),'%'))");
				params.put("codiceFiscaleDelegante", ricercaDelega.getCodiceFiscaleDelegante().trim());
			}
			if (ricercaDelega.getCognomeDelegante() != null) {
				jpql.append(" AND UPPER(t.cognomeDelegante) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :cognomeDelegante),'%'))");
				params.put("cognomeDelegante", ricercaDelega.getCognomeDelegante().trim());
			}
			if (ricercaDelega.getNomeDelegante() != null) {
				jpql.append(" AND UPPER(t.nomeDelegante) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :nomeDelegante),'%'))");
				params.put("nomeDelegante", ricercaDelega.getNomeDelegante().trim());
			}
			if (ricercaDelega.getCodiceFiscaleImpresa() != null) {
				jpql.append(" AND UPPER(t.cfImpresa) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :codiceFiscaleImpresa),'%'))");
				params.put("codiceFiscaleImpresa", ricercaDelega.getCodiceFiscaleImpresa().trim());
			}
			if (ricercaDelega.getDenominazione() != null) {
				jpql.append(" AND UPPER(t.denominazioneImpresa) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :denominazione),'%'))");
				params.put("denominazione", ricercaDelega.getDenominazione().trim());
			}

			if (ricercaDelega.getStatoDelegas() != null && ricercaDelega.getStatoDelegas().size() > 0) {
				List<String> statos = new ArrayList<>();
				for (StatoDelega statoDelega : ricercaDelega.getStatoDelegas()) {

					statos.add(statoDelega.getDsStatoDelega());
				}
				jpql.append(" AND t.statoDelega.dsStatoDelega IN :statos ");
				params.put("statos", statos);
			}

			log.debug("STAMPO I PARAMETRI PASSATI PER LA RICERCA ", "-------->" + ricercaDelega.toString());
		}
	}

	@Override
	public List<EntityDelega> findByCfDelegatoAndCFImpresaAndStatoDelega(String cfDelegato, String cfImpresa,
			String statoDelega, boolean ugualeAStatoDelega) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityDelega t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.cfDelegato", "cfDelegato", cfDelegato);
		JpaQueryHelper.andFieldEquals(jpql, params, "t.cfImpresa", "cfImpresa", cfImpresa);
		if (ugualeAStatoDelega) {
			JpaQueryHelper.andFieldEquals(jpql, params, "t.statoDelega.idStatoDelega", "idStatoDelega", statoDelega);
		} else {
			JpaQueryHelper.andFieldNotEquals(jpql, params, "t.statoDelega.idStatoDelega", "idStatoDelega", statoDelega);
		}

		jpql.append(" ORDER BY t.idDelega ");

		TypedQuery<EntityDelega> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<EntityDelega> findByCfDelegatoAndCFImpresa(String cfDelegato, String cfImpresa) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityDelega t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.cfDelegato", "cfDelegato", cfDelegato);
		JpaQueryHelper.andFieldEquals(jpql, params, "t.cfImpresa", "cfImpresa", cfImpresa);

		jpql.append(" ORDER BY t.idDelega ");

		TypedQuery<EntityDelega> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	
	

//	@Override
//	public Optional<EntityDelega> findByCfDelegatoAndCFImpresaAndStatoDelega(String cfDelegato, String cfImpresa,
//			String statoDelega) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		StringBuilder jpql = new StringBuilder();
//		jpql.append(" FROM EntityDelega t ");
//		jpql.append(" WHERE 1=1 ");
//
//		JpaQueryHelper.andFieldEquals(jpql, params, "t.cfDelegato", "cfDelegato", cfDelegato);
//		JpaQueryHelper.andFieldEquals(jpql, params, "t.cfImpresa", "cfImpresa", cfImpresa);
//		JpaQueryHelper.andFieldEquals(jpql, params, "t.statoDelega.idStatoDelega", "idStatoDelega", statoDelega);
//
//		jpql.append(" ORDER BY t.idDelega ");
//
//		TypedQuery<EntityDelega> query = composeTypedQuery(jpql, params);
//		return query.getResultList().stream().findFirst();
//	}

}
