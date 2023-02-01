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
package it.csi.comonl.comonlweb.ejb.business.be.dad;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDTutoreDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDTutore;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Tutore;



/**
 * Data Access Delegate for tutore
 */
@ApplicationScoped
public class TutoreDad extends BaseDad {

	@Inject
	private ComDTutoreDao comDTutoreDao;
	
	public Tutore getTutoreById(Long idTutore) {
		Optional<Tutore> tutoreFinded = comDTutoreDao.findOne(idTutore).map(ComonlMappers.TUTORE::toModel);
		return tutoreFinded.get();
	}
	
	
	public Tutore insertTutore(Tutore model) {
		ComDTutore entity = ComonlMappers.TUTORE.toEntity(model);
		return ComonlMappers.TUTORE.toModel(comDTutoreDao.insert(entity));
	}
	
	public Tutore updateTutore(Tutore model) {
		ComDTutore entity = ComonlMappers.TUTORE.toEntity(model);
		return ComonlMappers.TUTORE.toModel(comDTutoreDao.update(entity));
	}
	public void deleteTutore(Long idTutore) {
		comDTutoreDao.delete(idTutore);
	}
}
