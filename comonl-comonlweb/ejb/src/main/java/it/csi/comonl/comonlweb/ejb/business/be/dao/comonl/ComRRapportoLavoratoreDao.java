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
import it.csi.comonl.comonlweb.ejb.entity.ComRRapportoLavoratore;
import it.csi.comonl.comonlweb.ejb.entity.ComRRapportoLavoratorePK;

/**
 * Data Access Object interface for the entity ComRRapportoLavoratore
 */
public interface ComRRapportoLavoratoreDao extends BaseEntityDao<ComRRapportoLavoratorePK, ComRRapportoLavoratore> {

	List<ComRRapportoLavoratore> findByField(String field);
	
	Optional<ComRRapportoLavoratore> findLavoratoreByIdRapporto(Long idRapporto);
	
	List<ComRRapportoLavoratore> findLavoratoriByIdRapporto(Long idRapporto);
	
	ComRRapportoLavoratore getLavoratoreByIdRapporto(Long idRapporto);
	
	void deleteByIdLavoratore(Long idLavoratore);
}
