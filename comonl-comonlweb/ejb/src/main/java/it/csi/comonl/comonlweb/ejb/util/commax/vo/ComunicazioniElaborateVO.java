/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.util.commax.vo;

public class ComunicazioniElaborateVO {

	private int accettate;
	private int scartate;
	private int elaborate;
	private int warning;
	private String statoElaborazione;
	
	public String getStatoElaborazione() {
		return statoElaborazione;
	}
	public void setStatoElaborazione(String statoElaborazione) {
		this.statoElaborazione = statoElaborazione;
	}
	public int getAccettate() {
		return accettate;
	}
	public int getScartate() {
		return scartate;
	}
	public int getElaborate() {
		return elaborate;
	}
	public int getWarning() {
		return warning;
	}
	
	public void setAccettate(int accettate)
	{
		this.accettate=accettate;
	}
	public void setScartate(int scartate)
	{
		this.scartate=scartate;
	}
	public void setElaborate(int elaborate)
	{
		this.elaborate=elaborate;
	}
	public void setWarning(int warning)
	{
		this.warning=warning;
	}
}
