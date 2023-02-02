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

import it.csi.comonl.comonlweb.ejb.business.be.dad.sort.ComunicazioneSort;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityDelegaDao;
import it.csi.comonl.comonlweb.ejb.entity.EntityDelega;
import it.csi.comonl.comonlweb.ejb.exception.NotFoundException;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.ejb.util.jpa.Page;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaDelega;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.Sort;

/**
 * Data Access Delegate for comunicazione
 */
@ApplicationScoped
public class DelegaDad extends BaseDad {

	@Inject
	private EntityDelegaDao entityDelegaDao;

	public Long countRicercaDelega(FormRicercaDelega ricercaDelega) {
		log.debug("countRicerca----------->", "Entro nel metodo countRicerca");
		Long count = entityDelegaDao.countRicercaDelega(ricercaDelega);
		log.debug("countRicerca----------->", "ESCO DAl metodo countRicerca--->" + count);
		return count;
	}

	public PagedList<Delega> getRicerca(int page, int size, Sort sort, FormRicercaDelega ricercaDelega) {
		String sortField = null;
		String sortDirection = null;

		if (sort != null) {
			if (ComunicazioneSort.byModelName(sort.getField()) != null) {
				sortField = ComunicazioneSort.byModelName(sort.getField()).getQueryName();
			}
			sortDirection = sort.getOrder().getSortDirection();
		}

		Page<EntityDelega> deleghe = entityDelegaDao.findPaginated(page, size, sortField, sortDirection, ricercaDelega);

		return toPagedList(deleghe, page, size, ComonlMappers.DELEGA::toModel);

	}

	public boolean isPresenteDelegaValida(String cfDelegato, String cfImpresa) {
		List<EntityDelega> entities = entityDelegaDao.findByCfDelegatoAndCFImpresaAndStatoDelega(cfDelegato, cfImpresa,
				ComonlConstants.STATO_DELEGA_VALIDATA_3, true);
		return !entities.isEmpty();
	}

	public boolean isPresenteDelegaRevocata(String cfDelegato, String cfImpresa) {
		List<EntityDelega> entities = entityDelegaDao.findByCfDelegatoAndCFImpresaAndStatoDelega(cfDelegato, cfImpresa,
				ComonlConstants.STATO_DELEGA_REVOCATA_4, true);
		return !entities.isEmpty();
	}

	public List<Delega> getDelegaByCfDelegatoCfImpresaNotEqualSatoDelega(String cfDelegato, String cfImpresa,
			String statoDelega) {
		List<EntityDelega> entities = entityDelegaDao.findByCfDelegatoAndCFImpresaAndStatoDelega(cfDelegato, cfImpresa,
				statoDelega, false);
		return ComonlMappers.DELEGA.toModels(entities);
	}

	public List<Delega> getDelegaByCfDelegatoCfImpresaSatoDelega(String cfDelegato, String cfImpresa,
			boolean ugualeAStatoDelega) {
		List<EntityDelega> entities = entityDelegaDao.findByCfDelegatoAndCFImpresaAndStatoDelega(cfDelegato, cfImpresa,
				ComonlConstants.STATO_DELEGA_VALIDATA_3, ugualeAStatoDelega);
		return ComonlMappers.DELEGA.toModels(entities);
	}

	public List<Delega> getDelegaByCfDelegatoCfImpresa(String cfDelegato, String cfImpresa) {
		List<EntityDelega> entities = entityDelegaDao.findByCfDelegatoAndCFImpresa(cfDelegato, cfImpresa);
		return ComonlMappers.DELEGA.toModels(entities);
	}

	public Delega getDelegaById(Long idDelega) {

		Optional<Delega> delega = entityDelegaDao.findOne(idDelega).map(ComonlMappers.DELEGA::toModel);
		if (!delega.isPresent()) {
			throw new NotFoundException("getDelegaById");
		}
		return delega.get();
	}

	public Delega updateDelega(Delega delega) {
		EntityDelega entity = ComonlMappers.DELEGA.toEntity(delega);
		return ComonlMappers.DELEGA.toModel(entityDelegaDao.update(entity));
	}
	
	
	public Delega insertDelega(Delega delega) {
		EntityDelega entity = ComonlMappers.DELEGA.toEntity(delega);
		return ComonlMappers.DELEGA.toModel(entityDelegaDao.insert(entity));
	}

}
