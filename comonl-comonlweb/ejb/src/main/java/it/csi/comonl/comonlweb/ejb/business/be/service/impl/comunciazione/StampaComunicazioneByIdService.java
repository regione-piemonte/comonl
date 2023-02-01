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

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaTipoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetComunicazioneByIdOperatoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PdfResponse;
import it.csi.comonl.comonlweb.ejb.exception.NotFoundException;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.report.ReportComunicazione;
import it.csi.comonl.comonlweb.ejb.util.report.ReportUtilities;
import it.csi.comonl.comonlweb.ejb.util.report.Sezione;
import it.csi.comonl.comonlweb.ejb.util.report.SubSezione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.LegaleRappr;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportiLavoratoriSediInteressateVD;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Tutore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cessazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSomministrazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTracciato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.VariazioneSomm;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class StampaComunicazioneByIdService extends BaseComunicazioneService<GetComunicazioneByIdOperatoreRequest, PdfResponse> {

	private DecodificaTipoDad decodificaTipoDad;
	private AnagraficaDelegatoDad anagraficaDelegatoDad;
	private Boolean operatoreProvinciale;
	private Long idComunicazione;

	public StampaComunicazioneByIdService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad,
			DecodificaTipoDad decodificaTipoDad, AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, comunicazioneDad);
		this.decodificaTipoDad = decodificaTipoDad;
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
	}

	@Override
	protected void checkServiceParams() {
		checkNotNull(request.getId(), "id");
	}

	@Override
	protected void execute() {
		
		idComunicazione = request.getId();
		operatoreProvinciale = request.getOperatoreProvinciale();
		
		Comunicazione comunicazione = comunicazioneDad.getComunicazioneById(idComunicazione);

		if (comunicazione == null) {
			throw new NotFoundException("Comunicazione id:" + idComunicazione);
		}

		if (response != null)
			System.out.println(response.getClass().getName());
		
		try {
			
			String htmlContent = makePdf(comunicazione,operatoreProvinciale);
			ReportUtilities.makePdfResponse(response, htmlContent, "Comunicazione_" + comunicazione.getId());	
			
			// Richiesta di Giancarlo Cantoni il 28/07/2022
			
			/*
			String basePath = configurationHelper.getProperty(ConfigurationValue.FILE_SYSTEM_PDF);
			File dir = new File(basePath + "/" +getSubDirectory(idComunicazione));
			File targetFile = new File(dir,idComunicazione.longValue() + ".pdf.p7m");
			if (targetFile.exists()) {
				
				String htmlContent = makePdf(comunicazione,operatoreProvinciale);
				ReportUtilities.makePdfResponse(response, htmlContent, "Comunicazione_" + comunicazione.getId());	
				
				// generazione delle stampe precedenti al rilascio (28/07/2022)
				// ReportUtilities.makePdfFileResponse(response, targetFile, "" + idComunicazione.longValue());
			}
			else {
				targetFile = new File(dir,idComunicazione.longValue() + ".pdf");
				if (targetFile.exists()) {
					ReportUtilities.makePdfFileResponse(response, targetFile, "" + idComunicazione.longValue());
				}
				else {
					String htmlContent = makePdf(comunicazione,operatoreProvinciale);
					
					// CREAZIONE PDF E SALVATAGGIO SU FILESYSTEM
					// crea il file solamente se viene cancellato dopo l'invio
					if (comunicazione != null && comunicazione.getStatoComunicazione() != null &&
							ComonlConstants.STATO_COMUNICAZIONE_VALIDATA_ID.equals(comunicazione.getStatoComunicazione().getId()) &&
							comunicazione.getDtInvio() != null) {
						ReportUtilities.makePdfFile(basePath, htmlContent, idComunicazione.longValue());	
					}
					
					ReportUtilities.makePdfResponse(response, htmlContent, "Comunicazione_" + comunicazione.getId());	
				}
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			log.info("StampaComunicazioneByIdService", "idComunicazione: " + comunicazione.getId() + " - fine");
		}

	}
	
	public String makePdf(Comunicazione comunicazione, Boolean operatoreProvinciale) {
		this.operatoreProvinciale = operatoreProvinciale;
		if (comunicazione == null) {
			throw new NotFoundException("Comunicazione id:" + idComunicazione);
		}

		if (response != null)
			System.out.println(response.getClass().getName());

		ReportComunicazione reportComunicazione = null;
		TipoComunicazione tipo = comunicazione.getTipoComunicazione();
		TipoTracciato tipoTracciato = comunicazione.getTipoTracciato();
		TipoSomministrazione tipoSomm = comunicazione.getTipoSomministrazione();
		TipoTrasferimento tipoTrasferimento = comunicazione.getTipoTrasferimento();
		
		VariazioneSomm variazioneSomm = null;
		if (tipoSomm != null)
			variazioneSomm = decodificaTipoDad.getTipoVariazioneBySommComm(tipoSomm.getId(), tipo.getId());

		switch (tipo.getId()) {

		case ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE:
			if (tipoSomm != null && tipoSomm.getId().intValue() == 0) {
				// assunzione in somministrazione
				reportComunicazione = getReportComunicazioneAssunzioneSomm(comunicazione);
			} else if (tipoSomm != null && tipoSomm.getId().intValue() == 2) {
				// assunzione in somministrazione e missione
				reportComunicazione = getReportComunicazioneAssunzioneSommMiss(comunicazione);
			} else {
				// assunzione
				reportComunicazione = getReportComunicazioneAssunzione(comunicazione);
			}
			break;
		case ComonlConstants.TIPO_COMUNICAZIONE_PROROGA:
			if (variazioneSomm != null) {
				if ("2.01".equals(variazioneSomm.getCodTipoVariazioneMin())) {
					// proroga del rapporto di lavoro in assenza di missione
					reportComunicazione = getReportComunicazioneProrogaRapportoAssenzaMissione(comunicazione);
				} else if ("2.02".equals(variazioneSomm.getCodTipoVariazioneMin())) {
					// proroga del rapporto di lavoro e della missione
					reportComunicazione = getReportComunicazioneProrogaRapportoMissione(comunicazione);
				} else if ("2.03".equals(variazioneSomm.getCodTipoVariazioneMin())) {
					// proroga della mission in caso di rapporto indeterminato
					reportComunicazione = getReportComunicazioneProrogaMissioneRapportoIndeterminato(comunicazione);
				} else {
					// proroga
					reportComunicazione = getReportComunicazioneProroga(comunicazione);
				}
			} else
				reportComunicazione = getReportComunicazioneProroga(comunicazione);
			break;
		case ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE:
			if (variazioneSomm != null) {
				if ("3.01".equals(variazioneSomm.getCodTipoVariazioneMin())) {
					reportComunicazione = getReportComunicazioneTrasformazioneCostanzaMissione(comunicazione);
					// trasformazione in costanza di missione
				} else if ("3.02".equals(variazioneSomm.getCodTipoVariazioneMin())) {
					// trasformazione in costanza in assenza di missione
					reportComunicazione = getReportComunicazioneTrasformazioneCostanzaAssenzaMissione(comunicazione);
				} else {
					// trasformazione
					reportComunicazione = getReportComunicazioneTrasformazione(comunicazione);
				}
			} else
				reportComunicazione = getReportComunicazioneTrasformazione(comunicazione);
			break;
		case ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO:
			if (variazioneSomm != null) {
				if ("3.03".equals(variazioneSomm.getCodTipoVariazioneMin())) {
					// trasferimento di altra seda lavoro ditta utilizzatrice
					reportComunicazione = getReportComunicazioneTrasferimentoAltraSede(comunicazione);
				} else if ("3.04".equals(variazioneSomm.getCodTipoVariazioneMin())) {
					// trasferimento altra sede operativa
					reportComunicazione = getReportComunicazioneTrasferimentoSede(comunicazione);
				} 
			} else if (comunicazione.getRapporto() != null && comunicazione.getRapporto().getTrasformazionerl() != null
					&& "DL".equals(
							comunicazione.getRapporto().getTrasformazionerl().getCodTrasformazionirlMin())) {
				// distacco
				reportComunicazione = getReportComunicazioneDistacco(comunicazione);
			} else {
				// trasferimento
				reportComunicazione = getReportComunicazioneTrasferimento(comunicazione);
			}
			break;
		case ComonlConstants.TIPO_COMUNICAZIONE_CESSAZIONE:
			if (variazioneSomm != null) {
				if ("4.01".equals(variazioneSomm.getCodTipoVariazioneMin())) {
					// cessazione missione
					reportComunicazione = getReportComunicazioneCessazioneMissione(comunicazione);
				} else if ("4.02".equals(variazioneSomm.getCodTipoVariazioneMin())) {
					// cessazione del rapporto di lavoro
					reportComunicazione = getReportComunicazioneCessazioneRapporto(comunicazione);
				} else {
					// cessazione
					reportComunicazione = getReportComunicazioneCessazione(comunicazione);
				}
			} else
				reportComunicazione = getReportComunicazioneCessazione(comunicazione);
			break;
		case ComonlConstants.TIPO_COMUNICAZIONE_VARIAZIONE_DATORE:
			if (tipoTrasferimento != null && tipoTracciato != null && "VD".equals(tipoTracciato.getId())) {
				// vardatori trasferimento
				reportComunicazione = getReportComunicazioneVardatoriTrasferimento(comunicazione);
			} else {
				// vardatore anagrafico
				reportComunicazione = getReportComunicazioneVardatoriAnagrafico(comunicazione);
			}
			break;
		case ComonlConstants.TIPO_COMUNICAZIONE_URGENZA:
			reportComunicazione = getReportComunicazioneComunicazioneUrgenza(comunicazione);
			break;
		}

		if (reportComunicazione == null)
			throw new NotFoundException("ReportComunicazione non implementato!");

		reportComunicazione.setName("Stampa comunicazione");
		return ReportUtilities.createReportComunicazione(reportComunicazione);
	}

	// =================================================================================
	// ReportComunicazione

	private ReportComunicazione getReportComunicazioneComunicazioneUrgenza(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: URGENZA");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneDatiComunicazioneUrgenza(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneAssunzione(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: ASSUNZIONE");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione,true);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapporto(comunicazione,"Inizio rapporto");
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		

		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneAssunzioneSomm(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: ASSUNZIONE");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione, true);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapportoSomministrazione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneAssunzioneSommMiss(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: ASSUNZIONE");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAziendaUtilizzatrice(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione, true);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapportoSomministrazione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneMissione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneProrogaRapportoAssenzaMissione(Comunicazione comunicazione) {

		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: PROROGA");
		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapportoSomministrazione(comunicazione, true);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneProroga(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		return reportComunicazione;
		
	}

	private ReportComunicazione getReportComunicazioneProrogaRapportoMissione(Comunicazione comunicazione) {
		
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: PROROGA");
		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAziendaUtilizzatrice(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapportoSomministrazione(comunicazione, true);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneMissione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneProroga(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneProrogaMissioneRapportoIndeterminato(Comunicazione comunicazione) {
		
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: PROROGA");
		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAziendaUtilizzatrice(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapportoSomministrazione(comunicazione, true);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneMissione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneProroga(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneProroga(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: PROROGA");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapporto(comunicazione,"Rapporto attuale", false);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneProroga(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
	
		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneTrasformazioneCostanzaMissione(Comunicazione comunicazione) {
		
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: TRASFORMAZIONE");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAziendaUtilizzatrice(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapportoSomministrazione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneMissione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneDatiTrasformazione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
	
		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneTrasformazioneCostanzaAssenzaMissione(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: TRASFORMAZIONE");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapportoSomministrazione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneDatiTrasformazione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
	
		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneTrasformazione(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: TRASFORMAZIONE");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapporto(comunicazione, "Rapporto trasformato");
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneDatiTrasformazione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
	
		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneTrasferimentoAltraSede(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: TRASFERIMENTO");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAziendaUtilizzatrice(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapportoSomministrazione(comunicazione, true);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneMissione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneDatiTrasferimentoMissione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
	
		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneTrasferimentoSede(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: TRASFERIMENTO");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapportoSomministrazione(comunicazione, true);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneDatiTrasferimentoRapporto(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
	
		return reportComunicazione;
	}
	
	private ReportComunicazione getReportComunicazioneTrasferimento(Comunicazione comunicazione) {

		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: TRASFERIMENTO");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapporto(comunicazione,"Rapporto attuale", false);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);		
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
			
		sezione = getSezioneDatiTrasferimento(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
	
		return reportComunicazione;
	}
	
	private ReportComunicazione getReportComunicazioneDistacco(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: TRASFERIMENTO");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapporto(comunicazione,"Rapporto attuale", false);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);	
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
			
		sezione = getSezioneDatiTrasferimento(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
	
		return reportComunicazione;
	}
	
	private ReportComunicazione getReportComunicazioneCessazioneRapporto(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: CESSAZIONE");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapportoSomministrazione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneDatiCessazioneRapporto(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
	
		return reportComunicazione;

	}

	private ReportComunicazione getReportComunicazioneCessazione(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: CESSAZIONE");
		
		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapporto(comunicazione,"Rapporto attuale");
		if (sezione != null)
			reportComunicazione.addSezione(sezione);		
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneDatiCessazioneRapporto(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
	
		return reportComunicazione;
	}
	
	private ReportComunicazione getReportComunicazioneCessazioneMissione(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: CESSAZIONE");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);

		sezione = getSezioneAnagraficaImpresa(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAziendaUtilizzatrice(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneAnagraficaLavoratore(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneRapportoSomministrazione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		if (isContrattoApprendistatoTirocinio(comunicazione)) {
			sezione = getSezioneAnagraficaTutore(comunicazione);
			if (sezione != null)
				reportComunicazione.addSezione(sezione);	
		}
		
		sezione = getSezioneMissione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneDatiCessazioneMissione(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
	
		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneVardatoriTrasferimento(Comunicazione comunicazione) {
		
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: VARIAZIONE DATORE DI LAVORO");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		
		sezione = getSezioneAnagraficaImpresa(comunicazione, comunicazione.getDatoreAttuale(), "Azienda attuale");
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		
		sezione = getSezioneAnagraficaImpresa(comunicazione, comunicazione.getDatorePrecedente(), "Azienda precedente");
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		sezione = getSezioneElencoSediLavoratoreInteressato(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		
		return reportComunicazione;
	}

	private ReportComunicazione getReportComunicazioneVardatoriAnagrafico(Comunicazione comunicazione) {
		ReportComunicazione reportComunicazione = new ReportComunicazione("COMUNICAZIONE OBBLIGATORIA: VARIAZIONE DATORE DI LAVORO");

		Sezione sezione = getSezioneDatiGenerali(comunicazione);
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		
		sezione = getSezioneAnagraficaImpresa(comunicazione, comunicazione.getDatoreAttuale(), "Azienda attuale");
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		
		
		sezione = getSezioneVariazioneRagioneSocialeImpresa(comunicazione, comunicazione.getDatoreAttuale());
		if (sezione != null)
			reportComunicazione.addSezione(sezione);
		

		
		
		return reportComunicazione;
	}
	
	

	// =================================================================================
	// SEZIONI
	private Sezione getSezioneDatiGenerali(Comunicazione comunicazione) {
		
		String tipoComunicazione = comunicazione.getTipoComunicazione().getId();
		
		
		Sezione sezione = new Sezione("Dati Generali");
	
		SubSezione subSezione = new SubSezione("Dati di chi effettua la comunicazione:");	
		subSezione.addValore("E-mail notifica:", comunicazione.getEmail());
		subSezione.addValore("E-mail Soggetto Abilitato:", comunicazione.getEmailsoggetto());
		subSezione.addValore("Codice fiscale:", comunicazione.getCodiceFiscaleSoggetto());
		subSezione.addValore("Data timbro postale:",ReportUtilities.formatDate(comunicazione.getDtTimbroPostale()));
		
		String nomeCognome = "";
		if (comunicazione.getDelegato() != null) {
			if (comunicazione.getDelegato().getCognomeDelegatoImpresa() != null)
				nomeCognome = comunicazione.getDelegato().getCognomeDelegatoImpresa();
			if (comunicazione.getDelegato().getNomeDelegatoImpresa() != null)
				nomeCognome = nomeCognome + " " + comunicazione.getDelegato().getNomeDelegatoImpresa();
		}
		else if (comunicazione.getCodiceFiscaleSoggetto() != null) {
			SoggettoAbilitato soggettoAbilitato = anagraficaDelegatoDad.getSoggettoAbilitato(comunicazione.getCodiceFiscaleSoggetto());
			if (soggettoAbilitato != null && soggettoAbilitato.getCognomeDenominazione() != null) 
				nomeCognome = soggettoAbilitato.getCognomeDenominazione();
		}
		else if (comunicazione.getIdUserInsert() != null) {
			List<AnagraficaDelegato> listAnagraficaDelegato = anagraficaDelegatoDad.getAnagraficaDelegatoByCfDelegato(comunicazione.getIdUserInsert());
			if (listAnagraficaDelegato != null) {
				for (AnagraficaDelegato anagraficaDelegato : listAnagraficaDelegato) {
					if (anagraficaDelegato.getCognome() != null && anagraficaDelegato.getCognome().trim().length()>0) {
						nomeCognome = anagraficaDelegato.getCognome();
						if (anagraficaDelegato.getNome() != null && anagraficaDelegato.getNome().trim().length()>0) {
							nomeCognome = nomeCognome + " " + anagraficaDelegato.getNome();
						}
					}
				}
			}
		}
		
		if (nomeCognome != null && nomeCognome.trim().length()>0)
			subSezione.addValore("Cognome e Nome:", nomeCognome);
		
		sezione.addSubsezione(subSezione);
		
		
		if (ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE.equals(tipoComunicazione) ||
				ComonlConstants.TIPO_COMUNICAZIONE_PROROGA.equals(tipoComunicazione) ||
				ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE.equals(tipoComunicazione) ||
				ComonlConstants.TIPO_COMUNICAZIONE_CESSAZIONE.equals(tipoComunicazione) ||
				ComonlConstants.TIPO_COMUNICAZIONE_VARIAZIONE_DATORE.equals(tipoComunicazione) ||
				ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO.equals(tipoComunicazione)) {
			
			
			subSezione = new SubSezione("Inserimento d'ufficio:");
			String inserimentoUfficio = "NO";
			if (comunicazione.getTipoComunicazioneTu() != null && 
					ComonlConstants.TIPO_COMUNICAZIONE_TU_INSERIMENTO_UFFICIO.equals(comunicazione.getTipoComunicazioneTu().getId()))
				inserimentoUfficio = "SI";
			subSezione.addValore("Inserimento d'ufficio:", inserimentoUfficio);
			sezione.addSubsezione(subSezione);

			
			if (comunicazione.getDsCausaForzamaggiore() != null && comunicazione.getDsCausaForzamaggiore().trim().length()>0) {
				subSezione = new SubSezione("Causa di forza maggiore:");
				subSezione.addValore("Assunzione per causa forza maggiore:", comunicazione.getDsCausaForzamaggiore());
				sezione.addSubsezione(subSezione);	
			}
			
			subSezione = new SubSezione("Somministrazione:");
			String contrattosSomministrazione = "N/A";
			if (comunicazione.getTipoSomministrazione() != null) 
				contrattosSomministrazione = comunicazione.getTipoSomministrazione().getDsComTTipoSomministrazione();
			subSezione.addValore("Contratto di somministrazione:", contrattosSomministrazione);
			sezione.addSubsezione(subSezione);
			
			subSezione = new SubSezione("Lavoro domestico:");
			
			String domestico = "NO";
			if (comunicazione.getTipoTracciato() != null && 
					ComonlConstants.TIPO_TRACCIATO_DOMESTICO_ID.equals(comunicazione.getTipoTracciato().getId()))
				domestico = "SI";
			subSezione.addValore("Contratto di lavoro domestico:", domestico);
			sezione.addSubsezione(subSezione); 
			
			
			if (comunicazione.getTipoComunicazioneTu() != null  && comunicazione.getTipoComunicazioneTu().getCodTipoComunicazioneMin() != null) {
				String tipo = comunicazione.getTipoComunicazioneTu().getCodTipoComunicazioneMin();
				if (ComonlConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA.equals(tipo) || 
						ComonlConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_TELEMATICA.equals(tipo) ||
						ComonlConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_FAX.equals(tipo) ||
						ComonlConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_TURISTICO.equals(tipo)) {
					
					subSezione = new SubSezione("Comunicazione d'urgenza:");
					subSezione.addValore("Tipologia:", comunicazione.getTipoComunicazioneTu().getDsComTTipoComunicazione());
					subSezione.addValore("Codice regionale della comunicazione d'urgenza:", comunicazione.getCodiceComunRegPrec());
					sezione.addSubsezione(subSezione); 
				}
			}
			
			
			if (operatoreProvinciale != null && operatoreProvinciale) {
				subSezione = new SubSezione("Dati del soggetto per cui si effettua la comunicazione:");
				String comDatLav = "NO";
				if ("S".equalsIgnoreCase(comunicazione.getFlgComDatLav()))
					comDatLav = "SI";
				subSezione.addValore("Si effettua la comunicazione per il datore di lavoro:", comDatLav, "Data timbro postale:", ReportUtilities.formatDate(comunicazione.getDtTimbroPostale()));
				sezione.addSubsezione(subSezione); 
			}
		}
		
		String codTipoComunicazioneTu = "";
		codTipoComunicazioneTu = comunicazione.getTipoComunicazioneTu().getCodTipoComunicazioneMin(); 
		if (ComonlConstants.TIPO_COMUNICAZIONE_TU_ANNULLAMENTO_COD_MIN.equals(codTipoComunicazioneTu) ||
				ComonlConstants.TIPO_COMUNICAZIONE_TU_RETTIFICA_COD_MIN.equals(codTipoComunicazioneTu)) {
			
			String dsTipoComunicazioneTu = "";
			String codComuneRegPrec = "";
			dsTipoComunicazioneTu = comunicazione.getTipoComunicazioneTu().getDsComTTipoComunicazione();
			codComuneRegPrec = comunicazione.getCodiceComunRegPrec();
			subSezione = new SubSezione("Rettifica / Annullamento della comunicazione:");
			subSezione.addValore("Comunicazione rettifica/annullamento:", dsTipoComunicazioneTu, "Codice regionale della comunicazione da rettificare o da annullare:", codComuneRegPrec);
			
			String prot = "";
			if (comunicazione.getProvinciaPrec() != null)
				prot += comunicazione.getProvinciaPrec().getDsComTProvincia() + " / "; 
			prot += comunicazione.getAnnoProtComPrec() + " / " + comunicazione.getNumProtComPrec();
			subSezione.addValore("Protocollo della comunicazione:", prot);
			
			sezione.addSubsezione(subSezione);
		}
		
		subSezione = new SubSezione("Dati identificativi della comunicazione:");
		subSezione.addValore("Stato della comunicazione:", comunicazione.getStatoComunicazione() != null ? comunicazione.getStatoComunicazione().getDsComTStatoComunicazione() : "");
		
		
		if (ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE.equals(tipoComunicazione) ||
				ComonlConstants.TIPO_COMUNICAZIONE_PROROGA.equals(tipoComunicazione) ||
				ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE.equals(tipoComunicazione) ||
				ComonlConstants.TIPO_COMUNICAZIONE_CESSAZIONE.equals(tipoComunicazione) ||
				ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO.equals(tipoComunicazione) || 
				ComonlConstants.TIPO_COMUNICAZIONE_URGENZA.equals(tipoComunicazione) ||
				ComonlConstants.TIPO_COMUNICAZIONE_VARIAZIONE_DATORE.equals(tipoComunicazione)) {
			
			String prot = "";
			String provinciaProtocollazione = null;
			Datore datoreAttuale = comunicazione.getDatoreAttuale();
			if (datoreAttuale.getSedeOperativa() != null) {
				if (inPiemonte(datoreAttuale.getSedeOperativa())) {
					provinciaProtocollazione = datoreAttuale.getSedeOperativa().getComune().getProvincia().getDsTarga();
				}
			}
			else if (datoreAttuale.getSedeLegale() != null) {
				if (inPiemonte(datoreAttuale.getSedeLegale())) {
					provinciaProtocollazione = datoreAttuale.getSedeLegale().getComune().getProvincia().getDsTarga();
				}
			}
			if (provinciaProtocollazione != null)
				prot += provinciaProtocollazione + " / "; 
			prot += comunicazione.getAnnoProtCom() + " / " + comunicazione.getNumProtCom();
			subSezione.addValore("Protocollo della comunicazione:", prot);
			subSezione.addValore("Codice regionale della comunicazione:", comunicazione.getCodiceComunicazioneReg());
		}
		
		
		sezione.addSubsezione(subSezione);
		return sezione;
	}
	
	private Sezione getSezioneAnagraficaImpresa(Comunicazione comunicazione) {
		return getSezioneAnagraficaImpresa(comunicazione, comunicazione.getDatoreAttuale(), "Anagrafica Impresa");
	}
	
	
	private Sezione getSezioneAnagraficaImpresa(Comunicazione comunicazione, Datore datore, String titolo) {
		
		if (datore == null) return null;
		
		Sezione sezione = new Sezione(titolo);
		
		
		SubSezione subSezione = new SubSezione();
		
		if (comunicazione.getTipoTrasferimento() != null) {
			subSezione.addValore("Tipo variazione:", comunicazione.getTipoTrasferimento().getDsComTTipoTrasferimento(),"Data inizio:", ReportUtilities.formatDate(comunicazione.getDtTrasferimentoVarDatori()));
			subSezione.addValore("Data fine affitto ramo d'azienda:",  ReportUtilities.formatDate(comunicazione.getDtFineAffittoRamo()));
		} 
		
		subSezione.addValore("Codice fiscale:", datore.getCodiceFiscale(),"Partita Iva:", datore.getPartitaIva());
		
		if ("US".equals(comunicazione.getTipoTracciato().getId())) {
			String numAgenziaSomministrazione = "";
			String numIscrizioneAlbo = "";
			String dataIscrizioneAlbo = "";
			numAgenziaSomministrazione = datore.getNumAgenziaSommin();
			numIscrizioneAlbo = datore.getNumeroIscrizioneAlbo().substring(0, 10);
			dataIscrizioneAlbo = datore.getNumeroIscrizioneAlbo().substring(10, datore.getNumeroIscrizioneAlbo().length());
			
			if (numAgenziaSomministrazione != null) {
				subSezione.addValore("Numero Agenzia di Somministrazione:", numAgenziaSomministrazione);
			}
			if (numIscrizioneAlbo != null || dataIscrizioneAlbo != null) {
				subSezione.addValore("Numero iscrizione all'albo per le agenzie di somministrazione:", numIscrizioneAlbo, "Data iscrizione all'albo per le agenzie di somministrazione:", dataIscrizioneAlbo);
			}
		}
		
		String denominazione = datore.getDsDenominazioneDatore();
		if (denominazione == null) {
			denominazione = ""; 
			if (datore.getDsCognome() != null) denominazione = datore.getDsCognome() + " ";
			if (datore.getDsCognome() != null) denominazione += datore.getDsNome();
		}
		subSezione.addValore("Denominazione / Cognome e nome:", denominazione);
		
		String pubbAmm = "NO";
		if ("S".equalsIgnoreCase(datore.getFlgPubAmm()))
			pubbAmm = "SI";
		subSezione.addValore("Pubblica Amministrazione:", pubbAmm);
		
		
		String natura = "";
		if (datore.getNaturaGiuridica() != null)
			natura = datore.getNaturaGiuridica().getDescrizione();
		subSezione.addValore("Natura giuridica:", natura);
		
		String ateco = "";
		if (datore.getAtecofin() != null)
			ateco = datore.getAtecofin().getDsComTAtecofin();
		subSezione.addValore("Attivita':", ateco);
		String azArtigiana = "NO";
		if ("S".equalsIgnoreCase(datore.getFlgAzArtigiana()))
			azArtigiana = "SI";
		subSezione.addValore("Azienda artigiana:", azArtigiana);
		sezione.addSubsezione(subSezione);
		
		LegaleRappr legaleRappr = datore.getLegaleRappr();
		if (legaleRappr != null) {
			String legaleRapprCognome = legaleRappr.getDsCognome();
			String legaleRapprNome = legaleRappr.getDsNome();
			String legaleRapprSesso = legaleRappr.getSesso();
			String legaleRapprDataNadcita = ReportUtilities.formatDate(legaleRappr.getDtNascita());
			
			String legaleRapprComune = "";
			if (legaleRappr.getComune() != null)
				legaleRapprComune = legaleRappr.getComune().getDsComTComune();
			
			String legaleRapprCittadinanza = "";
			if (legaleRappr.getCittadinanza() != null)
				legaleRapprCittadinanza = legaleRappr.getCittadinanza().getDsComTCittadinanza();
			
			
			if (legaleRapprCognome != null || legaleRapprNome != null) {
				subSezione = new SubSezione("Legale Rappresentante:");
				subSezione.addValore("Cognome:",legaleRapprCognome);
				subSezione.addValore("Nome:", legaleRapprNome);
				subSezione.addValore("Sesso:", legaleRapprSesso);
				subSezione.addValore("Data di nascita:", legaleRapprDataNadcita);
				
				
				
				if (legaleRapprComune != null && legaleRapprComune.trim().length()>0) {
					subSezione.addValore("Comune di nascita:",legaleRapprComune);
				}
				else if (legaleRappr.getStatiEsteri() != null) {
					subSezione.addValore("Stato estero di nascita:",legaleRappr.getStatiEsteri().getDsStatiEsteri());
				}
				subSezione.addValore("Cittadinanza:", legaleRapprCittadinanza);
				
				
				if (legaleRappr.getMotivoPermesso() != null) {
					subSezione.addValore("DATI TITOLO DI SOGGIORNO:","");
					
					String soggiornanteItalia = "NO";
					if ("S".equalsIgnoreCase(legaleRappr.getFlgSoggiornanteItalia()))
						soggiornanteItalia = "SI";
					subSezione.addValore("Soggiornante in Italia:",soggiornanteItalia);
					
					String statusStraniero = "";
					if (legaleRappr.getStatusStraniero() != null)
						statusStraniero = legaleRappr.getStatusStraniero().getDsComTStatusStraniero();
					subSezione.addValore("Titolo di soggiorno:",statusStraniero);
					subSezione.addValore("Numero Titolo di soggiorno:",legaleRappr.getNumeroDocumento());
					subSezione.addValore("Motivo Titolo di soggiorno:",legaleRappr.getMotivoPermesso().getDsComTMotivoPermesso());
					subSezione.addValore("Data scadenza Titolo di soggiorno:",ReportUtilities.formatDate(legaleRappr.getDtScadenzaPermessoSogg()));
				}
				
				sezione.addSubsezione(subSezione);
			}
		}
		
		subSezione = new SubSezione("Sede Legale");
		SedeLavoro sedeLegale = datore.getSedeLegale();
		String sedeLegaleSede = "";
		String sedeLegaleTelefono = "";
		String sedeLegaleFax = "";
		String sedeLegaleMail = "";
		if (sedeLegale != null) {
			if (sedeLegale.getIndirizzo() != null) sedeLegaleSede += sedeLegale.getIndirizzo() + " - ";
			if (sedeLegale.getCodCap() != null) sedeLegaleSede += sedeLegale.getCodCap() + " ";
			if (sedeLegale.getComune() != null && sedeLegale.getComune().getDsComTComune() != null) sedeLegaleSede += sedeLegale.getComune().getDsComTComune();
			// if (sedeLegale.getComune() != null && sedeLegale.getComune().getProvincia() != null && sedeLegale.getComune().getProvincia().getCodProvinciaMin() != null) sedeLegaleSede += sedeLegale.getComune().getProvincia().getCodProvinciaMin();
			if (sedeLegale.getStatiEsteri() != null && sedeLegale.getStatiEsteri().getDsStatiEsteri() != null && 
					sedeLegale.getStatiEsteri().getDsStatiEsteri().trim().length()>0) {
				sedeLegaleSede += " - " + sedeLegale.getStatiEsteri().getDsStatiEsteri();
			}
			sedeLegaleTelefono = sedeLegale.getTelefono();
			sedeLegaleFax = sedeLegale.getFax();
			sedeLegaleMail = sedeLegale.getEmail();
		}
		subSezione.addValore("Sede legale:", sedeLegaleSede);
		subSezione.addValore("Telefono sede legale:", sedeLegaleTelefono, "Fax sede legale:", sedeLegaleFax);
		subSezione.addValore("E-mail sede legale:", sedeLegaleMail);
		sezione.addSubsezione(subSezione);
		
		SedeLavoro sedeOperativa = datore.getSedeOperativa();
		String sedeOperativaSede = "";
		String sedeOperativaTelefono = "";
		String sedeOperativaFax = "";
		String sedeOperativaMail = "";
		if (sedeOperativa != null ) {
			if (sedeOperativa.getIndirizzo() != null) sedeOperativaSede += sedeOperativa.getIndirizzo() + " - ";
			if (sedeOperativa.getCodCap() != null) sedeOperativaSede += sedeOperativa.getCodCap() + " ";
			if (sedeOperativa.getComune() != null && sedeOperativa.getComune().getDsComTComune() != null) sedeOperativaSede += sedeOperativa.getComune().getDsComTComune();
			// if (sedeOperativa.getComune() != null && sedeOperativa.getComune().getProvincia() != null && sedeOperativa.getComune().getProvincia().getCodProvinciaMin() != null) sedeOperativaSede += sedeOperativa.getComune().getProvincia().getCodProvinciaMin();
			
			sedeOperativaTelefono = sedeOperativa.getTelefono();
			sedeOperativaFax = sedeOperativa.getFax();
			sedeOperativaMail = sedeOperativa.getEmail();
			
			subSezione = new SubSezione("Sede operativa");
			subSezione.addValore("Sede operativa:", sedeOperativaSede);
			subSezione.addValore("Telefono sede operativa:", sedeOperativaTelefono, "Fax sede operativa:", sedeOperativaFax);
			subSezione.addValore("E-mail sede operativa:", sedeOperativaMail);
			sezione.addSubsezione(subSezione);
		}

		return sezione;
	}
	
	
	private Sezione getSezioneAnagraficaLavoratore(Comunicazione comunicazione) {
		return getSezioneAnagraficaLavoratore(comunicazione, false);
	}
	
	
	private Sezione getSezioneAnagraficaLavoratore(Comunicazione comunicazione, boolean visualizzaModelloQ) {
		
		
		if (comunicazione.getLavoratori() != null && comunicazione.getLavoratori().size()>0) {
			
			Sezione sezione = new Sezione("Anagrafica Lavoratore");
			
			for (Lavoratore lav : comunicazione.getLavoratori()) {
				
				SubSezione subSezione = new SubSezione();
				subSezione.addValore("Codice fiscale:", lav.getCodiceFiscale());
				
				String nome = "";
				if (lav.getCognome() != null) nome += lav.getCognome() + " ";
				if (lav.getNome() != null) nome += lav.getNome();
				subSezione.addValore("Cognome e nome:", nome);
				subSezione.addValore("Data di nascita:", ReportUtilities.formatDate(lav.getDtNascita()), "Sesso:", lav.getSesso());
				
				String comuneNascita = "";
				if (lav.getComuneNasc() != null)
					comuneNascita = lav.getComuneNasc().getDsComTComune();
				String statoEsteroNascita = "";
				if (lav.getStatiEsteriNasc() != null)
					statoEsteroNascita = lav.getStatiEsteriNasc().getDsStatiEsteri();
				subSezione.addValore("Stato estero di nascita:", statoEsteroNascita, "Comune di nascita:", comuneNascita);
				
				String cittadinanza  = "";
				if (lav.getCittadinanza() != null)
					cittadinanza = lav.getCittadinanza().getDsComTCittadinanza();
				subSezione.addValore("Cittadinanza:", cittadinanza);
				
				String tipoDocumento = "";
				if (lav.getStatusStraniero() != null)
					tipoDocumento = lav.getStatusStraniero().getDsComTStatusStraniero();
				subSezione.addValore("Tipo documento:", tipoDocumento, "Num. documento:", lav.getNumeroDocumento());

				String motivoPermesso = "";
				if (lav.getMotivoPermesso() != null) 
					motivoPermesso = lav.getMotivoPermesso().getDsComTMotivoPermesso();
				subSezione.addValore("Motivo permesso di soggiorno:", motivoPermesso, "Scadenza del permesso:", ReportUtilities.formatDate(lav.getDtScadenzaPermessoSogg()));
				
				
				if (visualizzaModelloQ) {
					String sussistenza = "N/A";
					if ("S".equalsIgnoreCase(lav.getFlgSistAlloggiativa())) sussistenza = "SI";
					else if ("N".equalsIgnoreCase(lav.getFlgSistAlloggiativa())) sussistenza = "NO";
					subSezione.addValore("Sussistenza della sistemazione alloggiativa:", sussistenza);
					
					String impegnoRimpatrio = "N/A";
					if ("S".equalsIgnoreCase(lav.getFlgRimborsoRimpatrio())) impegnoRimpatrio = "SI";
					else if ("N".equalsIgnoreCase(lav.getFlgRimborsoRimpatrio())) impegnoRimpatrio = "NO";
					subSezione.addValore("Impegno del datore di lavoro al pagamento delle spese per il rimpatrio:", impegnoRimpatrio);
				}
				
				
				
				String residenza = "";
				if (lav.getStatiEsteriRes() != null && lav.getStatiEsteriRes().getDsStatiEsteri() != null)
					residenza = lav.getStatiEsteriRes().getDsStatiEsteri();
				else {
					if (lav.getDsIndirizzoRes() != null) residenza += lav.getDsIndirizzoRes() + " - ";
					if (lav.getCodCapRes() != null ) residenza += lav.getCodCapRes() + " ";
					if (lav.getComuneRes() != null && lav.getComuneRes().getDsComTComune() != null) residenza += lav.getComuneRes().getDsComTComune();
					// if (lav.getComuneRes() != null && lav.getComuneRes().getProvincia() != null && lav.getComuneRes().getProvincia().getCodProvinciaMin() != null) residenza += lav.getComuneRes().getProvincia().getCodProvinciaMin();
				}
				subSezione.addValore("Residenza / stato estero:", residenza);
				
				String domicilio = "";
				if (lav.getStatiEsteriDom() != null && lav.getStatiEsteriDom().getDsStatiEsteri() != null)
					domicilio = lav.getStatiEsteriDom().getDsStatiEsteri();
				else {
					if (lav.getDsIndirizzoDom() != null) domicilio += lav.getDsIndirizzoDom() + " - ";
					if (lav.getCodCapDom() != null) domicilio += lav.getCodCapDom() + " ";
					if (lav.getComuneDom() != null && lav.getComuneDom().getDsComTComune() != null) domicilio += lav.getComuneDom().getDsComTComune();
					// if (lav.getComuneDom() != null && lav.getComuneDom().getProvincia() != null && lav.getComuneDom().getProvincia().getCodProvinciaMin() != null) domicilio += lav.getComuneDom().getProvincia().getCodProvinciaMin();
				}
				subSezione.addValore("Domicilio:", domicilio);
				
				String livelloStudio = "";
				if (lav.getLivelloStudio() != null)
					livelloStudio = lav.getLivelloStudio().getTitoloDiStudio();
				subSezione.addValore("Livello di istruzione",livelloStudio);

				sezione.addSubsezione(subSezione);
			}
			
			return sezione;
		}
		return null;
	}
	
	private Sezione getSezioneRapporto(Comunicazione comunicazione, String titoloSezione) {
		return getSezioneRapporto(comunicazione, titoloSezione, true);
	}
	
	private Sezione getSezioneRapporto(Comunicazione comunicazione, String titoloSezione, boolean visualizzaDataFineRapportoEPeriodoFormativo) {
		
		if (comunicazione.getRapporto() != null) {
			
			Rapporto rapporto = comunicazione.getRapporto();
			
			Sezione sezione = new Sezione(titoloSezione);
			
			
			SubSezione subSezione = new SubSezione();
			
			if (visualizzaDataFineRapportoEPeriodoFormativo)
				subSezione.addValore("Data inizio rapporto:", ReportUtilities.formatDate(rapporto.getDtInizioRapporto()), "Data fine rapporto:", ReportUtilities.formatDate(rapporto.getDtFineRapporto()));
			else subSezione.addValore("Data inizio rapporto:", ReportUtilities.formatDate(rapporto.getDtInizioRapporto()));
			
			String tipoRapporto = "";
			if (rapporto.getTipoContratti() != null)
				tipoRapporto = rapporto.getTipoContratti().getDsComTTipoContratto();
			subSezione.addValore("Tipo rapporto:", tipoRapporto);
			
			
			if (visualizzaDataFineRapportoEPeriodoFormativo)
				subSezione.addValore("Data fine periodo formativo:", ReportUtilities.formatDate(rapporto.getDtFinePeriodoFormativo()));
			
			String lavoroStagionale = "NO";
			if ("S".equalsIgnoreCase(rapporto.getFlgLavStagionale()))
				lavoroStagionale = "SI";
			subSezione.addValore("Lavoro stagionale:",lavoroStagionale);
			String tipologiaTirocinio = "";
			if (rapporto.getTipologiaTirocinio() != null)
				tipologiaTirocinio = rapporto.getTipologiaTirocinio().getDescrTipologiaTirocinio();
			subSezione.addValore("Tipologia tirocinio:", tipologiaTirocinio);
			String categTirocinante = "";
			if (rapporto.getCategTirocinante() != null)
				categTirocinante = rapporto.getCategTirocinante().getDescrCategTirocinante();
			subSezione.addValore("Categoria tirocinante:", categTirocinante);
			String tipoEntePromTirocinio = "";
			if (rapporto.getTipoEntePromTirocinio() != null)
				tipoEntePromTirocinio = rapporto.getTipoEntePromTirocinio().getDsComTTipoEntePromTir();
			subSezione.addValore("Tipo ente promotore tirocinio:", tipoEntePromTirocinio);
			String cpi = "";
			if (rapporto.getCpi() != null)
				cpi = rapporto.getCpi().getDsComTCpi();
			subSezione.addValore("Cpi come ente promotore tirocinio:", cpi);
			subSezione.addValore("Codice Fiscale ente promotore tirocinio:", rapporto.getCfSoggPromotoreTirocinio());
			subSezione.addValore("Soggetto ente promotore tirocinio:", rapporto.getDsSoggPromTirocinio());
			String contratto = "";
			if (rapporto.getCcnl() != null)
				contratto = rapporto.getCcnl().getDsCcnl();
			subSezione.addValore("Contratto collettivo applicato:", contratto);
			String livelloRetribuzione = "";
			if (rapporto.getLivelloRetribuzione() != null)
				livelloRetribuzione = rapporto.getLivelloRetribuzione().getDesLivello();
			subSezione.addValore("Livello di inquadramento:", livelloRetribuzione);
			subSezione.addValore("Retribuzione lorda annuale:", ReportUtilities.formatImporto(rapporto.getRetribuzioneCompenso()));
			String qualificaIstat = "";
			if (rapporto.getIstatLivello5() != null)
				qualificaIstat = rapporto.getIstatLivello5().getDsComIstat2001livello5();
			subSezione.addValore("Qualifica professionale (ISTAT):", qualificaIstat);
			String assunzioneObbligatoria = "NO";
			if ("S".equalsIgnoreCase(rapporto.getFlgAssunzioneObbligatoria()))
				assunzioneObbligatoria = "SI";
			subSezione.addValore("Assunzione obbligatoria:", assunzioneObbligatoria);
			String catAssObb = "";
			if (rapporto.getCategLavAssObbl() != null)
				catAssObb = rapporto.getCategLavAssObbl().getDsComTCategLavAssObbl();
			subSezione.addValore("Categoria lavoratore assunzione obbligatoria:", catAssObb);
			subSezione.addValore("Pat Inail:", rapporto.getPatInail());
			String ente = "";
			String codice = "";
			if (rapporto.getEntePrevidenziale() != null) {
				ente = rapporto.getEntePrevidenziale().getDsComTEntePrevidenziale();
				codice = rapporto.getCodiceEntePrev();
			}
			subSezione.addValore("Ente previdenziale:", ente, "Codice ente previdenziale:", codice);
			String tipoOrario = "";
			if (rapporto.getTipoOrario() != null)
				tipoOrario = rapporto.getTipoOrario().getDsComTTipoOrario();
			subSezione.addValore("Tipo orario:", tipoOrario, "Ore settimanali medie:", ReportUtilities.formatNumber(rapporto.getNumOreSettMed()));
			String tipoAtto = "";
			if (rapporto.getTipoAttoL68() != null)
				tipoAtto = rapporto.getTipoAttoL68().getDsComTTipoAttoL68();
			subSezione.addValore("Legge 68 tipo atto:", tipoAtto);
			subSezione.addValore("Legge 68 data nulla osta:", ReportUtilities.formatDate(rapporto.getDtLegge68()), "Legge 68 num. atto:", rapporto.getNumeroAttoLegge68());
			String socio = "NO";
			if ("S".equalsIgnoreCase(rapporto.getFlgSocioLavoratore()))
				socio = "SI";
			subSezione.addValore("Socio lavoratore:", socio);
			String lavoroAgricoltura = "NO";
			if ("S".equalsIgnoreCase(rapporto.getFlgLavoroAgricoltura()))
				lavoroAgricoltura = "SI";
			subSezione.addValore("Lavoro in agricoltura:", lavoroAgricoltura);
			subSezione.addValore("Giornante lavorative previste:", ReportUtilities.formatNumber(rapporto.getGiornateLavPreviste()), "Tipo lavorazione:", rapporto.getTipoLavorazione());
			String mobilita = "NO";
			if ("S".equalsIgnoreCase(rapporto.getFlgLavInMobilita()))
				mobilita = "SI";
			subSezione.addValore("Lavoratore in mobilita':", mobilita);

			sezione.addSubsezione(subSezione);
			
			return sezione;
		}
			
		return null;
	}

	private Sezione getSezioneDatiComunicazioneUrgenza(Comunicazione comunicazione) {
		Sezione sezione = new Sezione("Dati comunicazione d'urgenza");
		
		
		Datore datore = comunicazione.getDatoreAttuale();
		
		SubSezione subSezione = new SubSezione("Datore di lavoro:");
		subSezione.addValore("Codice fiscale:", datore.getCodiceFiscale());
		subSezione.addValore("Denominazione / cognome e nome:", datore != null ? datore.getDsDenominazioneDatore() : "");
		String sedeOperativa = "";
		if (datore != null && datore.getSedeOperativa() != null && datore.getSedeOperativa().getComune() != null)
			sedeOperativa = datore.getSedeOperativa().getComune().getDsComTComune();
		subSezione.addValore("Comune sede operativa:", sedeOperativa);
		sezione.addSubsezione(subSezione);

		
		Lavoratore lavoratore = null;
		if (comunicazione.getLavoratori() != null && comunicazione.getLavoratori().size()>0)
			lavoratore = comunicazione.getLavoratori().get(0);
		subSezione = new SubSezione("Lavoratore:");
		subSezione.addValore("Codice fiscale:", lavoratore.getCodiceFiscale());
		subSezione.addValore("Cognome:", lavoratore.getCognome());
		subSezione.addValore("Nome:", lavoratore.getNome());
		sezione.addSubsezione(subSezione);
		
		Comunicazione comunicazioneUrgenza = null;
		if (comunicazione.getRapporto() != null && comunicazione.getRapporto().getComunicazione() != null) 
			comunicazioneUrgenza = comunicazione.getRapporto().getComunicazione();
		subSezione = new SubSezione("Dati del rapporto:");
		subSezione.addValore("Data inizio rapporto:", ReportUtilities.formatDate(comunicazione.getRapporto().getDtInizioRapporto()));
		subSezione.addValore("Motivo dell'urgenza:", comunicazioneUrgenza.getDsMotivoUrgenza());
		sezione.addSubsezione(subSezione);

		return sezione;
	}
	
	
	private Sezione getSezioneAnagraficaTutore(Comunicazione comunicazione) {
		
		
		if (comunicazione.getRapporto() != null && comunicazione.getRapporto().getTutore() != null) {
			Tutore tutore = comunicazione.getRapporto().getTutore();
			
			Sezione sezione = new Sezione("Anagrafica Tutore");
			SubSezione subSezione = new SubSezione();
			
			String nome = "";
			if (tutore.getCognome() != null) nome += tutore.getCognome() + " ";
			if (tutore.getNome() != null) nome += tutore.getNome() + " ";
			subSezione.addValore("Cognome e nome:", nome);
			subSezione.addValore("Data di nascita:", ReportUtilities.formatDate(tutore.getDtNascita()), "Sesso:", tutore.getSesso());
			subSezione.addValore("Codice fiscale:", tutore.getCfTutore());
			String titolare = "NO";
			if ("S".equalsIgnoreCase(tutore.getFlgTitolare()))
				titolare = "SI";
			subSezione.addValore("Titolare:", titolare, "Anni di esperienza:", ReportUtilities.formatNumber(tutore.getNumAnniEsperienza()));
			String qualifica = "";
			if (tutore.getIstat() != null)
				qualifica = tutore.getIstat().getDsComIstat2001livello5();
			subSezione.addValore("Qualifica professionale:", qualifica);
			String gradoContrattuale = "";
			if (tutore.getGradoContrattuale() != null)
				gradoContrattuale = tutore.getGradoContrattuale().getDsComTGradoContrattuale();
			subSezione.addValore("Livello di inquadramento:", tutore.getDsLivelloInquadramento(), "Grado di qualificazione:", gradoContrattuale);
			subSezione.addValore("Data visita medica:", ReportUtilities.formatDate(tutore.getDtVisitaMedica()));
			
			
			sezione.addSubsezione(subSezione);
			return sezione;
		}
		return null;
	}
	
	
	private Sezione getSezioneRapportoSomministrazione(Comunicazione comunicazione) {
		return getSezioneRapportoSomministrazione(comunicazione, true);
	}
		
	private Sezione getSezioneRapportoSomministrazione(Comunicazione comunicazione, boolean visualizzaDataFineRapportoEFormativo) {
		
		if (comunicazione.getRapporto() != null) {
			
			Rapporto rapporto = comunicazione.getRapporto();
			
			Sezione sezione = new Sezione("Dati del rapporto con agenzia di somministrazione");
			SubSezione subSezione = new SubSezione();
			
			if (visualizzaDataFineRapportoEFormativo)
				subSezione.addValore("Data inizio rapporto:", ReportUtilities.formatDate(rapporto.getDtInizioRapporto()), "Data fine rapporto:",ReportUtilities.formatDate(rapporto.getDtFineRapporto()));
			else 
				subSezione.addValore("Data inizio rapporto:", ReportUtilities.formatDate(rapporto.getDtInizioRapporto()));
			
			String tipoRapporto = "";
			if (rapporto.getTipoContratti() != null)
				tipoRapporto = rapporto.getTipoContratti().getDsComTTipoContratto();
			subSezione.addValore("Tipo rapporto:", tipoRapporto);
			
			if (visualizzaDataFineRapportoEFormativo)
				subSezione.addValore("Data fine periodo formativo:", ReportUtilities.formatDate(rapporto.getDtFinePeriodoFormativo()));
			
			subSezione.addValore("Num. matricola lavoratore:", rapporto.getNumMatricolaLav());
			String ente = "";
			String codice = "";
			if (rapporto.getEntePrevidenziale() != null) {
				ente = rapporto.getEntePrevidenziale().getDsComTEntePrevidenziale();
				codice = rapporto.getCodiceEntePrev();
			}
			subSezione.addValore("Ente previdenziale:", ente, "Codice ente previdenziale:", codice);
			subSezione.addValore("Indennita' di disponibilita':", ReportUtilities.formatNumber(rapporto.getNumIndennitaSomm()));
			
			String mobilita = "NO";
			if ("S".equalsIgnoreCase(rapporto.getFlgLavInMobilita()))
				mobilita = "SI";
			subSezione.addValore("Lavoratore in mobilita':", mobilita);

			sezione.addSubsezione(subSezione);
			return sezione;
		}
		return null;
	}
	
	private Sezione getSezioneAziendaUtilizzatrice(Comunicazione comunicazione) {
		
		if (comunicazione.getMissione() != null && comunicazione.getMissione().getAziUtilizzatrice() != null) {
			
			Datore azi = comunicazione.getMissione().getAziUtilizzatrice();
			Sezione sezione = new Sezione("Anagrafica Impresa Utilizzatrice");
			SubSezione subSezione = new SubSezione();
			
			subSezione.addValore("Codice fiscale:", azi.getCodiceFiscale(),"Partita Iva:", azi.getPartitaIva());
			String denominazione = azi.getDsDenominazioneDatore();
			if (denominazione == null) {
				denominazione = ""; 
				if (azi.getDsCognome() != null) denominazione = azi.getDsCognome() + " ";
				if (azi.getDsCognome() != null) denominazione += azi.getDsNome();
			}
			subSezione.addValore("Denominazione / Cognome e nome:", denominazione);
			String ateco = "";
			if (azi.getAtecofin() != null)
				ateco = azi.getAtecofin().getDsComTAtecofin();
			subSezione.addValore("Attivita':", ateco);
			String pubbAmm = "NO";
			if ("S".equalsIgnoreCase(azi.getFlgPubAmm()))
				pubbAmm = "SI";
			subSezione.addValore("Pubblica Amministrazione:", pubbAmm);
			subSezione.addValore("Num contratto somministrazione:",ReportUtilities.formatNumber(azi.getNumContrSomm()));
			subSezione.addValore("Data inizio contratto somministrazione:", ReportUtilities.formatDate(azi.getDtInizioContrattoSom()));
			subSezione.addValore("Data fine contratto somministrazione:", ReportUtilities.formatDate(azi.getDtFineContrattoSom()));
						
			String estera = "NO";
			if ("S".equalsIgnoreCase(azi.getFlgUtilEstera()))
				estera = "SI";
			subSezione.addValore("Sede legale all'estero:", estera);
			
			SedeLavoro sedeLegale = azi.getSedeLegale();
			String sedeLegaleSede = "";
			String sedeLegaleTelefono = "";
			String sedeLegaleFax = "";
			String sedeLegaleMail = "";
			if (sedeLegale != null) {
				if (sedeLegale.getIndirizzo() != null) sedeLegaleSede += sedeLegale.getIndirizzo() + " - ";
				if (sedeLegale.getCodCap() != null) sedeLegaleSede += sedeLegale.getCodCap() + " ";
				if (sedeLegale.getComune() != null && sedeLegale.getComune().getDsComTComune() != null) sedeLegaleSede += sedeLegale.getComune().getDsComTComune();
				// if (sedeLegale.getComune() != null && sedeLegale.getComune().getProvincia() != null && sedeLegale.getComune().getProvincia().getCodProvinciaMin() != null) sedeLegaleSede += sedeLegale.getComune().getProvincia().getCodProvinciaMin();
				
				if (sedeLegale.getStatiEsteri() != null && sedeLegale.getStatiEsteri().getDsStatiEsteri() != null && 
						sedeLegale.getStatiEsteri().getDsStatiEsteri().trim().length()>0) {
					sedeLegaleSede += " - " + sedeLegale.getStatiEsteri().getDsStatiEsteri();
				}
				
				sedeLegaleTelefono = sedeLegale.getTelefono();
				sedeLegaleFax = sedeLegale.getFax();
				sedeLegaleMail = sedeLegale.getEmail();
			}
			subSezione.addValore("Sede legale:", sedeLegaleSede);
			subSezione.addValore("Telefono sede legale:", sedeLegaleTelefono, "Fax sede legale:", sedeLegaleFax);
			subSezione.addValore("E-mail sede legale:", sedeLegaleMail);

			
			SedeLavoro sedeOperativa = azi.getSedeOperativa();
			String sedeOperativaSede = "";
			String sedeOperativaTelefono = "";
			String sedeOperativaFax = "";
			String sedeOperativaMail = "";
			if (sedeOperativa != null ) {
				if (sedeOperativa.getIndirizzo() != null) sedeOperativaSede += sedeOperativa.getIndirizzo() + " - ";
				if (sedeOperativa.getCodCap() != null) sedeOperativaSede += sedeOperativa.getCodCap() + " ";
				if (sedeOperativa.getComune() != null && sedeOperativa.getComune().getDsComTComune() != null) sedeOperativaSede += sedeOperativa.getComune().getDsComTComune();
				// if (sedeOperativa.getComune() != null && sedeOperativa.getComune().getProvincia() != null && sedeOperativa.getComune().getProvincia().getCodProvinciaMin() != null) sedeOperativaSede += sedeOperativa.getComune().getProvincia().getCodProvinciaMin();
				
				sedeOperativaTelefono = sedeOperativa.getTelefono();
				sedeOperativaFax = sedeOperativa.getFax();
				sedeOperativaMail = sedeOperativa.getEmail();
			}
			subSezione.addValore("Sede operativa:", sedeOperativaSede);
			subSezione.addValore("Telefono sede operativa:", sedeOperativaTelefono, "Fax sede operativa:", sedeOperativaFax);
			subSezione.addValore("E-mail sede operativa:", sedeOperativaMail);
			
		
			sezione.addSubsezione(subSezione);
			return sezione;
		}
		return null;
	}

	private Sezione getSezioneMissione(Comunicazione comunicazione) {
		if (comunicazione.getMissione() != null) {
			
			Rapporto missione = comunicazione.getMissione();
			Sezione sezione = new Sezione("Dati della missione");
			SubSezione subSezione = new SubSezione();
			
			subSezione.addValore("Data inizio missione:", ReportUtilities.formatDate(missione.getDtInizioMissione()),"Data di fine missione:", ReportUtilities.formatDate(missione.getDtFineMissione()));
			String contratto = "";
			if (missione.getCcnl() != null)
				contratto = missione.getCcnl().getDsCcnl();
			subSezione.addValore("Contratto collettivo applicato:", contratto);
			String livelloRetribuzione = "";
			if (missione.getLivelloRetribuzione() != null)
				livelloRetribuzione = missione.getLivelloRetribuzione().getDesLivello();
			subSezione.addValore("Livello di inquadramento:", livelloRetribuzione);
			subSezione.addValore("Retribuzione:", ReportUtilities.formatImporto(missione.getRetribuzioneCompenso()),"Pat Inail:", missione.getPatInail());
			String qualificaIstat = "";
			if (missione.getIstatLivello5() != null)
				qualificaIstat = missione.getIstatLivello5().getDsComIstat2001livello5();
			subSezione.addValore("Qualifica professionale (ISTAT):", qualificaIstat);
			String assunzioneObbligatoria = "NO";
			if ("S".equalsIgnoreCase(missione.getFlgAssunzioneObbligatoria()))
				assunzioneObbligatoria = "SI";
			subSezione.addValore("Assunzione obbligatoria:", assunzioneObbligatoria);
			String cat = "";
			if (missione.getCategLavAssObbl() != null)
				cat = missione.getCategLavAssObbl().getDsComTCategLavAssObbl();
			subSezione.addValore("Categoria lavoratore assunzione obbligatoria:", cat);
			String tipoOrario = "";
			if (missione.getTipoOrario() != null)
				tipoOrario = missione.getTipoOrario().getDsComTTipoOrario();
			subSezione.addValore("Tipo orario:", tipoOrario, "Ore settimanali medie:", ReportUtilities.formatNumber(missione.getNumOreSettMed()));
			String lavoroAgricoltura = "NO";
			if ("S".equalsIgnoreCase(missione.getFlgLavoroAgricoltura()))
				lavoroAgricoltura = "SI";
			subSezione.addValore("Lavoro in agricoltura:", lavoroAgricoltura);
			subSezione.addValore("Giornate lavorative previste:", ReportUtilities.formatNumber(missione.getGiornateLavPreviste()));
			subSezione.addValore("Descrizione attivita' agricoltura:", missione.getDsAttivitaAgricoltura());
			String rischio = "NO";
			if ("S".equalsIgnoreCase(missione.getFlgRischioSilicAsbe()))
				rischio = "SI";
			subSezione.addValore("Rischio silicosi e asbestosi:", rischio);
			subSezione.addValore("Voce tariffa (1):", missione.getDsVoceTariffa1(),"Voce tariffa (2):", missione.getDsVoceTariffa2());
			subSezione.addValore("Voce tariffa (3):", missione.getDsVoceTariffa3());
			String descAttivita = "";
			if (missione.getIstatLivello5() != null)
				descAttivita = missione.getIstatLivello5().getDsComIstat2001livello5();
			subSezione.addValore("Descrizione attivita':", descAttivita);
		
			sezione.addSubsezione(subSezione);
			return sezione;
		}
		return null;
	}
	
	private Sezione getSezioneProroga(Comunicazione comunicazione) {
		Sezione sezione = new Sezione("Dati Proroga");
		SubSezione subSezione = new SubSezione();
		String dataProroga = "";
		
		if (comunicazione.getMissione() != null && comunicazione.getMissione().getDtFineProroga() != null) {
			dataProroga = ReportUtilities.formatDate(comunicazione.getMissione().getDtFineProroga());
		}else if (comunicazione.getRapporto() != null && comunicazione.getRapporto().getDtFineProroga() != null) {
			dataProroga = ReportUtilities.formatDate(comunicazione.getRapporto().getDtFineProroga());
		}
		subSezione.addValore("Data di proroga:", dataProroga);
		sezione.addSubsezione(subSezione);
		return sezione;
	}
	
	
	private Sezione getSezioneDatiTrasformazione(Comunicazione comunicazione) {
		Sezione sezione = new Sezione("Dati Trasformazione");
		SubSezione subSezione = new SubSezione();
		
		String dataTrasformazione = "";
		String tipoTrasformazione = "";
		if (comunicazione.getRapporto() != null) {
			dataTrasformazione = ReportUtilities.formatDate(comunicazione.getRapporto().getDtTrasformazione());

			if (comunicazione.getRapporto().getTrasformazionerl() != null)
				tipoTrasformazione = comunicazione.getRapporto().getTrasformazionerl().getDsComTTrasformazionerl();

		}
		subSezione.addValore("Data di trasformazione:", dataTrasformazione);
		subSezione.addValore("Tipo di trasformazione:", tipoTrasformazione);
		
		
		sezione.addSubsezione(subSezione);
		return sezione;
	}
	

	private Sezione getSezioneDatiTrasferimento(Comunicazione comunicazione) {
		Sezione sezione = new Sezione("Dati del trasferimento");
		SubSezione subSezione = new SubSezione();
		
		
		if (comunicazione.getRapporto() != null) {
			
			Rapporto rapporto = comunicazione.getRapporto();
			
			String tipoSpostamento  = "";
			if (rapporto.getTrasformazionerl() != null && rapporto.getTrasformazionerl().getDsComTTrasformazionerl() != null)
				tipoSpostamento = rapporto.getTrasformazionerl().getDsComTTrasformazionerl();
			subSezione.addValore("Data trasferimento / distacco:", ReportUtilities.formatDate(rapporto.getDtTrasformazione()), "Tipo spostamento:", tipoSpostamento);
			subSezione.addValore("Data fine distacco:", ReportUtilities.formatDate(rapporto.getDtFineRapporto()));
			
			
			String distaccoParziale = "NO";
			if ("S".equals(rapporto.getFlgDistaccoParziale()))
				distaccoParziale = "SI";
			subSezione.addValore("Distacco parziale:", distaccoParziale);
			
			String distaccoEstera = "NO";
			if ("S".equals(rapporto.getFlgDistaccoSuAziEstera()))
				distaccoEstera = "SI";
			subSezione.addValore("Distacco su azienda estera:", distaccoEstera);
			
			
			if (rapporto.getDatoreDistaccatario() != null) {

				Datore distacco = rapporto.getDatoreDistaccatario();
				
				subSezione.addValore("Codice fiscale datore distaccatario:", distacco.getCodiceFiscale());
				subSezione.addValore("Denominazione datore distaccatario:", distacco.getDsDenominazioneDatore());
				
				String ateco = "";
				if (distacco.getAtecofin() != null && distacco.getAtecofin().getDsComTAtecofin() != null)
					ateco =  distacco.getAtecofin().getDsComTAtecofin().toUpperCase();
				subSezione.addValore("Ateco:", ateco);
				subSezione.addValore("Pat Inail:", distacco.getPatInail());
				
				SedeLavoro sede = distacco.getSedeLegale();
				String sedeComune = "";
				String sedeCap = "";
				String sedeIndirizzo = "";
				String sedeTelefono = "";
				String sedFax = "";
				String sedeMail = "";
				if (sede != null ) {
					if (sede.getIndirizzo() != null) sedeIndirizzo += sede.getIndirizzo() + " - ";
					if (sede.getCodCap() != null) sedeIndirizzo += sede.getCodCap() + " ";
					if (sede.getComune() != null && sede.getComune().getDsComTComune() != null) sedeIndirizzo += sede.getComune().getDsComTComune();
					// if (sede.getComune() != null && sede.getComune().getProvincia() != null && sede.getComune().getProvincia().getCodProvinciaMin() != null) sedeIndirizzo += sede.getComune().getProvincia().getCodProvinciaMin();
					if (sede.getStatiEsteri() != null && sede.getStatiEsteri().getDsStatiEsteri() != null && 
							sede.getStatiEsteri().getDsStatiEsteri().trim().length()>0) {
						sedeIndirizzo += " - " + sede.getStatiEsteri().getDsStatiEsteri();
					}
					sedeTelefono = sede.getTelefono();
					sedFax = sede.getFax();
					sedeMail = sede.getEmail();
					if (sede.getComune() != null)
						sedeComune = sede.getComune().getDsComTComune();
					sedeCap = sede.getCodCap();
				}
				subSezione.addValore("Indirizzo del datore di distacco:", sedeIndirizzo);
				subSezione.addValore("Telefono:", sedeTelefono, "Fax:", sedFax);
				subSezione.addValore("Comune della sede:", sedeComune, "CAP:", sedeCap);
				subSezione.addValore("E-mail:", sedeMail);	
			}
			else {
				
				String sedePrecedente = "";
				if (rapporto.getSedeLavoroPrecedente() != null) {
					if (rapporto.getSedeLavoroPrecedente().getIndirizzo() != null)
						sedePrecedente += rapporto.getSedeLavoroPrecedente().getIndirizzo() + " ";
					if (rapporto.getSedeLavoroPrecedente().getComune() != null && rapporto.getSedeLavoroPrecedente().getComune().getDsComTComune() != null)
						sedePrecedente += rapporto.getSedeLavoroPrecedente().getComune().getDsComTComune();
					if (rapporto.getSedeLavoroPrecedente().getStatiEsteri() != null && rapporto.getSedeLavoroPrecedente().getStatiEsteri().getDsStatiEsteri() != null)
						sedePrecedente += rapporto.getSedeLavoroPrecedente().getStatiEsteri().getDsStatiEsteri();
				}
				subSezione.addValore("Indirizzo della sede di lavoro precedente:", sedePrecedente);
			}
			
			
			
			sezione.addSubsezione(subSezione);
			return sezione;
			
		}
		return null;
	}

	
	private Sezione getSezioneDatiTrasferimentoRapporto(Comunicazione comunicazione) {
		Sezione sezione = new Sezione("Dati del trasferimento");
		SubSezione subSezione = new SubSezione();
		
		
		if (comunicazione.getRapporto() != null) {
			
			Trasformazionerl tras = comunicazione.getRapporto().getTrasformazionerl();		
			subSezione.addValore("Data trasferimento / distacco:", ReportUtilities.formatDate(comunicazione.getRapporto().getDtTrasformazione()), "Tipo spostamento:", tras != null ? tras.getDsComTTrasformazionerl() :"");
			sezione.addSubsezione(subSezione);
			return sezione;
		}
		
		return null;
	}
	
	private Sezione getSezioneDatiTrasferimentoMissione(Comunicazione comunicazione) {
		Sezione sezione = new Sezione("Dati del trasferimento");
		SubSezione subSezione = new SubSezione();
		
		if (comunicazione.getMissione() != null) {
			
			Trasformazionerl tras = comunicazione.getMissione().getTrasformazionerl();		
			subSezione.addValore("Data trasferimento / distacco:", ReportUtilities.formatDate(comunicazione.getMissione().getDtTrasformazione()), "Tipo spostamento:", tras != null ? tras.getDsComTTrasformazionerl() :"");
			sezione.addSubsezione(subSezione);
			return sezione;
		}
		return null;
	}
	
	
	private Sezione getSezioneDatiCessazioneRapporto(Comunicazione comunicazione) {
		Sezione sezione = new Sezione("Dati Cessazione");
		SubSezione subSezione = new SubSezione();
		if (comunicazione.getRapporto() != null) {
			Cessazionerl cess = comunicazione.getRapporto().getCessazionerl();
			if (cess != null) {
				subSezione.addValore("Data di cessazione:", ReportUtilities.formatDate(comunicazione.getRapporto().getDtCessazione()));
				subSezione.addValore("Causa:", cess.getDsComTCessazionerl());
			}
			sezione.addSubsezione(subSezione);
			return sezione;
		}
		return null;
	}
	
	private Sezione getSezioneDatiCessazioneMissione(Comunicazione comunicazione) {
		Sezione sezione = new Sezione("Dati Cessazione");
		SubSezione subSezione = new SubSezione();
		if (comunicazione.getMissione() != null) {
			Cessazionerl cess = comunicazione.getMissione().getCessazionerl();
			subSezione.addValore("Data di cessazione:", ReportUtilities.formatDate(comunicazione.getMissione().getDtCessazione()));
			subSezione.addValore("Causa:", cess.getDsComTCessazionerl());
			sezione.addSubsezione(subSezione);
			return sezione;
		}
		return null;
	}
	
	
	private Sezione getSezioneVariazioneRagioneSocialeImpresa(Comunicazione comunicazione, Datore datore) {
		if (datore != null) {
			Sezione sezione = new Sezione("Azienda precedente");
			SubSezione subSezione = new SubSezione();
			subSezione.addValore("Denominazione precedente:", datore.getDsVariazioneRagSociale(), "Data inizio:", ReportUtilities.formatDate(comunicazione.getDtTrasferimentoVarDatori()));
			sezione.addSubsezione(subSezione);
			return sezione;
		}
		return null;
	}
	
	
	private Sezione getSezioneElencoSediLavoratoreInteressato(Comunicazione comunicazione) {

		List<RapportiLavoratoriSediInteressateVD> lavoratori = comunicazione.getRapLavSedeVD();
		if (lavoratori != null && lavoratori.size()>0) {
			
			Sezione sezione = new Sezione("Elenco sedi - lavoratori interessati");
			SubSezione subSezione = new SubSezione();
			
			for (RapportiLavoratoriSediInteressateVD lav : lavoratori) {
				String datiLav = "";
				if (lav.getLavoratoreVD() != null) {
					if (lav.getLavoratoreVD().getCodiceFiscale() != null)
						datiLav += lav.getLavoratoreVD().getCodiceFiscale() + " - ";
					if (lav.getLavoratoreVD().getCognome() != null)
						datiLav += lav.getLavoratoreVD().getCognome() + " ";
					if (lav.getLavoratoreVD().getNome() != null)
						datiLav += lav.getLavoratoreVD().getNome();
					
					if (lav.getLavoratoreVD().getComuneDom() != null) {
						datiLav += " - ";
						
						if (lav.getLavoratoreVD().getComuneDom().getDsComTComune() != null)
							datiLav += lav.getLavoratoreVD().getComuneDom().getDsComTComune();
						
//						if (lav.getLavoratoreVD().getComuneDom().getProvincia() != null && 
//								lav.getLavoratoreVD().getComuneDom().getProvincia().getDsComTProvincia() != null)
//							datiLav += lav.getLavoratoreVD().getComuneDom().getProvincia().getDsComTProvincia();
					}
				}
				subSezione.addValore("Lavoratore:", datiLav);
				
			
				Rapporto rapporto = lav.getRapportoVD();
				String rap = "";
				if (rapporto != null) {
					if (rapporto.getTipoContratti() != null && rapporto.getTipoContratti().getDsComTTipoContratto() != null)
						rap += rapporto.getTipoContratti().getDsComTTipoContratto() + " - ";
					if (rapporto.getDtInizioRapporto() != null)
						rap += ReportUtilities.formatDate(rapporto.getDtInizioRapporto()) + " - ";
					if (rapporto.getDtFineRapporto() != null)
						rap += ReportUtilities.formatDate(rapporto.getDtFineRapporto()) + " - ";
					if (rapporto.getCcnl() != null && rapporto.getCcnl().getDsCcnl() != null)
						rap += rapporto.getCcnl().getDsCcnl().toUpperCase() + " - ";
					if (rapporto.getIstatLivello5() != null && rapporto.getIstatLivello5().getDsComIstat2001livello5() != null)
						rap += rapporto.getIstatLivello5().getDsComIstat2001livello5() + " - ";
					if (rapporto.getPatInail() != null)
						rap += rapporto.getPatInail() + " - ";
					if (rapporto.getEntePrevidenziale() != null && rapporto.getEntePrevidenziale().getDsComTEntePrevidenziale() != null)
						rap += rapporto.getEntePrevidenziale().getDsComTEntePrevidenziale();
				}
				subSezione.addValore("Rapporto:", rap);
				
				SedeLavoro sede = lav.getSedeLavoroVD();
				String sedeIndirizzo = "";
				if (sede != null ) {
					if (sede.getIndirizzo() != null) sedeIndirizzo += sede.getIndirizzo() + " ";
					if (sede.getComune() != null && sede.getComune().getDsComTComune() != null) sedeIndirizzo += sede.getComune().getDsComTComune() +  " ";
					if (sede.getComune() != null && sede.getComune().getProvincia() != null && sede.getComune().getProvincia().getDsTarga() != null) sedeIndirizzo += "(" + sede.getComune().getProvincia().getDsTarga() + ")";
					if (sede.getCodCap() != null) sedeIndirizzo +=  " - " + sede.getCodCap();
				}
				subSezione.addValore("Sede:", sedeIndirizzo);
							
				subSezione.addValore("", "");
			}
			
			sezione.addSubsezione(subSezione);
			return sezione;
		}
		return null;
	}
	
	//========================================================================
	// UTILITIES 
	
	private boolean isContrattoApprendistatoTirocinio(Comunicazione comunicazione) {
		
		if (comunicazione.getRapporto() != null && comunicazione.getRapporto().getTipoContratti() != null) {
			String tipo = comunicazione.getRapporto().getTipoContratti().getTipo();
			
			if (tipo != null && tipo.length()>0) {
				return tipo.startsWith(ComonlConstants.TIPO_CONTRATTI_TIPO_AP) ||
						tipo.equals(ComonlConstants.TIPO_CONTRATTI_TIPO_TR) ||
						tipo.equals(ComonlConstants.TIPO_CONTRATTI_TIPO_TIE);	
			}
			
		}
		return false;
	}
	
	private static boolean inPiemonte(SedeLavoro sede) {
		if (sede != null) {
			return (sede.getComune() != null && sede.getComune().getProvincia() != null
					&& sede.getComune().getProvincia().getRegione() != null && ComonlConstants.CODICE_REGIONALE_PIEMONTE
							.equals(sede.getComune().getProvincia().getRegione().getCodRegioneMin()));
		}
		return false;
	}
	
	
	
	private static final long DIR_SEPARATOR = 50000;
	@SuppressWarnings("unused")
	private String getSubDirectory(Long idComunicazione) {
		if (idComunicazione == null)
			return null;
		long d = idComunicazione.longValue() / DIR_SEPARATOR;
		return Long.toString(DIR_SEPARATOR * (d+1));
	}


}
