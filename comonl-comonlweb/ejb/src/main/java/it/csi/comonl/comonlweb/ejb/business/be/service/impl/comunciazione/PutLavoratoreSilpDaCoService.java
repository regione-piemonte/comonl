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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;


import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorLavoratoreSilpEspansoDaCo;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutLavoratoreSilpDaCoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.PutLavoratoreSilpDaCoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperLavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.external.impl.silp.LavoratoreHelperImpl;
import it.csi.comonl.comonlweb.lib.external.impl.silp.invoker.SilpBusinessException;
import it.csi.comonl.comonlweb.lib.external.impl.silp.invoker.SilpRestAdapter;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ApiMessage;

public class PutLavoratoreSilpDaCoService extends BaseChkService<PutLavoratoreSilpDaCoRequest, PutLavoratoreSilpDaCoResponse> {

	private WrapperLavoratoreSilpEspanso wrapperLavoratoreSilpEspanso;
	private Boolean flgRettifica;
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad        the DAD for the comunicazione
	 */
	public PutLavoratoreSilpDaCoService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad,  ControlliDad controlliDad) {
		super(configurationHelper, controlliDad, comunicazioneDad);
	}
	
	@Override
	protected void checkServiceParams() {
		wrapperLavoratoreSilpEspanso = request.getWrapperLavoratoreSilpEspanso();
		flgRettifica = request.getFlgRettifica();
    }
	
	@Override
	protected void execute() {
		
		LavoratoreSilpEspanso lavoratore = wrapperLavoratoreSilpEspanso.getLavoratoreSilpEspanso();
		
		List<ApiError> apiErrors = new ArrayList<ApiError>();
		// Se RETTIFICA necessario controllo dei dati essenziali qualora sia fuori tempo.
		if(flgRettifica) {
			Comunicazione comunicazione = wrapperLavoratoreSilpEspanso.getComunicazione();
			boolean checkDatiEssenziali = isCheckDatiEssenziali(comunicazione, wrapperLavoratoreSilpEspanso.getRuolo());
			String cfLavoratore = wrapperLavoratoreSilpEspanso.getLavoratoreSilpEspanso().getId().getCodiceFiscale();
			 List<Lavoratore> lavoratori = comunicazione.getLavoratori();
			 Lavoratore lavoratoreCo = null;
			 if(lavoratori != null && lavoratori.size() > 0) {
				 boolean found = false;
				 for(int i = 0; i < lavoratori.size() && !found; i++ ) {
					 found = lavoratori.get(i).getCodiceFiscale().equalsIgnoreCase(cfLavoratore);
					 if(found) {
						 lavoratoreCo = lavoratori.get(i);
					 }
				 }
			 }
			 
			 
			 if(lavoratoreCo != null) {
				 Validator<LavoratoreSilpEspanso> validator = new ValidatorLavoratoreSilpEspansoDaCo(lavoratore, controlliDad, checkDatiEssenziali, lavoratoreCo);
				 if(!validator.isOk()) {
					response.addApiErrors(validator.getApiErrors());
					return;
				}
			 }
		}
		
		if (lavoratore != null) {

			try {
				SilpRestAdapter.getInstance().saveLavoratore(getUtente().getCodiceFiscale(), lavoratore);
				LavoratoreHelperImpl lavoratoreHelperImpl = new LavoratoreHelperImpl();
				LavoratoreSilpEspanso feedBackSalvataggioLavoratoreSilp = lavoratoreHelperImpl.ricercaPerCodiceFiscale(lavoratore.getId().getCodiceFiscale());
				if(feedBackSalvataggioLavoratoreSilp == null) {
					//TODO errore nel salvataggio ADD messaggio
				}
				response.setCfLavoratore(feedBackSalvataggioLavoratoreSilp.getId().getCodiceFiscale());
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
