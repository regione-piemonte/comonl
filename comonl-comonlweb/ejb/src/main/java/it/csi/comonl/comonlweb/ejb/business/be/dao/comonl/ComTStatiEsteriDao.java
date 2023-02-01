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
import it.csi.comonl.comonlweb.ejb.entity.ComTStatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Data Access Object interface for the entity ComTStatiEsteri
 */
public interface ComTStatiEsteriDao extends BaseEntityDao<Long, ComTStatiEsteri> {

	List<ComTStatiEsteri> findByField(String field);
	
	List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro);

	List<ComTStatiEsteri> findValidDate();
	
	Optional<ComTStatiEsteri> findByCodStatoMin(String codMin);
	
	Optional<ComTStatiEsteri> findBySiglaNazione(String siglaNazione);
	
	Optional<ComTStatiEsteri> findValid(ComTStatiEsteri entity, Date date);
}
