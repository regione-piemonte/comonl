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

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseAuditedEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.EntityDelega;
import it.csi.comonl.comonlweb.ejb.util.jpa.Page;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaDelega;

/**
 * Data Access Object interface for the entity EntityDelega
 */
public interface EntityDelegaDao extends BaseAuditedEntityDao<Long, EntityDelega> {

	List<EntityDelega> findByField(String field);

	List<EntityDelega> findByCfDelegatoAndCFImpresaAndStatoDelega(String cfDelegato, String cfImpresa,
			String statoDelega, boolean ugualeAStatoDelega);
	
	List<EntityDelega> findByCfDelegatoAndCFImpresa(String cfDelegato, String cfImpresa);

	public Long countRicercaDelega(FormRicercaDelega ricercaDelega);

	public Page<EntityDelega> findPaginated(int page, int size, String sortField, String sortDirection,
			FormRicercaDelega ricercaDelega);

}
