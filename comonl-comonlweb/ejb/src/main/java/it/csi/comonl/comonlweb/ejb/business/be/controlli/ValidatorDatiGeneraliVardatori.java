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
package it.csi.comonl.comonlweb.ejb.business.be.controlli;

import java.util.Date;
import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.Validazioni;
import it.csi.comonl.comonlweb.ejb.util.controlli.CodiceFiscale;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlDateUtils;
import it.csi.comonl.comonlweb.lib.util.ValidazioneCampi;


public class ValidatorDatiGeneraliVardatori extends Validator<Comunicazione> {

	private final ComunicazioneDad comunicazioneDad;
	private final DecodificaDad decodificaDad;
	
	
	
	public ValidatorDatiGeneraliVardatori(Comunicazione comunicazione, ComunicazioneDad comunicazioneDad, DecodificaDad decodificaDad) {
		super(comunicazione);
		this.comunicazioneDad = comunicazioneDad;
		this.decodificaDad = decodificaDad;
	}
	
	@Override
	public boolean isOk() {
		
		// TODO copntrollare che i codici decodifica passati siano presenti su db ? caso di invocazione fuori applicativo
		ValidazioneCampi validazioneCampi = ValidazioneCampi.getInstance();
		// TODO chek length all fields
		// TODO chk ID rapporto Lavoro 
		// TODO chk setFlgCurrentRecord 
		checkModel(controlObject.getTipoComunicazione(), "tipoComunicazione");
		checkModel(controlObject.getTipoTracciato(), "tipoTracciato");
		checkNotEmpty(controlObject.getFlgSommin(), "flgSommin");
		checkNotEmpty(controlObject.getEmail(), "email");
		checkCondition(validazioneCampi.checkMail(controlObject.getEmail()),MsgComonl.COMCOMW002.getError());// warming?
		checkModel(controlObject.getTipoComunicazioneTu(), "tipoComunicazioneTu");
		if(controlObject.getTipoComunicazioneTu() != null) {
			//TipoComunicazioneTu
		}
		/*
		checkNotEmpty(controlObject.getFlgCausaForzamaggiore(), "flgCausaForzamaggiore");
		if (controlObject.getFlgCausaForzamaggiore() != null && controlObject.getFlgCausaForzamaggiore().equals(ComonlConstants.FLAG_S)) {
			checkNotEmpty(controlObject.getDsCausaForzamaggiore(),"DsCausaForzamaggiore");
		}*/
		//checkNotEmpty(controlObject.getFlgComunSegUrgenza(),"flgComunSegUrgenza");
		//checkNotEmpty(controlObject.getFlgComDatLav(),"flgComDatLav");
		if (controlObject.getFlgComDatLav() != null && controlObject.getFlgComDatLav().equals(ComonlConstants.FLAG_N)) {
			checkModel(controlObject.getTipoSoggettoAbilitato(), "tipoSoggettoAbilitato");
			checkNotEmpty(controlObject.getCodiceFiscaleSoggetto(), "codiceFiscaleSoggetto");
			checkNotEmpty(controlObject.getEmailsoggetto(), "emailsoggetto");
			checkNotNull(controlObject.getDtTimbroPostale(),"dtTimbroPostale");
			if (controlObject.getDtTimbroPostale()!=null) {
				Date dataAvvioSistema = ComonlDateUtils.parseDate("11/01/2008"); // parametro su db?
				Date sysdate = new Date();
				checkCondition(controlObject.getDtTimbroPostale().compareTo(dataAvvioSistema)>0 && controlObject.getDtTimbroPostale().compareTo(sysdate)<=0, MsgComonl.COMCOME1300.getError());
			}
			
			if(controlObject.getCodiceFiscaleSoggetto() != null) {
				checkCondition(CodiceFiscale.getInstance().controllaCodiceFiscaleSoggetto(controlObject.getCodiceFiscaleSoggetto(), decodificaDad), MsgComonl.COMDATE002.getError());
			}
			
		}
		if (controlObject.getIdComTuPrecedenteAnnullo() != null || controlObject.getIdComTuDaRettificare()!= null) {
			checkNotEmpty(controlObject.getCodiceComunRegPrec(), "codiceComunRegPrec");
			Optional<Comunicazione> optComunicazione = comunicazioneDad.getComunicazione(controlObject.getIdComDComunicPrecedente());
			Comunicazione comunicazione = optComunicazione.isPresent() ? optComunicazione.get() : null;
			checkModel(comunicazione, "comunicazione da rettificare/annullare");
			if(comunicazione!=null && comunicazione.getCodiceComunicazioneReg() != null) {
				checkCondition(comunicazione.getCodiceComunicazioneReg().equalsIgnoreCase(controlObject.getCodiceComunRegPrec()),MsgComonl.COMCOME1302.getError());
			}
			if(comunicazione!=null && comunicazione.getCodiceComunicazioneReg() == null) {
				checkCondition(
						controlObject.getCodiceComunRegPrec()==null ||
						controlObject.getCodiceComunRegPrec().equalsIgnoreCase("1300000000000000"),MsgComonl.COMCOME1302.getError());
			}
		}
		return (!isInError());
	}
}
