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
package it.csi.comonl.comonlweb.ejb.util.commax.util;

import it.csi.spicom.util.TUConstants;

import java.util.Properties;


public class UtilMessaggi {

	private static UtilMessaggi _singleton;
	private static final String _constantPropertiesFilePath = "/it/csi/comonl/comonlweb/ejb/util/commax/util/constanterror.properties";
	private static Properties _p;
	  
	private static UtilMessaggi getSingleton()
	{
		if (_singleton == null)
		{
			_singleton = new UtilMessaggi();
		}
		return _singleton;
	}

	private UtilMessaggi() {
		try
		{
			_p = new Properties();
			_p.load(this.getClass().getResourceAsStream(_constantPropertiesFilePath));
			//non posso usare questo caricamento a runtime perche' non e' ancora instanziato il serviceLocator
			//_p.load(XMLUtility.getInputStreamDaFileODaDb(_constantPropertiesFilePath));
		}
		catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static String getConstant(String constantKey) {
		//System.out.println(constantKey+" -> "+ConstantManager.getSingleton()._p.get(constantKey));
		if (UtilMessaggi.getSingleton()._p.containsKey(constantKey)) {
			return ((String) UtilMessaggi.getSingleton()._p.get(constantKey)).trim();
		}
		return null;
	}



	public static Long getMessaggioErrore (String tipoComunicazione, String messaggio, boolean isUnilav, boolean isUnisomm, boolean isVardatori, boolean printMsgMissione) {

		String i = getConstant(determinaCostanteDaTipoComunicazione(tipoComunicazione, isUnilav, isUnisomm, isVardatori, printMsgMissione)+ messaggio);
		if (Util.isNotVoid(i)) {
			return new Long(i);
		}
		return new Long (0);
	}
	public static String determinaCostanteDaTipoComunicazione(String tipoComunicazione, boolean isUnival, boolean isUnisomm, boolean isVardatori, boolean printMsgMissione ) {
		
		if (isUnisomm && printMsgMissione) {
			return ConstantErrorsMessagesParteComune.MISSIONE;
			
		} else if (tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)
				|| tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_FINE_MISSIONE)) {
			//cessazione
			return ConstantErrorsMessagesParteComune.DATI_CESSAZIONE;

		} else if (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)
				|| tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA_CONTESTUALE_A_PROROGA_DI_MISSIONE)
				|| tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA_MISSIONE)) {
			//proroga
			return ConstantErrorsMessagesParteComune.DATI_PROROGA;

		} else if (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||
				tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE_CON_MISSIONE) 
				|| tipoComunicazione.equals(UtilConstant.TRASFERIMENTO)
				|| tipoComunicazione.equals(UtilConstant.DISTACCO)){
			//trasformazione
			return ConstantErrorsMessagesParteComune.DATI_TRASFORMAZIONE;

		} else if (tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE)
				 || tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE_CONTESTUALE_A_INIZIO_MISSIONE  ))  {
			return ConstantErrorsMessagesParteComune.DATI_ASSUNZIONE;

		} else if (isVardatori) {
			return ConstantErrorsMessagesParteComune.RAPPLAV_VARDATORI;
			
		} 
		return "";
	}
}
