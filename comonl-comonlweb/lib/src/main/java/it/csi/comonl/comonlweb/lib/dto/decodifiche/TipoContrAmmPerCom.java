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
import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class TipoContrAmmPerCom.
 */
public class TipoContrAmmPerCom extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String flgSocioLav;
	private String flgTipoOrarioNonDefinito;
	private String flgVldCes;
	private String flgVldDst;
	private String flgVldPro;
	private String flgVldSom;
	private String flgVldTra;
	private String flgVldTrs;
	private String flgVldUd;
	private String flgVldUl;
	private String flgVldVd;
	private TipoContratti tipoContratti;

	/**
	 * @return the flgSocioLav
	 */
	public String getFlgSocioLav() {
		return flgSocioLav;
	}
	
	/**
	 * @param flgSocioLav the flgSocioLav to set
	 */
	public void setFlgSocioLav(String flgSocioLav) {
		this.flgSocioLav = flgSocioLav;
	}

	/**
	 * @return the flgTipoOrarioNonDefinito
	 */
	public String getFlgTipoOrarioNonDefinito() {
		return flgTipoOrarioNonDefinito;
	}
	
	/**
	 * @param flgTipoOrarioNonDefinito the flgTipoOrarioNonDefinito to set
	 */
	public void setFlgTipoOrarioNonDefinito(String flgTipoOrarioNonDefinito) {
		this.flgTipoOrarioNonDefinito = flgTipoOrarioNonDefinito;
	}

	/**
	 * @return the flgVldCes
	 */
	public String getFlgVldCes() {
		return flgVldCes;
	}
	
	/**
	 * @param flgVldCes the flgVldCes to set
	 */
	public void setFlgVldCes(String flgVldCes) {
		this.flgVldCes = flgVldCes;
	}

	/**
	 * @return the flgVldDst
	 */
	public String getFlgVldDst() {
		return flgVldDst;
	}
	
	/**
	 * @param flgVldDst the flgVldDst to set
	 */
	public void setFlgVldDst(String flgVldDst) {
		this.flgVldDst = flgVldDst;
	}

	/**
	 * @return the flgVldPro
	 */
	public String getFlgVldPro() {
		return flgVldPro;
	}
	
	/**
	 * @param flgVldPro the flgVldPro to set
	 */
	public void setFlgVldPro(String flgVldPro) {
		this.flgVldPro = flgVldPro;
	}

	/**
	 * @return the flgVldSom
	 */
	public String getFlgVldSom() {
		return flgVldSom;
	}
	
	/**
	 * @param flgVldSom the flgVldSom to set
	 */
	public void setFlgVldSom(String flgVldSom) {
		this.flgVldSom = flgVldSom;
	}

	/**
	 * @return the flgVldTra
	 */
	public String getFlgVldTra() {
		return flgVldTra;
	}
	
	/**
	 * @param flgVldTra the flgVldTra to set
	 */
	public void setFlgVldTra(String flgVldTra) {
		this.flgVldTra = flgVldTra;
	}

	/**
	 * @return the flgVldTrs
	 */
	public String getFlgVldTrs() {
		return flgVldTrs;
	}
	
	/**
	 * @param flgVldTrs the flgVldTrs to set
	 */
	public void setFlgVldTrs(String flgVldTrs) {
		this.flgVldTrs = flgVldTrs;
	}

	/**
	 * @return the flgVldUd
	 */
	public String getFlgVldUd() {
		return flgVldUd;
	}
	
	/**
	 * @param flgVldUd the flgVldUd to set
	 */
	public void setFlgVldUd(String flgVldUd) {
		this.flgVldUd = flgVldUd;
	}

	/**
	 * @return the flgVldUl
	 */
	public String getFlgVldUl() {
		return flgVldUl;
	}
	
	/**
	 * @param flgVldUl the flgVldUl to set
	 */
	public void setFlgVldUl(String flgVldUl) {
		this.flgVldUl = flgVldUl;
	}

	/**
	 * @return the flgVldVd
	 */
	public String getFlgVldVd() {
		return flgVldVd;
	}
	
	/**
	 * @param flgVldVd the flgVldVd to set
	 */
	public void setFlgVldVd(String flgVldVd) {
		this.flgVldVd = flgVldVd;
	}

	/**
	 * @return the tipoContratti
	 */
	public TipoContratti getTipoContratti() {
		return tipoContratti;
	}
	
	/**
	 * @param tipoContratti the tipoContratti to set
	 */
	public void setTipoContratti(TipoContratti tipoContratti) {
		this.tipoContratti = tipoContratti;
	}

}
