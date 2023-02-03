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

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class ElaboratiOk.
 */
public class ElaboratiOk extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long progCom;
	private BigDecimal annoProtCom;
	private String cfImpresa;
	private String cfLavoratore;
	private String codiceComunicazioneReg;
	private BigDecimal fTipoAcquisiz;
	private String nomeFileXml;
	private String nroProtCom;
	private String pv;
	private String tipoCom;
	private UplDocumenti uplDocumenti;

	/**
	 * @return the annoProtCom
	 */
	public BigDecimal getAnnoProtCom() {
		return annoProtCom;
	}
	
	/**
	 * @param annoProtCom the annoProtCom to set
	 */
	public void setAnnoProtCom(BigDecimal annoProtCom) {
		this.annoProtCom = annoProtCom;
	}

	/**
	 * @return the cfImpresa
	 */
	public String getCfImpresa() {
		return cfImpresa;
	}
	
	/**
	 * @param cfImpresa the cfImpresa to set
	 */
	public void setCfImpresa(String cfImpresa) {
		this.cfImpresa = cfImpresa;
	}

	/**
	 * @return the cfLavoratore
	 */
	public String getCfLavoratore() {
		return cfLavoratore;
	}
	
	/**
	 * @param cfLavoratore the cfLavoratore to set
	 */
	public void setCfLavoratore(String cfLavoratore) {
		this.cfLavoratore = cfLavoratore;
	}

	/**
	 * @return the codiceComunicazioneReg
	 */
	public String getCodiceComunicazioneReg() {
		return codiceComunicazioneReg;
	}
	
	/**
	 * @param codiceComunicazioneReg the codiceComunicazioneReg to set
	 */
	public void setCodiceComunicazioneReg(String codiceComunicazioneReg) {
		this.codiceComunicazioneReg = codiceComunicazioneReg;
	}

	/**
	 * @return the fTipoAcquisiz
	 */
	public BigDecimal getFTipoAcquisiz() {
		return fTipoAcquisiz;
	}
	
	/**
	 * @param fTipoAcquisiz the fTipoAcquisiz to set
	 */
	public void setFTipoAcquisiz(BigDecimal fTipoAcquisiz) {
		this.fTipoAcquisiz = fTipoAcquisiz;
	}

	/**
	 * @return the nomeFileXml
	 */
	public String getNomeFileXml() {
		return nomeFileXml;
	}
	
	/**
	 * @param nomeFileXml the nomeFileXml to set
	 */
	public void setNomeFileXml(String nomeFileXml) {
		this.nomeFileXml = nomeFileXml;
	}

	/**
	 * @return the nroProtCom
	 */
	public String getNroProtCom() {
		return nroProtCom;
	}
	
	/**
	 * @param nroProtCom the nroProtCom to set
	 */
	public void setNroProtCom(String nroProtCom) {
		this.nroProtCom = nroProtCom;
	}

	/**
	 * @return the pv
	 */
	public String getPv() {
		return pv;
	}
	
	/**
	 * @param pv the pv to set
	 */
	public void setPv(String pv) {
		this.pv = pv;
	}

	/**
	 * @return the tipoCom
	 */
	public String getTipoCom() {
		return tipoCom;
	}
	
	/**
	 * @param tipoCom the tipoCom to set
	 */
	public void setTipoCom(String tipoCom) {
		this.tipoCom = tipoCom;
	}

	/**
	 * @return the uplDocumenti
	 */
	public UplDocumenti getUplDocumenti() {
		return uplDocumenti;
	}
	
	/**
	 * @param uplDocumenti the uplDocumenti to set
	 */
	public void setUplDocumenti(UplDocumenti uplDocumenti) {
		this.uplDocumenti = uplDocumenti;
	}

	public Long getProgCom() {
		return progCom;
	}

	public void setProgCom(Long progCom) {
		this.progCom = progCom;
	}

	
}
