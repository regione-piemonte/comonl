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
package it.csi.comonl.comonlweb.ejb.business.be.facade;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegatoImpresaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SoggettoAbilitatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.AddAziendaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.AddConsulenteService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.GetAnagraficaDelegatoByIdSoggettoAbilitatoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.GetAnagraficaDelegatoCfService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.GetAziendaAccentrataService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.GetDelegatoImpresaByIdAnagraficaDelegatoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostAnagraficaDelegatoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostAziendaAccentrataService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostRicercaAccreditamentoAziendaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostRicercaAccreditamentoConsulenteService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutAnagraficaDelegatoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutAziendaAccentrataService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.RemoveConsulenteService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.SetDataAnnullamentoCDEService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.StampaAnagraficheDelegatiService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.StampaStudiAziendeService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.AddAziendaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.AddConsulenteRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetAnagraficaDelegatoByIdSoggettoAbilitatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetAziendaAccentrataRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetDelegatoImpresaByIdAnagraficaDelegatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostAnagraficaDelegatoCfRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostAnagraficaDelegatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostAziendaAccentrataRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostRicercaAccreditamentoAnagraficaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostStampaElencoAnagraficheRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutAnagraficaDelegatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutAziendaAccentrataRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.RemoveConsulenteRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.SetDataAnnullamentoCDERequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.AddAziendaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.AddConsulenteResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetAnagraficaDelegatoByIdSoggettoAbilitatoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetAziendaAccentrataResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetDelegatoImpresaByIdAnagraficaDelegatoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PdfResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostAnagraficaDelegatoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostAziendaAccentrataResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRicercaAccreditamentoAziendaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRicercaAccreditamentoConsulenteResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutAnagraficaDelegatoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutAziendaAccentrataResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.RemoveConsulenteResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.SetDataAnnullamentoCDEResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaAccreditamentoAnagrafiche;

/**
 * Facade for the /comunicazione path
 */
@Stateless
public class AnagraficaDelegatoFacade extends BaseFacade {
	@Inject
	private AnagraficaDelegatoDad anagraficaDelegatoDad;
	
	@Inject
	private DecodificaDad decodificaDad;
	
	@Inject
	private DelegatoImpresaDad delegatoImpresaDad;
	
	@Inject
	private DelegaDad delegaDad;
	
