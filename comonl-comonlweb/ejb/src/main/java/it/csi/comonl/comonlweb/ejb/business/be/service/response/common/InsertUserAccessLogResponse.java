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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.common;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UserAccessLog;

/**
 * The Class GetRuoloResponse.
 */
public class InsertUserAccessLogResponse extends BaseGetResponse<UserAccessLog> {

	/** The model. */
	private UserAccessLog user = null;

	@Override
	protected UserAccessLog getEntity() {
		return user;
	}

	public UserAccessLog getUser() {
		return user;
	}

	public void setUser(UserAccessLog user) {
		this.user = user;
	}

}
