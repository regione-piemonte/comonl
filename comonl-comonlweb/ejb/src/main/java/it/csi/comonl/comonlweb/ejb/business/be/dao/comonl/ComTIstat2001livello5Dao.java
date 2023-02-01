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
import it.csi.comonl.comonlweb.ejb.entity.ComTIstat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Data Access Object interface for the entity ComTIstat2001livello5
 */
public interface ComTIstat2001livello5Dao extends BaseEntityDao<Long, ComTIstat2001livello5> {

	List<ComTIstat2001livello5> findByField(String field);
	
	List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro);
	
	Optional<ComTIstat2001livello5> findValid(ComTIstat2001livello5 comTIstat2001livello5, Date date);
	
	Optional<ComTIstat2001livello5> findByCodiceDescrizione(String codice, String descrizione);
	
}
