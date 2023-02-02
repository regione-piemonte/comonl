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
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDAnagraficaAziAccentDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDSoggettoAbilitatoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTAtecofinDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCategLavAssObblDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCategTirocinanteDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCcnlDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCessazionerlDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCittadinanzaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTComuneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCpiDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTEntePrevidenzialeDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTGradoContrattualeDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTIstat2001livello5Dao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTLivelloRetribuzioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTLivelloStudioDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTMotivoPermessoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTProvinciaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTQuesturaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTStatiEsteriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTStatoComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTStatusStranieroDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTipoContrAmmPerComDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTToponimoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTTrasformazionerlDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTVariazioneSommDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityCaricaPersonaPvDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityNaturaGiuridicaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityPersonalizzazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityStatoDelegaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.GeneralSequenceDao;
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
import it.csi.comonl.comonlweb.ejb.entity.ComTStatoComunicazione;
import it.csi.comonl.comonlweb.ejb.entity.ComTStatusStraniero;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoComunicazioneTu;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContrAmmPerCom;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContratti;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoEntePromTirocinio;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoOrario;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoTrasferimento;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipologiaTirocinio;
import it.csi.comonl.comonlweb.ejb.entity.ComTToponimo;
import it.csi.comonl.comonlweb.ejb.entity.ComTTrasformazionerl;
import it.csi.comonl.comonlweb.ejb.entity.ComTVariazioneSomm;
import it.csi.comonl.comonlweb.ejb.entity.EntityCaricaPersonaPv;
import it.csi.comonl.comonlweb.ejb.entity.EntityNaturaGiuridica;
import it.csi.comonl.comonlweb.ejb.entity.EntityPersonalizzazione;
import it.csi.comonl.comonlweb.ejb.entity.EntityStatoDelega;
import it.csi.comonl.comonlweb.ejb.exception.NotFoundException;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.ejb.util.cache.CacheKey;
import it.csi.comonl.comonlweb.ejb.util.cache.impl.CacheHelper;
import it.csi.comonl.comonlweb.ejb.util.cache.impl.CacheSupplier;
import it.csi.comonl.comonlweb.lib.dto.BaseDto;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.CaricaPersonaPv;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Personalizzazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.StatoDelega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Toponimo;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategTirocinante;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cessazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cpi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.GradoContrattuale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloRetribuzione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloStudio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.NaturaGiuridica;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Provincia;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Regione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazioneTu;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrAmmPerCom;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoEntePromTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.VariazioneSomm;
import it.csi.comonl.comonlweb.lib.util.ComonlDateUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

/**
 * Data Access Delegate for decodificas
 */
@ApplicationScoped
public class DecodificaDad extends BaseDad {

	@Inject
	private ComTProvinciaDao comTProvinciaDao;

	@Inject
	private ComTAtecofinDao comTAtecofinDao;

	@Inject
	private ComTStatoComunicazioneDao comTStatoComunicazioneDao;

	@Inject
	private ComTCategTirocinanteDao comTCategTirocinanteDao;

	@Inject
	private ComTCpiDao comTCpiDao;

	@Inject
	private ComTEntePrevidenzialeDao comTEntePrevidenzialeDao;

	@Inject
	private ComTCategLavAssObblDao comTCategLavAssObblDao;

	@Inject
	private ComTComuneDao comTComuneDao;

	@Inject
	private ComTStatiEsteriDao comTStatiEsteriDao;

	@Inject
	private ComTCcnlDao comTCcnlDao;

	@Inject
	private ComTIstat2001livello5Dao comTIstat2001livello5Dao;

	@Inject
	private ComTLivelloRetribuzioneDao comTLivelloRetribuzioneDao;

	@Inject
	private ComTCittadinanzaDao comTCittadinanzaDao;

	@Inject
	private ComTStatusStranieroDao comTStatusStranieroDao;

	@Inject
	private ComTMotivoPermessoDao comTMotivoPermessoDao;

	@Inject
	private ComTQuesturaDao comTQuesturaDao;

	@Inject
	private ComTLivelloStudioDao comTLivelloStudioDao;

	@Inject
	private EntityNaturaGiuridicaDao entityNaturaGiuridicaDao;

	@Inject
	private EntityCaricaPersonaPvDao entityCaricaPersonaPvDao;

