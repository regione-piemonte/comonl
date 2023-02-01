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
//classe generata automaticamente da generator.strategy.DaoExceptionStrategy
package it.csi.comonl.comonlweb.ejb.business.be.dao;

public class DaoException extends RuntimeException {

	public DaoException(String message, Exception e) {
		e.printStackTrace();
	}

}
