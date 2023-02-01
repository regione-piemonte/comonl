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

import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComTTrasformazionerl;

/**
 * Data Access Object interface for the entity ComTTrasformazionerl
 */
public interface ComTTrasformazionerlDao extends BaseEntityDao<Long, ComTTrasformazionerl> {

	List<ComTTrasformazionerl> findByField(String field);
	
	List<ComTTrasformazionerl> findByTipo(String tipo);
	
	Optional<ComTTrasformazionerl> findValid(ComTTrasformazionerl comTTrasformazionerl, Date date);
}
