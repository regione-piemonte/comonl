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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;

/**
 * The Class CapacitaFormativaModa.
 */
public class CapacitaFormativaModa extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cfAzienda;
	private String cfDelegatoRl;
	private String cognomeRl;
	private String dsAziDenominazione;
	private String dsIndirizAziSedelegale;
	private Date dtInsert;
	private Date dtNascRl;
	private Date dtUltMod;
	private String flgFirmato;
	private BigDecimal idComTStatiEsteri;
	private String idUserInsert;
	private String idUserUltMod;
	private String nomeRl;
	private byte[] pdfQuestionarioModA;
	private String pivaAzienda;
	private String r1APercAccademici;
	private String r1APercFormprof;
	private String r1APercImpresa;
	private String r1APercScolastici;
	private String r1BRisorseTitolo;
	private String r1CRisorsePosizione;
	private String r2LuoghiIdonei;
	private String r3Tutore;
	private Comune comune1;
	private Comune comune2;

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
	 * @return the cfDelegatoRl
	 */
	public String getCfDelegatoRl() {
		return cfDelegatoRl;
	}
	
	/**
	 * @param cfDelegatoRl the cfDelegatoRl to set
	 */
	public void setCfDelegatoRl(String cfDelegatoRl) {
		this.cfDelegatoRl = cfDelegatoRl;
	}

	/**
	 * @return the cognomeRl
	 */
	public String getCognomeRl() {
		return cognomeRl;
	}
	
	/**
	 * @param cognomeRl the cognomeRl to set
	 */
	public void setCognomeRl(String cognomeRl) {
		this.cognomeRl = cognomeRl;
	}

	/**
	 * @return the dsAziDenominazione
	 */
	public String getDsAziDenominazione() {
		return dsAziDenominazione;
	}
	
	/**
	 * @param dsAziDenominazione the dsAziDenominazione to set
	 */
	public void setDsAziDenominazione(String dsAziDenominazione) {
		this.dsAziDenominazione = dsAziDenominazione;
	}

	/**
	 * @return the dsIndirizAziSedelegale
	 */
	public String getDsIndirizAziSedelegale() {
		return dsIndirizAziSedelegale;
	}
	
	/**
	 * @param dsIndirizAziSedelegale the dsIndirizAziSedelegale to set
	 */
	public void setDsIndirizAziSedelegale(String dsIndirizAziSedelegale) {
		this.dsIndirizAziSedelegale = dsIndirizAziSedelegale;
	}

	/**
	 * @return the dtInsert
	 */
	public Date getDtInsert() {
		return dtInsert;
	}
	
	/**
	 * @param dtInsert the dtInsert to set
	 */
	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	/**
	 * @return the dtNascRl
	 */
	public Date getDtNascRl() {
		return dtNascRl;
	}
	
	/**
	 * @param dtNascRl the dtNascRl to set
	 */
	public void setDtNascRl(Date dtNascRl) {
		this.dtNascRl = dtNascRl;
	}

	/**
	 * @return the dtUltMod
	 */
	public Date getDtUltMod() {
		return dtUltMod;
	}
	
	/**
	 * @param dtUltMod the dtUltMod to set
	 */
	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	/**
	 * @return the flgFirmato
	 */
	public String getFlgFirmato() {
		return flgFirmato;
	}
	
	/**
	 * @param flgFirmato the flgFirmato to set
	 */
	public void setFlgFirmato(String flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	/**
	 * @return the idComTStatiEsteri
	 */
	public BigDecimal getIdComTStatiEsteri() {
		return idComTStatiEsteri;
	}
	
	/**
	 * @param idComTStatiEsteri the idComTStatiEsteri to set
	 */
	public void setIdComTStatiEsteri(BigDecimal idComTStatiEsteri) {
		this.idComTStatiEsteri = idComTStatiEsteri;
	}

	/**
	 * @return the idUserInsert
	 */
	public String getIdUserInsert() {
		return idUserInsert;
	}
	
	/**
	 * @param idUserInsert the idUserInsert to set
	 */
	public void setIdUserInsert(String idUserInsert) {
		this.idUserInsert = idUserInsert;
	}

	/**
	 * @return the idUserUltMod
	 */
	public String getIdUserUltMod() {
		return idUserUltMod;
	}
	
	/**
	 * @param idUserUltMod the idUserUltMod to set
	 */
	public void setIdUserUltMod(String idUserUltMod) {
		this.idUserUltMod = idUserUltMod;
	}

	/**
	 * @return the nomeRl
	 */
	public String getNomeRl() {
		return nomeRl;
	}
	
	/**
	 * @param nomeRl the nomeRl to set
	 */
	public void setNomeRl(String nomeRl) {
		this.nomeRl = nomeRl;
	}

	/**
	 * @return the pdfQuestionarioModA
	 */
	public byte[] getPdfQuestionarioModA() {
		return pdfQuestionarioModA;
	}
	
	/**
	 * @param pdfQuestionarioModA the pdfQuestionarioModA to set
	 */
	public void setPdfQuestionarioModA(byte[] pdfQuestionarioModA) {
		this.pdfQuestionarioModA = pdfQuestionarioModA;
	}

	/**
	 * @return the pivaAzienda
	 */
	public String getPivaAzienda() {
		return pivaAzienda;
	}
	
	/**
	 * @param pivaAzienda the pivaAzienda to set
	 */
	public void setPivaAzienda(String pivaAzienda) {
		this.pivaAzienda = pivaAzienda;
	}

	/**
	 * @return the r1APercAccademici
	 */
	public String getR1APercAccademici() {
		return r1APercAccademici;
	}
	
	/**
	 * @param r1APercAccademici the r1APercAccademici to set
	 */
	public void setR1APercAccademici(String r1APercAccademici) {
		this.r1APercAccademici = r1APercAccademici;
	}

	/**
	 * @return the r1APercFormprof
	 */
	public String getR1APercFormprof() {
		return r1APercFormprof;
	}
	
	/**
	 * @param r1APercFormprof the r1APercFormprof to set
	 */
	public void setR1APercFormprof(String r1APercFormprof) {
		this.r1APercFormprof = r1APercFormprof;
	}

	/**
	 * @return the r1APercImpresa
	 */
	public String getR1APercImpresa() {
		return r1APercImpresa;
	}
	
	/**
	 * @param r1APercImpresa the r1APercImpresa to set
	 */
	public void setR1APercImpresa(String r1APercImpresa) {
		this.r1APercImpresa = r1APercImpresa;
	}

	/**
	 * @return the r1APercScolastici
	 */
	public String getR1APercScolastici() {
		return r1APercScolastici;
	}
	
	/**
	 * @param r1APercScolastici the r1APercScolastici to set
	 */
	public void setR1APercScolastici(String r1APercScolastici) {
		this.r1APercScolastici = r1APercScolastici;
	}

	/**
	 * @return the r1BRisorseTitolo
	 */
	public String getR1BRisorseTitolo() {
		return r1BRisorseTitolo;
	}
	
	/**
	 * @param r1BRisorseTitolo the r1BRisorseTitolo to set
	 */
	public void setR1BRisorseTitolo(String r1BRisorseTitolo) {
		this.r1BRisorseTitolo = r1BRisorseTitolo;
	}

	/**
	 * @return the r1CRisorsePosizione
	 */
	public String getR1CRisorsePosizione() {
		return r1CRisorsePosizione;
	}
	
	/**
	 * @param r1CRisorsePosizione the r1CRisorsePosizione to set
	 */
	public void setR1CRisorsePosizione(String r1CRisorsePosizione) {
		this.r1CRisorsePosizione = r1CRisorsePosizione;
	}

	/**
	 * @return the r2LuoghiIdonei
	 */
	public String getR2LuoghiIdonei() {
		return r2LuoghiIdonei;
	}
	
	/**
	 * @param r2LuoghiIdonei the r2LuoghiIdonei to set
	 */
	public void setR2LuoghiIdonei(String r2LuoghiIdonei) {
		this.r2LuoghiIdonei = r2LuoghiIdonei;
	}

	/**
	 * @return the r3Tutore
	 */
	public String getR3Tutore() {
		return r3Tutore;
	}
	
	/**
	 * @param r3Tutore the r3Tutore to set
	 */
	public void setR3Tutore(String r3Tutore) {
		this.r3Tutore = r3Tutore;
	}

	/**
	 * @return the comune1
	 */
	public Comune getComune1() {
		return comune1;
	}
	
	/**
	 * @param comune1 the comune1 to set
	 */
	public void setComune1(Comune comune1) {
		this.comune1 = comune1;
	}

	/**
	 * @return the comune2
	 */
	public Comune getComune2() {
		return comune2;
	}
	
	/**
	 * @param comune2 the comune2 to set
	 */
	public void setComune2(Comune comune2) {
		this.comune2 = comune2;
	}

}
