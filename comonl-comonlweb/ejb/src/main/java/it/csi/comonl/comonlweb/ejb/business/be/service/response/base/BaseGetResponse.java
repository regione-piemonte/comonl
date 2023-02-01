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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.base;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.comonl.comonlweb.lib.util.serialization.JsonUtility;

/**
 * Base response for GET methods
 * @param <E> the returned type
 */
public abstract class BaseGetResponse<E> extends BaseResponse {

	/**
	 * @return whether the resulting value is present
	 */
	protected boolean isPresent() {
		return getEntity() != null;
	}

	/**
	 * @return the entity
	 */
	protected abstract E getEntity();

	@Override
	protected Response composeOwnResponse() {
		final String methodName = "composeOwnResponse";
		if(isPresent()) {
			String serialized = JsonUtility.serialize(getEntity());
			logSerialized(methodName, serialized);
			
			Response response =  Response
				.ok(serialized, MediaType.APPLICATION_JSON)
				// fv - il contenuto viene alle volte troncato - es. /comonl/api/v1/utente/settore/09ce7d4a-1c97-52de-9893-8eb23821df3d/modulo
				//.header("Content-Length", Integer.valueOf(StringHelper.getStringLengthUTF8(serialized)))
				.build();
			return response;
		}
		return Response.noContent().build();
	}

	/**
	 * Initializes a list
	 * @param <T> the generic type of the list
	 * @param value the value
	 * @return the list
	 */
	protected <T> List<T> initList(List<T> value) {
		return value != null ? value : new ArrayList<>();
	}

}
