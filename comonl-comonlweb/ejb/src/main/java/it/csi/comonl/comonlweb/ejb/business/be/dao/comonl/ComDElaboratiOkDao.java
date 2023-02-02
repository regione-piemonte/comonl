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

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDElaboratiOk;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ElaboratiOk;

/**
 * Data Access Object interface for the entity ComDElaboratiOk
 */
public interface ComDElaboratiOkDao extends BaseEntityDao<Long, ComDElaboratiOk> {

	List<ComDElaboratiOk> findByField(String field);
	
	ComDElaboratiOk esisteEsitoInComDElaboratiOk(ElaboratiOk elaboratiOkDto);
	
	List<ComDElaboratiOk> getEsitoComDElaboratiOkByIdUplDoc (Long idComDUplDocumenti);
}
