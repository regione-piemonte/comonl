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
import it.csi.comonl.comonlweb.ejb.entity.ComDRapporto;

/**
 * Data Access Object interface for the entity ComDRapporto
 */
public interface ComDRapportoDao extends BaseEntityDao<Long, ComDRapporto> {

	List<ComDRapporto> findByField(String field);
	
	Optional<ComDRapporto> findByIdComunicazione(Long idComunicazione);
	
	List<ComDRapporto> findRapportiByIdComunicazione(Long idComunicazione);
	
	Optional<ComDRapporto> findByTipoRapportoAzienda(Long idComunicazione, String tipoRapportoAzienda);

	List<ComDRapporto> findRapportiByIdTutore(Long idTutore);
}
