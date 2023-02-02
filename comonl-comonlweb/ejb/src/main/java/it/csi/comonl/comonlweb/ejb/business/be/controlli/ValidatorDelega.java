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

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;

// TODO Validator che dovrebbe estendere la ValidatorImpresa, perchè ha gli stessi controlli
public class ValidatorDelega extends Validator<Delega> {
	
	private final ControlliDad controlliDad;
	
	public ValidatorDelega(Delega delega, ControlliDad controlliDad) {
		super(delega);
		this.controlliDad = controlliDad;
	}
	
	@Override
	public boolean isOk() {
		
		// chek ,set comune delegante
		Comune comDeleganteValid = checkComune(controlObject.getComDelegante());
		if (comDeleganteValid != null)
			this.controlObject.setComDelegante(comDeleganteValid);

		Comune comDelegatoValid = checkComune(controlObject.getComDelegato());
		if (comDelegatoValid != null)
			this.controlObject.setComDelegato(comDelegatoValid);

		if (controlObject.getComDelegato()!=null) {
			checkNotEmpty(controlObject.getComDelegato().getDsComTComune(),"descrizione comune delegato");
			checkModel(controlObject.getComDelegato().getProvincia(),"provincia del comune del delegato");
		}
		if(controlObject.getComDelegante()!=null) {
			checkNotEmpty(controlObject.getComDelegante().getDsComTComune(),"descrizione comune delegante");
			checkModel(controlObject.getComDelegante().getProvincia(),"provincia del comune del delegante");
		}
	
		return (!isInError());
	}

	
	protected Comune checkComune(Comune comune) {
		Comune comuneValid = controlliDad.getComuneValid(comune, new Date());
		checkCondition(comuneValid!=null && comuneValid.getId()!=null, MsgComonl.COMCOMW099.getError());// Comune non trovato
		if (comuneValid == null)
			return null;
		return comuneValid;
	}
}


