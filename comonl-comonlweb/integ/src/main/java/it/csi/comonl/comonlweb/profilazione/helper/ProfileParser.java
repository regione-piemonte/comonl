/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION AAEP, IRIDE submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.profilazione.helper;

import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.profilazione.dto.ImpresaInfoc;
import it.csi.comonl.comonlweb.profilazione.dto.Profilo;
import it.csi.comonl.comonlweb.profilazione.dto.ProfiloUtente;
import it.csi.comonl.comonlweb.profilazione.dto.ProfiloUtenteComonl;
import it.csi.comonl.comonlweb.profilazione.dto.RuoloIrideListaCasiUso;
import it.csi.iride2.policy.entity.Identita;

/**
 * Crea il profilo dell'utente con le informazioni reperite da - Iride2 - AAEP -
 * COMONL NOTA: questa costruzione del profilo e' stata importata da
 * AMINDER.orchlavprof
 */
public class ProfileParser {

	protected final LogUtil logger = new LogUtil(getClass());

	public ProfiloUtenteComonl creaProfilo(ProfiloUtente listaProfili, Identita identitaIride) {
		ProfiloUtenteComonl profiloUtente = new ProfiloUtenteComonl(listaProfili);

		List<RuoloIrideListaCasiUso> listaRuoliIride2 = listaProfili.getListRuoliIride();

		List<ImpresaInfoc> listaAziendeAAEP = listaProfili.getListaAziendeAAEP();

		profiloUtente.inizializzaListaRuoli();

		for (RuoloIrideListaCasiUso ruoloProvenienteDaIride : listaRuoliIride2) {
			Profilo profiloIride = new Profilo();
			setCFUtenteCognomeNome(identitaIride, profiloIride);

			profiloIride.setCodiceFiscale(identitaIride.getCodFiscale());
			if (ComonlUtility.isNotVoid(ruoloProvenienteDaIride.getActor())) {
				String attore = ruoloProvenienteDaIride.getActor().getId();
				switch (attore) {
				case ComonlConstants.LEGALE_RAPPRESENTANTE_COMONL:
				case ComonlConstants.PERSONA_CARICA_AZIENDALE_COMONL:
					if (ComonlUtility.isNotVoid(listaAziendeAAEP)) {
						// -----------------------------------------------------------------------------
						// AAEP
						// -----------------------------------------------------------------------------
						for (ImpresaInfoc profiloAAEP : listaAziendeAAEP) {
							sonoLegaleRappresentante(identitaIride, profiloUtente, ruoloProvenienteDaIride, attore,
									profiloAAEP);
						}
					}
					break;
				case ComonlConstants.PERS_AUTORIZZATA_COMONL:
				case ComonlConstants.PERS_AUTORIZZATA_SCUOLA_COMONL:
					// -----------------------------------------------------------------------------
					// COMONL
					// -----------------------------------------------------------------------------
					sonoPersona(identitaIride, profiloUtente, attore);
					break;
				case ComonlConstants.CONSULENTE_RESPONSABILE_COMONL:
					// -----------------------------------------------------------------------------
					// COMONL
					// -----------------------------------------------------------------------------
					sonoConsulenteOperatore(identitaIride, profiloUtente, attore);
					break;
				case ComonlConstants.DELEGATO_RESPONSABILE_COMONL:
					// -----------------------------------------------------------------------------
					// COMONL
					// -----------------------------------------------------------------------------
					sonoDelegatoOperatore(identitaIride, profiloUtente, attore);
					break;
				case ComonlConstants.OPERATORE_APL_COMONL:
				case ComonlConstants.AMMINISTRATORE_COMONL:
				case ComonlConstants.MONITORAGGIO:
					// in questo caso fa la stessa cosa
					// -----------------------------------------------------------------------------
					// IRIDE
					// -----------------------------------------------------------------------------
					sonoOperatoreAmministratoreMonitoraggio(identitaIride, profiloUtente, ruoloProvenienteDaIride,
							attore);
					break;
				default:
					break;
				}
			}
		}
		return profiloUtente;
	}

