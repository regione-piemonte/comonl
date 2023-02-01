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
import it.csi.comonl.comonlweb.ejb.entity.ComDDuplicaGruppoLog;

/**
 * Data Access Object interface for the entity ComDDuplicaGruppoLog
 */
public interface ComDDuplicaGruppoLogDao extends BaseEntityDao<Long, ComDDuplicaGruppoLog> {

	List<ComDDuplicaGruppoLog> findByField(String field);
}
