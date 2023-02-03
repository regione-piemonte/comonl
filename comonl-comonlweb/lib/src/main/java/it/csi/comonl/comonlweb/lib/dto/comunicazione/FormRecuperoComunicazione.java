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

import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;

public class FormRecuperoComunicazione implements Serializable {

	private static final long serialVersionUID = 1L;

	private String elencoIds;
	private BigDecimal idDa;
	private BigDecimal idA;
	private Ruolo ruolo;

	public String getElencoIds() {
		return elencoIds;
	}

	public void setElencoIds(String elencoIds) {
		this.elencoIds = elencoIds;
	}

	public BigDecimal getIdDa() {
		return idDa;
	}

	public void setIdDa(BigDecimal idDa) {
		this.idDa = idDa;
	}

	public BigDecimal getIdA() {
		return idA;
	}

	public void setIdA(BigDecimal idA) {
		this.idA = idA;
	}
	

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	@Override
	public String toString() {
		return "FormRecuperoComunicazione [elencoIds=" + elencoIds + ", idDa=" + idDa + ", idA=" + idA + ", ruolo="
				+ ruolo + "]";
	}

	
}
