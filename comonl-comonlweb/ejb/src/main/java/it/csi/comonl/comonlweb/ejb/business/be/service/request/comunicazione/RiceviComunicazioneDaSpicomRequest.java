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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;

public class RiceviComunicazioneDaSpicomRequest implements BaseRequest {
	
	public ComunicazioneTracciatoUnicoDTO comunicazioneSpicom;
	
	public RiceviComunicazioneDaSpicomRequest(ComunicazioneTracciatoUnicoDTO comunicazioneSpicom) {
		this.comunicazioneSpicom = comunicazioneSpicom;
	}

	public ComunicazioneTracciatoUnicoDTO getComunicazioneSpicom() {
		return comunicazioneSpicom;
	}

	public void setComunicazioneSpicom(ComunicazioneTracciatoUnicoDTO comunicazioneSpicom) {
		this.comunicazioneSpicom = comunicazioneSpicom;
	}

	

}
