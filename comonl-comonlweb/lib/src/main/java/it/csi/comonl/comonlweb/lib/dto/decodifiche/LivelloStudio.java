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
import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class LivelloStudio.
 */
public class LivelloStudio extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codiceLivelloMin;
	private String codiceTitoloStudio;
	private String dsTitolo;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;
	private String isced97;
	private String isced97LevelProgDest;
	private String sinonimoTitoloStudio;
	private String tipoDiScuola;
	private String titoloDiStudio;

	/**
	 * @return the codiceLivelloMin
	 */
	public String getCodiceLivelloMin() {
		return codiceLivelloMin;
	}
	
	/**
	 * @param codiceLivelloMin the codiceLivelloMin to set
	 */
	public void setCodiceLivelloMin(String codiceLivelloMin) {
		this.codiceLivelloMin = codiceLivelloMin;
	}

	/**
	 * @return the codiceTitoloStudio
	 */
	public String getCodiceTitoloStudio() {
		return codiceTitoloStudio;
	}
	
	/**
	 * @param codiceTitoloStudio the codiceTitoloStudio to set
	 */
	public void setCodiceTitoloStudio(String codiceTitoloStudio) {
		this.codiceTitoloStudio = codiceTitoloStudio;
	}

	/**
	 * @return the dsTitolo
	 */
	public String getDsTitolo() {
		return dsTitolo;
	}
	
	/**
	 * @param dsTitolo the dsTitolo to set
	 */
	public void setDsTitolo(String dsTitolo) {
		this.dsTitolo = dsTitolo;
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
	 * @return the isced97
	 */
	public String getIsced97() {
		return isced97;
	}
	
	/**
	 * @param isced97 the isced97 to set
	 */
	public void setIsced97(String isced97) {
		this.isced97 = isced97;
	}

	/**
	 * @return the isced97LevelProgDest
	 */
	public String getIsced97LevelProgDest() {
		return isced97LevelProgDest;
	}
	
	/**
	 * @param isced97LevelProgDest the isced97LevelProgDest to set
	 */
	public void setIsced97LevelProgDest(String isced97LevelProgDest) {
		this.isced97LevelProgDest = isced97LevelProgDest;
	}

	/**
	 * @return the sinonimoTitoloStudio
	 */
	public String getSinonimoTitoloStudio() {
		return sinonimoTitoloStudio;
	}
	
	/**
	 * @param sinonimoTitoloStudio the sinonimoTitoloStudio to set
	 */
	public void setSinonimoTitoloStudio(String sinonimoTitoloStudio) {
		this.sinonimoTitoloStudio = sinonimoTitoloStudio;
	}

	/**
	 * @return the tipoDiScuola
	 */
	public String getTipoDiScuola() {
		return tipoDiScuola;
	}
	
	/**
	 * @param tipoDiScuola the tipoDiScuola to set
	 */
	public void setTipoDiScuola(String tipoDiScuola) {
		this.tipoDiScuola = tipoDiScuola;
	}

	/**
	 * @return the titoloDiStudio
	 */
	public String getTitoloDiStudio() {
		return titoloDiStudio;
	}
	
	/**
	 * @param titoloDiStudio the titoloDiStudio to set
	 */
	public void setTitoloDiStudio(String titoloDiStudio) {
		this.titoloDiStudio = titoloDiStudio;
	}

}
