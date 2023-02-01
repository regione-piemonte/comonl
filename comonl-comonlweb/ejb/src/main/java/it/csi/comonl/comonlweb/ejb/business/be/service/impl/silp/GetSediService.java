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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp;

import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.base.BaseService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.silp.GetSediRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.GetSediResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Toponimo;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.dto.silp.Sede;
import it.csi.comonl.comonlweb.lib.external.impl.silp.AziendaHelperImpl;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

public class GetSediService extends BaseService<GetSediRequest, GetSediResponse> {

	DecodificaDad decodificaDad;

	public GetSediService(ConfigurationHelper configurationHelper, DecodificaDad decodificaDad) {
		super(configurationHelper);
		this.decodificaDad = decodificaDad;
	}

	@Override
	protected void execute() {
		try {
			String idAzienda = request.getIdAzienda();
			AziendaHelperImpl aziendaHelperImpl = new AziendaHelperImpl();
			
			List<Sede> leSedi = new ArrayList<Sede>();
			
			try {
			leSedi = aziendaHelperImpl.getElencoSediByIdAzienda(idAzienda);
			}
			catch (Exception err) {
				log.error("GetSediService", err);
			}

			if (!leSedi.isEmpty()) {
				for (Sede laSede : leSedi) {
					// comune della Sede
					if (laSede.getComune() != null && ComonlUtility.isNotVoid(laSede.getComune().getCodComuneMin())) {
						try {
							Comune ilComune = decodificaDad
									.getComuneByCodComuneMin(laSede.getComune().getCodComuneMin());
							laSede.setComune(ilComune);
						} catch (Exception e) {
							laSede.setComune(null);
						}
					}

					// Stato estero
					if (laSede.getStatiEsteri() != null
							&& ComonlUtility.isNotVoid(laSede.getStatiEsteri().getCodNazioneMin())) {
						try {
							StatiEsteri loStatoEstero = decodificaDad
									.getStatoEsteroByCodStatoMin(laSede.getStatiEsteri().getCodNazioneMin());
							laSede.setStatiEsteri(loStatoEstero);
						} catch (Exception e) {
							laSede.setStatiEsteri(null);
						}
					}
					// Toponimo
					if (laSede.getToponimoSede() != null
							&& ComonlUtility.isNotVoid(laSede.getToponimoSede().getIdComTToponimo())) {
						try {
							Toponimo ilTopo = decodificaDad
									.getToponimoById(laSede.getToponimoSede().getIdComTToponimo());
							laSede.setToponimoSede(ilTopo);
						} catch (Exception e) {
							laSede.setToponimoSede(null);
						}
					}
					// TODO
					/*
					 * da chiarire se il legaleRappresentante bisogna recuperarlo anche per tutte le
					 * sedi legate all'azienda. Fosse così il legale va inserito nella sede e non
					 * nell'azienda
					 */
//					if (laSede.isSedeLegale()) {
//						Referente referente = SilpRestAdapter.getInstance().getReferente(getUtente().getCodiceFiscale(),
//								new BigDecimal(laSede.getIdSedeSilp()));
//					}
				}
			}
			response.setLeSedi(leSedi);
		} catch (Exception e) {
			log.error(ComonlClassUtils.truncClassName(getClass()) + " Eccezione !!", e);
			List<ApiError> apiErrors = new ArrayList<>();
			apiErrors.add(MsgComonl.COMCOMP0003.getError());
			response.setApiErrors(apiErrors);
		}
	}

}
