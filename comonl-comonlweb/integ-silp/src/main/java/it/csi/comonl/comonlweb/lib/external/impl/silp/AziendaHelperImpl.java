/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION SILP submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.external.impl.silp;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPFaultException;

import it.csi.comonl.comonlweb.lib.dto.comunicazione.Toponimo;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.NaturaGiuridica;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;
import it.csi.comonl.comonlweb.lib.dto.silp.Sede;
import it.csi.comonl.comonlweb.lib.external.impl.silp.exception.ServiziSilpException;
import it.csi.comonl.comonlweb.lib.util.ComonlDateUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.silpsv.silpsvaa.cxfclient.AnagraficaAziende;
import it.csi.silpsv.silpsvaa.cxfclient.AnagraficaSediAziende;
import it.csi.silpsv.silpsvaa.cxfclient.ElencoAziende;
import it.csi.silpsv.silpsvaa.cxfclient.ElencoSedi;
import it.csi.silpsv.silpsvaa.cxfclient.EstrazioneAnagraficaAziende;
import it.csi.silpsv.silpsvaa.cxfclient.ServiziSilpException_Exception;

public class AziendaHelperImpl {
	private static final String TIPO_SEDE_LEGALE_1 = "1";
	private static final String TIPO_SEDE_OPERATIVA_2 = "2";
	protected final LogUtil log = new LogUtil(getClass());
	private Service s = null;

	private static final String CALLER_COMONL = "COMONL"; // configurato da Sacchi

	public DatiAzienda getAzienda(BigDecimal id) throws Exception {
		final String methodName = "getAzienda";
		DatiAzienda datiAzienda = null;

		try {
			EstrazioneAnagraficaAziende estrazioneAnagraficaAziende = getService();
			AnagraficaAziende anagraficaAziende = estrazioneAnagraficaAziende.visualizzaDettaglioAzienda(CALLER_COMONL,
					id.toString());
			datiAzienda = caricaDatiAzienda(anagraficaAziende);
			datiAzienda.setIdAziendaSilp(id.toString());

			setSedeLegale(methodName, datiAzienda, estrazioneAnagraficaAziende, id.toString());

			// aggiungere chiamata al servizio per la ricerca sedi dell'azienda
			datiAzienda.setElencoSedi(this.getElencoSediByIdAzienda(id.toString()));
		} catch (Exception e) {
			log.error(methodName, e);
			throw e;
		}
		return datiAzienda;
	}

	private DatiAzienda caricaDatiAzienda(AnagraficaAziende anagraficaAziende) {
		DatiAzienda datiAzienda;
		datiAzienda = new DatiAzienda();
		datiAzienda.setDenominazioneDatoreLavoro(anagraficaAziende.getRagioneSociale());
		datiAzienda.setCfAzienda(anagraficaAziende.getCodiceFiscale());
		datiAzienda.setPartitaIva(anagraficaAziende.getPartitaIva());
		setNaturaGiuridica(datiAzienda, anagraficaAziende);
		setAteco(datiAzienda, anagraficaAziende);
		setCcnl(datiAzienda, anagraficaAziende);
		datiAzienda.setFlagArtigiana(anagraficaAziende.getFlagArtigiana());
		datiAzienda.setCodLavoroTemp(anagraficaAziende.getCodLavoroTemp());
		datiAzienda.setFlagTipoAzienda(anagraficaAziende.getFlagTipoAzienda());
		datiAzienda.setFlagMaster(anagraficaAziende.getFlagMaster());
		datiAzienda.setDescrTipoAzienda(anagraficaAziende.getDescrTipoAzienda());
		datiAzienda.setAltreInformazioni(anagraficaAziende.getAltreInformazioni());
		datiAzienda.setFlgPubblicaAmm(anagraficaAziende.getFlagPubblicaAmm());
		return datiAzienda;
	}

