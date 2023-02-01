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
package it.csi.comonl.comonlweb.ejb.business.be.controlli;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class ValidatorUnisomm extends  Validator<Comunicazione> {

	private final ComunicazioneDad comunicazioneDad;
	private final ControlliDad controlliDad;
	private final DecodificaDad decodificaDad;
	
	public ValidatorUnisomm(WrapperComunicazione wrapper, ComunicazioneDad comunicazioneDad, ControlliDad controlliDad, DecodificaDad decodificaDad) {
		super(wrapper.getComunicazione());
		this.comunicazioneDad = comunicazioneDad;
		this.controlliDad = controlliDad;
		this.decodificaDad = decodificaDad;
		this.ruolo=wrapper.getRuolo();
	}
	
	@Override
	public boolean isOk() {

		boolean inSomministrazioneConMissione = controlObject.getTipoSomministrazione()!= null && (
				controlObject.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE || controlObject.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE);
		

		Validator<Comunicazione> validatorDatiGenerali = new ValidatorDatiGenerali(controlObject, comunicazioneDad, decodificaDad);
		if (!validatorDatiGenerali.isOk()) {
			apiErrors.addAll(validatorDatiGenerali.getApiErrors());
		}
		
		Validator<Comunicazione> validatorDatore = new ValidatorDatore(controlObject, checkDatiEssenziali, dbObject, controlliDad, decodificaDad, ruolo);
		if (!validatorDatore.isOk()) {
			apiErrors.addAll(validatorDatore.getApiErrors());
		}
		
		if (inSomministrazioneConMissione) {
			Validator<Comunicazione> validatorAziendaUtilizzatrice = new ValidatorAziendaUtilizzatrice(controlObject, checkDatiEssenziali, dbObject, controlliDad);
			if (!validatorAziendaUtilizzatrice.isOk()) {
				apiErrors.addAll(validatorAziendaUtilizzatrice.getApiErrors());
			}
		}
		
		Validator<Comunicazione> validatorDatiRapporto = new ValidatorDatiRapportoUnisomm(controlObject, controlliDad, checkDatiEssenziali, dbObject);
		if (!validatorDatiRapporto.isOk()) {
			apiErrors.addAll(validatorDatiRapporto.getApiErrors());
		}
		
		apiWarnings.addAll(validatorDatiGenerali.getApiWarnings());
		apiWarnings.addAll(validatorDatore.getApiWarnings());
		apiWarnings.addAll(validatorDatiRapporto.getApiWarnings());

		Validator<Comunicazione> validatorLavoratore = new ValidatorLavoratore(controlObject, controlliDad, checkDatiEssenziali, dbObject);
		validatorLavoratore.setTipoProvenienza(tipoProvenienza);
		if (!validatorLavoratore.isOk()) {
			apiErrors.addAll(validatorLavoratore.getApiErrors());
		}
		apiWarnings.addAll(validatorLavoratore.getApiWarnings());

		return (!isInError());
	}
}
