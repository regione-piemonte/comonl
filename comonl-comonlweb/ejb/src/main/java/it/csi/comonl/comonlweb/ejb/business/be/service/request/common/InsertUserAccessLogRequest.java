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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.common;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UserAccessLog;

public class InsertUserAccessLogRequest implements BaseRequest {

	private final UserAccessLog user;

	public InsertUserAccessLogRequest(UserAccessLog user) {
		this.user = user;
	}

	public UserAccessLog getUser() {
		return user;
	}

}
