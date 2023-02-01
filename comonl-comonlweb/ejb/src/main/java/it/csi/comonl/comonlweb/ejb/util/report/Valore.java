/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.util.report;

public class Valore {

	private String name;
	private String value;

	private String name2;
	private String value2;

	public Valore(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public Valore(String name, String value, String name2, String value2) {
		this.name = name;
		this.value = value;
		this.name2 = name2;
		this.value2 = value2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		if (value == null)
			return "";
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

}
