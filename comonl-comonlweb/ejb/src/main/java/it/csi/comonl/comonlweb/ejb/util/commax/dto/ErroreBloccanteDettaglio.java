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
package it.csi.comonl.comonlweb.ejb.util.commax.dto;

public class ErroreBloccanteDettaglio {

	public Long idComTListaErroriFz;
	public Long idComDFzErrori;
	public Long getIdComTListaErroriFz() {
		return idComTListaErroriFz;
	}
	public void setIdComTListaErroriFz(Long idComTListaErroriFz) {
		this.idComTListaErroriFz = idComTListaErroriFz;
	}
	public Long getIdComDFzErrori() {
		return idComDFzErrori;
	}
	public void setIdComDFzErrori(Long idComDFzErrori) {
		this.idComDFzErrori = idComDFzErrori;
	}
	
}
