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
package it.csi.comonl.comonlweb.ejb.business.be.facade;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import it.csi.comonl.comonlweb.ejb.business.be.service.impl.utente.GetUtenteSelfService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.utente.GetUtenteSelfRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.utente.GetUtenteSelfResponse;

/**
 * Fa&ccedil;ade for the /utente path
 */
@Stateless
@Lock(LockType.READ)
public class UtenteFacade extends BaseFacade {

	// Injection point
//	@Inject
//	private UtenteDad utenteDad;

	/**
	 * Retrieves the Utente
	 * 
	 * @return the utente
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetUtenteSelfResponse getUtenteSelf() {
		// return executeService(new GetUtenteSelfRequest(), new GetUtenteSelfService(configurationHelper, utenteDad));
		return executeService(new GetUtenteSelfRequest(), new GetUtenteSelfService(configurationHelper));
	}

}
