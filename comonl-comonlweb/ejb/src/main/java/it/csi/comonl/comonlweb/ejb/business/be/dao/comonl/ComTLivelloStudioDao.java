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
import it.csi.comonl.comonlweb.ejb.entity.ComTLivelloStudio;

/**
 * Data Access Object interface for the entity ComTLivelloStudio
 */
public interface ComTLivelloStudioDao extends BaseEntityDao<Long, ComTLivelloStudio> {

	List<ComTLivelloStudio> findByField(String field);

	List<ComTLivelloStudio> findValidDate();

	Optional<ComTLivelloStudio> findByCodMin(String codMin);
}
