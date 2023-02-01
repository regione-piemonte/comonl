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
package it.csi.comonl.comonlweb.ejb.entity.base;

/**
 * Marker interface for a model
 * @param <K> the key type
 */
public interface BaseEntity<K> {

	/**
	 * @return the id
	 */
	K getId();
	/**
	 * @param id the id to set
	 */
	void setId(K id);
	/**
	 * Initializes the id with the instance fields, if applicable
	 */
	default void initId() {
		// May be overridden
	}
}
