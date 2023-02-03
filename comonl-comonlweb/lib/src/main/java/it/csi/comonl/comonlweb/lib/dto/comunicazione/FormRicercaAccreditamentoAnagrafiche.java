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

import it.csi.comonl.comonlweb.lib.util.ComonlStringUtils;

public class FormRicercaAccreditamentoAnagrafiche {
	
	private String cf;
	private String cognome;
	private String nome;
	private String cfAzienda;
	private String pIva;
	private String denom;
	private Boolean annullate;
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCfAzienda() {
		return cfAzienda;
	}
	public void setCfAzienda(String cfAzienda) {
		this.cfAzienda = cfAzienda;
	}
	public String getpIva() {
		return pIva;
	}
	public void setpIva(String pIva) {
		this.pIva = pIva;
	}
	public String getDenom() {
		return denom;
	}
	public void setDenom(String denom) {
		this.denom = denom;
	}
	public Boolean getAnnullate() {
		return annullate;
	}
	public void setAnnullate(Boolean annullate) {
		this.annullate = annullate;
	}
	
	
	
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RicercaAccreditamentoAnagrafiche [");
		builder.append("cf=" + ComonlStringUtils.checkNull(cf));
		builder.append(",\n ");
		builder.append("nome=" + ComonlStringUtils.checkNull(nome));
		builder.append(",\n ");
		builder.append("cognome=" + ComonlStringUtils.checkNull(cognome));
		builder.append(",\n ");
		builder.append("cfAzienda=" + ComonlStringUtils.checkNull(cfAzienda));
		builder.append(",\n ");
		builder.append("pIva=" + ComonlStringUtils.checkNull(pIva));
		builder.append(",\n ");
		builder.append("denom=" + ComonlStringUtils.checkNull(denom));
		builder.append(",\n ");
		builder.append("annullate=" + ComonlStringUtils.checkNull(annullate));
		builder.append(",\n ");
		
		if (super.toString() != null) {
			builder.append("\n super.toString()=");
			builder.append(super.toString());
		}
		builder.append("]");
		return builder.toString();
	}
	
}
