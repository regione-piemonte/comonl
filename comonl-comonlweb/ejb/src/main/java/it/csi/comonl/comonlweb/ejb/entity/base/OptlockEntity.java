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
package it.csi.comonl.comonlweb.ejb.entity.base;

import java.util.UUID;

/**
 * Marker interface for an entity with Optlock
 */
public interface OptlockEntity {

	/**
	 * @return the id
	 */
	UUID getOptlock();
	/**
	 * @param id the id to set
	 */
	void setOptlock(UUID id);
	/**
	 * Generates a new Optlock
	 */
	void generateNewOptlock();
}
