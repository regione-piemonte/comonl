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

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import it.csi.comonl.comonlweb.ejb.business.be.facade.ComunicazioneFacade;
import it.csi.comonl.comonlweb.ejb.business.be.facade.ComunicazioneStatelessFacade;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRecuperoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperRapporto;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.web.business.be.api.ComunicazioneApi;
import it.csi.comonl.comonlweb.web.dto.WebComunicazioneFileHolder;
import it.csi.comonl.comonlweb.web.util.annotation.Logged;
import it.csi.util.performance.StopWatch;

/**
 * Implementation for ComunicazioneApi
 */
@Logged
public class ComunicazioneApiServiceImpl extends BaseRestServiceImpl implements ComunicazioneApi {

	@EJB
	private ComunicazioneFacade comunicazioneFacade;
	
	@EJB
	private ComunicazioneStatelessFacade comunicazioneStatelessFacade;

	@Override
	public Response uploadComunicazioni(
			@MultipartForm WebComunicazioneFileHolder webComunicazioneFileHolder,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		log.info(ComonlClassUtils.truncClassName(getClass()), "[uploadComunicazioni]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			Ruolo ruolo = new Ruolo();
			ruolo.setIlRuolo(webComunicazioneFileHolder.getIlRuolo());
			ruolo.setCodiceFiscaleUtente(webComunicazioneFileHolder.getCodiceFiscaleUtente());
			ruolo.setDsCognome(webComunicazioneFileHolder.getDsCognome());
			ruolo.setDsNome(webComunicazioneFileHolder.getDsNome());
			ruolo.setCodiceFiscaleAzienda(webComunicazioneFileHolder.getCodiceFiscaleAzienda());
			ruolo.setConsulenteRespo(webComunicazioneFileHolder.getConsulenteRespo());
			ruolo.setAmministratore(webComunicazioneFileHolder.getIsAmministratore());
			ruolo.setOperatoreProvinciale(webComunicazioneFileHolder.getIsOperatoreProvinciale());
			ruolo.setDelegatoRespo(webComunicazioneFileHolder.getIsDelegatoRespo());
			ruolo.setLegaleRappresentante(webComunicazioneFileHolder.getIsLegaleRappresentante());
			ruolo.setPersonaAutorizzata(webComunicazioneFileHolder.getIsPersonaAutorizzata());
			ruolo.setEmail(webComunicazioneFileHolder.getEmailRuolo());
			
			return invoke(() -> comunicazioneStatelessFacade.uploadComunicazioni(webComunicazioneFileHolder.toFileHolder(), ruolo));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "uploadComunicazioni()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".uploadComunicazioni", "");
			watcher.stop();
		}

	}
	
	
	@Override
	public Response stampaRicercaComunicazioni(FormRicercaComunicazione ricercaComunicazione, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[stampaRicercaComunicazioni]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.stampaRicercaComunicazioni(ricercaComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "stampaRicercaComunicazioni()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".stampaRicercaComunicazioni", "");
			watcher.stop();
		}

	}
	@Override
	public Response stampaRicercaComunicazioniVardatori(FormRicercaComunicazione ricercaComunicazione, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[stampaRicercaComunicazioniVardatori]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.stampaRicercaComunicazioniVardatori(ricercaComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "stampaRicercaComunicazioniVardatori()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".stampaRicercaComunicazioniVardatori", "");
			watcher.stop();
		}
		
	}

