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

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreSedeDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaTipoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LegaleRapprDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoLavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.TutoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.CheckDatiEssenzialiService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.ChkRiepilogoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.GetComunicazioneByIdService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.GetComunicazioneUrgenzaHolderByIdService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostAnnullaComunicazioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostAnnullaComunicazioneVardatoreService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostChkAziendaUtilizzatriceService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostChkDatiGeneraliService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostChkDatiGeneraliVardatoriService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostChkImpresaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostChkLavoratoreService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostChkLavoratoriComunicazioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostChkRapportoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostChkTutoreService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostComunicazioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostComunicazioneUrgenzaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostComunicazioneVardatoreService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostRapportoMissioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostRettificaComunicazioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostRettificaComunicazioneVardatoreService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostRicercaComunicazioniService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostRicercaComunicazioniVardatoriService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostRicercaVardatoriService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostTutoreService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostVerificaDatoreService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostchkDatiAziendaPrecedenteService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostchkSediLavoroService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutCancellaComunicazioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutComunicazioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutComunicazioneUrgenzaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutComunicazioneVardatoreService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutDatiCessazioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutDatiProrogaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutDatiTrasferimentoDistaccoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutDatiTrasformazioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutRapportoMissioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutTutoreService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.RiceviComunicazioneDaSpicomService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.StampaComunicazioneByIdService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.StampaRicercaComunicazioniService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.StampaRicercaComunicazioniVardatoriService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.CheckDatiEssenzialiRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.ChkRiepilogoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetComunicazioneByIdOperatoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetComunicazioneByIdRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkAziendaUtilizzatriceRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkDatiAziendaPrecedenteRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkDatiGeneraliRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkImpresaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkLavoratoriComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkRapportoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkSediLavoroRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkTutoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostComunicazioneUrgenzaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostLavoratoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostRapportoMissioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostRicercaComunicazioniRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostRicercaVardatoriRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostStampaComunicazioniRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostTutoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostVerificaDatoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutCancellaComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutDatiCessazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutDatiProrogaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutDatiTrasferimentoDistaccoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutDatiTrasformazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutRapportoMissioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutTutoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.RiceviComunicazioneDaSpicomRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.CheckDatiEssenzialiResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.ChkRiepilogoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetComunicazioneByIdResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetComunicazioneUrgHolderByIdResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PdfResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostComunicazioneUrgenzaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostComunicazioneVardatoreResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostLavoratoreResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRapportoMissioneResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRicercaComunicazioniResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRicercaVardatoriResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostTutoreResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostVerificaDatoreResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutCancellaComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutComunicazioneVardatoreResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutDatiCessazioneResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutDatiProrogaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutDatiTrasferimentoDistaccoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutDatiTrasformazioneResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutRapportoMissioneResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutTutoreResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.RiceviComunicazioneDaSpicomResponse;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperRapporto;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;
import it.csi.spicom.util.serialization.ObjectSerializer;

/**
 * Facade for the /comunicazione path
 */
@Stateless
public class ComunicazioneFacade extends BaseFacade {

	// Injection point
	@Inject
	private ComunicazioneDad comunicazioneDad;
	@Inject
	private DecodificaDad decodificaDad;
	@Inject
	private DecodificaTipoDad decodificaTipoDad;
	@Inject
	private DatoreDad datoreDad;
	@Inject
	private LavoratoreDad lavoratoreDad;
	@Inject
	private RapportoDad rapportoDad;
	@Inject
	private ComunicazioneDatoreDad comunicazioneDatoreDad;
	@Inject
	private RapportoLavoratoreDad rapportoLavoratoreDad;
	@Inject
	private DatoreSedeDad datoreSedeDad;
	@Inject
	private SedeLavoroDad sedeLavoroDad;
	@Inject
	private CommonDad commonDad;
	@Inject
	private LegaleRapprDad legaleRapprDad;
	@Inject
	private TutoreDad tutoreDad;
	@Inject
	private ControlliDad controlliDad;

	@Inject
	private AnagraficaDelegatoDad anagraficaDelegatoDad;

	/*
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public PostRicercaProspettoResponse getRicercaProspetti(Integer page, Integer limit, String sort, String direction,
			RicercaProspetto ricercaProspetto) {
		return executeService(new PostRicercaProspettoRequest(page, limit, sort, direction, ricercaProspetto),
				new PostRicercaProspettoService(configurationHelper, prospettoDad, commonDad, riepilogoNazionaleDad));
	}*/


