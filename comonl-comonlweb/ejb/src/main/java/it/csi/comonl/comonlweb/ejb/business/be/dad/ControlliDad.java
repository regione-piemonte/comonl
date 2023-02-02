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
package it.csi.comonl.comonlweb.ejb.business.be.dad;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDAnagraficaAziAccentDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDSoggettoAbilitatoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTAtecofinDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCategLavAssObblDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCcnlDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCessazionerlDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCittadinanzaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTComonlsParametriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTComuneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTEntePrevidenzialeDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTGradoContrattualeDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTIstat2001livello5Dao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTLivelloRetribuzioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTStatiEsteriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTStatusStranieroDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoContrAmmPerComDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoContrPeriodiDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoContrattiDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoOrarioDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTrasformazionerlDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDAnagraficaAziAccent;
import it.csi.comonl.comonlweb.ejb.entity.ComDSoggettoAbilitato;
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
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoComunicazioneTu;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContrAmmPerCom;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContrPeriodi;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContratti;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoEntePromTirocinio;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoOrario;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoTrasferimento;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipologiaTirocinio;
import it.csi.comonl.comonlweb.ejb.entity.ComTTrasformazionerl;
import it.csi.comonl.comonlweb.ejb.entity.ComTVariazioneSomm;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.BaseDto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategTirocinante;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cessazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cpi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.GradoContrattuale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloRetribuzione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloStudio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Provincia;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Regione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazioneTu;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrAmmPerCom;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrPeriodi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoEntePromTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.VariazioneSomm;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

@ApplicationScoped
public class ControlliDad extends BaseDad {
	
	@Inject
	private ComTCcnlDao comTCcnlDao;
	
	@Inject
	private ComTTipoContrAmmPerComDao comTTipoContrAmmPerComDao;
	
	@Inject
	private ComTTipoContrPeriodiDao comTTipoContrPeriodiDao;
	
	@Inject
	private ComTLivelloRetribuzioneDao comTLivelloRetribuzioneDao;
	
	@Inject
	private ComTTipoOrarioDao comTTipoOrarioDao;
	
	@Inject
	private ComTIstat2001livello5Dao comTIstat2001livello5Dao;
	
	@Inject
	private ComTEntePrevidenzialeDao comTEntePrevidenzialeDao;
	
	@Inject
	private ComTComonlsParametriDao comTComonlsParametriDao;
	
	@Inject
	private ComTGradoContrattualeDao comTGradoContrattualeDao;
	
	@Inject
	private ComTTrasformazionerlDao comTTrasformazionerlDao;
	
	@Inject
	private ComTCessazionerlDao comTCessazionerlDao;
	
	@Inject
	private ComTAtecofinDao comTAtecofinDao;
	
	@Inject
	private ComTStatiEsteriDao comTStatiEsteriDao;
	
	@Inject
	private ComTComuneDao comTComuneDao;
	
	@Inject
	private ComTCategLavAssObblDao comTCategLavAssObblDao;
	
	@Inject
	private ComTTipoContrattiDao comTTTipoContrattiDao;
	
	@Inject
	private ComTStatusStranieroDao comTStatusStranieroDao;
	
	@Inject
	private ComDSoggettoAbilitatoDao comDSoggettoAbilitatoDao;
	@Inject
	private ComDAnagraficaAziAccentDao comDAnagraficaAziAccentDao;
	
	@Inject
	private ComDComunicazioneDao comDComunicazioneDao;
	
	@Inject
	private ComTCittadinanzaDao comTCittadinanzaDao;
	
	public TipoContrAmmPerCom getTipoContrAmmPerComByTipoContratto(Long idComTTipoContratto) {
		Optional<ComTTipoContrAmmPerCom> record = comTTipoContrAmmPerComDao.findByTipoContratto(idComTTipoContratto);
		return record.map(ComonlMappers.TIPO_CONTR_AMM_PER_COM::toModel).orElse(null);
	}
	
	public TipoContrPeriodi getTipoContrPeriodiByTipoContrattoInData(Long idComTTipoContratto, Date inData) {
		Optional<ComTTipoContrPeriodi> record = comTTipoContrPeriodiDao.findByTipoContrattoInData(idComTTipoContratto, inData);
		return record.map(ComonlMappers.TIPO_CONTR_PERIODI::toModel).orElse(null);
	}
	
