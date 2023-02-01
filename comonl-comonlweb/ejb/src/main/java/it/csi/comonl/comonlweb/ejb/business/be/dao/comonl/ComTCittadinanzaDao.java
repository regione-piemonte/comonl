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

import java.util.List;
import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComTCittadinanza;

/**
 * Data Access Object interface for the entity ComTCittadinanza
 */
public interface ComTCittadinanzaDao extends BaseEntityDao<Long, ComTCittadinanza> {

	List<ComTCittadinanza> findByField(String field);

	List<ComTCittadinanza> findValidDate();

	Optional<ComTCittadinanza> findByCodMin(String codMin);
	Optional<ComTCittadinanza> findByCodMf(String codMf);
}
