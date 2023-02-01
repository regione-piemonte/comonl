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
import it.csi.comonl.comonlweb.ejb.entity.ComTAtecofin;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Data Access Object interface for the entity ComTAtecofin
 */
public interface ComTAtecofinDao extends BaseEntityDao<Long, ComTAtecofin> {

	List<ComTAtecofin> findByField(String field);

	Optional<ComTAtecofin> findByCodAteco(String codAteco);

	List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro);
	
	Optional<ComTAtecofin> findValid(ComTAtecofin entity, Date date);

}
