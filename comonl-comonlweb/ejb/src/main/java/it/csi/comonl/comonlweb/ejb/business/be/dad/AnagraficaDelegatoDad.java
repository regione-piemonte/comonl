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

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDAnagraficaAziAccentDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDSoggettoAbilitatoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityAnagraficaDelegatoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityDelegaDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityDelegatoImpresaDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDAnagraficaAziAccent;
import it.csi.comonl.comonlweb.ejb.entity.ComDSoggettoAbilitato;
import it.csi.comonl.comonlweb.ejb.entity.EntityAnagraficaDelegato;
import it.csi.comonl.comonlweb.ejb.entity.EntityAnagraficaDelegatoPK;
import it.csi.comonl.comonlweb.ejb.entity.EntityDelegatoImpresa;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.ejb.util.jpa.Page;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAzienda;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaAccreditamentoAnagrafiche;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.Sort;

@ApplicationScoped
public class AnagraficaDelegatoDad extends BaseDad {

	@Inject
	private EntityAnagraficaDelegatoDao entityAnagraficaDelegatoDao;

	@Inject
	private EntityDelegatoImpresaDao entityDelegatoImpresaDao;

	@Inject
	private ComDAnagraficaAziAccentDao comDAnagraficaAziAccentDao;

	@Inject
	private ComDSoggettoAbilitatoDao comDSoggettoAbilitatoDao;

	@Inject
	private EntityDelegaDao entityDelegaDao;

	public Long countRicercaAccreditamentoConsulente(
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		log.debug("countRicerca----------->", "Entro nel metodo countRicerca");
		Long count = entityAnagraficaDelegatoDao.countRicercaAccreditamentoConsulente(ricercaAccreditamentoAnagrafiche);
		log.debug("countRicerca----------->", "ESCO DAl metodo countRicerca--->" + count);
		return count;
	}
	
	public Long countRicercaAccreditamentoAzienda(
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		log.debug("countRicerca----------->", "Entro nel metodo countRicerca");
		Long count = entityAnagraficaDelegatoDao.countRicercaAccreditamentoAzienda(ricercaAccreditamentoAnagrafiche);
		log.debug("countRicerca----------->", "ESCO DAl metodo countRicerca--->" + count);
		return count;
	}

	public PagedList<AnagraficaDelegato> getRicercaConsulente(int page, int size, Sort sort,
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		String sortField = null;
		String sortDirection = null;

		if (sort != null) {
			/*
			 * if (ComunicazioneSort.byModelName(sort.getField()) != null) { sortField =
			 * ComunicazioneSort.byModelName(sort.getField()).getQueryName(); }
			 * sortDirection = sort.getOrder().getSortDirection();
			 */
		}

		Page<EntityAnagraficaDelegato> entityAnagraficaDelegato = entityAnagraficaDelegatoDao
				.findPaginatedConsulente(page, size, sortField, sortDirection, ricercaAccreditamentoAnagrafiche);

		PagedList<AnagraficaDelegato> pagedList = toPagedList(entityAnagraficaDelegato, page, size,
				ComonlMappers.ANAGRAFICA_DELEGATO::toModel);
		return pagedList;
	}


	

	public PagedList<AnagraficaAzienda> getRicercaAzienda(long limitMax, int page, int size, Sort sort,
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		String sortField = null;
		String sortDirection = null;

		if (sort != null) {
			/*
			 * if (ComunicazioneSort.byModelName(sort.getField()) != null) { sortField =
			 * ComunicazioneSort.byModelName(sort.getField()).getQueryName(); }
			 * sortDirection = sort.getOrder().getSortDirection();
			 */
		}

		Page<AnagraficaAzienda> entityAnagraficaAzienda = entityAnagraficaDelegatoDao.findPaginatedAzienda(limitMax, page, size,
				sortField, sortDirection, ricercaAccreditamentoAnagrafiche);

		return toPagedList(entityAnagraficaAzienda, page, size);
	}

	public List<DelegatoImpresa> findDelegatoimpresaByIdAnagraficaDelegato(String cfDelegato, String tipoAnagrafica) {
		List<EntityDelegatoImpresa> entities = entityDelegatoImpresaDao.findByIdAnagraficaDelegato(cfDelegato,
				tipoAnagrafica);
		return ComonlMappers.DELEGATO_IMPRESA.toModels(entities);
	}

	public List<AnagraficaDelegato> findSoggettoByCfSoggettoAndTipoAnagraficaDelegato(String cfSoggetto,
			String tipoAnagrafica) {
		List<EntityAnagraficaDelegato> entities = entityAnagraficaDelegatoDao
				.findSoggettoByCfSoggettoAndTipoAnagraficaDelegato(cfSoggetto, tipoAnagrafica);
		return ComonlMappers.ANAGRAFICA_DELEGATO.toModels(entities);
	}

