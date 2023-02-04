/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - SRV submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.srv.business.be.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.comonl.comonlweb.ejb.business.be.facade.CommonFacade;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.common.GetParametroResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.common.GetRuoloResponse;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.srv.business.be.api.RuoliApi;
import it.csi.comonl.comonlweb.srv.util.annotation.Logged;
import it.csi.util.performance.StopWatch;

/**
 * Implementation for ComunicazioneApi
 */
@Logged
public class RuoliApiServiceImpl extends BaseRestServiceImpl implements RuoliApi {

	@EJB
	private CommonFacade commonFacade;

	@Override
	public Response getRuoli(String idChiamante, String codiceFiscaleIdentita, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		log.info(ComonlClassUtils.truncClassName(getClass()), "[getRuoli]");
		GetRuoloResponse laResponse = new GetRuoloResponse();
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {

			List<ApiError> loApi = new ArrayList<ApiError>();
			GetParametroResponse verificaServizioSeAttivo = commonFacade
					.getParametroByDescrizione(ComonlConstants.VERIFY_IF_SERVICE_IS_WORKING);

			if (ComonlUtility.isNotVoid(verificaServizioSeAttivo.getParametro().getValoreParametro())
					&& verificaServizioSeAttivo.getParametro().getValoreParametro().equals(ComonlConstants.FLAG_N)) {
				loApi.add(MsgComonl.COMCOMP0003.getError());
				laResponse.setApiErrors(loApi);
				return laResponse.composeResponse();
			}

			GetParametroResponse ilParametroTrovato = commonFacade.getParametroByDescrizione(idChiamante);
			if ((ComonlUtility.isNotVoid(ilParametroTrovato.getParametro())
					&& ComonlUtility.isNotVoid(ilParametroTrovato.getParametro().getValoreParametro())
					&& !ilParametroTrovato.getParametro().getValoreParametro().equals(ComonlConstants.FLAG_S))
					|| ComonlUtility.isVoid(ilParametroTrovato.getParametro())) {
				ApiError loErrore = new ApiError();
				loErrore.setErrorMessage("APPLICAZIONE CON IDENTIFICATIVO CHIAMANTE = " + idChiamante
						+ " NON VALIDO PER ACCEDERE AL SERVIZIO DI PROFILAZIONE!");
				loApi.add(loErrore);
				laResponse.setApiErrors(loApi);
				return laResponse.composeResponse();
			}
			if (ComonlUtility.isNotVoid(codiceFiscaleIdentita)) {
				/*
				 * Il SERVIZIO E' DISPONIBILE E L'IDENTIFICATIVO CHIAMANTE E' VALIDO E ABILITATO
				 * PER LA PROFILAZIONE QUINDI SI PROCEDE AL RECUPERO DELLA LISTA RUOLI SE IL
				 * CODICE FISCALE NON E' VUOTO
				 */
				return invoke(() -> commonFacade.getRuoliPerServizioEsterno(codiceFiscaleIdentita));

			} else {
				ApiError loErrore = new ApiError();
				loErrore.setErrorMessage("NESSUN CODICE FISCALE DA RICERCARE !! ");
				loApi.add(loErrore);
				laResponse.setApiErrors(loApi);
				return laResponse.composeResponse();
			}

		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getRuoli()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getRuoli", "");
			watcher.stop();
		}

	}
}
