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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.util.ValidazioneCampi;


public class ValidatorVariazioneRagioneSocialeVardatori extends Validator<Comunicazione> {

	private final ComunicazioneDad comunicazioneDad;
	public ValidatorVariazioneRagioneSocialeVardatori(Comunicazione comunicazione, ComunicazioneDad comunicazioneDad) {
		super(comunicazione);
		this.comunicazioneDad = comunicazioneDad;
	}
	
	@Override
	public boolean isOk() {
		
		// TODO copntrollare che i codici decodifica passati siano presenti su db ? caso di invocazione fuori applicativo
		ValidazioneCampi validazioneCampi = ValidazioneCampi.getInstance();
		
		checkNotNull(controlObject.getDtTrasferimentoVarDatori(), "dtTrasferimentoVarDatori");
		if(controlObject.getDatoreAttuale()!= null) {
			Datore datoreAttuale = controlObject.getDatoreAttuale();
			checkNotEmpty(datoreAttuale.getDsVariazioneRagSociale(), "dsVariazioneRagSociale");
		}
		checkNotEmpty(controlObject.getFlgSommin(), "flgSommin");
		
		return (!isInError());
	}
}
