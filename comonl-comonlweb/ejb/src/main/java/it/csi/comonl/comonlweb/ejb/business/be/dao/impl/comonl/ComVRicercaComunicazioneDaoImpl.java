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
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;

import org.apache.commons.lang.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComVRicercaComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComVRicercaComunicazione;
import it.csi.comonl.comonlweb.ejb.exception.RicercaLimMaxException;
import it.csi.comonl.comonlweb.ejb.exception.TimeoutRicercaException;
import it.csi.comonl.comonlweb.ejb.util.jpa.Page;
import it.csi.comonl.comonlweb.ejb.util.jpa.PageImpl;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;

/**
 * Data Access Object implementor for the entity ComDComunicazione
 */
@ApplicationScoped
public class ComVRicercaComunicazioneDaoImpl extends BaseEntityDaoImpl<Long, ComVRicercaComunicazione>
		implements ComVRicercaComunicazioneDao {
	

	@Override
	public Long countRicercaComunicazione(FormRicercaComunicazione ricercaComunicazione) {
		log.debug("countRicerca----------->", "Entro nel metodo countRicerca");
		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		composeQueryRicercaNative(ricercaComunicazione, params, jpql);
		Query qn = entityManager.createNativeQuery(getCountQuery(jpql.toString()));
		qn = replaceParams(qn, params);
		
		
		//Query qn = composeTypedNativeQuery(getCountQuery(jpql.toString()),params);
		long count = ((Number) qn.getSingleResult()).longValue();
		return count;

	}
	
	@Override
	public Page<ComVRicercaComunicazione> findPaginated(int page, int size, String sortField, String sortDirection, FormRicercaComunicazione ricercaComunicazione, int limMaxRisultati) {

		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		composeQueryRicercaNative(ricercaComunicazione, params, jpql);
		return getNativePagedResult(jpql, params, page, size, limMaxRisultati);
	}
	
	private void composeQueryRicercaNative(FormRicercaComunicazione ricercaComunicazione, Map<String, Object> params, StringBuilder sql) {
		
		
		sql.append(" SELECT ID_COM_D_COMUNICAZIONE, ANNO_PROT_COM, CODICE_COMUNICAZIONE_REG, ");
		sql.append(" CODICE_FISCALE_DATORE, CODICE_FISCALE_DATORE_PREC, CODICE_FISCALE_LAVORATORE, CODICE_FISCALE_SOGGETTO, COGNOME_LAVORATORE, ");
		sql.append(" DS_COM_T_STATO_COMUNICAZIONE, DS_COM_T_TIPO_COMUNICAZIONE, DS_DENOMINAZIONE_DATORE, DS_DENOMINAZIONE_DATORE_PREC, ");
		sql.append(" DT_EVENTO,DT_INIZIO_RAPPORTO,DT_INSERT,DT_INVIO, ");
		sql.append(" FLG_CURRENT_RECORD, ID_COM_D_DATORE, ID_COM_D_DATORE_PREC, ID_COM_T_PROVINCIA, ID_COM_T_STATO_COMUNICAZIONE, ID_COM_T_TIPO_COMUNICAZIONE, ID_COM_T_TIPO_COMUNICAZIONE_TU, ");
		sql.append(" ID_COM_T_TIPO_SOMMINISTRAZIONE, ID_COM_T_TIPO_TRASFERIMENTO, ID_COM_T_VARIAZIONE_SOMM, ID_TIPO_TRACCIATO, ");
		sql.append(" NOME_LAVORATORE, NUM_PROT_COM ");
		sql.append(" FROM COM_V_RICERCA_COMUNICAZIONE WHERE 1 = 1 ");
		
		if (StringUtils.isNotBlank(ricercaComunicazione.getCodiceFiscaleSoggetto())) {
			sql.append("AND CODICE_FISCALE_SOGGETTO = :codiceFiscaleSoggetto");
			params.put("codiceFiscaleSoggetto", ricercaComunicazione.getCodiceFiscaleSoggetto());
		}
		
		if (ricercaComunicazione.getDataInvioInserimentoDa() != null && ricercaComunicazione.getDataInvioInserimentoA() != null) {
			String dataDa = getDateDB(ricercaComunicazione.getDataInvioInserimentoDa(), true);
			String dataA = getDateDB(ricercaComunicazione.getDataInvioInserimentoA(), false);
			sql.append(" AND ((DT_INSERT BETWEEN to_date(" + dataDa  + " , 'dd/MM/yyyy HH24:MI:SS')  AND to_date(" + dataA  + ", 'dd/MM/yyyy HH24:MI:SS') ");
			sql.append(" AND DT_INVIO IS  NULL ) ");
			sql.append(" OR (DT_INVIO BETWEEN to_date(" + dataDa  + ", 'dd/MM/yyyy HH24:MI:SS') AND to_date(" + dataA  + ", 'dd/MM/yyyy HH24:MI:SS') ");
			sql.append(" AND DT_INVIO IS not NULL)) ");
					
		}
		else if (ricercaComunicazione.getDataInvioInserimentoDa() != null && ricercaComunicazione.getDataInvioInserimentoA() == null) {
			String dataDa = getDateDB(ricercaComunicazione.getDataInvioInserimentoDa(), true);			
			sql.append(" AND ((DT_INSERT >= to_date(" + dataDa  + " , 'dd/MM/yyyy HH24:MI:SS') AND DT_INVIO IS NULL) ");
			sql.append(" OR (DT_INVIO >= to_date(" + dataDa  + ", 'dd/MM/yyyy HH24:MI:SS') AND DT_INVIO IS NOT NULL)) ");
		}
		
		
		if (StringUtils.isNotBlank(ricercaComunicazione.getCodiceFiscaleAzienda())) {
			sql.append(" AND CODICE_FISCALE_DATORE = :cfAzienda ");
			params.put("cfAzienda", ricercaComunicazione.getCodiceFiscaleAzienda());
		}
		
		if (StringUtils.isNotBlank(ricercaComunicazione.getDenominazioneAzienda())) {
			if (!StringUtils.isNotBlank(ricercaComunicazione.getCodiceFiscaleAzienda())) {
				sql.append(" AND DS_DENOMINAZIONE_DATORE = :denominazioneAzienda ");
				params.put("denominazioneAzienda", ricercaComunicazione.getDenominazioneAzienda());
			}
		}
		
		if (ricercaComunicazione.getStatoComunicaziones() != null && !ricercaComunicazione.getStatoComunicaziones().isEmpty()) {
			List<Long> idStatos = ricercaComunicazione.getStatoComunicaziones().stream().map(StatoComunicazione::getId).collect(Collectors.toList());
			sql.append(" AND ID_COM_T_STATO_COMUNICAZIONE IN :idStatos ");
			params.put("idStatos", idStatos);
		}
		
		if (StringUtils.isNotBlank(ricercaComunicazione.getCodiceRegionale())) {
    		sql.append(" AND CODICE_COMUNICAZIONE_REG = :codiceRegionale ");
			params.put("codiceRegionale", ricercaComunicazione.getCodiceRegionale());
    	}

		if (ricercaComunicazione.getTipoComunicazioneTu()!=null) {
			sql.append(" AND ID_COM_T_TIPO_COMUNICAZIONE_TU = :tipoComunicazioneTu ");
			params.put("tipoComunicazioneTu", ricercaComunicazione.getTipoComunicazioneTu().getId());
		}

		if (ricercaComunicazione.getProvinciaProtocollo()!=null) {
			sql.append(" AND ID_COM_T_PROVINCIA = :provincia ");
			params.put("provincia", ricercaComunicazione.getProvinciaProtocollo().getId());
		}

		if (ricercaComunicazione.getAnnoProtocollo()!=null) {
			sql.append(" AND ANNO_PROT_COM = :annoProtocollo ");
			params.put("annoProtocollo", ricercaComunicazione.getAnnoProtocollo());
		}

		if (ricercaComunicazione.getNumeroProtocollo()!=null) {
			sql.append(" AND NUM_PROT_COM = :numeroProtocollo ");
			params.put("numeroProtocollo", ricercaComunicazione.getNumeroProtocollo());
		}
		
		if (StringUtils.isNotBlank(ricercaComunicazione.getTipoTracciato())) {
			sql.append(" AND ID_TIPO_TRACCIATO = :tipoTracciato ");
			params.put("tipoTracciato", ricercaComunicazione.getTipoTracciato());
		}

		if (ricercaComunicazione.getTipoComunicazione()!=null) {
			sql.append(" AND ID_COM_T_TIPO_COMUNICAZIONE = :tipoComunicazione ");
			params.put("tipoComunicazione", ricercaComunicazione.getTipoComunicazione().getId());
		}

		
		if (StringUtils.isNotBlank(ricercaComunicazione.getCodiceFiscaleLavoratore())){
			sql.append(" AND CODICE_FISCALE_LAVORATORE = :cfLavoratore ");
			params.put("cfLavoratore", ricercaComunicazione.getCodiceFiscaleLavoratore());
		}

		if (StringUtils.isNotBlank(ricercaComunicazione.getCognomeLavoratore())){
			sql.append(" AND COGNOME_LAVORATORE = :cognomeLavoratore ");
			params.put("cognomeLavoratore", ricercaComunicazione.getCognomeLavoratore());
		}

		if (StringUtils.isNotBlank(ricercaComunicazione.getNomeLavoratore())){
			sql.append(" AND NOME_LAVORATORE = :nomeLavoratore ");
			params.put("nomeLavoratore", ricercaComunicazione.getNomeLavoratore());
		}

		sql.append(" ORDER BY FLG_CURRENT_RECORD ASC ");

		log.debug("STAMPO SQL ", "-------->" + sql.toString());
		log.debug("STAMPO I PARAMETRI PASSATI PER LA RICERCA ", "-------->" + ricercaComunicazione.toString());
	}


	protected Page<ComVRicercaComunicazione> getNativePagedResult(CharSequence sql, Map<String, Object> params, int page, int size, int limMaxRisultati) {

		try {
			String countSql = " select count(*) from (";
			countSql += sql.toString();
			countSql += ")";
	
			Query qn = composeNativeQuery(countSql, params);
			
			long count = ((Number) qn.getSingleResult()).longValue();
			if(count == 0) {
				return new PageImpl<>(0);
			}
			
			if (limMaxRisultati > 0 && count >= limMaxRisultati) 
				throw new RicercaLimMaxException(limMaxRisultati, "La ricerca ha superato il limite massimo dei risultati consentiti:" + limMaxRisultati);
			
	
			Query query = entityManager.createNativeQuery(sql.toString());
			
			
			query = replaceParams(query, params);
			query.setFirstResult(page * size);
			query.setMaxResults(size);
	
			@SuppressWarnings("unchecked")
			List<Object> resultList = query.getResultList();
			
			if (resultList != null) {
				
				List<ComVRicercaComunicazione> res = new ArrayList<ComVRicercaComunicazione>();
				
				ComVRicercaComunicazione com = null;
				for (Object rowObj : resultList) {
					
					
					StringBuilder jpql = new StringBuilder();
					jpql.append(" SELECT ID_COM_D_COMUNICAZIONE, ANNO_PROT_COM, CODICE_COMUNICAZIONE_REG, ");
					jpql.append(" CODICE_FISCALE_DATORE, CODICE_FISCALE_DATORE_PREC, CODICE_FISCALE_LAVORATORE, CODICE_FISCALE_SOGGETTO, COGNOME_LAVORATORE, ");
					jpql.append(" DS_COM_T_STATO_COMUNICAZIONE, DS_COM_T_TIPO_COMUNICAZIONE, DS_DENOMINAZIONE_DATORE, DS_DENOMINAZIONE_DATORE_PREC, ");
					jpql.append(" DT_EVENTO,DT_INIZIO_RAPPORTO,DT_INSERT,DT_INVIO, ");
					jpql.append(" FLG_CURRENT_RECORD, ID_COM_D_DATORE, ID_COM_D_DATORE_PREC, ID_COM_T_PROVINCIA, ID_COM_T_STATO_COMUNICAZIONE, ID_COM_T_TIPO_COMUNICAZIONE, ID_COM_T_TIPO_COMUNICAZIONE_TU, ");
					jpql.append(" ID_COM_T_TIPO_SOMMINISTRAZIONE, ID_COM_T_TIPO_TRASFERIMENTO, ID_COM_T_VARIAZIONE_SOMM, ID_TIPO_TRACCIATO, ");
					jpql.append(" NOME_LAVORATORE, NUM_PROT_COM ");
					
					Object[] row = (Object[])rowObj;
					com = new ComVRicercaComunicazione();
					
					if (row[0] != null) {
						com.setId(((BigDecimal)row[0]).longValue());
						com.setIdComDComunicazione(((BigDecimal)row[0]).longValue());
					}
					
					if (row[1] != null)
						com.setAnnoProtCom(((BigDecimal)row[1]).longValue());
					
					com.setCodiceComunicazioneReg((String)row[2]);
					com.setCodiceFiscaleDatore((String)row[3]);
					com.setCodiceFiscaleDatorePrec((String)row[4]);
					com.setCodiceFiscaleLavoratore((String)row[5]);
					com.setCodiceFiscaleSoggetto((String)row[6]);
					com.setCognomeLavoratore((String)row[7]);
					com.setDsComTStatoComunicazione((String)row[8]);
					com.setDsComTTipoComunicazione((String)row[9]);
					com.setDsDenominazioneDatore((String)row[10]);
					com.setDsDenominazioneDatorePrec((String)row[11]);
					com.setDtEvento(getDate(row[12]));
					com.setDtInizioRapporto(getDate(row[13]));
					com.setDtInsert(getDate(row[14]));
					com.setDtInvio(getDate(row[15]));
					com.setFlgCurrentRecord((String)row[16]);
					if (row[17] != null)
						com.setIdComDDatore(((BigDecimal)row[17]).longValue());
					if (row[18] != null)
						com.setIdComDDatorePrec(((BigDecimal)row[18]).longValue());
					if (row[19] != null)
						com.setIdComTProvincia(((BigDecimal)row[19]).longValue());
					if (row[20] != null)
						com.setIdComTStatoComunicazione(((BigDecimal)row[20]).longValue());
					com.setIdComTTipoComunicazione((String)row[21]);
					if (row[22] != null)
						com.setIdComTTipoComunicazioneTu(((BigDecimal)row[22]).longValue());
					if (row[23] != null)
						com.setIdComTTipoSomministrazione(((BigDecimal)row[23]).longValue());
					if (row[24] != null)
						com.setIdComTTipoTrasferimento(((BigDecimal)row[24]).longValue());
					if (row[25] != null)
						com.setIdComVariazioneSomm(((BigDecimal)row[25]).longValue());
					if (row[26] != null)
						com.setIdTipoTracciato((String)row[26]);
					com.setNomeLavoratore((String)row[27]);
					if (row[28] != null)
					com.setNumProtCom(((BigDecimal)row[28]).longValue());
					
					
					res.add(com);
				}
				return new PageImpl<>(count, res);
			}
			return new PageImpl<>(0);
		}
		catch (QueryTimeoutException ex) {
			throw new TimeoutRicercaException();
		}
	}
	
	private static final SimpleDateFormat dateFormatDatabase = new SimpleDateFormat("dd/MM/yyyy");
	private String getDateDB(Date date, boolean start) {
		String str = dateFormatDatabase.format(date);
		if (start) 
			return "'" + str + " 00:00:00'";
		else return "'" + str + " 23:59:59'";
	}
	
	private Date getDate(Object obj) {
		if (obj !=  null) {
			java.sql.Timestamp timestamp = (java.sql.Timestamp) obj; 
			if (timestamp != null)
				return new Date(timestamp.getTime());
		}
		return null;
	}

}
