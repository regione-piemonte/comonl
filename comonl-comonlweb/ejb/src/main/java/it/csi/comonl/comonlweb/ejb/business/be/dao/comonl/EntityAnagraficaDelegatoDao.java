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
package it.csi.comonl.comonlweb.ejb.business.be.dao.comonl;

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseAuditedEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.EntityAnagraficaDelegato;
import it.csi.comonl.comonlweb.ejb.entity.EntityAnagraficaDelegatoPK;
import it.csi.comonl.comonlweb.ejb.util.jpa.Page;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAzienda;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaAccreditamentoAnagrafiche;

/**
 * Data Access Object interface for the entity EntityAnagraficaDelegato
 */
public interface EntityAnagraficaDelegatoDao
		extends BaseAuditedEntityDao<EntityAnagraficaDelegatoPK, EntityAnagraficaDelegato> {

	List<EntityAnagraficaDelegato> findByField(String field);

	public Long countRicercaAccreditamentoConsulente(
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche);

	public Page<EntityAnagraficaDelegato> findPaginatedConsulente(int page, int size, String sortField,
			String sortDirection, FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche);

	public Long countRicercaAccreditamentoAzienda(
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche);

	public Page<AnagraficaAzienda> findPaginatedAzienda(long limMaxRicerca,int page, int size, String sortField, String sortDirection,
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche);

	List<EntityAnagraficaDelegato> findSoggettoByCfSoggettoAndTipoAnagraficaDelegato(String cfSoggetto,
			String tipoAnagrafica);

	List<EntityAnagraficaDelegato> findByCfDelegato(String field);

	List<EntityAnagraficaDelegato> findByIdSoggettoAbilitato(Long idSoggettoAbilitato);

	List<EntityAnagraficaDelegato> findNotCanceledByIdSoggettoAbilitato(Long idSoggettoAbilitato);

	List<Ruolo> findRuoliPerServizioEsposto(String codiceFiscale);
}
