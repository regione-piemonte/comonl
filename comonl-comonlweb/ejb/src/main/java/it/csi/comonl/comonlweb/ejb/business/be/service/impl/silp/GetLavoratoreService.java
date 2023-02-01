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
import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.base.BaseService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.silp.GetLavoratoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.GetLavoratoreResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Toponimo;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloStudio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.external.impl.silp.LavoratoreHelperImpl;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

/**
 * GetLavoratore
 */
public class GetLavoratoreService extends BaseService<GetLavoratoreRequest, GetLavoratoreResponse> {

	DecodificaDad decodificaDad;

	public GetLavoratoreService(ConfigurationHelper configurationHelper, DecodificaDad decodificaDad) {
		super(configurationHelper);
		this.decodificaDad = decodificaDad;
	}

	@Override
	protected void execute() {
		try {
//			String codiceFiscale = request.getCodiceFiscale();
//			checkBusinessCondition(ComonlUtility.controllaCFConOmocodia(codiceFiscale),
//					MsgComonl.COMCOMP0002.getError());

			/* Viene recuperato il lavoratore da Silp */
			LavoratoreHelperImpl lavoratoreHelperImpl = new LavoratoreHelperImpl();
			LavoratoreSilpEspanso lavoratoreSilpEspanso = lavoratoreHelperImpl
					.ricercaLavoratorePerCodiceFiscaleCognomeNome(request.getCodiceFiscale(), request.getCognome(),
							request.getNome());
			if (ComonlUtility.isNotVoid(lavoratoreSilpEspanso)) {

				// comune nascita
				if (lavoratoreSilpEspanso.getComuneNascita() != null
						&& ComonlUtility.isNotVoid(lavoratoreSilpEspanso.getComuneNascita().getCodComuneMin())) {
					try {
						Comune ilComune = (Comune) decodificaDad
								.getTfromMin(Comune.class, lavoratoreSilpEspanso.getComuneNascita().getCodComuneMin(), lavoratoreSilpEspanso.getDataNascita());
						if (ilComune==null) {
							ilComune = (Comune) decodificaDad
									.getTfromMin(Comune.class, lavoratoreSilpEspanso.getComuneNascita().getCodComuneMin(), null);
						}
						lavoratoreSilpEspanso.setComuneNascita(ilComune);
					} catch (Exception e) {
						lavoratoreSilpEspanso.setComuneNascita(null);
					}
				}
				// comune Domicilio
				if (lavoratoreSilpEspanso.getComuneDomicilio() != null
						&& ComonlUtility.isNotVoid(lavoratoreSilpEspanso.getComuneDomicilio().getCodComuneMin())) {
					try {
						Comune ilComune = decodificaDad
								.getComuneByCodComuneMin(lavoratoreSilpEspanso.getComuneDomicilio().getCodComuneMin());
						lavoratoreSilpEspanso.setComuneDomicilio(ilComune);
					} catch (Exception e) {
						lavoratoreSilpEspanso.setComuneDomicilio(null);
					}
				}
				// comune Residenza
				if (lavoratoreSilpEspanso.getComuneResidenza() != null
						&& ComonlUtility.isNotVoid(lavoratoreSilpEspanso.getComuneResidenza().getCodComuneMin())) {
					try {
						Comune ilComune = decodificaDad
								.getComuneByCodComuneMin(lavoratoreSilpEspanso.getComuneResidenza().getCodComuneMin());
						lavoratoreSilpEspanso.setComuneResidenza(ilComune);
					} catch (Exception e) {
						lavoratoreSilpEspanso.setComuneResidenza(null);
					}
				}

				// cittadinanza
				if (lavoratoreSilpEspanso.getCittadinanza() != null
						&& ComonlUtility.isNotVoid(lavoratoreSilpEspanso.getCittadinanza().getCodCittadinanzaMin())) {
					try {
						Cittadinanza laCittadinanza = decodificaDad.getCittadinanzaByCodMin(
								lavoratoreSilpEspanso.getCittadinanza().getCodCittadinanzaMin());
						lavoratoreSilpEspanso.setCittadinanza(laCittadinanza);
					} catch (Exception e) {
						lavoratoreSilpEspanso.setCittadinanza(null);
					}
				}

				// status straniero
				if (lavoratoreSilpEspanso.getStatusStraniero() != null
						&& ComonlUtility.isNotVoid(lavoratoreSilpEspanso.getStatusStraniero().getCodStatusMin())) {
					try {
						StatusStraniero loStatus = decodificaDad.getStatusStranieroByCodMin(
								lavoratoreSilpEspanso.getStatusStraniero().getCodStatusMin());
						lavoratoreSilpEspanso.setStatusStraniero(loStatus);
					} catch (Exception e) {
						lavoratoreSilpEspanso.setStatusStraniero(null);
					}
				}

				// questura
				if (lavoratoreSilpEspanso.getQuestura() != null
						&& ComonlUtility.isNotVoid(lavoratoreSilpEspanso.getQuestura().getCodQuesturaMin())) {
					try {
						Questura laQuestura = decodificaDad
								.getQuesturaByCodMin(lavoratoreSilpEspanso.getQuestura().getCodQuesturaMin());
						lavoratoreSilpEspanso.setQuestura(laQuestura);
					} catch (Exception e) {
						lavoratoreSilpEspanso.setQuestura(null);
					}
				}

				// livello studio
				if (lavoratoreSilpEspanso.getLivelloStudio() != null
						&& ComonlUtility.isNotVoid(lavoratoreSilpEspanso.getLivelloStudio().getCodiceLivelloMin())) {
					try {
						LivelloStudio ilLivello = decodificaDad.getLivelloStudioByCodMin(
								lavoratoreSilpEspanso.getLivelloStudio().getCodiceLivelloMin());
						lavoratoreSilpEspanso.setLivelloStudio(ilLivello);
					} catch (Exception e) {
						lavoratoreSilpEspanso.setLivelloStudio(null);
					}
				}

				// Motivo Permesso di soggiorno
				if (lavoratoreSilpEspanso.getMotivoPermesso() != null
						&& ComonlUtility.isNotVoid(lavoratoreSilpEspanso.getMotivoPermesso().getCodMotivoMin())) {
					try {
						MotivoPermesso ilMotivo = decodificaDad
								.getMotivoPermessoByCodMin(lavoratoreSilpEspanso.getMotivoPermesso().getCodMotivoMin());
						lavoratoreSilpEspanso.setMotivoPermesso(ilMotivo);
					} catch (Exception e) {
						lavoratoreSilpEspanso.setMotivoPermesso(null);
					}
				}

				// Stato estero Nascita
				if (lavoratoreSilpEspanso.getStatiEsteriNascita() != null
						&& ComonlUtility.isNotVoid(lavoratoreSilpEspanso.getStatiEsteriNascita().getCodNazioneMin())) {
					try {
						StatiEsteri loStatoNascita = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
								lavoratoreSilpEspanso.getStatiEsteriNascita().getCodNazioneMin(),
								lavoratoreSilpEspanso.getDataNascita());
						if(loStatoNascita==null) {
							loStatoNascita = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
									lavoratoreSilpEspanso.getStatiEsteriNascita().getCodNazioneMin(),null);
						}
						lavoratoreSilpEspanso.setStatiEsteriNascita(loStatoNascita);
					} catch (Exception e) {
						lavoratoreSilpEspanso.setStatiEsteriNascita(null);
					}
				}
				// Stato estero Residenza
				if (lavoratoreSilpEspanso.getStatiEsteriResidenza() != null && ComonlUtility
						.isNotVoid(lavoratoreSilpEspanso.getStatiEsteriResidenza().getCodNazioneMin())) {
					try {
						StatiEsteri loStatoREs = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
								lavoratoreSilpEspanso.getStatiEsteriResidenza().getCodNazioneMin(),new Date());
						lavoratoreSilpEspanso.setStatiEsteriResidenza(loStatoREs);
					} catch (Exception e) {
						lavoratoreSilpEspanso.setStatiEsteriResidenza(null);
					}
				}

