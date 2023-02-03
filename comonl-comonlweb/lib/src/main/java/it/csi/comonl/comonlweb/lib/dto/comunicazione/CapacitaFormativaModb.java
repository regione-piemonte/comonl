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
 * The Class CapacitaFormativaModb.
 */
public class CapacitaFormativaModb extends BaseDto<Long> implements Serializable {

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
	private byte[] pdfQuestionarioModB;
	private String pivaAzienda;
	private String r1AEsperienza;
	private String r1BSpecifiche;
	private String r2ProfContrattuale;
	private String r3LuoghiSic;
	private String r4LuoghiPfi;
	private String r5Tutore;
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
	 * @return the pdfQuestionarioModB
	 */
	public byte[] getPdfQuestionarioModB() {
		return pdfQuestionarioModB;
	}
	
	/**
	 * @param pdfQuestionarioModB the pdfQuestionarioModB to set
	 */
	public void setPdfQuestionarioModB(byte[] pdfQuestionarioModB) {
		this.pdfQuestionarioModB = pdfQuestionarioModB;
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
	 * @return the r1AEsperienza
	 */
	public String getR1AEsperienza() {
		return r1AEsperienza;
	}
	
	/**
	 * @param r1AEsperienza the r1AEsperienza to set
	 */
	public void setR1AEsperienza(String r1AEsperienza) {
		this.r1AEsperienza = r1AEsperienza;
	}

	/**
	 * @return the r1BSpecifiche
	 */
	public String getR1BSpecifiche() {
		return r1BSpecifiche;
	}
	
	/**
	 * @param r1BSpecifiche the r1BSpecifiche to set
	 */
	public void setR1BSpecifiche(String r1BSpecifiche) {
		this.r1BSpecifiche = r1BSpecifiche;
	}

	/**
	 * @return the r2ProfContrattuale
	 */
	public String getR2ProfContrattuale() {
		return r2ProfContrattuale;
	}
	
	/**
	 * @param r2ProfContrattuale the r2ProfContrattuale to set
	 */
	public void setR2ProfContrattuale(String r2ProfContrattuale) {
		this.r2ProfContrattuale = r2ProfContrattuale;
	}

	/**
	 * @return the r3LuoghiSic
	 */
	public String getR3LuoghiSic() {
		return r3LuoghiSic;
	}
	
	/**
	 * @param r3LuoghiSic the r3LuoghiSic to set
	 */
	public void setR3LuoghiSic(String r3LuoghiSic) {
		this.r3LuoghiSic = r3LuoghiSic;
	}

	/**
	 * @return the r4LuoghiPfi
	 */
	public String getR4LuoghiPfi() {
		return r4LuoghiPfi;
	}
	
	/**
	 * @param r4LuoghiPfi the r4LuoghiPfi to set
	 */
	public void setR4LuoghiPfi(String r4LuoghiPfi) {
		this.r4LuoghiPfi = r4LuoghiPfi;
	}

	/**
	 * @return the r5Tutore
	 */
	public String getR5Tutore() {
		return r5Tutore;
	}
	
	/**
	 * @param r5Tutore the r5Tutore to set
	 */
	public void setR5Tutore(String r5Tutore) {
		this.r5Tutore = r5Tutore;
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
