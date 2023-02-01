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
package it.csi.comonl.comonlweb.ejb.util.commax.dto;

public class WarningDettaglio {

	public Long idComTListaWarning;
	public Long idComDWarning;
	
	public Long getIdComDWarning() {
		return idComDWarning;
	}
	public void setIdComDWarning(Long idComDWarning) {
		this.idComDWarning = idComDWarning;
	}
	public Long getIdComTListaWarning() {
		return idComTListaWarning;
	}
	public void setIdComTListaWarning(Long idComTListaWarning) {
		this.idComTListaWarning = idComTListaWarning;
	}
	
}
