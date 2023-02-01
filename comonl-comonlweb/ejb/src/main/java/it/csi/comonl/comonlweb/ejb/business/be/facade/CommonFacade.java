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
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.common.GetApplicativoAbilitatoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.common.GetParametroCommaxService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.common.GetParametroService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.common.GetRuoliPerServizioEsternoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.common.GetRuoloService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.common.PreCompilaAnagraficaByCfService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp.InsertUserAccessLogService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.common.GetParametroCommaxRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.common.GetParametroRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.common.GetRuoloRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.common.InsertUserAccessLogRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.common.PreCompilaAnagraficaByCfRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.common.GetParametroCommaxResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.common.GetParametroResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.common.GetRuoloResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.common.InsertUserAccessLogResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.common.PreCompilaAnagraficaByCfResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UserAccessLog;

/**
 * Facade for the /common path
 */
@Stateless
public class CommonFacade extends BaseFacade {

	@Inject
	private CommonDad commonDad;
	@Inject
	private DecodificaDad decodificaDad;
	@Inject
	private AnagraficaDelegatoDad anagraficaDelegatoDad;

	@Inject
	private DelegaDad delegaDad;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public InsertUserAccessLogResponse insertUserAccessLog(UserAccessLog loUserLog) {
		return executeService(new InsertUserAccessLogRequest(loUserLog),
				new InsertUserAccessLogService(configurationHelper, commonDad));
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetRuoloResponse getRuoli() {
		return executeService(new GetRuoloRequest(),
				new GetRuoloService(configurationHelper, commonDad, decodificaDad, anagraficaDelegatoDad, delegaDad));
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetRuoloResponse getRuoliPerServizioEsterno(String codiceFiscale) {
		return executeService(new GetRuoloRequest(codiceFiscale),
				new GetRuoliPerServizioEsternoService(configurationHelper, commonDad, anagraficaDelegatoDad));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public PreCompilaAnagraficaByCfResponse preCompilaAnagraficaByCf(String cf) {
		return executeService(new PreCompilaAnagraficaByCfRequest(cf),
				new PreCompilaAnagraficaByCfService(configurationHelper, commonDad, decodificaDad));
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetParametroResponse getParametroByDescrizione(String nomeParam) {
		return executeService(new GetParametroRequest(nomeParam),
				new GetParametroService(configurationHelper, commonDad));
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetParametroCommaxResponse getParametroCommaxById(Long idParametro) {
		return executeService(new GetParametroCommaxRequest(idParametro),
				new GetParametroCommaxService(configurationHelper, commonDad));
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public GetParametroResponse getApplicativoAbilitato() {
		return executeService(new GetParametroRequest(),
				new GetApplicativoAbilitatoService(configurationHelper, commonDad));
	}

}
