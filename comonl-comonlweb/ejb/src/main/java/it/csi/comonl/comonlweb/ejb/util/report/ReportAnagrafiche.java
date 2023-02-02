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

public class ReportAnagrafiche {

	private String name;
	private String titolo;
	private List<String> nomiColonne;
	private List<List<String>> valori;
	private boolean landscape;

	public ReportAnagrafiche(String titolo) {
		this.titolo = titolo;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public List<String> getNomiColonne() {
		return nomiColonne;
	}

	public void setNomiColonne(List<String> nomiColonne) {
		this.nomiColonne = nomiColonne;
	}

	public void addValori(List<String> valore) {
		if (valori == null)
			this.valori = new ArrayList<>();
		this.valori.add(valore);
	}

	public List<List<String>> getValori() {
		return valori;
	}

	public void addNomiColonne(String nomeColonna) {
		if (nomiColonne == null)
			this.nomiColonne = new ArrayList<>();
		this.nomiColonne.add(nomeColonna);
	}

	public boolean isLandscape() {
		return landscape;
	}

	public void setLandscape(boolean landscape) {
		this.landscape = landscape;
	}

	public String getName() {
		if (name != null)
			return name;
		else return titolo;
	}

	public void setName(String name) {
		this.name = name;
	}

}
