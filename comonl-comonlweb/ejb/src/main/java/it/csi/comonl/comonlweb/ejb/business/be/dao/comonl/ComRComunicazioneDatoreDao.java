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
import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComRComunicazioneDatore;
import it.csi.comonl.comonlweb.ejb.entity.ComRComunicazioneDatorePK;

/**
 * Data Access Object interface for the entity ComRComunicazioneDatore
 */
public interface ComRComunicazioneDatoreDao extends BaseEntityDao<ComRComunicazioneDatorePK, ComRComunicazioneDatore> {

	List<ComRComunicazioneDatore> findByField(String field);
	
	Optional<ComRComunicazioneDatore> findDatoreByIdComunicazione(Long idComunicazione);
	public List<ComRComunicazioneDatore> findLegameCoDatoreByIdComunicazione(Long idComunicazione) ;
}
