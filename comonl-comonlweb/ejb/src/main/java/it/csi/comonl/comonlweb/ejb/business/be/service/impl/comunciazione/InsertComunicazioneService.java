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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LegaleRapprDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoLavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.TutoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostInviaComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostInviaComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.LegaleRappr;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportiLavoratoriSediInteressateVD;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportoLavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportoLavoratorePK;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Tutore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoLavoratore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoRapportoAzienda;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class InsertComunicazioneService
		extends BaseComunicazioneService<PostInviaComunicazioneRequest, PostInviaComunicazioneResponse> {

	private DatoreDad datoreDad;
	private LegaleRapprDad legaleRapprDad;
	private SedeLavoroDad sedeLavoroDad;
	private RapportoDad rapportoDad;
	private LavoratoreDad lavoratoreDad;
	private RapportoLavoratoreDad rapportoLavoratoreDad;
	private TutoreDad tutoreDad;

	public InsertComunicazioneService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad,
			DatoreDad datoreDad,LegaleRapprDad legaleRapprDad, SedeLavoroDad sedeLavoroDad, RapportoDad rapportoDad,
			LavoratoreDad lavoratoreDad, RapportoLavoratoreDad rapportoLavoratoreDad, TutoreDad tutoreDad) {
		super(configurationHelper, comunicazioneDad);
		this.datoreDad = datoreDad;
		this.legaleRapprDad = legaleRapprDad;
		this.sedeLavoroDad = sedeLavoroDad;
		this.rapportoDad = rapportoDad;
		this.lavoratoreDad = lavoratoreDad;
		this.rapportoLavoratoreDad = rapportoLavoratoreDad;
		this.tutoreDad = tutoreDad;

	}

	@Override
	protected void checkServiceParams() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void execute() {
		throw new UnsupportedOperationException();
	}

	public Comunicazione insertComunicazione(Comunicazione comunicazioneLav) {

		// INSERT COMUNICAZIONE
		comunicazioneLav.setId(null);
		
		// IMPOSTA LA DATA RIFERIMENTO / IDRAPPORTO (solo se provenienza COMMAX)
		impostaDataRiferimento(comunicazioneLav);
		impostaIdRapporto(comunicazioneLav);
		
		Comunicazione comunicazionePers = comunicazioneDad.insertComunicazione(comunicazioneLav);
		comunicazioneLav.setId(comunicazionePers.getId());
		comunicazioneLav.setDtInsert(comunicazionePers.getDtInsert());
		comunicazioneLav.setIdUserInsert(comunicazionePers.getIdUserInsert());
		comunicazioneLav.setIdRapportoLavoro(comunicazionePers.getIdRapportoLavoro());
		
		// DATORE
		comunicazioneLav.getDatoreAttuale().setId(null);
		LegaleRappr legaleRappr = comunicazioneLav.getDatoreAttuale().getLegaleRappr();
		if (legaleRappr != null ) {
			legaleRappr.setId(null);
		}
		Datore datore = datoreDad.insertDatore(comunicazioneLav.getDatoreAttuale(), comunicazioneLav.getId(),
				ComonlConstants.TIPO_DATORE_ATTUALE_ID);
		if (legaleRappr != null) {
			legaleRappr.setId(datore.getId());
			legaleRapprDad.insert(legaleRappr);
		}
//		// SEDE LEGALE
		SedeLavoro sedeLegale = comunicazioneLav.getDatoreAttuale().getSedeLegale();
		if (sedeLegale != null) {
			sedeLegale.setEmail(trim(sedeLegale.getEmail()));

			if ("".equalsIgnoreCase(sedeLegale.getFlgSedeLegale()) || sedeLegale.getFlgSedeLegale() == null) {
				sedeLegale.setFlgSedeLegale(ComonlConstants.FLAG_S);
			}

			sedeLegale.setId(null);
			sedeLegale = sedeLavoroDad.insertSedeLavoro(sedeLegale, datore.getId());
		}
		// SEDE OPERATIVA
		SedeLavoro sedeOperativa = comunicazioneLav.getDatoreAttuale().getSedeOperativa();
		if (sedeOperativa != null) {
			sedeOperativa.setEmail(trim(sedeOperativa.getEmail()));
			if ("".equalsIgnoreCase(sedeOperativa.getFlgSedeLegale()) || sedeOperativa.getFlgSedeLegale() == null) {
				sedeOperativa.setFlgSedeLegale(ComonlConstants.FLAG_N);
			}
			sedeOperativa.setId(null);
			sedeOperativa = sedeLavoroDad.insertSedeLavoro(sedeOperativa, datore.getId());
		}

		// TRASFERIMENTO
		if (comunicazioneLav.getRapporto().getSedeLavoroPrecedente() != null) {
			comunicazioneLav.getRapporto().getSedeLavoroPrecedente().setId(null);
			SedeLavoro sedeLavoroPrecedente = sedeLavoroDad
					.insertSedeLavoro(comunicazioneLav.getRapporto().getSedeLavoroPrecedente());
			comunicazioneLav.getRapporto().getSedeLavoroPrecedente().setId(sedeLavoroPrecedente.getId());
		}
		// DATORE DISTACCATARIO
		if (comunicazioneLav.getRapporto().getDatoreDistaccatario() != null) {
			comunicazioneLav.getRapporto().getDatoreDistaccatario().setId(null);
			Datore datoreDistaccatario = datoreDad
					.insertDatore(comunicazioneLav.getRapporto().getDatoreDistaccatario());
			
			
			if (comunicazioneLav.getRapporto().getDatoreDistaccatario().getSedeLegale() != null) {
				comunicazioneLav.getRapporto().getDatoreDistaccatario().getSedeLegale().setId(null);
				sedeLegale = sedeLavoroDad.insertSedeLavoro(
						comunicazioneLav.getRapporto().getDatoreDistaccatario().getSedeLegale(),
						datoreDistaccatario.getId());
				comunicazioneLav.getRapporto().getDatoreDistaccatario().setId(datoreDistaccatario.getId());
			}
			else if (comunicazioneLav.getRapporto().getDatoreDistaccatario().getSedeOperativa() != null) {
				
				SedeLavoro operativa = comunicazioneLav.getRapporto().getDatoreDistaccatario().getSedeOperativa();
				operativa.setId(null);
				sedeLegale = sedeLavoroDad.insertSedeLavoro(
						operativa,
						datoreDistaccatario.getId());
				comunicazioneLav.getRapporto().getDatoreDistaccatario().setId(datoreDistaccatario.getId());
				
			}
			else throw new RuntimeException("DATORE DISTACCATARIO non ha ne la sede legale ne la sede operativa");
		}
		
		// RAPPORTO
		Rapporto rapporto = comunicazioneLav.getRapporto();
		rapporto.setId(null);
		Comunicazione c = new Comunicazione();
		c.setId(comunicazioneLav.getId());
		if (rapporto.getDtEvento()==null) {
			rapporto.setDtEvento(comunicazioneLav.getDataRiferimento());
		}
		rapporto.setComunicazione(c);
		
		// TUTORE
		if(rapporto.getTutore()!=null) {
			rapporto.getTutore().setId(null);
			Tutore tutore = tutoreDad.insertTutore(rapporto.getTutore());
			rapporto.setTutore(tutore);
		}
		
		// MISSIONE E SUOI DATI
		boolean insertRapportoMissione = comunicazioneLav.getMissione() != null && comunicazioneLav.getMissione().getDtInizioMissione() != null;
		if (comunicazioneLav.getMissione() != null) {

			// TRASFERIMENTO
			if (comunicazioneLav.getMissione().getSedeLavoroPrecedente() != null) {
				comunicazioneLav.getMissione().getSedeLavoroPrecedente().setId(null);
				SedeLavoro sedeLavoroPrecedente = sedeLavoroDad
						.insertSedeLavoro(comunicazioneLav.getMissione().getSedeLavoroPrecedente());
				comunicazioneLav.getMissione().getSedeLavoroPrecedente().setId(sedeLavoroPrecedente.getId());
			}

			// DATORE DISTACCATARIO
			if (comunicazioneLav.getMissione().getDatoreDistaccatario() != null) {
				comunicazioneLav.getMissione().getDatoreDistaccatario().setId(null);
				Datore datoreDistaccatario = datoreDad
						.insertDatore(comunicazioneLav.getMissione().getDatoreDistaccatario());
				comunicazioneLav.getMissione().getDatoreDistaccatario().getSedeLegale().setId(null);
				sedeLegale = sedeLavoroDad.insertSedeLavoro(
						comunicazioneLav.getMissione().getDatoreDistaccatario().getSedeLegale(),
						datoreDistaccatario.getId());
				comunicazioneLav.getMissione().getDatoreDistaccatario().setId(datoreDistaccatario.getId());
			}
			// AZI UTILIZZATRICE
			if (comunicazioneLav.getMissione().getAziUtilizzatrice() != null) {
				Datore aziUtilizzatrice = comunicazioneLav.getMissione().getAziUtilizzatrice();
				aziUtilizzatrice.setId(null);
				aziUtilizzatrice = datoreDad.insertDatore(aziUtilizzatrice);
				
				// SEDE LEGALE
				if (comunicazioneLav.getMissione().getAziUtilizzatrice().getSedeLegale() != null) {
					comunicazioneLav.getMissione().getAziUtilizzatrice().getSedeLegale().setId(null);
					comunicazioneLav.getMissione().getAziUtilizzatrice().getSedeLegale().setFlgSedeLegale(ComonlConstants.FLAG_S);
					sedeLavoroDad.insertSedeLavoro(comunicazioneLav.getMissione().getAziUtilizzatrice().getSedeLegale(),
						aziUtilizzatrice.getId());
				}
				// SEDE OPERATIVA (se presente)
				if (comunicazioneLav.getMissione().getAziUtilizzatrice().getSedeOperativa() != null) {
					comunicazioneLav.getMissione().getAziUtilizzatrice().getSedeOperativa().setFlgSedeLegale(ComonlConstants.FLAG_N);
					comunicazioneLav.getMissione().getAziUtilizzatrice().getSedeOperativa().setId(null);
					sedeLavoroDad.insertSedeLavoro(
							comunicazioneLav.getMissione().getAziUtilizzatrice().getSedeOperativa(),
							aziUtilizzatrice.getId());
				}
				if (insertRapportoMissione) {
					comunicazioneLav.getMissione().setAziUtilizzatrice(aziUtilizzatrice);
				}else {
					rapporto.setAziUtilizzatrice(aziUtilizzatrice);
				}
			}
		}

		// RAPPORTO
		rapporto = rapportoDad.insertRapporto(rapporto);
		
		// LAVORATORE COOBBLIGATO
		if (comunicazioneLav.getLavoratoreCoObbligato() != null) {
			comunicazioneLav.getLavoratoreCoObbligato().setId(null);
			comunicazioneLav.getLavoratoreCoObbligato().setNome(trim(comunicazioneLav.getLavoratoreCoObbligato().getNome()));
			comunicazioneLav.getLavoratoreCoObbligato().setCognome(trim(comunicazioneLav.getLavoratoreCoObbligato().getCognome()));
			comunicazioneLav.getLavoratoreCoObbligato().setCodCapRes(trim(comunicazioneLav.getLavoratoreCoObbligato().getCodCapRes()));
			comunicazioneLav.getLavoratoreCoObbligato().setCodCapDom(trim(comunicazioneLav.getLavoratoreCoObbligato().getCodCapDom()));
			Lavoratore lav = lavoratoreDad.insertLavoratore(comunicazioneLav.getLavoratoreCoObbligato(), rapporto.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P, ComonlConstants.TIPO_LAVORATORE_C);
			lavoratoreDad.insertSedeLavoroLavoratore(lav.getId(), sedeOperativa.getId());
		}

		// LAVORATORE
		List<Lavoratore> lavoratoriInsert = new ArrayList<Lavoratore>();
		if (comunicazioneLav.getLavoratori() != null) {
			for (Lavoratore lav : comunicazioneLav.getLavoratori()) {
				lav.setId(null);
				lav.setNome(trim(lav.getNome()));
				lav.setCognome(trim(lav.getCognome()));
				lav.setCodCapRes(trim(lav.getCodCapRes()));
				lav.setCodCapDom(trim(lav.getCodCapDom()));
				lav = lavoratoreDad.insertLavoratore(lav, rapporto.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P,
						ComonlConstants.TIPO_LAVORATORE_P);
				lavoratoreDad.insertSedeLavoroLavoratore(lav.getId(), sedeOperativa.getId());
				lavoratoriInsert.add(lav);
			}
		}
		if (insertRapportoMissione) {
			
			comunicazioneLav.getMissione().setId(null);
			comunicazioneLav.getMissione().setComunicazione(c);
			Rapporto rapportoM = rapportoDad.insertRapporto(comunicazioneLav.getMissione());

			for (Lavoratore lav : lavoratoriInsert) {
				RapportoLavoratore rapportoLavoratore = new RapportoLavoratore();
				RapportoLavoratorePK idRapportoLavoratore = new RapportoLavoratorePK();
				idRapportoLavoratore.setIdComDLavoratore(lav.getId());
				idRapportoLavoratore.setIdComDRapporto(rapportoM.getId());
				rapportoLavoratore.setId(idRapportoLavoratore);
				TipoRapportoAzienda tipoRapportoAzienda = new TipoRapportoAzienda();
				tipoRapportoAzienda.setId(ComonlConstants.TIPO_RAPPORTO_AZIENDA_M);
				rapportoLavoratore.setTipoRapportoAzienda(tipoRapportoAzienda);
				TipoLavoratore tipoLavoratore = new TipoLavoratore();
				tipoLavoratore.setId(ComonlConstants.TIPO_LAVORATORE_P);
				rapportoLavoratore.setTipoLavoratore(tipoLavoratore);
				rapportoLavoratoreDad.insertRapportoLavoratore(rapportoLavoratore);
			}
			
		}
		return comunicazioneLav;
	}


	
	public Comunicazione insertComunicazioneSemplice(Comunicazione comunicazione) {
		boolean inSomministrazioneConMissione = comunicazione.getTipoSomministrazione()!= null && (
				comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE || comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE);
		boolean isAssunzione = comunicazione.getTipoComunicazione()!=null && comunicazione.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE);
		
		if(isAssunzione && !inSomministrazioneConMissione) {
			comunicazione.setDataRiferimento(comunicazione.getRapporto().getDtInizioRapporto());
		}
		if(isAssunzione && inSomministrazioneConMissione) {
			Rapporto rapportoM = null;
			if(comunicazione.getIdComDComunicPrecedente()!=null) {
				 rapportoM = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getIdComDComunicPrecedente(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_M);
			}
			if (rapportoM == null) {
				comunicazione.setDataRiferimento(comunicazione.getRapporto().getDtInizioRapporto());
			}
		}
		
		// INSERT COMUNICAZIONE
		comunicazione.setFlgCurrentRecord(ComonlConstants.FLAG_S);
		Comunicazione comunicazionePersist = comunicazioneDad.insertComunicazione(comunicazione);
		// DATORE
		Datore datore = datoreDad.insertDatore(comunicazione.getDatoreAttuale(), comunicazionePersist.getId(), ComonlConstants.TIPO_DATORE_ATTUALE_ID);
		LegaleRappr legaleRappr = comunicazione.getDatoreAttuale().getLegaleRappr();
		legaleRappr.setId(datore.getId());
		legaleRapprDad.insert(legaleRappr);
		
		//SEDE LEGALE
		SedeLavoro sedeLegale = comunicazione.getDatoreAttuale().getSedeLegale();
		sedeLegale.setFlgSedeLegale(ComonlConstants.FLAG_S);
		sedeLegale.setEmail(trim(sedeLegale.getEmail()));
		sedeLegale = sedeLavoroDad.insertSedeLavoro(sedeLegale, datore.getId());
		
		// SEDE OPERATIVA
		comunicazione.getDatoreAttuale().getSedeOperativa().setFlgSedeLegale(ComonlConstants.FLAG_N);
		comunicazione.getDatoreAttuale().getSedeOperativa().setEmail(trim(comunicazione.getDatoreAttuale().getSedeOperativa().getEmail()));
		SedeLavoro sedeOperativa = sedeLavoroDad.insertSedeLavoro(comunicazione.getDatoreAttuale().getSedeOperativa(), datore.getId());
				
		Rapporto rapporto = comunicazione.getRapporto();

		// AZIENDA UTILIZZATRICE (CDU 08)
		if (inSomministrazioneConMissione) {
			Rapporto missione = comunicazione.getMissione();
			checkBusinessCondition( missione.getAziUtilizzatrice() != null, MsgComonl.COMCOMP0002.getError());// definire un messagio di errore appropriato
			
			Datore aziUtilizzatrice = datoreDad.insertDatore(missione.getAziUtilizzatrice());
			
			if (missione.getAziUtilizzatrice().getSedeLegale()!=null &&
					("".equalsIgnoreCase(missione.getAziUtilizzatrice().getSedeLegale().getFlgSedeLegale()) || missione.getAziUtilizzatrice().getSedeLegale().getFlgSedeLegale() == null)) {
				missione.getAziUtilizzatrice().getSedeLegale().setFlgSedeLegale(ComonlConstants.FLAG_S);
			}
			sedeLavoroDad.insertSedeLavoro(missione.getAziUtilizzatrice().getSedeLegale(), aziUtilizzatrice.getId());
			
			if (missione.getAziUtilizzatrice().getSedeOperativa()!=null) {
				missione.getAziUtilizzatrice().getSedeOperativa().setFlgSedeLegale(ComonlConstants.FLAG_N);
				sedeLavoroDad.insertSedeLavoro(missione.getAziUtilizzatrice().getSedeOperativa(), aziUtilizzatrice.getId());
			}
			
			Datore au = new Datore();
			au.setId(aziUtilizzatrice.getId());
			rapporto.setAziUtilizzatrice(au);// a questo punto l'idAziUtilizzatrice è salvata sul rapporto Principale, al salvataggio del rapporto Missione sarà ribaltato e pulito il campo
		}
		
		// RAPPORTO
		Comunicazione c = new Comunicazione();
		c.setId(comunicazionePersist.getId());
		rapporto.setComunicazione(c);
		rapporto = rapportoDad.insertRapporto(rapporto);
		
		// LAVORATORI
		for(Lavoratore lavoratore : comunicazione.getLavoratori()) {
			lavoratore.setCodCapRes(trim(lavoratore.getCodCapRes()));
			lavoratore.setCodCapDom(trim(lavoratore.getCodCapDom()));
			Lavoratore lav = lavoratoreDad.insertLavoratore(lavoratore, rapporto.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P, ComonlConstants.TIPO_LAVORATORE_P);
			lavoratoreDad.insertSedeLavoroLavoratore(lav.getId(), sedeOperativa.getId());
		}
		if (comunicazione.getLavoratoreCoObbligato()!=null) {
			comunicazione.getLavoratoreCoObbligato().setCodCapRes(trim(comunicazione.getLavoratoreCoObbligato().getCodCapRes()));
			comunicazione.getLavoratoreCoObbligato().setCodCapDom(trim(comunicazione.getLavoratoreCoObbligato().getCodCapDom()));
			Lavoratore lav = lavoratoreDad.insertLavoratore(comunicazione.getLavoratoreCoObbligato(), rapporto.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P, ComonlConstants.TIPO_LAVORATORE_C);
			lavoratoreDad.insertSedeLavoroLavoratore(lav.getId(), sedeOperativa.getId());
		}
		return comunicazionePersist;
	}
	
	public Comunicazione insertComunicazioneSempliceVardatore(Comunicazione comunicazione) {
		
		// INSERT COMUNICAZIONE
		comunicazione.setFlgCurrentRecord(ComonlConstants.FLAG_S);
		Comunicazione comunicazionePersist = comunicazioneDad.insertComunicazione(comunicazione);
		// DATORE
		Datore datore = datoreDad.insertDatore(comunicazione.getDatoreAttuale(), comunicazionePersist.getId(), ComonlConstants.TIPO_DATORE_ATTUALE_ID);
		LegaleRappr legaleRappr = comunicazione.getDatoreAttuale().getLegaleRappr();
		legaleRappr.setId(datore.getId());
		legaleRapprDad.insert(legaleRappr);
		
		//SEDE LEGALE
		SedeLavoro sedeLegale = comunicazione.getDatoreAttuale().getSedeLegale();
		sedeLegale.setFlgSedeLegale(ComonlConstants.FLAG_S);
		sedeLegale.setEmail(trim(sedeLegale.getEmail()));
		sedeLegale = sedeLavoroDad.insertSedeLavoro(sedeLegale, datore.getId());
		
		// VARIAZIONE TRASFERIMENTO
		if(StringUtils.isBlank(datore.getDsVariazioneRagSociale()))
		{
			// DATORE PRECEDENTE
			Datore datorePrecedente = datoreDad.insertDatore(comunicazione.getDatorePrecedente(), comunicazionePersist.getId(), ComonlConstants.TIPO_DATORE_PRECEDENTE_ID);
			//SEDE LEGALE
			comunicazione.getDatorePrecedente().getSedeLegale().setFlgSedeLegale(ComonlConstants.FLAG_S);
			sedeLavoroDad.insertSedeLavoro(comunicazione.getDatorePrecedente().getSedeLegale(), datorePrecedente.getId());

			Map<SedeLavoro, List<RapportiLavoratoriSediInteressateVD>> mapIdSilp =
					comunicazione.getRapLavSedeVD().stream().collect(Collectors.groupingBy(a-> a.getSedeLavoroVD()));
			for(Map.Entry<SedeLavoro, List<RapportiLavoratoriSediInteressateVD>> entry : mapIdSilp.entrySet()) {
				SedeLavoro sedeOperativa = sedeLavoroDad.insertSedeLavoro(entry.getKey(), datore.getId());
				for(RapportiLavoratoriSediInteressateVD value : entry.getValue()) {

					Rapporto rapporto = value.getRapportoVD();
					rapporto.setComunicazione(comunicazionePersist);
					rapporto.setDtEvento(comunicazione.getDtTrasferimentoVarDatori());
					rapporto = rapportoDad.insertRapporto(rapporto);
					Lavoratore lavoratore = value.getLavoratoreVD();
					lavoratore.setCodCapRes(trim(lavoratore.getCodCapRes()));
					lavoratore.setCodCapDom(trim(lavoratore.getCodCapDom()));
					lavoratore = lavoratoreDad.insertLavoratore(lavoratore, rapporto.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P, ComonlConstants.TIPO_LAVORATORE_P);
					lavoratoreDad.insertSedeLavoroLavoratore(lavoratore.getId(), sedeOperativa.getId());
				}
			}
		}
		return comunicazionePersist;
	}
	
	
	public Comunicazione insertComunicazioneUrgenza(Comunicazione comunicazione) {
		
		// INSERT COMUNICAZIONE
		comunicazione.setFlgCurrentRecord(ComonlConstants.FLAG_S);
		Comunicazione comunicazionePersist = comunicazioneDad.insertComunicazione(comunicazione);
		// DATORE
		Datore datore = datoreDad.insertDatore(comunicazione.getDatoreAttuale(), comunicazionePersist.getId(), ComonlConstants.TIPO_DATORE_ATTUALE_ID);
		
		// SEDE OPERATIVA
		SedeLavoro sedeOperativa = comunicazione.getDatoreAttuale().getSedeOperativa();
		sedeOperativa.setId(null);
		sedeOperativa.setEmail(trim(sedeOperativa.getEmail()));
		sedeOperativa.setFlgSedeLegale(ComonlConstants.FLAG_N);
		sedeOperativa = sedeLavoroDad.insertSedeLavoro(sedeOperativa, datore.getId());
		
		// RAPPORTO
		Rapporto rapporto = comunicazione.getRapporto();
		rapporto.setComunicazione(comunicazionePersist); 
		rapporto = rapportoDad.insertRapporto(rapporto);
		
		// LAVORATORI
		for(Lavoratore lavoratore : comunicazione.getLavoratori()) {
			lavoratore.setCodCapRes(trim(lavoratore.getCodCapRes()));
			lavoratore.setCodCapDom(trim(lavoratore.getCodCapDom()));
			Lavoratore lav = lavoratoreDad.insertLavoratore(lavoratore, rapporto.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P, ComonlConstants.TIPO_LAVORATORE_P);
			lavoratoreDad.insertSedeLavoroLavoratore(lav.getId(), sedeOperativa.getId());
		}
		
		return comunicazionePersist;
	}
	
	private void impostaDataRiferimento(Comunicazione comunicazione) {
		boolean inSomministrazioneConMissione = comunicazione.getTipoSomministrazione()!= null && (
				comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE || comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE);
		boolean isAssunzione = comunicazione.getTipoComunicazione()!=null && comunicazione.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE);
		boolean isCessazione = comunicazione.getTipoComunicazione()!=null && comunicazione.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_CESSAZIONE);
		boolean isTrasferimento = comunicazione.getTipoComunicazione()!=null && comunicazione.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO);
		boolean isTrasformazione = comunicazione.getTipoComunicazione()!=null && comunicazione.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE);
		boolean isProroga = comunicazione.getTipoComunicazione()!=null && comunicazione.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_PROROGA);
		
		if(isAssunzione && !inSomministrazioneConMissione) {
			comunicazione.setDataRiferimento(comunicazione.getRapporto().getDtInizioRapporto());
		}
		if(isAssunzione && inSomministrazioneConMissione) {
			Rapporto rapportoM = null;
			if(comunicazione.getIdComDComunicPrecedente()!=null) {
				 rapportoM = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getIdComDComunicPrecedente(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_M);
			}
			if (rapportoM == null) {
				comunicazione.setDataRiferimento(comunicazione.getRapporto().getDtInizioRapporto());
			}
		}
		
		if (isCessazione && !inSomministrazioneConMissione){
			comunicazione.setDataRiferimento(comunicazione.getRapporto().getDtCessazione());
		}
		if (isCessazione && inSomministrazioneConMissione && comunicazione.getMissione()!=null){
			comunicazione.setDataRiferimento(comunicazione.getMissione().getDtCessazione());
		}
		if ((isTrasferimento ||isTrasformazione) && !inSomministrazioneConMissione){
			comunicazione.setDataRiferimento(comunicazione.getRapporto().getDtTrasformazione());
		}
		if ((isTrasferimento ||isTrasformazione) && inSomministrazioneConMissione  && comunicazione.getMissione()!=null){
			comunicazione.setDataRiferimento(comunicazione.getMissione().getDtTrasformazione());
		}
		if (isProroga) {
			Date dtRiferimento = null;
			Date dtInsert = new Date();
			if (!inSomministrazioneConMissione) {
				Rapporto rapportoPrecedente = null;
				if(comunicazione.getIdComDComunicPrecedente()!=null) {
					rapportoPrecedente = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getIdComDComunicPrecedente(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P);
				}
				if(rapportoPrecedente!=null && (rapportoPrecedente.getDtFinePeriodoFormativo()!=null || rapportoPrecedente.getDtFineRapporto() != null)) {
					Date date = rapportoPrecedente.getDtFinePeriodoFormativo()!=null ? rapportoPrecedente.getDtFinePeriodoFormativo() : rapportoPrecedente.getDtFineRapporto();
					dtRiferimento = DateUtils.addDays(date, 1);
				}else {
					if (comunicazione.getRapporto() != null && comunicazione.getRapporto().getDtFineProroga() != null)
						dtRiferimento = dtInsert.compareTo(comunicazione.getRapporto().getDtFineProroga())<=0 ? dtInsert : comunicazione.getRapporto().getDtFineProroga();
				}
			}else {
				Rapporto rapportoPrecedente = null;
				if(comunicazione.getIdComDComunicPrecedente()!=null) {
					rapportoPrecedente = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getIdComDComunicPrecedente(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_M);
				}
				if(rapportoPrecedente!=null && (rapportoPrecedente.getDtFinePeriodoFormativo()!=null || rapportoPrecedente.getDtFineMissione() != null)) {
					Date date = rapportoPrecedente.getDtFinePeriodoFormativo()!=null ? rapportoPrecedente.getDtFinePeriodoFormativo() : rapportoPrecedente.getDtFineMissione();
					dtRiferimento = DateUtils.addDays(date, 1);
				}else {
					if (comunicazione.getRapporto() != null && comunicazione.getRapporto().getDtFineProroga() != null)
						dtRiferimento = dtInsert.compareTo(comunicazione.getMissione().getDtFineProroga())<=0 ? dtInsert : comunicazione.getMissione().getDtFineProroga();
				}
			}
			comunicazione.setDataRiferimento(dtRiferimento);
		}
	}
	private void impostaIdRapporto(Comunicazione comunicazione) {
		
		boolean inSomministrazioneConMissione = comunicazione.getTipoSomministrazione()!= null && (
				comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE || comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE);
		boolean isAssunzione = comunicazione.getTipoComunicazione()!=null && comunicazione.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE);
		
		if (isAssunzione && !inSomministrazioneConMissione) { // è assunzione unilav o unisomm
			comunicazione.setFlgCurrentRecord(ComonlConstants.FLAG_S);
			return;
		}
		
		if (comunicazione.getTipoProvenienza()==null) {
			comunicazione.setFlgCurrentRecord(ComonlConstants.FLAG_S);
			return;
		}
		if (!ComonlConstants.TIPO_PROVENIENZA_COMMAX.equalsIgnoreCase(comunicazione.getTipoProvenienza().getCodTipoProvenienzaMin())) {
			comunicazione.setFlgCurrentRecord(ComonlConstants.FLAG_S);
			return;
		}
		
		String cfDatore = comunicazione.getDatoreAttuale().getCodiceFiscale();
		String cfLavoratore = comunicazione.getLavoratori().get(0).getCodiceFiscale();
		Date dtInizioRapporto = comunicazione.getRapporto().getDtInizioRapporto();
		
		if (cfDatore == null || cfLavoratore == null || dtInizioRapporto == null) {
			comunicazione.setFlgCurrentRecord(ComonlConstants.FLAG_S);
			return;
		}
		
		List<Comunicazione> comunicazioni = comunicazioneDad.getComunicazioniByIdentificativoRapporto(cfDatore, cfLavoratore, dtInizioRapporto);
		if (comunicazioni == null || comunicazioni.isEmpty()) {
			// idRapporto dato da sequence.
			comunicazione.setFlgCurrentRecord(ComonlConstants.FLAG_S);
			return;
		}
		if (comunicazioni.size()==1) {
			comunicazione.setIdRapportoLavoro(comunicazioni.get(0).getIdRapportoLavoro());
		}else {
			List<Comunicazione> comunicazioniConIdRapportoLavoro = comunicazioni.stream().filter(x->x.getIdRapportoLavoro()!=null).collect(Collectors.toList());
			if (comunicazioniConIdRapportoLavoro!=null && !comunicazioniConIdRapportoLavoro.isEmpty()) {
				
				if (comunicazioniConIdRapportoLavoro.stream().filter(x -> !x.getTipoComunicazione().getId().equals(ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE)).findAny().orElse(null) == null) {
					// le comunicazione SONO tutte Assunzione -> idRapportoLavoro + piccolo
					Comunicazione minByIdRapporto = comunicazioniConIdRapportoLavoro
						.stream()
						.min(Comparator.comparing(Comunicazione::getIdRapportoLavoro))
						.orElseThrow(NoSuchElementException::new);
					comunicazione.setIdRapportoLavoro(minByIdRapporto.getIdRapportoLavoro());
				}else {
					// le comunicazione NON SONO tutte Assunzione -> idRapportoLavoro + grande
					Comunicazione maxByIdRapporto = comunicazioniConIdRapportoLavoro
							.stream()
							.max(Comparator.comparing(Comunicazione::getIdRapportoLavoro))
							.orElseThrow(NoSuchElementException::new);
					comunicazione.setIdRapportoLavoro(maxByIdRapporto.getIdRapportoLavoro());
				}
			}
		}
			
		// Set Flg_current_record = S sulla comunicazione con data riferimento maggiore.
		
		if (comunicazione.getIdRapportoLavoro()!=null) {
			List<Comunicazione> comunicazioniConStessoIdRapportoLavoro =
					comunicazioni.stream().filter(x->x.getIdRapportoLavoro()!=null && x.getIdRapportoLavoro().compareTo(comunicazione.getIdRapportoLavoro())==0).collect(Collectors.toList());
			
			Comunicazione comunicazioneCurrentS = comunicazioniConStessoIdRapportoLavoro
					.stream().filter(x-> ComonlConstants.FLAG_S.equals(x.getFlgCurrentRecord())).findFirst().orElse(null);
			
			if (comunicazioneCurrentS !=  null) {
				if( compareTo(comunicazione.getDataRiferimento(), comunicazioneCurrentS.getDataRiferimento())>=0) {
					comunicazione.setFlgCurrentRecord(ComonlConstants.FLAG_S);
				}else {
					comunicazione.setFlgCurrentRecord(null);
				}
			}else {
				comunicazione.setFlgCurrentRecord(ComonlConstants.FLAG_S);
			}
			if (ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getFlgCurrentRecord())) {
				comunicazioneCurrentS = comunicazioneDad.getComunicazioneByIdRapportoLavoro(comunicazione.getIdRapportoLavoro());
				if(comunicazioneCurrentS != null) {
					comunicazioneCurrentS.setFlgCurrentRecord(null);
					comunicazioneDad.updateComunicazione(comunicazioneCurrentS);
				}
			}
		}else {
			comunicazione.setFlgCurrentRecord(ComonlConstants.FLAG_S);
		}
	}
}
