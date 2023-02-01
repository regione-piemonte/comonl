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

import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class ValidatorDatiProroga extends Validator<Comunicazione> {

	Rapporto rapporto = null;
	Rapporto dbRapporto = null;
	
	public ValidatorDatiProroga(Comunicazione comunicazione) {
		super(comunicazione);
	}
	public ValidatorDatiProroga(Comunicazione comunicazione, boolean checkDatiEssenziali, Comunicazione dbComunicazione) {
		super(comunicazione, checkDatiEssenziali, dbComunicazione);
	}
	
	@Override
	public boolean isOk() {
		
		boolean inSomministrazioneConMissione = controlObject.getTipoSomministrazione()!= null && (
				controlObject.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE || controlObject.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE);

		if (inSomministrazioneConMissione) {
			rapporto = controlObject.getMissione();
			dbRapporto = checkDatiEssenziali ? dbObject.getMissione() : null;
		}else {
			rapporto = controlObject.getRapporto();
			dbRapporto = checkDatiEssenziali ? dbObject.getRapporto() : null;
		}
		
		checkDatiEssenziali();
		
		Date dtProroga = rapporto.getDtFineProroga();

		checkNotNull(dtProroga, "data fine proroga");
		
		Rapporto rapportoP = controlObject.getRapporto();
		
		checkCondition(dtProroga == null || dtProroga.compareTo(rapportoP.getDtInizioRapporto())>=0, MsgComonl.COMPROE0021.getError());
		
		checkCondition(controlObject.getMissione() == null || dtProroga == null || 
				(dtProroga.compareTo(controlObject.getMissione().getDtInizioMissione())>=0), MsgComonl.COMPROE0023.getError());
		
		checkCondition(controlObject.getTipoSomministrazione() == null ||  rapportoP.getDtFineRapporto() == null ||
				(dtProroga.compareTo(rapportoP.getDtFineRapporto())<=0), MsgComonl.COMPROE0025.getError());
		
		// WARNING
		if (inSomministrazioneConMissione && rapporto.getAziUtilizzatrice().getDtFineContrattoSom() !=null) {
			boolean compareTo = compareTo(dtProroga, rapporto.getAziUtilizzatrice().getDtFineContrattoSom())<=0;
			checkConditionWarning(compareTo, MsgComonl.COMPROW0024.getError());
			if (!compareTo) {
				rapporto.getAziUtilizzatrice().setDtFineContrattoSom(dtProroga);
			}
		}
		
		return (!isInError());
	}
	
	private void checkDatiEssenziali() {
		if (!checkDatiEssenziali)
			return;
		checkDatoEssenzialeDate(rapporto.getDtFineProroga(), dbRapporto.getDtFineProroga(), "data fine proroga");
	}
}
