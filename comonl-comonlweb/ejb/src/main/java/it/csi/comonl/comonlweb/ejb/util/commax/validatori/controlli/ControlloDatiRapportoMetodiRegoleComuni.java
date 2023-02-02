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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ConstantErrorsMessagesParteComune;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtilities;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.Util;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilMessaggi;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;
import it.csi.spicom.dto.InizioMissioneDTO;
import it.csi.spicom.dto.InizioRapportoUnilavDTO;
import it.csi.spicom.util.TUConstants;


public class ControlloDatiRapportoMetodiRegoleComuni extends ControlloAstratto {

	
	//---------Adeguamenti 2016
		public ArrayList validateAssunzioneObbligatoria(InizioRapportoUnilavDTO assunzione, 
				                                     InizioMissioneDTO missione, 
				                                     String tipoComunicazione, 
				                                     boolean isUnilav,
				                                     boolean isUnisomm,
				                                     boolean isVardatori, 
				                                     boolean printMsgMissione, 
				                                     DecodificaDad decodificaDad, 
				                                     CommonDad commonDad) throws  Exception{
			String flgAssunzioneObbligatoria = null;
			String categLavAssObbl = null;
			ArrayList vMessaggiErroreToDisplay = new ArrayList();
			
			
			if (Util.isNotVoid(assunzione)) {
				flgAssunzioneObbligatoria = assunzione.getFlgAssunzioneObbligatoria();
				categLavAssObbl = assunzione.getCategoriaLavoratoreAssunzioneObbligatoria();
			} else if (Util.isNotVoid(missione)) {
				flgAssunzioneObbligatoria = missione.getFlgAssunzioneObbligatoria();
				categLavAssObbl = missione.getCategoriaLavoratoreAssunzioneObbligatoria();
			}

			if (Util.isVoid(flgAssunzioneObbligatoria) ){

				vMessaggiErroreToDisplay.add(UtilMessaggi.getMessaggioErrore(tipoComunicazione, ConstantErrorsMessagesParteComune._FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO, isUnilav, isUnisomm, isVardatori, printMsgMissione));
			}

			if (Util.isNotVoid(flgAssunzioneObbligatoria) 
					&& UtilConstant.FLAG_SI.equalsIgnoreCase(flgAssunzioneObbligatoria)
					&& Util.isVoid(categLavAssObbl)) {

				vMessaggiErroreToDisplay.add(UtilMessaggi.getMessaggioErrore(tipoComunicazione, ConstantErrorsMessagesParteComune._CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA, isUnilav, isUnisomm, isVardatori, printMsgMissione));
			}

			if (Util.isNotVoid(flgAssunzioneObbligatoria )
					&& UtilConstant.FLAG_NO.equalsIgnoreCase(flgAssunzioneObbligatoria)
					&& Util.isNotVoid(categLavAssObbl)) {

				vMessaggiErroreToDisplay.add(UtilMessaggi.getMessaggioErrore(tipoComunicazione, ConstantErrorsMessagesParteComune._FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI, isUnilav, isUnisomm, isVardatori, printMsgMissione));

			}
			if (Util.isNotVoid(categLavAssObbl)) {

				boolean categAmmessa = false;
				CategLavAssObbl categLavAssObblDb = (CategLavAssObbl) decodificaDad.getTfromMin(CategLavAssObbl.class, categLavAssObbl, null);
				categAmmessa = (categLavAssObblDb != null && categLavAssObblDb.getId() !=null ? true : false);

				if (categAmmessa && isUnisomm) {
					categAmmessa = isCategoriaLavoratorePerAssunzioneObbligatoriaAmmessoUnisomm(categLavAssObbl, commonDad, decodificaDad);
				}
				if (!categAmmessa) {
					vMessaggiErroreToDisplay.add(UtilMessaggi.getMessaggioErrore(tipoComunicazione, ConstantErrorsMessagesParteComune._CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA, isUnilav, isUnisomm, isVardatori, printMsgMissione));				
				}
			}
			return vMessaggiErroreToDisplay;
		}

		
		
		public boolean isCategoriaLavoratorePerAssunzioneObbligatoriaAmmessoUnisomm (String categoriaLavoratore, CommonDad commonDad, DecodificaDad decodificaDad) throws  Exception{
			boolean isCategoriaTrovata = false;
			//id dele categorie ammesse per unisomma dalla tabella params
			String categorieAmmesse = commonDad.getParametroCommaxById(ParametriConstants.VINCOLI_CATEG_LAV_ASS_OBBL_AMMESSE_UNISOMM).getValoreParametro();
			// id corrispondente al codice ministeriale
			CategLavAssObbl categLavAssObbl = (CategLavAssObbl) decodificaDad.getTfromMin(CategLavAssObbl.class, categoriaLavoratore, null);
			String idCategoriaAmmessaByCodMinisteriale = String.valueOf(categLavAssObbl.getId());
			
			if (categorieAmmesse.indexOf(";") != -1) {
				Vector vCat = new Vector();
				vCat = Util.tokenizerForParameterValues(categorieAmmesse, ";");

				for (int i=0;i<vCat.size();i++) {
					
					if (idCategoriaAmmessaByCodMinisteriale.equals(vCat.get(i)) ) {
						isCategoriaTrovata = true;
						break;
					}
				}
			} else  {
				if (idCategoriaAmmessaByCodMinisteriale.equals(categorieAmmesse) ) {
					isCategoriaTrovata = true;
				}
			}
			return isCategoriaTrovata;
		}

