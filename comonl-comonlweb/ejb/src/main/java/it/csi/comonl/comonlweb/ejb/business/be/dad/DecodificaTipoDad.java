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

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoAttoL68Dao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoComunicazioneTuDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoContrAmmPerComDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoContrPeriodiDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoContrattiDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoEntePromTirocinioDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoOrarioDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoSoggettoAbilitatoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoSomministrazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoTracciatoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoTrasferimentoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipologiaTirocinioDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTVariazioneSommDao;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoAttoL68;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoComunicazione;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoComunicazioneTu;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContratti;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoEntePromTirocinio;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoOrario;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoTracciato;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoTrasferimento;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipologiaTirocinio;
import it.csi.comonl.comonlweb.ejb.exception.NotFoundException;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoAttoL68;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazioneTu;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoEntePromTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTracciato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.VariazioneSomm;

/**
 * Data Access Delegate for decodificas
 */
@ApplicationScoped
public class DecodificaTipoDad extends BaseDad {

	@Inject
	private ComTTipoComunicazioneDao comTTipoComunicazioneDao;
	
	@Inject
	private ComTTipoComunicazioneTuDao comTTipoComunicazioneTuDao;
	
	@Inject
	private ComTTipoSomministrazioneDao comTTipoSomministrazioneDao;
	
	@Inject
	private ComTVariazioneSommDao comTTipoVariazioneDao;
	
	@Inject
	private ComTTipologiaTirocinioDao comTTipologiaTirocinioDao;
	
	@Inject
	private ComTTipoContrattiDao comTTipoContrattiDao;
	
	@Inject
	private ComTTipoEntePromTirocinioDao comTTipoEntePromTirocinioDao;
	
	@Inject
	private ComTTipoOrarioDao comTTipoOrarioDao;
	
	@Inject
	private ComTTipoAttoL68Dao comTTipoAttoL68Dao;
	
	@Inject
	private ComTTipoSoggettoAbilitatoDao comTTipoSoggettoAbilitatoDao;
	
	@Inject
	private ComTTipoTracciatoDao comTTipoTracciatoDao;
	
	@Inject
	private ComTTipoContrAmmPerComDao comTTipoContrAmmPerComDao;
	
	@Inject
	private ComTTipoContrPeriodiDao comTTipoContrPeriodiDao;
	
	@Inject
	private ComTTipoTrasferimentoDao comTTipoTrasferimentoDao;

	
	/**
	 * Returns the TipoComunicazione's list
	 * 
	 * @return the TipoComunicazione's list
	 */
	public List<TipoComunicazione> getTipoComunicazione() {
		List<ComTTipoComunicazione> list = comTTipoComunicazioneDao.findAll();
		return ComonlMappers.TIPO_COMUNICAZIONE.toModels(list);
	}
	
	/**
	 * Returns the TipoComunicazioneTu's list
	 * 
	 * @return the TipoComunicazioneTu's list
	 */
	public List<TipoComunicazioneTu> getTipoComunicazioneTu(Boolean flgRicerca) {
		List<ComTTipoComunicazioneTu> list = comTTipoComunicazioneTuDao.findAllByFlg(flgRicerca);
		return ComonlMappers.TIPO_COMUNICAZIONE_TU.toModels(list);
	}
	
	public List<DecodificaGenerica> getTipoSomministrazioneDecodifica(DecodificaGenerica filtro) {
		return comTTipoSomministrazioneDao.findByFilter(filtro);
	}
	
	public List<DecodificaGenerica> getTipoVariazioneDecodifica(DecodificaGenerica filtro) {
		return comTTipoVariazioneDao.findByFilter(filtro);
	}
	
	public VariazioneSomm getTipoVariazioneBySommComm(Long tipoSomministrazione, String tipoComunicazione) {
		return ComonlMappers.VARIAZIONE_SOMM.toModel(comTTipoVariazioneDao.findTipoVariazioneBySommComm(tipoSomministrazione,tipoComunicazione));
	}
	
	public List<DecodificaGenerica> getTipoComunicazioneDecodifica(DecodificaGenerica filtro) {
		return comTTipoComunicazioneDao.findByFilter(filtro);
	}
	
	/**
	 * Returns the TipologiaTirocinio's list
	 * 
	 * @return the TipologiaTirocinio's list
	 */
	public List<TipologiaTirocinio> getTipologiaTirocinio() {
		List<ComTTipologiaTirocinio> list = comTTipologiaTirocinioDao.findValidDate();
		return ComonlMappers.TIPOLOGIA_TIROCINIO.toModels(list);
	}
	
	/**
	 * Returns the TipoContratto's list
	 * 
	 * @return the TipoContratto's list
	 */
	public List<TipoContratti> getTipoContratto() {
		List<ComTTipoContratti> list = comTTipoContrattiDao.findValidDate();
		return ComonlMappers.TIPO_CONTRATTI.toModels(list);
	}
	
