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
 * The Class MotivoPermesso.
 */
public class MotivoPermesso extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codMotivoMin;
	private String dsComTMotivoPermesso;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;
	private MotivoPermesso motivoPermesso;

	/**
	 * @return the codMotivoMin
	 */
	public String getCodMotivoMin() {
		return codMotivoMin;
	}
	
	/**
	 * @param codMotivoMin the codMotivoMin to set
	 */
	public void setCodMotivoMin(String codMotivoMin) {
		this.codMotivoMin = codMotivoMin;
	}

	/**
	 * @return the dsComTMotivoPermesso
	 */
	public String getDsComTMotivoPermesso() {
		return dsComTMotivoPermesso;
	}
	
	/**
	 * @param dsComTMotivoPermesso the dsComTMotivoPermesso to set
	 */
	public void setDsComTMotivoPermesso(String dsComTMotivoPermesso) {
		this.dsComTMotivoPermesso = dsComTMotivoPermesso;
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
	 * @return the motivoPermesso
	 */
	public MotivoPermesso getMotivoPermesso() {
		return motivoPermesso;
	}
	
	/**
	 * @param motivoPermesso the motivoPermesso to set
	 */
	public void setMotivoPermesso(MotivoPermesso motivoPermesso) {
		this.motivoPermesso = motivoPermesso;
	}

}