	@Override
	public Response postRicercaComunicazioni(FormRicercaComunicazione ricercaComunicazione, @Min(0) Integer page,
			@Min(1) @Max(100) Integer limit, String sort, String direction, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postRicercaComunicazioni]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.postRicercaComunicazioni(page, limit, sort, direction,
					ricercaComunicazione));
		} 
		catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getRicercaComunicazioni()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getRicercaComunicazioni", "");
			watcher.stop();
		}

	}
	
	
	@Override
	public Response postRecuperoComunicazioni(
			FormRecuperoComunicazione recuperoComunicazione,
			SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postRecuperoComunicazioni]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> comunicazioneStatelessFacade.postRecuperoComunicazioni(recuperoComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "postRecuperoComunicazioni()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postRecuperoComunicazioni", "");
			watcher.stop();
		}

	}
	
	@Override
	public Response postRicercaVardatori(FormRicercaComunicazione ricercaComunicazione, @Min(0) Integer page,
			@Min(1) @Max(100) Integer limit, String sort, String direction, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postRicercaVardatori]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.postRicercaVardatori(page, limit, sort, direction,
					ricercaComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getRicercaVardatori()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getRicercaVardatori", "");
			watcher.stop();
		}

	}
	
	@Override
	public Response postRicercaComunicazioniVardatori(FormRicercaComunicazione ricercaComunicazione, @Min(0) Integer page,
			@Min(1) @Max(100) Integer limit, String sort, String direction, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postRicercaComunicazioniVardatori]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.postRicercaComunicazioniVardatori(page, limit, sort, direction,
					ricercaComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "postRicercaComunicazioniVardatori()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postRicercaComunicazioniVardatori", "");
			watcher.stop();
		}
		
	}

	@Override
	public Response getComunicazioneById(Long idComunicazione, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getProspettoById]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.getComunicazioneById(idComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getComunicazioneById()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getComunicazioneById", "");
			watcher.stop();
		}

	}

	@Override
	public Response postComunicazioneUrgenza(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postComunicazioneUrgenza]");
		watcher.start();
		try {

			return invoke(() -> comunicazioneFacade.postComunicazioneUrgenza(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),
					"postComunicazioneUrgenza(WrapperComunicazione wrapperComunicazione)",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postComunicazioneUrgenza", "");
			watcher.stop();
		}

	}
	
	
	
	@Override
	public Response putComunicazioneUrgenza(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putComunicazioneUrgenza]");
		watcher.start();
		try {

			return invoke(() -> comunicazioneFacade.putComunicazioneUrgenza(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),
					"putComunicazioneUrgenza(WrapperComunicazione wrapperComunicazione)",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putComunicazioneUrgenza", "");
			watcher.stop();
		}

	}
	
	

	@Override
	public Response getComunicazioneUrgHolderById(Long idComunicazione, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getProspettoById]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.getComunicazioneUrgHolderById(idComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getComunicazioneUrgenzaHolderById()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass())
							+ ".getComunicazioneUrgenzaHolderById",
					"");
			watcher.stop();
		}

	}
	
	@Override
	public Response verificaDatore(WrapperComunicazione wrapperComunicazione, 
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[verificaDatore]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			
			return invoke(() -> comunicazioneFacade.verificaDatore(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "verificaDatore()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".verificaDatore", "");
			watcher.stop();
		}

	}
	
	
	@Override
	public Response stampaComunicazioneById(Long id, Boolean operatoreProvinciale, SecurityContext securityContext, HttpHeaders httpHeaders,HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[stampaComunicazioneById]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.stampaComunicazioneById(id, operatoreProvinciale));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "stampaComunicazioneById()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".stampaComunicazioneById", "");
			watcher.stop();
		}

	}
	@Override
	public Response postComunicazione(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postComunicazione]");
		watcher.start();
		try {
			// Passare il ruolo ai servizi di controllo
			return invoke(() -> comunicazioneFacade.postComunicazione(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"postComunicazione()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postComunicazione", "");
			watcher.stop();
		}

	}
	@Override
	public Response putComunicazione(WrapperComunicazione wrapper,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putComunicazione]");
		watcher.start();
		try {
			// Passare il ruolo ai servizi di controllo
			return invoke(() -> comunicazioneFacade.putComunicazione(wrapper));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"putComunicazione()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putComunicazione", "");
			watcher.stop();
		}
	}
	
	@Override
	public Response putComunicazioneVardatore(WrapperComunicazione wrapper,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putComunicazioneVardatore]");
		watcher.start();
		try {
			// Passare il ruolo ai servizi di controllo
			return invoke(() -> comunicazioneFacade.putComunicazioneVardatore(wrapper));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"putComunicazioneVardatore()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putComunicazioneVardatore", "");
			watcher.stop();
		}
	}
	
	@Override
	public Response inviaComunicazione(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postComunicazione]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneStatelessFacade.inviaComunicazione(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"postComunicazione()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postComunicazione", "");
			watcher.stop();
		}
	}
	
	@Override
	public Response annullaComunicazione(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postComunicazione]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.annullaComunicazione(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"postComunicazione()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postComunicazione", "");
			watcher.stop();
		}
	}
	
	
	
	@Override
	public Response annullaComunicazioneVardatore(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[annullaComunicazioneVardatore]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.annullaComunicazioneVardatore(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"annullaComunicazioneVardatore()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".annullaComunicazioneVardatore", "");
			watcher.stop();
		}
	}
	
	
	
	@Override
	public Response rettificaComunicazione(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[rettificaComunicazione]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.rettificaComunicazione(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"rettificaComunicazione()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".rettificaComunicazione", "");
			watcher.stop();
		}
	}
	
	
	
	
	@Override
	public Response rettificaComunicazioneVardatore(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[rettificaComunicazioneVardatre]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.rettificaComunicazioneVardatore(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"rettificaComunicazioneVardatre()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".rettificaComunicazioneVardatre", "");
			watcher.stop();
		}
	}
	
	
	
	@Override
	public Response postTutore(Comunicazione comunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postTutore]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.postTutore(comunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"postTutore()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postTutore", "");
			watcher.stop();
		}
	}
	
	@Override
	public Response putTutore(Comunicazione comunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putTutore]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.putTutore(comunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"putTutore()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putTutore", "");
			watcher.stop();
		}
	}
	
	@Override
	public Response postRapportoMissione(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putRapporto]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.postRapportoMissione(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"putRapporto()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putRapporto", "");
			watcher.stop();
		}
	}
	
	@Override
	public Response putRapportoMissione(WrapperRapporto wrapperRapporto,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putRapporto]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.putRapportoMissione(wrapperRapporto));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"putRapporto()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putRapporto", "");
			watcher.stop();
		}
	}
	@Override
	public Response putDatiProroga(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putDatiProroga]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.putDatiProroga(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"putDatiProroga()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putDatiProroga", "");
			watcher.stop();
		}
	}
	
	@Override
	public Response putDatiTrasformazione(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putDatiTrasformazione]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.putDatiTrasformazione(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"putDatiTrasformazione()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putDatiTrasformazione", "");
			watcher.stop();
		}
	}
	@Override
	public Response putDatiCessazione(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putDatiCessazione]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.putDatiCessazione(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"putDatiCessazione()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putDatiCessazione", "");
			watcher.stop();
		}
	}
	
	
	@Override
	public Response putDatiTrasferimentoDistacco(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putDatiTrasferimentoDistacco]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.putDatiTrasferimentoDistacco(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"putDatiTrasferimentoDistacco()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putDatiTrasferimentoDistacco", "");
			watcher.stop();
		}
	}
			
	public Response chkDatiGenerali(
			WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, 
			HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[chkDatiGenerali]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.chkDatiGenerali(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"chkDatiGenerali()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".chkDatiGenerali", "");
			watcher.stop();
		}
	};
	
	
	
	
	public Response chkDatiGeneraliVardatori(
			WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, 
			HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[chkDatiGeneraliVardatori]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.chkDatiGeneraliVardatori(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"chkDatiGeneraliVardatori()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".chkDatiGeneraliVardatori", "");
			watcher.stop();
		}
	};
	
	
	/**
	 * chkAziendaUtilizzatrice
	 * 
	 * @param wrapperComunicazione   comunicazione di cui controllare i dati relativi all'azienda utilizzatrice e il ruolo
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	public Response chkAziendaUtilizzatrice(
			WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, 
			HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[chkAziendaUtilizzatrice]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.chkAziendaUtilizzatrice(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"chkAziendaUtilizzatrice()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".chkAziendaUtilizzatrice", "");
			watcher.stop();
		}
	}	
	
	@Override
	public Response chkImpresa(
			WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, 
			HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[chkImpresa]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.chkImpresa(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"chkImpresa()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".chkImpresa", "");
			watcher.stop();
		}
	}

	/**
	 * chkLavoratore
	 * 
	 * @param lavoratore   lavoratore da controllare prima di essere inserito nella comunicazione
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	public Response chkLavoratore(
			Lavoratore lavoratore,
			SecurityContext securityContext, 
			HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[chkImpresa]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.chkLavoratore(lavoratore));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"chkLavoratore()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".chkLavoratore", "");
			watcher.stop();
		}
	}
	
	/**
	 * chkLavoratore
	 * 
	 * @param wrapperComunicazione   comunicazione di cui controllare i dati del lavoratore e ruolo 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	public Response chkLavoratoriComunicazione(
			WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, 
			HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[chkLavoratoriComunicazione]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.chkLavoratoriComunicazione(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"chkLavoratoriComunicazione()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".chkLavoratoriComunicazione", "");
			watcher.stop();
		}
	}

	/**
	 * chkDatiRapporto
	 * 
	 * @param comunicazione   comunicazione di cui controllare i dati relativi al rapporto di lavoro
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	public Response chkRapporto(
			Comunicazione comunicazione,
			SecurityContext securityContext, 
			HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[chkRapporto]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.chkRapporto(comunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"chkRapporto()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".chkRapporto", "");
			watcher.stop();
		}
	}


	/**
	 * chkTutore
	 * 
	 * @param comunicazione   comunicazione di cui controllare i dati relativi al tutore
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	public Response chkTutore(
			Comunicazione comunicazione,
			SecurityContext securityContext, 
			HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[chkTutore]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.chkTutore(comunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"chkTutore()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".chkTutore", "");
			watcher.stop();
		}
	}
///////////////////////// RITRASMISSIONE COMMAX
	@Override
	public Response ritrasmettiComunicazioni(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		log.info(ComonlClassUtils.truncClassName(getClass()), "[ritrasmettiComunicazioni]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> comunicazioneStatelessFacade.ritrasmettiComunicazioni());
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "ritrasmettiComunicazioni()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".ritrasmettiComunicazioni", "");
			watcher.stop();
		}

	}
	
	@Override
	public Response postComunicazioneVardatore(WrapperComunicazione wrapperComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postComunicazioneVardatore]");
		watcher.start();
		try {
			// Passare il ruolo ai servizi di controllo
			return invoke(() -> comunicazioneFacade.postComunicazioneVardatore(wrapperComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"postComunicazioneVardatore()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postComunicazioneVardatore", "");
			watcher.stop();
		}

	}
	
	@Override
	public Response cancellaComunicazione(Long id, SecurityContext securityContext, HttpHeaders httpHeaders,HttpServletRequest httpRequest) {
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[cancellaComunicazione]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.cancellaComunicazione(id));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"cancellaComunicazione()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".cancellaComunicazione", "");
			watcher.stop();
		}	

	}


	@Override
	public Response chkDatiAziendaPrecedente(Comunicazione comunicazione, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[chkDatiGeneraliVardatori]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.chkDatiAziendaPrecedente(comunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"chkDatiAziendaPrecedente()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".chkDatiAziendaPrecedente", "");
			watcher.stop();
		}
	}

	@Override
	public Response chkSediLavoro(Comunicazione comunicazione, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		log.info(ComonlClassUtils.truncClassName(getClass()), "[chkSediLavoro]");
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.chkSediLavoro(comunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),"chkSediLavoro()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".chkSediLavoro", "");
			watcher.stop();
		}
	}

	@Override
	public Response chkRiepilogo(Long idComunicazione, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[chkInvioFuoriTempo]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.chkRiepilogo(idComunicazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "chkRiepilogo()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".chkRiepilogo", "");
			watcher.stop();
		}
	}
	
	@Override
	public Response checkDatiEssenziali(WrapperComunicazione wrapperComunicazione, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[checkDatiEssenziali]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> comunicazioneFacade.checkDatiEssenziali(wrapperComunicazione.getComunicazione(),wrapperComunicazione.getRuolo()));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "checkDatiEssenziali()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".checkDatiEssenziali", "");
			watcher.stop();
		}
	}

}
