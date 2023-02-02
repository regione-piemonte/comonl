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
package it.csi.comonl.comonlweb.ejb.entity;

import java.io.Serializable;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;

public class NextVal implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return val;
	}

	@Override
	public void setId(Long id) {
		val = id;
	}

	private static final long serialVersionUID = 1L;

	private long val;

	public long getVal() {
		return val;
	}

	public void setVal(long val) {
		this.val = val;
	}

}
