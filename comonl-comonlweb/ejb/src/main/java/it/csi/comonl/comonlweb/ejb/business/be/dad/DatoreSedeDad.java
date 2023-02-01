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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRDatoreSedeDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDSedeLavoro;
import it.csi.comonl.comonlweb.ejb.entity.ComRDatoreSede;
import it.csi.comonl.comonlweb.ejb.exception.NotFoundException;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DatoreSede;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;





/**
 * Data Access Delegate for comunicazione
 */
@ApplicationScoped
public class DatoreSedeDad extends BaseDad {

	@Inject
	private ComRDatoreSedeDao comRSedeDatoreDao;
	
	
	public DatoreSede insertDatoreSede(DatoreSede model) {
		ComRDatoreSede entity = ComonlMappers.DATORE_SEDE.toEntity(model);
		return ComonlMappers.DATORE_SEDE.toModel(comRSedeDatoreDao.insert(entity));
	}


	public SedeLavoro getSedeByIdDatore(Long idDatore) {
		Optional<ComRDatoreSede> datoreSedeFinded = comRSedeDatoreDao.findSedeLavoroByIdDatore(idDatore);
		if(!datoreSedeFinded.isPresent()) {
			throw new NotFoundException("SedeByIdDatore");
		}else {
			ComDSedeLavoro entity = datoreSedeFinded.get().getComDSedeLavoro();
			return ComonlMappers.SEDE_LAVORO.toModel(entity);
		}
	}


}
