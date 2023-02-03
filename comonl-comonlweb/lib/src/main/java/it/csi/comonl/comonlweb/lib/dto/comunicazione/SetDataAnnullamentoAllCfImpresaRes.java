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

public class SetDataAnnullamentoAllCfImpresaRes {

	private List<DelegatoImpresa> delegatoImpresas;
	private String warning;
	
	public String getWarning() {
		return warning;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}
	public List<DelegatoImpresa> getDelegatoImpresas() {
		return delegatoImpresas;
	}
	public void setDelegatoImpresas(List<DelegatoImpresa> delegatoImpresas) {
		this.delegatoImpresas = delegatoImpresas;
	}
	
	
	
	
		
}
