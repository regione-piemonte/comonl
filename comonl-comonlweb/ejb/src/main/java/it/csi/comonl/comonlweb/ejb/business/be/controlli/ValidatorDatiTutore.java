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
import it.csi.comonl.comonlweb.ejb.util.controlli.CodiceFiscale;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Tutore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.GradoContrattuale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

// validazione richiamata solo da COMONOL
public class ValidatorDatiTutore extends Validator<Comunicazione> {

	private final ControlliDad controlliDad;
	private Tutore tutore = null;

	public ValidatorDatiTutore(Comunicazione comunicazione, ControlliDad controlliDad) {
		super(comunicazione);
		this.controlliDad = controlliDad;
	}
	
	@Override
	public boolean isOk() {
		
		Rapporto rapporto = controlObject.getRapporto();
		
		if (rapporto == null || !ValidatorUtility.isTipoContrattoApprendistato(rapporto.getTipoContratti())) {
			return true;
		}
		tutore = rapporto.getTutore();
		
		checkNotEmpty(tutore.getCfTutore(), "codice fiscale");
		checkNotEmpty(tutore.getNome(), "nome");
		checkNotEmpty(tutore.getCognome(), "cognome");
		checkNotNull(tutore.getDtNascita(), "data di nascita");
		checkNotEmpty(tutore.getSesso(), "sesso");
		
		if (!isInError())
			checkCondition(CodiceFiscale.getInstance().checkCFPersonaSenzaComune(tutore.getCognome(), tutore.getNome(), ComonlUtility.convertiDataInStringa(tutore.getDtNascita()), tutore.getSesso(), tutore.getCfTutore()),
					MsgComonl.COMTUTE0069.getError());
		
		checkAllowedValuesFlag(tutore.getFlgTitolare(), "titolare", false);
		checkNotNull(tutore.getNumAnniEsperienza(),"anni di esperienza");
		checkCondition(tutore.getIstat()!=null, MsgComonl.COMTUTE0065.getError());
		checkAllowedValuesFlag(tutore.getFlgAccettazione(), "accettazione", false);
		
		
		for (Lavoratore lavoratore : controlObject.getLavoratori()) {
			checkCondition(!lavoratore.getCodiceFiscale().equalsIgnoreCase(tutore.getCfTutore()), MsgComonl.COMTUTE0070.getError());// Il cf tutore deve essere != da quello del lavoratore
		}
		
		checkQualificaProfessionale(tutore.getIstat());

		checkGradoContrattuale(tutore.getGradoContrattuale());
		
		return (!isInError());
	}
	
	private void checkQualificaProfessionale(Istat2001livello5 qualifica) {
		if (qualifica == null)
			return;
		
		String codice = qualifica.getCodIstat2001livello5Min();
		String descrizione = qualifica.getDsComIstat2001livello5();
		
		checkCondition(
				StringUtils.isNotBlank(codice) && StringUtils.isNotBlank(descrizione),
				StringUtils.isBlank(descrizione) ? MsgComonl.COMTUTE0071.getError() :  MsgComonl.COMTUTE0072.getError());//Devono essere presenti entrambi i campi del codice e descrizione della Qualifica professionale.
		
		Istat2001livello5 record = controlliDad.getIstat2001livello5ByCodiceDescrizione(codice,descrizione);
		checkCondition(record != null, MsgComonl.COMTUTE0073.getError());// Il codice e la descrizione devono essere congruenti
		
		if (record != null) {
			boolean recordValido = record.getDtFine()==null || record.getDtFine().compareTo(new Date())>0;
			checkCondition (recordValido, MsgComonl.COMTUTE0074.getError());// la qualifica deve essere valida intermini di tempo
			if (recordValido)
				this.controlObject.getRapporto().getTutore().setIstat(record);
		}
	}
	private void checkGradoContrattuale(GradoContrattuale gradoContrattuale) {
		if (gradoContrattuale == null)
			return;
		
		String codice = gradoContrattuale.getCodGradoContrattuale();
		String descrizione = gradoContrattuale.getDsComTGradoContrattuale();
		
		GradoContrattuale record = controlliDad.getGradoContrattualeByCodiceDescrizione(codice,descrizione);
		if (record!=null) {
			checkCondition (record.getDtFine()==null || record.getDtFine().compareTo(new Date())>0, MsgComonl.COMTUTE0075.getError());// il grado contrattuale deve essere valido intermini di tempo
		}
	}
}
