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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlDateUtils;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.csi.porte.InfoPortaDelegata;
import it.csi.csi.porte.proxy.PDProxy;
import it.csi.csi.util.xml.PDConfigReader;
import it.csi.csi.wrapper.UserException;
import it.csi.servizioaaep2.interfaces.AAEPInterface2;
import it.csi.servizioaaep2.vo.AziendaAAEP;
import it.csi.servizioaaep2.vo.Carica;
import it.csi.servizioaaep2.vo.ImpresaInfoc;
import it.csi.servizioaaep2.vo.ListaPersona;
import it.csi.servizioaaep2.vo.ListaPersonaCaricaInfoc;
import it.csi.servizioaaep2.vo.ListaSediInfoc;
import it.csi.servizioaaep2.vo.Persona;
import it.csi.servizioaaep2.vo.Sede;
import it.csi.servizioaaep2.vo.SedeInfoc;
import it.csi.util.performance.StopWatch;

/**
 * Recupero dei dati da AAEP.aaepsv (PD/PA SOPA)
 */
public class AdapterAAEP {

//	private static final Logger logger = Logger.getLogger(ComonlConstants.LOGGER);
	protected final LogUtil logger = new LogUtil(getClass());

	public static final String FONTE_DATO_INFOCAMERE = "3";

	private static final AdapterAAEP instance = new AdapterAAEP();

	private static final String PD_SERVICE = "/defpd_aaep_soap.xml";

	private static AAEPInterface2 service;

	public static AdapterAAEP getInstance() {
		return instance;
	}

	/**
	 * Restituisce l'interfaccia della Porta Delegata SOAP
	 */
	private AAEPInterface2 getService() throws Exception {
		if (service == null) {
			InputStream pd = this.getClass().getResourceAsStream(PD_SERVICE);
			InfoPortaDelegata info = PDConfigReader.read(pd);
			service = (AAEPInterface2) PDProxy.newInstance(info);
		}
		return service;
	}

