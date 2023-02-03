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
package it.csi.comonl.comonlweb.lib.dto.comunicazione;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategTirocinante;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.UnitaMisuraDurata;

/**
 * The Class TipolTirocCatTiroc.
 */
public class TipolTirocCatTiroc extends BaseDto<TipolTirocCatTirocPK> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Date dFine;
	private Date dInizio;
	private String flgVisualizzaWarning;
	private BigDecimal numDurataMax;
	private CategTirocinante categTirocinante;
	private TipologiaTirocinio tipologiaTirocinio;
	private UnitaMisuraDurata unitaMisuraDurata;

	/**
	 * @return the dFine
	 */
	public Date getDFine() {
		return dFine;
	}
	
	/**
	 * @param dFine the dFine to set
	 */
	public void setDFine(Date dFine) {
		this.dFine = dFine;
	}

	/**
	 * @return the dInizio
	 */
	public Date getDInizio() {
		return dInizio;
	}
	
	/**
	 * @param dInizio the dInizio to set
	 */
	public void setDInizio(Date dInizio) {
		this.dInizio = dInizio;
	}

	/**
	 * @return the flgVisualizzaWarning
	 */
	public String getFlgVisualizzaWarning() {
		return flgVisualizzaWarning;
	}
	
	/**
	 * @param flgVisualizzaWarning the flgVisualizzaWarning to set
	 */
	public void setFlgVisualizzaWarning(String flgVisualizzaWarning) {
		this.flgVisualizzaWarning = flgVisualizzaWarning;
	}

	/**
	 * @return the numDurataMax
	 */
	public BigDecimal getNumDurataMax() {
		return numDurataMax;
	}
	
	/**
	 * @param numDurataMax the numDurataMax to set
	 */
	public void setNumDurataMax(BigDecimal numDurataMax) {
		this.numDurataMax = numDurataMax;
	}

	/**
	 * @return the categTirocinante
	 */
	public CategTirocinante getCategTirocinante() {
		return categTirocinante;
	}
	
	/**
	 * @param categTirocinante the categTirocinante to set
	 */
	public void setCategTirocinante(CategTirocinante categTirocinante) {
		this.categTirocinante = categTirocinante;
	}

	/**
	 * @return the tipologiaTirocinio
	 */
	public TipologiaTirocinio getTipologiaTirocinio() {
		return tipologiaTirocinio;
	}
	
	/**
	 * @param tipologiaTirocinio the tipologiaTirocinio to set
	 */
	public void setTipologiaTirocinio(TipologiaTirocinio tipologiaTirocinio) {
		this.tipologiaTirocinio = tipologiaTirocinio;
	}

	/**
	 * @return the unitaMisuraDurata
	 */
	public UnitaMisuraDurata getUnitaMisuraDurata() {
		return unitaMisuraDurata;
	}
	
	/**
	 * @param unitaMisuraDurata the unitaMisuraDurata to set
	 */
	public void setUnitaMisuraDurata(UnitaMisuraDurata unitaMisuraDurata) {
		this.unitaMisuraDurata = unitaMisuraDurata;
	}

}
