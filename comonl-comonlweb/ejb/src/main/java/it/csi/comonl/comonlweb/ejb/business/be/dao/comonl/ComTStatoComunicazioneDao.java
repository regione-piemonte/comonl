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
import it.csi.comonl.comonlweb.ejb.entity.ComTStatoComunicazione;

/**
 * Data Access Object interface for the entity ComTStatoComunicazione
 */
public interface ComTStatoComunicazioneDao extends BaseEntityDao<Long, ComTStatoComunicazione> {

	List<ComTStatoComunicazione> findByField(String field);
	
	List<ComTStatoComunicazione> findAllByFlg(Boolean flgRicerca);
}
