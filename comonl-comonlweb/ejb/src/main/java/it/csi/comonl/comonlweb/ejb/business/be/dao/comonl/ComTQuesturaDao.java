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
import it.csi.comonl.comonlweb.ejb.entity.ComTQuestura;

/**
 * Data Access Object interface for the entity ComTQuestura
 */
public interface ComTQuesturaDao extends BaseEntityDao<Long, ComTQuestura> {

	List<ComTQuestura> findByField(String field);

	List<ComTQuestura> findValidDate();

	Optional<ComTQuestura> findByCodMin(String codMin);
}
