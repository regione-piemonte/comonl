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

/**
 * The Class UplDocumenti.
 */
public class UplDocumenti extends BaseDto<Long> implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cfAzienda;
	private BigDecimal crcUpload;
	private Date dataElab;
	private Date dataRicezione;
	private Date dataVerifica;
	private String dsRuoloOperatore;
	private Date dtRichiestaElaborazione;
	private String flgVerifica;
	private String mittenteCf;
	private String mittenteCognome;
	private String mittenteEmail;
	private String mittenteNome;
	private String nomeFileUpload;
	private String pv;
	private String statoElaborazione;
	private BigDecimal totComElab;
	private BigDecimal totComScar;
	private BigDecimal totComWarn;
	private String verifica;

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
	 * @return the crcUpload
	 */
	public BigDecimal getCrcUpload() {
		return crcUpload;
	}
	
	/**
	 * @param crcUpload the crcUpload to set
	 */
	public void setCrcUpload(BigDecimal crcUpload) {
		this.crcUpload = crcUpload;
	}

	/**
	 * @return the dataElab
	 */
	public Date getDataElab() {
		return dataElab;
	}
	
	/**
	 * @param dataElab the dataElab to set
	 */
	public void setDataElab(Date dataElab) {
		this.dataElab = dataElab;
	}

	/**
	 * @return the dataRicezione
	 */
	public Date getDataRicezione() {
		return dataRicezione;
	}
	
	/**
	 * @param dataRicezione the dataRicezione to set
	 */
	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	/**
	 * @return the dataVerifica
	 */
	public Date getDataVerifica() {
		return dataVerifica;
	}
	
	/**
	 * @param dataVerifica the dataVerifica to set
	 */
	public void setDataVerifica(Date dataVerifica) {
		this.dataVerifica = dataVerifica;
	}

	/**
	 * @return the dsRuoloOperatore
	 */
	public String getDsRuoloOperatore() {
		return dsRuoloOperatore;
	}
	
	/**
	 * @param dsRuoloOperatore the dsRuoloOperatore to set
	 */
	public void setDsRuoloOperatore(String dsRuoloOperatore) {
		this.dsRuoloOperatore = dsRuoloOperatore;
	}

	/**
	 * @return the dtRichiestaElaborazione
	 */
	public Date getDtRichiestaElaborazione() {
		return dtRichiestaElaborazione;
	}
	
	/**
	 * @param dtRichiestaElaborazione the dtRichiestaElaborazione to set
	 */
	public void setDtRichiestaElaborazione(Date dtRichiestaElaborazione) {
		this.dtRichiestaElaborazione = dtRichiestaElaborazione;
	}

	/**
	 * @return the flgVerifica
	 */
	public String getFlgVerifica() {
		return flgVerifica;
	}
	
	/**
	 * @param flgVerifica the flgVerifica to set
	 */
	public void setFlgVerifica(String flgVerifica) {
		this.flgVerifica = flgVerifica;
	}

	/**
	 * @return the mittenteCf
	 */
	public String getMittenteCf() {
		return mittenteCf;
	}
	
	/**
	 * @param mittenteCf the mittenteCf to set
	 */
	public void setMittenteCf(String mittenteCf) {
		this.mittenteCf = mittenteCf;
	}

	/**
	 * @return the mittenteCognome
	 */
	public String getMittenteCognome() {
		return mittenteCognome;
	}
	
	/**
	 * @param mittenteCognome the mittenteCognome to set
	 */
	public void setMittenteCognome(String mittenteCognome) {
		this.mittenteCognome = mittenteCognome;
	}

	/**
	 * @return the mittenteEmail
	 */
	public String getMittenteEmail() {
		return mittenteEmail;
	}
	
	/**
	 * @param mittenteEmail the mittenteEmail to set
	 */
	public void setMittenteEmail(String mittenteEmail) {
		this.mittenteEmail = mittenteEmail;
	}

	/**
	 * @return the mittenteNome
	 */
	public String getMittenteNome() {
		return mittenteNome;
	}
	
	/**
	 * @param mittenteNome the mittenteNome to set
	 */
	public void setMittenteNome(String mittenteNome) {
		this.mittenteNome = mittenteNome;
	}

	/**
	 * @return the nomeFileUpload
	 */
	public String getNomeFileUpload() {
		return nomeFileUpload;
	}
	
	/**
	 * @param nomeFileUpload the nomeFileUpload to set
	 */
	public void setNomeFileUpload(String nomeFileUpload) {
		this.nomeFileUpload = nomeFileUpload;
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
	 * @return the statoElaborazione
	 */
	public String getStatoElaborazione() {
		return statoElaborazione;
	}
	
	/**
	 * @param statoElaborazione the statoElaborazione to set
	 */
	public void setStatoElaborazione(String statoElaborazione) {
		this.statoElaborazione = statoElaborazione;
	}

	/**
	 * @return the totComElab
	 */
	public BigDecimal getTotComElab() {
		return totComElab;
	}
	
	/**
	 * @param totComElab the totComElab to set
	 */
	public void setTotComElab(BigDecimal totComElab) {
		this.totComElab = totComElab;
	}

	/**
	 * @return the totComScar
	 */
	public BigDecimal getTotComScar() {
		return totComScar;
	}
	
	/**
	 * @param totComScar the totComScar to set
	 */
	public void setTotComScar(BigDecimal totComScar) {
		this.totComScar = totComScar;
	}

	/**
	 * @return the totComWarn
	 */
	public BigDecimal getTotComWarn() {
		return totComWarn;
	}
	
	/**
	 * @param totComWarn the totComWarn to set
	 */
	public void setTotComWarn(BigDecimal totComWarn) {
		this.totComWarn = totComWarn;
	}

	/**
	 * @return the verifica
	 */
	public String getVerifica() {
		return verifica;
	}
	
	/**
	 * @param verifica the verifica to set
	 */
	public void setVerifica(String verifica) {
		this.verifica = verifica;
	}

}
