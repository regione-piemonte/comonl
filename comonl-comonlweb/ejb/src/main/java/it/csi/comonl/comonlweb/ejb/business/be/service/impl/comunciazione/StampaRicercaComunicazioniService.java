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

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostStampaComunicazioniRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PdfResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.report.ReportAnagrafiche;
import it.csi.comonl.comonlweb.ejb.util.report.ReportUtilities;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.Sort;

public class StampaRicercaComunicazioniService
		extends BaseComunicazioneService<PostStampaComunicazioniRequest, PdfResponse> {

	private FormRicercaComunicazione ricercaComunicazione;
	CommonDad commonDad;

	public StampaRicercaComunicazioniService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, CommonDad commonDad) {
		super(configurationHelper, comunicazioneDad);
		this.commonDad=commonDad;
	}


	@Override
	protected void checkServiceParams() {
		 ricercaComunicazione = request.getRicercaComunicazione();
			checkNotNull(ricercaComunicazione, "ricercaComunicazione", true);
		}

	@Override
	protected void execute() {

		ComonlsParametri  comonlsParametri= commonDad
				.getParametroByDescrizione(ComonlConstants.COMONLS_PARAMETRI_RICERCA_COMUNICAZIONI_NMASSIMO);
		checkBusinessCondition(comonlsParametri!=null, MsgComonl.COMCOMP0002.getError());
		int limMaxRicerca = Integer.parseInt(comonlsParametri.getValoreParametro());

		long count = comunicazioneDad.countRicercaComunicazione(ricercaComunicazione);
		log.debug("execute----------->", "valore di count -----> " + count);
		checkBusinessCondition(count > 0, MsgComonl.COMCOMP0002.getError());
		checkBusinessCondition(count <= limMaxRicerca, MsgComonl.COMCOME0004.getError() );


		PagedList<RicercaComunicazione> res = comunicazioneDad.getRicerca(0, limMaxRicerca,
				new Sort("", "asc"), request.getRicercaComunicazione(), limMaxRicerca);


		if (res != null && res.getList() != null) {
			createPdf(res.getList());
		}
	}

	private void createPdf(List<RicercaComunicazione> res) {

		ReportAnagrafiche reportAnagrafiche = new ReportAnagrafiche("Comunicazioni");
		reportAnagrafiche.setLandscape(true);

		reportAnagrafiche.addNomiColonne("Azienda");
		reportAnagrafiche.addNomiColonne("Lavoratore");
		reportAnagrafiche.addNomiColonne("Data invio/ins");
		reportAnagrafiche.addNomiColonne("Data assunz.");
		reportAnagrafiche.addNomiColonne("Data evento");
		reportAnagrafiche.addNomiColonne("Tipo");
		reportAnagrafiche.addNomiColonne("Stato");
		reportAnagrafiche.addNomiColonne("Cod. comunic.");
		
		
		for (RicercaComunicazione com : res) {
			List<String> cols = new ArrayList<String>();
			
			cols.add(com.getCodiceFiscaleDatore());
			cols.add(com.getCodiceFiscaleLavoratore());
			cols.add(ReportUtilities.formatDate(com.getDtInvio() != null ? com.getDtInvio() : com.getDtInsert()));
			cols.add(ReportUtilities.formatDate(com.getDtInizioRapporto()));
			cols.add(ReportUtilities.formatDate(com.getDtEvento()));
			cols.add(com.getDsComTTipoComunicazione());
			cols.add(com.getDsComTStatoComunicazione());
			cols.add(com.getCodiceComunicazioneReg());
			
			reportAnagrafiche.addValori(cols);
		}
		
		String htmlContent = ReportUtilities.createReportAnagrafiche(reportAnagrafiche);
		
		try {
			ReportUtilities.makePdfResponse(response, htmlContent, "Comunicazioni");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			log.info("StampaRicercaComunicazioniService", "Comunicazioni - fine");
		}
	}
	
}

