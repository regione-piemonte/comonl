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
import it.csi.comonl.comonlweb.ejb.entity.ComTComune;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Data Access Object interface for the entity ComTComune
 */
public interface ComTComuneDao extends BaseEntityDao<Long, ComTComune> {

	List<DecodificaGenerica> findGenereciaByFilter(DecodificaGenerica filtro);
	
	Optional<ComTComune> findByCodComuneMin(String codMin);
	
	Optional<ComTComune> findValid(ComTComune entity, Date date);
	
}
