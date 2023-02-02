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

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostRicercaVardatoriRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRicercaVardatoriResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RicercaVardatori;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;

/**
 * PostRicercaProspetto
 */
public class PostRicercaVardatoriService
		extends BaseComunicazioneService<PostRicercaVardatoriRequest, PostRicercaVardatoriResponse> {
	
	private FormRicercaComunicazione ricercaComunicazione;
	CommonDad commonDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param prospettoDad        the DAD for the prospetto
	 */
	public PostRicercaVardatoriService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad,CommonDad commonDad) {
		super(configurationHelper, comunicazioneDad);
		this.commonDad=commonDad;
	}
	
	 @Override
		protected void checkServiceParams() {
		 ricercaComunicazione = request.getRicercaComunicazione();
		 checkNotNull(ricercaComunicazione, "ricercaComunicazione", true);
		 checkNotNull(ricercaComunicazione.getDataInvioInserimentoDa(),"dataInserimentoDa", true);
		 checkNotNull(ricercaComunicazione.getDataInvioInserimentoA(),"dataInserimentoA", true);
	    }
	
	@Override
	protected void execute() {
		log.info("execute----------->", "Entro nel metodo execute");

		ComonlsParametri  comonlsParametri= commonDad
				.getParametroByDescrizione(ComonlConstants.COMONLS_PARAMETRI_RICERCA_COMUNICAZIONI_NMASSIMO);
		checkBusinessCondition(comonlsParametri!=null, MsgComonl.COMCOMP0002.getError());// usare un messaggio appropriato
		int limMaxRicerca = Integer.parseInt(comonlsParametri.getValoreParametro());
		
		long count = comunicazioneDad.countRicercaVardatori(ricercaComunicazione);
		checkBusinessCondition(count > 0, MsgComonl.COMCOMP0002.getError());
		checkBusinessCondition(count <= limMaxRicerca, MsgComonl.COMCOME0004.getError() );
		
		if (request.getSize() == 0 || request.getSize() > limMaxRicerca) {
			request.setSize(limMaxRicerca);
		}
		
		ricercaComunicazione.setTipoTracciato(ComonlConstants.TIPO_TRACCIATO_VARDATORI_ID); 
		TipoComunicazione tipoComunicazione = new TipoComunicazione();
		tipoComunicazione.setId(ComonlConstants.TIPO_COMUNICAZIONE_VARIAZIONE_DATORE_ID);
		ricercaComunicazione.setTipoComunicazione(tipoComunicazione);
		
		PagedList<RicercaVardatori> risultatiRicercaComunicazioni = comunicazioneDad.getRicercaVardatori(request.getPage(), request.getSize(),
				request.getSort(), request.getRicercaComunicazione(), limMaxRicerca);
		
		response.setRisultatiRicercaComunicazioni(risultatiRicercaComunicazioni);
	}
}
