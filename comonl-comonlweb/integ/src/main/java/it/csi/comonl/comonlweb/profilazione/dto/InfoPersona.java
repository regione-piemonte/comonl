/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION AAEP, IRIDE submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.profilazione.dto;

public class InfoPersona {

	private String mail;
	private String codiceEnte;
	private String[] province;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String[] getProvince() {
		return province;
	}

	public void setProvince(String[] province) {
		this.province = province;
	}

	public String getCodiceEnte() {
		return codiceEnte;
	}

	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}

	@Override
	public String toString() {
		return "InfoPersona [mail=" + mail + ", codiceEnte=" + codiceEnte + ", province=" + province + "]";
	}

}
