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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.SessionContext;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.facade.ComunicazioneStatelessFacade;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.RitrasmettiComunicazioniRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.RitrasmettiComunicazioniResponse;
import it.csi.comonl.comonlweb.ejb.util.ComonlThreadLocalContainer;
import it.csi.comonl.comonlweb.ejb.util.commax.manager.EleborazioneProcessiManager;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtil;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.Utente;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CommaxParametri;
import it.csi.comonl.comonlweb.lib.dto.error.MsgCommax;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class RitrasmettiComunicazioniService
		extends BaseComunicazioneService<RitrasmettiComunicazioniRequest, RitrasmettiComunicazioniResponse> {

	private AnagraficaDelegatoDad anagraficaDelegatoDad;
	private CommonDad commonDad;
	private DecodificaDad decodificaDad;
	private SessionContext sessionContext;
	private ComunicazioneStatelessFacade statelessFacade;

	public RitrasmettiComunicazioniService(ComunicazioneStatelessFacade statelessFacade, SessionContext  sessionContext, ConfigurationHelper configurationHelper,
			ComunicazioneDad comunicazioneDad, AnagraficaDelegatoDad anagraficaDelegatoDad, CommonDad commonDad,
			DecodificaDad decodificaDad) {
		super(configurationHelper, comunicazioneDad);
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
		this.commonDad = commonDad;
		this.decodificaDad = decodificaDad;
		this.sessionContext = sessionContext;
		this.statelessFacade = statelessFacade;
	}

	@Override
	protected void execute() {

		List<ApiError> apiErrors = new ArrayList<ApiError>();

		try {
			String minutiAttesaPrimaDiRitrasmettere = commonDad
					.getParametroCommaxById(ParametriConstants.MINUTI_ATTESA_PRIMA_DI_RITRASMETTERE)
					.getValoreParametro();

			List<UplDocumenti> listaUplDaRitrasmettere = comunicazioneDad.gestisciUploadNonCompletati(minutiAttesaPrimaDiRitrasmettere);
			if (listaUplDaRitrasmettere != null && !listaUplDaRitrasmettere.isEmpty()) {

				// aggiorna ora ultima ritrasmissione su tabella parametri

				CommaxParametri commaxPar = commonDad.getParametroCommaxById(ParametriConstants.RITRASMISSIONI_ORA_ULTIMA_RITRASMISSIONE);
				commaxPar.setValoreParametro(DateUtil.convertiDataInStringaConOrario(new Date()));
				sessionContext.getUserTransaction().begin();
				commonDad.updateUltimaRitrasmissione(commaxPar);
				sessionContext.getUserTransaction().commit();

				for (UplDocumenti uplSingolo : listaUplDaRitrasmettere) {
					Ruolo ruolo = new Ruolo();
					ruolo.setIlRuolo(uplSingolo.getDsRuoloOperatore());
					ruolo.setCodiceFiscaleUtente(uplSingolo.getMittenteCf());
					ruolo.setDsCognome(uplSingolo.getMittenteCognome());
					ruolo.setDsNome(uplSingolo.getMittenteNome());
					ruolo.setCodiceFiscaleAzienda(uplSingolo.getCfAzienda());
					if (ComonlConstants.CONSULENTE_RESPONSABILE_COMONL_DESC.equalsIgnoreCase(uplSingolo.getDsRuoloOperatore())) {
						ruolo.setConsulenteRespo(true);
					} else {
						ruolo.setConsulenteRespo(false);
					}
					
					// update data richiesta elaborazione
					uplSingolo.setDataElab(new Date());
					sessionContext.getUserTransaction().begin();

					comunicazioneDad.updateUploadDocumenti(uplSingolo);
					// elabora processo

					sessionContext.getUserTransaction().commit();
					
					eleboraProcesso(ruolo, uplSingolo, comunicazioneDad, anagraficaDelegatoDad, commonDad, decodificaDad);					
					
				}
			}

		} catch (Exception e) {
			try {
				sessionContext.getUserTransaction().rollback();;
			}catch (Exception ee) {}
			apiErrors.add(MsgCommax.COMXVAL0010.getError());
			response.setApiErrors(apiErrors);
			return;
		}
	}

	private void eleboraProcesso(Ruolo ruolo, UplDocumenti uploadDocumenti,ComunicazioneDad comunicazioneDad, 
			AnagraficaDelegatoDad anagraficaDelegatoDad,  CommonDad commonDad, DecodificaDad decodificaDad) throws Exception {

		Utente utente = new Utente();
		utente.setCodiceFiscale(ruolo.getCodiceFiscaleUtente());
		ComonlThreadLocalContainer.UTENTE_CONNESSO.set(utente); 	
		
		// Stato R : comunicazione validata e 'registrata' sul DB
		statelessFacade.eseguiFaseElaborazioneMemorizzazioneEControlli(ruolo, uploadDocumenti, comunicazioneDad, anagraficaDelegatoDad,  commonDad, decodificaDad);

		// Stato I : comunicazione protocollata provincialmente ed inviata a SPICOM
		statelessFacade.eseguiFaseInvioASistemiEsterni(ruolo, uploadDocumenti, comunicazioneDad, anagraficaDelegatoDad, commonDad, decodificaDad);

		// Stato N : notifica tramite email dell'esito dell'elaborazione
		statelessFacade.eseguiFaseInvioEsito(uploadDocumenti, comunicazioneDad, commonDad);
	}
}
