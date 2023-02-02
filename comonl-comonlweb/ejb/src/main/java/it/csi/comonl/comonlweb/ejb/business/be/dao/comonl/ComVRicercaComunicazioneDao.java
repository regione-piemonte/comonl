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

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComVRicercaComunicazione;
import it.csi.comonl.comonlweb.ejb.util.jpa.Page;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;

/**
 * Data Access Object interface for the entity ComDComunicazione
 */
public interface ComVRicercaComunicazioneDao extends BaseEntityDao<Long, ComVRicercaComunicazione> {

	//List<ComVRicercaComunicazione> findByField(String field);
	
	public Long countRicercaComunicazione(FormRicercaComunicazione ricercaComunicazione);
	
	public Page<ComVRicercaComunicazione> findPaginated(int page, int size, String sortField, String sortDirection,
			FormRicercaComunicazione ricercaComunicazione, int limMaxRisultati);
	
//	public Page<ComVRicercaComunicazione> findPaginatedVardatori(int page, int size, String sortField, String sortDirection,
//			FormRicercaComunicazione ricercaComunicazione);
}