	@Inject
	private EntityStatoDelegaDao entityStatoDelegaDao;

	@Inject
	private ComTTrasformazionerlDao comTTrasformazionerlDao;

	@Inject
	private ComTGradoContrattualeDao comTGradoContrattualeDao;
	@Inject
	private ComTCessazionerlDao comTCessazionerlDao;

	@Inject
	private ComTToponimoDao comTToponimoDao;

	@Inject
	private EntityPersonalizzazioneDao entityPersonalizzazioneDao;

	@Inject
	private ComDComunicazioneDao comDComunicazioneDao;

	@Inject
	private ComDSoggettoAbilitatoDao comDSoggettoAbilitatoDao;
	
	@Inject
	private ComDAnagraficaAziAccentDao comDAnagraficaAziAccentDao;
	
	@Inject
	private ComTTipoContrAmmPerComDao comTTipoContrAmmPerComDao;
	
	@Inject
	private GeneralSequenceDao generalSequenceDao;
	
	@Inject
	private ComTVariazioneSommDao comTTipoVariazioneDao;
	
	@Inject
	private CacheHelper cacheHelper;

	
	/**
	 * Returns the Provincia
	 * 
	 * @return the Provincia
	 */
	public List<Provincia> getProvincia(String codRegioneMin) {
		List<ComTProvincia> list = comTProvinciaDao.findByRegione(codRegioneMin);
		return ComonlMappers.PROVINCIA.toModels(list);
	}

	public List<DecodificaGenerica> getAtecofinDecodifica(DecodificaGenerica filtro) {
		return comTAtecofinDao.findByFilter(filtro);
	}
	
	public VariazioneSomm getTipoVariazioneBySommComm(Long tipoSomministrazione, String tipoComunicazione) {
		return ComonlMappers.VARIAZIONE_SOMM.toModel(comTTipoVariazioneDao.findTipoVariazioneBySommComm(tipoSomministrazione,tipoComunicazione));
	}

	/**
	 * Returns the StatoComunicazione's list
	 * 
	 * @return the StatoComunicazione's list
	 */
	public List<StatoComunicazione> getStatoComunicazione(Boolean flgRicerca) {
		List<ComTStatoComunicazione> list = comTStatoComunicazioneDao.findAllByFlg(flgRicerca);
		return ComonlMappers.STATO_COMUNICAZIONE.toModels(list);
	}

	/**
	 * Returns the CategTirocinante's list
	 * 
	 * @return the CategTirocinante's list
	 */
	public List<CategTirocinante> getCategTirocinante() {
		List<ComTCategTirocinante> list = comTCategTirocinanteDao.findValidDate();
		return ComonlMappers.CATEG_TIROCINANTE.toModels(list);
	}

	/**
	 * Returns the Cpi's list
	 * 
	 * @return the Cpi's list
	 */
	public List<Cpi> getCpi() {
		List<ComTCpi> list = comTCpiDao.findValidDate();
		return ComonlMappers.CPI.toModels(list);
	}

	/**
	 * Returns the EntePrevidenziale's list
	 * 
	 * @return the EntePrevidenziale's list
	 */
	public List<EntePrevidenziale> getEntePrevidenziale() {
		List<ComTEntePrevidenziale> list = comTEntePrevidenzialeDao.findValidDate();
		return ComonlMappers.ENTE_PREVIDENZIALE.toModels(list);
	}

	/**
	 * Returns the EntePrevidenziale's list
	 * 
	 * @return the EntePrevidenziale's list
	 */
	public List<CategLavAssObbl> getCategLavoratoreAssObbl() {
		List<ComTCategLavAssObbl> list = comTCategLavAssObblDao.findValidDate();
		return ComonlMappers.CATEG_LAV_ASS_OBBL.toModels(list);
	}

	/**
	 * Returns the Cittadinanza's list
	 * 
	 * @return the Cittadinanza's list
	 */

	public List<Cittadinanza> getCittadinanza() {
		List<ComTCittadinanza> list = comTCittadinanzaDao.findValidDate();
		return ComonlMappers.CITTADINANZA.toModels(list);
	}

	/**
	 * Returns the StatusStraniero's list
	 * 
	 * @return the StatusStraniero's list
	 */

	public List<StatusStraniero> getTitoloSoggiorno() {
		List<ComTStatusStraniero> list = comTStatusStranieroDao.findValidDate();
		return ComonlMappers.STATUS_STRANIERO.toModels(list);
	}