	private void sonoLegaleRappresentante(Identita identitaIride, ProfiloUtenteComonl profiloUtente,
			RuoloIrideListaCasiUso ruoloProvenienteDaIride, String attore, ImpresaInfoc profiloAAEP) {
		// si commenta per poter far entrare anche i "Reg. Impresa"
//		if (profiloAAEP.getDescrFonte().equals("LR")) {
		Profilo profilo = new Profilo();
		setCFUtenteCognomeNome(identitaIride, profilo);

		profilo.setDenominazioneAzienda(profiloAAEP.getRagioneSociale());
		profilo.setCodiceFiscale(profiloAAEP.getCodiceFiscale());
		/*
		 * potrei arrivare anche con il profilo di una PERSONA CON CARICA AZIENDALE E
		 * QUINDI DEVO FARLO VEDERE COME LEGALE RAPPRESENTANTE
		 */
		profilo.setRuolo(determinaRuoloAttoreDaVisualizzare(ComonlConstants.LEGALE_RAPPRESENTANTE_COMONL));

		profiloUtente.addRuoloInLista(profilo);
		logger.debug("[ProfileParser::creaProfilo] aggiunto ruolo, " + profiloAAEP.getRagioneSociale() + " "
				+ profiloAAEP.getCodiceFiscale(), "");
//		}
	}

	private void sonoOperatoreAmministratoreMonitoraggio(Identita identitaIride, ProfiloUtenteComonl profiloUtente,
			RuoloIrideListaCasiUso ruoloProvenienteDaIride, String attore) {
		Profilo profiloIride;
		profiloIride = new Profilo();
		setCFUtenteCognomeNome(identitaIride, profiloIride);

		profiloIride.setCodiceFiscale(identitaIride.getCodFiscale());
		profiloIride.setDenominazioneAzienda(identitaIride.getCognome() + " " + identitaIride.getNome());
		profiloIride.setRuolo(determinaRuoloAttoreDaVisualizzare(attore));

		profiloIride.setEmail(ruoloProvenienteDaIride.getMail());
		profiloIride.setCodiceEnteOperatoreApl(ruoloProvenienteDaIride.getCodiceEnte());

		profiloUtente.addRuoloInLista(profiloIride);
		logger.debug("[ProfileParser::creaProfilo] OrchLavProfImpl, aggiunto ruolo: " + attore, "");
	}

