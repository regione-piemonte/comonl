/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - WAR submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.web.business.be.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.comonl.comonlweb.ejb.business.be.facade.AnagraficaDelegatoFacade;
import it.csi.comonl.comonlweb.ejb.business.be.facade.SilpFacade;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.AddAziendaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostAnagraficaDelegatoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.GetAziendaResponse;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaAccreditamentoAnagrafiche;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.web.business.be.api.AnagraficaDelegatoApi;
import it.csi.comonl.comonlweb.web.util.annotation.Logged;
import it.csi.util.performance.StopWatch;

/**
 * Implementation for ComunicazioneApi
 */
@Logged
public class AnagraficaDelegatoApiServiceImpl extends BaseRestServiceImpl implements AnagraficaDelegatoApi {

	@EJB
	private AnagraficaDelegatoFacade anagraficaDelegatoFacade;

	@EJB
	private SilpFacade silpFacade;

	@Override
	public Response postRicercaAccreditamentoConsulente(
			FormRicercaAccreditamentoAnagrafiche formRicercaAccreditamentoAnagrafiche, @Min(0) Integer page,
			@Min(1) @Max(100) Integer limit, String sort, String direction, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postRicercaComunicazioni]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> anagraficaDelegatoFacade.postRicercaAccreditamentoConsulente(page, limit, sort,
					direction, formRicercaAccreditamentoAnagrafiche));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getRicercaAccreditamentoConsulente()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass())
							+ ".getRicercaAccreditamentoConsulente",
					"");
			watcher.stop();
		}
	}

	@Override
	public Response postRicercaAccreditamentoAzienda(
			FormRicercaAccreditamentoAnagrafiche formRicercaAccreditamentoAnagrafiche, @Min(0) Integer page,
			@Min(1) @Max(100) Integer limit, String sort, String direction, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postRicercaAccreditamentoAzienda]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> anagraficaDelegatoFacade.postRicercaAccreditamentoAzienda(page, limit, sort, direction,
					formRicercaAccreditamentoAnagrafiche));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "postRicercaAccreditamentoAzienda()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass())
							+ ".postRicercaAccreditamentoAzienda",
					"");
			watcher.stop();
		}
	}

	@Override
	public Response getDelegatoImpresaByIdAnagraficaDelegato(String cfDelegato, String tipoAnagrafica,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postRicercaAccreditamentoAzienda]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> anagraficaDelegatoFacade.getDelegatoImpresaByIdAnagraficaDelegato(cfDelegato,
					tipoAnagrafica));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "postRicercaAccreditamentoAzienda()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass())
							+ ".postRicercaAccreditamentoAzienda",
					"");
			watcher.stop();
		}
	}

	@Override
	public Response postAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		log.info(ComonlClassUtils.truncClassName(getClass()), "[postAnagraficaDelegato]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {

			return invoke(() -> anagraficaDelegatoFacade.postAnagraficaDelegato(anagraficaDelegato));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),
					"postAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato)",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postAnagraficaDelegato", "");
			watcher.stop();
		}
	}

	@Override
	public Response putAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putAnagraficaDelegato]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {

			return invoke(() -> anagraficaDelegatoFacade.putAnagraficaDelegato(anagraficaDelegato));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),
					"putAnagraficaDelegatoAnagraficaDelegato anagraficaDelegato)",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putAnagraficaDelegato", "");
			watcher.stop();
		}
	}

	@Override
	public Response getAnagraficaDelegatoByIdSoggettoAbilitato(Long idSoggettoAbilitato,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getAnagraficaDelegatoByIdSoggettoAbilitato]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(
					() -> anagraficaDelegatoFacade.getAnagraficaDelegatoByIdSoggettoAbilitato(idSoggettoAbilitato));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),
					"getAnagraficaDelegatoByIdSoggettoAbilitato()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass())
							+ ".getAnagraficaDelegatoByIdSoggettoAbilitato",
					"");
			watcher.stop();
		}
	}

	@Override
	public Response addConsulente(String cfDelegato, Long idSoggettoAbilitato, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[addConsulente]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> anagraficaDelegatoFacade.addConsulente(cfDelegato, idSoggettoAbilitato));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "addConsulente()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".addConsulente", "");
			watcher.stop();
		}
	}
	
	
	@Override
	public Response removeConsulente(String cfDelegato, Long idSoggettoAbilitato, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[removeConsulente]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> anagraficaDelegatoFacade.removeConsulente(cfDelegato, idSoggettoAbilitato));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "removeConsulente()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".removeConsulente", "");
			watcher.stop();
		}
	}

	@Override
	public Response setDataAnnullamentoCDE(String cfDelegato, String tipoAnagrafica, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> anagraficaDelegatoFacade.setDataAnnullamentoCDE(cfDelegato, tipoAnagrafica));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getAttivazioneCDE()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getAttivazioneCDE", "");
			watcher.stop();
		}
	}

	@Override
	public Response addAzienda(DelegatoImpresa delegatoImpresa, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[addAzienda]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			/*
			 * return invoke(() -> anagraficaDelegatoFacade.addAzienda(cfDelegato,
			 * tipoAnagrafica, cfImpresa));
			 */
			String tipoAnagrafica = delegatoImpresa.getId().getTipoAnagrafica();
			String cfImpresa = delegatoImpresa.getId().getCfImpresa();
			//String cfDelegato = delegatoImpresa.getId().getCfDelegato();

			AddAziendaResponse addAziendaResponse = new AddAziendaResponse();
			GetAziendaResponse getAziendaResponse = silpFacade.getAziendaSilp(cfImpresa);

			List<ApiError> apiErrors = new ArrayList<ApiError>();

			if (getAziendaResponse != null) {

				if (getAziendaResponse.getApiErrors() != null && getAziendaResponse.getApiErrors().size() > 0) {
					addAziendaResponse.setApiErrors(getAziendaResponse.getApiErrors());
					return addAziendaResponse.composeResponse();
				}

				DatiAzienda datiAzienda = getAziendaResponse.getDatiAzienda();

				if (datiAzienda == null) {
					// azienda non trovata
					apiErrors.add(MsgComonl.COMCOME1025.getError());
					addAziendaResponse.setApiErrors(apiErrors);
					return addAziendaResponse.composeResponse();
				}

				/*
				 * DelegatoImpresaPK delegatoImpresaPK = new DelegatoImpresaPK();
				 * 
				 * delegatoImpresaPK.setCfDelegato(cfDelegato);
				 * delegatoImpresaPK.setTipoAnagrafica(tipoAnagrafica);
				 * delegatoImpresa.setId(delegatoImpresaPK);
				 */
				delegatoImpresa.setDenominazione(tipoAnagrafica);

				delegatoImpresa.setDenominazione(datiAzienda.getDenominazioneDatoreLavoro());

				addAziendaResponse = anagraficaDelegatoFacade.addAzienda(delegatoImpresa);
			}

			return addAziendaResponse.composeResponse();

		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "addAzienda()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".addAzienda", "");
			watcher.stop();
		}
	}

	@Override
	public Response getAziendaAccentrata(String cfImpresa, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getAziendaAccentrata]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> anagraficaDelegatoFacade.getAziendaAccentrata(cfImpresa));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getAziendaAccentrata()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getAziendaAccentrata", "");
			watcher.stop();
		}
	}

	@Override
	public Response putAziendaAccentrata(AnagraficaAziAccent anagraficaAziAccent, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putAziendaAccentrata]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> anagraficaDelegatoFacade.putAziendaAccentrata(anagraficaAziAccent));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "putAziendaAccentrata()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putAziendaAccentrata", "");
			watcher.stop();
		}
	}

	@Override
	public Response postAziendaAccentrata(AnagraficaAziAccent anagraficaAziAccent, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postAziendaAccentrata]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> anagraficaDelegatoFacade.postAziendaAccentrata(anagraficaAziAccent));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "postAziendaAccentrata()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postAziendaAccentrata", "");
			watcher.stop();
		}
	}

	@Override
	public Response stampaElencoAnagraficheDelegati(String tipoFormato,
			FormRicercaAccreditamentoAnagrafiche formRicercaAccreditamentoAnagrafiche, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postAziendaAccentrata]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> anagraficaDelegatoFacade.stampaElencoAnagraficheDelegati(tipoFormato,formRicercaAccreditamentoAnagrafiche));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "postAziendaAccentrata()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postAziendaAccentrata", "");
			watcher.stop();
		}
	}

	@Override
	public Response stampaElencoStudiAziende(String tipoFormato,
			FormRicercaAccreditamentoAnagrafiche formRicercaAccreditamentoAnagrafiche, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postAziendaAccentrata]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> anagraficaDelegatoFacade.stampaElencoStudiAziende(tipoFormato,formRicercaAccreditamentoAnagrafiche));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "postAziendaAccentrata()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postAziendaAccentrata", "");
			watcher.stop();
		}
	}
	
	
	@Override
	public Response getAnagraficaDelegatoByCodiceFiscale(@PathParam("cfDelegato") String cfDelegato, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getAnagraficaDelegatoByCodiceFiscale]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> {
				PostAnagraficaDelegatoResponse res = anagraficaDelegatoFacade.getAnagraficaDelegatoByCodiceFiscale(cfDelegato);
				if (res.getAnagraficaDelegato() == null) {
					List<ApiError> apiErrors = new ArrayList<ApiError>();
					apiErrors.add(MsgComonl.COMCOME1824.getError());
					res.setApiErrors(apiErrors);
				}
				return res;
				});
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getAnagraficaDelegatoByCodiceFiscale()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getAnagraficaDelegatoByCodiceFiscale", "");
			watcher.stop();
		}
	}
	

}