	/**
	 * Returns the MotivoPermesso's list
	 * 
	 * @return the MotivoPermesso's list
	 */

	public List<MotivoPermesso> getMotivoPermesso() {
		List<ComTMotivoPermesso> list = comTMotivoPermessoDao.findValidDate();
		return ComonlMappers.MOTIVO_PERMESSO.toModels(list);
	}

	/**
	 * Returns the Questura's list
	 * 
	 * @return the Questura's list
	 */

	public List<Questura> getQuestura() {
		List<ComTQuestura> list = comTQuesturaDao.findValidDate();
		return ComonlMappers.QUESTURA.toModels(list);
	}

	/**
	 * Returns the LivelloStudio's list
	 * 
	 * @return the LivelloStudio's list
	 */

	public List<LivelloStudio> getLivelloStudio() {
		List<ComTLivelloStudio> list = comTLivelloStudioDao.findValidDate();
		return ComonlMappers.LIVELLO_STUDIO.toModels(list);
	}

	/**
	 * Returns the NaturaGiuridica's list
	 * 
	 * @return the NaturaGiuridica's list
	 */

	public List<NaturaGiuridica> getNaturaGiuridica() {
		List<EntityNaturaGiuridica> list = entityNaturaGiuridicaDao.findValidDate();
		return ComonlMappers.NATURA_GIURIDICA.toModels(list);
	}

	/**
	 * Returns the StatoEstero's list
	 * 
	 * @return the StatoEstero's list
	 */

	public List<StatiEsteri> getStatoEstero() {
		List<ComTStatiEsteri> list = comTStatiEsteriDao.findValidDate();
		return ComonlMappers.STATI_ESTERI.toModels(list);
	}

	public Comune getComuneByCodComuneMin(String codComuneMin) {
		Map<String,Comune> cache = cacheHelper.getCachedValue(CacheKey.COMUNE, new CacheSupplier<String,Comune>());
		Comune comune = cache.get(codComuneMin);
		if (comune != null)
			return comune;
		
		Optional<ComTComune> comuneFinded = comTComuneDao.findByCodComuneMin(codComuneMin);
		if (!comuneFinded.isPresent()) {
			throw new NotFoundException("ComuneByCodComuneMin");
		} else {
			comune = ComonlMappers.COMUNE.toModel(comuneFinded.get());
			cache.put(codComuneMin, comune);
			return comune;
		}
	}
	
	
	public Comune getComuneValidByCodComuneMin(String codComuneMin) {
		return getComuneValidByCodComuneMin(codComuneMin, null);
	}
	
	public Comune getComuneValidByCodComuneMin(String codComuneMin, Date validita) {
		Map<String,Comune> cache = cacheHelper.getCachedValue(CacheKey.COMUNE, new CacheSupplier<String,Comune>());
		Comune comune = cache.get(codComuneMin);
		if (comune != null)
			return comune;
		ComTComune comTcomune = new ComTComune();
		comTcomune.setCodComuneMin(codComuneMin);
		
		Date validitaComune = new Date();
		if (validita != null)
			validitaComune = validita;
		
		Optional<ComTComune> comuneFinded = comTComuneDao.findValid(comTcomune, validitaComune);
		if (!comuneFinded.isPresent()) {
			throw new NotFoundException("ComuneByCodComuneMin alla data: "+  validitaComune);
		} else {
			comune = ComonlMappers.COMUNE.toModel(comuneFinded.get());
			cache.put(codComuneMin, comune);
			return comune;
		}
	}
	
	
	public Comune getComuneByCodComuneMinNoExceptin(String codComuneMin) {
		Map<String,Comune> cache = cacheHelper.getCachedValue(CacheKey.COMUNE, new CacheSupplier<String, Comune>());
		Comune comune = cache.get(codComuneMin);
		if (comune != null)
			return comune;
		Optional<ComTComune> comuneFinded = comTComuneDao.findByCodComuneMin(codComuneMin);
		if (!comuneFinded.isPresent()) {
			return null;
		} else {
			comune = ComonlMappers.COMUNE.toModel(comuneFinded.get());
			cache.put(codComuneMin, comune);
			return comune;
		}
	}
	
