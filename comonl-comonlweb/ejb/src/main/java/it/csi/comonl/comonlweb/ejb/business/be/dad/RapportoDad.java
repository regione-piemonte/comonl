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

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDRapportoDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDRapporto;
import it.csi.comonl.comonlweb.ejb.exception.NotFoundException;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;



/**
 * Data Access Delegate for comunicazione
 */
@ApplicationScoped
public class RapportoDad extends BaseDad {

	@Inject
	private ComDRapportoDao comDRapportoDao;
	
	
	public Rapporto insertRapporto(Rapporto model) {
		ComDRapporto entity = ComonlMappers.RAPPORTO.toEntity(model);
		entity.setId(null);
		return ComonlMappers.RAPPORTO.toModel(comDRapportoDao.insert(entity));
		
	}

	public Rapporto getRapportoByIdComunicazione(Long idComunicazione) {
		Optional<ComDRapporto> rapportoFinded = comDRapportoDao.findByIdComunicazione(idComunicazione);
		if(!rapportoFinded.isPresent()) {
			throw new NotFoundException("RapportoByIdComunicazione");
		}else {
			return ComonlMappers.RAPPORTO.toModel(rapportoFinded.get());
		}
	}
	public List<Rapporto> getRapportiByIdComunicazione(Long idComunicazione) {
		List<ComDRapporto> comDRapportos = comDRapportoDao.findRapportiByIdComunicazione(idComunicazione);
		return ComonlMappers.RAPPORTO.toModels(comDRapportos);
	}
	
	public Rapporto updateRapporto(Rapporto model) {
		ComDRapporto entity = ComonlMappers.RAPPORTO.toEntity(model);
		entity = comDRapportoDao.update(entity);
		comDRapportoDao.flush();
		return ComonlMappers.RAPPORTO.toModel(entity);
	}
	public Rapporto getRapportoByTipoRapportoAzienda(Long idComunicazione, String tipoRapportoAzienda) {
		Optional<ComDRapporto> rapportoFinded = comDRapportoDao.findByTipoRapportoAzienda(idComunicazione, tipoRapportoAzienda);
		return rapportoFinded.map(ComonlMappers.RAPPORTO::toModel).orElse(null);
	}
	public List<Rapporto> getRapportiByIdTutore(Long idTutore) {
		List<ComDRapporto> comDRapportos = comDRapportoDao.findRapportiByIdTutore(idTutore);
		return ComonlMappers.RAPPORTO.toModels(comDRapportos);
	}
	public void deleteRapporto(Long idRapporto) {
		
		comDRapportoDao.delete(idRapporto);
	}

}
