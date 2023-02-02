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

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

// TODO Validator che dovrebbe estendere la ValidatorImpresa, perchè ha gli stessi controlli
public class ValidatorAziendaUtilizzatrice extends Validator<Comunicazione> {
	
	protected final ControlliDad controlliDad;
	public ValidatorAziendaUtilizzatrice(Comunicazione comunicazione, ControlliDad controlliDad) {
		this(comunicazione, false, null,controlliDad);
	}
	public ValidatorAziendaUtilizzatrice(Comunicazione comunicazione, boolean checkDatiEssenziali, Comunicazione dbComunicazione,ControlliDad controlliDad) {
		super(comunicazione, checkDatiEssenziali, dbComunicazione);
		this.controlliDad=controlliDad;
	}
	
	@Override
	public boolean isOk() {
		
		checkDatiEssenziali();

		Datore aziendaUtilizzatrice = null;
		if (controlObject.getMissione() != null) {
			aziendaUtilizzatrice = controlObject.getMissione().getAziUtilizzatrice();
		}else {
			aziendaUtilizzatrice = controlObject.getRapporto().getAziUtilizzatrice();
		}
			
		Datore datore = controlObject.getDatoreAttuale();
		
		checkNotNull(aziendaUtilizzatrice.getNumContrSomm(),"numero contratto");
		checkNotNull(aziendaUtilizzatrice.getDtInizioContrattoSom(),"data inizio contratto");
			
		checkCondition(StringUtils.isBlank(aziendaUtilizzatrice.getCodiceFiscale()) ||
				!StringUtils.isBlank(aziendaUtilizzatrice.getCodiceFiscale()) && !aziendaUtilizzatrice.getCodiceFiscale().equalsIgnoreCase(datore.getCodiceFiscale()), MsgComonl.COMIMPE1344.getError()
				);//Il codice fiscale dell'azienda utilizzatrice deve essere diverso da quello del datore.
		checkCondition(StringUtils.isBlank(aziendaUtilizzatrice.getPartitaIva()) ||
				!StringUtils.isBlank(aziendaUtilizzatrice.getPartitaIva()) && !aziendaUtilizzatrice.getPartitaIva().equalsIgnoreCase(datore.getPartitaIva()), MsgComonl.COMIMPE1344.getError()
				);//Il codice fiscale dell'azienda utilizzatrice deve essere diverso da quello del datore.

		
		checkSedeLegale(aziendaUtilizzatrice);
		
		return (!isInError());
	}
	private void checkDatiEssenziali() {
		if (!checkDatiEssenziali)
			return;
		checkDatoEssenziale(controlObject.getMissione().getAziUtilizzatrice().getCodiceFiscale(), dbObject.getMissione().getAziUtilizzatrice().getCodiceFiscale(), "codice fiscale");
	}
	
	private void checkSedeLegale(Datore datore) {
		SedeLavoro sedeLegale = datore.getSedeLegale();
		if (sedeLegale.getStatiEsteri()!=null && StringUtils.isNotBlank(sedeLegale.getStatiEsteri().getCodNazioneMin())) {
			// check stato extra UE
			Cittadinanza cittadinanza = controlliDad.getCittadinanzaByCodMf(sedeLegale.getStatiEsteri().getCodNazioneMin());
			checkCondition(cittadinanza == null || ComonlConstants.FLAG_S.equalsIgnoreCase(cittadinanza.getFlgUe()), MsgComonl.COMIMPE1345.getError());
			
		}
	}
}


