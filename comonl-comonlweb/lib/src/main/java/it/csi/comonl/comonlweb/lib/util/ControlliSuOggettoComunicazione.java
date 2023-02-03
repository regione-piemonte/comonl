/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - LIB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.util;

import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;

public class ControlliSuOggettoComunicazione {

	// Unica istanza della classe
	private static ControlliSuOggettoComunicazione singleton = null;

	// Per ottenere l'unica istanza della classe
	public static ControlliSuOggettoComunicazione getInstance() {
		if (singleton == null)
			createSingleton();
		return singleton;
	}

	private static synchronized void createSingleton() {
		if (singleton == null)
			singleton = new ControlliSuOggettoComunicazione();
	}


	public static boolean isUNILAV (Comunicazione co) {
		return ComonlConstants.TIPO_TRACCIATO_UNILAV_ID.equalsIgnoreCase(co.getTipoTracciato().getId());
	}
	public static boolean isUNISOMM(Comunicazione co) {
		return ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID.equalsIgnoreCase(co.getTipoTracciato().getId());
	}
	public static boolean isVARDATORI (Comunicazione co) {
		return ComonlConstants.TIPO_TRACCIATO_VARDATORI_ID.equalsIgnoreCase(co.getTipoTracciato().getId());
	}
	public static boolean isUNIURG (Comunicazione co) {
		return ComonlConstants.TIPO_TRACCIATO_URGENZA_ID.equalsIgnoreCase(co.getTipoTracciato().getId());
	}
	public static boolean isUNIDOM (Comunicazione co) {
		return ComonlConstants.TIPO_TRACCIATO_DOMESTICO_ID.equalsIgnoreCase(co.getTipoTracciato().getId());
	}

	public static boolean isTRASFERIMENTO (Comunicazione co) {
		return ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO.equalsIgnoreCase(co.getTipoComunicazione().getId());
	}
	public static boolean isTRASFORMAZIONE (Comunicazione co) {
		return ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE.equalsIgnoreCase(co.getTipoComunicazione().getId());
	}
	public static boolean isASSUNZIONE (Comunicazione co) {
		return ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE.equalsIgnoreCase(co.getTipoComunicazione().getId());
	}

	public boolean checkDiAnnullRettSeguitoUrgenza(Comunicazione c) {
		 if(this.checkComunicazioneDiAnnull(c)) {
			 return true;
		 }
		 else if(this.checkComunicazioneDiRett(c)) {
			 return true;
		 }
		 else if(c.getFlgComunSegUrgenza() != null && ComonlConstants.FLAG_S.equalsIgnoreCase(c.getFlgComunSegUrgenza())) {
			 return true;
		 }

		 return false;
	 }
	 public boolean checkComunicazioneDiAnnull(Comunicazione c) {
		 if(c != null &&
				 c.getTipoComunicazione() != null &&
				 c.getTipoComunicazione().getTipo().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TU_ANNULLAMENTO_COD_MIN)) {
			 return true;
		 }
		 return false;
	 }
	 public boolean checkComunicazioneDiRett(Comunicazione c) {
		 if(c != null &&
				 c.getTipoComunicazione() != null &&
				 c.getTipoComunicazione().getTipo().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TU_RETTIFICA_COD_MIN)) {
			 return true;
		 }
		 return false;
	 }
	 public boolean checkComunicazioneSomm_SommEMiss(Comunicazione c) {
		 if(c.getTipoSomministrazione() != null
				 && c.getTipoSomministrazione().getId() != null
				 && (ComonlConstants.TIPO_SOMMINISTRAZIONE.equals(c.getTipoSomministrazione().getId())
						 ||  ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE.equals(c.getTipoSomministrazione().getId()))) {
	    	  return true;
	      }
	      else {
	    	  return false;
	      }
	 }	 
	 
	 public boolean checkComunicazioneMiss_SommEMiss(Comunicazione c) {
		 if(c.getTipoSomministrazione() != null
				 && c.getTipoSomministrazione().getId() != null
				 && (ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE.equals(c.getTipoSomministrazione().getId())
						 ||  ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE.equals(c.getTipoSomministrazione().getId()))) {
			 return true;
		 }
		 else {
			 return false;
		 }
	 }


	 //////////////////////////////////// Da ComunicazioneTracciatoUnicoDTO (spicom) a Comunicazione comonl
	 public static boolean isSpicom_UNISOMM (ComunicazioneTracciatoUnicoDTO c) {
		 return ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID.equalsIgnoreCase(c.getDatiAggiuntivi().getTipoTracciatoXML());
	 }
	 public static boolean isSpicom_UNILAV (ComunicazioneTracciatoUnicoDTO c) {
		 return ComonlConstants.TIPO_TRACCIATO_UNILAV_ID.equalsIgnoreCase(c.getDatiAggiuntivi().getTipoTracciatoXML());
	 }
	 public static boolean isSpicom_UNIURG (ComunicazioneTracciatoUnicoDTO c) {
		 return ComonlConstants.TIPO_TRACCIATO_URGENZA_ID.equalsIgnoreCase(c.getDatiAggiuntivi().getTipoTracciatoXML());
	 }
	 public static boolean isSpicom_VARDATORI (ComunicazioneTracciatoUnicoDTO c) {
		 return ComonlConstants.TIPO_TRACCIATO_VARDATORI_ID.equalsIgnoreCase(c.getDatiAggiuntivi().getTipoTracciatoXML());
	 }
	 public static boolean isSpicom_UNIDOM (ComunicazioneTracciatoUnicoDTO c) {
		 return ComonlConstants.TIPO_TRACCIATO_DOMESTICO_ID.equalsIgnoreCase(c.getDatiAggiuntivi().getTipoTracciatoXML());
	 }
	 public static boolean isSpicom_TRASFERIMENTO (ComunicazioneTracciatoUnicoDTO c) {
		return ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO.equalsIgnoreCase(c.getDatiInvio().getTipoComunicazione());
	 }
	 public static boolean isSpicom_TRASFORMAZIONE (ComunicazioneTracciatoUnicoDTO c) {
		return ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE.equalsIgnoreCase(c.getDatiInvio().getTipoComunicazione());
	 }

	 public boolean checkSpicomComunicazioneMiss_SommEMiss(ComunicazioneTracciatoUnicoDTO c) {
		 if(c.getCessazione().getCodCausa() != null
				 && (ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE.equals(c.getCessazione().getCodCausa())
						 ||  ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE.equals(c.getCessazione().getCodCausa()))) {
			 return true;
		 }
		 else {
			 return false;
		 }
	 }



}
