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

import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaTipoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LegaleRapprDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoLavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.TutoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.EseguiFaseElaborazioneMemorizzazioneEControlliService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.EseguiFaseInvioASistemiEsterniService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.EseguiFaseInvioEsitoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostInviaComunicazioneService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostRecuperoComunicazioniService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.RitrasmettiComunicazioniService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.UploadComunicazioniService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.EseguiFaseElaborazioneMemorizzazioneEControlliRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.EseguiFaseInvioASistemiEsterniRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.EseguiFaseInvioEsitoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostInviaComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostRecuperoComunicazioniRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.RitrasmettiComunicazioniRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.UploadComunicazioniRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostInviaComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRecuperoComunicazioniResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.RitrasmettiComunicazioniResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.UploadComunicazioniResponse;
import it.csi.comonl.comonlweb.ejb.util.ComonlThreadLocalContainer;
import it.csi.comonl.comonlweb.lib.dto.FileHolder;
import it.csi.comonl.comonlweb.lib.dto.Utente;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRecuperoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;


@Stateless
@TransactionManagement(value=TransactionManagementType.BEAN)
public class ComunicazioneStatelessFacade extends BaseFacade {

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
	private RapportoLavoratoreDad rapportoLavoratoreDad;
	@Inject
	private SedeLavoroDad sedeLavoroDad;
	@Inject
	private CommonDad commonDad;
	@Inject
	private LegaleRapprDad legaleRapprDad;
	@Inject
	private AnagraficaDelegatoDad anagraficaDelegatoDad;
	@Inject
	private TutoreDad tutoreDad;
	
	
	/**
	 * Invia Comunicazione
	 *
	 * @param id              the id to be cancelled
	 * @param comunicazione   the comunicazione to save
	 * @return the comunicazione
	 */
	@Lock(LockType.WRITE)
	public PostInviaComunicazioneResponse inviaComunicazione(WrapperComunicazione wrapperComunicazione) {
		return executeService(new PostInviaComunicazioneRequest(wrapperComunicazione),
			new PostInviaComunicazioneService(sessionContext, configurationHelper,  comunicazioneDad,
					 decodificaTipoDad,  decodificaDad,  commonDad,
					 datoreDad, legaleRapprDad, sedeLavoroDad, rapportoDad, lavoratoreDad,
					 rapportoLavoratoreDad,tutoreDad, mailHelper,anagraficaDelegatoDad));
	}
	
	@Lock(LockType.WRITE)
	public UploadComunicazioniResponse uploadComunicazioni(FileHolder fileHolder, Ruolo ruolo) {
		return executeService(new UploadComunicazioniRequest(fileHolder,ruolo),
				new UploadComunicazioniService(sessionContext, configurationHelper, comunicazioneDad, anagraficaDelegatoDad, commonDad, decodificaDad));
	}
	
	
	@Lock(LockType.WRITE)	
	public UploadComunicazioniResponse eseguiFaseInvioASistemiEsterni(Ruolo ruolo, UplDocumenti uploadDocumenti,ComunicazioneDad comunicazioneDad,
			AnagraficaDelegatoDad anagraficaDelegatoDad,  CommonDad commonDad, DecodificaDad decodificaDad) {
		return executeService(new EseguiFaseInvioASistemiEsterniRequest(ruolo, uploadDocumenti.getId(), comunicazioneDad, anagraficaDelegatoDad,  commonDad, decodificaDad),
				new EseguiFaseInvioASistemiEsterniService(sessionContext, configurationHelper, ruolo, uploadDocumenti, comunicazioneDad, anagraficaDelegatoDad,  commonDad, decodificaDad, decodificaTipoDad));
	}

	@Lock(LockType.WRITE)
	public PostRecuperoComunicazioniResponse postRecuperoComunicazioni(FormRecuperoComunicazione recuperoComunicazione) {
		return executeService(new PostRecuperoComunicazioniRequest(recuperoComunicazione),
				new PostRecuperoComunicazioniService(sessionContext, configurationHelper,  comunicazioneDad,
				 decodificaTipoDad,  decodificaDad,  commonDad,
				 datoreDad, legaleRapprDad, sedeLavoroDad, rapportoDad, lavoratoreDad,
				 rapportoLavoratoreDad,tutoreDad,mailHelper,anagraficaDelegatoDad));
	}
	
