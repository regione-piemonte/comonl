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
import it.csi.comonl.comonlweb.ejb.entity.ComTTipologiaRapporto;

/**
 * Data Access Object interface for the entity ComTTipologiaRapporto
 */
public interface ComTTipologiaRapportoDao extends BaseEntityDao<String, ComTTipologiaRapporto> {

	List<ComTTipologiaRapporto> findByField(String field);
}
