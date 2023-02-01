/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION AAEP, IRIDE submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.profilazione.helper;

import java.util.List;

import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.profilazione.dto.ImpresaInfoc;
import it.csi.comonl.comonlweb.profilazione.dto.ProfiloUtente;
import it.csi.comonl.comonlweb.profilazione.dto.RuoloIrideListaCasiUso;
import it.csi.iride2.policy.entity.Identita;

/**
 * Classe di utilita' per il reperimento del profilo.
 * 
 */
public class ProfilazioneHelper {

	protected final LogUtil logger = new LogUtil(getClass());

	/**
	 * Recupera il profilo a partire dall'identita' restituita da Shibboleth.
	 * Richiama i servizi di AAEP, COMONL e IRIDE2 per ottenere i dati del profilo.
	 * La logica di costruzione del profilo e' delegata a ProfileParser.
	 * 
	 * @param identitaIride un'identita' Iride2
	 * @return
	 * @throws Exception
	 */
	public ProfiloUtente ricercaProfiloUtente(Identita identitaIride) throws Exception {
		logger.info("[ProfilazioneHelper::ricercaProfiloUtente] " + identitaIride, "");
		ProfiloUtente profiloUtente = new ProfiloUtente();

		List<ImpresaInfoc> listaAziendeAAEP = AdapterAAEP.getInstance()
				.cercaElencoAziende(identitaIride.getCodFiscale());
		profiloUtente.setListaAziendeAAEP(listaAziendeAAEP);

		List<RuoloIrideListaCasiUso> ruoliIride = AdapterIride.getInstance().getListRuoliIride(identitaIride);
		profiloUtente.setListRuoliIride(ruoliIride);

		return profiloUtente;

	}

}