		public void eseguiControllo(List<ApiError >elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception {
			// TODO Auto-generated method stub
			
		}

//		public void registraEsito(EsitoErrore esito) throws Exception {
//			// TODO Auto-generated method stub
//			
//		}
		

		/* 
		 * parametri in input
		 * Date dataFinePeriodoFormativo --dataFinePeriodoFormativo
		 * Date dataInizioRapporto       -dataInizioRapporto
		 * String tipoComunicazione,     --tipoComunicazione (ASS-CES-TRS-TRD-PRO)
		 * String lavoroStagionale,      --flgLavoroStagionale
		 * Date dataFineRapporto,        --dataFineRapporto
		 * boolean isUnilav,             --tipo tracciato
         * boolean isUnisomm,            --tipo tracciato
         * boolean isVardatori,          --tipo tracciato
         * boolean isApprendistato,      --tipo contratto 
         * boolean printMsgMissione      --indicazione se il messaggio deve iniziare con MISSIONE_ perche' il dato controllato fa parte del tag missione dell'oggettone    
		 * */
		public ArrayList validateDataFinePeridioFormativo(Date dataFinePeriodoFormativo,  
				Date dataInizioRapporto,
				String tipoComunicazione,
				String lavoroStagionale,
				Date dataFineRapporto,
				boolean isUnilav,
                boolean isUnisomm,
                boolean isVardatori,
                boolean isApprendistato, 
                boolean printMsgMissione) throws Exception{
			
			
			
			ArrayList vMessaggiErroreToDisplay = new ArrayList();
			
			if(isApprendistato){
				
//				NON E' AMMESSA: [msg: _DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO]
//				. UNILAV    - PRO
//				. UNILAV    - TRS
//				. UNILAV    - TRD
				if (isUnilav 
						&& !TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE.equals(tipoComunicazione)
						&& !TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE.equals(tipoComunicazione)
						&& Util.isNotVoid(dataFinePeriodoFormativo)) {
					vMessaggiErroreToDisplay.add(UtilMessaggi.getMessaggioErrore(tipoComunicazione, ConstantErrorsMessagesParteComune._DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO,isUnilav,isUnisomm,isVardatori, printMsgMissione));
				} else {
//					E' OBBLIGATORIA: [msg: _DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO]
//					. UNILAV    - ASS
//					. UNILAV    - CES
//					. VARDATORI -  -
//					. UNISOMM   - ASS
//					. UNISOMM   - CES
//					. UNISOMM   - PRO
//					. UNISOMM   - TRS
//					. UNISOMM   - TRD	
					if (isDataFinePeriodoFormativoObbligatoria(tipoComunicazione, isUnilav,isUnisomm, isVardatori,isApprendistato)) {
						
						if (Util.isVoid(dataFinePeriodoFormativo)) {
							vMessaggiErroreToDisplay.add(UtilMessaggi.getMessaggioErrore(tipoComunicazione, ConstantErrorsMessagesParteComune._DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO, isUnilav,isUnisomm,isVardatori, printMsgMissione));
						} else {
//							DEVE ESSERE > DT_INIZIO_RAPPORTO : [msg: _DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO]
//									. UNILAV    - ASS
//									. UNILAV    - CES
//									. VARDATORI -  -
//									. UNISOMM   - ASS
//									. UNISOMM   - CES
//									. UNISOMM   - PRO
//									. UNISOMM   - TRS
//									. UNISOMM   - TRD	

							if( ! DateUtilities.isData1MaggioreDiData2(dataFinePeriodoFormativo, dataInizioRapporto) ) {
								vMessaggiErroreToDisplay.add(UtilMessaggi.getMessaggioErrore(tipoComunicazione, ConstantErrorsMessagesParteComune._DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO,isUnilav,isUnisomm,isVardatori, printMsgMissione));
							}
						}
//						
//						DEVE ESSERE = DT_FINE_RAPPORTO se FLG_STAGIONALE = SI: [msg: DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO]
//								. UNILAV    - ASS
//								. UNILAV    - CES
//								. VARDATORI -  -
//								. non riguarda UNISOMM perche' non ha flg_stagionale
						if(!isUnisomm && lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI)){
							
							if(dataFinePeriodoFormativo != null && !dataFinePeriodoFormativo.equals(dataFineRapporto)){
								vMessaggiErroreToDisplay.add(UtilMessaggi.getMessaggioErrore(tipoComunicazione, ConstantErrorsMessagesParteComune._DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO,isUnilav,isUnisomm,isVardatori, printMsgMissione));									
							}
						} 						
					}
				}
			} else { //NON E' AMMESSA  MAI se non e' un apprendistato
				if (Util.isNotVoid(dataFinePeriodoFormativo)) {
					vMessaggiErroreToDisplay.add(UtilMessaggi.getMessaggioErrore(tipoComunicazione, ConstantErrorsMessagesParteComune._DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO,isUnilav,isUnisomm,isVardatori, printMsgMissione));
				}
			}
		
			return vMessaggiErroreToDisplay;			
		
		}
		
		
		//Obbligatoria se per i contratti di apprendistato siamo in una delle seguenti condizioni
		
		private boolean isDataFinePeriodoFormativoObbligatoria (String tipoComunicazione, boolean isUnilav,boolean isUnisomm, boolean isVardatori,boolean isApprendistato) {
			
			if (isApprendistato) {
				if (isUnilav 
						&& (TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE.equals(tipoComunicazione)
						|| TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE.equals(tipoComunicazione)) ){
					return true;
				}
				if (isUnisomm || isVardatori) {
					return true;
				}
			}
			return false;
		}

}
