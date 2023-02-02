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

import java.util.List;
import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComTMotivoPermesso;

/**
 * Data Access Object interface for the entity ComTMotivoPermesso
 */
public interface ComTMotivoPermessoDao extends BaseEntityDao<Long, ComTMotivoPermesso> {

	List<ComTMotivoPermesso> findByField(String field);

	List<ComTMotivoPermesso> findValidDate();

	Optional<ComTMotivoPermesso> findByCodMin(String codMin);
}