	public AnagraficaAziAccent getAziendaAccentrata(String codiceFiscaleAzienda) {
		Optional<AnagraficaAziAccent> entities = comDAnagraficaAziAccentDao.findByCodiceFiscale(codiceFiscaleAzienda)
				.map(ComonlMappers.ANAGRAFICA_AZI_ACCENT::toModel);
		if (entities.isPresent()) {
			return entities.get();
		}
		return null;
	}

	public SoggettoAbilitato getSoggettoAbilitato(String codiceFiscaleSoggetto) {
		Optional<SoggettoAbilitato> entities = comDSoggettoAbilitatoDao.findByCfSoggetto(codiceFiscaleSoggetto)
				.map(ComonlMappers.SOGGETTO_ABILITATO::toModel);
		if (entities.isPresent()) {
			return entities.get();
		}
		return null;
	}
	
	public List<AnagraficaDelegato> getAnagraficaDelegatoByCfDelegato(String cfDelegato) {
		List<EntityAnagraficaDelegato> entities = entityAnagraficaDelegatoDao.findByCfDelegato(cfDelegato);
		return ComonlMappers.ANAGRAFICA_DELEGATO.toModels(entities);
	}

	public AnagraficaDelegato insertAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato) {
		EntityAnagraficaDelegato entityAnagraficaDelegato = ComonlMappers.ANAGRAFICA_DELEGATO
				.toEntity(anagraficaDelegato);
		return ComonlMappers.ANAGRAFICA_DELEGATO.toModel(entityAnagraficaDelegatoDao.insert(entityAnagraficaDelegato));
	}

	public AnagraficaDelegato getAnagraficaDelegatoById(String cfDelegato, String tipoAnagrafica) {
		EntityAnagraficaDelegatoPK entityAnagraficaDelegatoPK = new EntityAnagraficaDelegatoPK();
		entityAnagraficaDelegatoPK.setCfDelegato(cfDelegato);
		entityAnagraficaDelegatoPK.setTipoAnagrafica(tipoAnagrafica);
		Optional<AnagraficaDelegato> anagraficaDelegatoFinded = entityAnagraficaDelegatoDao
				.findById(entityAnagraficaDelegatoPK).map(ComonlMappers.ANAGRAFICA_DELEGATO::toModel);
		if (anagraficaDelegatoFinded.isPresent()) {
			return anagraficaDelegatoFinded.get();
		}
		return null;
	}

	public AnagraficaDelegato updateAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato) {
		EntityAnagraficaDelegato entityAnagraficaDelegato = ComonlMappers.ANAGRAFICA_DELEGATO
				.toEntity(anagraficaDelegato);
		return ComonlMappers.ANAGRAFICA_DELEGATO.toModel(entityAnagraficaDelegatoDao.update(entityAnagraficaDelegato));
	}

	public List<AnagraficaDelegato> findAnagraficaDelegatoByIdSoggettoAbilitato(Long idSoggettoAbilitato) {
		List<EntityAnagraficaDelegato> entities = entityAnagraficaDelegatoDao
				.findByIdSoggettoAbilitato(idSoggettoAbilitato);
		return ComonlMappers.ANAGRAFICA_DELEGATO.toModels(entities);
	}

	public List<AnagraficaDelegato> getAnagraficaDelegatoNotCanceledByIdSoggettoAbilitato(Long idSoggettoAbilitato) {
		List<EntityAnagraficaDelegato> entities = entityAnagraficaDelegatoDao
				.findByIdSoggettoAbilitato(idSoggettoAbilitato);
		return ComonlMappers.ANAGRAFICA_DELEGATO.toModels(entities);
	}

	public AnagraficaAziAccent updateAziendaAccentrata(AnagraficaAziAccent anagraficaAziAccent) {
		ComDAnagraficaAziAccent comDAnagraficaAziAccent = ComonlMappers.ANAGRAFICA_AZI_ACCENT
				.toEntity(anagraficaAziAccent);
		return ComonlMappers.ANAGRAFICA_AZI_ACCENT.toModel(comDAnagraficaAziAccentDao.update(comDAnagraficaAziAccent));
	}

	public AnagraficaAziAccent insertAziendaAccentrata(AnagraficaAziAccent anagraficaAziAccent) {
		Long maxId = comDAnagraficaAziAccentDao.getIdMax();
		anagraficaAziAccent.setId(++maxId);
		ComDAnagraficaAziAccent comDAnagraficaAziAccent = ComonlMappers.ANAGRAFICA_AZI_ACCENT
				.toEntity(anagraficaAziAccent);
		return ComonlMappers.ANAGRAFICA_AZI_ACCENT.toModel(comDAnagraficaAziAccentDao.insert(comDAnagraficaAziAccent));
	}

	public List<Ruolo> findRuoliPerServizioEsposto(String codiceFiscale) {
		return entityAnagraficaDelegatoDao.findRuoliPerServizioEsposto(codiceFiscale);
	}
}
