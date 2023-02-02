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

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;

public class ValidatorVardatori extends  Validator<Comunicazione> {

	private final ComunicazioneDad comunicazioneDad;
	private final ControlliDad controlliDad;
	private final DecodificaDad decodificaDad;
	
	public ValidatorVardatori(WrapperComunicazione wrapper, ComunicazioneDad comunicazioneDad, ControlliDad controlliDad,DecodificaDad decodificaDad) {
		super(wrapper.getComunicazione());
		this.comunicazioneDad = comunicazioneDad;
		this.controlliDad = controlliDad;
		this.decodificaDad = decodificaDad;
		ruolo = wrapper.getRuolo();
	}
	
	@Override
	public boolean isOk() {

		Validator<Comunicazione> validatorDatiGeneraliVardatori = new ValidatorDatiGeneraliVardatori(controlObject, comunicazioneDad,decodificaDad);
		Validator<Comunicazione> validatorDatore = new ValidatorDatore(controlObject, controlliDad,decodificaDad,ruolo);
		if (!validatorDatiGeneraliVardatori.isOk()) {
			apiErrors.addAll(validatorDatiGeneraliVardatori.getApiErrors());
		}
		if (!validatorDatore.isOk()) {
			apiErrors.addAll(validatorDatore.getApiErrors());
		}
		apiWarnings.addAll(validatorDatiGeneraliVardatori.getApiWarnings());
		apiWarnings.addAll(validatorDatore.getApiWarnings());
		

		// VARDATORI RAGIONE SOCIALE
		if (StringUtils.isNotBlank(controlObject.getDatoreAttuale().getDsVariazioneRagSociale())) 
		{
			Validator<Comunicazione> ValidatorVariazioneRagioneSocialeVardatori = new ValidatorVariazioneRagioneSocialeVardatori(controlObject, comunicazioneDad);
			if (!ValidatorVariazioneRagioneSocialeVardatori.isOk()) {
				apiErrors.addAll(ValidatorVariazioneRagioneSocialeVardatori.getApiErrors());
			}
			apiWarnings.addAll(ValidatorVariazioneRagioneSocialeVardatori.getApiWarnings());
		}
		if (StringUtils.isBlank(controlObject.getDatoreAttuale().getDsVariazioneRagSociale())) 
		{
			Validator<Comunicazione> validatorDatiAziendaPrecedente = new ValidatorDatiAziendaPrecedente(controlObject,controlliDad);
			if (!validatorDatiAziendaPrecedente.isOk()) {
				apiErrors.addAll(validatorDatiAziendaPrecedente.getApiErrors());
			}
			apiWarnings.addAll(validatorDatiAziendaPrecedente.getApiWarnings());
			
			Validator<Comunicazione> validatorSediLavoro = new ValidatorSediLavoro(controlObject, controlliDad, decodificaDad);
			if (!validatorSediLavoro.isOk()) {
				apiErrors.addAll(validatorSediLavoro.getApiErrors());
			}
			apiWarnings.addAll(validatorSediLavoro.getApiWarnings());
		}
		return (!isInError());
		
	}
}
