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
package it.csi.comonl.comonlweb.ejb.util.jpa;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * Helper for JPA queries
 */
public final class JpaQueryHelper {

	/** Prevent instantiation */
	private JpaQueryHelper() {
		// Prevent instantiation
	}

	/**
	 * Adds field equal query, in AND
	 * @param jpql the JPQL builder
	 * @param params the params
	 * @param fieldName the field name
	 * @param parameterAlias the parameter alias
	 * @param fieldValue the field value
	 */
	public static <P> void andFieldEquals(StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, NullableParameter<P> fieldValue) {
		if(fieldValue.shouldBeNull()) {
			JpaQueryHelper.andFieldNull(jpql, fieldName);
			return;
		}
		JpaQueryHelper.andField(jpql, params, fieldName, parameterAlias, fieldValue, CompareTypes.EQUALS);
	}
	/**
	 * Adds field equal query, in AND
	 * @param jpql the JPQL builder
	 * @param params the params
	 * @param fieldName the field name
	 * @param parameterAlias the parameter alias
	 * @param fieldValue the field value
	 */
	public static void andFieldEquals(StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, Object fieldValue) {
		JpaQueryHelper.andField(jpql, params, fieldName, parameterAlias, fieldValue, CompareTypes.EQUALS);
	}

	/**
	 * Adds field equal query, in AND
	 * @param jpql the JPQL builder
	 * @param params the params
	 * @param fieldName the field name
	 * @param parameterAlias the parameter alias
	 * @param fieldValue the field value
	 */
	public static <P> void andFieldNotEquals(StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, NullableParameter<P> fieldValue) {
		if(fieldValue.shouldBeNull()) {
			JpaQueryHelper.andFieldNotNull(jpql, fieldName);
			return;
		}
		JpaQueryHelper.andField(jpql, params, fieldName, parameterAlias, fieldValue, CompareTypes.NOT_EQUALS);
	}
	/**
	 * Adds field equal query, in AND
	 * @param jpql the JPQL builder
	 * @param params the params
	 * @param fieldName the field name
	 * @param parameterAlias the parameter alias
	 * @param fieldValue the field value
	 */
	public static void andFieldNotEquals(StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, Object fieldValue) {
		JpaQueryHelper.andField(jpql, params, fieldName, parameterAlias, fieldValue, CompareTypes.NOT_EQUALS);
	}

	
	/**
	 * Adds field comparison query, in AND.
	 *
	 * @param jpql           the JPQL builder
	 * @param params         the params
	 * @param fieldName      the field name
	 * @param parameterAlias the parameter alias
	 * @param fieldValue     the field value
	 * @param comparison     the comparison
	 */
	public static void andField(StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, Object fieldValue, CompareTypes comparison) {
		if(fieldValue == null) {
			return;
		}
		Object tmp = fieldValue;
		if(fieldValue instanceof String) {
			tmp = StringUtils.trimToEmpty((String) tmp);
			
			if(StringUtils.isBlank((String)tmp)) {
				return;
			}
		}
		jpql.append(" AND ")
			.append(fieldName)
			.append(" ")
			.append(comparison.getValue())
			.append(" :")
			.append(parameterAlias);
		params.put(parameterAlias, tmp);
	}

	/**
	 * Adds field not null, in AND.
	 *
	 * @param jpql      the JPQL builder
	 * @param fieldName the field name
	 */
	public static void andFieldNotNull(StringBuilder jpql, String fieldName) {
		jpql.append(" AND ")
			.append(fieldName)
			.append(" IS NOT NULL ");
	}

	/**
	 * Adds field null, in AND.
	 *
	 * @param jpql      the JPQL builder
	 * @param fieldName the field name
	 */
	public static void andFieldNull(StringBuilder jpql, String fieldName) {
		jpql.append(" AND ")
			.append(fieldName)
			.append(" IS NULL ");
	}

