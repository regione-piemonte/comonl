/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - LIB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.dto.comunicazione;

import java.io.Serializable;
import java.math.BigDecimal;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

public class RecuperoComunicazione extends BaseDto<BigDecimal> implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal idComunicazione;
	private String esitoInvio;
	private String codiceRegionale;
	private String email;

	public BigDecimal getIdComunicazione() {
		return idComunicazione;
	}

	public void setIdComunicazione(BigDecimal idComunicazione) {
		this.idComunicazione = idComunicazione;
	}

	public String getEsitoInvio() {
		return esitoInvio;
	}

	public void setEsitoInvio(String esitoInvio) {
		this.esitoInvio = esitoInvio;
	}

	public String getCodiceRegionale() {
		return codiceRegionale;
	}

	public void setCodiceRegionale(String codiceRegionale) {
		this.codiceRegionale = codiceRegionale;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "RecuperoComunicazione [idComunicazione=" + idComunicazione + ", esitoInvio=" + esitoInvio
				+ ", codiceRegionale=" + codiceRegionale + ", email=" + email + "]";
	}

}
