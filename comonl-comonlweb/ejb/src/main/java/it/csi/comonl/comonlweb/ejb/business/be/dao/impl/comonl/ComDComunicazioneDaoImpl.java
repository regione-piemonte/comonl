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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseAuditedEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComDComunicazione;
import it.csi.comonl.comonlweb.ejb.entity.ComTAtecofin;
import it.csi.comonl.comonlweb.ejb.entity.ComTCategLavAssObbl;
import it.csi.comonl.comonlweb.ejb.entity.ComTCategTirocinante;
import it.csi.comonl.comonlweb.ejb.entity.ComTCcnl;
import it.csi.comonl.comonlweb.ejb.entity.ComTCessazionerl;
import it.csi.comonl.comonlweb.ejb.entity.ComTCittadinanza;
import it.csi.comonl.comonlweb.ejb.entity.ComTComune;
import it.csi.comonl.comonlweb.ejb.entity.ComTCpi;
import it.csi.comonl.comonlweb.ejb.entity.ComTEntePrevidenziale;
import it.csi.comonl.comonlweb.ejb.entity.ComTGradoContrattuale;
import it.csi.comonl.comonlweb.ejb.entity.ComTIstat2001livello5;
import it.csi.comonl.comonlweb.ejb.entity.ComTLivelloRetribuzione;
import it.csi.comonl.comonlweb.ejb.entity.ComTLivelloStudio;
import it.csi.comonl.comonlweb.ejb.entity.ComTMotivoPermesso;
import it.csi.comonl.comonlweb.ejb.entity.ComTProvincia;
import it.csi.comonl.comonlweb.ejb.entity.ComTQuestura;
import it.csi.comonl.comonlweb.ejb.entity.ComTRegione;
import it.csi.comonl.comonlweb.ejb.entity.ComTStatiEsteri;
import it.csi.comonl.comonlweb.ejb.entity.ComTStatusStraniero;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoComunicazione;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoComunicazioneTu;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContratti;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoEntePromTirocinio;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoOrario;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoTrasferimento;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipologiaTirocinio;
import it.csi.comonl.comonlweb.ejb.entity.ComTTrasformazionerl;
import it.csi.comonl.comonlweb.ejb.entity.ComTVariazioneSomm;
import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRecuperoComunicazione;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlDateUtils;

/**
 * Data Access Object implementor for the entity ComDComunicazione
 */
@ApplicationScoped
public class ComDComunicazioneDaoImpl extends BaseAuditedEntityDaoImpl<Long, ComDComunicazione> implements ComDComunicazioneDao {

	@Override
	public List<ComDComunicazione> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDComunicazione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComDComunicazione ");

		TypedQuery<ComDComunicazione> query = composeTypedQuery(jpql, params);

