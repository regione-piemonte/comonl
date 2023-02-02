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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.common.GetRuoloRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.common.GetRuoloResponse;
import it.csi.comonl.comonlweb.ejb.util.ComonlThreadLocalContainer;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.profilazione.dto.Profilo;
import it.csi.comonl.comonlweb.profilazione.dto.ProfiloUtente;
import it.csi.comonl.comonlweb.profilazione.dto.ProfiloUtenteComonl;
import it.csi.comonl.comonlweb.profilazione.helper.ProfilazioneHelper;
import it.csi.comonl.comonlweb.profilazione.helper.ProfileParser;
import it.csi.iride2.policy.entity.Identita;

/**
 * GetRuolo
 */
public class GetRuoloService extends BaseCommonService<GetRuoloRequest, GetRuoloResponse> {

	DecodificaDad decodificaDad;
	AnagraficaDelegatoDad anagraficaDelegatoDad;
	DelegaDad delegaDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param ruoloDad            the DAD for the ruolo
	 */
	public GetRuoloService(ConfigurationHelper configurationHelper, CommonDad commonDad, DecodificaDad decodificaDad,
			AnagraficaDelegatoDad anagraficaDelegatoDad, DelegaDad delegaDad) {
		super(configurationHelper, commonDad);
		this.decodificaDad = decodificaDad;
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
		this.delegaDad = delegaDad;
	}

