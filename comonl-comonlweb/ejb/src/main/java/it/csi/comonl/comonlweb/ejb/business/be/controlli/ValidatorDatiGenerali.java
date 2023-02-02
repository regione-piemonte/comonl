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
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.controlli.CodiceFiscale;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlDateUtils;
import it.csi.comonl.comonlweb.lib.util.ValidazioneCampi;


public class ValidatorDatiGenerali extends Validator<Comunicazione> {

	private final ComunicazioneDad comunicazioneDad;
	private final DecodificaDad decodificaDad;
	public ValidatorDatiGenerali(Comunicazione comunicazione, ComunicazioneDad comunicazioneDad,DecodificaDad decodificaDad) {
		super(comunicazione);
		this.comunicazioneDad = comunicazioneDad;
		this.decodificaDad = decodificaDad;
	}
	
	@Override
	public boolean isOk() {
		
		ValidazioneCampi validazioneCampi = ValidazioneCampi.getInstance();
		// TODO chek length all fields
		// TODO chk ID rapporto Lavoro 
		// TODO chk setFlgCurrentRecord 
		checkModel(controlObject.getTipoComunicazione(), "tipo comunicazione");
		checkModel(controlObject.getTipoTracciato(), "tipo tracciato");
		checkNotEmpty(controlObject.getFlgSommin(), "somministrazione");
		checkNotEmpty(controlObject.getEmail(), "email");
		checkCondition(validazioneCampi.checkMail(controlObject.getEmail()),MsgComonl.COMCOMW002.getError());// warming?
		checkModel(controlObject.getTipoComunicazioneTu(), "tipoComunicazioneTu");
		checkNotEmpty(controlObject.getFlgCausaForzamaggiore(), "causa di forza maggiore");
		if (controlObject.getFlgCausaForzamaggiore() != null && controlObject.getFlgCausaForzamaggiore().equals(ComonlConstants.FLAG_S)) {
			checkNotEmpty(controlObject.getDsCausaForzamaggiore(),"descrizione causa forza maggiore");
		}
		checkNotEmpty(controlObject.getFlgComunSegUrgenza(),"flgComunSegUrgenza");
		checkNotEmpty(controlObject.getFlgComDatLav(),"Si effettua la comunicazione per il datore di Lavoro");
		if (controlObject.getFlgComDatLav() != null && controlObject.getFlgComDatLav().equals(ComonlConstants.FLAG_N)) {
			checkModel(controlObject.getTipoSoggettoAbilitato(), "altro soggetto");
			checkNotEmpty(controlObject.getCodiceFiscaleSoggetto(), "codice fiscale del soggetto");
			checkNotEmpty(controlObject.getEmailsoggetto(), "email del soggetto");
			checkNotNull(controlObject.getDtTimbroPostale(),"data timbro postale");
			if (controlObject.getDtTimbroPostale()!=null) {
				Date dataAvvioSistema = ComonlDateUtils.parseDate("11/01/2008"); // parametro su db?
				Date sysdate = new Date();
				checkCondition(controlObject.getDtTimbroPostale().compareTo(dataAvvioSistema)>0 &&
						compareTo(controlObject.getDtTimbroPostale(), sysdate)<=0 , MsgComonl.COMCOME1300.getError());
			}
			
			if(controlObject.getCodiceFiscaleSoggetto() != null) {
				checkCondition(CodiceFiscale.getInstance().controllaCodiceFiscaleSoggetto(controlObject.getCodiceFiscaleSoggetto(), decodificaDad), MsgComonl.COMDATE002.getError());
			}
		}
		if (controlObject.getIdComTuPrecedenteAnnullo() != null || controlObject.getIdComTuDaRettificare()!= null) {
			checkNotEmpty(controlObject.getCodiceComunRegPrec(), "Codice regionale della comunicazione da rettificare o da annullare");
			Optional<Comunicazione> optComunicazione = comunicazioneDad.getComunicazione(controlObject.getIdComDComunicPrecedente());
			Comunicazione comunicazione = optComunicazione.isPresent() ? optComunicazione.get() : null;
			checkModel(comunicazione, "comunicazione da rettificare/annullare");
			if(comunicazione!=null && comunicazione.getCodiceComunicazioneReg() != null) {
				checkCondition(comunicazione.getCodiceComunicazioneReg().equalsIgnoreCase(controlObject.getCodiceComunRegPrec()),MsgComonl.COMCOME1302.getError());
			}
			if(comunicazione!=null && comunicazione.getCodiceComunicazioneReg() == null) {
				checkCondition(
						controlObject.getCodiceComunRegPrec()==null ||
						controlObject.getCodiceComunRegPrec().equalsIgnoreCase("1300000000000000"),MsgComonl.COMCOME1302.getError());
			}
		}
		checkCondition(controlObject.getTipoComunicazioneTu()==null || 
				(!ComonlConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_TELEMATICA.equals(controlObject.getTipoComunicazioneTu().getCodTipoComunicazioneMin()) && 
				 !ComonlConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_TURISTICO.equals(controlObject.getTipoComunicazioneTu().getCodTipoComunicazioneMin())) ||
				StringUtils.isNotBlank(controlObject.getCodiceComunRegPrec()),MsgComonl.COMCOME1303.getError());
		
		return (!isInError());
		
		//TODO check data timbro postale se getFlgComDatLav è valorizzato indipentemente da S o N allora deve essere valorizzata e compresa tra 11/01/2008 e data sistema.
		// se getFlgComDatLav non è passato perchè ruolo != da ammistrat eccc. allora viene impostata alla data di sistema.
	}
}
