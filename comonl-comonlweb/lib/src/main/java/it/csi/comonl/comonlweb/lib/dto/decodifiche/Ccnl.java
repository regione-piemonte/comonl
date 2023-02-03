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
import java.math.BigDecimal;
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class Ccnl.
 */
public class Ccnl extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codCcnlMin;
	private String dsCcnl;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;
	private BigDecimal idNewCcnl;
	private String settore;
//	private List<DatiAzienda> datiAziendas;
	private Ccnl ccnl;
//	private List<Ccnl> ccnls;

	/**
	 * @return the codCcnlMin
	 */
	public String getCodCcnlMin() {
		return codCcnlMin;
	}

	/**
	 * @param codCcnlMin the codCcnlMin to set
	 */
	public void setCodCcnlMin(String codCcnlMin) {
		this.codCcnlMin = codCcnlMin;
	}

	/**
	 * @return the dsCcnl
	 */
	public String getDsCcnl() {
		return dsCcnl;
	}

	/**
	 * @param dsCcnl the dsCcnl to set
	 */
	public void setDsCcnl(String dsCcnl) {
		this.dsCcnl = dsCcnl;
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
	 * @return the idNewCcnl
	 */
	public BigDecimal getIdNewCcnl() {
		return idNewCcnl;
	}

	/**
	 * @param idNewCcnl the idNewCcnl to set
	 */
	public void setIdNewCcnl(BigDecimal idNewCcnl) {
		this.idNewCcnl = idNewCcnl;
	}

	/**
	 * @return the settore
	 */
	public String getSettore() {
		return settore;
	}

	/**
	 * @param settore the settore to set
	 */
	public void setSettore(String settore) {
		this.settore = settore;
	}

	/**
	 * @return the datiAziendas
	 */
//	public List<DatiAzienda> getDatiAziendas() {
//		return datiAziendas;
//	}
//	
//	/**
//	 * @param datiAziendas the datiAziendas to set
//	 */
//	public void setDatiAziendas(List<DatiAzienda> datiAziendas) {
//		this.datiAziendas = datiAziendas;
//	}

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
	 * @return the ccnls
	 */
//	public List<Ccnl> getCcnls() {
//		return ccnls;
//	}
//	
//	/**
//	 * @param ccnls the ccnls to set
//	 */
//	public void setCcnls(List<Ccnl> ccnls) {
//		this.ccnls = ccnls;
//	}

}
