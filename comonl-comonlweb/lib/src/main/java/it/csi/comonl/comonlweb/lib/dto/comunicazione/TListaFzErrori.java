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

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class TListaFzErrori.
 */
public class TListaFzErrori extends BaseDto<TListaFzErroriPK> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private FzErrori fzErrori;

	/**
	 * @return the fzErrori
	 */
	public FzErrori getFzErrori() {
		return fzErrori;
	}
	
	/**
	 * @param fzErrori the fzErrori to set
	 */
	public void setFzErrori(FzErrori fzErrori) {
		this.fzErrori = fzErrori;
	}

}
