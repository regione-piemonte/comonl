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

import java.sql.Timestamp;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComTCommaxParametri;

/**
 * Data Access Object interface for the entity ComTCommaxParametri
 */
public interface ComTCommaxParametriDao extends BaseEntityDao<Long, ComTCommaxParametri> {

	List<ComTCommaxParametri> findByField(String field);
	Timestamp getNowFromDB();
	boolean tryLockSemaforo();
	boolean unLockSemaforo();
}
