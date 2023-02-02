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

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostStampaRicercaDelegheRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PdfResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.report.ReportAnagrafiche;
import it.csi.comonl.comonlweb.ejb.util.report.ReportUtilities;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaDelega;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.Sort;

public class StampaAccreditamentoDelegheService
		extends BaseDelegaService<PostStampaRicercaDelegheRequest, PdfResponse> {

	private DelegaDad delegaDad;
	private FormRicercaDelega ricercaDelega;
	
	public StampaAccreditamentoDelegheService(ConfigurationHelper configurationHelper,
			DelegaDad delegaDad, DecodificaDad decodificaDad) {
		super(configurationHelper, delegaDad,decodificaDad);
		this.delegaDad = delegaDad;
	}

	@Override
	protected void checkServiceParams() {
		ricercaDelega = request.getFormRicercaDelega();
		checkNotNull(ricercaDelega, "ricercaDelega", true);
	}

	@Override
	protected void execute() {
		
		PagedList<Delega> risultatiRicercaDeleghe = delegaDad.getRicerca(0, Integer.MAX_VALUE,
				new Sort("", "asc"), request.getFormRicercaDelega());

		if (risultatiRicercaDeleghe != null && risultatiRicercaDeleghe.getList() != null) {
			createPdf(risultatiRicercaDeleghe.getList());
		}
	}

	private void createPdf(List<Delega> res) {
		
		ReportAnagrafiche reportAnagrafiche = new ReportAnagrafiche("Accreditamento Deleghe");
		reportAnagrafiche.setLandscape(false);
		
		reportAnagrafiche.addNomiColonne("Denominazione Impresa");
		reportAnagrafiche.addNomiColonne("Delegato");
		reportAnagrafiche.addNomiColonne("Delegante");
		reportAnagrafiche.addNomiColonne("Stato");
		
		for (Delega delega : res) {
			List<String> cols = new ArrayList<String>();
			
			cols.add(delega.getDenominazioneImpresa());
			cols.add(delega.getCognomeDelegato() + " " + delega.getNomeDelegato());
			cols.add(delega.getCognomeDelegante() + " " + delega.getNomeDelegante());
			String stato = "";
			if (delega.getStatoDelega() != null)
				stato = delega.getStatoDelega().getDsStatoDelega();
			cols.add(stato);
			reportAnagrafiche.addValori(cols);
		}
		
		String htmlContent = ReportUtilities.createReportAnagrafiche(reportAnagrafiche);
		
		try {
			ReportUtilities.makePdfResponse(response, htmlContent, "AccreditamentoDeleghe");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			log.info("StampaAccreditamentoDelegheService", "Accreditamento Deleghe - fine");
		}
	}
	
	

}