	private void sonoDelegatoOperatore(Identita identitaIride, ProfiloUtenteComonl profiloUtente, String attore) {
		Profilo profiloIride;
		// -----------------------------------------------------------------------------
		// COMONL
		// -----------------------------------------------------------------------------
		for (int k = 0; k < profiloUtente.getProfiloOrchestratore().getListaProfiliDelegatoResponsabile().size(); k++) {
			DelegatoImpresa delegatoImpresa = profiloUtente.getProfiloOrchestratore()
					.getListaProfiliDelegatoResponsabile().get(k);
			if (delegatoImpresa.getId().getTipoAnagrafica().equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_DELEGATO_D)
					&& null == delegatoImpresa.getDataAnnullamento()
					&& null == delegatoImpresa.getAnagraficaDelegato().getDataAnnullamento()) {
				profiloIride = new Profilo();
				profiloIride.setCfUtente(delegatoImpresa.getId().getCfDelegato());
				profiloIride.setCodiceFiscale(delegatoImpresa.getId().getCfImpresa());
				profiloIride.setDenominazioneAzienda(delegatoImpresa.getDenominazione());
				profiloIride.setRuolo(determinaRuoloAttoreDaVisualizzare(attore));
				profiloIride.setEmail(delegatoImpresa.getAnagraficaDelegato().getEmail());
				profiloUtente.addRuoloInLista(profiloIride);

			}
		}
	}

	private void sonoConsulenteOperatore(Identita identitaIride, ProfiloUtenteComonl profiloUtente, String attore) {
		Profilo profiloIride;
		// -----------------------------------------------------------------------------
		// COMONL
		// -----------------------------------------------------------------------------
		for (int k = 0; k < profiloUtente.getProfiloOrchestratore().getListaProfiliConsulenteResponsabile()
				.size(); k++) {
			AnagraficaDelegato anaDelegato = profiloUtente.getProfiloOrchestratore()
					.getListaProfiliConsulenteResponsabile().get(k);
			if (anaDelegato.getId().getTipoAnagrafica().equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_CONSULENTE_C)
					&& null == anaDelegato.getDataAnnullamento()) {
				profiloIride = new Profilo();
				profiloIride.setCfUtente(anaDelegato.getId().getCfDelegato());
				profiloIride.setCodiceFiscale(anaDelegato.getSoggettoAbilitato().getCfSoggetto());
				profiloIride.setTipoSoggettoAbilitato(anaDelegato.getSoggettoAbilitato().getTipoSoggettoAbilitato());
				profiloIride.setDenominazioneAzienda(anaDelegato.getSoggettoAbilitato().getCognomeDenominazione());
				profiloIride.setEmail(anaDelegato.getEmail());
				profiloIride.setRuolo(determinaRuoloAttoreDaVisualizzare(attore));
				profiloUtente.addRuoloInLista(profiloIride);
			}
		}
	}

	private void sonoPersona(Identita identitaIride, ProfiloUtenteComonl profiloUtente, String attore) {
		Profilo profiloIride;
		// -----------------------------------------------------------------------------
		// COMONL
		// -----------------------------------------------------------------------------

		for (int k = 0; k < profiloUtente.getProfiloOrchestratore().getListaProfiliPersonaAutorizzata().size(); k++) {
			// den azienda | ruolo | cf

			DelegatoImpresa delegatoImpresa = profiloUtente.getProfiloOrchestratore()
					.getListaProfiliPersonaAutorizzata().get(k);
			if (delegatoImpresa.getId().getTipoAnagrafica()
					.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_PERSONA_AUTORIZZATA_E)
					&& null == delegatoImpresa.getDataAnnullamento()
					&& null == delegatoImpresa.getAnagraficaDelegato().getDataAnnullamento()) {

				profiloIride = new Profilo();
				setCFUtenteCognomeNome(identitaIride, profiloIride);

				profiloIride.setDenominazioneAzienda(delegatoImpresa.getDenominazione());

				profiloIride.setRuolo(determinaRuoloAttoreDaVisualizzare(attore));
				profiloIride.setCodiceFiscale(delegatoImpresa.getId().getCfImpresa());
				profiloIride.setEmail(delegatoImpresa.getAnagraficaDelegato().getEmail());

				profiloUtente.addRuoloInLista(profiloIride);
			}
		}
	}

	private void setCFUtenteCognomeNome(Identita identitaIride, Profilo profiloIride) {
		profiloIride.setCfUtente(identitaIride.getCodFiscale());
		profiloIride.setCognome(identitaIride.getCognome());
		profiloIride.setNome(identitaIride.getNome());
	}

	private String determinaRuoloAttoreDaVisualizzare(String attore) {
		switch (attore) {
		case ComonlConstants.LEGALE_RAPPRESENTANTE_COMONL:
			return ComonlConstants.LEGALE_RAPPRESENTANTE_COMONL_DESC;
		case ComonlConstants.AMMINISTRATORE_COMONL:
			return ComonlConstants.AMMINISTRATORE_COMONL_DESC;
		case ComonlConstants.OPERATORE_APL_COMONL:
			return ComonlConstants.OPERATORE_APL_COMONL_DESC;
		case ComonlConstants.MONITORAGGIO:
			return ComonlConstants.MONITORAGGIO_DESC;
		case ComonlConstants.CONSULENTE_RESPONSABILE_COMONL:
			return ComonlConstants.CONSULENTE_RESPONSABILE_COMONL_DESC;
		case ComonlConstants.DELEGATO_RESPONSABILE_COMONL:
			return ComonlConstants.DELEGATO_RESPONSABILE_COMONL_DESC;
		case ComonlConstants.PERS_AUTORIZZATA_COMONL:
			return ComonlConstants.PERS_AUTORIZZATA_COMONL_DESC;
		case ComonlConstants.PERSONA_CARICA_AZIENDALE_COMONL:
			return ComonlConstants.PERSONA_CARICA_AZIENDALE_COMONL_DESC;
		case ComonlConstants.PERS_AUTORIZZATA_SCUOLA_COMONL:
			return ComonlConstants.PERS_AUTORIZZATA_COMONL_DESC;
		default:
			return ComonlConstants.DATO_NON_PERVENUTO;
		}
	}
}
