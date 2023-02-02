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

import it.csi.comonl.comonlweb.ejb.business.be.dad.BaseDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTracciato;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class ValidatorFactory {
	
	public static Validator<Comunicazione> getValidator (WrapperComunicazione wrapperComunicazione,  BaseDad... baseDad){
		TipoTracciato tipoTracciato = wrapperComunicazione.getComunicazione().getTipoTracciato();
		
		if (tipoTracciato.getId().equals(ComonlConstants.TIPO_TRACCIATO_UNILAV_ID)) {
			return new ValidatorUnilav(wrapperComunicazione, (ComunicazioneDad) baseDad[0], (ControlliDad) baseDad[1], (DecodificaDad)  baseDad[2]);
		}
		if (tipoTracciato.getId().equals(ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID)) {
				return new ValidatorUnisomm(wrapperComunicazione, (ComunicazioneDad) baseDad[0], (ControlliDad) baseDad[1], (DecodificaDad)  baseDad[2]);
		}
		if (tipoTracciato.getId().equals(ComonlConstants.TIPO_TRACCIATO_VARDATORI_ID)) {
			return new ValidatorVardatori(wrapperComunicazione, (ComunicazioneDad) baseDad[0], (ControlliDad) baseDad[1], (DecodificaDad)  baseDad[2]);
		}
		return null;
	}
}
