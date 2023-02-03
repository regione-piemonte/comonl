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
package it.csi.comonl.comonlweb.lib.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;

import org.apache.commons.text.StringSubstitutor;

/**
 * The Class ApiError.
 */
public class ApiError implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The code. */
	private String code;
	/** The code. */
	private String group;
	/** The error message. */
	private String errorMessage;
	/** The error parameters */
	private Map<String, Object> params = new HashMap<>();
	/** The error group parameters */
	private Map<String, Object> groupParams = new HashMap<>();
	/** The type of error */
	private String type;

	/** Default constructor */
	public ApiError() {}

	/**
	 * Constructor using fields
	 * @param code the code
	 * @param errorMessage the error message
	 */
	public ApiError(String code, String errorMessage) {
		this.code = code;
		this.errorMessage = errorMessage;
		this.params = new HashMap<>();
		this.groupParams = new HashMap<>();
	}

	/**
	 * Constructor using fields
	 * @param code the code
	 * @param errorMessage the error message
	 * @param params the params
	 */
	public ApiError(String code, String errorMessage, Map<String, Object> params) {
		this.code = code;
		this.errorMessage = errorMessage;
		this.params = params;
		this.groupParams = new HashMap<>();
	}
	
	/**
	 * Constructor using fields
	 * @param code the code
	 * @param group the group
	 * @param errorMessage the error message
	 */
	public ApiError(String code, String group, String errorMessage) {
		this.code = code;
		this.group = group;
		this.errorMessage = errorMessage;
		this.params = new HashMap<>();
		this.groupParams = new HashMap<>();
	}

	/**
	 * Constructor using fields
	 * @param code the code
	 * @param group the group
	 * @param errorMessage the error message
	 * @param params the params
	 */
	public ApiError(String code, String group, String errorMessage, Map<String, Object> params) {
		this.code = code;
		this.group = group;
		this.errorMessage = errorMessage;
		this.params = params;
		this.groupParams = new HashMap<>();
	}
	
	/**
	 * Gets the code.
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Gets the error message.
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params != null ? params : new HashMap<>();
	}

	/**
	 * @return the groupParams
	 */
	public Map<String, Object> getGroupParams() {
		return groupParams;
	}
	
	/**
	 * @param groupParams the groupParams to set
	 */
	public void setGroupParams(Map<String, Object> groupParams) {
		this.groupParams = groupParams != null ? groupParams : new HashMap<>();
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Equals.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ApiError apiError = (ApiError) o;
		// per controllo errore già verificatesi in service
		return Objects.equals(code, apiError.code);
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new StringBuilder()
			.append("ApiError [code=").append(code)
			.append(", errorMessage=").append(errorMessage)
			.append(", params=").append(params)
			.append(", groupParams=").append(groupParams)
			.append(", group=").append(group)
			.append("]")
			.toString();
	}

	/**
	 * Gets the full error message.
	 *
	 * @return the fullErrorMessage
	 */
	@JsonbTransient
	public String getFullErrorMessage() {
		StringSubstitutor sub = new StringSubstitutor(this.params, "{", "}");
		return this.code + " - " + sub.replace(this.errorMessage);
	}
	
}
