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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;

public class EseguiFaseInvioEsitoRequest implements BaseRequest {

	private UplDocumenti uploadDocumenti;
	private  ComunicazioneDad comunicazioneDad;
	private  CommonDad commonDad;
	
	public EseguiFaseInvioEsitoRequest(UplDocumenti uploadDocumenti,ComunicazioneDad comunicazioneDad, CommonDad commonDad) {
		
		
		this.uploadDocumenti = uploadDocumenti;
		this.comunicazioneDad = comunicazioneDad;
		this.commonDad = commonDad;
	}

	public UplDocumenti getUploadDocumenti() {
		return uploadDocumenti;
	}

	public void setUploadDocumenti(UplDocumenti uploadDocumenti) {
		this.uploadDocumenti = uploadDocumenti;
	}

	public ComunicazioneDad getComunicazioneDad() {
		return comunicazioneDad;
	}

	public void setComunicazioneDad(ComunicazioneDad comunicazioneDad) {
		this.comunicazioneDad = comunicazioneDad;
	}

	public CommonDad getCommonDad() {
		return commonDad;
	}

	public void setCommonDad(CommonDad commonDad) {
		this.commonDad = commonDad;
	}

}

	