	public DatiAzienda ricercaPerCodiceFiscale(String codiceFiscaleAzienda) throws ServiziSilpException {
		final String methodName = "ricercaPerCodiceFiscale";
		DatiAzienda datiAzienda = null;

		try {
			EstrazioneAnagraficaAziende estrazioneAnagraficaAziende = getService();

			ElencoAziende elencoAziendeFilter = new ElencoAziende();
			elencoAziendeFilter.setCodFiscale(codiceFiscaleAzienda);

			List<ElencoAziende> elencoAziendes = estrazioneAnagraficaAziende.ricercaElencoAziende(CALLER_COMONL,
					elencoAziendeFilter);

			if (elencoAziendes == null || elencoAziendes.isEmpty()) {
				log.error(methodName, "azienda non trovata: " + codiceFiscaleAzienda);
				return datiAzienda;
			}

			ElencoAziende elencoAziende = null;
			if (elencoAziendes.size() == 1) {
				// prendo l'unica trovata
				elencoAziende = elencoAziendes.get(0);
			} else if (elencoAziendes.size() > 1) {
				// Se ci sono più aziende senza flag Master il sistema non carica e segnala che
				// non è stata trovata azienda.
				// Se ci sono più azienda con flag master ne carica una
				for (var iterator = elencoAziendes.iterator(); iterator.hasNext();) {
					ElencoAziende elencoAziendeItem = iterator.next();
					if (elencoAziendeItem.getFlgMaster() != null) {
						if (elencoAziendeItem.getFlgMaster().equalsIgnoreCase("S")) {
							elencoAziende = elencoAziendeItem;
							break;
						}
					} else {
						elencoAziende = elencoAziendeItem;
					}
				}
			}

			if (elencoAziende == null) {
				log.error(methodName, "azienda master non trovata: " + codiceFiscaleAzienda);
				return datiAzienda;
			}

			// denominazione, settore ateco, ccnl
			AnagraficaAziende anagraficaAziende = estrazioneAnagraficaAziende.visualizzaDettaglioAzienda(CALLER_COMONL,
					elencoAziende.getIdAzienda());
			datiAzienda = caricaDatiAzienda(anagraficaAziende);
			datiAzienda.setIdAziendaSilp(elencoAziende.getIdAzienda());

			
			
			try {
				setSedeLegale(methodName, datiAzienda, estrazioneAnagraficaAziende, elencoAziende.getIdAzienda());
			}
			catch (Exception e) {
				log.error(methodName, e);
			}

			// aggiungere chiamata al servizio per la ricerca sedi dell'azienda
			try {
				datiAzienda.setElencoSedi(this.getElencoSediByIdAzienda(elencoAziende.getIdAzienda()));
			}
			catch (Exception e) {
				log.error(methodName, e);
			}

		} catch (ServiziSilpException_Exception e) {
			log.error(methodName, e);
			throw new ServiziSilpException(e.getFaultInfo().getCodice(),e.getFaultInfo().getDescrizione());
		}
		catch (IOException e) {
			log.error(methodName, e);
		}

		catch (SOAPFaultException e) {
			log.error(methodName, e);
		}

		return datiAzienda;
	}

	private void setSedeLegale(final String methodName, DatiAzienda datiAzienda,
			EstrazioneAnagraficaAziende estrazioneAnagraficaAziende, String idAzienda)
			throws ServiziSilpException_Exception {

		ElencoSedi elencoSediFilter = new ElencoSedi();
		elencoSediFilter.setIdAzienda(idAzienda);
		elencoSediFilter.setTipoSede(TIPO_SEDE_LEGALE_1); // estrae la sede legale (tipoSede = 1)
		List<ElencoSedi> elencoSedis = estrazioneAnagraficaAziende.ricercaSediAzienda(CALLER_COMONL, elencoSediFilter);

		if (elencoSedis == null || elencoSedis.isEmpty()) {
			log.error(methodName, "sede non trovata per idAzienda: " + idAzienda);
		} else {
			ElencoSedi laSedeLegaleRecuperataDaSilp = null;
			if (elencoSedis.size() > 1) {
				for (ElencoSedi laSedeLegaleDaControllareSeValida : elencoSedis) {
					/* Ricerca della sede valida tra le sedi legali restituite da silp */
					if (null != laSedeLegaleDaControllareSeValida.getNumPreferenza()
							&& (Long.parseLong(laSedeLegaleDaControllareSeValida.getNumPreferenza()) > 0)
							&& !laSedeLegaleDaControllareSeValida.getIdStatoSede().equals("3")
							&& (laSedeLegaleDaControllareSeValida.getDataFineAttivita() == null
									|| (ComonlUtility.confrontaData1MaggioreData2(
											laSedeLegaleDaControllareSeValida.getDataFineAttivita(),
											ComonlDateUtils.getCurrentDateGGMMAAAA())
											&& laSedeLegaleDaControllareSeValida.getDataFineAttivita() != null))) {
						laSedeLegaleRecuperataDaSilp = laSedeLegaleDaControllareSeValida;
						break;
					}
				}
			} else {
				laSedeLegaleRecuperataDaSilp = elencoSedis.get(0);
			}
			Sede laSedeLegale = new Sede();
			if (null != laSedeLegaleRecuperataDaSilp) {

				settaDatiSede(methodName, estrazioneAnagraficaAziende, laSedeLegaleRecuperataDaSilp, laSedeLegale);

				String indirizzo = laSedeLegaleRecuperataDaSilp.getDescrIndirizzoSede() + " "
						+ (laSedeLegaleRecuperataDaSilp.getDescrNumCivicoSede() == null ? ""
								: laSedeLegaleRecuperataDaSilp.getDescrNumCivicoSede());
				laSedeLegale.setIndirizzo(indirizzo);

				datiAzienda.setSedeLegale(laSedeLegale);
			} else {
				datiAzienda.setSedeLegale(null);
			}
		}
	}

