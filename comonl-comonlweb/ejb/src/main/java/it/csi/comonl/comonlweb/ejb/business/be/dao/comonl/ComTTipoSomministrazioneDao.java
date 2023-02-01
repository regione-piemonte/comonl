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

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoSomministrazione;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Data Access Object interface for the entity ComTTipoSomministrazione
 */
public interface ComTTipoSomministrazioneDao extends BaseEntityDao<Long, ComTTipoSomministrazione> {

	List<ComTTipoSomministrazione> findByField(String field);
	
	List<DecodificaGenerica> findByFilter(DecodificaGenerica filtro);
}
