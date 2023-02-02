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

import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContratti;

/**
 * Data Access Object interface for the entity ComTTipoContratti
 */
public interface ComTTipoContrattiDao extends BaseEntityDao<Long, ComTTipoContratti> {

	List<ComTTipoContratti> findByField(String field);
	
	List<ComTTipoContratti> findValidDate();
	
	List<ComTTipoContratti> GetTipoContrattiByTracciatoAndTipoComunicazioneService(String tipoTracciato,String tipoCmunicazione);
	
	Optional<ComTTipoContratti> findValid(ComTTipoContratti comTTipoContratti, Date date);
}
