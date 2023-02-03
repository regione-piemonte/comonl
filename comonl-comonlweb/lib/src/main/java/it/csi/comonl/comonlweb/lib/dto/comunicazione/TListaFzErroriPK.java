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
 * The Class TListaFzErroriPK.
 */
public class TListaFzErroriPK extends BaseDto<Integer> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private long idComTListaErroriFz;
	private long idComDFzErrori;

	/**
	 * @return the idComTListaErroriFz
	 */
	public long getIdComTListaErroriFz() {
		return idComTListaErroriFz;
	}
	
	/**
	 * @param idComTListaErroriFz the idComTListaErroriFz to set
	 */
	public void setIdComTListaErroriFz(long idComTListaErroriFz) {
		this.idComTListaErroriFz = idComTListaErroriFz;
	}

	/**
	 * @return the idComDFzErrori
	 */
	public long getIdComDFzErrori() {
		return idComDFzErrori;
	}
	
	/**
	 * @param idComDFzErrori the idComDFzErrori to set
	 */
	public void setIdComDFzErrori(long idComDFzErrori) {
		this.idComDFzErrori = idComDFzErrori;
	}

}
