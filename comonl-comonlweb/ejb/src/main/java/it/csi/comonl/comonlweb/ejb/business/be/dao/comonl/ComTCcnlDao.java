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
import it.csi.comonl.comonlweb.ejb.entity.ComTCcnl;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Data Access Object interface for the entity ComTCcnl
 */
public interface ComTCcnlDao extends BaseEntityDao<Long, ComTCcnl> {

	List<ComTCcnl> findByField(String field);

	List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro);

	Optional<ComTCcnl> findValid(ComTCcnl comTCcnl, Date date);

	Optional<ComTCcnl> findByCodCcnl(String codCcnl);

}
