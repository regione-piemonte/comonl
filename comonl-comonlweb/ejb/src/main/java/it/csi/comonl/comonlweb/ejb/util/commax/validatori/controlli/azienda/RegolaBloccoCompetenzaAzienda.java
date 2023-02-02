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

public class RegolaBloccoCompetenzaAzienda extends ControlloAstrattoBloccante {

	public void eseguiControllo(List<ApiError >elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception
	{
		//Azienda non di competenza dell' utente; mi limito a registrare l'errore
		RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_AZIENDA_NON_DI_COMPETENZA_DELL_UTENTE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
//		registraErroreSeEsitoControlloNegativo( false , ErrorConstants.DATORE_AZIENDA_NON_DI_COMPETENZA_DELL_UTENTE);
	}

}