	public List<it.csi.comonl.comonlweb.profilazione.dto.ImpresaInfoc> cercaElencoAziende(String codiceFiscale)
			throws Exception {
		logger.debug("[AdapterAAEP::cercaElencoAziende] BEGIN codiceFiscale=" + codiceFiscale, "");

		StopWatch watcher = new StopWatch(ComonlConstants.LOGGER);
		watcher.start();

		try {

			Date today = new Date();
			Map<ImpresaInfoc, List<SedeInfoc>> elencoAziendeRis = null;
			ListaPersonaCaricaInfoc[] elencoPersonaCaricaInfoc = null;

			try {
				elencoPersonaCaricaInfoc = getService().cercaPerFiltriCodFiscFonte(codiceFiscale,
						FONTE_DATO_INFOCAMERE);
			} catch (UserException e) {
				logger.error("UserException---->>>> [AdapterAAEP::cercaElencoAziende] " + e.getMessage(), e);
			} catch (Exception e) {
				logger.error("Exception---->>>> [AdapterAAEP::cercaElencoAziende] ", e);
				throw new Exception(e);
			}

			if (elencoPersonaCaricaInfoc != null && elencoPersonaCaricaInfoc.length > 0) {
				elencoAziendeRis = new HashMap<>();

				for (ListaPersonaCaricaInfoc personaCaricaInfoc : elencoPersonaCaricaInfoc) {
					try {
						ImpresaInfoc aziendaInfoc = getService().cercaPerCodiceFiscaleINFOC(
								personaCaricaInfoc.getCodFiscaleAzienda(), FONTE_DATO_INFOCAMERE, "", "", "");

						GregorianCalendar dataCessaz = ComonlDateUtils.stringToDate(aziendaInfoc.getDataCessaz());
						GregorianCalendar dataDenunciaCessaz = ComonlDateUtils
								.stringToDate(aziendaInfoc.getDataDenunciaCessaz());

						if ((dataCessaz == null || dataCessaz.after(today))
								&& (dataDenunciaCessaz == null || dataDenunciaCessaz.after(today))
								&& (aziendaInfoc.getCodCausCessaz() == null)) {
							boolean found = false;
							// ListaSediInfoc[] listaSedi = aziendaInfoc.getListaSediInfoc();
							List<SedeInfoc> elencoSediInfoc = new ArrayList<>();
							elencoSediInfoc.add(null); // spazio sede legale

							ListaPersona listaPersona = getService().cercaPerCodiceFiscalePersConCaricaFonteInfoc(
									personaCaricaInfoc.getCodFiscaleAzienda(), "N", FONTE_DATO_INFOCAMERE);

							Persona[] pp = listaPersona.getListaPersone();

							for (int k = 0; k < pp.length; k++) {
								if (StringUtils.equalsIgnoreCase(codiceFiscale, pp[k].getCodiceFiscale())) {
									logger.debug("[AdapterAAEP::cercaElencoAziende] trovata carica per azienda CF = "
											+ personaCaricaInfoc.getCodFiscaleAzienda() + " - Carica: "
											+ personaCaricaInfoc.getDescrCarica() + " ("
											+ personaCaricaInfoc.getCodiceCarica() + ")", "");
									found = true;

									// modifica per passare anche l'informazione se l'operatore e' assimilabile ad
									// un legale rappresentante per l'azienda oppure no.
									// In tal caso sara' assimilato al ruolo di "persona con carica aziendale"
									Carica[] listaCariche = pp[k].getListaCariche();
									for (int a = 0; a < listaCariche.length; a++) {
										if (listaCariche[a].getFlagRappresentanteLegale() != null
												&& listaCariche[a].getFlagRappresentanteLegale().equals("S")) {
											// uso questo campo perche' tanto non viene utilizzato
											aziendaInfoc.setDescrFonte("LR");
											break;
										}
									}
								}
							}

							if (found) {
								elencoAziendeRis.put(aziendaInfoc, elencoSediInfoc);
							}
						}

					} catch (Exception e) {
						logger.error("Exception----->  AdapterAAEP::cercaElencoAziende", e);
					}
				}
			}

			if (elencoAziendeRis == null)
				return null;

			List<it.csi.comonl.comonlweb.profilazione.dto.ImpresaInfoc> result = new ArrayList<>();
			for (ImpresaInfoc key : elencoAziendeRis.keySet()) {
				result.add(converti(key));
			}
			return result;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "cercaElencoAziende()",
					"invocazione servizi AAEP " + ComonlClassUtils.truncClassName(getClass()) + ".cercaElencoAziende",
					"");
			watcher.stop();
		}
	}

	/**
	 * Converte i dati restituiti dal servizio di AAEP in DTO di comonl.
	 */
	private it.csi.comonl.comonlweb.profilazione.dto.ImpresaInfoc converti(ImpresaInfoc iWS) {
		it.csi.comonl.comonlweb.profilazione.dto.ImpresaInfoc i = new it.csi.comonl.comonlweb.profilazione.dto.ImpresaInfoc();
		i.setAnnoDenunciaAddetti(iWS.getAnnoDenunciaAddetti());
		i.setCodCausCessaz(iWS.getCodCausCessaz());
		i.setCodCausCessazFunSede(iWS.getCodCausCessazFunSede());
		i.setCodFonte(iWS.getCodFonte());
		i.setCodiceFiscale(iWS.getCodiceFiscale());
		i.setCodNaturaGiuridica(iWS.getCodNaturaGiuridica());
		i.setDataCancellazRea(iWS.getDataCancellazRea());
		i.setDataCessaz(iWS.getDataCessaz());
		i.setDataCessazFunSede(iWS.getDataCessazFunSede());
		i.setDataDenunciaCessaz(iWS.getDataDenunciaCessaz());
		i.setDataDlbIscrAlboArtigiano(iWS.getDataDlbIscrAlboArtigiano());
		i.setDataInizioAtt(iWS.getDataInizioAtt());
		i.setDataIscrizioneRea(iWS.getDataIscrizioneRea());
		i.setDataIscrRegistroImpr(iWS.getDataIscrRegistroImpr());
		i.setDataUltimoAggiorn(iWS.getDataUltimoAggiorn());
		i.setDataUltimoAggRi(iWS.getDataUltimoAggRi());
		i.setDescrCausCessaz(iWS.getDescrCausCessaz());
		i.setDescrFonte(iWS.getDescrFonte());
		i.setDescrIndicStatoAttiv(iWS.getDescrIndicStatoAttiv());
		i.setDescrIndicTrasfSede(iWS.getDescrIndicTrasfSede());
		i.setDescrIterIscrAlboArt(iWS.getDescrIterIscrAlboArt());
		i.setDescrNaturaGiuridica(iWS.getDescrNaturaGiuridica());
		i.setFlgAggiornamento(iWS.getFlgAggiornamento());
		i.setFlgIterIscrAlboArt(iWS.getFlgIterIscrAlboArt());
		i.setIdAAEPAziendaSL(iWS.getIdAAEPAziendaSL());
		i.setIdAAEPFonteDatoSL(iWS.getIdAAEPFonteDatoSL());
		i.setImpresaCessata(iWS.getImpresaCessata());
		i.setIndicStatoAttiv(iWS.getIndicStatoAttiv());
		i.setIndicTrasfSede(iWS.getIndicTrasfSede());
		if (iWS.getListaSediInfoc() != null) {
			List<it.csi.comonl.comonlweb.profilazione.dto.ListaSediInfoc> listaSediInfoc = new ArrayList<>();
			for (ListaSediInfoc lWS : iWS.getListaSediInfoc()) {
				listaSediInfoc.add(converti(lWS));
			}
			i.setListaSediInfoc(listaSediInfoc);
		}
		i.setLocalizzazPiemonte(iWS.getLocalizzazPiemonte());
		i.setNumAddettiFam(iWS.getNumAddettiFam());
		i.setNumAddettiSubord(iWS.getNumAddettiSubord());
		i.setNumeroIscrAlboArtigiano(iWS.getNumeroIscrAlboArtigiano());
		i.setNumIscrizRea(iWS.getNumIscrizRea());
		i.setNumIscrizReaSede(iWS.getNumIscrizReaSede());
		i.setNumRegistroImpr(iWS.getNumRegistroImpr());
		i.setPartitaIva(iWS.getPartitaIva());
		i.setProgrSedeSL(iWS.getProgrSedeSL());
		i.setProvinciaIscrAlboArtigiano(iWS.getProvinciaIscrAlboArtigiano());
		i.setRagioneSociale(iWS.getRagioneSociale());
		i.setSiglaProvRea(iWS.getSiglaProvRea());
		i.setSiglaProvReaSede(iWS.getSiglaProvReaSede());
		return i;
	}

	/**
	 * Converte i dati restituiti dal servizio di AAEP in DTO di comonl.
	 */
	private it.csi.comonl.comonlweb.profilazione.dto.ListaSediInfoc converti(ListaSediInfoc lWS) {
		it.csi.comonl.comonlweb.profilazione.dto.ListaSediInfoc l = new it.csi.comonl.comonlweb.profilazione.dto.ListaSediInfoc();
		l.setDenominazioneSede(lWS.getDenominazioneSede());
		l.setDescrComuneUL(lWS.getDescrComuneUL());
		l.setDescrTipoLocalizzazione(lWS.getDescrTipoLocalizzazione());
		l.setIdAAEPAzienda(lWS.getIdAAEPAzienda());
		l.setIdAAEPFonteDato(lWS.getIdAAEPFonteDato());
		l.setIndirizzo(lWS.getIndirizzo());
		l.setNumIscrizREA(lWS.getNumIscrizREA());
		l.setProgrSede(lWS.getProgrSede());
		l.setSiglaProvCCIAA(lWS.getSiglaProvCCIAA());
		l.setSiglaProvUL(lWS.getSiglaProvUL());
		return l;
	}

	public AziendaAAEP cercaPerCodiceFiscaleAAEP(String codiceFiscale) throws Exception {
		logger.debug("[AdapterAAEP::cercaPerCodiceFiscale] BEGIN codiceFiscale=" + codiceFiscale, "");
		StopWatch watcher = new StopWatch(ComonlConstants.LOGGER);
		watcher.start();
		try {
			return getService().cercaPerCodiceFiscaleAAEP(codiceFiscale, "", "");
		} finally {
			watcher.dumpElapsed("AdapterAAEP", "cercaPerCodiceFiscale", "invocazione servizio AAEP", "");
			watcher.stop();
		}
	}

	public Sede cercaPuntualeSedeAAEP(String idSede) throws Exception {
		logger.debug("[AdapterAAEP::cercaPuntualeSedeAAEP] BEGIN idSede=" + idSede, "");
		StopWatch watcher = new StopWatch(ComonlConstants.LOGGER);
		watcher.start();
		try {
			return getService().cercaPuntualeSedeAAEP(idSede, "", "");
		} finally {
			watcher.dumpElapsed("AdapterAAEP", "cercaPuntualeSedeAAEP", "invocazione servizio AAEP", "");
			watcher.stop();
		}
	}

}