				// Toponimo Residenza
				if (lavoratoreSilpEspanso.getToponimoDomicilio() != null
						&& ComonlUtility.isNotVoid(lavoratoreSilpEspanso.getToponimoDomicilio().getId())) {
					try {
						Toponimo ilTopo = decodificaDad
								.getToponimoById(lavoratoreSilpEspanso.getToponimoDomicilio().getId());
						lavoratoreSilpEspanso.setToponimoDomicilio(ilTopo);
					} catch (Exception e) {
						lavoratoreSilpEspanso.setToponimoDomicilio(null);
					}
				}

				// Toponimo Domicilio
				if (lavoratoreSilpEspanso.getToponimoResidenza() != null
						&& ComonlUtility.isNotVoid(lavoratoreSilpEspanso.getToponimoResidenza().getId())) {
					try {
						Toponimo ilTopo = decodificaDad
								.getToponimoById(lavoratoreSilpEspanso.getToponimoResidenza().getId());
						lavoratoreSilpEspanso.setToponimoResidenza(ilTopo);
					} catch (Exception e) {
						lavoratoreSilpEspanso.setToponimoResidenza(null);
					}
				}

			}

			if (null != lavoratoreSilpEspanso) {
				response.setLavoratoreSilpEspanso(lavoratoreSilpEspanso);
			} else {
				List<ApiError> apiErrors = new ArrayList<>();
				apiErrors.add(MsgComonl.COMCOMP0002.getError());
				response.setApiErrors(apiErrors);
			}

		} catch (Exception e) {
			log.error(ComonlClassUtils.truncClassName(getClass()) + " Eccezione !!", e);
			List<ApiError> apiErrors = new ArrayList<>();
			apiErrors.add(MsgComonl.COMCOMP0003.getError());
			response.setApiErrors(apiErrors);
		}
	}

}