	/**
	 * Adds field equal query as string, in AND, case-insensitive
	 * @param jpql the JPQL builder
	 * @param params the params
	 * @param fieldName the field name
	 * @param parameterAlias the parameter alias
	 * @param fieldValue the field value
	 */
	public static void andStringEquals(StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, String fieldValue) {
		if(StringUtils.isBlank(fieldValue)) {
			return;
		}
		jpql.append(" AND UPPER(")
			.append(fieldName)
			.append(") = UPPER(:")
			.append(parameterAlias)
			.append(")");
		params.put(parameterAlias, fieldValue);
	}
	
	
	public static void andStringEqualsNoUpper(StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, String fieldValue) {
		if(StringUtils.isBlank(fieldValue)) {
			return;
		}
		jpql.append(" AND ")
			.append(fieldName)
			.append(" = :")
			.append(parameterAlias);
		params.put(parameterAlias, fieldValue);
	}

	/**
	 * Adds field LIKE query, in AND, case insensitive
	 * @param jpql the JPQL builder
	 * @param params the params
	 * @param fieldName the field name
	 * @param parameterAlias the parameter alias
	 * @param fieldValue the field value
	 */
	public static void andStringLike(StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, String fieldValue) {
		stringLike("AND", jpql, params, fieldName, parameterAlias, fieldValue);
	}

	/**
	 * Adds field LIKE query, in OR, case insensitive
	 * @param jpql the JPQL builder
	 * @param params the params
	 * @param fieldName the field name
	 * @param parameterAlias the parameter alias
	 * @param fieldValue the field value
	 */
	public static void orStringLike(StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, String fieldValue) {
		stringLike("OR", jpql, params, fieldName, parameterAlias, fieldValue);
	}

	/**
	 * Adds field LIKE query, case insensitive
	 * @param operator the operator
	 * @param jpql the JPQL builder
	 * @param params the params
	 * @param fieldName the field name
	 * @param parameterAlias the parameter alias
	 * @param fieldValue the field value
	 */
	private static void stringLike(String operator, StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, String fieldValue) {
		String tmp = StringUtils.trimToEmpty(fieldValue);
		if(StringUtils.isBlank(tmp)) {
			return;
		}
		jpql.append(" ")
			.append(operator)
			.append(" UPPER(")
			.append(fieldName)
			.append(") LIKE UPPER(CONCAT('%', :")
			.append(parameterAlias)
			.append(", '%'))");
		params.put(parameterAlias, tmp);
	}

	/**
	 * Adds field LIKE query, in AND, case insensitive
	 * @param jpql the JPQL builder
	 * @param params the params
	 * @param fieldName the field name
	 * @param parameterAlias the parameter alias
	 * @param fieldValues the field value
	 */
	public static void andStringsLike(StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, List<String> fieldValues) {
		if(fieldValues == null || fieldValues.isEmpty()) {
			return;
		}
		jpql.append(" AND ( ");
		int i = 0;
		for(String fieldValue : fieldValues) {
			stringLike(i++ == 0 ? "" : "OR", jpql, params, fieldName, parameterAlias + "_" + i, fieldValue);
		}
		jpql.append(" ) ");
	}

	/**
	 * Adds field in query as string
	 * @param jpql the JPQL builder
	 * @param params the params
	 * @param fieldName the field name
	 * @param parameterAlias the parameter alias
	 * @param fieldValue the field value
	 */
	public static void andStringIn(StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, Collection<String> fieldValue) {
		if(fieldValue == null || fieldValue.isEmpty()) {
			return;
		}
		jpql.append(" AND UPPER(")
			.append(fieldName)
			.append(") IN :")
			.append(parameterAlias);
		params.put(parameterAlias, fieldValue.stream().map(String::toUpperCase).collect(Collectors.toList()));
	}

	/**
	 * Adds field in query
	 * @param <V> the value type
	 * @param jpql the JPQL builder
	 * @param params the params
	 * @param fieldName the field name
	 * @param parameterAlias the parameter alias
	 * @param fieldValue the field value
	 */
	public static <V extends Object> void andFieldIn(StringBuilder jpql, Map<String, Object> params, String fieldName, String parameterAlias, Collection<V> fieldValue) {
		if(fieldValue == null || fieldValue.isEmpty()) {
			return;
		}
		jpql.append(" AND UPPER(")
			.append(fieldName)
			.append(") IN :")
			.append(parameterAlias);
		params.put(parameterAlias, fieldValue);
	}

