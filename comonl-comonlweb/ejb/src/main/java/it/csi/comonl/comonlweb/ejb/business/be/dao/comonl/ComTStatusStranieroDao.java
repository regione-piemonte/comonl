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
package it.csi.comonl.comonlweb.ejb.business.be.dao.comonl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComTStatusStraniero;

/**
 * Data Access Object interface for the entity ComTStatusStraniero
 */
public interface ComTStatusStranieroDao extends BaseEntityDao<Long, ComTStatusStraniero> {

	List<ComTStatusStraniero> findByField(String field);

	List<ComTStatusStraniero> findValidDate();

	Optional<ComTStatusStraniero> findByCodMin(String codMin);
	
	Optional<ComTStatusStraniero> findValid(ComTStatusStraniero comTStatusStraniero, Date date);
}
