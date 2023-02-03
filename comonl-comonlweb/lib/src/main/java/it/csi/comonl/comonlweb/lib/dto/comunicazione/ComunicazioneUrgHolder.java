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

import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;

public class ComunicazioneUrgHolder {
	
	private Comunicazione comunicazione;
	private String flgComPerDatore;
	private Datore datore;
	private Rapporto rapporto;
	private Lavoratore lavoratore;
	private Comune comune;
	public Comunicazione getComunicazione() {
		return comunicazione;
	}
	public void setComunicazione(Comunicazione comunicazione) {
		this.comunicazione = comunicazione;
	}
	
	public Datore getDatore() {
		return datore;
	}
	public void setDatore(Datore datore) {
		this.datore = datore;
	}
	public Rapporto getRapporto() {
		return rapporto;
	}
	public void setRapporto(Rapporto rapporto) {
		this.rapporto = rapporto;
	}
	public Lavoratore getLavoratore() {
		return lavoratore;
	}
	public void setLavoratore(Lavoratore lavoratore) {
		this.lavoratore = lavoratore;
	}
	
	public Comune getComune() {
		return comune;
	}
	public void setComune(Comune comune) {
		this.comune = comune;
	}
	public String getFlgComPerDatore() {
		return flgComPerDatore;
	}
	public void setFlgComPerDatore(String flgComPerDatore) {
		this.flgComPerDatore = flgComPerDatore;
	}
	
}