		return query.getResultList();
	}

	@Override
	public Long getIdRapportoNextVal () {
		String sb = "SELECT SEQ_ID_RAPPORTO_DI_LAVORO.nextVal from dual";
		Query query = composeNativeQuery(sb, null);
		BigDecimal result=(BigDecimal)query.getSingleResult();
		return result.longValue();
	}

	
	@Override
	public <T extends BaseEntity<Long>> BaseEntity<Long> getTfromMin(Class<T> transcodifica, String fieldValue) {
		
		if (fieldValue == null || fieldValue.trim().equals("")) {
			return null;
		}

		StringBuilder sql = new StringBuilder();

		sql.append("FROM ").append(transcodifica.getName());
		sql.append("  WHERE ");
		if (transcodifica == ComTEntePrevidenziale.class) {
			sql.append(" codEntePrevidenzialeMin = :fieldValue ");
		} else if (transcodifica == ComTCcnl.class) {
			sql.append(" codCcnlMin = :fieldValue ");
		} else if (transcodifica == ComTStatiEsteri.class) {
			sql.append(" codNazioneMin = :fieldValue ");
		} else if (transcodifica == ComTIstat2001livello5.class) {
			sql.append(" codIstat2001livello5Min = :fieldValue ");
		} else if (transcodifica == ComTTipoContratti.class) {
			sql.append(" codTipoContrattoMin = :fieldValue ");
		} else if (transcodifica == ComTTipoOrario.class) {
			sql.append(" codTipoorarioMin = :fieldValue ");
		} else if (transcodifica == ComTAtecofin.class) {
			sql.append(" codAtecofinMin = :fieldValue ");
		} else if (transcodifica == ComTCategLavAssObbl.class) {
			sql.append(" codCategLavAssObblMin = :fieldValue ");
		} else if (transcodifica == ComTCategTirocinante.class) {
			sql.append(" codCategTirocinanteMin = :fieldValue ");
		} else if (transcodifica == ComTCessazionerl.class) {
			sql.append(" codCessazioneMin = :fieldValue ");
		} else if (transcodifica == ComTCittadinanza.class) {
			sql.append(" codCittadinanzaMin = :fieldValue ");
		} else if (transcodifica == ComTComune.class) {
			sql.append(" codComuneMin = :fieldValue ");
		} else if (transcodifica == ComTCpi.class) {
			sql.append(" codCpi = :fieldValue ");
		} else if (transcodifica == ComTGradoContrattuale.class) {
			sql.append(" codGradoContrattuale = :fieldValue ");
		} else if (transcodifica == ComTLivelloRetribuzione.class) {
			sql.append(" codLivello = :fieldValue ");
		} else if (transcodifica == ComTLivelloStudio.class) {
			sql.append(" codiceLivelloMin = :fieldValue ");
		} else if (transcodifica == ComTMotivoPermesso.class) {
			sql.append(" codMotivoMin = :fieldValue ");
		} else if (transcodifica == ComTProvincia.class) {
			sql.append(" codProvinciaMin = :fieldValue ");
		} else if (transcodifica == ComTQuestura.class) {
			sql.append(" codQuesturaMin = :fieldValue ");
		} else if (transcodifica == ComTRegione.class) {
			sql.append(" codRegioneMin = :fieldValue ");
		} else if (transcodifica == ComTStatusStraniero.class) {
			sql.append(" codStatusMin = :fieldValue ");
		} else if (transcodifica == ComTTipoComunicazioneTu.class) {
			sql.append(" codTipoComunicazioneMin = :fieldValue ");
		} else if (transcodifica == ComTTipoEntePromTirocinio.class) {
			sql.append(" codiceEntePromTirocinioMin = :fieldValue ");
		} else if (transcodifica == ComTTipologiaTirocinio.class) {
			sql.append(" codTipologiaTirocinioMin = :fieldValue ");
		} else if (transcodifica == ComTTipoSoggettoAbilitato.class) {
			sql.append(" codSoggettoAbilitatoMin = :fieldValue ");
		} else if (transcodifica == ComTTipoTrasferimento.class) {
			sql.append(" codTipotrasferimentoMin = :fieldValue ");
		} else if (transcodifica == ComTTrasformazionerl.class) {
			sql.append(" codTrasformazionirlMin = :fieldValue ");
		}  else if (transcodifica == ComTVariazioneSomm.class) {
			sql.append(" codTipoVariazioneMin = :fieldValue ");
		}
		

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("fieldValue", fieldValue);

		TypedQuery<T> query = composeTypedQuery(sql, params, transcodifica);
		T ret = null;
		try {
			ret = query.setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			log.error("getTfromMin", e);
		}
		return ret;

	}

	@Override
	public <T extends BaseEntity<Long>> BaseEntity<Long> getTfromMin(Class<T> transcodifica, String fieldValue,
			Date dataRiferimento) {

		if (fieldValue == null || fieldValue.trim().equals("")) {
			return null;
		}

		StringBuilder sql = new StringBuilder();

		sql.append("FROM ").append(transcodifica.getName());
		sql.append("  WHERE ");
		if (transcodifica == ComTEntePrevidenziale.class) {
			sql.append(" codEntePrevidenzialeMin = :fieldValue ");
		} else if (transcodifica == ComTCcnl.class) {
			sql.append(" codCcnlMin = :fieldValue ");
		} else if (transcodifica == ComTStatiEsteri.class) {
			sql.append(" codNazioneMin = :fieldValue ");
		} else if (transcodifica == ComTIstat2001livello5.class) {
			sql.append(" codIstat2001livello5Min = :fieldValue ");
		} else if (transcodifica == ComTTipoContratti.class) {
			sql.append(" codTipoContrattoMin = :fieldValue ");
		} else if (transcodifica == ComTTipoOrario.class) {
			sql.append(" codTipoorarioMin = :fieldValue ");
		} else if (transcodifica == ComTAtecofin.class) {
			sql.append(" codAtecofinMin = :fieldValue ");
		} else if (transcodifica == ComTCategLavAssObbl.class) {
			sql.append(" codCategLavAssObblMin = :fieldValue ");
		} else if (transcodifica == ComTCategTirocinante.class) {
			sql.append(" codCategTirocinanteMin = :fieldValue ");
		} else if (transcodifica == ComTCessazionerl.class) {
			sql.append(" codCessazioneMin = :fieldValue ");
		} else if (transcodifica == ComTCittadinanza.class) {
			sql.append(" codCittadinanzaMin = :fieldValue ");
		} else if (transcodifica == ComTComune.class) {
			sql.append(" codComuneMin = :fieldValue ");
		} else if (transcodifica == ComTCpi.class) {
			sql.append(" codCpi = :fieldValue ");
		} else if (transcodifica == ComTGradoContrattuale.class) {
			sql.append(" codGradoContrattuale = :fieldValue ");
		} else if (transcodifica == ComTLivelloRetribuzione.class) {
			sql.append(" codLivello = :fieldValue ");
		} else if (transcodifica == ComTLivelloStudio.class) {
			sql.append(" codiceLivelloMin = :fieldValue ");
		} else if (transcodifica == ComTMotivoPermesso.class) {
			sql.append(" codMotivoMin = :fieldValue ");
		} else if (transcodifica == ComTProvincia.class) {
			sql.append(" codProvinciaMin = :fieldValue ");
		} else if (transcodifica == ComTQuestura.class) {
			sql.append(" codQuesturaMin = :fieldValue ");
		} else if (transcodifica == ComTRegione.class) {
			sql.append(" codRegioneMin = :fieldValue ");
		} else if (transcodifica == ComTStatusStraniero.class) {
			sql.append(" codStatusMin = :fieldValue ");
		} else if (transcodifica == ComTTipoComunicazioneTu.class) {
			sql.append(" codTipoComunicazioneMin = :fieldValue ");
		} else if (transcodifica == ComTTipoEntePromTirocinio.class) {
			sql.append(" codiceEntePromTirocinioMin = :fieldValue ");
		} else if (transcodifica == ComTTipologiaTirocinio.class) {
			sql.append(" codTipologiaTirocinioMin = :fieldValue ");
		} else if (transcodifica == ComTTipoSoggettoAbilitato.class) {
			sql.append(" codSoggettoAbilitatoMin = :fieldValue ");
		} else if (transcodifica == ComTTipoTrasferimento.class) {
			sql.append(" codTipotrasferimentoMin = :fieldValue ");
		} else if (transcodifica == ComTTrasformazionerl.class) {
			sql.append(" codTrasformazionirlMin = :fieldValue ");
		}  else if (transcodifica == ComTVariazioneSomm.class) {
			sql.append(" codTipoVariazioneMin = :fieldValue ");
		}

		if (dataRiferimento == null) {
			dataRiferimento = new Date();
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fieldValue", fieldValue);
		
		if(!dataRiferimento.equals(ComonlDateUtils.get1900())) { // marcatore per evitare il vincolo della data
			sql.append(" AND (dtInizio IS NULL OR (dtInizio IS NOT NULL and dtInizio <= :dataRif ) ) ");
			sql.append(" AND (dtFine   IS NULL OR (dtFine   IS NOT NULL and dtFine   >= :dataRif ) ) ");
			params.put("dataRif", dataRiferimento);
		}

		TypedQuery<T> query = composeTypedQuery(sql, params, transcodifica);

		T ret = null;
		try {
			
			ret = query.getResultList().stream().findFirst().get();
			
		}catch(NoSuchElementException e) {
			log.error("getTfromMin", e);
		}
		catch (NoResultException e) {
			log.error("getTfromMin", e);
		}
		return ret;
	}


	@Override
	public Long countRecuperoComunicazione(FormRecuperoComunicazione recuperoComunicazione) {
		log.debug("countRecupero----------->", "Entro nel metodo countRecuperoComunicazione");
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		if (recuperoComunicazione.getElencoIds() != null || (recuperoComunicazione.getIdA() != null && recuperoComunicazione.getIdDa() != null)) {
			composeQueryRecupero(recuperoComunicazione, params, jpql);
			Query qn = composeQuery(getCountQuery(jpql), params);
			long count = ((Number) qn.getSingleResult()).longValue();
			return count;
		}
		return 0L;

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getRecuperoComunicazione(FormRecuperoComunicazione recuperoComunicazione) {
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		composeQueryRecupero(recuperoComunicazione, params, jpql);
		Query query = entityManager.createQuery("SELECT t.idComDComunicazione " + jpql.toString());
		return query.getResultList();
	}


	private void composeQueryRecupero(FormRecuperoComunicazione recuperoComunicazione, Map<String, Object> params,
			StringBuilder jpql) {

		jpql.append(" FROM ");
		jpql.append(" ComDComunicazione t ");
		jpql.append(" WHERE 1=1 ");

		if (StringUtils.isNotBlank(recuperoComunicazione.getElencoIds())) {
			jpql.append(" AND t.idComDComunicazione IN (" + recuperoComunicazione.getElencoIds() + ") ");
		}
		else if (recuperoComunicazione.getIdA() != null && recuperoComunicazione.getIdA().intValue()>0 &&
				recuperoComunicazione.getIdDa() != null && recuperoComunicazione.getIdDa().intValue()>0) {
			jpql.append(" AND t.idComDComunicazione >= " + recuperoComunicazione.getIdA() + " AND t.idComDComunicazione <= " + recuperoComunicazione.getIdDa() + " ");
		}

		jpql.append(" AND t.codiceComunicazioneReg IS NULL AND t.comTStatoComunicazione.idComTStatoComunicazione IN (3,7) ");


		log.debug("STAMPO SQL ", "-------->" + jpql.toString());
		log.debug("STAMPO I PARAMETRI PASSATI PER IL RECUPERO ", "-------->" + recuperoComunicazione.toString());
	}

	@Override
	public List<Long> getIdComunicazioneByCodReg(String codiceComunicazioneReg) {

		Map<String, Object> params = new HashMap<>();

		StringBuilder jpql = new StringBuilder();
		jpql.append(" select t.idComDComunicazione");
		jpql.append(" FROM ComDComunicazione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.codiceComunicazioneReg", "codiceComunicazioneReg", codiceComunicazioneReg);

		Query query = composeQuery(jpql, params);

		return query.getResultList();
	}

	@Override
	public List<Long> getIdComunicazioneByCodRegPrec(String codiceComunicazioneReg) {

		Map<String, Object> params = new HashMap<>();

		StringBuilder jpql = new StringBuilder();
		jpql.append(" select t.idComDComunicazione");
		jpql.append(" FROM ComDComunicazione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.codiceComunRegPrec", "codiceComunicazioneReg", codiceComunicazioneReg);

		Query query = composeQuery(jpql, params);

		return query.getResultList();
	}


	private static final SimpleDateFormat dateFormatDatabase = new SimpleDateFormat("dd/MM/yyyy");
	public List<ComDComunicazione> getByIdentificativoRapporto(String codiceFiscaleDatore, String codiceFiscaleLavoratore, Date dtInizioRapporto){
		Map<String, Object> params = new HashMap<>();

		String dataInizioRapporto = dateFormatDatabase.format(dtInizioRapporto);
		
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT cdc.ID_COM_D_COMUNICAZIONE , cdc.ID_RAPPORTO_DI_LAVORO , cdc.DT_RIFERIMENTO, cdc.ID_COM_T_TIPO_COMUNICAZIONE , cdc.FLG_CURRENT_RECORD ");
		jpql.append(" FROM COM_D_COMUNICAZIONE cdc ");
		jpql.append(" , COM_D_RAPPORTO cdr "); 
		jpql.append(" , COM_R_RAPPORTO_LAVORATORE crrl "); 
		jpql.append(" , COM_D_LAVORATORE cdl "); 
		jpql.append(" , COM_D_DATORE cdd ");
		jpql.append(" , COM_R_COMUNICAZIONE_DATORE crcd "); 
		jpql.append(" WHERE  "); 
		jpql.append(" cdl.CODICE_FISCALE = :codiceFiscaleLavoratore "); 
		jpql.append(" AND cdd.CODICE_FISCALE = :codiceFiscaleDatore "); 
		jpql.append(" AND trunc(cdr.DT_INIZIO_RAPPORTO) = to_date('"+dataInizioRapporto+"' , 'dd/MM/yyyy') ");
		jpql.append(" AND cdd.ID_COM_D_DATORE = CRCD .ID_COM_D_DATORE "); 
		jpql.append(" AND CRCD.ID_COM_D_COMUNICAZIONE = cdc.ID_COM_D_COMUNICAZIONE  "); 
		jpql.append(" AND cdr.ID_COM_D_COMUNICAZIONE = cdc.ID_COM_D_COMUNICAZIONE  "); 
		jpql.append(" AND crrl.ID_COM_D_LAVORATORE = cdl.ID_COM_D_LAVORATORE  "); 
		jpql.append(" AND crrl.id_com_d_rapporto = cdr.ID_COM_D_RAPPORTO  "); 
		jpql.append(" AND crrl.ID_COM_T_TIPO_RAPPORTO_AZIENDA = 'P'"); 		

		jpql.append(" AND (cdc.FLG_MULTI_LAV IS NULL OR   cdc.FLG_MULTI_LAV = :flgMultiLav)"); 		
		jpql.append(" AND cdc.ID_COM_T_STATO_COMUNICAZIONE IN (:validata,:transito)"); // VALIDATA, TRANSITO
		jpql.append(" AND cdc.ID_COM_T_TIPO_COMUNICAZIONE_TU != :annullamento"); //ANNULLAMENTO
		
		params.put("codiceFiscaleLavoratore",codiceFiscaleLavoratore);
		params.put("codiceFiscaleDatore",codiceFiscaleDatore);
		params.put("flgMultiLav",ComonlConstants.FLAG_N);
		params.put("validata",ComonlConstants.STATO_COMUNICAZIONE_VALIDATA_ID);
		params.put("transito",ComonlConstants.STATO_COMUNICAZIONE_TRANSITO_ID);
		params.put("annullamento",ComonlConstants.TIPO_COMUNICAZIONE_TU_ANNULLAMENTO_ID);

		Query query = composeNativeQuery(jpql, params);
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.getResultList();

		List<ComDComunicazione> list = new ArrayList<ComDComunicazione>();
		if (result != null) {
			ComDComunicazione obj = null;
			for (Object[] rec : result) {

				obj = new ComDComunicazione();
				obj.setId(((BigDecimal)rec[0]).longValue());
				obj.setIdRapportoLavoro(((BigDecimal)rec[1]).longValue());
				obj.setDataRiferimento((Date) rec[2]);
				ComTTipoComunicazione comTTipoComunicazione = new ComTTipoComunicazione();
				comTTipoComunicazione.setId((String) rec[3]);
				obj.setComTTipoComunicazione(comTTipoComunicazione);
				obj.setFlgCurrentRecord((String) rec[4]);
				list.add(obj);
			}
		}
		return list;
	}
	public Optional<ComDComunicazione> getByIdRapportoLavoro(Long idRapportoLavoro) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDComunicazione t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.idRapportoLavoro", "idRapportoLavoro", idRapportoLavoro);
		JpaQueryHelper.andFieldEquals(jpql, params, "t.flgCurrentRecord", "flgCurrentRecord", ComonlConstants.FLAG_S);

		TypedQuery<ComDComunicazione> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}
}
