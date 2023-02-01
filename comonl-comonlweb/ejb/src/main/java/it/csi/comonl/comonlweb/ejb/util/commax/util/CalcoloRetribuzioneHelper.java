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
package it.csi.comonl.comonlweb.ejb.util.commax.util;


import java.util.Vector;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloRetribuzione;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;


public class CalcoloRetribuzioneHelper  {

	public static final String COD_MIN_CCNL_ND = "ND";

	public static Long calcolaRetribuzione(LivelloRetribuzione dto, String codTipoOrario, Long oreSettimanaliMedie, Long retribuzioneInserita) {

		if (!datiObbligatoriValorizzati(codTipoOrario, oreSettimanaliMedie, retribuzioneInserita)) {
			return null;
		}
		String tipoOrario          = codTipoOrario; 
		Float lordoMensile         = dto.getLordoMensile().floatValue(); 
		Long mensilita	           = dto.getMensilita().longValue();        
		Float divisoreOrario       = dto.getDivisoreOrario().floatValue();
		Float minutiFloat          = null;
		Long minutiSettimanaliMedi = null;

		if (oreSettimanaliMedie != null) {
			minutiFloat          = oreSettimanaliMedie.floatValue()*60;
			minutiSettimanaliMedi = minutiFloat.longValue();
		}

		Long retribuzioneLordaAnnualeCalcolata = ComunicazioneTracciatoUnicoDTO.calcolaRetribuzioneCompenso(tipoOrario, 
				lordoMensile, 
				mensilita, 
				divisoreOrario, 
				minutiSettimanaliMedi);

		return retribuzioneLordaAnnualeCalcolata;
	}

	private static boolean datiObbligatoriValorizzati(String codiceTipoOrario, Long oreMedie, Long retribuzioneInserita) {


		if (codiceTipoOrario == null
				|| (!UtilConstant.COD_TIPO_ORARIO_TEMPO_PIENO.equals(codiceTipoOrario) && !UtilConstant.COD_TIPO_ORARIO_NON_DEFINITO.equals(codiceTipoOrario) && oreMedie == null) 
				|| retribuzioneInserita==null
				) {
			return false;
		}
		return true;
	}	
	
	public static Long determinaIdLivelloRetribuzioneByCodCcnlCodLivello(String codCcnl, String codLivello, DecodificaDad decodificaDad) throws Exception {
		try {
			return decodificaDad.determinaIdLivelloRetribuzioneByCodCcnlCodLivello(codCcnl, codLivello, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	public static Long determinaTuplaLivelloAndCalcolaRetribuzione (Long idLivello, String codTipoOrario, Long oreSettimanaliMedie, Long retribuzioneInserita, DecodificaDad decodificaDad) throws Exception {
		LivelloRetribuzione tuplaLivello = decodificaDad.getLivelloRetribuzioneById(idLivello);
		if (tuplaLivello != null) {
			//andiamo a calcolare la retribuzione prevista dalla tabella 
			if (retribuzioneInserita != null) {
				return  CalcoloRetribuzioneHelper.calcolaRetribuzione(tuplaLivello,codTipoOrario,oreSettimanaliMedie,retribuzioneInserita );
			}
		}
		return null;
	}
	
	public static boolean isTipoContrattoVincolatoCongruenteCCNL(String ccnl, String tipoContratto, Vector tipiContrattoVoncolati) {
		
		//se il CCNL = ND il tipo contratto deve essere compreso nella lista dei contratti vincolati
		//altrimenti NON E' AMMESSA L'ACCOPPIATA
		//se il CCNL <> ND il contratto non deve essere uno di quelli compresi nella lista dei contratti vicolati 
		
		if (ccnl != null 
				&& !"".equals(ccnl)
				&& tipoContratto != null 
				&& !"".equals(tipoContratto)
				&& tipiContrattoVoncolati != null 
				&& tipiContrattoVoncolati.size()>0) {
			
			if (COD_MIN_CCNL_ND.equalsIgnoreCase(ccnl)
					&& isTipoContrattoPresenteInVincolatiCnnl(tipoContratto, tipiContrattoVoncolati)) {
				return true;
			} 
			if (!COD_MIN_CCNL_ND.equalsIgnoreCase(ccnl)
					&& !isTipoContrattoPresenteInVincolatiCnnl(tipoContratto, tipiContrattoVoncolati)) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean isTipoContrattoPresenteInVincolatiCnnl (String tipoContrattoCO, Vector tipiContrattoVoncolati) {
		for (int i=0;i<tipiContrattoVoncolati.size();i++) {
			String tipoContrattoVincolato = (String) tipiContrattoVoncolati.get(i);
			if (tipoContrattoVincolato.equalsIgnoreCase(tipoContrattoCO)) {
				return true;
			}
		}
		return false;
	}
	public static boolean checkValoreRetribuzioneIfControlloAttivoAndDeterminaSeValida (Long retribuzione, String codiceContratto, CommonDad commonDad) throws Exception {
		String controlloBloccanteRetribuzioneUgualeAZeroAttivo = commonDad.getParametroCommaxById(ParametriConstants.CONTROLLI_BLOCCANTE_RETRIBUZIONE_UGUALEZERO_ATTIVO).getValoreParametro();
		String elencoContrattiAmmessiConRetribuzioneZero = ""; 
		elencoContrattiAmmessiConRetribuzioneZero = commonDad.getParametroCommaxById(ParametriConstants.ELENCO_CONTRATTI_AMMETTONO_RETRIBUZIONEZERO_SE_CONTROLLO_BLOCCANTE_ATTIVO).getValoreParametro();
		
		Vector vCont = new Vector();
		if (elencoContrattiAmmessiConRetribuzioneZero != null 
				&& !"".equals(elencoContrattiAmmessiConRetribuzioneZero) 
				&& elencoContrattiAmmessiConRetribuzioneZero.indexOf(";") != -1) {
			vCont = Util.tokenizerForParameterValues(elencoContrattiAmmessiConRetribuzioneZero, ";");
		}
		
		if ("S".equalsIgnoreCase(controlloBloccanteRetribuzioneUgualeAZeroAttivo)
				&& !Util.isVuoto(Format.toString(retribuzione))
				&& retribuzione.equals(0L) ) {
			
			//
			
			
			if (vCont != null && vCont.size() > 0) {
				for (int i=0;i<vCont.size();i++) {
					String tipoContratto = (String) vCont.get(i);
					if (tipoContratto.equalsIgnoreCase(codiceContratto)) {
						return true;
					}
				}	
			}
			
			return false;
		}
		return true;		
	}


	
	// tiro su la riga del CCNL con il codMin in input e verifico se il settore è ente pubblico
	public static boolean isCCNLEntePubblico(String ccnl, DecodificaDad decodificaDad) throws Exception {
		Ccnl ccnlDb = (Ccnl) decodificaDad.getTfromMin(Ccnl.class, ccnl, null);
		if (ccnlDb != null) {
			String settore = ccnlDb.getSettore();
			if (UtilConstant.SETTORE_ENTE_PUBBLICO.equalsIgnoreCase(settore)) {
				return true;
			}
		}
		
		return false;
	}
}