	@TransactionAttribute(TransactionAttributeType.NEVER)
	public PdfResponse stampaRicercaComunicazioni(FormRicercaComunicazione ricercaComunicazione) {
		return executeService(new PostStampaComunicazioniRequest(ricercaComunicazione),
				new StampaRicercaComunicazioniService(configurationHelper, comunicazioneDad,commonDad));
	}
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public PdfResponse stampaRicercaComunicazioniVardatori(FormRicercaComunicazione ricercaComunicazione) {
		return executeService(new PostStampaComunicazioniRequest(ricercaComunicazione),
				new StampaRicercaComunicazioniVardatoriService(configurationHelper, comunicazioneDad,commonDad));
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public PostRicercaComunicazioniResponse postRicercaComunicazioni(Integer page, Integer limit, String sort, String direction,
			FormRicercaComunicazione ricercaComunicazione) {
		return executeService(new PostRicercaComunicazioniRequest(page, limit, sort, direction, ricercaComunicazione),
				new PostRicercaComunicazioniService(configurationHelper, comunicazioneDad,commonDad));
	}

	

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public PostRicercaVardatoriResponse postRicercaVardatori(Integer page, Integer limit, String sort, String direction,
			FormRicercaComunicazione ricercaComunicazione) {
		return executeService(new PostRicercaVardatoriRequest(page, limit, sort, direction, ricercaComunicazione),
				new PostRicercaVardatoriService(configurationHelper, comunicazioneDad,commonDad));
	}


	@TransactionAttribute(TransactionAttributeType.NEVER)
	public PostRicercaComunicazioniResponse postRicercaComunicazioniVardatori(Integer page, Integer limit, String sort, String direction,
			FormRicercaComunicazione ricercaComunicazione) {
		return executeService(new PostRicercaComunicazioniRequest(page, limit, sort, direction, ricercaComunicazione),
				new PostRicercaComunicazioniVardatoriService(configurationHelper, comunicazioneDad,commonDad));
	}

	/**
	 * Posts an Comunicazione
	 *
	 * @param idComunicazione
	 * @return the Comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public GetComunicazioneByIdResponse getComunicazioneById(Long idComunicazione) {
		return executeService(new GetComunicazioneByIdRequest(idComunicazione),
				new GetComunicazioneByIdService(configurationHelper, mailHelper, comunicazioneDad));
	}

	/**
	 * Posts an Comunicazione d'urgenza
	 *
	 * @param prospetto
	 * @return the prospetto
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostComunicazioneUrgenzaResponse postComunicazioneUrgenza(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostComunicazioneUrgenzaRequest(wrapperComunicazione),
				new PostComunicazioneUrgenzaService(configurationHelper,comunicazioneDad, decodificaDad, datoreDad,
						lavoratoreDad, rapportoDad, sedeLavoroDad, controlliDad ));
	}



	/**
	 * Posts an Comunicazione d'urgenza
	 *
	 * @param prospetto
	 * @return the prospetto
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostComunicazioneUrgenzaResponse putComunicazioneUrgenza(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostComunicazioneUrgenzaRequest(wrapperComunicazione),
				new PutComunicazioneUrgenzaService(configurationHelper,comunicazioneDad, decodificaDad, datoreDad,
						lavoratoreDad, rapportoDad, sedeLavoroDad, controlliDad ));
	}


	/**
	 * Get a ComunicazioneUrgenzaHolder
	 *
	 * @param idComunicazione
	 * @return the Comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public GetComunicazioneUrgHolderByIdResponse getComunicazioneUrgHolderById(Long idComunicazione) {
		return executeService(new GetComunicazioneByIdRequest(idComunicazione),
				new GetComunicazioneUrgenzaHolderByIdService(configurationHelper, comunicazioneDad, rapportoDad,
						rapportoLavoratoreDad, comunicazioneDatoreDad, datoreSedeDad));
	}

	/**
	 * Posts an Prospetto
	 *
	 * @param prospetto
	 * @return the prospetto
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostVerificaDatoreResponse verificaDatore(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostVerificaDatoreRequest(wrapperComunicazione),
				new PostVerificaDatoreService(configurationHelper,comunicazioneDad, decodificaDad, controlliDad ));
	}

	/**
	 * Stampa Comunicazione
	 *
	 * @param idComunicazione
	 * @return the Pdf
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PdfResponse stampaComunicazioneById(Long id, Boolean operatoreProvinciale) {
		return executeService(new GetComunicazioneByIdOperatoreRequest(id, operatoreProvinciale),
				new StampaComunicazioneByIdService(configurationHelper, comunicazioneDad, decodificaTipoDad,anagraficaDelegatoDad));
	}

	/**
	 * Posts a Comunicazione
	 *
	 * @param comunicazione
	 * @return the comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostComunicazioneResponse postComunicazione(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostComunicazioneRequest(wrapperComunicazione),
				new PostComunicazioneService(configurationHelper, comunicazioneDad, datoreDad, sedeLavoroDad, legaleRapprDad,rapportoDad, lavoratoreDad, controlliDad, decodificaDad, rapportoLavoratoreDad,tutoreDad));
	}
	/**
	 * Posts a Comunicazione
	 *
	 * @param comunicazione
	 * @return the comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostComunicazioneVardatoreResponse postComunicazioneVardatore(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostComunicazioneRequest(wrapperComunicazione),
				new PostComunicazioneVardatoreService(configurationHelper, comunicazioneDad, datoreDad, sedeLavoroDad, legaleRapprDad,rapportoDad, lavoratoreDad, controlliDad, decodificaDad));
	}
	/**
	 * Puts a Comunicazione
	 *
	 * @param comunicazione
	 * @return the comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutComunicazioneResponse putComunicazione(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PutComunicazioneRequest(wrapperComunicazione),
				new PutComunicazioneService(configurationHelper, comunicazioneDad, datoreDad, sedeLavoroDad, legaleRapprDad,rapportoDad, lavoratoreDad,tutoreDad, controlliDad, decodificaDad));
	}


	/**
	 * Puts a Comunicazione vardatore
	 *
	 * @param comunicazione
	 * @return the comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutComunicazioneVardatoreResponse putComunicazioneVardatore(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PutComunicazioneRequest(wrapperComunicazione),
				new PutComunicazioneVardatoreService(configurationHelper, comunicazioneDad, datoreDad, sedeLavoroDad, legaleRapprDad,rapportoDad, lavoratoreDad,tutoreDad, controlliDad, decodificaDad));
	}


	/**
	 * Cancel a Comunicazione
	 *
	 * @param id              the id to be cancelled
	 * @param comunicazione   the comunicazione to save
	 * @return the comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostComunicazioneResponse annullaComunicazione(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostComunicazioneRequest(wrapperComunicazione),
				new PostAnnullaComunicazioneService(configurationHelper, comunicazioneDad, datoreDad, sedeLavoroDad, legaleRapprDad,rapportoDad, lavoratoreDad, controlliDad, decodificaDad,rapportoLavoratoreDad,tutoreDad));
	}


	/**
	 * Cancel a Comunicazione
	 *
	 * @param id              the id to be cancelled
	 * @param comunicazione   the comunicazione to save
	 * @return the comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostComunicazioneVardatoreResponse annullaComunicazioneVardatore(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostComunicazioneRequest(wrapperComunicazione),
				new PostAnnullaComunicazioneVardatoreService(configurationHelper, comunicazioneDad, datoreDad, sedeLavoroDad, legaleRapprDad,rapportoDad, lavoratoreDad, controlliDad, decodificaDad));
	}


	/**
	 * Rettifica una Comunicazione
	 *
	 * @param comunicazione
	 * @return the comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostComunicazioneResponse rettificaComunicazione(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostComunicazioneRequest(wrapperComunicazione),
				new PostRettificaComunicazioneService(configurationHelper, comunicazioneDad, datoreDad, sedeLavoroDad, legaleRapprDad,rapportoDad, lavoratoreDad, controlliDad, decodificaDad,rapportoLavoratoreDad,tutoreDad));
	}



	/**
	 * Rettifica una Comunicazione
	 *
	 * @param comunicazione
	 * @return the comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostComunicazioneVardatoreResponse rettificaComunicazioneVardatore(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostComunicazioneRequest(wrapperComunicazione),
				new PostRettificaComunicazioneVardatoreService(configurationHelper, comunicazioneDad, datoreDad, sedeLavoroDad, legaleRapprDad,rapportoDad, lavoratoreDad, controlliDad, decodificaDad));
	}


	/**
	 * Save a Tutore
	 *
	 * @param comunicazione
	 * @return the tutore
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostTutoreResponse postTutore(Comunicazione comunicazione) {
		return executeService(new PostTutoreRequest(comunicazione),
				new PostTutoreService(configurationHelper, comunicazioneDad, rapportoDad, tutoreDad, controlliDad));
	}
	/**
	 * Put a Tutore
	 *
	 * @param tutore the tutore to update
	 * @return the tutore
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutTutoreResponse putTutore(Comunicazione comunicazione) {
		return executeService(new PutTutoreRequest(comunicazione),
				new PutTutoreService(configurationHelper, comunicazioneDad, tutoreDad, controlliDad));
	}
	/**
	 * post a Rapporto Missione
	 *
	 * @param rapporto the rapporto to save
	 * @return the rapporto
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostRapportoMissioneResponse postRapportoMissione(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostRapportoMissioneRequest(wrapperComunicazione),
				new PostRapportoMissioneService(configurationHelper, comunicazioneDad, rapportoDad, rapportoLavoratoreDad, controlliDad));
	}
	/**
	 * Put a Rapporto
	 *
	 * @param rapporto the rapporto to update
	 * @return the rapporto
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutRapportoMissioneResponse putRapportoMissione(WrapperRapporto wrapperRapporto) {
		return executeService(new PutRapportoMissioneRequest(wrapperRapporto),
				new PutRapportoMissioneService(configurationHelper, comunicazioneDad, rapportoDad, controlliDad));
	}
	/**
	 * Put dati Proroga per il rapporto
	 *
	 * @param comunicazione the comunicazione to update
	 * @return the rapporto
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutDatiProrogaResponse putDatiProroga(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PutDatiProrogaRequest(wrapperComunicazione),
				new PutDatiProrogaService(configurationHelper,comunicazioneDad, rapportoDad, datoreDad));
	}

	/**
	 * Put dati Trasformazione per il rapporto
	 *
	 * @param comunicazione the comunicazione to update
	 * @return the comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutDatiTrasformazioneResponse putDatiTrasformazione(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PutDatiTrasformazioneRequest(wrapperComunicazione),
				new PutDatiTrasformazioneService(configurationHelper,comunicazioneDad, rapportoDad, controlliDad));
	}

	/**
	 * Put dati cessazione per il rapporto
	 *
	 * @param comunicazione the comunicazione to update
	 * @return the comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutDatiCessazioneResponse putDatiCessazione(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PutDatiCessazioneRequest(wrapperComunicazione),
				new PutDatiCessazioneService(configurationHelper,comunicazioneDad, rapportoDad, controlliDad));
	}

	/**
	 * Put dati trasferimento e distacco
	 *
	 * @param comunicazione the comunicazione to update
	 * @return the comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutDatiTrasferimentoDistaccoResponse putDatiTrasferimentoDistacco(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PutDatiTrasferimentoDistaccoRequest(wrapperComunicazione),
				new PutDatiTrasferimentoDistaccoService(configurationHelper,comunicazioneDad, rapportoDad, datoreDad, sedeLavoroDad, controlliDad, decodificaDad));
	}
	/**
	 * Esegue i controlli realitivi ai dati generali della comunicazione
	 *
	 * @param comunicazione
	 * @return la comunicazione o degli errori
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostComunicazioneResponse chkDatiGenerali(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostChkDatiGeneraliRequest(wrapperComunicazione),
				new PostChkDatiGeneraliService(configurationHelper,comunicazioneDad,decodificaDad));
	}

	/**
	 * Esegue i controlli realitivi ai dati generali di una comunicazione di vardatori
	 *
	 * @param comunicazione
	 * @return la comunicazione o degli errori
	 */

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostComunicazioneResponse chkDatiGeneraliVardatori(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostChkDatiGeneraliRequest(wrapperComunicazione),
				new PostChkDatiGeneraliVardatoriService(configurationHelper,comunicazioneDad, decodificaDad));
	}

