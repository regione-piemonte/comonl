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

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;

public class ValidatorUnilav extends  Validator<Comunicazione> {

	private final ComunicazioneDad comunicazioneDad;
	private final ControlliDad controlliDad;
	private final DecodificaDad decodificaDad;
	
	public ValidatorUnilav(WrapperComunicazione wrapper, ComunicazioneDad comunicazioneDad, ControlliDad controlliDad,DecodificaDad decodificaDad) {
		super(wrapper.getComunicazione());
		this.comunicazioneDad = comunicazioneDad;
		this.controlliDad = controlliDad;
		this.decodificaDad = decodificaDad;
		this.ruolo=wrapper.getRuolo();
	}
	
	@Override
	public boolean isOk() {
		

		Validator<Comunicazione> validatorDatiGenerali = new ValidatorDatiGenerali(controlObject, comunicazioneDad,decodificaDad);
		Validator<Comunicazione> validatorDatiRapporto = new ValidatorDatiRapportoUnilav(controlObject, controlliDad,decodificaDad,checkDatiEssenziali, dbObject);
		Validator<Comunicazione> validatorDatore = new ValidatorDatore (controlObject, checkDatiEssenziali, dbObject, controlliDad,decodificaDad, ruolo);
		Validator<Comunicazione> validatorLavoratore = new ValidatorLavoratore(controlObject, controlliDad, checkDatiEssenziali, dbObject);
		validatorLavoratore.setTipoProvenienza(tipoProvenienza);
				
		if (!validatorDatiGenerali.isOk()) {
			apiErrors.addAll(validatorDatiGenerali.getApiErrors());
		}
		if (!validatorDatore.isOk()) {
			apiErrors.addAll(validatorDatore.getApiErrors());
		}
		if (!validatorDatiRapporto.isOk()) {
			apiErrors.addAll(validatorDatiRapporto.getApiErrors());
		}
		if (!validatorLavoratore.isOk()) {
			apiErrors.addAll(validatorLavoratore.getApiErrors());
		}
		
		apiWarnings.addAll(validatorDatiGenerali.getApiWarnings());
		apiWarnings.addAll(validatorDatore.getApiWarnings());
		apiWarnings.addAll(validatorDatiRapporto.getApiWarnings());
		apiWarnings.addAll(validatorLavoratore.getApiWarnings());
		
		return (!isInError());
	}
}
