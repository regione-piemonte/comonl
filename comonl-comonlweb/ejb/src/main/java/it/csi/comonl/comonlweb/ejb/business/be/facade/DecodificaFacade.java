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
package it.csi.comonl.comonlweb.ejb.business.be.facade;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetCaricaPersonaPvService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetCategLavoratoreAssObblService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetCategTirocinanteService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetCessazionerlService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetCittadinanzaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetCpiService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetEntePrevidenzialeService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetGradoContrattualeService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetLivelloStudioService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetMotivoPermessoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetNaturaGiuridicaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetPersonalizzazioneByPvService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetProvinciaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetQuesturaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetStatoComunicazioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetStatoDelegaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetStatoEsteroService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTitoloSoggiornoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetToponimoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTrasformazionerlByTipoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetVariazioneSommByTipoSommAndTipoComService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.PostAtecofinDecodificaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.PostCcnlDecodificaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.PostComuneDecodificaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.PostLivelloRetribuzioneDecodificaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.PostQualificaDecodificaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.PostStatiEsteriDecodificaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetCaricaPersonaPvRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetCategLavoratoreAssObblRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetCategTirocinanteRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetCessazionerlRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetCittadinanzaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetCpiRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetEntePrevidenzialeRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetGradoContrattualeRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetLivelloStudioRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetMotivoPermessoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetNaturaGiuridicaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetPersonalizzazioneByPvRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetProvinciaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetQuesturaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetStatoComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetStatoDelegaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetStatoEsteroRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTitoloSoggiornoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetToponimoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTrasformazionerlByTipoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetVariazioneSommByTipoSommAndTipoComRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.PostAtecofinDecodificaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetCaricaPersonaPvResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetCategLavoratoreAssObblResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetCategTirocinanteResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetCessazionerlResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetCittadinanzaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetCpiResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetEntePrevidenzialeResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetGradoContrattualeResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetLivelloStudioResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetMotivoPermessoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetNaturaGiuridicaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetPersonalizzazioneByPvResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetProvinciaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetQuesturaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetStatoComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetStatoDelegaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetStatoEsteroResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTitoloSoggiornoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetToponimoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTrasformazionerlByTipoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetVariazioneSommByTipoSommAndTipoComResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.PostCcnlDecodificaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.PostComuneDecodificaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.PostDecodificaGenericaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.PostLivelloRetribuzioneDecodificaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.PostQualificaDecodificaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.PostStatiEsteriDecodificaRequest;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Facade for the /decodifica path
 */
@Stateless
public class DecodificaFacade extends BaseFacade {
	// Injection point
	@Inject
	private DecodificaDad decodificaDad;

