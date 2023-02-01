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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostAnagraficaDelegatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostAnagraficaDelegatoResponse;
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
public class PostAnagraficaDelegatoService
		extends BaseAnagraficaDelegatoService<PostAnagraficaDelegatoRequest, PostAnagraficaDelegatoResponse> {

	private DecodificaDad decodificaDad;

	private AnagraficaDelegato anagraficaDelegato;

	private ControlliService iControlli;
	

	public PostAnagraficaDelegatoService(ConfigurationHelper configurationHelper,
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
		String tipoAnagrafica = anagraficaDelegato.getId().getTipoAnagrafica();
		// TODO controllo correttezza cfDelegato

		/* se il controllo sul cfDelegato è andato a buon fine */

		/*
		 * Il sistema al salvataggio verifica che non sia presente una persona fisica
		 * con lo stesso codice fiscale e con lo stesso tipo anagrafica
		 */
		AnagraficaDelegato ExistingAnagraficaDelegato = anagraficaDelegatoDad.getAnagraficaDelegatoById(cfDelegato,
				tipoAnagrafica);
		
		
		if (ExistingAnagraficaDelegato != null) {
			apiErrors.add(MsgComonl.COMCOME1021.getError());
			response.setApiErrors(apiErrors);
			return;
		}
		/*
		 * Inoltre non è possibile inserire la stessa persona come “Consulente” e
		 * “Delegato”.
		 */
		if (tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_CONSULENTE_C)
				|| tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_DELEGATO_D)) {
			
			
			ExistingAnagraficaDelegato = anagraficaDelegatoDad.getAnagraficaDelegatoById(cfDelegato,
					ComonlConstants.TIPOLOGIA_ANAGRAFICA_CONSULENTE_C);
			if (ExistingAnagraficaDelegato != null &&  ExistingAnagraficaDelegato.getDataAnnullamento() == null) {
				
				apiErrors.add(MsgComonl.COMCOME1027.getError("tipo","Consulente"));
				response.setApiErrors(apiErrors);
				return;
			}
			
			
			
			
			
			ExistingAnagraficaDelegato = anagraficaDelegatoDad.getAnagraficaDelegatoById(cfDelegato,
					ComonlConstants.TIPOLOGIA_ANAGRAFICA_DELEGATO_D );
			if (ExistingAnagraficaDelegato != null && ExistingAnagraficaDelegato.getDataAnnullamento() == null) {
				apiErrors.add(MsgComonl.COMCOME1027.getError("tipo","Delegato"));
				response.setApiErrors(apiErrors);
				return;
			}
			
			
			
			
			
		}
		
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

		// Comune Residenza
		iControlli.checkComuneValidity(decodificaDad, anagraficaDelegato.getComuneRes(), apiErrors,
				MsgComonl.COMCOME1852.getError(), MsgComonl.COMCOME1855.getError());

		/* I dati del domicilio non sono obbligatori */
		// Comune Domicilio
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
		
		String luogoNascita = new String();
		if(comuneNasc != null && comuneNasc.getCodComuneMin() != null && !comuneNasc.getCodComuneMin().equals("")) {
			luogoNascita = comuneNasc.getCodComuneMin();
		}
		if(statiEsteri != null && statiEsteri.getCodNazioneMin() != null && !statiEsteri.getCodNazioneMin().equals("")) {
			luogoNascita = statiEsteri.getCodNazioneMin();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dtNascita = sdf.format(anagraficaDelegato.getDtNascita());
		
		if(!CodiceFiscale.getInstance().checkCF(anagraficaDelegato.getCognome(), anagraficaDelegato.getNome(), dtNascita, anagraficaDelegato.getSesso(), luogoNascita, cfDelegato)) {
			apiErrors.add(MsgComonl.COMCFE0001.getError());
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
//		anagraficaDelegato.setIndirizzoRes(anagraficaDelegato.getIndirizzoRes().trim());

		if (!tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_CONSULENTE_C)) {
			anagraficaDelegato.setSoggettoAbilitato(null);
		}

		anagraficaDelegato = anagraficaDelegatoDad.insertAnagraficaDelegato(anagraficaDelegato);

		response.setAnagraficaDelegato(anagraficaDelegato);
	}

}