	private void settaDatiSede(final String methodName, EstrazioneAnagraficaAziende estrazioneAnagraficaAziende,
			ElencoSedi laSedeLegaleRecuperataDaSilp, Sede laSedeLegale) throws ServiziSilpException_Exception {
		laSedeLegale.setComune(settaIlComune(laSedeLegaleRecuperataDaSilp));
		if (ComonlUtility.isNotVoid(laSedeLegaleRecuperataDaSilp.getIdNazioneSede())) {
			laSedeLegale.setStatiEsteri(settaLaNazione(laSedeLegaleRecuperataDaSilp));
		}
		settaIlTipoDiSede(methodName, laSedeLegaleRecuperataDaSilp, laSedeLegale);
		// cap (capSede)
		laSedeLegale.setCap(laSedeLegaleRecuperataDaSilp.getCapSede());
		Toponimo ilTopo = new Toponimo();
		ilTopo.setIdComTToponimo(laSedeLegaleRecuperataDaSilp.getIdToponimoSede());
		laSedeLegale.setToponimoSede(ilTopo);
		
		
		
		laSedeLegale.setEmail(laSedeLegaleRecuperataDaSilp.getEmail());
		laSedeLegale.setFax(laSedeLegaleRecuperataDaSilp.getFax());
		laSedeLegale.setTelefono(laSedeLegaleRecuperataDaSilp.getTelefono());
		laSedeLegale.setIdSedeSilp(laSedeLegaleRecuperataDaSilp.getIdSedeAzienda());
		
		laSedeLegale.setMotivoInvalida(laSedeLegaleRecuperataDaSilp.getMotivoValidita());
		laSedeLegale.setFlgValida(laSedeLegaleRecuperataDaSilp.getFlgValida());
		if (laSedeLegaleRecuperataDaSilp.getMotivoValidita() != null && laSedeLegaleRecuperataDaSilp.getMotivoValidita().trim().length()>0)
			laSedeLegale.setFlgValida("N");
		
		laSedeLegale.setPatInail(laSedeLegaleRecuperataDaSilp.getCodPatInail());
		laSedeLegale.setNumAgenziaSomministrazione(laSedeLegaleRecuperataDaSilp.getNumAgenziaSomministrazione());
	}


	private void setCcnl(DatiAzienda datiAzienda, AnagraficaAziende anagraficaAziende) {
		if (ComonlUtility.isNotVoid(anagraficaAziende.getIdCcnlAzienda())
				|| ComonlUtility.isNotVoid(anagraficaAziende.getDescrCcnlAzienda())) {
			Ccnl ccnl = new Ccnl();
			ccnl.setCodCcnlMin(anagraficaAziende.getIdCcnlAzienda());
			ccnl.setDsCcnl(anagraficaAziende.getDescrCcnlAzienda());
			datiAzienda.setCcnl(ccnl);
		}
	}

	private void setAteco(DatiAzienda datiAzienda, AnagraficaAziende anagraficaAziende) {
		if (ComonlUtility.isNotVoid(anagraficaAziende.getCodMinAteco())
				|| ComonlUtility.isNotVoid(anagraficaAziende.getDescrAteco())) {
			Atecofin atecofin = new Atecofin();
			atecofin.setCodAtecofinMin(anagraficaAziende.getCodMinAteco());
			atecofin.setDsComTAtecofin(anagraficaAziende.getDescrAteco());
			datiAzienda.setAtecofin(atecofin);
		}
	}

