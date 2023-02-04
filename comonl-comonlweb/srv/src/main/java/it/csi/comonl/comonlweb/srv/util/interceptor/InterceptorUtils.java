/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - SRV submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.srv.util.interceptor;

import javax.interceptor.InvocationContext;

import it.csi.comonl.comonlweb.lib.util.reflection.ReflectionHelper;

/**
 * Utilities for the Interceptors
 */
public final class InterceptorUtils {

	/** Private constructor */
	private InterceptorUtils() {
		// Prevent instantiation
	}
	
	/**
	 * Tries to remove the proxy from a class.
	 * <p>WELD proxy classes are NON-Proxy classes, with names of the form
	 * <pre>&lt;original name&gt;$Proxy$_$$_WeldSubclass</pre>
	 * We try to intercept these cases
	 * @param invocationContext
	 * @return the unproxied class
	 */
	public static Class<?> deProxy(InvocationContext invocationContext) {
		Class<?> initialClass = invocationContext.getTarget().getClass();
		return ReflectionHelper.deProxy(initialClass);
	}
}
