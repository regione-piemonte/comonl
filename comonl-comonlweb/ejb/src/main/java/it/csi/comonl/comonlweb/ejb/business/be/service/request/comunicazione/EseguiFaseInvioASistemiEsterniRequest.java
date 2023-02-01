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

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;

public class EseguiFaseInvioASistemiEsterniRequest implements BaseRequest {

	private  Ruolo ruolo;
	private  Long idUplDocumenti;
	private  ComunicazioneDad comunicazioneDad;
	private  AnagraficaDelegatoDad anagraficaDelegatoDad;
	private  CommonDad commonDad;
	private  DecodificaDad decodificaDad;
	
	public EseguiFaseInvioASistemiEsterniRequest(Ruolo ruolo, Long idUplDocumenti,
			ComunicazioneDad comunicazioneDad, AnagraficaDelegatoDad anagraficaDelegatoDad, CommonDad commonDad,
			DecodificaDad decodificaDad) {
		
		
		this.ruolo = ruolo;
		this.idUplDocumenti = idUplDocumenti;
		this.comunicazioneDad = comunicazioneDad;
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
		this.commonDad = commonDad;
		this.decodificaDad = decodificaDad;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public Long getIdUplDocumenti() {
		return idUplDocumenti;
	}

	public void setIdUplDocumenti(Long idUplDocumenti) {
		this.idUplDocumenti = idUplDocumenti;
	}

	public ComunicazioneDad getComunicazioneDad() {
		return comunicazioneDad;
	}

	public void setComunicazioneDad(ComunicazioneDad comunicazioneDad) {
		this.comunicazioneDad = comunicazioneDad;
	}

	public AnagraficaDelegatoDad getAnagraficaDelegatoDad() {
		return anagraficaDelegatoDad;
	}

	public void setAnagraficaDelegatoDad(AnagraficaDelegatoDad anagraficaDelegatoDad) {
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
	}

	public CommonDad getCommonDad() {
		return commonDad;
	}

	public void setCommonDad(CommonDad commonDad) {
		this.commonDad = commonDad;
	}

	public DecodificaDad getDecodificaDad() {
		return decodificaDad;
	}

	public void setDecodificaDad(DecodificaDad decodificaDad) {
		this.decodificaDad = decodificaDad;
	}
	
	


}

	