	@Lock(LockType.WRITE)	
	public UploadComunicazioniResponse eseguiFaseElaborazioneMemorizzazioneEControlli(Ruolo ruolo, UplDocumenti uploadDocumenti,ComunicazioneDad comunicazioneDad,
			AnagraficaDelegatoDad anagraficaDelegatoDad,  CommonDad commonDad, DecodificaDad decodificaDad) {
		return executeService(new EseguiFaseElaborazioneMemorizzazioneEControlliRequest(ruolo, uploadDocumenti.getId(), comunicazioneDad, anagraficaDelegatoDad,  commonDad, decodificaDad),
				new EseguiFaseElaborazioneMemorizzazioneEControlliService(sessionContext, configurationHelper, ruolo, uploadDocumenti, comunicazioneDad, anagraficaDelegatoDad,  commonDad, decodificaDad,
						datoreDad,
						legaleRapprDad,
						sedeLavoroDad,
						rapportoDad,
						lavoratoreDad,
						rapportoLavoratoreDad,
						tutoreDad));
	}
	
	@Lock(LockType.WRITE)
	public UploadComunicazioniResponse eseguiFaseInvioEsito(UplDocumenti uploadDocumenti,ComunicazioneDad comunicazioneDad, CommonDad commonDad) {
		return executeService(new EseguiFaseInvioEsitoRequest(uploadDocumenti,comunicazioneDad, commonDad),
				new EseguiFaseInvioEsitoService(sessionContext, configurationHelper, uploadDocumenti,comunicazioneDad, commonDad));
	}
	
	@Lock(LockType.WRITE)
	public void eleboraProcessoSynch(Ruolo ruolo, UplDocumenti uploadDocumenti,ComunicazioneDad comunicazioneDad, 
			AnagraficaDelegatoDad anagraficaDelegatoDad,  CommonDad commonDad, DecodificaDad decodificaDad) throws Exception {

		Utente utente = new Utente();
		utente.setCodiceFiscale(ruolo.getCodiceFiscaleUtente());
		ComonlThreadLocalContainer.UTENTE_CONNESSO.set(utente); 

		// Stato R : comunicazione validata e 'registrata' sul DB
		eseguiFaseElaborazioneMemorizzazioneEControlli(ruolo, uploadDocumenti, comunicazioneDad, anagraficaDelegatoDad,  commonDad, decodificaDad);

		// Stato I : comunicazione protocollata provincialmente ed inviata a SPICOM
		eseguiFaseInvioASistemiEsterni(ruolo, uploadDocumenti, comunicazioneDad, anagraficaDelegatoDad, commonDad, decodificaDad);

		// Stato N : notifica tramite email dell'esito dell'elaborazione
		eseguiFaseInvioEsito(uploadDocumenti, comunicazioneDad, commonDad);
	}
	
	@Asynchronous
	@Lock(LockType.WRITE)
	public void eleboraProcesso(Ruolo ruolo, UplDocumenti uploadDocumenti,ComunicazioneDad comunicazioneDad, 
			AnagraficaDelegatoDad anagraficaDelegatoDad,  CommonDad commonDad, DecodificaDad decodificaDad) throws Exception {
		
		eleboraProcessoSynch(ruolo, uploadDocumenti, comunicazioneDad, 
				anagraficaDelegatoDad, commonDad, decodificaDad);
	}
	
	//////////////////////// RITRASMETTI COMUNICAZIONI COMMAX
	@Lock(LockType.WRITE)
	public RitrasmettiComunicazioniResponse ritrasmettiComunicazioni() {
		return executeService(new RitrasmettiComunicazioniRequest(),
				new RitrasmettiComunicazioniService(this, sessionContext, configurationHelper, comunicazioneDad, anagraficaDelegatoDad, commonDad, decodificaDad));
	}
	
	
}
