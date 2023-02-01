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
package it.csi.comonl.comonlweb.ejb.business.be.dao.comonl;

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.EntityDelegatoImpresa;
import it.csi.comonl.comonlweb.ejb.entity.EntityDelegatoImpresaPK;

/**
 * Data Access Object interface for the entity EntityDelegatoImpresa
 */
public interface EntityDelegatoImpresaDao extends BaseEntityDao<EntityDelegatoImpresaPK, EntityDelegatoImpresa> {

	List<EntityDelegatoImpresa> findByField(String field);
	
	List<EntityDelegatoImpresa> findByIdAnagraficaDelegato(String cfDelegato, String tipoAnagrafica);
	
	List<EntityDelegatoImpresa> findByCfImpresa(String cfImpresa);
	
	List<EntityDelegatoImpresa> findByCfImpresaValidOrNotValid(String cfImpresa,boolean flgDataAnnullamentoNull);
}
