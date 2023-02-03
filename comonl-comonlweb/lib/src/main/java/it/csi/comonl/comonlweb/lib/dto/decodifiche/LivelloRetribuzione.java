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
 * The Class LivelloRetribuzione.
 */
public class LivelloRetribuzione extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codLivello;
	private String desLivello;
	private BigDecimal divisoreOrario;
	private Date dtFine;
	private Date dtInizio;
	private BigDecimal lordoMensile;
	private BigDecimal mensilita;
	private BigDecimal ordinamento;
	private Ccnl ccnl;
	private TipoLivelloRetrib tipoLivelloRetrib;

	/**
	 * @return the codLivello
	 */
	public String getCodLivello() {
		return codLivello;
	}
	
	/**
	 * @param codLivello the codLivello to set
	 */
	public void setCodLivello(String codLivello) {
		this.codLivello = codLivello;
	}

	/**
	 * @return the desLivello
	 */
	public String getDesLivello() {
		return desLivello;
	}
	
	/**
	 * @param desLivello the desLivello to set
	 */
	public void setDesLivello(String desLivello) {
		this.desLivello = desLivello;
	}

	/**
	 * @return the divisoreOrario
	 */
	public BigDecimal getDivisoreOrario() {
		return divisoreOrario;
	}
	
	/**
	 * @param divisoreOrario the divisoreOrario to set
	 */
	public void setDivisoreOrario(BigDecimal divisoreOrario) {
		this.divisoreOrario = divisoreOrario;
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
	 * @return the lordoMensile
	 */
	public BigDecimal getLordoMensile() {
		return lordoMensile;
	}
	
	/**
	 * @param lordoMensile the lordoMensile to set
	 */
	public void setLordoMensile(BigDecimal lordoMensile) {
		this.lordoMensile = lordoMensile;
	}

	/**
	 * @return the mensilita
	 */
	public BigDecimal getMensilita() {
		return mensilita;
	}
	
	/**
	 * @param mensilita the mensilita to set
	 */
	public void setMensilita(BigDecimal mensilita) {
		this.mensilita = mensilita;
	}

	/**
	 * @return the ordinamento
	 */
	public BigDecimal getOrdinamento() {
		return ordinamento;
	}
	
	/**
	 * @param ordinamento the ordinamento to set
	 */
	public void setOrdinamento(BigDecimal ordinamento) {
		this.ordinamento = ordinamento;
	}

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
	 * @return the tipoLivelloRetrib
	 */
	public TipoLivelloRetrib getTipoLivelloRetrib() {
		return tipoLivelloRetrib;
	}
	
	/**
	 * @param tipoLivelloRetrib the tipoLivelloRetrib to set
	 */
	public void setTipoLivelloRetrib(TipoLivelloRetrib tipoLivelloRetrib) {
		this.tipoLivelloRetrib = tipoLivelloRetrib;
	}

}
