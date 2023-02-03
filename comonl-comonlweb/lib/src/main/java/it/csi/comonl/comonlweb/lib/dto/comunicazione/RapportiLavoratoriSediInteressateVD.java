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

import java.util.List;

public class RapportiLavoratoriSediInteressateVD  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Rapporto rapportoVD;
	Lavoratore lavoratoreVD;
	SedeLavoro sedeLavoroVD;
	
	public Rapporto getRapportoVD() {
		return rapportoVD;
	}
	public void setRapportoVD(Rapporto rapportoVD) {
		this.rapportoVD = rapportoVD;
	}
	public Lavoratore getLavoratoreVD() {
		return lavoratoreVD;
	}
	public void setLavoratoreVD(Lavoratore lavoratoreVD) {
		this.lavoratoreVD = lavoratoreVD;
	}
	public SedeLavoro getSedeLavoroVD() {
		return sedeLavoroVD;
	}
	public void setSedeLavoroVD(SedeLavoro sedeLavoroVD) {
		this.sedeLavoroVD = sedeLavoroVD;
	}
	
	
	
		
}
