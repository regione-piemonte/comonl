/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - LIB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.dto.silp;

import java.io.Serializable;
import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.BaseAuditedDto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.LegaleRappr;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.NaturaGiuridica;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;

/**
 * The Class DatiAzienda.
 */
public class DatiAzienda extends BaseAuditedDto<Integer> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String capReferente;
	private String cfAzienda;
	private String cfCapogruppo;
	private String cfReferente;
	private String cognomeReferente;
	private String denominazioneDatoreLavoro;
	private String emailReferente;
	private String faxReferente;
	private String flgCapogruppoEstero;
	private String flgProspettoDaCapogruppo;
	private String indirizzoReferente;
	private String nomeReferente;
	private String telefonoReferente;
	private Atecofin atecofin;
	private Ccnl ccnl;
	private Comune comune;
	private StatiEsteri statiEsteri;
	private Sede sedeLegale;
	private List<Sede> elencoSedi;
	private String idAziendaSilp;
	private boolean cessata;
	private boolean accentrata;
	private boolean presenteSuAAEP;
	private NaturaGiuridica naturaGiuridica;
	private String flagArtigiana;
	private String codLavoroTemp;
	private String flagTipoAzienda;
	private String descrTipoAzienda;
	private String flagMaster;
	private String altreInformazioni;
	private String partitaIva;
	private String flgPubblicaAmm;
	private LegaleRappr ilLegaleRapprDellaSedeLegale;

	/**
	 * @return the capReferente
	 */
	public String getCapReferente() {
		return capReferente;
	}

	/**
	 * @param capReferente the capReferente to set
	 */
	public void setCapReferente(String capReferente) {
		this.capReferente = capReferente;
	}

	/**
	 * @return the cfAzienda
	 */
	public String getCfAzienda() {
		return cfAzienda;
	}

	/**
	 * @param cfAzienda the cfAzienda to set
	 */
	public void setCfAzienda(String cfAzienda) {
		this.cfAzienda = cfAzienda;
	}

	/**
	 * @return the cfCapogruppo
	 */
	public String getCfCapogruppo() {
		return cfCapogruppo;
	}

	/**
	 * @param cfCapogruppo the cfCapogruppo to set
	 */
	public void setCfCapogruppo(String cfCapogruppo) {
		this.cfCapogruppo = cfCapogruppo;
	}

	/**
	 * @return the cfReferente
	 */
	public String getCfReferente() {
		return cfReferente;
	}

	/**
	 * @param cfReferente the cfReferente to set
	 */
	public void setCfReferente(String cfReferente) {
		this.cfReferente = cfReferente;
	}

	/*
	 * @return the cognomeReferente
	 */
	public String getCognomeReferente() {
		return cognomeReferente;
	}

	/**
	 * @param cognomeReferente the cognomeReferente to set
	 */
	public void setCognomeReferente(String cognomeReferente) {
		this.cognomeReferente = cognomeReferente;
	}

	/**
	 * @return the denominazioneDatoreLavoro
	 */
	public String getDenominazioneDatoreLavoro() {
		return denominazioneDatoreLavoro;
	}

	/**
	 * @param denominazioneDatoreLavoro the denominazioneDatoreLavoro to set
	 */
	public void setDenominazioneDatoreLavoro(String denominazioneDatoreLavoro) {
		this.denominazioneDatoreLavoro = denominazioneDatoreLavoro;
	}

	/**
	 * @return the emailReferente
	 */
	public String getEmailReferente() {
		return emailReferente;
	}

	/**
	 * @param emailReferente the emailReferente to set
	 */
	public void setEmailReferente(String emailReferente) {
		this.emailReferente = emailReferente;
	}

	/**
	 * @return the faxReferente
	 */
	public String getFaxReferente() {
		return faxReferente;
	}

	/**
	 * @param faxReferente the faxReferente to set
	 */
	public void setFaxReferente(String faxReferente) {
		this.faxReferente = faxReferente;
	}

	/**
	 * @return the flgCapogruppoEstero
	 */
	public String getFlgCapogruppoEstero() {
		return flgCapogruppoEstero;
	}

	/**
	 * @param flgCapogruppoEstero the flgCapogruppoEstero to set
	 */
	public void setFlgCapogruppoEstero(String flgCapogruppoEstero) {
		this.flgCapogruppoEstero = flgCapogruppoEstero;
	}

	/**
	 * @return the flgProspettoDaCapogruppo
	 */
	public String getFlgProspettoDaCapogruppo() {
		return flgProspettoDaCapogruppo;
	}

	/**
	 * @param flgProspettoDaCapogruppo the flgProspettoDaCapogruppo to set
	 */
	public void setFlgProspettoDaCapogruppo(String flgProspettoDaCapogruppo) {
		this.flgProspettoDaCapogruppo = flgProspettoDaCapogruppo;
	}

	/**
	 * @return the indirizzoReferente
	 */
	public String getIndirizzoReferente() {
		return indirizzoReferente;
	}

	/**
	 * @param indirizzoReferente the indirizzoReferente to set
	 */
	public void setIndirizzoReferente(String indirizzoReferente) {
		this.indirizzoReferente = indirizzoReferente;
	}

	/**
	 * @return the nomeReferente
	 */
	public String getNomeReferente() {
		return nomeReferente;
	}

	/**
	 * @param nomeReferente the nomeReferente to set
	 */
	public void setNomeReferente(String nomeReferente) {
		this.nomeReferente = nomeReferente;
	}

	/**
	 * @return the telefonoReferente
	 */
	public String getTelefonoReferente() {
		return telefonoReferente;
	}

	/**
	 * @param telefonoReferente the telefonoReferente to set
	 */
	public void setTelefonoReferente(String telefonoReferente) {
		this.telefonoReferente = telefonoReferente;
	}

	/**
	 * @return the atecofin
	 */
	public Atecofin getAtecofin() {
		return atecofin;
	}

	/**
	 * @param atecofin the atecofin to set
	 */
	public void setAtecofin(Atecofin atecofin) {
		this.atecofin = atecofin;
	}

	/**
	 * @return the ccnl
	 */
	public Ccnl getCcnl() {
		return ccnl;
	}

	/**
	 * @param ccnl the ccnl to set
	 */
	public void setCcnl(Ccnl ccnl) {
		this.ccnl = ccnl;
	}

	/**
	 * @return the comune
	 */
	public Comune getComune() {
		return comune;
	}

	/**
	 * @param comune the comune to set
	 */
	public void setComune(Comune comune) {
		this.comune = comune;
	}

	/**
	 * @return the statiEsteri
	 */
	public StatiEsteri getStatiEsteri() {
		return statiEsteri;
	}

	/**
	 * @param statiEsteri the statiEsteri to set
	 */
	public void setStatiEsteri(StatiEsteri statiEsteri) {
		this.statiEsteri = statiEsteri;
	}

	/**
	 * @return the sedeLegale
	 */
	public Sede getSedeLegale() {
		return sedeLegale;
	}

	/**
	 * @param sedeLegale the sedeLegale to set
	 */
	public void setSedeLegale(Sede sedeLegale) {
		this.sedeLegale = sedeLegale;
	}

	public String getIdAziendaSilp() {
		return idAziendaSilp;
	}

	public void setIdAziendaSilp(String idAziendaSilp) {
		this.idAziendaSilp = idAziendaSilp;
	}

	public List<Sede> getElencoSedi() {
		return elencoSedi;
	}

	public void setElencoSedi(List<Sede> elencoSedi) {
		this.elencoSedi = elencoSedi;
	}

	public boolean isCessata() {
		return cessata;
	}

	public void setCessata(boolean cessata) {
		this.cessata = cessata;
	}

	public boolean isAccentrata() {
		return accentrata;
	}

	public void setAccentrata(boolean accentrata) {
		this.accentrata = accentrata;
	}

	public boolean isPresenteSuAAEP() {
		return presenteSuAAEP;
	}

	public void setPresenteSuAAEP(boolean presenteSuAAEP) {
		this.presenteSuAAEP = presenteSuAAEP;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getFlagArtigiana() {
		return flagArtigiana;
	}

	public void setFlagArtigiana(String flagArtigiana) {
		this.flagArtigiana = flagArtigiana;
	}

	public String getCodLavoroTemp() {
		return codLavoroTemp;
	}

	public void setCodLavoroTemp(String codLavoroTemp) {
		this.codLavoroTemp = codLavoroTemp;
	}

	public String getFlagTipoAzienda() {
		return flagTipoAzienda;
	}

	public void setFlagTipoAzienda(String flagTipoAzienda) {
		this.flagTipoAzienda = flagTipoAzienda;
	}

	public String getDescrTipoAzienda() {
		return descrTipoAzienda;
	}

	public void setDescrTipoAzienda(String descrTipoAzienda) {
		this.descrTipoAzienda = descrTipoAzienda;
	}

	public String getFlagMaster() {
		return flagMaster;
	}

	public void setFlagMaster(String flagMaster) {
		this.flagMaster = flagMaster;
	}

	public String getAltreInformazioni() {
		return altreInformazioni;
	}

	public void setAltreInformazioni(String altreInformazioni) {
		this.altreInformazioni = altreInformazioni;
	}

	public NaturaGiuridica getNaturaGiuridica() {
		return naturaGiuridica;
	}

	public void setNaturaGiuridica(NaturaGiuridica naturaGiuridica) {
		this.naturaGiuridica = naturaGiuridica;
	}

	public String getFlgPubblicaAmm() {
		return flgPubblicaAmm;
	}

	public void setFlgPubblicaAmm(String flgPubblicaAmm) {
		this.flgPubblicaAmm = flgPubblicaAmm;
	}

	public LegaleRappr getIlLegaleRapprDellaSedeLegale() {
		return ilLegaleRapprDellaSedeLegale;
	}

	public void setIlLegaleRapprDellaSedeLegale(LegaleRappr ilLegaleRapprDellaSedeLegale) {
		this.ilLegaleRapprDellaSedeLegale = ilLegaleRapprDellaSedeLegale;
	}

}