	@Inject
	private SoggettoAbilitatoDad soggettoAbilitatoDad;
	
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public PostRicercaAccreditamentoConsulenteResponse postRicercaAccreditamentoConsulente(Integer page, Integer limit, String sort, String direction,
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		return executeService(new PostRicercaAccreditamentoAnagraficaRequest(page, limit, sort, direction, ricercaAccreditamentoAnagrafiche),
				new PostRicercaAccreditamentoConsulenteService(configurationHelper, anagraficaDelegatoDad));
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public PostRicercaAccreditamentoAziendaResponse postRicercaAccreditamentoAzienda(Integer page, Integer limit, String sort, String direction,
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		return executeService(new PostRicercaAccreditamentoAnagraficaRequest(page, limit, sort, direction, ricercaAccreditamentoAnagrafiche),
				new PostRicercaAccreditamentoAziendaService(configurationHelper, anagraficaDelegatoDad));
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Lock(LockType.READ)
	public GetDelegatoImpresaByIdAnagraficaDelegatoResponse getDelegatoImpresaByIdAnagraficaDelegato(String cfDelegato, String tipoAnagrafica) {
		return executeService(
					new GetDelegatoImpresaByIdAnagraficaDelegatoRequest(cfDelegato,tipoAnagrafica), new GetDelegatoImpresaByIdAnagraficaDelegatoService(configurationHelper, anagraficaDelegatoDad)
				);
	}
	
	/**
	 * Posts a AnagraficaDelegato
	 * 
	 * @param anagraficaDelegato
	 * @return the anagraficaDelegato
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostAnagraficaDelegatoResponse postAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato) {
		return executeService(new PostAnagraficaDelegatoRequest(anagraficaDelegato),
				new PostAnagraficaDelegatoService(configurationHelper, anagraficaDelegatoDad, decodificaDad ));
	}
	
	
	
	/**
	 * Posts a AnagraficaDelegato
	 * 
	 * @param anagraficaDelegato
	 * @return the anagraficaDelegato
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutAnagraficaDelegatoResponse putAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato) {
		return executeService(new PutAnagraficaDelegatoRequest(anagraficaDelegato),
				new PutAnagraficaDelegatoService(configurationHelper, anagraficaDelegatoDad, decodificaDad ));
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Lock(LockType.READ)
	public GetAnagraficaDelegatoByIdSoggettoAbilitatoResponse getAnagraficaDelegatoByIdSoggettoAbilitato(Long idSoggettoAbilitato) {
		return executeService(
					new GetAnagraficaDelegatoByIdSoggettoAbilitatoRequest(idSoggettoAbilitato), new GetAnagraficaDelegatoByIdSoggettoAbilitatoService(configurationHelper, anagraficaDelegatoDad)
				);
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public AddConsulenteResponse addConsulente(String cfDelegato, Long idSoggettoAbilitato) {
		return executeService(
					new AddConsulenteRequest(cfDelegato,idSoggettoAbilitato), new AddConsulenteService(configurationHelper, anagraficaDelegatoDad)
				);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public RemoveConsulenteResponse removeConsulente(String cfDelegato, Long idSoggettoAbilitato) {
		return executeService(
					new RemoveConsulenteRequest(cfDelegato,idSoggettoAbilitato), new RemoveConsulenteService(configurationHelper, anagraficaDelegatoDad, soggettoAbilitatoDad)
				);
	}
	
	/**
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public SetDataAnnullamentoCDEResponse setDataAnnullamentoCDE(String cfDelegato, String tipoAnagrafica) {
		return executeService(new SetDataAnnullamentoCDERequest(cfDelegato,tipoAnagrafica),
				new SetDataAnnullamentoCDEService(configurationHelper, anagraficaDelegatoDad ));
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public AddAziendaResponse addAzienda(DelegatoImpresa delegatoImpresa) {
		return executeService(
					new AddAziendaRequest(delegatoImpresa), new AddAziendaService(configurationHelper, anagraficaDelegatoDad, delegatoImpresaDad,delegaDad)
				);
	}
		
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Lock(LockType.READ)
	public GetAziendaAccentrataResponse getAziendaAccentrata(String cfImpresa) {
		return executeService(
					new GetAziendaAccentrataRequest(cfImpresa), new GetAziendaAccentrataService(configurationHelper, anagraficaDelegatoDad)
				);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutAziendaAccentrataResponse putAziendaAccentrata(AnagraficaAziAccent anagraficaAziAccent) {
		return executeService(
				new PutAziendaAccentrataRequest(anagraficaAziAccent), new PutAziendaAccentrataService(configurationHelper, anagraficaDelegatoDad)
				);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostAziendaAccentrataResponse postAziendaAccentrata(AnagraficaAziAccent anagraficaAziAccent) {
		return executeService(
				new PostAziendaAccentrataRequest(anagraficaAziAccent), new PostAziendaAccentrataService(configurationHelper, anagraficaDelegatoDad)
				);
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Lock(LockType.WRITE)
	public PdfResponse stampaElencoAnagraficheDelegati(String tipoFormato, FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		return executeService(new PostStampaElencoAnagraficheRequest(tipoFormato, ricercaAccreditamentoAnagrafiche),
				new StampaAnagraficheDelegatiService(configurationHelper, anagraficaDelegatoDad));
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Lock(LockType.WRITE)
	public PdfResponse stampaElencoStudiAziende(String tipoFormato, FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		return executeService(new PostStampaElencoAnagraficheRequest(tipoFormato, ricercaAccreditamentoAnagrafiche),
				new StampaStudiAziendeService(configurationHelper, anagraficaDelegatoDad));
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Lock(LockType.WRITE)
	public PostAnagraficaDelegatoResponse getAnagraficaDelegatoByCodiceFiscale(String cfDelegato) {
		return executeService(new PostAnagraficaDelegatoCfRequest(cfDelegato),
				new GetAnagraficaDelegatoCfService(configurationHelper, anagraficaDelegatoDad));
	}
}
