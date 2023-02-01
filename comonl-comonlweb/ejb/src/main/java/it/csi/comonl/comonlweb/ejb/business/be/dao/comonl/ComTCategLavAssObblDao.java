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
import it.csi.comonl.comonlweb.ejb.entity.ComTCategLavAssObbl;

/**
 * Data Access Object interface for the entity ComTCategLavAssObbl
 */
public interface ComTCategLavAssObblDao extends BaseEntityDao<Long, ComTCategLavAssObbl> {

	List<ComTCategLavAssObbl> findByField(String field);
	
	List<ComTCategLavAssObbl> findValidDate();
	
	Optional<ComTCategLavAssObbl> findValid(ComTCategLavAssObbl entity, Date date);
}
