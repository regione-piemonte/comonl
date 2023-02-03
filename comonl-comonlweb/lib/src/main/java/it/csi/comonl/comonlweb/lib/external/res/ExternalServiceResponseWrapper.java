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
package it.csi.comonl.comonlweb.lib.external.res;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for external service invocation response
 * @param <R> the wrapped response data type
 */
public class ExternalServiceResponseWrapper<R> {

	private boolean success;
	private List<String> errors = new ArrayList<>();
	private List<String> messages = new ArrayList<>();
	private R response;

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}
	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors != null ? errors : new ArrayList<>();
	}
	/**
	 * @return the messages
	 */
	public List<String> getMessages() {
		return messages;
	}
	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<String> messages) {
		this.messages = messages != null ? messages : new ArrayList<>();
	}
	/**
	 * @return the response
	 */
	public R getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(R response) {
		this.response = response;
	}
	/**
	 * Adds an error
	 * @param error the error to add
	 * @return whether the error was added
	 */
	public boolean addError(String error) {
		return this.errors.add(error);
	}
	/**
	 * Adds a message
	 * @param message the message to add
	 * @return whether the message was added
	 */
	public boolean addMessage(String message) {
		return this.messages.add(message);
	}
}
