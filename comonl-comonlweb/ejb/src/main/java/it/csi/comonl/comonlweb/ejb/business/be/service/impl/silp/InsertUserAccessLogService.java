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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp;

import java.util.Date;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.common.BaseCommonService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.common.InsertUserAccessLogRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.common.InsertUserAccessLogResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UserAccessLog;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

public class InsertUserAccessLogService
		extends BaseCommonService<InsertUserAccessLogRequest, InsertUserAccessLogResponse> {

	public InsertUserAccessLogService(ConfigurationHelper configurationHelper, CommonDad commonDad) {
		super(configurationHelper, commonDad);
	}

	@Override
	protected void execute() {
		UserAccessLog loUser = request.getUser();
		loUser.setDtEvento(new Date());
		String dsCognome = "";
		if (ComonlUtility.isNotVoid(loUser.getDsCognome()) && loUser.getDsCognome().length() > 40) {
			dsCognome = loUser.getDsCognome().substring(0, 40);
		} else {
			dsCognome = loUser.getDsCognome();
		}
		loUser.setDsCognome(dsCognome);
		String dsNome = "";
		if (ComonlUtility.isNotVoid(loUser.getDsCognome()) && loUser.getDsCognome().length() > 40) {
			dsNome = loUser.getDsNome().substring(0, 40);
		} else {
			dsNome = loUser.getDsNome();
		}
		loUser.setDsNome(dsNome);
		loUser = commonDad.insertUserAccessLog(loUser);
		response.setUser(loUser);
	}
}
