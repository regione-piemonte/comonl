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

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostRicercaComunicazioniRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRicercaComunicazioniResponse;
import it.csi.comonl.comonlweb.ejb.exception.RicercaLimMaxException;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;

/**
 * PostRicercaProspetto
 */
public class PostRicercaComunicazioniVardatoriService
		extends BaseComunicazioneService<PostRicercaComunicazioniRequest, PostRicercaComunicazioniResponse> {

	private FormRicercaComunicazione ricercaComunicazione;
	CommonDad commonDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param prospettoDad        the DAD for the prospetto
	 */
	public PostRicercaComunicazioniVardatoriService(ConfigurationHelper configurationHelper,
			ComunicazioneDad comunicazioneDad, CommonDad commonDad) {
		super(configurationHelper, comunicazioneDad);
		this.commonDad = commonDad;
	}

	@Override
	protected void checkServiceParams() {
		ricercaComunicazione = request.getRicercaComunicazione();
		checkNotNull(ricercaComunicazione, "ricercaComunicazione", true);
		checkNotNull(ricercaComunicazione.getDataInvioInserimentoDa(), "dataInserimentoDa", true);
		checkNotNull(ricercaComunicazione.getDataInvioInserimentoA(), "dataInserimentoA", true);
	}
	
	
	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");

		ComonlsParametri  comonlsParametri= commonDad
				.getParametroByDescrizione(ComonlConstants.COMONLS_PARAMETRI_RICERCA_COMUNICAZIONI_NMASSIMO);
		checkBusinessCondition(comonlsParametri!=null, MsgComonl.COMCOMP0002.getError());// usare un messaggio appropriato
		int limMaxRicerca = Integer.parseInt(comonlsParametri.getValoreParametro());

		long time = System.currentTimeMillis();
		PagedList<RicercaComunicazione> risultatiRicercaComunicazioni = null;
		try {
			
			ricercaComunicazione.setTipoTracciato(ComonlConstants.TIPO_TRACCIATO_VARDATORI_ID);
			TipoComunicazione tipoComunicazione = new TipoComunicazione();
			tipoComunicazione.setId(ComonlConstants.TIPO_COMUNICAZIONE_VARIAZIONE_DATORE_ID);
			ricercaComunicazione.setTipoComunicazione(tipoComunicazione);
			
			risultatiRicercaComunicazioni = comunicazioneDad.getRicerca(request.getPage(), request.getSize(),
				request.getSort(), request.getRicercaComunicazione(), limMaxRicerca);
		}
		catch (RicercaLimMaxException err) {
			checkBusinessCondition(false, MsgComonl.COMCOME0004.getError() );
		}
		long end = System.currentTimeMillis() - time;

		if (risultatiRicercaComunicazioni != null) {
			long count = risultatiRicercaComunicazioni.getTotalElements();
			checkBusinessCondition(count > 0, MsgComonl.COMCOMP0002.getError());
		}

		System.out.println("TEMPO RICERCA COMUNICAZIONI:" + end);

		response.setRisultatiRicercaComunicazioni(risultatiRicercaComunicazioni);
	}

}
