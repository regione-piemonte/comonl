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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDSedeLavoroDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRDatoreSedeDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDSedeLavoro;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DatoreSede;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DatoreSedePK;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;



/**
 * Data Access Delegate for sedeLavoro
 */
@ApplicationScoped
public class SedeLavoroDad extends BaseDad {

	@Inject
	private ComDSedeLavoroDao comDSedeLavoroDao;
	
	@Inject
	private ComRDatoreSedeDao comRDatoreSedeDao;
	
	/*
	 * Inserisce la sede di lavoro
	 * */
	public SedeLavoro insertSedeLavoro(SedeLavoro model) {
		ComDSedeLavoro entity = ComonlMappers.SEDE_LAVORO.toEntity(model);
		return ComonlMappers.SEDE_LAVORO.toModel(comDSedeLavoroDao.insert(entity));
	}
	
	/*
	 * Inserisce la sede di lavoro e la relazione sedeLavoro/Datore
	 * */
	public SedeLavoro insertSedeLavoro(SedeLavoro model, Long idDatore) {

		ComDSedeLavoro entity = ComonlMappers.SEDE_LAVORO.toEntity(model);
		entity.setId(null);
		entity = comDSedeLavoroDao.insert(entity);
		comDSedeLavoroDao.flush();
		
		DatoreSede datoreSede = new DatoreSede();
		DatoreSedePK datoreSedePK = new DatoreSedePK();
		datoreSedePK.setIdComDDatore(idDatore);
		datoreSedePK.setIdComDSedeLavoro(entity.getId());
		datoreSede.setId(datoreSedePK);
		comRDatoreSedeDao.insert(ComonlMappers.DATORE_SEDE.toEntity(datoreSede));
		comRDatoreSedeDao.flush();
		
		return ComonlMappers.SEDE_LAVORO.toModel(entity);
	}
	
	public void deleteSedeLavoro(Long idSedeLavoro) {
		comDSedeLavoroDao.delete(idSedeLavoro);
	}
	
	public void deleteSedeLavoro(Long idSedeLavoro, Long idDatore) {
		DatoreSede datoreSede = new DatoreSede();
		DatoreSedePK datoreSedePK = new DatoreSedePK();
		datoreSedePK.setIdComDDatore(idDatore);
		datoreSedePK.setIdComDSedeLavoro(idSedeLavoro);
		datoreSede.setId(datoreSedePK);
		comRDatoreSedeDao.delete(ComonlMappers.DATORE_SEDE.toEntity(datoreSede).getId());
		comDSedeLavoroDao.delete(idSedeLavoro);
	}

	public SedeLavoro updateSedeLavoro(SedeLavoro model) {
		ComDSedeLavoro entity = ComonlMappers.SEDE_LAVORO.toEntity(model);
		return ComonlMappers.SEDE_LAVORO.toModel(comDSedeLavoroDao.update(entity));
	}
	
	public SedeLavoro saveSedeLegale(SedeLavoro model, Long idDatore) {
		if (model.getId()==null)
			return insertSedeLavoro(model, idDatore);
		else
			return updateSedeLavoro(model);
	}
	public SedeLavoro saveSedeLavoro(SedeLavoro model) {
		ComDSedeLavoro entity = ComonlMappers.SEDE_LAVORO.toEntity(model);
		return ComonlMappers.SEDE_LAVORO.toModel(comDSedeLavoroDao.save(entity));
	}

}
