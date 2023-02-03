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
package it.csi.comonl.comonlweb.lib.util.soap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.logging.log4j.Level;

import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
/**
 * SOAP handler for request/response logging
 */
public class LogSOAPHandler implements SOAPHandler<SOAPMessageContext> {
	
	private final LogUtil log = new LogUtil(getClass());
	private final Level level;
	
	/**
	 * Constructor wrapping the level
	 * @param level the log level
	 */
	public LogSOAPHandler(Level level) {
		this.level = level;
	}

	@Override
	public boolean handleMessage(final SOAPMessageContext context) {
		final SOAPMessage msg = context.getMessage();
		final boolean request = Boolean.TRUE.equals(context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY));
		final String endpointAddress = (String) context.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
		
		if (request) {
			// This is a request message.
			logRequestMessage(endpointAddress, msg);
		} else {
			// This is the response message
			logResponseMessage(endpointAddress, msg);
		}
		return true;
	}

	@Override
	public boolean handleFault(final SOAPMessageContext context) {
		final String endpointAddress = (String) context.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
		logErrorMessage(endpointAddress, context.getMessage());
		return false;
	}

	/**
	 * Writes the message to the given appendable.
	 * 
	 * @param appendable the appendable to which add the message
	 * @param soapMessage the message to write
	 * @throws LogSOAPException in case of an exception during the logging phase
	 */
	private void writeMessage(Appendable appendable, final SOAPMessage soapMessage) throws LogSOAPException {
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			// Write the message to the output stream
			soapMessage.writeTo(baos);
			appendable.append(baos.toString("UTF-8"));
		} catch (IOException | SOAPException e) {
			throw new LogSOAPException("Exception in message write", e);
		}
	}
	
	/**
	 * Logs the request message
	 * @param endpointAddress 
	 * @param requestMessage the request
	 */
	private void logRequestMessage(String endpointAddress, final SOAPMessage requestMessage) {
		if(!log.isLevelEnabled(level)) {
			return;
		}
		final String methodName = "logRequestMessage";
		StringBuilder sb = new StringBuilder();
		sb.append("REQUEST:\n");
		sb.append("ENDPOINT: ").append(endpointAddress).append("\n");
		try {
			writeMessage(sb, requestMessage);
			log.log(methodName, level, sb.toString());
		} catch(LogSOAPException e) {
			log.error(methodName, "Caught exception for request message: " + e.getMessage(), e);
		}
	}
	
	/**
	 * Logs the response message
	 * @param endpointAddress 
	 * @param msg the response message
	 */
	private void logResponseMessage(String endpointAddress, final SOAPMessage msg) {
		if(!log.isLevelEnabled(level)) {
			return;
		}
		final String methodName = "logResponseMessage";
		StringBuilder sb = new StringBuilder();
		sb.append("RESPONSE:\n");
		sb.append("ENDPOINT: ").append(endpointAddress).append("\n");
		try {
			writeMessage(sb, msg);
			log.log(methodName, level, sb.toString());
		} catch(LogSOAPException e) {
			log.error(methodName, "Caught exception for response message: " + e.getMessage(), e);
		}
	}
	
	/**
	 * Logs the error message
	 * @param endpointAddress 
	 * @param msg the error message
	 */
	private void logErrorMessage(String endpointAddress, final SOAPMessage msg) {
		if(!log.isLevelEnabled(level)) {
			return;
		}
		final String methodName = "logErrorMessage";
		StringBuilder sb = new StringBuilder();
		sb.append("ERROR:\n");
		sb.append("ENDPOINT: ").append(endpointAddress).append("\n");
		try {
			writeMessage(sb, msg);
			log.log(methodName, level, sb.toString());
		} catch(LogSOAPException e) {
			log.error(methodName, "Caught exception for error message: " + e.getMessage(), e);
		}
	}

	@Override
	public void close(final MessageContext context) {
		// Not required for logging
	}

	@Override
	public Set<QName> getHeaders() {
		// Not required for logging
		return new HashSet<>();
	}
	
	/**
	 * Exception in SOAP logging
	 */
	public final static class LogSOAPException extends Exception {

		/** For serialization */
		private static final long serialVersionUID = -138656310859927654L;

		/**
		 * Exception in SOAP logging
		 * @param message the message
		 * @param cause the underlying cause
		 */
		public LogSOAPException(String message, Throwable cause) {
			super(message, cause);
		}
	}

}