	public List<DecodificaGenerica> getComuneDecodifica(DecodificaGenerica filtro) {
		return comTComuneDao.findGenereciaByFilter(filtro);
	}

	public Comune getComuneById(Long id) {
		Optional<ComTComune> comuneFinded = comTComuneDao.findOne(id);
		if (!comuneFinded.isPresent()) {
			throw new NotFoundException("ComuneById");
		} else {
			return ComonlMappers.COMUNE.toModel(comuneFinded.get());
		}
	}

//	public Soggetti getSoggettoByCodice(String codSoggetti) {
//		List<EntityAnagraficaDelegato> entity = anagraficaDelegatoDao.findByField(codSoggetti);
//		return ProdisMappers.SOGGETTI.toModel(entity);
//	}

	public List<DecodificaGenerica> getStatiEsteriDecodifica(DecodificaGenerica filtro) {
		return comTStatiEsteriDao.findByFilter(filtro);
	}

	public List<DecodificaGenerica> getCcnlDecodifica(DecodificaGenerica filtro) {
		return comTCcnlDao.findByFilter(filtro);
	}

	public List<DecodificaGenerica> getQualificaDecodifica(DecodificaGenerica filtro) {
		return comTIstat2001livello5Dao.findByFilter(filtro);
	}

	public List<DecodificaGenerica> getLivelloRetribuzioneDecodifica(DecodificaGenerica filtro) {
		return comTLivelloRetribuzioneDao.findByFilter(filtro);
	}

	public List<CaricaPersonaPv> getCaricaPersonaPv() {
		List<EntityCaricaPersonaPv> list = entityCaricaPersonaPvDao.findAll();
		return ComonlMappers.CARICA_PERSONA_PV.toModels(list);
	}

	public List<Toponimo> getToponimo() {
		Map<String,List<Toponimo>> cache = cacheHelper.getCachedValue(CacheKey.TOPONIMO_ALL, new CacheSupplier<String,List<Toponimo>>());
		List<Toponimo> toponimi = cache.get("all");
		if (toponimi != null)
			return toponimi;
		
		List<ComTToponimo> list = comTToponimoDao.findAll();
		if (list != null) {
			toponimi = ComonlMappers.TOPONIMO.toModels(list);
			cache.put("all", toponimi);
			return toponimi;
		}
		return null;
	}

	public List<StatoDelega> getStatoDelega() {
		List<EntityStatoDelega> list = entityStatoDelegaDao.findAll();
		return ComonlMappers.STATO_DELEGA.toModels(list);
	}

	public StatiEsteri getStatoEsteroByCodStatoMin(String codStatoMin) {
		Optional<ComTStatiEsteri> statoFinded = comTStatiEsteriDao.findByCodStatoMin(codStatoMin);
		if (!statoFinded.isPresent()) {
			throw new NotFoundException("getStatoEsteroByCodStatoMin");
		} else {
			return ComonlMappers.STATI_ESTERI.toModel(statoFinded.get());
		}
	}

	public List<Trasformazionerl> getTrasformazionerlByTipo(String tipo) {
		List<ComTTrasformazionerl> list = comTTrasformazionerlDao.findByTipo(tipo);
		return ComonlMappers.TRASFORMAZIONERL.toModels(list);
	}

	/**
	 * Returns the GradoContrattuale's list
	 * 
	 * @return the GradoContrattuale's list
	 */
	public List<GradoContrattuale> getGradoContrattuale() {
		List<ComTGradoContrattuale> list = comTGradoContrattualeDao.findValid();
		return ComonlMappers.GRADO_CONTRATTUALE.toModels(list);
	}

	/**
	 * Returns the Cessazionerl list
	 * 
	 * @return the Cessazionerl list
	 */
	public List<Cessazionerl> getCessazionerl() {
		List<ComTCessazionerl> list = comTCessazionerlDao.findValid();
		return ComonlMappers.CESSAZIONERL.toModels(list);
	}

	public Cittadinanza getCittadinanzaByCodMin(String codComuneMin) {
		Optional<ComTCittadinanza> cittadinanzaFinded = comTCittadinanzaDao.findByCodMin(codComuneMin);
		if (!cittadinanzaFinded.isPresent()) {
			throw new NotFoundException("CittadinanzaByCodMin");
		} else {
			return ComonlMappers.CITTADINANZA.toModel(cittadinanzaFinded.get());
		}
	}

