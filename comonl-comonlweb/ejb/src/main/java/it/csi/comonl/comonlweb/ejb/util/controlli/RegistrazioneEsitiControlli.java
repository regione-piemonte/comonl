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
package it.csi.comonl.comonlweb.ejb.util.controlli;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class RegistrazioneEsitiControlli {

	public static boolean registraErroreSeEsitoControlloNegativo(boolean esitoControllo,long idErrore, ApiError e, List<ApiError> errors, String provenienza, CommonDad commonDad) {
		if (esitoControllo==true) {
			return true;
		} else {
		  if (!ComonlConstants.TIPO_PROVENIENZA_COMMAX.equalsIgnoreCase(provenienza)) {
			  errors.add(e);
		  } else {
			  String descErrore = commonDad.getDescrizioneErroreCommaxById(idErrore);
			  errors.add(new ApiError(""+idErrore, descErrore));
		  }
		  
		  return false;
		}
	}
	public static boolean registraErroreSeEsitoControlloNegativo(boolean esitoControllo,long idErrore, ApiError e, List<ApiError> errors, String provenienza, CommonDad commonDad, String parametri) {
		if (esitoControllo==true) {
			return true;
		} else {
			if (!ComonlConstants.TIPO_PROVENIENZA_COMMAX.equalsIgnoreCase(provenienza)) {
				errors.add(e);
			} else {
				String descErrore = commonDad.getDescrizioneErroreCommaxById(idErrore);
				descErrore += "_" + parametri;
				String cfLavoratore = StringUtils.substringBetween(parametri, "(", ")");
				errors.add(new ApiError(""+idErrore, cfLavoratore, descErrore));
			}

			return false;
		}
	}
}
