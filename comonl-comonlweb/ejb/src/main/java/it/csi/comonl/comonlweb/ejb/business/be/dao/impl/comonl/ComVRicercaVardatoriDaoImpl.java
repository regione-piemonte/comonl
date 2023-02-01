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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComVRicercaVardatoriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComVRicercaVardatori;
import it.csi.comonl.comonlweb.ejb.exception.TimeoutRicercaException;
import it.csi.comonl.comonlweb.ejb.util.jpa.Page;
import it.csi.comonl.comonlweb.ejb.util.jpa.PageImpl;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;

/**
 * Data Access Object implementor for the entity ComDComunicazione
 */
@ApplicationScoped
public class ComVRicercaVardatoriDaoImpl extends BaseEntityDaoImpl<Long, ComVRicercaVardatori> implements ComVRicercaVardatoriDao {


	@Override
	public Long countRicercaComunicazione(FormRicercaComunicazione ricercaComunicazione) {

		log.debug("countRicerca----------->", "Entro nel metodo countRicerca");

		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		composeQueryRicerca(ricercaComunicazione, params, jpql);


		String countSql = " select count(*) from (";
		countSql += jpql.toString();
		countSql += ")";


		Query qn = composeNativeQuery(countSql, params);
		long count = ((Number) qn.getSingleResult()).longValue();

		log.debug("countRicerca----------->", "count:" + count);

		return count;
	}


	@Override
	public Page<ComVRicercaVardatori> findPaginated(int page, int size, String sortField, String sortDirection, FormRicercaComunicazione ricercaComunicazione, int limMaxRisultati) {

		Map<String, Object> params = new HashMap<>();
		StringBuilder jpql = new StringBuilder();
		composeQueryRicerca(ricercaComunicazione, params, jpql);
		return getNativePagedResult(jpql, params, page, size);
	}



	@Override
	protected Page<ComVRicercaVardatori> getNativePagedResult(CharSequence sql, Map<String, Object> params, int page, int size) {

		try {
			String countSql = " select count(*) from (";
			countSql += sql.toString();
			countSql += ")";
	
			Query qn = composeNativeQuery(countSql, params);
			long count = ((Number) qn.getSingleResult()).longValue();
			if(count == 0) {
				return new PageImpl<>(0);
			}
	
			Query query = entityManager.createNativeQuery(sql.toString());
			query = replaceParams(query, params);
			query.setFirstResult(page * size);
			query.setMaxResults(size);
	
			@SuppressWarnings("unchecked")
			List<Object> resultList = query.getResultList();
			
			if (resultList != null) {
				
				List<ComVRicercaVardatori> res = new ArrayList<ComVRicercaVardatori>();
				
				ComVRicercaVardatori com = null;
				for (Object rowObj : resultList) {
					
					Object[] row = (Object[])rowObj;
					com = new ComVRicercaVardatori();
					
					if (row[0] != null)
						com.setId(((BigDecimal)row[0]).longValue());
					
					if (row[1] != null)
						com.setIdComDDatore(((BigDecimal)row[1]).longValue());
					
					if (row[2] != null)
						com.setIdComDDatorePrec(((BigDecimal)row[2]).longValue());
					com.setCodiceFiscaleDatore((String)row[3]);
					com.setDsDenominazioneDatore((String)row[4]);
					com.setCodiceComunicazioneReg((String)row[5]);
					com.setCodiceFiscaleDatorePrec((String)row[6]);
					com.setDsComTStatoComunicazione((String)row[7]);
					com.setDsComTTipoComunicazione((String)row[8]);
					com.setDtInvio(getDate(row[9]));
					com.setDtInsert(getDate(row[10]));
					com.setDtEvento(getDate(row[11]));
					com.setDtTrasferimento(getDate(row[12]));
					com.setIdComTTipoComunicazioneTu(((BigDecimal)row[13]).longValue());
					
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
	
	private Date getDate(Object obj) {
		
		if (obj !=  null) {
			java.sql.Timestamp timestamp = (java.sql.Timestamp) obj; 
			if (timestamp != null)
				return new Date(timestamp.getTime());
		}
		return null;
	}
	

	
	private void composeQueryRicerca(FormRicercaComunicazione ricercaComunicazione, Map<String, Object> params, StringBuilder sql) {
				
		sql.append("SELECT DISTINCT ID_COM_D_COMUNICAZIONE, ID_COM_D_DATORE, ID_COM_D_DATORE_PREC, CODICE_FISCALE_DATORE, DS_DENOMINAZIONE_DATORE, CODICE_COMUNICAZIONE_REG, CODICE_FISCALE_DATORE_PREC, DS_COM_T_STATO_COMUNICAZIONE, DS_COM_T_TIPO_COMUNICAZIONE, DT_INVIO, DT_INSERT, trunc(DT_EVENTO) AS DT_EVENTO, DT_TRASFERIMENTO, ID_COM_T_TIPO_COMUNICAZIONE_TU");
		sql.append(" FROM (");
		sql.append("SELECT * FROM COM_V_RICERCA_VARDATORI WHERE 1=1");
		
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
		
		if (StringUtils.isNotBlank(ricercaComunicazione.getCodiceFiscaleSoggetto())) {
			sql.append("AND CODICE_FISCALE_SOGGETTO = :codiceFiscaleSoggetto");
			params.put("codiceFiscaleSoggetto", ricercaComunicazione.getCodiceFiscaleSoggetto());
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
			sql.append(" AND ID_COM_T_PROVINCIA = :provincia  ");
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
		
		sql.append(" AND ID_TIPO_TRACCIATO = 'VD' ");


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

		if (StringUtils.isNotBlank(ricercaComunicazione.getTipoVariazione())){
			if ("A".equalsIgnoreCase(ricercaComunicazione.getTipoVariazione()))
				sql.append(" AND ID_COM_D_DATORE_PREC is null ");
			else if ("T".equalsIgnoreCase(ricercaComunicazione.getTipoVariazione()))
				sql.append(" AND ID_COM_D_DATORE_PREC is not null ");
		}

		sql.append(")");


		log.debug("STAMPO SQL ", "-------->" + sql.toString());
		log.debug("STAMPO I PARAMETRI PASSATI PER LA RICERCA ", "-------->" + ricercaComunicazione.toString());
	}


	private static final SimpleDateFormat dateFormatDatabase = new SimpleDateFormat("dd/MM/yyyy");
	private String getDateDB(Date date, boolean start) {
		String str = dateFormatDatabase.format(date);
		if (start)
			return "'" + str + " 00:00:00'";
		else return "'" + str + " 23:59:59'";
	}


}