	public StatusStraniero getStatusStranieroByCodMin(String codStatusMin) {
		Optional<ComTStatusStraniero> statusStranieroFinded = comTStatusStranieroDao.findByCodMin(codStatusMin);
		if (!statusStranieroFinded.isPresent()) {
			throw new NotFoundException("StatusByCodMin");
		} else {
			return ComonlMappers.STATUS_STRANIERO.toModel(statusStranieroFinded.get());
		}
	}

	public Questura getQuesturaByCodMin(String codMin) {
		Optional<ComTQuestura> questuraFinded = comTQuesturaDao.findByCodMin(codMin);
		if (!questuraFinded.isPresent()) {
			throw new NotFoundException("QuesturaByCodMin");
		} else {
			return ComonlMappers.QUESTURA.toModel(questuraFinded.get());
		}
	}

	public LivelloStudio getLivelloStudioByCodMin(String codMin) {
		Optional<ComTLivelloStudio> questuraFinded = comTLivelloStudioDao.findByCodMin(codMin);
		if (!questuraFinded.isPresent()) {
			throw new NotFoundException("LivelloStudioByCodMin");
		} else {
			;
			return ComonlMappers.LIVELLO_STUDIO.toModel(questuraFinded.get());
		}
	}

	public MotivoPermesso getMotivoPermessoByCodMin(String codMin) {
		Optional<ComTMotivoPermesso> questuraFinded = comTMotivoPermessoDao.findByCodMin(codMin);
		if (!questuraFinded.isPresent()) {
			throw new NotFoundException("MotivoPermessoByCodMin");
		} else {
			return ComonlMappers.MOTIVO_PERMESSO.toModel(questuraFinded.get());
		}
	}

	public Personalizzazione getPersonalizzazioneByPv(String pv) {
		Optional<EntityPersonalizzazione> personalizzazioneFinded = entityPersonalizzazioneDao.findByPv(pv);
		if (!personalizzazioneFinded.isPresent()) {
			return null;
		} else {
			return ComonlMappers.PERSONALIZZAZIONE.toModel(personalizzazioneFinded.get());
		}
	}

	public Toponimo getToponimoById(String id) {
		Map<String,Toponimo> cache = cacheHelper.getCachedValue(CacheKey.TOPONIMO, new CacheSupplier<String,Toponimo>());
		Toponimo toponimo = cache.get(id);
		if (toponimo != null)
			return toponimo;
		
		Optional<ComTToponimo> ilTopo = comTToponimoDao.findById(id);
		if (!ilTopo.isPresent()) {
			throw new NotFoundException("getToponimoById");
		} else {
			toponimo = ComonlMappers.TOPONIMO.toModel(ilTopo.get());
			cache.put(id, toponimo);
			return toponimo;
		}
	}

	public Atecofin getAtecoByCodAtecoFin(String codAteco) {
		Optional<ComTAtecofin> atecoFinded = comTAtecofinDao.findByCodAteco(codAteco);
		if (!atecoFinded.isPresent()) {
			throw new NotFoundException("AtecoByCodAteco");
		} else {
			return ComonlMappers.ATECOFIN.toModel(atecoFinded.get());
		}
	}

	public Ccnl getCcnlByCodCcnl(String codCcnl) {
		Optional<ComTCcnl> ccnlFinded = comTCcnlDao.findByCodCcnl(codCcnl);
		if (!ccnlFinded.isPresent()) {
			throw new NotFoundException("CcnlByCodCcnl");
		} else {
			return ComonlMappers.CCNL.toModel(ccnlFinded.get());
		}
	}

	/////////////////////////////// transcodifiche per commax e import da spicom
	// restituisce la entity di transcodifica dato il codice ministeriale e la data
	/////////////////////////////// di riferimento
	
