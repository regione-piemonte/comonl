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

public class SubSezione {

	private String titolo;
	private List<Valore> valori;
	
	
	public SubSezione() {
	}

	public SubSezione(String titolo) {
		this.titolo = titolo;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public List<Valore> getValori() {
		return valori;
	}

	public void setValori(List<Valore> valori) {
		this.valori = valori;
	}

	public void addValore(Valore valore) {
		if (valori == null)
			this.valori = new ArrayList<>();
		this.valori.add(valore);
	}
	
	public void addValore(String name) {
		if (valori == null)
			this.valori = new ArrayList<>();
		this.valori.add(new Valore(name,""));
	}
	
	public void addValore(String name, String value) {
		if (valori == null)
			this.valori = new ArrayList<>();
		this.valori.add(new Valore(name,value));
	}
	
	
	public void addValore(String name, String value, String name2, String value2) {
		if (valori == null)
			this.valori = new ArrayList<>();
		
		
		this.valori.add(new Valore(name,value,name2,value2));
	}

}
