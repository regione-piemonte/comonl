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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDLavoratoreDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRRapportoLavoratoreDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRSedeLavoroLavoratoreDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDLavoratore;
import it.csi.comonl.comonlweb.ejb.entity.ComRSedeLavoroLavoratore;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DatoreSede;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DatoreSedePK;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportoLavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportoLavoratorePK;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoroLavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoroLavoratorePK;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoLavoratore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoRapportoAzienda;

/**
 * Data Access Delegate for Lavoratore
 */
@ApplicationScoped
public class LavoratoreDad extends BaseDad {

	@Inject
	private ComDLavoratoreDao comDLavoratoreDao;
	@Inject
	private RapportoLavoratoreDad rapportoLavoratoreDad;
	
	@Inject
	private ComRSedeLavoroLavoratoreDao comRSedeLavoroLavoratoreDao;
	@Inject
	private ComRRapportoLavoratoreDao comRRapportoLavoratoreDao;

	public Lavoratore insertLavoratore(Lavoratore model) {
		ComDLavoratore entity = ComonlMappers.LAVORATORE.toEntity(model);
		return ComonlMappers.LAVORATORE.toModel(comDLavoratoreDao.insert(entity));
	}
	public Lavoratore insertLavoratore(Lavoratore model, Long idRapporto, String idTipoRapportoAzienda, String idTipoLavoratore) {
		ComDLavoratore entity = ComonlMappers.LAVORATORE.toEntity(model);
		entity.setId(null);
		entity = comDLavoratoreDao.insert(entity);
		
		//RELAZIONE RAPPORTO LAVORATORE
		RapportoLavoratore rapportoLavoratore = new RapportoLavoratore();
		RapportoLavoratorePK rapportoLavoratorePK = new RapportoLavoratorePK();
		rapportoLavoratorePK.setIdComDLavoratore(entity.getId());
		rapportoLavoratorePK.setIdComDRapporto(idRapporto);
		rapportoLavoratore.setId(rapportoLavoratorePK);
		TipoRapportoAzienda tipoRapportoAzienda = new TipoRapportoAzienda();
		tipoRapportoAzienda.setId(idTipoRapportoAzienda);
		rapportoLavoratore.setTipoRapportoAzienda(tipoRapportoAzienda);
		TipoLavoratore tipoLavoratore = new TipoLavoratore();
		tipoLavoratore.setId(idTipoLavoratore);
		rapportoLavoratore.setTipoLavoratore(tipoLavoratore);
		rapportoLavoratoreDad.insertRapportoLavoratore(rapportoLavoratore);			
		
		return ComonlMappers.LAVORATORE.toModel(entity);
	}
	public SedeLavoroLavoratore insertSedeLavoroLavoratore(Long idLavoratore, Long idSedeLavoro) {
		
		//RELAZIONE SEDE LAVORO - LAVORATORE
		SedeLavoroLavoratore sedeLavoroLavoratore = new SedeLavoroLavoratore();
		SedeLavoroLavoratorePK sedeLavoroLavoratorePK = new SedeLavoroLavoratorePK();
		sedeLavoroLavoratorePK.setIdComDLavoratore(idLavoratore);
		sedeLavoroLavoratorePK.setIdComDSedeLavoro(idSedeLavoro);
		sedeLavoroLavoratore.setId(sedeLavoroLavoratorePK);

		ComRSedeLavoroLavoratore comRSedeLavoroLavoratore = comRSedeLavoroLavoratoreDao.insert(ComonlMappers.SEDE_LAVORO_LAVORATORE.toEntity(sedeLavoroLavoratore));
		
		return ComonlMappers.SEDE_LAVORO_LAVORATORE.toModel(comRSedeLavoroLavoratore);
		
	}
	public Lavoratore updateLavoratore(Lavoratore model) {
		ComDLavoratore entity = ComonlMappers.LAVORATORE.toEntity(model);
		return ComonlMappers.LAVORATORE.toModel(comDLavoratoreDao.update(entity));
	}
	
	public void deleteLavoratore(Long idLavoratore) {
		
		comRSedeLavoroLavoratoreDao.deleteByIdLavoratore(idLavoratore);
		
		comRRapportoLavoratoreDao.deleteByIdLavoratore(idLavoratore);
		
		comDLavoratoreDao.delete(idLavoratore);
	}
}
