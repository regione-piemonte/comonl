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

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityAnagraficaDelegatoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseAuditedEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.EntityAnagraficaDelegato;
import it.csi.comonl.comonlweb.ejb.entity.EntityAnagraficaDelegatoPK;
import it.csi.comonl.comonlweb.ejb.exception.BusinessException;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.ejb.util.jpa.Page;
import it.csi.comonl.comonlweb.ejb.util.jpa.PageImpl;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAzienda;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaAccreditamentoAnagrafiche;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

/**
 * Data Access Object implementor for the entity EntityAnagraficaDelegato
 */
@ApplicationScoped
public class EntityAnagraficaDelegatoDaoImpl
		extends BaseAuditedEntityDaoImpl<EntityAnagraficaDelegatoPK, EntityAnagraficaDelegato>
		implements EntityAnagraficaDelegatoDao {

	@Override
	public List<EntityAnagraficaDelegato> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityAnagraficaDelegato t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<EntityAnagraficaDelegato> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public Long countRicercaAccreditamentoConsulente(
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		log.debug("countRicerca----------->", "Entro nel metodo countRicerca");
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		// jpql.append(" DISTINCT "); TODO verificare se è necessario
		composeQueryRicercaConsulente(ricercaAccreditamentoAnagrafiche, params, jpql);
		Query qn = composeQuery(getCountQuery(jpql), params);
		long count = ((Number) qn.getSingleResult()).longValue();
		return count;
	}

	@Override
	public Page<EntityAnagraficaDelegato> findPaginatedConsulente(int page, int size, String sortField,
			String sortDirection, FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		StringBuilder jpql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		composeQueryRicercaConsulente(ricercaAccreditamentoAnagrafiche, params, jpql);

		jpql.append(" ORDER BY ");
		jpql.append(" t.id.cfDelegato ");
		// is not sufficient to give a deterministic answer, you do need to add more
		// columns. The most obvious choice would be a primary key column

		return getPagedResult(jpql, params, page, size);
	}

	private void composeQueryRicercaConsulente(FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche,
			Map<String, Object> params, StringBuilder jpql) {

		jpql.append(" FROM ");
		jpql.append(" EntityAnagraficaDelegato t ");
		jpql.append(" WHERE 1=1 ");

		if (ricercaAccreditamentoAnagrafiche != null) {

			if (!StringUtils.isEmpty(ricercaAccreditamentoAnagrafiche.getCf())) {
				jpql.append(" AND UPPER(t.id.cfDelegato) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :cfDelegato),'%'))");
				params.put("cfDelegato", ricercaAccreditamentoAnagrafiche.getCf().trim());
			}

			if (ricercaAccreditamentoAnagrafiche.getCognome() != null) {
				jpql.append(" AND UPPER(t.cognome) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :cognome),'%'))");
				params.put("cognome", ricercaAccreditamentoAnagrafiche.getCognome().trim());
			}

			if (ricercaAccreditamentoAnagrafiche.getNome() != null) {
				jpql.append(" AND UPPER(t.nome) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :nome),'%'))");
				params.put("nome", ricercaAccreditamentoAnagrafiche.getNome().trim());
			}

			if (ricercaAccreditamentoAnagrafiche.getAnnullate() != null
					&& ricercaAccreditamentoAnagrafiche.getAnnullate()) {
				jpql.append(" AND t.dataAnnullamento IS NOT NULL ");
			} else {
				jpql.append(" AND t.dataAnnullamento IS NULL ");
			}
		}

		jpql.append(" AND (t.id.tipoAnagrafica =:c OR t.id.tipoAnagrafica =:d OR t.id.tipoAnagrafica =:e) ");
		params.put("c", ComonlConstants.TIPOLOGIA_ANAGRAFICA_CONSULENTE_C);
		params.put("d", ComonlConstants.TIPOLOGIA_ANAGRAFICA_DELEGATO_D);
		params.put("e", ComonlConstants.TIPOLOGIA_ANAGRAFICA_PERSONA_AUTORIZZATA_E);

	}

	@Override
	public Page<AnagraficaAzienda> findPaginatedAzienda(long limMaxRicerca, int page, int size, String sortField, String sortDirection,
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {

		Map<String, Object> params = new HashMap<>();

		long count = countRicercaAccreditamentoAzienda(ricercaAccreditamentoAnagrafiche);
		
		if (count > limMaxRicerca)
			throw new BusinessException(MsgComonl.COMCOME0023.getError());
				
		
		StringBuilder jpql = new StringBuilder();
		composeQueryRicercaAzienda(ricercaAccreditamentoAnagrafiche, params, jpql);

		int start = page * size;
		int end = size * (page + 1);

		String queryLimit = String.format(
				"SELECT * FROM (SELECT pagination.*, rownum rownumPagination FROM ((%s)) pagination WHERE rownum <= (%d)) WHERE rownumPagination > (%d)",
				jpql.toString(), end, start);
		Query query = composeNativeQuery(queryLimit, params);
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.getResultList();

		List<AnagraficaAzienda> list = new ArrayList<AnagraficaAzienda>();
		if (result != null) {
			AnagraficaAzienda obj = null;
			for (Object[] rec : result) {

				obj = new AnagraficaAzienda();
				obj.setTipologia((String) rec[0]);
				obj.setCodiceFiscale((String) rec[1]);
				obj.setPartitaIva((String) rec[2]);
				obj.setDenominazione((String) rec[3]);
				obj.setTipoStudio((String) rec[4]);
				obj.setComune((String) rec[5]);
				obj.setProvincia((String) rec[6]);
				obj.setIdChiave((String) rec[7]);
				obj.setDataAnnullamento((Date) rec[8]);

				list.add(obj);
			}
		}

		return new PageImpl<AnagraficaAzienda>(count, list);
	}

	@Override
	public Long countRicercaAccreditamentoAzienda(
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		log.debug("countRicerca----------->", "Entro nel metodo countRicerca");
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		composeQueryRicercaAzienda(ricercaAccreditamentoAnagrafiche, params, jpql);
		
		String sqlCount = "SELECT count(*) FROM (" +  jpql.toString() + ")";
		
		System.err.println(sqlCount);
		
		Query query = composeNativeQuery(sqlCount, params);
		long count = ((Number) query.getSingleResult()).longValue();
		return count;
	}
	
	

	private void composeQueryRicercaAzienda(FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche,
			Map<String, Object> params, StringBuilder jpql) {
		jpql.append(" SELECT * FROM ( ");
		jpql.append(" SELECT DISTINCT 'AZIENDA',di.CF_IMPRESA AS CODICE_FISCALE,'' AS PARTITA_IVA,di.DENOMINAZIONE AS DENOMINAZIONE,'' AS TIPO_STUDIO ,'' AS COMUNE,'' AS PROVINCIA,TRIM(di.CF_IMPRESA) AS ID, di.DT_ANNULLAMENTO ");
		jpql.append(" FROM DELEGATO_IMPRESA di WHERE 1 = 1 ");

		jpql.append(" UNION ALL ");

		jpql.append(
				" SELECT 'STUDIO_PROFESSIONALE', studi_prof.CF_SOGGETTO as CODICE_FISCALE, studi_prof.PARTITA_IVA as PARTITA_IVA, studi_prof.COGNOME_DENOMINAZIONE as DENOMINAZIONE, tp_studi_prof.DS_COM_TIPO_SOGGETTO_ABILITATO as TIPO_STUDIO, comune.DS_COM_T_COMUNE as COMUNE, prov.DS_TARGA as PROVINCIA, ''||studi_prof.ID_COM_D_SOGGETTO_ABILITATO AS ID, studi_prof.DT_ANNULLAMENTO ");
		jpql.append(" FROM COM_D_SOGGETTO_ABILITATO studi_prof, ");
		jpql.append(" COM_T_TIPO_SOGGETTO_ABILITATO tp_studi_prof, ");
		jpql.append(" COM_T_COMUNE comune, ");
		jpql.append(" COM_T_PROVINCIA prov ");
		jpql.append(" WHERE studi_prof.ID_COM_TIPO_SOGGETTO_ABILITATO = tp_studi_prof.ID_COM_TIPO_SOGGETTO_ABILITATO ");
		jpql.append(" AND studi_prof.ID_COM_T_COMUNE = comune.ID_COM_T_COMUNE(+) ");
		jpql.append(" AND comune.ID_COM_T_PROVINCIA = prov.ID_COM_T_PROVINCIA(+) ");
		jpql.append(" ORDER BY 1 ");
		jpql.append(" ) WHERE 1 = 1 ");
		if (ricercaAccreditamentoAnagrafiche != null) {
			if (!StringUtils.isEmpty(ricercaAccreditamentoAnagrafiche.getCfAzienda())) {
				jpql.append(" AND UPPER(CODICE_FISCALE) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :cfDelegato),'%')) ");
				params.put("cfDelegato", ricercaAccreditamentoAnagrafiche.getCfAzienda().trim());
			}

			if (!StringUtils.isEmpty(ricercaAccreditamentoAnagrafiche.getpIva())) {
				jpql.append(" AND UPPER(PARTITA_IVA) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :pIva),'%')) ");
				params.put("pIva", ricercaAccreditamentoAnagrafiche.getpIva().trim());
			}

			if (!StringUtils.isEmpty(ricercaAccreditamentoAnagrafiche.getDenom())) {
				jpql.append(" AND UPPER(DENOMINAZIONE) ");
				jpql.append(" LIKE UPPER(CONCAT(CONCAT('%', :denom),'%')) ");
				params.put("denom", ricercaAccreditamentoAnagrafiche.getDenom().trim());
			}
			if (ricercaAccreditamentoAnagrafiche.getAnnullate() != null
					&& ricercaAccreditamentoAnagrafiche.getAnnullate()) {
				jpql.append(" AND DT_ANNULLAMENTO IS NOT NULL ");
			} else {
				jpql.append(" AND DT_ANNULLAMENTO IS NULL ");
			}
		}

	}

	@Override
	public List<EntityAnagraficaDelegato> findByCfDelegato(String cfDelegato) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityAnagraficaDelegato t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id.cfDelegato", "cfDelegato", cfDelegato);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<EntityAnagraficaDelegato> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<EntityAnagraficaDelegato> findByIdSoggettoAbilitato(Long idSoggettoAbilitato) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityAnagraficaDelegato t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDSoggettoAbilitato.id", "comDSoggettoAbilitato",
				idSoggettoAbilitato);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<EntityAnagraficaDelegato> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<EntityAnagraficaDelegato> findNotCanceledByIdSoggettoAbilitato(Long idSoggettoAbilitato) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityAnagraficaDelegato t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDSoggettoAbilitato.id", "comDSoggettoAbilitato",
				idSoggettoAbilitato);

		JpaQueryHelper.andFieldNotNull(jpql, "t.dataAnnullamento");

		jpql.append(" ORDER BY t.id ");

		TypedQuery<EntityAnagraficaDelegato> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<EntityAnagraficaDelegato> findSoggettoByCfSoggettoAndTipoAnagraficaDelegato(String cfSoggetto,
			String tipoAnagrafica) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityAnagraficaDelegato t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id.cfDelegato", "cfDelegato", cfSoggetto);

		JpaQueryHelper.andFieldEquals(jpql, params, "t.id.tipoAnagrafica", "tipoAnagrafica", tipoAnagrafica);

		TypedQuery<EntityAnagraficaDelegato> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ruolo> findRuoliPerServizioEsposto(String codiceFiscale) {

		Map<String, Object> params = new HashMap<>();

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT " + "DISTINCT(AN.CF_DELEGATO), " + " AN.TIPO_ANAGRAFICA, "
				+ "AN.ID_COM_D_SOGGETTO_ABILITATO, " + "AN.COGNOME, " + "AN.NOME, " + "H.C_ENTE, " + "COM.CF_SOGGETTO, "
				+ "COM.COGNOME_DENOMINAZIONE, " + "COM.PARTITA_IVA, " + "COM.FLG_ACCENTRAMENTO, "
				+ "COMT.COD_SOGGETTO_ABILITATO_MIN, "
				+ "DECODE(ACC.ID_COM_D_ANAGRAFICA_AZI_ACCENT, '', 'N', 'S') AS FLG_IMPRESA_ACCENTRATA, "
				+ "IM.DENOMINAZIONE AS DENOMINAZ_IMPRESA_NO_AAEP, " + "IM.FLG_SCUOLA, " + "IM.CF_IMPRESA " + "FROM "
				+ "ANAGRAFICA_DELEGATO AN, " + "DELEGATO_IMPRESA IM, " + "DELEGA G, " + "ENTE_DELEGATO H, "
				+ "COM_D_SOGGETTO_ABILITATO COM, " + "COM_T_TIPO_SOGGETTO_ABILITATO COMT, "
				+ "COM_D_ANAGRAFICA_AZI_ACCENT ACC " + "WHERE " + "AN.CF_DELEGATO = IM.CF_DELEGATO (+) "
				+ "AND AN.TIPO_ANAGRAFICA = IM.TIPO_ANAGRAFICA (+) " + "AND H.CF_DELEGATO (+) = AN.CF_DELEGATO "
				+ "AND H.TIPO_ANAGRAFICA (+) = AN.TIPO_ANAGRAFICA " + "AND (IM.DT_FINE_CARICA IS NULL "
				+ "OR IM.DT_FINE_CARICA >= TRUNC(SYSDATE)) " + "AND IM.CF_DELEGATO = G.CF_DELEGATO (+) "
				+ "AND IM.CF_IMPRESA = G.CF_IMPRESA (+) " + "AND (AN.TIPO_ANAGRAFICA <> 'D' "
				+ "OR (AN.TIPO_ANAGRAFICA = 'D' " + "AND G.C_STATO = '3')) "
				+ "AND COM.ID_COM_TIPO_SOGGETTO_ABILITATO = COMT.ID_COM_TIPO_SOGGETTO_ABILITATO(+) "
				+ "AND COM.ID_COM_D_SOGGETTO_ABILITATO(+) = AN.ID_COM_D_SOGGETTO_ABILITATO "
				+ "AND AN.DT_ANNULLAMENTO IS NULL  " + "AND IM.DT_ANNULLAMENTO IS NULL " + "AND ACC.DT_FINE IS NULL "
				+ "AND COMT.DT_FINE IS NULL " + "AND IM.CF_IMPRESA = ACC.CODICE_FISCALE (+) ");
		if (codiceFiscale != null) {
			jpql.append(" AND UPPER(AN.CF_DELEGATO) ");
			jpql.append(" LIKE UPPER(:codFiscale) ");
			params.put("codFiscale", codiceFiscale.trim());
		}
		Query query = composeNativeQuery(jpql.toString(), params);
		List<Ruolo> listaRuoli = new ArrayList<>();

		List<Object[]> result = query.getResultList();

		if (result != null) {
			for (Object[] ilResult : result) {
				listaRuoli.add(mappingRuolo(ilResult));
			}
		}

		return listaRuoli;
	}

	private Ruolo mappingRuolo(Object[] ilResult) {

		Ruolo unRuolo = new Ruolo();
		unRuolo.setCodiceFiscaleUtente((String) ilResult[0]);
		unRuolo.setTipoAnagrafica(((Character) ilResult[1]).toString());
		
		if (ilResult[2] != null) {
			if (ilResult[2] instanceof BigDecimal) {
				BigDecimal ids = (BigDecimal)ilResult[2];
				unRuolo.setIdSoggetti(ids.longValue());
			}
			else unRuolo.setIdSoggetti((Long) ilResult[2]);
		}
		
		
		unRuolo.setDsCognome((String) ilResult[3]);
		unRuolo.setDsNome((String) ilResult[4]);
		unRuolo.setCodiceFiscaleStudioProfessionale((String) ilResult[6]);
		unRuolo.setDescrizioneStudioProfessionale((String) ilResult[7]);

		unRuolo.setFlgConsulenteAccentrato(
				ComonlUtility.isNotVoid(ilResult[9]) && ComonlConstants.FLAG_S.equals(ilResult[9]));
		unRuolo.setCodMinSoggettoAbilitato((String) ilResult[10]);

		unRuolo.setDenominazioneAzienda((String) ilResult[12]);
		unRuolo.setFlgScuolaPubblica(
				ComonlUtility.isNotVoid(ilResult[13]) && ComonlConstants.FLAG_S.equals(ilResult[13]));
		unRuolo.setCodiceFiscaleAzienda((String) ilResult[14]);

		if (ComonlUtility.isNotVoid(unRuolo.getTipoAnagrafica())) {
			switch (unRuolo.getTipoAnagrafica()) {
			case ComonlConstants.TIPOLOGIA_ANAGRAFICA_DELEGATO_D:
				unRuolo.setDelegaValida(ComonlUtility.isNotVoid(unRuolo.getCodiceFiscaleAzienda()));
				unRuolo.setDelegatoRespo(true);
				break;
			case ComonlConstants.TIPOLOGIA_ANAGRAFICA_CONSULENTE_C:
				unRuolo.setConsulenteRespo(true);
				break;
			case ComonlConstants.TIPOLOGIA_ANAGRAFICA_PERSONA_AUTORIZZATA_E:
				unRuolo.setPersonaAutorizzata(true);
				break;
			default:
				break;
			}
		}

		return unRuolo;
	}

}
