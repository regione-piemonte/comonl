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

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRComunicazioneDatoreDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDDatore;
import it.csi.comonl.comonlweb.ejb.entity.ComRComunicazioneDatore;
import it.csi.comonl.comonlweb.ejb.exception.NotFoundException;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ComunicazioneDatore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;




/**
 * Data Access Delegate for comunicazione
 */
@ApplicationScoped
public class ComunicazioneDatoreDad extends BaseDad {

	@Inject
	private ComRComunicazioneDatoreDao comRComunicazioneDatoreDao;
	
	
	public ComunicazioneDatore insertComunicazioneDatore(ComunicazioneDatore model) {
		ComRComunicazioneDatore entity = ComonlMappers.COMUNICAZIONE_DATORE.toEntity(model);
		entity = comRComunicazioneDatoreDao.insert(entity);
		comRComunicazioneDatoreDao.flush();
		return ComonlMappers.COMUNICAZIONE_DATORE.toModel(entity);
	}
	
	public Datore getDatoreByIdComunicazione(Long idComunicazione) {
		Optional<ComRComunicazioneDatore> datoreComunicazioneFinded = comRComunicazioneDatoreDao.findDatoreByIdComunicazione(idComunicazione);
		if(!datoreComunicazioneFinded.isPresent()) {
			throw new NotFoundException("DatoreByIdComunicazione");
		}else {
			ComDDatore entity = datoreComunicazioneFinded.get().getComDDatore();
			return ComonlMappers.DATORE.toModel(entity);
		}
	}


}
