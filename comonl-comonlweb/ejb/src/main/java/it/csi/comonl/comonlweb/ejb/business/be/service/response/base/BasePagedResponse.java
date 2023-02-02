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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.base;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.serialization.JsonUtility;

/**
 * Base paged response
 * @param <E> the paged type
 */
public abstract class BasePagedResponse<E> extends BaseResponse {

	/**
	 * @return the entity
	 */
	protected abstract PagedList<E> getEntity();

	@Override
	protected Response composeOwnResponse() {
		final String methodName = "composeOwnResponse";
		String serialized = JsonUtility.serialize(getEntity());
		logSerialized(methodName, serialized);
		return Response
			.ok(serialized, MediaType.APPLICATION_JSON)
			.build();
	}

}
