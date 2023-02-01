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

import javax.ws.rs.core.Response;

/**
 * Base response for DELETE methods
 */
public abstract class BaseDeleteResponse extends BaseResponse {

	@Override
	protected Response composeOwnResponse() {
		return Response.noContent().build();
	}
}
