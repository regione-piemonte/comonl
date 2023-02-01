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

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;


public class ValidatorDatiAziendaPrecedente extends Validator<Comunicazione> {

	private final ControlliDad controlliDad;
	
	
	
	
	public ValidatorDatiAziendaPrecedente(Comunicazione comunicazione, ControlliDad controlliDad) {
		super(comunicazione);
		this.controlliDad = controlliDad;
	}
	
	@Override
	public boolean isOk() {
		
		
		//Variazione datore lavoro
		TipoTrasferimento tipoTrasefrimento = controlObject.getTipoTrasferimento();
		checkModel(tipoTrasefrimento, "Tipo variazione");
		checkNotNull(controlObject.getDtTrasferimentoVarDatori(),"Data inizio");
		
		if(tipoTrasefrimento != null && tipoTrasefrimento.getCodTipotrasferimentoMin().equals(ComonlConstants.TIPO_TRASFERIMENTO_AFFITTO_RAMO_COD_MIN)) {
			checkNotNull(controlObject.getDtFineAffittoRamo(),"Data fine affitto ramo d'azienda");
		}
		
		//Dati dell'azienda precedente
		Datore datoreAttuale = controlObject.getDatoreAttuale();
		Datore datorePrecedente = controlObject.getDatorePrecedente();
		
		
		
		checkNotEmpty(datorePrecedente.getCodiceFiscale(), "codice fiscale");
		
		Atecofin atecofin = controlliDad.getAtecofinValid(datorePrecedente.getAtecofin(), new Date());
		checkModel (atecofin, "attivita' ATECO");
		if(atecofin != null)
			datorePrecedente.setAtecofin(atecofin);
		
		checkModel(datorePrecedente.getAtecofin(), "Settore ateco");
		
		
		checkNotEmpty(datorePrecedente.getFlgAzArtigiana(), "Azienda artigiana");
		
		
		checkCondition(StringUtils.isBlank(datorePrecedente.getCodiceFiscale()) ||
				!StringUtils.isBlank(datorePrecedente.getCodiceFiscale()) && !datorePrecedente.getCodiceFiscale().equalsIgnoreCase(datoreAttuale.getCodiceFiscale()), MsgComonl.COMDATE1600.getError()
				);//Il codice fiscale del datore precedente deve essere diverso da quello del datore attuale.

		
		checkCondition(controlObject.getDtTrasferimentoVarDatori()== null || controlObject.getDtFineAffittoRamo()==null ||
				compareTo(controlObject.getDtFineAffittoRamo(), controlObject.getDtTrasferimentoVarDatori())>=0, MsgComonl.COMDATE1601.getError());
		
		SedeLavoro sedeLegale= datorePrecedente.getSedeLegale();
		if(sedeLegale == null){
			checkNotEmpty(null, "Sede legale");
		}else {
			Comune comune = sedeLegale.getComune();
			checkModel(comune, "Comune sede legale Azienda precedente");
			checkNotEmpty(sedeLegale.getCodCap(), "Cap sede legale azienda precedente");
			checkNotEmpty(sedeLegale.getIndirizzo(), "Indirizzo sede legale azienda precedente");
			boolean atLeastoneNotNull = StringUtils.isNotBlank(sedeLegale.getTelefono()) || StringUtils.isNotBlank(sedeLegale.getFax()) || StringUtils.isNotBlank(sedeLegale.getEmail());
			checkCondition(atLeastoneNotNull, MsgComonl.COMTDIE1416.getError());
			
		}
		
		return (!isInError());
	}
}
