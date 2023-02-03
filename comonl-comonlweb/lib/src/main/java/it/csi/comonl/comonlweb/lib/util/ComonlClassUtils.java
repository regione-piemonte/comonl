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
package it.csi.comonl.comonlweb.lib.util;


public abstract class ComonlClassUtils {
	/**
	 * Il metodo truncClassName restituisce il nome della classe passata come
	 * parametro senza il package
	 *
	 * @param cl l'istanza da cui ottenere il nome della classe
	 *
	 * @return il nome della classe senza l'indicazione del package
	 */

	public static String truncClassName(Class cl) {
		return cl.getName().substring(cl.getPackage().getName().length() + 1);
	}

}
