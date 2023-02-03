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
package it.csi.comonl.comonlweb.lib.util.uuid;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

/**
 * Utilities for UUIDs
 */
public final class UuidUtils {

	/** Default DNS UUID namespace */
	public static final UUID NAMESPACE_DNS = UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8");
	/** Default URL UUID namespace */
	public static final UUID NAMESPACE_URL = UUID.fromString("6ba7b811-9dad-11d1-80b4-00c04fd430c8");
	/** Default OID UUID namespace */
	public static final UUID NAMESPACE_OID = UUID.fromString("6ba7b812-9dad-11d1-80b4-00c04fd430c8");
	/** Default X.500 UUID namespace */
	public static final UUID NAMESPACE_X500 = UUID.fromString("6ba7b814-9dad-11d1-80b4-00c04fd430c8");

	/** Private constructor */
	private UuidUtils() {
		// Prevent instantiation
	}

	/**
	 * Generates an UUID from a namespace and a string
	 * @param namespace the namespace
	 * @param name the string
	 * @return the UUID
	 */
	public static UUID generateUUIDv5FromNamespaceAndString(UUID namespace, String name) {
		return generateUUIDv5FromNamespaceAndBytes(namespace, Objects.requireNonNull(name, "name == null").getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Generates an UUID from a namespace and the bytes of a string
	 * @param namespace the namespace
	 * @param name the bytes
	 * @return the UUID
	 */
	public static UUID generateUUIDv5FromNamespaceAndBytes(UUID namespace, byte[] name) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException nsae) {
			throw new InternalError("SHA-1 not supported");
		}
		md.update(toBytes(Objects.requireNonNull(namespace, "namespace is null")));
		md.update(Objects.requireNonNull(name, "name is null"));
		byte[] sha1Bytes = md.digest();
		// Clear version
		sha1Bytes[6] &= 0x0f;
		// Set to version 5
		sha1Bytes[6] |= 0x50;
		// Clear variant
		sha1Bytes[8] &= 0x3f;
		// Set to IETF variant
		sha1Bytes[8] |= 0x80;
		return fromBytes(sha1Bytes);
	}

	/**
	 * Generates an UUID from bytes
	 *
	 * @param data the bytes of the UUID
	 * @return the UUID
	 */
	private static UUID fromBytes(byte[] data) {
		// Based on the private UUID(bytes[]) constructor
		long msb = 0;
		long lsb = 0;
		assert data.length >= 16;
		for (int i = 0; i < 8; i++) {
			msb = (msb << 8) | (data[i] & 0xff);
		}
		for (int i = 8; i < 16; i++) {
			lsb = (lsb << 8) | (data[i] & 0xff);
		}
		return new UUID(msb, lsb);
	}

	/**
	 * Transforms an UUID to its bytes
	 *
	 * @param uuid the UUID
	 * @return the bytes
	 */
	private static byte[] toBytes(UUID uuid) {
		// inverted logic of fromBytes()
		byte[] out = new byte[16];
		long msb = uuid.getMostSignificantBits();
		long lsb = uuid.getLeastSignificantBits();
		for (int i = 0; i < 8; i++) {
			out[i] = (byte) ((msb >> ((7 - i) * 8)) & 0xff);
		}
		for (int i = 8; i < 16; i++) {
			out[i] = (byte) ((lsb >> ((15 - i) * 8)) & 0xff);
		}
		return out;
	}

}
