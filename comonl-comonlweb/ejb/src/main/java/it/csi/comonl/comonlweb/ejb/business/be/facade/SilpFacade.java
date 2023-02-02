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
package it.csi.comonl.comonlweb.ejb.business.be.facade;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutLavoratoreSilpDaCoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp.GetAziendaAaepService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp.GetAziendaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp.GetLavoratoreService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp.GetSediService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp.PutAziendaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp.PutLavoratoreService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutLavoratoreSilpDaCoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.silp.GetAziendaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.silp.GetLavoratoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.silp.GetSediRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.silp.PutAziendaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.silp.PutLavoratoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.GetAziendaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.GetLavoratoreResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.GetSediResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.PutAziendaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.PutLavoratoreResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.PutLavoratoreSilpDaCoResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperLavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;

/**
 * Facade for the /silp path
 */
@Stateless
public class SilpFacade extends BaseFacade {

	@Inject
	private DecodificaDad decodificaDad;
	
	@Inject
	private  ComunicazioneDad comunicazioneDad;
	
	@Inject
	private ControlliDad controlliDad;

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetAziendaResponse getAziendaSilp(String codiceFiscale) {
		return executeService(new GetAziendaRequest(codiceFiscale),
				new GetAziendaService(configurationHelper, decodificaDad));
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetAziendaResponse getAziendaAaep(String codiceFiscale) {
		return executeService(new GetAziendaRequest(codiceFiscale),
				new GetAziendaAaepService(configurationHelper, decodificaDad));
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetLavoratoreResponse getLavoratoreSilpEspanso(String codiceFiscale, String cognome, String nome) {
		return executeService(new GetLavoratoreRequest(codiceFiscale, cognome, nome),
				new GetLavoratoreService(configurationHelper, decodificaDad));
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetSediResponse getSediSilp(String idAzienda) {
		return executeService(new GetSediRequest(idAzienda), new GetSediService(configurationHelper, decodificaDad));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutAziendaResponse putAziendaSilp(DatiAzienda azienda) {
		return executeService(new PutAziendaRequest(azienda), new PutAziendaService(configurationHelper));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutLavoratoreResponse putLavoratoreSilp(LavoratoreSilpEspanso lavoratoreSilpEspanso) {
		return executeService(new PutLavoratoreRequest(lavoratoreSilpEspanso),
				new PutLavoratoreService(configurationHelper));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutLavoratoreSilpDaCoResponse putLavoratoreSilpDaCo(WrapperLavoratoreSilpEspanso wrapperLavoratoreSilpEspanso,Boolean flgRettifica) {
		return executeService(new PutLavoratoreSilpDaCoRequest(wrapperLavoratoreSilpEspanso, flgRettifica),
				new PutLavoratoreSilpDaCoService(configurationHelper,comunicazioneDad,controlliDad));
	}
	
}