	/**
	 * Gets the Province
	 * 
	 * @return the province
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetProvinciaResponse getProvincia(String codRegioneMin) {
		return executeService(new GetProvinciaRequest(codRegioneMin), new GetProvinciaService(configurationHelper, decodificaDad));
	}
	

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostDecodificaGenericaResponse postAtecofinDecodifica(DecodificaGenerica filtro) {
		return executeService(new PostAtecofinDecodificaRequest(filtro),
				new PostAtecofinDecodificaService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the Province
	 * 
	 * @return the province
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetStatoComunicazioneResponse getStatoComunicazione(Boolean flgRicerca) {
		return executeService(new GetStatoComunicazioneRequest(flgRicerca), new GetStatoComunicazioneService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the Categoria
	 * 
	 * @return the categoria
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetCategTirocinanteResponse getCategTirocinante() {
		return executeService(new GetCategTirocinanteRequest(), new GetCategTirocinanteService(configurationHelper, decodificaDad));
	}

	/**
	 * Gets the Cpi
	 * 
	 * @return the cpi
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetCpiResponse getCpi() {
		return executeService(new GetCpiRequest(), new GetCpiService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the EntePrevidenziale
	 * 
	 * @return the entePrevidenziale
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetEntePrevidenzialeResponse getEntePrevidenziale() {
		return executeService(new GetEntePrevidenzialeRequest(), new GetEntePrevidenzialeService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the CategLavoratoreAssObbl
	 * 
	 * @return the categLavoratoreAssObbl
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetCategLavoratoreAssObblResponse getCategLavoratoreAssObbl() {
		return executeService(new GetCategLavoratoreAssObblRequest(), new GetCategLavoratoreAssObblService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the Cittadinanza
	 * 
	 * @return the cittadinanza
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetCittadinanzaResponse getCittadinanza() {
		return executeService(new GetCittadinanzaRequest(), new GetCittadinanzaService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the TitoloSoggiorno
	 * 
	 * @return the titoloSoggiorno
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTitoloSoggiornoResponse getTitoloSoggiorno() {
		return executeService(new GetTitoloSoggiornoRequest(), new GetTitoloSoggiornoService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the MotivoPermesso
	 * 
	 * @return the motivoPermesso
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetMotivoPermessoResponse getMotivoPermesso() {
		return executeService(new GetMotivoPermessoRequest(), new GetMotivoPermessoService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the Questura
	 * 
	 * @return the questura
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetQuesturaResponse getQuestura() {
		return executeService(new GetQuesturaRequest(), new GetQuesturaService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the LivelloStudio
	 * 
	 * @return the LivelloStudio
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetLivelloStudioResponse getLivelloStudio() {
		return executeService(new GetLivelloStudioRequest(), new GetLivelloStudioService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the LivelloStudio
	 * 
	 * @return the LivelloStudio
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetNaturaGiuridicaResponse getNaturaGiuridica() {
		return executeService(new GetNaturaGiuridicaRequest(), new GetNaturaGiuridicaService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the StatoEstero
	 * 
	 * @return the StatoEstero
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetStatoEsteroResponse getStatoEstero() {
		return executeService(new GetStatoEsteroRequest(), new GetStatoEsteroService(configurationHelper, decodificaDad));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostDecodificaGenericaResponse postComuneDecodifica(DecodificaGenerica filtro) {
		return executeService(new PostComuneDecodificaRequest(filtro),
				new PostComuneDecodificaService(configurationHelper, decodificaDad));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostDecodificaGenericaResponse postStatiEsteriDecodifica(DecodificaGenerica filtro) {
		return executeService(new PostStatiEsteriDecodificaRequest(filtro),
				new PostStatiEsteriDecodificaService(configurationHelper, decodificaDad));
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostDecodificaGenericaResponse postCcnlDecodifica(DecodificaGenerica filtro) {
		return executeService(new PostCcnlDecodificaRequest(filtro),
				new PostCcnlDecodificaService(configurationHelper, decodificaDad));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostDecodificaGenericaResponse postQualificaDecodifica(DecodificaGenerica filtro) {
		return executeService(new PostQualificaDecodificaRequest(filtro),
				new PostQualificaDecodificaService(configurationHelper, decodificaDad));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostDecodificaGenericaResponse postLivelloRetribuzioneDecodifica(DecodificaGenerica filtro) {
		return executeService(new PostLivelloRetribuzioneDecodificaRequest(filtro),
				new PostLivelloRetribuzioneDecodificaService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the TitoloSoggiorno
	 * 
	 * @return the titoloSoggiorno
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetCaricaPersonaPvResponse getCaricaPersonaPv() {
		return executeService(new GetCaricaPersonaPvRequest(), new GetCaricaPersonaPvService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the Toponimo
	 * 
	 * @return the toponimo
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetToponimoResponse getToponimo() {
		return executeService(new GetToponimoRequest(), new GetToponimoService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the StatoDelega
	 * 
	 * @return the statoDelega
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetStatoDelegaResponse getStatoDelega() {
		return executeService(new GetStatoDelegaRequest(), new GetStatoDelegaService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the Trasformazionerl
	 * 
	 * @return the statoDelega
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTrasformazionerlByTipoResponse getTrasformazionerlByTipo(String tipo) {
		return executeService(new GetTrasformazionerlByTipoRequest(tipo), new GetTrasformazionerlByTipoService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the GradoContrattuale
	 * 
	 * @return the GradoContrattuale
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetGradoContrattualeResponse getGradoContrattuale() {
		return executeService(new GetGradoContrattualeRequest(), new GetGradoContrattualeService(configurationHelper, decodificaDad));
	}
	/**
	 * Gets the Cessazionerl
	 * 
	 * @return the Cessazionerl
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetCessazionerlResponse getCessazionerl() {
		return executeService(new GetCessazionerlRequest(), new GetCessazionerlService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the PersonalizzazioneByPv
	 * 
	 * @return the Personalizzazione
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Lock(LockType.READ)
	public GetPersonalizzazioneByPvResponse getPersonalizzazioneByPv(String pv) {
		return executeService(new GetPersonalizzazioneByPvRequest(pv), new GetPersonalizzazioneByPvService(configurationHelper, decodificaDad));
	}
	
	/**
	 * Gets the VariazioneSomm
	 * 
	 * @return the VariazioneSomm
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Lock(LockType.READ)
	public GetVariazioneSommByTipoSommAndTipoComResponse getVariazioneSommByTipoSommAndTipoCom(Long idTipoSomm, String idTipoComunicazione) {
		return executeService(new GetVariazioneSommByTipoSommAndTipoComRequest(idTipoComunicazione,idTipoSomm), new GetVariazioneSommByTipoSommAndTipoComService(configurationHelper, decodificaDad));
	}
}
