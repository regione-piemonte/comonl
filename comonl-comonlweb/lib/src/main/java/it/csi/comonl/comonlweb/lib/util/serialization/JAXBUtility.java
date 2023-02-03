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
package it.csi.comonl.comonlweb.lib.util.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXB;

/**
 * Utilities for JAXB objects
 */
public class JAXBUtility {

	/** Prevent instantiation */
	private JAXBUtility() {
		// Prevent instantiation
	}

	/**
	 * Marshalls an entity
	 * @param obj the object to marshall
	 * @return the string representation
	 */
	public static String marshall(Object obj) {
		if (obj == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JAXB.marshal(obj, baos);
		return baos.toString();
	}

	/**
	 * Unmarshalls the entity
	 * @param <T> the entity type
	 * @param xml the string representation
	 * @param clazz the resulting class
	 * @return the resulting entity
	 */
	public static <T> T unmarshall(String xml, Class<T> clazz) {
		if (xml == null) {
			return null;
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
		return JAXB.unmarshal(bais, clazz);
	}

	/**
	 * Clones an entity
	 * @param <T> the resulting type
	 * @param obj the object to clone
	 * @return the cloned object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T obj) {
		if(obj == null) {
			// Null-safe
			return null;
		}
		String xml = marshall(obj);
		return (T) unmarshall(xml, obj.getClass());
	}

}
