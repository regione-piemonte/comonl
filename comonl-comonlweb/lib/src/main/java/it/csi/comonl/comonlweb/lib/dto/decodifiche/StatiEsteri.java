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
package it.csi.comonl.comonlweb.lib.dto.decodifiche;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilp;
import it.csi.comonl.comonlweb.lib.dto.silp.Sede;

/**
 * The Class StatiEsteri.
 */
public class StatiEsteri extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codNazioneMin;
	private String dsStatiEsteri;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;
	private String flgUe;
	private String siglaNazione;
	private List<DatiAzienda> datiAziendas;
	private List<LavoratoreSilp> lavoratoriSilps;
	private List<Sede> sedeLegales;

	/**
	 * @return the codNazioneMin
	 */
	public String getCodNazioneMin() {
		return codNazioneMin;
	}

	/**
	 * @param codNazioneMin the codNazioneMin to set
	 */
	public void setCodNazioneMin(String codNazioneMin) {
		this.codNazioneMin = codNazioneMin;
	}

	/**
	 * @return the dsStatiEsteri
	 */
	public String getDsStatiEsteri() {
		return dsStatiEsteri;
	}

	/**
	 * @param dsStatiEsteri the dsStatiEsteri to set
	 */
	public void setDsStatiEsteri(String dsStatiEsteri) {
		this.dsStatiEsteri = dsStatiEsteri;
	}

	/**
	 * @return the dtFine
	 */
	public Date getDtFine() {
		return dtFine;
	}

	/**
	 * @param dtFine the dtFine to set
	 */
	public void setDtFine(Date dtFine) {
		this.dtFine = dtFine;
	}

	/**
	 * @return the dtInizio
	 */
	public Date getDtInizio() {
		return dtInizio;
	}

	/**
	 * @param dtInizio the dtInizio to set
	 */
	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
	}

	/**
	 * @return the dtTmst
	 */
	public Date getDtTmst() {
		return dtTmst;
	}

	/**
	 * @param dtTmst the dtTmst to set
	 */
	public void setDtTmst(Date dtTmst) {
		this.dtTmst = dtTmst;
	}

	/**
	 * @return the flgUe
	 */
	public String getFlgUe() {
		return flgUe;
	}

	/**
	 * @param flgUe the flgUe to set
	 */
	public void setFlgUe(String flgUe) {
		this.flgUe = flgUe;
	}

	/**
	 * @return the siglaNazione
	 */
	public String getSiglaNazione() {
		return siglaNazione;
	}

	/**
	 * @param siglaNazione the siglaNazione to set
	 */
	public void setSiglaNazione(String siglaNazione) {
		this.siglaNazione = siglaNazione;
	}

	/**
	 * @return the datiAziendas
	 */
	public List<DatiAzienda> getDatiAziendas() {
		return datiAziendas;
	}

	/**
	 * @param datiAziendas the datiAziendas to set
	 */
	public void setDatiAziendas(List<DatiAzienda> datiAziendas) {
		this.datiAziendas = datiAziendas;
	}

	/**
	 * @return the lavoratoriSilps
	 */
	public List<LavoratoreSilp> getLavoratoriSilps() {
		return lavoratoriSilps;
	}

	/**
	 * @param lavoratoriSilps the lavoratoriSilps to set
	 */
	public void setLavoratoriSilps(List<LavoratoreSilp> lavoratoriSilps) {
		this.lavoratoriSilps = lavoratoriSilps;
	}

	/**
	 * @return the sedeLegales
	 */
	public List<Sede> getSedeLegales() {
		return sedeLegales;
	}

	/**
	 * @param sedeLegales the sedeLegales to set
	 */
	public void setSedeLegales(List<Sede> sedeLegales) {
		this.sedeLegales = sedeLegales;
	}

}