	public Ccnl getCcnlValid(Ccnl ccnl, Date date) {
		if (ccnl==null)
			return null;
		if(ccnl.getId()==null && (ccnl.getCodCcnlMin()==null || ccnl.getCodCcnlMin().isBlank()))
			return null;		
		ComTCcnl comTCcnl = ComonlMappers.CCNL.toEntity(ccnl);
		Optional<ComTCcnl> record = comTCcnlDao.findValid(comTCcnl, date);
		return record.map(ComonlMappers.CCNL::toModel).orElse(null);
	}
	
	public LivelloRetribuzione getLivelloRetribuzioneValid(LivelloRetribuzione livelloRetribuzione, Date date) {
		if (livelloRetribuzione==null)
			return null;
		if(livelloRetribuzione.getId()==null && (livelloRetribuzione.getCodLivello()==null ||  StringUtils.isBlank(livelloRetribuzione.getDesLivello())))
			return null;
		ComTLivelloRetribuzione comTLivelloRetribuzione = ComonlMappers.LIVELLO_RETRIBUZIONE.toEntity(livelloRetribuzione);
		Optional<ComTLivelloRetribuzione> record = comTLivelloRetribuzioneDao.findValid(comTLivelloRetribuzione, date);
		return record.map(ComonlMappers.LIVELLO_RETRIBUZIONE::toModel).orElse(null);
	}
	
	// valutare se usare il metodo che ritorna la lista di validi o interrogare puntualmente per il id e filtro su data 
	public List<TipoOrario> getTipoOrario() {
		List<ComTTipoOrario> list = comTTipoOrarioDao.findValidDate();
		return ComonlMappers.TIPO_ORARIO.toModels(list);
	}
	
	public TipoOrario getTipoOrarioValid(TipoOrario tipoOrario, Date date) {
		if (tipoOrario==null)
			return null;
		if(tipoOrario.getId()==null && StringUtils.isBlank(tipoOrario.getCodTipoorarioMin()))
			return null;
		ComTTipoOrario comTTipoOrario = ComonlMappers.TIPO_ORARIO.toEntity(tipoOrario);
		Optional<ComTTipoOrario> record = comTTipoOrarioDao.findValid(comTTipoOrario, date);
		return record.map(ComonlMappers.TIPO_ORARIO::toModel).orElse(null);
	}
	
	public Istat2001livello5 getIstat2001livello5Valid(Istat2001livello5 istat2001livello5, Date date) {
		if (istat2001livello5==null)
			return null;
		if(istat2001livello5.getId()==null && (istat2001livello5.getCodIstat2001livello5Min()==null || istat2001livello5.getCodIstat2001livello5Min().isBlank()))
			return null;
		ComTIstat2001livello5 comTIstat2001livello5 = ComonlMappers.ISTAT2001LIVELLO5.toEntity(istat2001livello5);
		Optional<ComTIstat2001livello5> record = comTIstat2001livello5Dao.findValid(comTIstat2001livello5, date);
		return record.map(ComonlMappers.ISTAT2001LIVELLO5::toModel).orElse(null);
	}
	public Istat2001livello5 getIstat2001livello5ByCodiceDescrizione(String codice, String descrizione) {
		if(StringUtils.isBlank(codice) && StringUtils.isBlank(descrizione))
			return null;
		Optional<ComTIstat2001livello5> record = comTIstat2001livello5Dao.findByCodiceDescrizione(codice, descrizione);
		return record.map(ComonlMappers.ISTAT2001LIVELLO5::toModel).orElse(null);
	}
	
	public EntePrevidenziale getEntePrevidenzialeValid(EntePrevidenziale entePrevidenziale, Date date) {
		if (entePrevidenziale==null)
			return null;
		if(entePrevidenziale.getId()==null && (entePrevidenziale.getCodEntePrevidenzialeMin()==null || entePrevidenziale.getCodEntePrevidenzialeMin().isBlank()))
			return null;
		
		ComTEntePrevidenziale comTEntePrevidenziale = ComonlMappers.ENTE_PREVIDENZIALE.toEntity(entePrevidenziale);
		Optional<ComTEntePrevidenziale> record = comTEntePrevidenzialeDao.findValid(comTEntePrevidenziale, date);
		return record.map(ComonlMappers.ENTE_PREVIDENZIALE::toModel).orElse(null);
	}
	public GradoContrattuale getGradoContrattualeByCodiceDescrizione(String codice, String descrizione) {
		if(StringUtils.isBlank(codice) && StringUtils.isBlank(descrizione))
			return null;
		Optional<ComTGradoContrattuale> record = comTGradoContrattualeDao.findByCodiceDescrizione(codice, descrizione);
		return record.map(ComonlMappers.GRADO_CONTRATTUALE::toModel).orElse(null);
	}
	public Trasformazionerl getTrasformazionerlValid(Trasformazionerl trasformazionerl, Date date) {
		if (trasformazionerl==null)
			return null;
		if(trasformazionerl.getId()==null && (trasformazionerl.getCodTrasformazionirlMin()==null))
			return null;
		
		ComTTrasformazionerl comTTrasformazionerl = ComonlMappers.TRASFORMAZIONERL.toEntity(trasformazionerl);
		Optional<ComTTrasformazionerl> record = comTTrasformazionerlDao.findValid(comTTrasformazionerl, date);
		return record.map(ComonlMappers.TRASFORMAZIONERL::toModel).orElse(null);
	}
	
