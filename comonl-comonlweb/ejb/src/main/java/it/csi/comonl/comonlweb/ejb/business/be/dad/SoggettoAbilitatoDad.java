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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDSoggettoAbilitatoDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDSoggettoAbilitato;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;

@ApplicationScoped
public class SoggettoAbilitatoDad extends BaseDad {
	
	@Inject
	private ComDSoggettoAbilitatoDao comDSoggettoAbilitatoDao;
	
	/**
	 * Find by cfsoggetto
	 * 
	 * @param String cfsoggetto
	 * @return the model instance
	 */
	public SoggettoAbilitato findByCfSoggetto(String cfsoggetto) {
		Optional<ComDSoggettoAbilitato> soggettoAbilitatoFinded = comDSoggettoAbilitatoDao.findByCfSoggetto(cfsoggetto);
		if (!soggettoAbilitatoFinded.isPresent()) {
			return null;
		} else {
			return ComonlMappers.SOGGETTO_ABILITATO.toModel(soggettoAbilitatoFinded.get());
		}
	}
	
	/**
	 * Find by id
	 * 
	 * @param Long idSeoggettoAbilitato
	 * @return the model instance
	 */
	public SoggettoAbilitato getById(Long idSeoggettoAbilitato) {
		Optional<ComDSoggettoAbilitato> soggettoAbilitatoFinded = comDSoggettoAbilitatoDao.findById(idSeoggettoAbilitato);
		if (!soggettoAbilitatoFinded.isPresent()) {
			return null;
		} else {
			return ComonlMappers.SOGGETTO_ABILITATO.toModel(soggettoAbilitatoFinded.get());
		}
	}
	
	
	public SoggettoAbilitato insertSoggettoAbilitato(SoggettoAbilitato soggettoAbilitato) {
		ComDSoggettoAbilitato entity = ComonlMappers.SOGGETTO_ABILITATO.toEntity(soggettoAbilitato);
		return ComonlMappers.SOGGETTO_ABILITATO.toModel(comDSoggettoAbilitatoDao.insert(entity));
	}
	
	public SoggettoAbilitato updateSoggettoAbilitato(SoggettoAbilitato soggettoAbilitato) {
		ComDSoggettoAbilitato entity = ComonlMappers.SOGGETTO_ABILITATO.toEntity(soggettoAbilitato);
		return ComonlMappers.SOGGETTO_ABILITATO.toModel(comDSoggettoAbilitatoDao.update(entity));
	}

}
