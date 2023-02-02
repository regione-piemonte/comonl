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

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;

public class ValidatorDatiRapportoUniurg extends ValidatorDatiRapporto {

	public ValidatorDatiRapportoUniurg(Comunicazione comunicazione, ControlliDad controlliDad) {
		super(comunicazione, controlliDad);
	}
	
	@Override
	public boolean isOk() {
		
		super.isOk(); // validazione comune

		checkAltriDati();
		
		return (!isInError());
	}
	
	private void checkAltriDati() {
		checkNotEmpty(controlObject.getDsMotivoUrgenza(), "getDsMotivoUrgenza");
	}
}
