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
package it.csi.comonl.comonlweb.ejb.business.be.dad.sort;

/**
 * Base interface for a JPA sort
 */
public interface JpaSort {

	/**
	 * @return the query name
	 */
	String getQueryName();
	/**
	 * @return the model name
	 */
	String getModelName();
}