	/**
	 * Returns the TipoContratto's list
	 * 
	 * @return the TipoContratto's list
	 */
	public List<TipoContratti> GetTipoContrattiByTracciatoAndTipoComunicazione(String tipoTracciato,String tipoCmunicazione) {
		List<ComTTipoContratti> list = comTTipoContrattiDao.GetTipoContrattiByTracciatoAndTipoComunicazioneService(tipoTracciato,tipoCmunicazione);
		return ComonlMappers.TIPO_CONTRATTI.toModels(list);
	}
	
	/**
	 * Returns the TipoEntePromTirocinio's list
	 * 
	 * @return the TipoEntePromTirocinio's list
	 */
	public List<TipoEntePromTirocinio> getTipoEntePromTirocinio() {
		List<ComTTipoEntePromTirocinio> list = comTTipoEntePromTirocinioDao.findValidDate();
		return ComonlMappers.TIPO_ENTE_PROM_TIROCINIO.toModels(list);
	}
	
	/**
	 * Returns the TipoOrario's list
	 * 
	 * @return the TipoOrario's list
	 */
	public List<TipoOrario> getTipoOrario() {
		List<ComTTipoOrario> list = comTTipoOrarioDao.findValidDate();
		return ComonlMappers.TIPO_ORARIO.toModels(list);
	}
	
	/**
	 * Returns the TipoAttoL68's list
	 * 
	 * @return the TipoAttoL68's list
	 */
	public List<TipoAttoL68> getTipoAttoL68() {
		List<ComTTipoAttoL68> list = comTTipoAttoL68Dao.findAll();
		return ComonlMappers.TIPO_ATTO_L68.toModels(list);
	}
	
	
	/**
	 * Returns the TipoAltroSoggettoAbilitato's list
	 * 
	 * @return the TipoAltroSoggettoAbilitato's list
	 */
	public List<TipoSoggettoAbilitato> getTipoSoggettoAbilitato() {
		List<ComTTipoSoggettoAbilitato> list = comTTipoSoggettoAbilitatoDao.findValidDate();
		return ComonlMappers.TIPO_SOGGETTO_ABILITATO.toModels(list);
	}
	
	public TipoComunicazione getTipoComunicazioneById(String idComTipoComunicazione) {
		Optional<ComTTipoComunicazione> tipoComunicazioneFinded = comTTipoComunicazioneDao.findOne(idComTipoComunicazione);
		if(!tipoComunicazioneFinded.isPresent()) {
			throw new NotFoundException("TipoComunicazione");
		}else {
			return ComonlMappers.TIPO_COMUNICAZIONE.toModel(tipoComunicazioneFinded.get());
		}
		
	}
	
	public TipoTracciato getTipoTracciatoById(String idComTipoTracciato) {
		Optional<ComTTipoTracciato> tipoTracciatoFinded = comTTipoTracciatoDao.findOne(idComTipoTracciato);
		if(!tipoTracciatoFinded.isPresent()) {
			throw new NotFoundException("TipoTracciato");
		}else {
			return ComonlMappers.TIPO_TRACCIATO.toModel(tipoTracciatoFinded.get());
		}
		
	}
	
	public TipoComunicazioneTu getTipoComunicazioneTuById(Long idComTipoComunicazioneTu) {
		Optional<ComTTipoComunicazioneTu> tipoComunicazioneTuFinded = comTTipoComunicazioneTuDao.findOne(idComTipoComunicazioneTu);
		if(!tipoComunicazioneTuFinded.isPresent()) {
			throw new NotFoundException("tipoComunicazioneTuFinded");
		}else {
			return ComonlMappers.TIPO_COMUNICAZIONE_TU.toModel(tipoComunicazioneTuFinded.get());
		}
		
	}
	
	
	/**
	 * Returns the TipoAltroSoggettoAbilitato's list
	 * 
	 * @return the TipoAltroSoggettoAbilitato's list
	 */
	public List<TipoSoggettoAbilitato> getTipoStudioProfessionale() {
		List<ComTTipoSoggettoAbilitato> list = comTTipoSoggettoAbilitatoDao.findTipoStudioProfessionales();
		return ComonlMappers.TIPO_SOGGETTO_ABILITATO.toModels(list);
	}
	
	
	/**
	 * Returns the TipoTrasferimento's list
	 * 
	 * @return the TipoTrasferimento's list
	 */
	public List<TipoTrasferimento> getTipoTrasferimento() {
		List<ComTTipoTrasferimento> list = comTTipoTrasferimentoDao.findValidDate();
		return ComonlMappers.TIPO_TRASFERIMENTO.toModels(list);
	}
	



}
