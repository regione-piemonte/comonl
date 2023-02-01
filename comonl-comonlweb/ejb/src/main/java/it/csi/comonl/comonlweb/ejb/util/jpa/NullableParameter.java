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
package it.csi.comonl.comonlweb.ejb.util.jpa;

/**
 * Wraps a parameter to ensure that it may be asked to be null
 * @param <P> the wrapper parameter type
 */
public class NullableParameter<P> {
	private final P value;
	private final boolean forceNull;
	
	/**
	 * Constructor
	 * @param value the value of the parameter
	 * @param forceNull whether the parameter must be null
	 */
	public NullableParameter(P value, boolean forceNull) {
		this.value = value;
		this.forceNull = forceNull;
	}

	/**
	 * @return the value
	 */
	public P getValue() {
		return value;
	}

	/**
	 * @return the forceNull
	 */
	public boolean isForceNull() {
		return forceNull;
	}
	
	/**
	 * Whether the data should be null
	 * @return true if the data should be forces as null
	 */
	public boolean shouldBeNull() {
		return value == null && forceNull;
	}
}
