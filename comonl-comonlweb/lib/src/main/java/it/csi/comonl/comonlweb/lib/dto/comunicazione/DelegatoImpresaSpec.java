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
import java.util.List;

/**
 * The Class DelegatoImpresa.
 */
public class DelegatoImpresaSpec extends DelegatoImpresa implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private List<Delega> delegas;

	public List<Delega> getDelegas() {
		return delegas;
	}

	public void setDelegas(List<Delega> delegas) {
		this.delegas = delegas;
	}
	
}
