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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

/**
 * Utilities for serialization
 */
public class SerializationUtils {

	/** Private constructor */
	private SerializationUtils() {
		// Prevent instantiation
	}

	/**
	 * Reads an object from its Base64 representation
	 * @param <T> the resulting object type
	 * @param s the string containing the Base64 of the object
	 * @return the object
	 * @throws IOException in case of an IO exception
	 * @throws ClassNotFoundException in case the class is not found by the ClassLoader
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromBase64String(String s) throws IOException, ClassNotFoundException {
		byte [] data = Base64.getDecoder().decode(s);
		T o;
		try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
			o  = (T) ois.readObject();
		}
		return o;
	}
	/**
	 * Writes an object to its Base64 representation
	 * @param o the object to serialize
	 * @return the Base64 serialization
	 * @throws IOException in case of an IO exception
	 */
	public static String toBase64String(Serializable o) throws IOException {
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			// Inner try for the Object stream
			try(ObjectOutputStream oos = new ObjectOutputStream(baos)) {
				oos.writeObject(o);
			}
			return Base64.getEncoder().encodeToString(baos.toByteArray());
		}
	}

}