	/**
	 * Esegue i controlli realitivi ai dati aziendali della comunicazione
	 *
	 * @param comunicazione
	 * @return la comunicazione o degli errori
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostComunicazioneResponse chkImpresa(WrapperComunicazione wrapperComunicazione) {
		// TODO costruire il service
		return executeService(new PostChkImpresaRequest(wrapperComunicazione),
				new PostChkImpresaService(configurationHelper, controlliDad, comunicazioneDad, decodificaDad));

	}



	/**
	 * Esegue i controlli realitivi ai dati aziendali della comunicazione
	 *
	 * @param comunicazione
	 * @return la comunicazione o degli errori
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostComunicazioneResponse chkDatiAziendaPrecedente(Comunicazione comunicazione) {
		return executeService(new PostChkDatiAziendaPrecedenteRequest(comunicazione),
				new PostchkDatiAziendaPrecedenteService(configurationHelper, controlliDad));

	}



	/**
	 * Esegue i controlli realitivi ai dati aziendali della comunicazione
	 *
	 * @param comunicazione
	 * @return la comunicazione o degli errori
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostComunicazioneResponse chkSediLavoro(Comunicazione comunicazione) {
		return executeService(new PostChkSediLavoroRequest(comunicazione),
				new PostchkSediLavoroService(configurationHelper, controlliDad, decodificaDad));

	}



	/**
	 * Esegue i controlli realitivi ai dati del lavoratore della comunicazione
	 *
	 * @param lavoratore
	 * @return la lavoratore o degli errori
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostLavoratoreResponse chkLavoratore(Lavoratore lavoratore) {
		return executeService(new PostLavoratoreRequest(lavoratore),
				new PostChkLavoratoreService(configurationHelper, controlliDad));

	}

	/**
	 * Esegue i controlli realitivi ai lavoratori della comunicazione
	 *
	 * @param comunicazione
	 * @return la comunicazione o degli errori
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostComunicazioneResponse chkLavoratoriComunicazione(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostChkLavoratoriComunicazioneRequest(wrapperComunicazione),
				new PostChkLavoratoriComunicazioneService(configurationHelper, comunicazioneDad, controlliDad));

	}

	/**
	 * Esegue i controlli realitivi al rapporto della comunicazione
	 *
	 * @param comunicazione
	 * @return la comunicazione o degli errori
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostComunicazioneResponse chkRapporto(Comunicazione comunicazione) {
		return executeService(new PostChkRapportoRequest(comunicazione),
				new PostChkRapportoService(configurationHelper, controlliDad));

	}
	/**
	 * Esegue i controlli realitivi all'azienda utilizzatrice
	 *
	 * @param comunicazione
	 * @return la comunicazione o degli errori
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostComunicazioneResponse chkAziendaUtilizzatrice(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostChkAziendaUtilizzatriceRequest(wrapperComunicazione),
				new PostChkAziendaUtilizzatriceService(configurationHelper, controlliDad,comunicazioneDad));

	}
	/**
	 * Esegue i controlli realitivi al tutore del rapporto della comunicazione
	 *
	 * @param comunicazione
	 * @return la comunicazione o degli errori
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostComunicazioneResponse chkTutore(Comunicazione comunicazione) {
		return executeService(new PostChkTutoreRequest(comunicazione),
				new PostChkTutoreService(configurationHelper, controlliDad));

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public RiceviComunicazioneDaSpicomResponse riceviComunicazioneDaSpicom(String comunicazioneSpicomXml) {
	    ComunicazioneTracciatoUnicoDTO comm = null;
		try {
		    comm = (ComunicazioneTracciatoUnicoDTO) ObjectSerializer.xmlToObject(comunicazioneSpicomXml);
		} catch (Exception e) {
		    RiceviComunicazioneDaSpicomResponse resp = new RiceviComunicazioneDaSpicomResponse();
		    resp.addApiError(new ApiError("PARAMITER_ERROR",e.getMessage()));
		}
		return executeService(new RiceviComunicazioneDaSpicomRequest(comm),
				new RiceviComunicazioneDaSpicomService(configurationHelper, comunicazioneDad, anagraficaDelegatoDad, commonDad, decodificaDad,  datoreDad,  legaleRapprDad,
						sedeLavoroDad,  rapportoDad,  lavoratoreDad,
						rapportoLavoratoreDad,tutoreDad));
	}

	/**
	 * Cancella una Comunicazione
	 *
	 * @param comunicazione
	 * @return the comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutCancellaComunicazioneResponse cancellaComunicazione(Long id) {
		return executeService(new PutCancellaComunicazioneRequest(id),
				new PutCancellaComunicazioneService(configurationHelper, comunicazioneDad));
	}
	
	/**
	 * 
	 *
	 * @param idComunicazione
	 * @return the Comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public ChkRiepilogoResponse chkRiepilogo(Long idComunicazione) {
		return executeService(new ChkRiepilogoRequest(idComunicazione),
				new ChkRiepilogoService(configurationHelper, comunicazioneDad));
	}
	
	/**
	 * 
	 *
	 * @param idComunicazione
	 * @return the Comunicazione
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public CheckDatiEssenzialiResponse checkDatiEssenziali(Comunicazione comunicazione, Ruolo ruolo) {
		return executeService(new CheckDatiEssenzialiRequest(comunicazione,ruolo),
				new CheckDatiEssenzialiService(configurationHelper, comunicazioneDad));
	}
}
