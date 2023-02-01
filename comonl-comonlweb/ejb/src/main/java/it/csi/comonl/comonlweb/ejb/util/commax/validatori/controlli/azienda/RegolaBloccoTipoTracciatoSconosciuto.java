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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.azienda;

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ErrorConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class RegolaBloccoTipoTracciatoSconosciuto extends ControlloAstrattoBloccante {

	public void eseguiControllo(List<ApiError >elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad
) throws Exception
	{
		RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.TIPO_TRACCIATO_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		//Tipo tracciato non ammesso; mi limito a registrare l'errore
//		registraErroreSeEsitoControlloNegativo( false , ErrorConstants.TIPO_TRACCIATO_NON_AMMESSO);
	}
}
