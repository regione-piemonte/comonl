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


import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorDatiTrasferimentoDistacco;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutDatiTrasferimentoDistaccoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutDatiTrasferimentoDistaccoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSomministrazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PutDatiTrasferimentoDistaccoService
 */
public class PutDatiTrasferimentoDistaccoService
		extends BaseComunicazioneService<PutDatiTrasferimentoDistaccoRequest, PutDatiTrasferimentoDistaccoResponse> {
	
	private WrapperComunicazione wrappercomunicazione;
	private Comunicazione comunicazioneClient;
	
	private RapportoDad rapportoDad;
	private DatoreDad datoreDad;
	private SedeLavoroDad sedeLavoroDad;
	private ControlliDad controlliDad;
	private DecodificaDad decodificaDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param rapportoDad      the DAD for the rapporto
	 */
	public PutDatiTrasferimentoDistaccoService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, RapportoDad rapportoDad,DatoreDad datoreDad, SedeLavoroDad sedeLavoroDad,
			ControlliDad controlliDad, DecodificaDad decodificaDad) {
		super(configurationHelper, comunicazioneDad);
		this.rapportoDad = rapportoDad;
		this.datoreDad = datoreDad;
		this.sedeLavoroDad = sedeLavoroDad;
		this.controlliDad = controlliDad;
		this.decodificaDad = decodificaDad;
	}

	@Override
	protected void checkServiceParams() {
		wrappercomunicazione = request.getWrapperComunicazione();
		checkModel(wrappercomunicazione.getComunicazione(), "comunicazione", true);
    }

	@Override
	protected void execute() {
		// CDU-14
		log.debug("execute----------->", "Entro nel metodo execute");
		
		comunicazioneClient = wrappercomunicazione.getComunicazione();

		Comunicazione comunicazione = isEntityPresent(()-> comunicazioneDad.getComunicazione(comunicazioneClient.getId()), "comunicazione");
		checkBusinessCondition(comunicazione.getTipoComunicazione().getId().equals(ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO), MsgComonl.COMCOMP0002.getError());// definire messaggio appropriato: TIPO COMUNICAZIONE CHE NON PERMETTE INSERIMENTO DI DISTACCO / TRASFERIMENTO

		boolean checkDatiEssenziali = isCheckDatiEssenziali(comunicazioneClient, wrappercomunicazione.getRuolo());
		Comunicazione comunicazioneDiPartenza = null;
		if (checkDatiEssenziali) {
			comunicazioneDiPartenza = comunicazioneDad.getComunicazioneById(comunicazioneClient.getIdComDComunicPrecedente());
		}
		if (isComunicazioneAnnulla(comunicazioneClient)) {
			comunicazioneDiPartenza = comunicazioneDad.getComunicazioneById(comunicazioneClient.getIdComTuPrecedenteAnnullo());
		}
		
		Validator<Comunicazione> validator = new ValidatorDatiTrasferimentoDistacco(comunicazioneClient, controlliDad, decodificaDad, checkDatiEssenziali, comunicazioneDiPartenza);
		if (!validator.isOk()) {
			response.setApiErrors(validator.getApiErrors());
			return;
		}
		response.setApiWarnings(validator.getApiWarnings());

		TipoSomministrazione tipoSomministrazione = comunicazione.getTipoSomministrazione();
		Rapporto rapportoClient = null;
		// identificazione del rapporto da trattare
		if (tipoSomministrazione == null ||
			tipoSomministrazione != null && tipoSomministrazione.getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE) {
			// RAPPORTO P
			rapportoClient = comunicazioneClient.getRapporto();
		}
		if (tipoSomministrazione != null && tipoSomministrazione.getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE) {
			// RAPPORTO M
			rapportoClient = comunicazioneClient.getMissione();
		}
		
		Trasformazionerl trasformazionerl = rapportoClient.getTrasformazionerl();
		boolean distacco = trasformazionerl.getCodTrasformazionirlMin().equals(ComonlConstants.TIPO_TRASFORMAZIONERL_MIN_DL);
		boolean trasferimento = trasformazionerl.getCodTrasformazionirlMin().equals(ComonlConstants.TIPO_TRASFORMAZIONERL_MIN_TL);

		boolean daTLaDL =  distacco && !isModelNull(rapportoClient.getSedeLavoroPrecedente());// Si passa da Trasferimento a Distacco
		boolean daDLaTL =  trasferimento && !isModelNull(rapportoClient.getDatoreDistaccatario());// Si passa da Distacco a Trasferimento
		
		if(distacco) {
			Datore datoreDistaccatario = datoreDad.saveDatore(rapportoClient.getDatoreDistaccatario());
			SedeLavoro sedeLegale = sedeLavoroDad.saveSedeLegale(rapportoClient.getDatoreDistaccatario().getSedeLegale(), datoreDistaccatario.getId()); // TODO verificare se sia da slavare la sede legale o sede operativa.
			
			rapportoClient.getDatoreDistaccatario().setId(datoreDistaccatario.getId());
			rapportoClient.setDtEvento(rapportoClient.getDtTrasformazione());

			if(daTLaDL) {
				sedeLavoroDad.deleteSedeLavoro(rapportoClient.getSedeLavoroPrecedente().getId());
				rapportoClient.setSedeLavoroPrecedente(null);
			}
			
			rapportoDad.updateRapporto(rapportoClient);
		}
		if(trasferimento) {
			
			if (daDLaTL) {
				// passaggio solo per UNILAV perchè unisom ha solo il trasferimento
				sedeLavoroDad.deleteSedeLavoro(rapportoClient.getDatoreDistaccatario().getSedeLegale().getId(), rapportoClient.getDatoreDistaccatario().getId());
				datoreDad.deleteDatore(rapportoClient.getDatoreDistaccatario().getId());
				rapportoClient.setDatoreDistaccatario(null);
			}
			if (tipoSomministrazione==null) {
				// UL
				SedeLavoro sedeLavoroPrecedente = sedeLavoroDad.saveSedeLavoro(rapportoClient.getSedeLavoroPrecedente());
				rapportoClient.getSedeLavoroPrecedente().setId(sedeLavoroPrecedente.getId());
			}
			rapportoClient.setDtEvento(rapportoClient.getDtTrasformazione());
			rapportoDad.updateRapporto(rapportoClient);
		}
		
		// UPD dtRiferimento della comunicazione
		comunicazione.setDataRiferimento(rapportoClient.getDtTrasformazione());
		comunicazione = comunicazioneDad.updateComunicazione(comunicazione);

		comunicazione.setApiWarnings(response.getApiWarnings());
		response.setComunicazione(comunicazione);
	}
}
