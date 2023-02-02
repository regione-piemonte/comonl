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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.base.BaseService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.silp.GetAziendaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.GetAziendaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.LegaleRappr;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Toponimo;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;
import it.csi.comonl.comonlweb.lib.dto.silp.Sede;
import it.csi.comonl.comonlweb.lib.external.impl.silp.AziendaHelperImpl;
import it.csi.comonl.comonlweb.lib.external.impl.silp.exception.ServiziSilpException;
import it.csi.comonl.comonlweb.lib.external.impl.silp.invoker.SilpRestAdapter;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

/**
 * GetAzienda
 */
public class GetAziendaService extends BaseService<GetAziendaRequest, GetAziendaResponse> {

	DecodificaDad decodificaDad;

	public GetAziendaService(ConfigurationHelper configurationHelper, DecodificaDad decodificaDad) {
		super(configurationHelper);
		this.decodificaDad = decodificaDad;
	}

	@Override
	protected void execute() {
		try {
			String codiceFiscale = request.getCodiceFiscale();
			AziendaHelperImpl aziendaHelperImpl = new AziendaHelperImpl();
			DatiAzienda datiAzienda = aziendaHelperImpl.ricercaPerCodiceFiscale(codiceFiscale);
			if (datiAzienda != null) {

				if (null != datiAzienda.getSedeLegale() && null != datiAzienda.getSedeLegale().getIdSedeSilp()) {
					LegaleRappr ilLegaleDellaSedeLegale = SilpRestAdapter.getInstance().getReferente(
							getUtente().getCodiceFiscale(),
							new BigDecimal(datiAzienda.getSedeLegale().getIdSedeSilp()));
					if (null != ilLegaleDellaSedeLegale) {
						ilLegaleDellaSedeLegale.setComune(getComune(ilLegaleDellaSedeLegale.getComune(), ilLegaleDellaSedeLegale.getDtNascita()));
						ilLegaleDellaSedeLegale
								.setStatiEsteri(getStatiEsteri(ilLegaleDellaSedeLegale.getStatiEsteri()));
						ilLegaleDellaSedeLegale
								.setCittadinanza(getCittadinanza(ilLegaleDellaSedeLegale.getCittadinanza()));
						ilLegaleDellaSedeLegale
								.setMotivoPermesso(getMotivoPermesso(ilLegaleDellaSedeLegale.getMotivoPermesso()));
						ilLegaleDellaSedeLegale.setQuestura(getQuestura(ilLegaleDellaSedeLegale.getQuestura()));
						ilLegaleDellaSedeLegale
								.setStatusStraniero(getStatusStraniero(ilLegaleDellaSedeLegale.getStatusStraniero()));

						datiAzienda.setIlLegaleRapprDellaSedeLegale(ilLegaleDellaSedeLegale);
					}
				}

				datiAzienda.setComune(getComune(datiAzienda.getComune()));
				if (datiAzienda.getSedeLegale() != null) {
					// Comune Sede Legale
					datiAzienda.getSedeLegale().setComune(getComune(datiAzienda.getSedeLegale().getComune()));
					// Stati Sede Legale
					datiAzienda.getSedeLegale()
							.setStatiEsteri(getStatiEsteri(datiAzienda.getSedeLegale().getStatiEsteri()));
					// Toponimo
					if (datiAzienda.getSedeLegale().getToponimoSede() != null && ComonlUtility
							.isNotVoid(datiAzienda.getSedeLegale().getToponimoSede().getIdComTToponimo())) {
						try {
							Toponimo ilTopo = decodificaDad
									.getToponimoById(datiAzienda.getSedeLegale().getToponimoSede().getIdComTToponimo());
							datiAzienda.getSedeLegale().setToponimoSede(ilTopo);
						} catch (Exception e) {
							datiAzienda.getSedeLegale().setToponimoSede(null);
						}
					}

				}

				// ATECOFIN
				if (datiAzienda.getAtecofin() != null
						&& ComonlUtility.isNotVoid(datiAzienda.getAtecofin().getCodAtecofinMin())) {
					try {
						Atecofin ateco = decodificaDad
								.getAtecoByCodAtecoFin(datiAzienda.getAtecofin().getCodAtecofinMin());
						datiAzienda.setAtecofin(ateco);
					} catch (Exception e) {
						datiAzienda.setAtecofin(null);
					}
				}

				// CCNL
				if (datiAzienda.getCcnl() != null && ComonlUtility.isNotVoid(datiAzienda.getCcnl().getCodCcnlMin())) {
					try {
						Ccnl ccnl = decodificaDad.getCcnlByCodCcnl(datiAzienda.getCcnl().getCodCcnlMin());
						datiAzienda.setCcnl(ccnl);
					} catch (Exception e) {
						datiAzienda.setCcnl(null);
					}
				}

				// StatiEsteri
				datiAzienda.setStatiEsteri(getStatiEsteri(datiAzienda.getStatiEsteri()));

				// recupero dati dalle eventuali elenco sedi
				if (datiAzienda.getElencoSedi() != null && !datiAzienda.getElencoSedi().isEmpty()) {
					for (Sede laSedeDelloElenco : datiAzienda.getElencoSedi()) {
						if (laSedeDelloElenco != null) {
							// Comune Sede Legale
							laSedeDelloElenco.setComune(getComune(laSedeDelloElenco.getComune()));
							// Stati Sede Legale
							laSedeDelloElenco.setStatiEsteri(getStatiEsteri(laSedeDelloElenco.getStatiEsteri()));
						}	
						
						if (laSedeDelloElenco.getToponimoSede() != null) {
							try {
								Toponimo ilTopo = decodificaDad
										.getToponimoById(laSedeDelloElenco.getToponimoSede().getIdComTToponimo());
								laSedeDelloElenco.setToponimoSede(ilTopo);
							} catch (Exception e) {
								laSedeDelloElenco.setToponimoSede(null);
							}
						}

					}
				}
				response.setDatiAzienda(datiAzienda);
			} else {
				List<ApiError> apiErrors = new ArrayList<>();
				apiErrors.add(MsgComonl.COMCOMP0002.getError());
				response.setApiErrors(apiErrors);
			}
			
		} catch (ServiziSilpException e) {
			e.printStackTrace();
			List<ApiError> apiErrors = new ArrayList<>();
			apiErrors.add(new ApiError(e.getCodice(),e.getDescrizione()));
			response.setApiErrors(apiErrors);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ComonlClassUtils.truncClassName(getClass()) + " Eccezione !!", e);
			List<ApiError> apiErrors = new ArrayList<>();
			apiErrors.add(MsgComonl.COMCOMP0003.getError());
			response.setApiErrors(apiErrors);
		}
	}

	private StatusStraniero getStatusStraniero(StatusStraniero statusStraniero) {
		if (statusStraniero != null && ComonlUtility.isNotVoid(statusStraniero.getCodStatusMin())) {
			try {
				StatusStraniero loStatus = decodificaDad.getStatusStranieroByCodMin(statusStraniero.getCodStatusMin());
				return loStatus;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	private Questura getQuestura(Questura questura) {
		if (questura != null && ComonlUtility.isNotVoid(questura.getCodQuesturaMin())) {
			try {
				Questura laQuest = decodificaDad.getQuesturaByCodMin(questura.getCodQuesturaMin());
				return laQuest;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	private StatiEsteri getStatiEsteri(StatiEsteri stati) {
		if (stati != null && ComonlUtility.isNotVoid(stati.getCodNazioneMin())) {
			try {
				StatiEsteri loStatoEstero = decodificaDad.getStatoEsteroByCodStatoMin(stati.getCodNazioneMin());
				return loStatoEstero;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
	
	private Map<String, Comune> cacheComuneSedi;

	private Comune getComune(Comune comune) {
		return getComune(comune, null);
	}
	
	private Comune getComune(Comune comune, Date validita) {
		// Comune
		if (comune != null && ComonlUtility.isNotVoid(comune.getCodComuneMin())) {
			
			if (cacheComuneSedi != null && cacheComuneSedi.get(comune.getCodComuneMin()) != null)
				return cacheComuneSedi.get(comune.getCodComuneMin());
			
			try {
				Comune ilComune = decodificaDad.getComuneValidByCodComuneMin(comune.getCodComuneMin(),validita);
				
				if (cacheComuneSedi == null)
					cacheComuneSedi = new HashMap<String, Comune>();
				
				cacheComuneSedi.put(comune.getCodComuneMin(), ilComune);
				
				return ilComune;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	private Cittadinanza getCittadinanza(Cittadinanza laCittadina) {
		// Cittadinanza
		if (laCittadina != null && ComonlUtility.isNotVoid(laCittadina.getCodCittadinanzaMin())) {
			try {
				Cittadinanza laCittadTrovata = decodificaDad
						.getCittadinanzaByCodMin(laCittadina.getCodCittadinanzaMin());
				return laCittadTrovata;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	private MotivoPermesso getMotivoPermesso(MotivoPermesso motivoPermesso) {
		if (motivoPermesso != null && ComonlUtility.isNotVoid(motivoPermesso.getCodMotivoMin())) {
			try {
				MotivoPermesso ilMotivo = decodificaDad.getMotivoPermessoByCodMin(motivoPermesso.getCodMotivoMin());
				return ilMotivo;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

}
