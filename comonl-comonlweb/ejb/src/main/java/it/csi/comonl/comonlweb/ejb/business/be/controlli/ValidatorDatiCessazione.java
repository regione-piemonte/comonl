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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cessazionerl;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class ValidatorDatiCessazione extends Validator<Comunicazione> {
	
	private final ControlliDad controlliDad;
	Rapporto rapporto = null;
	Rapporto dbRapporto = null;
	
	public ValidatorDatiCessazione(Comunicazione comunicazione, ControlliDad controlliDad) {
		super(comunicazione);
		this.controlliDad = controlliDad;
	}
	public ValidatorDatiCessazione(Comunicazione comunicazione, ControlliDad controlliDad, boolean checkDatiEssenziali, Comunicazione dbComunicazione) {
		super(comunicazione, checkDatiEssenziali, dbComunicazione);
		this.controlliDad = controlliDad;
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
		
		Date dtCessazione = rapporto.getDtCessazione();

		checkNotNull(dtCessazione, "data cessazione");
		
		Rapporto rapportoP = controlObject.getRapporto();
		checkCondition(dtCessazione == null || dtCessazione.compareTo(rapportoP.getDtInizioRapporto())>=0, MsgComonl.COMCESE0042.getError());//// Maggiore o uguale a “data inizio rapporto” (7.2.a). (pto 9 SFU)
		
		Rapporto missione = controlObject.getMissione();
		checkCondition(missione == null || dtCessazione == null || 
				(dtCessazione.compareTo(missione.getDtInizioMissione())>=0), MsgComonl.COMCESE0044.getError());// US - inoltre, se codice variazione 4.01 deve essere maggiore o uguale alla data inizio missione (9.1.a).
		
		checkCondition(dtCessazione == null || missione == null || missione.getAziUtilizzatrice()==null ||  missione.getAziUtilizzatrice().getDtFineContrattoSom() == null ||
				(dtCessazione.compareTo(missione.getAziUtilizzatrice().getDtFineContrattoSom())<=0), MsgComonl.COMCESE0045.getError());	//US - Se codice variazione 4.01 deve essere inferiore o uguale alla “data fine contratto somministrazione” nella sezione “ditta utilizzatrice”.
		
		checkCondition(
				dtCessazione==null || missione == null || rapportoP.getDtInizioRapporto()==null ||
				getDurataRapportoInMesi(dtCessazione, rapportoP.getDtInizioRapporto()) <= 600, MsgComonl.COMCESE0046.getError());//US - Inoltre, la differenza tra “Data cessazione” e “Data inizio rapporto” non deve essere > di 50 anni.
		
		Cessazionerl cessazionerl = controlliDad.getCessazionerlValid(rapporto.getCessazionerl(), new Date());
		checkModel(cessazionerl, "motivo");

		return (!isInError());
	}
	
	private void checkDatiEssenziali() {
		if (!checkDatiEssenziali)
			return;
		checkDatoEssenzialeDate(rapporto.getDtCessazione(), dbRapporto.getDtCessazione(), "data cessazione");
		Cessazionerl cessazionerlValid = controlliDad.getCessazionerlValid(rapporto.getCessazionerl(), new Date());
		if (cessazionerlValid!=null) {
			checkDatoEssenzialeModel(rapporto.getCessazionerl(), dbRapporto.getCessazionerl(), "causa");
		}
	}
	// FIXME stesso metodo presente su ValidatorDatiRapporto. Mettere in posto comune
	protected int getDurataRapportoInMesi(java.util.Date dataInizio, java.util.Date dataFine) {
		if (dataInizio== null || dataFine == null)
			return 0;
		
		Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(dataInizio);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(dataFine);
        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = (diffYear * 12) + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        return Math.abs(diffMonth);
	}
}
