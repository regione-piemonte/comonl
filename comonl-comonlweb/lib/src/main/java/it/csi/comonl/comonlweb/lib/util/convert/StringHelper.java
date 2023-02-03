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
package it.csi.comonl.comonlweb.lib.util.convert;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.Base64;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * Helper for Strings
 */
public final class StringHelper {

	private static final Pattern MESSAGE_FORMAT_PLACEHOLDER_PATTERN = Pattern.compile("\\{(\\d+)\\}");
	private static final Pattern PUNCTUATION_PATTERN = Pattern.compile("[ \\p{Punct}]");

	/** Private constructor */
	private StringHelper() {
		// prevent instantiation
	}

	/**
	 * Formats a String replacing the unsubstituted replacements with default ones
	 * @param format the format of the resulting string
	 * @param defaultSubstitution the default substitution
	 * @param replacements the replacements to set
	 * @return the formatted string
	 */
	public static String formatStringWithDefaultReplacements(String format, String defaultSubstitution, Object... replacements) {
		final String msg = MessageFormat.format(format, replacements);
		final Matcher matcher = MESSAGE_FORMAT_PLACEHOLDER_PATTERN.matcher(msg);
		return matcher.replaceAll(defaultSubstitution);
	}
	/**
	 * Formats a String replacing the unsubstituted replacements with default ones
	 * @param format the format of the resulting string
	 * @param defaultSubstitution the default substitution
	 * @param replacements the replacements to set
	 * @return the formatted string
	 */
	public static String formatStringWithDefaultReplacementsAlternative(String format, String defaultSubstitution, Object... replacements) {
		final String msg = String.format(format, replacements);
		final Matcher matcher = MESSAGE_FORMAT_PLACEHOLDER_PATTERN.matcher(msg);
		return matcher.replaceAll(defaultSubstitution);
	}

	/**
	 * Removes punctuation and spacing from a string
	 * @param originalString the original string
	 * @return the modified string
	 */
	public static String removePunctuationSpace(String originalString) {
		if(originalString == null) {
			return null;
		}
		Matcher matcher = PUNCTUATION_PATTERN.matcher(originalString);
		return matcher.replaceAll("");
	}

	/**
	 * String normalization
	 * @param originalString the string to normalize
	 * @return the normalized string
	 */
	public static String normalizeString(String originalString) {
		String tmp = originalString.toLowerCase();
		tmp = removePunctuationSpace(tmp);
		return Normalizer.normalize(tmp, Normalizer.Form.NFC);
	}

