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

import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class ValidatorUtility {

	public static boolean isTipoContrattoApprendistato(TipoContratti tipoContratti) {
		return  tipoContratti!=null &&
				tipoContratti.getCodTipoContrattoMin()!=null && (
				tipoContratti.getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0308)||
				tipoContratti.getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0309)||
				tipoContratti.getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0310));		
	}
	public static boolean isTipoContrattoRipartito(TipoContratti tipoContratti) {
		return  tipoContratti !=null && tipoContratti.getCodTipoContrattoMin()!=null && tipoContratti.getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0702);		
	}
	public static boolean isTipoContrattoTirocinio(TipoContratti tipoContratti) {
		return  tipoContratti !=null && tipoContratti.getTipo()!=null && tipoContratti.getTipo().equalsIgnoreCase("TR");		
	}
	public static boolean isLavoroStagionale(Rapporto rapporto) {
		return rapporto !=null && rapporto.getFlgLavStagionale()!=null && rapporto.getFlgLavStagionale().equalsIgnoreCase(ComonlConstants.FLAG_S);
	}
	public static boolean isTipoContrattoIndeterminato(TipoContratti tipoContratti) {
		return tipoContratti!=null && tipoContratti.getFlgForma()!=null && tipoContratti.getFlgForma().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_FLGFORMA_I);
	}
	
	public static int getDiffInMonths(java.util.Date a, java.util.Date b) {
		if (a== null || b == null)
			return 0;
		
		Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(a);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(b);
        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = (diffYear * 12) + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        return diffMonth;
	}
	
	public static int getEtaLavoratore (java.util.Date dataNascita, java.util.Date allaData) {
		if (dataNascita== null || allaData == null)
			return 0;
		
		Calendar dataNascitaGC = new GregorianCalendar();
		dataNascitaGC.setTime(dataNascita);
		
        Calendar allaDataGC = new GregorianCalendar();
        allaDataGC.setTime(allaData);
        
        int diffYear = allaDataGC.get(Calendar.YEAR) - dataNascitaGC.get(Calendar.YEAR);
        
        dataNascitaGC.set(Calendar.YEAR, allaDataGC.get(Calendar.YEAR));
        if (dataNascitaGC.compareTo(allaDataGC)>0)
        	diffYear= diffYear-1;
        
        return diffYear;
	}
}