	public Cessazionerl getCessazionerlValid(Cessazionerl cessazionerl, Date date) {
		if (cessazionerl==null)
			return null;
		if(cessazionerl.getId()==null && (cessazionerl.getCodCessazioneMin()==null))
			return null;
		
		ComTCessazionerl comTCessazionerl = ComonlMappers.CESSAZIONERL.toEntity(cessazionerl);
		Optional<ComTCessazionerl> record = comTCessazionerlDao.findValid(comTCessazionerl, date);
		return record.map(ComonlMappers.CESSAZIONERL::toModel).orElse(null);
	}
	
	public Atecofin getAtecofinValid(Atecofin model, Date date) {
		if (model==null)
			return null;
		if(model.getId()==null && (model.getCodAtecofinMin()==null))
			return null;
		
		ComTAtecofin entity = ComonlMappers.ATECOFIN.toEntity(model);
		Optional<ComTAtecofin> record = comTAtecofinDao.findValid(entity, date);
		return record.map(ComonlMappers.ATECOFIN::toModel).orElse(null);
	}
	
	public StatiEsteri getStatiEsteriValid(StatiEsteri model, Date date) {
		if (model==null)
			return null;
		if(model.getId()==null && (model.getCodNazioneMin()==null))
			return null;
		
		ComTStatiEsteri entity = ComonlMappers.STATI_ESTERI.toEntity(model);
		Optional<ComTStatiEsteri> record = comTStatiEsteriDao.findValid(entity, date);
		return record.map(ComonlMappers.STATI_ESTERI::toModel).orElse(null);
	}
	
	public Comune getComuneValid(Comune model, Date date) {
		if (model==null)
			return null;
		if(model.getId()==null && (model.getCodComuneMin()==null))
			return null;
		
		ComTComune entity = ComonlMappers.COMUNE.toEntity(model);
		Optional<ComTComune> record = comTComuneDao.findValid(entity, date);
		return record.map(ComonlMappers.COMUNE::toModel).orElse(null);
	}
	public CategLavAssObbl getCategLavAssObblValid(CategLavAssObbl model, Date date) {
		if (model==null)
			return null;
		if(model.getId()==null && (model.getCodCategLavAssObblMin()==null))
			return null;
		
		ComTCategLavAssObbl entity = ComonlMappers.CATEG_LAV_ASS_OBBL.toEntity(model);
		Optional<ComTCategLavAssObbl> record = comTCategLavAssObblDao.findValid(entity, date);
		return record.map(ComonlMappers.CATEG_LAV_ASS_OBBL::toModel).orElse(null);
	}
	public TipoContratti getTipoContrattiValid(TipoContratti model, Date date) {
		if (model==null)
			return null;
		if(model.getId()==null && (model.getCodTipoContrattoMin()==null))
			return null;
		
		ComTTipoContratti entity = ComonlMappers.TIPO_CONTRATTI.toEntity(model);
		Optional<ComTTipoContratti> record = comTTTipoContrattiDao.findValid(entity, date);
		return record.map(ComonlMappers.TIPO_CONTRATTI::toModel).orElse(null);
	}
	public StatusStraniero getStatusStranieroValid(StatusStraniero model, Date date) {
		if (model==null)
			return null;
		if(model.getId()==null && (model.getCodStatusMin()==null))
			return null;
		
		ComTStatusStraniero entity = ComonlMappers.STATUS_STRANIERO.toEntity(model);
		Optional<ComTStatusStraniero> record = comTStatusStranieroDao.findValid(entity, date);
		return record.map(ComonlMappers.STATUS_STRANIERO::toModel).orElse(null);
	}
	
	public ComonlsParametri getParametroAbilitatoByDescrizione(String nomeParam) {
		Optional<ComonlsParametri> entities = comTComonlsParametriDao.findByDescrizioneParametro(nomeParam)
				.map(ComonlMappers.COMONLS_PARAMETRI::toModel);
		if (entities.isPresent() && entities.get().getAbilitato().equalsIgnoreCase(ComonlConstants.FLAG_S)) {
			return entities.get();
		}
		return null;
	}
	
