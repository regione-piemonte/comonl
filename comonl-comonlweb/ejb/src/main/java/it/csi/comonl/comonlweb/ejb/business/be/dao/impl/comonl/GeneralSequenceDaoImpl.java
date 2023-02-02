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
package it.csi.comonl.comonlweb.ejb.business.be.dao.impl.comonl;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.GeneralSequenceDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.NextVal;


@ApplicationScoped
public class GeneralSequenceDaoImpl extends BaseEntityDaoImpl<Long, NextVal> implements GeneralSequenceDao {

	@Override
	public Long nextSequence(String sequenceName) {
		Query qn = composeNativeQuery("select " + sequenceName + ".nextval from dual", null);
		return ((Number) qn.getSingleResult()).longValue();
	}

}
