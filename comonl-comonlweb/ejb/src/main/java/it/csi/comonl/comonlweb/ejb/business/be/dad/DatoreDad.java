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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDDatoreDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDDatore;
import it.csi.comonl.comonlweb.ejb.entity.ComTAtecofin;
import it.csi.comonl.comonlweb.ejb.entity.EntityNaturaGiuridica;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ComunicazioneDatore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ComunicazioneDatorePK;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoDatore;


/**
 * Data Access Delegate for comunicazione
 */
@ApplicationScoped
public class DatoreDad extends BaseDad {

	@Inject
	private ComDDatoreDao comDDatoreDao;
	@Inject
	private ComunicazioneDatoreDad comunicazioneDatoreDad;
	
	public Datore insertDatore(Datore model) {
		ComDDatore entity = ComonlMappers.DATORE.toEntity(model);
		entity = comDDatoreDao.insert(entity);
		comDDatoreDao.flush();
		return ComonlMappers.DATORE.toModel(entity);
	}
	
	/*
	 * insert datore in relazione alla comunicazione
	 * */
	public Datore insertDatore(Datore model, Long idComunicazione, String idTipoDatore) {
		ComDDatore entity = ComonlMappers.DATORE.toEntity(model);
		entity.setId(null);
		entity = comDDatoreDao.insert(entity);
		comDDatoreDao.flush();
		
		ComunicazioneDatore comunicazioneDatore = new ComunicazioneDatore();
		ComunicazioneDatorePK comunicazioneDatorePk = new ComunicazioneDatorePK();
		comunicazioneDatorePk.setIdComDComunicazione(idComunicazione);
		comunicazioneDatorePk.setIdComDDatore(entity.getId());
		comunicazioneDatore.setId(comunicazioneDatorePk);
		TipoDatore tipoDatore = new TipoDatore();
		tipoDatore.setId(idTipoDatore); // non sarebbe meglio leggere il dato dal tipo anziche crearlo con new ()
		comunicazioneDatore.setTipoDatore(tipoDatore);
		comunicazioneDatoreDad.insertComunicazioneDatore(comunicazioneDatore);
		
		return ComonlMappers.DATORE.toModel(entity);
	}
	public Datore updateDatore(Datore model) {
		ComDDatore entity = ComonlMappers.DATORE.toEntity(model);
		return ComonlMappers.DATORE.toModel(comDDatoreDao.update(entity));		
	}
	public void deleteDatore(Long idDatore) {
		
		comDDatoreDao.delete(idDatore);
	}

	public Datore saveDatore (Datore model) {
		ComDDatore entity = ComonlMappers.DATORE.toEntity(model);
		return ComonlMappers.DATORE.toModel(comDDatoreDao.save(entity));		
	}
}
