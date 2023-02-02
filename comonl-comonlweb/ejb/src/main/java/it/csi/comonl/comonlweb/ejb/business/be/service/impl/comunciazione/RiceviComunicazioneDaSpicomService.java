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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LegaleRapprDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoLavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.TutoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.RiceviComunicazioneDaSpicomRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.RiceviComunicazioneDaSpicomResponse;
import it.csi.comonl.comonlweb.ejb.entity.ComDImportErrore;
import it.csi.comonl.comonlweb.ejb.util.ComonlThreadLocalContainer;
import it.csi.comonl.comonlweb.ejb.util.commax.CtuSpicomToComunicazioneMapper;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.Utente;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoAcquisizione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoProvenienza;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlDateUtils;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;

public class RiceviComunicazioneDaSpicomService extends BaseComunicazioneService<RiceviComunicazioneDaSpicomRequest, RiceviComunicazioneDaSpicomResponse> {

	private AnagraficaDelegatoDad anagraficaDelegatoDad;
	private CommonDad commonDad;
	private DecodificaDad decodificaDad;
	private DatoreDad datoreDad;
	private LegaleRapprDad legaleRapprDad;
	private SedeLavoroDad sedeLavoroDad;
	private RapportoDad rapportoDad;
	private LavoratoreDad lavoratoreDad;
	private RapportoLavoratoreDad rapportoLavoratoreDad;
	private TutoreDad tutoreDad;

	protected final LogUtil log = new LogUtil(RiceviComunicazioneDaSpicomService.class);

