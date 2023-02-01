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

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaTipoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTipoAttoL68Service;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTipoComunicazioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTipoComunicazioneTuService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTipoContrattiByTracciatoAndTipoComunicazioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTipoContrattoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTipoEntePromTirocinioService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTipoOrarioService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTipoSoggettoAbilitatoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTipoStudioProfessionaleService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTipoTrasferimentoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.GetTipologiaTirocinioService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.PostTipoComunicazioneUnilavDecodificaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica.PostTipoComunicazioneUnisommDecodificaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoAttoL68Request;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoComunicazioneTuRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoContrattiByTracciatoAndTipoComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoContrattoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoEntePromTirocinioRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoOrarioRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoSoggettoAbilitatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoStudioProfessionaleRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoTrasferimentoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipologiaTirocinioRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.PostTipoComunicazioneUnilavDecodificaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.PostTipoComunicazioneUnisommDecodificaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoAttoL68Response;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoComunicazioneTuResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoContrattoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoEntePromTirocinioResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoOrarioResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoSoggettoAbilitatoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoStudioProfessionaleResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoTrasferimentoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipologiaTirocinioResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.PostDecodificaGenericaResponse;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
;

/**
 * Facade for the /decodifica path
 */
@Stateless
public class DecodificaTipoFacade extends BaseFacade {
	// Injection point
	@Inject
	private DecodificaTipoDad decodificaTipoDad;

	/**
	 * Gets the TipoComunicazione
	 * 
	 * @return the tipoComunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTipoComunicazioneResponse getTipoComunicazione() {
		return executeService(new GetTipoComunicazioneRequest(), new GetTipoComunicazioneService(configurationHelper, decodificaTipoDad));
	}
	
	/**
	 * Gets the TipoComunicazioneTu
	 * 
	 * @return the TipoComunicazioneTu
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTipoComunicazioneTuResponse getTipoComunicazioneTu(Boolean flgRicerca) {
		return executeService(new GetTipoComunicazioneTuRequest(flgRicerca), new GetTipoComunicazioneTuService(configurationHelper, decodificaTipoDad));
	}
	
	/**
	 * Gets the TipoSomministrazione TipoVariazioneSom as DecodificaGenerica for UNISOMM
	 * 
	 * @return the DecodificaGenerica
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public PostDecodificaGenericaResponse postTipoComunicazioneUnisommDecodifica(DecodificaGenerica filtro) {
		return executeService(new PostTipoComunicazioneUnisommDecodificaRequest(filtro), new PostTipoComunicazioneUnisommDecodificaService(configurationHelper, decodificaTipoDad));
	}
	
	

	/**
	 * Gets the TipoComunicazione as DecodificaGenerica for UNILAV
	 * 
	 * @return the DecodificaGenerica
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public PostDecodificaGenericaResponse postTipoComunicazioneUnilavDecodifica(DecodificaGenerica filtro) {
		return executeService(new PostTipoComunicazioneUnilavDecodificaRequest(filtro), new PostTipoComunicazioneUnilavDecodificaService(configurationHelper, decodificaTipoDad));
	}

	/**
	 * Gets the TipologiaTirocinio
	 * 
	 * @return the tipologiaTirocinio
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTipologiaTirocinioResponse getTipologiaTirocinio() {
		return executeService(new GetTipologiaTirocinioRequest(), new GetTipologiaTirocinioService(configurationHelper, decodificaTipoDad));
	}
	
	/**
	 * Gets the TipoContratto
	 * 
	 * @return the tipoloContratto
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTipoContrattoResponse getTipoContratti() {
		return executeService(new GetTipoContrattoRequest(), new GetTipoContrattoService(configurationHelper, decodificaTipoDad));
	}
	
	
	/**
	 * Gets the TipoContratto
	 * 
	 * @return the tipoContratto
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTipoContrattoResponse getTipoContrattiByTracciatoAndTipoComunicazione(String tipoTracciato, String tipoComunicazione) {
		return executeService(new GetTipoContrattiByTracciatoAndTipoComunicazioneRequest(tipoTracciato,tipoComunicazione), new GetTipoContrattiByTracciatoAndTipoComunicazioneService(configurationHelper, decodificaTipoDad));
	}
	
	/**
	 * Gets the TipoEntePromTirocinio
	 * 
	 * @return the tipoEntePromTirocinio
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTipoEntePromTirocinioResponse getTipoEntePromTirocinio() {
		return executeService(new GetTipoEntePromTirocinioRequest(), new GetTipoEntePromTirocinioService(configurationHelper, decodificaTipoDad));
	}
	
	/**
	 * Gets the TipoOrario
	 * 
	 * @return the tipoOrario
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTipoOrarioResponse getTipoOrario() {
		return executeService(new GetTipoOrarioRequest(), new GetTipoOrarioService(configurationHelper, decodificaTipoDad));
	}
	
	/**
	 * Gets the TipoOrario
	 * 
	 * @return the tipoOrario
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTipoAttoL68Response getTipoAttoL68() {
		return executeService(new GetTipoAttoL68Request(), new GetTipoAttoL68Service(configurationHelper, decodificaTipoDad));
	}
	
	/**
	 * Gets the TipoOrario
	 * 
	 * @return the tipoOrario
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTipoSoggettoAbilitatoResponse getTipoSoggettoAbilitato() {
		return executeService(new GetTipoSoggettoAbilitatoRequest(), new GetTipoSoggettoAbilitatoService(configurationHelper, decodificaTipoDad));
	}
	
	
	/**
	 * Gets the list TipoSoggettoAbilitato for tipo studio professionale
	 * 
	 * @return the list TipoSoggettoAbilitato
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTipoStudioProfessionaleResponse getTipoStudioProfessionale() {
		return executeService(new GetTipoStudioProfessionaleRequest(), new GetTipoStudioProfessionaleService(configurationHelper, decodificaTipoDad));
	}
	
	
	/**
	 * Gets the list TipoTrasferimento
	 * 
	 * @return the list TipoTrasferimento
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetTipoTrasferimentoResponse getTipoTrasferimento() {
		return executeService(new GetTipoTrasferimentoRequest(), new GetTipoTrasferimentoService(configurationHelper, decodificaTipoDad));
	}
	
}