	/**
	 * Encode a byte array to a hexadecimal string
	 * @param byteArray the bytes
	 * @return the hex string
	 */
	public static String encodeHexString(byte[] byteArray) {
		StringBuilder hexStringBuffer = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			hexStringBuffer.append(byteToHex(byteArray[i]));
		}
		return hexStringBuffer.toString();
	}

	/**
	 * Decodes a hexadecimal string into its bytes
	 * @param hexString the hex string
	 * @return the bytes
	 */
	public static byte[] decodeHexString(String hexString) {
		if (hexString.length() % 2 == 1) {
			throw new IllegalArgumentException("Invalid hexadecimal String supplied.");
		}
		byte[] bytes = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i += 2) {
			bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
		}
		return bytes;
	}

	/**
	 * Converts a byte to an hex string
	 * @param num the byte
	 * @return the hex string corresponding to the byte
	 */
	public static String byteToHex(byte num) {
		char[] hexDigits = new char[2];
		hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
		hexDigits[1] = Character.forDigit((num & 0xF), 16);
		return new String(hexDigits);
	}

	/**
	 * Converts an hex string to a byte
	 * <p>The string must contain at least 2 characters, and only the first 2 are used
	 * @param hexString the hex string
	 * @return the byte
	 */
	public static byte hexToByte(String hexString) {
		int firstDigit = toDigit(hexString.charAt(0));
		int secondDigit = toDigit(hexString.charAt(1));
		return (byte) ((firstDigit << 4) + secondDigit);
	}

	/**
	 * Converts an hexadecimal character to a integer
	 * @param hexChar the character to convert
	 * @return the integer
	 */
	public static int toDigit(char hexChar) {
		int digit = Character.digit(hexChar, 16);
		if(digit == -1) {
			throw new IllegalArgumentException("Invalid Hexadecimal Character: "+ hexChar);
		}
		return digit;
	}

	/**
	 * Replace placeholders in a string
	 * @param <V> the value typing
	 * @param template the string template
	 * @param replacements the replacements
	 * @return the resulting string
	 */
	public static <V> String replacePlaceholders(String template, Map<String, V> replacements) {
		final String replacementsData = replacements.keySet().stream().collect(Collectors.joining("|"));
		final Pattern pattern = Pattern.compile("\\{(" + replacementsData + ")\\}");

		Matcher m = pattern.matcher(template);
		StringBuffer sb = new StringBuffer();
		while(m.find()) {
			Object replacement = replacements.get(m.group(1));
			m.appendReplacement(sb, replacement !=  null ? replacement.toString() : null);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	/**
	 * Pads a string to the right and truncates it to a given length
	 * @param baseString the base string
	 * @param padChar the padding char
	 * @param length the length of the desired string
	 * @return the padded and truncated string
	 */
	public static String padRightAndTruncate(String baseString, char padChar, int length) {
		String tmp = StringUtils.rightPad(baseString, length, padChar);
		return StringUtils.substring(tmp, 0, length);
	}
	/**
	 * Pads a string to the left and truncates it to a given length
	 * @param baseString the base string
	 * @param padChar the padding char
	 * @param length the length of the desired string
	 * @return the padded and truncated string
	 */
	public static String padLeftAndTruncate(String baseString, char padChar, int length) {
		String tmp = StringUtils.leftPad(baseString, length, padChar);
		return StringUtils.substring(tmp, tmp.length() - length);
	}
	/**
	 * Inserts a string periodically every `period` characters
	 * @param text the starting text
	 * @param insert the inserted text
	 * @param period the period
	 * @return the inserted string
	 */
	public static String insertPeriodically(String text, String insert, int period) {
		StringBuilder builder = new StringBuilder(text.length() + insert.length() * (text.length() / period) + 1);
		int index = 0;
		String prefix = "";
		while (index < text.length()) {
			// Don't put the insert in the very first iteration.
			// This is easier than appending it *after* each substring
			builder.append(prefix);
			prefix = insert;
			builder.append(text.substring(index, Math.min(index + period, text.length())));
			index += period;
		}
		return builder.toString();
	}
	/**
	 * Returns the string if not blank
	 * @param string the string
	 * @return the string
	 */
	public static String ifNotBlank(String string) {
		return ifNotBlank(string, null);
	}
	/**
	 * Returns the string if not blank
	 * @param string the string
	 * @param defaultValue the default value
	 * @return the string
	 */
	public static String ifNotBlank(String string, String defaultValue) {
		return StringUtils.isNotBlank(string) ? string.trim() : defaultValue;
	}
	
	/**
	 * Computes the UTF-8 length of a string
	 * @param str the string
	 * @return the length
	 */
	public static int getStringLengthUTF8(String str) {
		return str.getBytes(StandardCharsets.UTF_8).length;
	}
	
	/**
	 * Trims a string to a specified length
	 * @param original the original string
	 * @param length the max length
	 * @return the trimmed string
	 */
	public static String trimToLength(String original, int length) {
		if (original != null && original.length() > length) {
			return original.substring(0,  length);
		}
		return original;
	}
	
	/**
	 * Transforms a string to kebab-case
	 * @param str the string to transform
	 * @return the kebab-cased string
	 */
	public static String toKebabCase(String str) {
		return str
				.replaceAll("([a-z0-9])([A-Z])", "$1-$2")
				.replaceAll("([A-Z])([A-Z])(?=[a-z])", "$1-$2");
	}
	
	/**
	 * Merge multiple strings as a SQL-like enabled string
	 * @param chunks the chunks to merge
	 * @return the merged string (may be null)
	 */
	public static String mergeAsLike(String... chunks) {
		StringBuilder result = new StringBuilder();
		for(String chunk : chunks) {
			if(StringUtils.isNotBlank(chunk)) {
				result.append(" %")
					.append(chunk)
					.append("%");
			}
		}
		return StringUtils.trimToNull(result.toString());
	}
	
	/**
	 * Computes the SHA-1 of a given string
	 * @param input the input string
	 * @return the SHA-1
	 */
	public static byte[] sha1(String input) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.update(input.getBytes(StandardCharsets.UTF_8), 0, input.length());
			return messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			// Should never happen
			throw new RuntimeException("SHA-1 computation error for string \"" + input + "\"", e);
		}
	}
	
	/**
	 * Computes the Base64 encoding of the SHA-1 of a given string
	 * @param input the input string
	 * @return the SHA-1
	 */
	public static String base64SHA1(String input) {
		byte[] base64 = Base64.getEncoder().encode(sha1(input));
		return new String(base64, StandardCharsets.UTF_8);
	}
}