	public RiceviComunicazioneDaSpicomService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, AnagraficaDelegatoDad anagraficaDelegatoDad,
			CommonDad commonDad, DecodificaDad decodificaDad,
			DatoreDad datoreDad, LegaleRapprDad legaleRapprDad, SedeLavoroDad sedeLavoroDad, RapportoDad rapportoDad, LavoratoreDad lavoratoreDad,
			RapportoLavoratoreDad rapportoLavoratoreDad, TutoreDad tutoreDad
			) {
		super(configurationHelper, comunicazioneDad);
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
		this.commonDad = commonDad;
		this.decodificaDad = decodificaDad;
		this.datoreDad = datoreDad;
		this.legaleRapprDad = legaleRapprDad;
		this.sedeLavoroDad = sedeLavoroDad;
		this.rapportoDad =  rapportoDad;
		this.lavoratoreDad = lavoratoreDad;
		this.rapportoLavoratoreDad = rapportoLavoratoreDad;
		this.tutoreDad = tutoreDad;
	}

	@Override
	protected void execute() {
		log.debug("[RiceviComunicazioneDaSpicomService::execute]","BEGIN");

		// ricerca la CO per codice comunicazione
		// se lo trova esce
		// altrimenti
		// cancella i record relativi alla trasmissione su COM_D_IMPORT_ERRORE
		// completa la CO con tutte le transcodifiche (da ministero a locale)
		// salva eventuali errori su COM_D_IMPORT_ERRORE
		// se ci sono errori esce
		// altrimenti
		// valida comunicazione come se fosse in bozza
		// esegue l'inserimento
		// cerca se ci sono comunicazioni con codice comunicazione precedente
		// cambia lo stato della comunicazione precedente
//
		Utente utente = getUtente();
		if (utente == null) {
			utente = new Utente();
			utente.setCodiceFiscale(ComonlConstants.UTENTE_IMPORT_COMUNICAZIONE_DA_SPICOM);
			ComonlThreadLocalContainer.UTENTE_CONNESSO.set(utente); //FIXME
		}

		ComunicazioneTracciatoUnicoDTO comunicazioneSpicom = request.getComunicazioneSpicom();
		if(comunicazioneSpicom.getDatiInvio().getCodiceComunicazione() == null || "".equalsIgnoreCase(comunicazioneSpicom.getDatiInvio().getCodiceComunicazione())) {
			log.error("[RiceviComunicazioneDaSpicomService::execute]"," ERROR: comunicazione senza codice regionale");
			response.addApiError(MsgComonl.COMIMP0002.getError());
			return;
		}

		FormRicercaComunicazione filter = new FormRicercaComunicazione();
		filter.setCodiceRegionale(comunicazioneSpicom.getDatiInvio().getCodiceComunicazione());

		List<Long> numComunicazione = comunicazioneDad.getIdComunicazioneByCodReg(comunicazioneSpicom.getDatiInvio().getCodiceComunicazione());
		if(numComunicazione.size() > 0) {
			log.info("[RiceviComunicazioneDaSpicomService::execute]"," INFO: comunicazione con codice = " + comunicazioneSpicom.getDatiInvio().getCodiceComunicazione() +" gia presente in Comonl");
			response.addApiError(MsgComonl.COMIMP0001.getError());
			return;
		}

		comunicazioneDad.deleteImportErrori(comunicazioneSpicom.getDatiInvio().getCodiceComunicazione());

		CtuSpicomToComunicazioneMapper mapper = new CtuSpicomToComunicazioneMapper(comunicazioneSpicom, decodificaDad, ComonlDateUtils.get1900());
		Comunicazione comunicazione = mapper.toComunicazioneComonl();

		List<ComDImportErrore> errors = mapper.getErrors();
		if(errors.size() > 0) {
			response.addApiError(MsgComonl.COMIMP0003.getError());

			for(ComDImportErrore error: errors) {
				comunicazioneDad.insertImportErrori(error);
			}
			return;
		}
		
		// controlla se la comunicazione è precedente di qualche altra
		List<Long> idComunicazioni = comunicazioneDad.getIdComunicazioneByCodRegPrec(comunicazione.getCodiceComunicazioneReg());
		Long idComunicazioneSuccessiva = null;
		if(idComunicazioni.size() > 1) {
			response.addApiError(MsgComonl.COMIMP0004.getError()); // caso più comunicazioni direttamente successive
			return;
		} else if(idComunicazioni.size() == 1) {
			idComunicazioneSuccessiva = idComunicazioni.get(0);
		}

		// imposta lo stato della comunicazione a validata e il tipo acquisizione a completa
		StatoComunicazione statoValidata = new StatoComunicazione();
		statoValidata.setId(ComonlConstants.STATO_COMUNICAZIONE_VALIDATA_ID);
		comunicazione.setStatoComunicazione(statoValidata);
		comunicazione.setTipoAcquisizione(new TipoAcquisizione());
		comunicazione.getTipoAcquisizione().setId(ComonlConstants.TIPO_ACQUISIZIONE_COMUNICAZIONE_COMPLETA_ID);
		//TIPO PROVENIENZA SPICOM
		comunicazione.setTipoProvenienza(new TipoProvenienza());
		comunicazione.getTipoProvenienza().setId(ComonlConstants.TIPO_PROVENIENZA_SPICOM);
		//questa data invio la mettiamo anche nel timbro postale e nella data invio a ministero 
		comunicazione.setDtTimbroPostale(comunicazione.getDtInvio());
		comunicazione.setDtInvioMinistero(comunicazione.getDtInvio());
		comunicazione.setFlgInvioMinistero(ComonlConstants.FLAG_S);
		
		InsertComunicazioneService insertComunicazioneService = new InsertComunicazioneService(configurationHelper,
				comunicazioneDad, datoreDad, legaleRapprDad, sedeLavoroDad, rapportoDad, lavoratoreDad,
				rapportoLavoratoreDad, tutoreDad);

		Comunicazione comunicazioneInserita = null;
		

		if(comunicazione.getRapLavSedeVD() != null && comunicazione.getRapLavSedeVD().size() > 0) {
			comunicazioneInserita = insertComunicazioneService.insertComunicazioneSempliceVardatore(comunicazione);
		} else {
			comunicazioneInserita = insertComunicazioneService.insertComunicazione(comunicazione);
		}
		
		if(comunicazioneInserita.getCodiceComunRegPrec() != null) {
			Long idComunicazionePrecedente = comunicazioneDad.updateStatoComunicazionePrecedente(comunicazioneInserita);
			if (comunicazioneInserita.getTipoComunicazioneTu().getCodTipoComunicazioneMin()
					.equals(ComonlConstants.TIPO_COMUNICAZIONE_TU_RETTIFICA_COD_MIN)) {
				comunicazioneInserita.setIdComTuDaRettificare(idComunicazionePrecedente);
				comunicazioneDad.updateComunicazione(comunicazioneInserita);
			} else if (comunicazioneInserita.getTipoComunicazioneTu().getCodTipoComunicazioneMin()
					.equals(ComonlConstants.TIPO_COMUNICAZIONE_TU_ANNULLAMENTO_COD_MIN)){
				comunicazioneInserita.setIdComTuPrecedenteAnnullo(idComunicazionePrecedente);
				comunicazioneDad.updateComunicazione(comunicazioneInserita);
			}

		}

		// SE ESISTE GIA UNA COMUNICAZIONE SUCCESSIVA A QUESTA OCCORRE GESTIRE LO STATO
		if(idComunicazioneSuccessiva != null) {
			Comunicazione comunicazioneSuccessiva  = comunicazioneDad.getComunicazioneById(idComunicazioneSuccessiva);
			comunicazioneDad.updateStatoComunicazionePrecedente(comunicazioneSuccessiva);

		}

		response.setIdComunicazione(comunicazioneInserita.getId());
		log.debug("[RiceviComunicazioneDaSpicomService::execute]","END");

	}

}

