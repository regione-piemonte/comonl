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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.SessionContext;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaTipoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LegaleRapprDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoLavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.TutoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostRecuperoComunicazioniRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRecuperoComunicazioniResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.mail.MailHelper;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRecuperoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RecuperoComunicazione;

public class PostRecuperoComunicazioniService
		extends BaseComunicazioneService<PostRecuperoComunicazioniRequest, PostRecuperoComunicazioniResponse> {

	private FormRecuperoComunicazione recuperoComunicazione;

	private CommonDad commonDad;
	private DecodificaTipoDad decodificaTipoDad;
	private DecodificaDad decodificaDad;
	private DatoreDad datoreDad;
	private LegaleRapprDad legaleRapprDad;
	private SedeLavoroDad sedeLavoroDad;
	private RapportoDad rapportoDad;
	private LavoratoreDad lavoratoreDad;
	private RapportoLavoratoreDad rapportoLavoratoreDad;
	private AnagraficaDelegatoDad anagraficaDelegatoDad;
	private TutoreDad tutoreDad;
	private MailHelper mailHelper;
	private SessionContext sessionContext;
	
	
	public PostRecuperoComunicazioniService(SessionContext sessionContext,ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad,
			DecodificaTipoDad decodificaTipoDad, DecodificaDad decodificaDad, CommonDad commonDad,
			DatoreDad datoreDad,LegaleRapprDad legaleRapprDad,SedeLavoroDad sedeLavoroDad,RapportoDad rapportoDad,LavoratoreDad lavoratoreDad,
			RapportoLavoratoreDad rapportoLavoratoreDad,TutoreDad tutoreDad, MailHelper mailHelper, AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, comunicazioneDad);
		this.sessionContext = sessionContext;
		this.decodificaTipoDad = decodificaTipoDad;
		this.commonDad = commonDad;
		this.decodificaDad = decodificaDad;
		this.datoreDad = datoreDad;
		this.legaleRapprDad = legaleRapprDad;
		this.sedeLavoroDad = sedeLavoroDad;
		this.rapportoDad = rapportoDad;
		this.lavoratoreDad = lavoratoreDad;
		this.rapportoLavoratoreDad = rapportoLavoratoreDad;
		this.tutoreDad = tutoreDad;
		this.mailHelper = mailHelper;
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
	}



	@Override
	protected void checkServiceParams() {
		recuperoComunicazione = request.getRecuperoComunicazione();
		checkNotNull(recuperoComunicazione, "recuperoComunicazione", true);
	}


	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");


		InviaComunicazioneManager manager = new InviaComunicazioneManager(sessionContext, configurationHelper,
				comunicazioneDad, decodificaTipoDad, decodificaDad, commonDad, datoreDad, legaleRapprDad, sedeLavoroDad,
				rapportoDad, lavoratoreDad, rapportoLavoratoreDad,tutoreDad, mailHelper, anagraficaDelegatoDad);

		List<Long> ids = comunicazioneDad.getRecuperoComunicazione(request.getRecuperoComunicazione());

		List<RecuperoComunicazione> list = new ArrayList<RecuperoComunicazione>();
		Ruolo ruolo = request.getRecuperoComunicazione().getRuolo();
		RecuperoComunicazione res = null;
		for (Long id : ids) {
			List<Comunicazione> lista = manager.invia(new BigDecimal(id), ruolo);
			
			Comunicazione comm = lista.get(0);

			res = new RecuperoComunicazione();
			res.setIdComunicazione(new BigDecimal(id));
			if (comm != null && "S".equalsIgnoreCase(comm.getFlgInvioMinistero())) {
				res.setEsitoInvio("OK");
				res.setCodiceRegionale(comm.getCodiceComunicazioneReg());
				res.setEmail(comm.getEmail());
			}
			else {
				res.setEsitoInvio("KO");
			}
			list.add(res);
		}
		response.setRecuperoComunicazione(list);
	}
}
