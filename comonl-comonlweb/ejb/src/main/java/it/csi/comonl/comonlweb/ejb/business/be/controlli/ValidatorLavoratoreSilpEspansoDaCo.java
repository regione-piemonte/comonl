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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class ValidatorLavoratoreSilpEspansoDaCo extends Validator<LavoratoreSilpEspanso> {
	
	

	protected final ControlliDad controlliDad;
	
	protected final Lavoratore dbObject;
	
	protected boolean checkDatiEssenziali;
	
	
	public ValidatorLavoratoreSilpEspansoDaCo(LavoratoreSilpEspanso controlObject, ControlliDad controlliDad, boolean checkDatiEssenziali, Lavoratore dbObject) {
		super(controlObject);
		this.controlliDad = controlliDad;
		this.dbObject = dbObject;
	}	
	

	@Override
	public boolean isOk() {
		
		checkDatiEssenziali(controlObject, dbObject);

		return !isInError();
	}


	//	private boolean isInError() {
	//		return this.apiErrors!=null && this.apiErrors.size() > 0;
	//	}

	private void addError(ApiError error) {
		addApiError(error);
	}
	
	private void checkDatiEssenziali(LavoratoreSilpEspanso controlObject, Lavoratore dbObject) {
		if (!checkDatiEssenziali)
			return;
		checkDatoEssenziale(controlObject.getId().getCodiceFiscale(), dbObject.getCodiceFiscale(), "codice fiscale");
		if(controlObject.getCittadinanza().getFlgUe().equals(ComonlConstants.FLAG_N)) {
			StatusStraniero statusStranieroValido = controlliDad.getStatusStranieroValid(dbObject.getStatusStraniero(), new Date());
			if (statusStranieroValido != null)
				checkDatoEssenzialeModel(controlObject.getStatusStraniero(),dbObject.getStatusStraniero(),"numero titolo soggiorno");
			checkDatoEssenzialeDate(controlObject.getDataScadPermSogg(),dbObject.getDtScadenzaPermessoSogg(),"scadenza titolo soggiorno");
		}
	}
}
