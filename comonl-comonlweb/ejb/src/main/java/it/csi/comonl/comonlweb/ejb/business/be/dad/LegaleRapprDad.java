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
package it.csi.comonl.comonlweb.ejb.business.be.dad;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDLegaleRapprDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDLegaleRappr;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.LegaleRappr;

/**
 * Data Access Delegate for LegaleRappr
 */
@ApplicationScoped
public class LegaleRapprDad extends BaseDad {

	@Inject
	private ComDLegaleRapprDao comDLegaleRapprDao;
	
	
	public LegaleRappr insert(LegaleRappr model) {
		ComDLegaleRappr entity = ComonlMappers.LEGALE_RAPPR.toEntity(model);
		return ComonlMappers.LEGALE_RAPPR.toModel(comDLegaleRapprDao.insert(entity));
	}
	
	public LegaleRappr update(LegaleRappr model) {
		ComDLegaleRappr entity = ComonlMappers.LEGALE_RAPPR.toEntity(model);
		return ComonlMappers.LEGALE_RAPPR.toModel(comDLegaleRapprDao.update(entity));
	}
}
