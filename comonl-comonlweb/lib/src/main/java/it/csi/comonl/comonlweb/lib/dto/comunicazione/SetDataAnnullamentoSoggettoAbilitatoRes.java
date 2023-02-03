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

import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.ApiError;

public class SetDataAnnullamentoSoggettoAbilitatoRes {

	private Date dataAnnullamento;
	private List<ApiError> warnings;
	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}
	public void setDataAnnullamento(Date dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}
	public List<ApiError> getWarnings() {
		return warnings;
	}
	public void setWarnings(List<ApiError> warnings) {
		this.warnings = warnings;
	}
	
	
		
}