	@Override
	protected void execute() {
		try {
			Map<String, Ruolo> elencoRuoliSenzaDuplicati = new HashMap<String, Ruolo>();

			Identita identita = ComonlThreadLocalContainer.IDENTITA.get();
			ProfilazioneHelper helper = new ProfilazioneHelper();
			ProfiloUtente ilProfiloUtente = helper.ricercaProfiloUtente(identita);

			List<DelegatoImpresa> listaProfiliPersonaAutorizzata = anagraficaDelegatoDad
					.findDelegatoimpresaByIdAnagraficaDelegato(identita.getCodFiscale(),
							ComonlConstants.TIPOLOGIA_ANAGRAFICA_PERSONA_AUTORIZZATA_E);
			ilProfiloUtente.setListaProfiliPersonaAutorizzata(listaProfiliPersonaAutorizzata);
			List<AnagraficaDelegato> listaProfiliConsulente = anagraficaDelegatoDad
					.findSoggettoByCfSoggettoAndTipoAnagraficaDelegato(identita.getCodFiscale(),
							ComonlConstants.TIPOLOGIA_ANAGRAFICA_CONSULENTE_C);
			ilProfiloUtente.setListaProfiliConsulenteResponsabile(listaProfiliConsulente);
			List<DelegatoImpresa> listaProfiliDelegato = anagraficaDelegatoDad
					.findDelegatoimpresaByIdAnagraficaDelegato(identita.getCodFiscale(),
							ComonlConstants.TIPOLOGIA_ANAGRAFICA_DELEGATO_D);
			ilProfiloUtente.setListaProfiliDelegatoResponsabile(listaProfiliDelegato);

			ProfileParser parser = new ProfileParser();
			ProfiloUtenteComonl r = parser.creaProfilo(ilProfiloUtente, identita);

			for (Profilo profilo : r.getListaRuoliAmmessi()) {
				Ruolo ruolo = new Ruolo();
				ruolo.setDenominazioneAzienda(profilo.getDenominazioneAzienda());
				ruolo.setCodiceFiscaleAzienda(profilo.getCodiceFiscale());
				ruolo.setCodiceFiscaleUtente(profilo.getCfUtente());
				ruolo.setIlRuolo(profilo.getRuolo());
				ruolo.setDsCognome(identita.getCognome());
				ruolo.setDsNome(identita.getNome());
				ruolo.setCodiceEnteOperatoreAPL(profilo.getCodiceEnteOperatoreApl());
				ruolo.setEmail(profilo.getEmail());
				// TODO DEMO 24 - CONSULENTE
				if (determinaRuoloUtenteFlagImpresaAccentrata(profilo, ruolo)) {
					
					
					String key = ruolo.getCodiceFiscaleAzienda() + ruolo.getIlRuolo();
					elencoRuoliSenzaDuplicati.put(key, ruolo);
					
					
					/*
					if (elencoRuoliSenzaDuplicati.containsKey(ruolo.getCodiceFiscaleAzienda())
							&& !(elencoRuoliSenzaDuplicati.get(ruolo.getCodiceFiscaleAzienda()).getIlRuolo()
									.equals(ruolo.getIlRuolo()))) {
						elencoRuoliSenzaDuplicati.put(ruolo.getCodiceFiscaleAzienda(), ruolo);
					} else {
						elencoRuoliSenzaDuplicati.put(ruolo.getCodiceFiscaleAzienda(), ruolo);
					}
					*/
				}
				if (null != profilo.getTipoSoggettoAbilitato()) {
					ruolo.setTipoSoggettoAbilitato(profilo.getTipoSoggettoAbilitato());
				}
			}
			List<Ruolo> listaRuoloSenzaDuplicati = elencoRuoliSenzaDuplicati.values().stream()
					.collect(Collectors.toList());
			response.setRuolos(listaRuoloSenzaDuplicati);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ComonlClassUtils.truncClassName(getClass()) + " Eccezione !!", e);
			throw new RuntimeException(e);
		}
	}

	private boolean determinaRuoloUtenteFlagImpresaAccentrata(Profilo ilprofilo, Ruolo ilRuolo) {
		AnagraficaAziAccent laAziendaAccentrata = null;
		boolean isProfiloDaAggiungere = true;
		switch (ilprofilo.getRuolo()) {
		case ComonlConstants.LEGALE_RAPPRESENTANTE_COMONL_DESC:
		case ComonlConstants.PERSONA_CARICA_AZIENDALE_COMONL_DESC:
			ilRuolo.setLegaleRappresentante(true);
			break;
		case ComonlConstants.AMMINISTRATORE_COMONL_DESC:
			ilRuolo.setAmministratore(true);
			break;
		case ComonlConstants.OPERATORE_APL_COMONL_DESC:
			ilRuolo.setOperatoreProvinciale(true);
			break;
		case ComonlConstants.MONITORAGGIO_DESC:
			ilRuolo.setMonitoraggio(true);
			break;
		case ComonlConstants.DELEGATO_RESPONSABILE_COMONL_DESC:
			boolean laDelegaIsValida = delegaDad.isPresenteDelegaValida(ilprofilo.getCfUtente(),
					ilprofilo.getCodiceFiscale());
			if (laDelegaIsValida) {
				ilRuolo.setDelegatoRespo(true);
				laAziendaAccentrata = anagraficaDelegatoDad.getAziendaAccentrata(ilprofilo.getCodiceFiscale());
				if (ComonlUtility.isNotVoid(laAziendaAccentrata)) {
					ilprofilo.setFlgImpresaAccentrata(true);
				}
			} else {
				isProfiloDaAggiungere = false;
			}
			break;
		case ComonlConstants.CONSULENTE_RESPONSABILE_COMONL_DESC:
			ilRuolo.setConsulenteRespo(true);
			SoggettoAbilitato ilSoggettoAbilitato = anagraficaDelegatoDad
					.getSoggettoAbilitato(ilprofilo.getCodiceFiscale());
			if (ComonlUtility.isNotVoid(ilSoggettoAbilitato)) {
				ilprofilo.setFlgImpresaAccentrata(ComonlConstants.FLAG_S.equals(ilSoggettoAbilitato.getFlgAccentramento()));
				ilprofilo.setDenominazioneAzienda(ilSoggettoAbilitato.getCognomeDenominazione());
				ilprofilo.setCfUtente(ilSoggettoAbilitato.getCfSoggetto());
			}
			break;
		case ComonlConstants.PERS_AUTORIZZATA_COMONL_DESC:
			laAziendaAccentrata = anagraficaDelegatoDad.getAziendaAccentrata(ilprofilo.getCodiceFiscale());
			ilprofilo.setFlgImpresaAccentrata(ComonlUtility.isNotVoid(laAziendaAccentrata));
			ilRuolo.setPersonaAutorizzata(true);
			break;
		default:
			break;
		}
		return isProfiloDaAggiungere;
	}
}
