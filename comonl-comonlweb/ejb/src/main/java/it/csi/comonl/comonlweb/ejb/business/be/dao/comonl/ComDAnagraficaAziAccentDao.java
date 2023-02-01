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
import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDAnagraficaAziAccent;

/**
 * Data Access Object interface for the entity ComDAnagraficaAziAccent
 */
public interface ComDAnagraficaAziAccentDao extends BaseEntityDao<Long, ComDAnagraficaAziAccent> {

	List<ComDAnagraficaAziAccent> findByField(String field);

	Optional<ComDAnagraficaAziAccent> findByCodiceFiscale(String codiceFiscale);
}
