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

import java.math.BigDecimal;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.EntityDepositoCommax;
import it.csi.comonl.comonlweb.ejb.entity.EntityDepositoCommaxPK;

/**
 * Data Access Object interface for the entity EntityDepositoCommax
 */
public interface EntityDepositoCommaxDao extends BaseEntityDao<EntityDepositoCommaxPK, EntityDepositoCommax> {

	List<EntityDepositoCommax> findByField(String field);

	List<EntityDepositoCommax> getDepositoCommaxByIdUplDocumenti(BigDecimal idComDUplDocumenti);
	
	List<EntityDepositoCommax> estraiDepositiDelleComunicazioniValide(BigDecimal idUplDocumenti);
	
	public EntityDepositoCommaxPK getSequenceDepositoCommax(int progr);
}
