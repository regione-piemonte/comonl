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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRRapportoLavoratoreDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDLavoratore;
import it.csi.comonl.comonlweb.ejb.entity.ComRRapportoLavoratore;
import it.csi.comonl.comonlweb.ejb.exception.NotFoundException;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportoLavoratore;





/**
 * Data Access Delegate for comunicazione
 */
@ApplicationScoped
public class RapportoLavoratoreDad extends BaseDad {

	@Inject
	private ComRRapportoLavoratoreDao comRRapportoLavoratoreDao;
	
	
	public RapportoLavoratore insertRapportoLavoratore(RapportoLavoratore model) {
		ComRRapportoLavoratore entity = ComonlMappers.RAPPORTO_LAVORATORE.toEntity(model);
		return ComonlMappers.RAPPORTO_LAVORATORE.toModel(comRRapportoLavoratoreDao.insert(entity));
	}
	
	public Lavoratore getLavoratoreByIdRapporto(Long idRapporto) {
		Optional<ComRRapportoLavoratore> rapportoLavoratoreFinded = comRRapportoLavoratoreDao.findLavoratoreByIdRapporto(idRapporto);
		if(!rapportoLavoratoreFinded.isPresent()) {
			throw new NotFoundException("ComuneByCodComuneMin");
		}else {
			ComDLavoratore entity = rapportoLavoratoreFinded.get().getComDLavoratore();
			return ComonlMappers.LAVORATORE.toModel(entity);
		}
	}


}