	public <T extends BaseDto<Long>> BaseDto<Long> getTfromMin(Class<T> transcodifica, String fieldValue,
			Date dataRiferimento) {

		if(transcodifica == EntePrevidenziale.class) {
			ComTEntePrevidenziale entity = (ComTEntePrevidenziale)comDComunicazioneDao.getTfromMin(ComTEntePrevidenziale.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.ENTE_PREVIDENZIALE.toModel(entity);
			
		} else if (transcodifica == Ccnl.class) {
			ComTCcnl entity = (ComTCcnl)comDComunicazioneDao.getTfromMin(ComTCcnl.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.CCNL.toModel(entity);

		} else if (transcodifica == StatiEsteri.class) {
			ComTStatiEsteri entity = null;
			if (dataRiferimento == null)
				entity = (ComTStatiEsteri)comDComunicazioneDao.getTfromMin(ComTStatiEsteri.class, fieldValue);
			else entity = (ComTStatiEsteri)comDComunicazioneDao.getTfromMin(ComTStatiEsteri.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.STATI_ESTERI.toModel(entity);

		} else if (transcodifica == Istat2001livello5.class) {
			ComTIstat2001livello5 entity = (ComTIstat2001livello5)comDComunicazioneDao.getTfromMin(ComTIstat2001livello5.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.ISTAT2001LIVELLO5.toModel(entity);

		} else if (transcodifica == TipoContratti.class) {
			ComTTipoContratti entity = (ComTTipoContratti)comDComunicazioneDao.getTfromMin(ComTTipoContratti.class, fieldValue);
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
			
			Map<String,Comune> cache = cacheHelper.getCachedValue(CacheKey.COMUNE, new CacheSupplier<String,Comune>());
			Comune comune = cache.get(fieldValue);
			if (comune != null)
				return comune;
			
			ComTComune entity = (ComTComune)comDComunicazioneDao.getTfromMin(ComTComune.class, fieldValue, dataRiferimento);
			if (entity != null) { 
				comune = ComonlMappers.COMUNE.toModel(entity);
				cache.put(fieldValue, comune);
				return comune;
			}
			return null;

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
			Map<String,Provincia> cache = cacheHelper.getCachedValue(CacheKey.PROVINCIA, new CacheSupplier<String,Provincia>());
			Provincia provincia = cache.get(fieldValue);
			if (provincia != null)
				return provincia;
			
			ComTProvincia entity = (ComTProvincia)comDComunicazioneDao.getTfromMin(ComTProvincia.class, fieldValue, dataRiferimento);
			if (entity != null) { 
				provincia = ComonlMappers.PROVINCIA.toModel(entity);
				cache.put(fieldValue, provincia);
				return provincia;
			}
			return null;
		
		} else if (transcodifica == Questura.class) {
			ComTQuestura entity = (ComTQuestura)comDComunicazioneDao.getTfromMin(ComTQuestura.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.QUESTURA.toModel(entity);

		} else if (transcodifica == Regione.class) {
			
			Map<String,Regione> cache = cacheHelper.getCachedValue(CacheKey.REGIONE, new CacheSupplier<String,Regione>());
			Regione regione = cache.get(fieldValue);
			if (regione != null)
				return regione;
			
			ComTRegione entity = (ComTRegione)comDComunicazioneDao.getTfromMin(ComTRegione.class, fieldValue, dataRiferimento);
			if (entity != null) { 
				regione = ComonlMappers.REGIONE.toModel(entity);
				cache.put(fieldValue, regione);
				return regione;
			}
			return null;

		} else if (transcodifica == StatusStraniero.class) {
			ComTStatusStraniero entity = (ComTStatusStraniero)comDComunicazioneDao.getTfromMin(ComTStatusStraniero.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.STATUS_STRANIERO.toModel(entity);

		} else if (transcodifica == TipoComunicazioneTu.class) {
			ComTTipoComunicazioneTu entity = (ComTTipoComunicazioneTu)comDComunicazioneDao.getTfromMin(ComTTipoComunicazioneTu.class, fieldValue, dataRiferimento);
			return entity == null? null: ComonlMappers.TIPO_COMUNICAZIONE_TU.toModel(entity);

		} else if (transcodifica == TipoEntePromTirocinio.class) {
			ComTTipoEntePromTirocinio entity = (ComTTipoEntePromTirocinio)comDComunicazioneDao.getTfromMin(ComTTipoEntePromTirocinio.class, fieldValue, dataRiferimento);
			if (entity != null && entity.getDtFine() != null ) {
				entity = (ComTTipoEntePromTirocinio)comDComunicazioneDao.getTfromMin(ComTTipoEntePromTirocinio.class, fieldValue , null);
			}
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
	
	public String getFlgAccentramento(String codiceFiscaleSoggetto) {
		Optional<ComDSoggettoAbilitato> flgFinded = comDSoggettoAbilitatoDao.findByCfSoggetto(codiceFiscaleSoggetto);
		if (!flgFinded.isPresent()) {
			throw new NotFoundException("getFlgAccentramento");
		} else {
			SoggettoAbilitato soggetto = ComonlMappers.SOGGETTO_ABILITATO.toModel(flgFinded.get());
			return soggetto.getFlgAccentramento();
		}
	} 
	public String getAnagraficaAziAccent(String codiceFiscaleDatoreLavoro) {
		Optional<ComDAnagraficaAziAccent> anaAziAcc = comDAnagraficaAziAccentDao.findByCodiceFiscale(codiceFiscaleDatoreLavoro);
		if (!anaAziAcc.isPresent()) {
			throw new NotFoundException("getAnagraficaAziAccent");
		} else {
			AnagraficaAziAccent anagrafica = ComonlMappers.ANAGRAFICA_AZI_ACCENT.toModel(anaAziAcc.get());
			return anagrafica.getCodiceFiscale();
		}
	} 
	public Long getStatoBySiglaNazione(String siglaNazione) {
		Optional<ComTStatiEsteri> stati = comTStatiEsteriDao.findBySiglaNazione(siglaNazione);
		if (!stati.isPresent()) {
			throw new NotFoundException("getStatoBySiglaNazione");
		} else {
			StatiEsteri statiEsteri = ComonlMappers.STATI_ESTERI.toModel(stati.get());
			return statiEsteri.getId();
		}
	} 
	
	public List<TipoContrAmmPerCom> getDatiTipoContrattoAmmessoPerCom () {
		List<ComTTipoContrAmmPerCom> contrattiAmmessi = comTTipoContrAmmPerComDao.findAll();
		return ComonlMappers.TIPO_CONTR_AMM_PER_COM.toModels(contrattiAmmessi);
	}

	public Long determinaIdLivelloRetribuzioneByCodCcnlCodLivello(String codCcnl, String codLivello, Date dataRiferimento) {
		Ccnl ccnl = (Ccnl) getTfromMin(Ccnl.class, codCcnl, dataRiferimento);
		if (ccnl != null && ccnl.getId() != null) {
			ComTLivelloRetribuzione retr = comTLivelloRetribuzioneDao.findByIdCcnlAndCodLivello(ccnl.getId(), codLivello);
			if (retr != null) {
				return retr.getId();
			}
		}
		return null;
	}
	
	public LivelloRetribuzione getLivelloRetribuzioneById(Long idLivello) {
		Optional<LivelloRetribuzione> entities = comTLivelloRetribuzioneDao.findOne(idLivello).map(ComonlMappers.LIVELLO_RETRIBUZIONE::toModel);
		if (entities.isPresent()) {
			return entities.get();
		}
		return null;
	}
	
	
	public Long nextSequenceVal(String sequenceName) {
		return generalSequenceDao.nextSequence(sequenceName);
	}
	
	public Provincia getProvinciaById(Long idProvincia) {
		
		Map<Long,Provincia> cache = cacheHelper.getCachedValue(CacheKey.PROVINCIA_ID, new CacheSupplier<Long,Provincia>());
		
		Provincia provincia = cache.get(idProvincia);
		if (provincia != null)
			return provincia;
		
		Optional<Provincia> entities = comTProvinciaDao.findOne(idProvincia).map(ComonlMappers.PROVINCIA::toModel);
		if (entities.isPresent()) {
			provincia = entities.get();
			cache.put(idProvincia, provincia);
			return provincia;
		}
		return null;
	}
	public Provincia getProvinciaByDsTarga(String dsTarga) {
		Map<String,Provincia> cache = cacheHelper.getCachedValue(CacheKey.PROVINCIA_TARGA, new CacheSupplier<String,Provincia>());
		
		Provincia provincia = cache.get(dsTarga);
		if (provincia != null)
			return provincia;
		
		ComTProvincia entity = comTProvinciaDao.findByDsTarga(dsTarga);
		provincia = ComonlMappers.PROVINCIA.toModel(entity);
		if (provincia != null)
			cache.put(dsTarga, provincia);
		return provincia;
	}
	
	public Long getProtocolloDaSequence(String targaProvincia) {
		return comTProvinciaDao.getProtocolloDaSequence(targaProvincia);
	}

}
