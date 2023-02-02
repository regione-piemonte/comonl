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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp;

import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.service.impl.base.BaseService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.silp.PutLavoratoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.PutLavoratoreResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.external.impl.silp.LavoratoreHelperImpl;
import it.csi.comonl.comonlweb.lib.external.impl.silp.invoker.SilpBusinessException;
import it.csi.comonl.comonlweb.lib.external.impl.silp.invoker.SilpRestAdapter;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ApiMessage;

public class PutLavoratoreService extends BaseService<PutLavoratoreRequest, PutLavoratoreResponse> {
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * 
	 */
	public PutLavoratoreService(ConfigurationHelper configurationHelper) {
		super(configurationHelper);
	}

	@Override
	protected void execute() {

		LavoratoreSilpEspanso lavoratore = request.getLavoratore();

		List<ApiError> apiErrors = new ArrayList<ApiError>();

		if (lavoratore != null) {

			try {
				SilpRestAdapter.getInstance().saveLavoratore(getUtente().getCodiceFiscale(), lavoratore);
				LavoratoreHelperImpl lavoratoreHelperImpl = new LavoratoreHelperImpl();
				response.setLavoratoreSilpEspanso(
						lavoratoreHelperImpl.ricercaPerCodiceFiscale(lavoratore.getId().getCodiceFiscale()));
			} catch (SilpBusinessException sex) {
				List<ApiMessage> listaMsgSilp = sex.getErrors();
				if (!listaMsgSilp.isEmpty()) {
					for (ApiMessage apiMessage : listaMsgSilp) {
						ApiError loErr = new ApiError();
						loErr.setCode(MsgComonl.COMCOMP0004.getMessage());
						loErr.setErrorMessage(apiMessage.getMessage());
						apiErrors.add(loErr);
					}
				}
			} catch (Exception err) {
				err.printStackTrace();
				apiErrors.add(MsgComonl.COMCOME0001.getError());
			}

		} else {
			apiErrors.add(MsgComonl.COMCOME0002.getError());

		}

		response.setApiErrors(apiErrors);
	}
}
