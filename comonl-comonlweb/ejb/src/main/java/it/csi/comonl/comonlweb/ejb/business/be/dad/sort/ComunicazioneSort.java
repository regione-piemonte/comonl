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
package it.csi.comonl.comonlweb.ejb.business.be.dad.sort;

/**
 * Sort mapper for Prospetto.
 */
public enum ComunicazioneSort implements JpaSort {

	ID("id", "t.idComDComunicazione"), 
	ANNO("annoProtocollo", "t.annoProtCom");

	/** The model name. */
	private final String modelName;

	/** The query name. */
	private final String queryName;

	/**
	 * Constructor.
	 *
	 * @param modelName the model name
	 * @param queryName the query name
	 */
	private ComunicazioneSort(String modelName, String queryName) {
		this.modelName = modelName;
		this.queryName = queryName;
	}

	@Override
	public String getQueryName() {
		return queryName;
	}

	@Override
	public String getModelName() {
		return modelName;
	}

	/**
	 * Retrieves the Sort by its model name.
	 *
	 * @param modelName the model name
	 * @return the sort
	 */
	public static ComunicazioneSort byModelName(String modelName) {
		for (ComunicazioneSort is : ComunicazioneSort.values()) {
			if (is.modelName.equalsIgnoreCase(modelName)) {
				return is;
			}
		}
		return ComunicazioneSort.ID;
	}

}