	public SoggettoAbilitato findByCfSoggetto(String cfsoggetto) {
		Optional<ComDSoggettoAbilitato> soggettoAbilitatoFinded = comDSoggettoAbilitatoDao.findByCfSoggetto(cfsoggetto);
		if (!soggettoAbilitatoFinded.isPresent()) {
			return null;
		} else {
			return ComonlMappers.SOGGETTO_ABILITATO.toModel(soggettoAbilitatoFinded.get());
		}
	}
	
	public String getFlgAccentramento(String cfSoggetto) {
		Optional<ComDSoggettoAbilitato> flgFinded = comDSoggettoAbilitatoDao.findByCfSoggetto(cfSoggetto);
		if (!flgFinded.isPresent()) {
			return null;
		} else {
			SoggettoAbilitato soggetto = ComonlMappers.SOGGETTO_ABILITATO.toModel(flgFinded.get());
			return soggetto.getFlgAccentramento();
		}
	}
	public AnagraficaAziAccent getAnagraficaAziAccent(String codiceFiscaleDatoreLavoro) {
		Optional<ComDAnagraficaAziAccent> record = comDAnagraficaAziAccentDao.findByCodiceFiscale(codiceFiscaleDatoreLavoro);
		return record.map(ComonlMappers.ANAGRAFICA_AZI_ACCENT::toModel).orElse(null);
	}
	public Cittadinanza getCittadinanzaByCodMf(String codMf) {
		Optional<ComTCittadinanza> record = comTCittadinanzaDao.findByCodMf(codMf);
		return record.map(ComonlMappers.CITTADINANZA::toModel).orElse(null);
	}
	
	
	// Metodo copiato da DecodificaDad
	// TODO centralizzare e avene uno
	public <T extends BaseDto<Long>> BaseDto<Long> getTfromMin(Class<T> transcodifica, String fieldValue,
			Date dataRiferimento) {

		if(transcodifica == EntePrevidenziale.class) {
			ComTEntePrevidenziale entity = (ComTEntePrevidenziale)comDComunicazioneDao.getTfromMin(ComTEntePrevidenziale.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.ENTE_PREVIDENZIALE.toModel(entity);
			
		} else if (transcodifica == Ccnl.class) {
			ComTCcnl entity = (ComTCcnl)comDComunicazioneDao.getTfromMin(ComTCcnl.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.CCNL.toModel(entity);

		} else if (transcodifica == StatiEsteri.class) {
			ComTStatiEsteri entity = (ComTStatiEsteri)comDComunicazioneDao.getTfromMin(ComTStatiEsteri.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.STATI_ESTERI.toModel(entity);

		} else if (transcodifica == Istat2001livello5.class) {
			ComTIstat2001livello5 entity = (ComTIstat2001livello5)comDComunicazioneDao.getTfromMin(ComTIstat2001livello5.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.ISTAT2001LIVELLO5.toModel(entity);

		} else if (transcodifica == TipoContratti.class) {
			ComTTipoContratti entity = (ComTTipoContratti)comDComunicazioneDao.getTfromMin(ComTTipoContratti.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.TIPO_CONTRATTI.toModel(entity);

		} else if (transcodifica == TipoOrario.class) {
			ComTTipoOrario entity = (ComTTipoOrario)comDComunicazioneDao.getTfromMin(ComTTipoOrario.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.TIPO_ORARIO.toModel(entity);

		} else if (transcodifica == Atecofin.class) {
			ComTAtecofin entity = (ComTAtecofin)comDComunicazioneDao.getTfromMin(ComTAtecofin.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.ATECOFIN.toModel(entity);

		} else if (transcodifica == CategLavAssObbl.class) {
			ComTCategLavAssObbl entity = (ComTCategLavAssObbl)comDComunicazioneDao.getTfromMin(ComTCategLavAssObbl.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.CATEG_LAV_ASS_OBBL.toModel(entity);

		} else if (transcodifica == CategTirocinante.class) {
			ComTCategTirocinante entity = (ComTCategTirocinante)comDComunicazioneDao.getTfromMin(ComTCategTirocinante.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.CATEG_TIROCINANTE.toModel(entity);

		} else if (transcodifica == Cessazionerl.class) {
			ComTCessazionerl entity = (ComTCessazionerl)comDComunicazioneDao.getTfromMin(ComTCessazionerl.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.CESSAZIONERL.toModel(entity);

		} else if (transcodifica == Cittadinanza.class) {
			ComTCittadinanza entity = (ComTCittadinanza)comDComunicazioneDao.getTfromMin(ComTCittadinanza.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.CITTADINANZA.toModel(entity);

		} else if (transcodifica == Comune.class) {
			ComTComune entity = (ComTComune)comDComunicazioneDao.getTfromMin(ComTComune.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.COMUNE.toModel(entity);

		} else if (transcodifica == Cpi.class) {
			ComTCpi entity = (ComTCpi)comDComunicazioneDao.getTfromMin(ComTCpi.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.CPI.toModel(entity);

		} else if (transcodifica == GradoContrattuale.class) {
			ComTGradoContrattuale entity = (ComTGradoContrattuale)comDComunicazioneDao.getTfromMin(ComTGradoContrattuale.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.GRADO_CONTRATTUALE.toModel(entity);

		} else if (transcodifica == LivelloRetribuzione.class) {
			ComTLivelloRetribuzione entity = (ComTLivelloRetribuzione)comDComunicazioneDao.getTfromMin(ComTLivelloRetribuzione.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.LIVELLO_RETRIBUZIONE.toModel(entity);

		} else if (transcodifica == LivelloStudio.class) {
			ComTLivelloStudio entity = (ComTLivelloStudio)comDComunicazioneDao.getTfromMin(ComTLivelloStudio.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.LIVELLO_STUDIO.toModel(entity);

		} else if (transcodifica == MotivoPermesso.class) {
			ComTMotivoPermesso entity = (ComTMotivoPermesso)comDComunicazioneDao.getTfromMin(ComTMotivoPermesso.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.MOTIVO_PERMESSO.toModel(entity);

		} else if (transcodifica == Provincia.class) {
			ComTProvincia entity = (ComTProvincia)comDComunicazioneDao.getTfromMin(ComTProvincia.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.PROVINCIA.toModel(entity);

		} else if (transcodifica == Questura.class) {
			ComTQuestura entity = (ComTQuestura)comDComunicazioneDao.getTfromMin(ComTQuestura.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.QUESTURA.toModel(entity);

		} else if (transcodifica == Regione.class) {
			ComTRegione entity = (ComTRegione)comDComunicazioneDao.getTfromMin(ComTRegione.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.REGIONE.toModel(entity);

		} else if (transcodifica == StatusStraniero.class) {
			ComTStatusStraniero entity = (ComTStatusStraniero)comDComunicazioneDao.getTfromMin(ComTStatusStraniero.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.STATUS_STRANIERO.toModel(entity);

		} else if (transcodifica == TipoComunicazioneTu.class) {
			ComTTipoComunicazioneTu entity = (ComTTipoComunicazioneTu)comDComunicazioneDao.getTfromMin(ComTTipoComunicazioneTu.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.TIPO_COMUNICAZIONE_TU.toModel(entity);

		} else if (transcodifica == TipoEntePromTirocinio.class) {
			ComTTipoEntePromTirocinio entity = (ComTTipoEntePromTirocinio)comDComunicazioneDao.getTfromMin(ComTTipoEntePromTirocinio.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.TIPO_ENTE_PROM_TIROCINIO.toModel(entity);

		} else if (transcodifica == TipologiaTirocinio.class) {
			ComTTipologiaTirocinio entity = (ComTTipologiaTirocinio)comDComunicazioneDao.getTfromMin(ComTTipologiaTirocinio.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.TIPOLOGIA_TIROCINIO.toModel(entity);

		} else if (transcodifica == TipoSoggettoAbilitato.class) {
			ComTTipoSoggettoAbilitato entity = (ComTTipoSoggettoAbilitato)comDComunicazioneDao.getTfromMin(ComTTipoSoggettoAbilitato.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.TIPO_SOGGETTO_ABILITATO.toModel(entity);

		} else if (transcodifica == TipoTrasferimento.class) {
			ComTTipoTrasferimento entity = (ComTTipoTrasferimento)comDComunicazioneDao.getTfromMin(ComTTipoTrasferimento.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.TIPO_TRASFERIMENTO.toModel(entity);

		} else if (transcodifica == Trasformazionerl.class) {
			ComTTrasformazionerl entity = (ComTTrasformazionerl)comDComunicazioneDao.getTfromMin(ComTTrasformazionerl.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.TRASFORMAZIONERL.toModel(entity);

		} else if (transcodifica == VariazioneSomm.class) {
			ComTVariazioneSomm entity = (ComTVariazioneSomm)comDComunicazioneDao.getTfromMin(ComTVariazioneSomm.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.VARIAZIONE_SOMM.toModel(entity);
		}

		return null;
	}
}
