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
 * The Class TitoloStudio.
 */
public class TitoloStudio extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cGradostudio;
	private String cTipostudio;
	private String cTitolostudio;
	private String cTitolostudiomin;
	private String gradostudio;
	private String tipostudio;
	private String titolostudio;
	private String titolostudiomin;
	private LivelloStudio livelloStudio;

	/**
	 * @return the cGradostudio
	 */
	public String getCGradostudio() {
		return cGradostudio;
	}
	
	/**
	 * @param cGradostudio the cGradostudio to set
	 */
	public void setCGradostudio(String cGradostudio) {
		this.cGradostudio = cGradostudio;
	}

	/**
	 * @return the cTipostudio
	 */
	public String getCTipostudio() {
		return cTipostudio;
	}
	
	/**
	 * @param cTipostudio the cTipostudio to set
	 */
	public void setCTipostudio(String cTipostudio) {
		this.cTipostudio = cTipostudio;
	}

	/**
	 * @return the cTitolostudio
	 */
	public String getCTitolostudio() {
		return cTitolostudio;
	}
	
	/**
	 * @param cTitolostudio the cTitolostudio to set
	 */
	public void setCTitolostudio(String cTitolostudio) {
		this.cTitolostudio = cTitolostudio;
	}

	/**
	 * @return the cTitolostudiomin
	 */
	public String getCTitolostudiomin() {
		return cTitolostudiomin;
	}
	
	/**
	 * @param cTitolostudiomin the cTitolostudiomin to set
	 */
	public void setCTitolostudiomin(String cTitolostudiomin) {
		this.cTitolostudiomin = cTitolostudiomin;
	}

	/**
	 * @return the gradostudio
	 */
	public String getGradostudio() {
		return gradostudio;
	}
	
	/**
	 * @param gradostudio the gradostudio to set
	 */
	public void setGradostudio(String gradostudio) {
		this.gradostudio = gradostudio;
	}

	/**
	 * @return the tipostudio
	 */
	public String getTipostudio() {
		return tipostudio;
	}
	
	/**
	 * @param tipostudio the tipostudio to set
	 */
	public void setTipostudio(String tipostudio) {
		this.tipostudio = tipostudio;
	}

	/**
	 * @return the titolostudio
	 */
	public String getTitolostudio() {
		return titolostudio;
	}
	
	/**
	 * @param titolostudio the titolostudio to set
	 */
	public void setTitolostudio(String titolostudio) {
		this.titolostudio = titolostudio;
	}

	/**
	 * @return the titolostudiomin
	 */
	public String getTitolostudiomin() {
		return titolostudiomin;
	}
	
	/**
	 * @param titolostudiomin the titolostudiomin to set
	 */
	public void setTitolostudiomin(String titolostudiomin) {
		this.titolostudiomin = titolostudiomin;
	}

	/**
	 * @return the livelloStudio
	 */
	public LivelloStudio getLivelloStudio() {
		return livelloStudio;
	}
	
	/**
	 * @param livelloStudio the livelloStudio to set
	 */
	public void setLivelloStudio(LivelloStudio livelloStudio) {
		this.livelloStudio = livelloStudio;
	}

}