	private void setNaturaGiuridica(DatiAzienda datiAzienda, AnagraficaAziende anagraficaAziende) {
		if (ComonlUtility.isNotVoid(anagraficaAziende.getIdNaturaGiuridica())
				|| ComonlUtility.isNotVoid(anagraficaAziende.getDescrNaturaGiuridica())) {
			NaturaGiuridica laNaturaGiuridica = new NaturaGiuridica();
			laNaturaGiuridica.setId(anagraficaAziende.getIdNaturaGiuridica());
			laNaturaGiuridica.setDescrizione(anagraficaAziende.getDescrNaturaGiuridica());
			datiAzienda.setNaturaGiuridica(laNaturaGiuridica);
		}
	}

	public EstrazioneAnagraficaAziende getService() throws IOException {
		if (s == null) {
			s = Service.create(getClass().getResource("/silpsv.silpsvaa.wsdl"),
					new QName("urn:silpsvaa", "EstrazioneAnagraficaAziendeService"));
		}
		EstrazioneAnagraficaAziende service = s.getPort(EstrazioneAnagraficaAziende.class);
		BindingProvider bp = (BindingProvider) service;
		Map<String, Object> context = bp.getRequestContext();
		context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getEndpoint());

		return service;
	}

	public String getEndpoint() throws IOException {
		final String SILP_RESOURCE = "/silp.properties";
		InputStream is = this.getClass().getResourceAsStream(SILP_RESOURCE);
		Properties properties = new Properties();
		properties.load(is);
		return properties.getProperty("url_aa");
	}

	public List<Sede> getElencoSediByIdAzienda(String idAzienda) {
		final String methodName = "elencoSediByIdAzienda";
		List<Sede> elencoDelleSediDaVisualizzare = new ArrayList<>();

		try {
			EstrazioneAnagraficaAziende estrazioneAnagraficaAziende = getService();

			ElencoSedi elencoSediFilter = new ElencoSedi();
			elencoSediFilter.setIdAzienda(idAzienda);

			List<ElencoSedi> elencoSedis = estrazioneAnagraficaAziende.ricercaSediAzienda(CALLER_COMONL,
					elencoSediFilter);
	
			if (elencoSedis == null || elencoSedis.isEmpty()) {
				log.error(methodName, "sede non trovata per idAzienda: " + idAzienda);
			} else {
				for (ElencoSedi laSedeRecuperataDaSilp : elencoSedis) {
					Sede sede = new Sede();
					settaDatiSede(methodName, estrazioneAnagraficaAziende, laSedeRecuperataDaSilp, sede);
					sede.setIndirizzo(laSedeRecuperataDaSilp.getDescrIndirizzoSede());
					sede.setNumeroCivico(laSedeRecuperataDaSilp.getDescrNumCivicoSede());
					elencoDelleSediDaVisualizzare.add(sede);
				}
			}

		} catch (ServiziSilpException_Exception | IOException e) {
			log.error(methodName, e);
		}

		return elencoDelleSediDaVisualizzare;
	}

	private StatiEsteri settaLaNazione(ElencoSedi laSedeSilp) {
		StatiEsteri loStatoEstero = new StatiEsteri();
		loStatoEstero.setDsStatiEsteri(laSedeSilp.getDescrNazioneSede());
		loStatoEstero.setCodNazioneMin(laSedeSilp.getIdNazioneSede());
		return loStatoEstero;
	}

	private void settaIlTipoDiSede(final String methodName, ElencoSedi laSedeSilp, Sede sede) {
		switch (laSedeSilp.getTipoSede()) {
		case TIPO_SEDE_LEGALE_1:
			sede.setSedeLegale(true);
			sede.setSedeOperativa(false);
			break;
		case TIPO_SEDE_OPERATIVA_2:
			sede.setSedeOperativa(true);
			sede.setSedeLegale(false);
			break;
		default:
			log.error(methodName, "TIPO DELLA SEDE NON RICONOSCIUTO : " + laSedeSilp.getTipoSede());
			break;
		}
	}

	private Comune settaIlComune(ElencoSedi laSede) {
		// comune (descrComuneSede, idComuneSede)
		Comune comune = new Comune();
		comune.setDsComTComune(laSede.getDescrComuneSede());
		comune.setCodComuneMin(laSede.getIdComuneSede());
		return comune;
	}

	public Sede putSede(String idAzienda, Sede sede) {
		final String methodName = "putSedeLegale";
		Sede sedeOut = null;
		try {
			EstrazioneAnagraficaAziende estrazioneAnagraficaAziende = getService();

			AnagraficaSediAziende anagraficaSede = new AnagraficaSediAziende();
			anagraficaSede.setCapSede(sede.getCap());

			anagraficaSede.setCodUser(sede.getCodUserAggiorn());
			// anagraficaSede.setCodUser( sede.getCodUserInserim() );
			if (sede.getComune() != null) {
				anagraficaSede.setDescrComuneSede(sede.getComune().getDsComTComune());
				anagraficaSede.setIdComuneSede(sede.getComune().getCodComuneMin());
			}
			// sede.getDAggiorn() ;
			// sede.getDatiAzienda() ;
			anagraficaSede.setEMail(sede.getEmail());
			anagraficaSede.setFax(sede.getFax());
			anagraficaSede.setTelefono(sede.getTelefono());

			if (sede.getId() != null) {
				anagraficaSede.setIdSedeAzienda(sede.getId().toString());
			}
			anagraficaSede.setDescrIndirizzoSede(sede.getIndirizzo());
			if (sede.getStatiEsteri() != null) {
				anagraficaSede.setIdNazioneSede(sede.getStatiEsteri().getCodNazioneMin());
				anagraficaSede.setDescrNazioneSede(sede.getStatiEsteri().getDsStatiEsteri());
			}

			anagraficaSede.setIdAzienda(idAzienda);
			// etc
			AnagraficaSediAziende anagraficaSedeOut = estrazioneAnagraficaAziende.modificaSede(CALLER_COMONL,
					anagraficaSede);

			if (anagraficaSedeOut == null || anagraficaSedeOut.getIdSedeAzienda() == null) {
				log.error(methodName, "sede non inserita ");

			} else {
				sedeOut = new Sede();
				sedeOut.setCap(anagraficaSedeOut.getCapSede());
				sedeOut.setCodUserInserim(anagraficaSedeOut.getCodUser());

				Comune com = new Comune();
				com.setDsComTComune(anagraficaSedeOut.getDescrComuneSede());
				com.setCodComuneMin(anagraficaSedeOut.getIdComuneSede());
				sedeOut.setComune(com);

				sedeOut.setEmail(anagraficaSedeOut.getEMail());
				sedeOut.setFax(anagraficaSedeOut.getFax());
				sedeOut.setTelefono(anagraficaSedeOut.getTelefono());

				sedeOut.setId(Integer.valueOf(anagraficaSedeOut.getIdSedeAzienda()));
				sedeOut.setIndirizzo(anagraficaSedeOut.getDescrIndirizzoSede());

				StatiEsteri stat = new StatiEsteri();
				stat.setCodNazioneMin(anagraficaSedeOut.getIdNazioneSede());
				stat.setDsStatiEsteri(anagraficaSedeOut.getDescrNazioneSede());
				sedeOut.setStatiEsteri(stat);

				DatiAzienda azi = new DatiAzienda();
				azi.setIdAziendaSilp(anagraficaSedeOut.getIdAzienda());

				sedeOut.setDatiAzienda(azi);

			}
		} catch (ServiziSilpException_Exception | IOException e) {
			log.error(methodName, e);
		}
		return sedeOut;
	}

	public DatiAzienda putDatiAzienda(DatiAzienda azienda) {
		final String methodName = "putDatiAzienda";
		DatiAzienda out = null;
		try {
			EstrazioneAnagraficaAziende estrazioneAnagraficaAziende = getService();

			AnagraficaAziende anagraficaIn = new AnagraficaAziende();
			// DatiAzienda -->> AnagraficaAziende
			// etc
			AnagraficaAziende anOut = estrazioneAnagraficaAziende.modificaAzienda(CALLER_COMONL, anagraficaIn);

			if (anOut == null || anOut.getIdAzienda() == null) {
				log.error(methodName, "azienda non inserita ");

			} else {
				out = new DatiAzienda();

			}
		} catch (ServiziSilpException_Exception | IOException e) {
			log.error(methodName, e);
		}
		return out;
	}
}
