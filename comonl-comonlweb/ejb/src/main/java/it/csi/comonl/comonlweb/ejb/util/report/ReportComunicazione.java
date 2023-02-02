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
package it.csi.comonl.comonlweb.ejb.util.report;

import java.util.ArrayList;
import java.util.List;

public class ReportComunicazione {

	private String name;
	private String titolo;
	private List<Sezione> sezioni;
	private boolean landscape;

	public ReportComunicazione(String titolo) {
		this.titolo = titolo;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public List<Sezione> getSezioni() {
		return sezioni;
	}

	public void setSezioni(List<Sezione> sezioni) {
		this.sezioni = sezioni;
	}

	public void addSezione(Sezione sezione) {
		if (sezioni == null)
			this.sezioni = new ArrayList<>();
		this.sezioni.add(sezione);
	}

	public boolean isLandscape() {
		return landscape;
	}

	public void setLandscape(boolean landscape) {
		this.landscape = landscape;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
