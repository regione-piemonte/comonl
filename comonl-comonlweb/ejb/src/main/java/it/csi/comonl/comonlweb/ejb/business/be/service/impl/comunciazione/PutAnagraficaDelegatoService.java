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

import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutAnagraficaDelegatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutAnagraficaDelegatoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.controlli.CodiceFiscale;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

/**
 * PostAnagraficaDelegatoService
 */
public class PutAnagraficaDelegatoService
		extends BaseAnagraficaDelegatoService<PutAnagraficaDelegatoRequest, PutAnagraficaDelegatoResponse> {

	private DecodificaDad decodificaDad;
	private ControlliService iControlli;

	private AnagraficaDelegato anagraficaDelegato;
	

	public PutAnagraficaDelegatoService(ConfigurationHelper configurationHelper,
			AnagraficaDelegatoDad anagraficaDelegatoDad, DecodificaDad decodificaDad) {
		super(configurationHelper, anagraficaDelegatoDad);
		this.decodificaDad = decodificaDad;
		this.iControlli = new ControlliService(configurationHelper);
	}

	@Override
	protected void checkServiceParams() {
		anagraficaDelegato = request.getAnagraficaDelegato();
		checkNotNull(anagraficaDelegato, "anagraficaDelegato", true);
	}

	@Override
	protected void execute() {

		List<ApiError> apiErrors = new ArrayList<ApiError>();

		String cfDelegato = anagraficaDelegato.getId().getCfDelegato();
		//controllo coerenza cfDelegato
		if(!CodiceFiscale.getInstance().controllaCodiceFiscaleSoggetto(cfDelegato, decodificaDad)) {
			apiErrors.add(MsgComonl.COMTUTE0069.getError());
			response.setApiErrors(apiErrors);
			return;
		}

		StatiEsteri statiEsteri = anagraficaDelegato.getStatiEsteri();
		Comune comuneNasc = anagraficaDelegato.getComuneNasc();
		if (comuneNasc == null && statiEsteri == null) {
			apiErrors.add(MsgComonl.COMCOME0007.getError());
		} else if (ComonlUtility.isNotVoid(comuneNasc) && ComonlUtility.isNotVoid(comuneNasc.getId())
				&& ComonlUtility.isNotVoid(statiEsteri) && ComonlUtility.isNotVoid(statiEsteri.getId())) {
			apiErrors.add(MsgComonl.COMCOME0014.getError());
		} else {
			iControlli.checkComuneValidity(decodificaDad, comuneNasc, apiErrors, MsgComonl.COMCOME1851.getError(),
					MsgComonl.COMCOME1854.getError());
			iControlli.checkStatoEsteroValidity(decodificaDad, statiEsteri, apiErrors, MsgComonl.COMCOME1859.getError(),
					MsgComonl.COMCOME1854.getError());
		}

		iControlli.checkComuneValidity(decodificaDad, anagraficaDelegato.getComuneRes(), apiErrors,
				MsgComonl.COMCOME1852.getError(), MsgComonl.COMCOME1855.getError());
		/* I dati del domicilio non sono obbligatori */
//		iControlli.checkComuneValidity(decodificaDad, anagraficaDelegato.getComuneDom(), apiErrors,
//				MsgComonl.COMCOME1853.getError(), MsgComonl.COMCOME1856.getError());

		iControlli.checkCampoObbligatorio(anagraficaDelegato.getCognome(), apiErrors, MsgComonl.COMCOME1860.getError());
		iControlli.checkCampoObbligatorio(anagraficaDelegato.getNome(), apiErrors, MsgComonl.COMCOME1861.getError());
		iControlli.checkCampoObbligatorio(anagraficaDelegato.getIndirizzoRes(), apiErrors,
				MsgComonl.COMCOME1862.getError());

		if (!apiErrors.isEmpty()) {
			response.setApiErrors(apiErrors);
			return;
		}

		anagraficaDelegato.setComuneRes(anagraficaDelegato.getComuneRes());
		anagraficaDelegato.setComuneDom(anagraficaDelegato.getComuneDom());
		anagraficaDelegato.setComuneNasc(comuneNasc);
		/*
		 * Ripulisco i campi da eventuali spazi vuoti in attesa di capire come gestire
		 * il trim se lato front-end o back-end
		 */
//		anagraficaDelegato.setCognome(anagraficaDelegato.getCognome().trim());
//		anagraficaDelegato.setNome(anagraficaDelegato.getNome().trim());
//		anagraficaDelegato.setIndirizzoRes(anagraficaDelegato.getIndirizzoDom().trim());

		String tipoAnagrafica = anagraficaDelegato.getId().getTipoAnagrafica();
		if (!tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_CONSULENTE_C)) {
			anagraficaDelegato.setSoggettoAbilitato(null);
		}

		anagraficaDelegato = anagraficaDelegatoDad.updateAnagraficaDelegato(anagraficaDelegato);

		response.setAnagraficaDelegato(anagraficaDelegato);
	}

}
