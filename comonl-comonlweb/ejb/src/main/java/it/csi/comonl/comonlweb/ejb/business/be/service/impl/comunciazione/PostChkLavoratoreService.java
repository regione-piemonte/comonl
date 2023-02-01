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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorLavoratore;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.base.BaseService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostLavoratoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostLavoratoreResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;

public class PostChkLavoratoreService extends BaseService<PostLavoratoreRequest, PostLavoratoreResponse> {

	private static final Date DATE_DUEMILA = Date
			.from((LocalDate.of(2000, 1, 1)).atStartOfDay(ZoneId.systemDefault()).toInstant());
	private static final Date DATE_NOVECENTO = Date
			.from((LocalDate.of(1900, 1, 1)).atStartOfDay(ZoneId.systemDefault()).toInstant());
	private static final Date DATE_CARTA_PERMANENTE = Date
			.from((LocalDate.of(2099, 12, 31)).atStartOfDay(ZoneId.systemDefault()).toInstant());

	private ControlliDad controlliDad;

	public PostChkLavoratoreService(ConfigurationHelper configurationHelper,  ControlliDad controlliDad) {
		super(configurationHelper);
		this.controlliDad = controlliDad;
	}

	@Override
	protected void execute() {
//		Lavoratore lavoratore = request.getLavoratore();
//		
//		Validator<Lavoratore> validator = new ValidatorLavoratore(lavoratore, controlliDad);
//		
//		if(validator.isOk()) {
//			response.setLavoratore(lavoratore);
//			return;
//		}
//		
//		response.addApiErrors(validator.getApiErrors());
		
	}

}
