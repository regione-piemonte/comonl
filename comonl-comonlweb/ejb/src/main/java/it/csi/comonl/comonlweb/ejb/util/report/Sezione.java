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

public class Sezione {

	private String titolo;
	private List<SubSezione> subsezioni;
	private boolean pageBreak = false;

	public Sezione(String titolo) {
		this.titolo = titolo;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public List<SubSezione> getSubsezioni() {
		return subsezioni;
	}

	public void setSubsezioni(List<SubSezione> subsezioni) {
		this.subsezioni = subsezioni;
	}

	public void addSubsezione(SubSezione subsezione) {
		if (subsezioni == null)
			this.subsezioni = new ArrayList<>();
		this.subsezioni.add(subsezione);
	}

	public boolean isPageBreak() {
		return pageBreak;
	}

	public void setPageBreak(boolean pageBreak) {
		this.pageBreak = pageBreak;
	}

}
