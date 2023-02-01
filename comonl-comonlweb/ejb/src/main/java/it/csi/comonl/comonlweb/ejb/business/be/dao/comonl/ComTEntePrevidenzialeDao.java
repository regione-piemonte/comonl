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
package it.csi.comonl.comonlweb.ejb.business.be.dao.comonl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComTEntePrevidenziale;

/**
 * Data Access Object interface for the entity ComTEntePrevidenziale
 */
public interface ComTEntePrevidenzialeDao extends BaseEntityDao<Long, ComTEntePrevidenziale> {

	List<ComTEntePrevidenziale> findByField(String field);
	
	List<ComTEntePrevidenziale> findValidDate();
	
	Optional<ComTEntePrevidenziale> findValid(ComTEntePrevidenziale comTEntePrevidenziale, Date date);
}
