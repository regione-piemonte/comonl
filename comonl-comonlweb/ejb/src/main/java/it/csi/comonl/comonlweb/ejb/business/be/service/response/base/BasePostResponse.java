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

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;
import it.csi.comonl.comonlweb.lib.util.serialization.JsonUtility;

/**
 * Base response for POST methods
 * @param <K> the key type for the DTO
 * @param <D> the DTO type
 */
public abstract class BasePostResponse<K, D extends BaseDto<K>> extends BaseResponse {

	/**
	 * The base URI to use
	 * @return the base URI as string
	 */
	protected abstract String getBaseUri();
	/**
	 * The underlying entity
	 * @return the entity
	 */
	protected abstract D getEntity();

	@Override
	protected Response composeOwnResponse() {
		final String methodName = "composeOwnResponse";
		String serialized = JsonUtility.serialize(getEntity());
		logSerialized(methodName, serialized);
		return Response
				.status(Status.CREATED)
				.entity(serialized)
				.location(URI.create(getBaseUri() + "/" + getEntity().getId()))
				.build();
	}

}