	/**
	 * Sets the field.
	 * @param jpql       the jpql
	 * @param params     the params
	 * @param fieldName  the field name
	 * @param fieldAlias the field alias
	 * @param fieldValue the field value
	 * @param force      the force
	 */
	public static void setField(StringBuilder jpql, Map<String, Object> params, String fieldName, String fieldAlias, Object fieldValue, boolean force) {
		if(!force && fieldValue == null) {
			return;
		}
		jpql.append(", ")
			.append(fieldName)
			.append(" = :")
			.append(fieldAlias);
		params.put(fieldAlias, fieldValue);
	}

	/**
	 * Sets the field.
	 * @param jpql       the jpql
	 * @param params     the params
	 * @param fieldName  the field name
	 * @param fieldAlias the field alias
	 * @param fieldValue the field value
	 * @param force      the force
	 */
	public static void setField(StringBuilder jpql, Map<String, Object> params, String fieldName, String fieldAlias, String fieldValue, boolean force) {
		if(!force && StringUtils.isBlank(fieldValue)) {
			return;
		}
		jpql.append(", ")
			.append(fieldName)
			.append(" = :")
			.append(fieldAlias);
		params.put(fieldAlias, fieldValue);
	}
	
	/**
	 * Checks the parameter for validita
	 * @param jpql       the jpql
	 * @param params     the params
	 * @param tableAlias the table alias
	 * @param fieldAlias the field alias
	 * @param checkTime  the time to check against
	 */
	public static void andCheckDateValidita(StringBuilder jpql, Map<String, Object> params, String tableAlias, String fieldAlias, Date checkTime) {
		// validita_inizio
		jpql
			.append(" AND ( ")
			.append(tableAlias).append(".dtInizio IS NULL OR (")
			.append(tableAlias).append(".dtInizio IS NOT NULL AND ")
			.append(tableAlias).append(".dtInizio <= :").append(fieldAlias)
			.append(")")
			.append(")");
		// validita_fine
		jpql
			.append(" AND ( ")
			.append(tableAlias).append(".dtFine IS NULL OR (")
			.append(tableAlias).append(".dtFine IS NOT NULL AND ")
			.append(tableAlias).append(".dtFine >= :").append(fieldAlias)
			.append(")")
			.append(")");
	}
	/**
	 * Checks the parameter for validita
	 * @param jpql       the jpql
	 * @param params     the params
	 * @param tableAlias the table alias
	 * @param checkTime  the time to check against
	 */
	public static void andCheckDateValidita(StringBuilder jpql, Map<String, Object> params, String tableAlias, Date checkTime) {
		JpaQueryHelper.andCheckDateValidita(jpql, params, tableAlias, "now", checkTime);
	}
	
	/**
	 * Checks the parameter for validita
	 * @param jpql       the jpql
	 * @param params     the params
	 * @param tableAlias the table alias
	 * @param fieldAlias the field alias
	 * @param checkTime  the time to check against
	 */
	public static void andCheckDataCancellazione(StringBuilder jpql, Map<String, Object> params, String tableAlias, String fieldAlias, Date checkTime) {
		jpql
			.append(" AND ( ")
			.append(tableAlias).append(".dataCancellazione IS NULL OR (")
			.append(tableAlias).append(".dataCancellazione IS NOT NULL AND ")
			.append(tableAlias).append(".dataCancellazione > :").append(fieldAlias)
			.append(")")
			.append(")");
	}
	/**
	 * Checks the parameter for validita
	 * @param jpql       the jpql
	 * @param params     the params
	 * @param tableAlias the table alias
	 * @param checkTime  the time to check against
	 */
	public static void andCheckDataCancellazione(StringBuilder jpql, Map<String, Object> params, String tableAlias, Date checkTime) {
		JpaQueryHelper.andCheckDataCancellazione(jpql, params, tableAlias, "now", checkTime);
	}
}
