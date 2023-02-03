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
package it.csi.comonl.comonlweb.lib.util.log;

import java.util.function.Supplier;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.csi.comonl.comonlweb.lib.util.serialization.JAXBUtility;

/**
 * Centralized logging utility
 */
public class LogUtil {

	/** Log pattern */
	private static final String PATTERN = "[%s.%s] - %s";

	/** The logged category */
	private final String category;
	/** The true logger */
	private final Logger logger;

	/**
	 * Constructor
	 * @param clazz the class to be logged
	 */
	public LogUtil(Class<?> clazz) {
		logger = LogManager.getLogger(clazz);
		category = clazz.getSimpleName();
	}
	
	/**
	 * Constructor
	 * @param category the category to be logged
	 */
	public LogUtil(String category) {
		logger = LogManager.getLogger(category);
		this.category = category;
	}

	/**
	 * Log at TRACE level
	 * @param methodName the method name
	 * @param message the message
	 */
	public void trace(String methodName, Object message) {
		log(methodName, Level.TRACE, message, null);
	}
	/**
	 * Log at DEBUG level
	 * @param methodName the method name
	 * @param message the message
	 */
	public void debug(String methodName, Object message) {
		log(methodName, Level.DEBUG, message, null);
	}
	/**
	 * Log at INFO level
	 * @param methodName the method name
	 * @param message the message
	 */
	public void info(String methodName, Object message) {
		log(methodName, Level.INFO, message, null);
	}
	
	/**
	 * Log at WARN level
	 * @param methodName the method name
	 * @param message the message
	 */
	public void warn(String methodName, Object message) {
		log(methodName, Level.WARN, message, null);
	}
	
	/**
	 * Log at WARN level
	 * @param methodName the method name
	 * @param message the message
	 * @param t the throwable
	 */
	public void warn(String methodName, Object message, Throwable t) {
		log(methodName, Level.WARN, message, t);
	}
	
	/**
	 * Log at ERROR level
	 * @param methodName the method name
	 * @param message the message
	 */
	public void error(String methodName, Object message) {
		log(methodName, Level.ERROR, message, null);
	}
	
	public void error(String methodName, Throwable ex) {
	    log(methodName, Level.ERROR, "error", ex);
	}

	/**
	 * Log at ERROR level
	 * @param methodName the method name
	 * @param message the message
	 * @param t the throwable
	 */
	public void error(String methodName, Object message, Throwable t) {
		log(methodName, Level.ERROR, message, t);
	}
	
	/**
	 * Log at FATAL level
	 * @param methodName the method name
	 * @param message the message
	 */
	public void fatal(String methodName, Object message) {
		log(methodName, Level.FATAL, message, null);
	}
	/**
	 * Log at FATAL level
	 * @param methodName the method name
	 * @param message the message
	 * @param t the throwable
	 */
	public void fatal(String methodName, Object message, Throwable t) {
		log(methodName, Level.FATAL, message, t);
	}
	/**
	 * Log at the given level
	 * @param methodName the method name
	 * @param level the level to log to
	 * @param message the message
	 */
	public void log(String methodName, Level level, Object message) {
		log(methodName, level, message, null);
	}
	/**
	 * Log at the given level
	 * @param methodName the method name
	 * @param level the level to log to
	 * @param message the message
	 * @param t the throwable
	 */
	public void log(String methodName, Level level, Object message, Throwable t) {
		if(isLevelEnabled(level)) {
			logger.log(level, formatMessage(methodName, message), t);
		}
	}

	/**
	 * @return whether TRACE log is enabled
	 */
	public boolean isTraceEnabled(){
		return logger.isTraceEnabled();
	}
	/**
	 * @return whether DEBUG log is enabled
	 */
	public boolean isDebugEnabled(){
		return logger.isDebugEnabled();
	}
	/**
	 * @return whether INFO log is enabled
	 */
	public boolean isInfoEnabled(){
		return logger.isInfoEnabled();
	}
	/**
	 * @return whether WARN log is enabled
	 */
	public boolean isWarnEnabled(){
		return logger.isEnabled(Level.WARN);
	}
	/**
	 * @return whether ERROR log is enabled
	 */
	public boolean isErrorEnabled(){
		return logger.isEnabled(Level.ERROR);
	}
	/**
	 * @return whether FATAL log is enabled
	 */
	public boolean isFatalEnabled(){
		return logger.isEnabled(Level.FATAL);
	}
	/**
	 * @param level the level to c
	 * @return whether the level is enabled for logging
	 */
	public boolean isLevelEnabled(Level level){
		return logger.isEnabled(level);
	}

	/**
	 * Log at TRACE level
	 * @param methodName the method name
	 * @param supplier the message supplier
	 */
	public void trace(String methodName, Supplier<Object> supplier) {
		log(methodName, Level.TRACE, supplier);
	}
	/**
	 * Log at DEBUG level
	 * @param methodName the method name
	 * @param supplier the message supplier
	 */
	public void debug(String methodName, Supplier<Object> supplier) {
		log(methodName, Level.DEBUG, supplier);
	}
	/**
	 * Log at INFO level
	 * @param methodName the method name
	 * @param supplier the message supplier
	 */
	public void info(String methodName, Supplier<Object> supplier) {
		log(methodName, Level.INFO, supplier);
	}
	/**
	 * Log at WARN level
	 * @param methodName the method name
	 * @param supplier the message supplier
	 */
	public void warn(String methodName, Supplier<Object> supplier) {
		log(methodName, Level.WARN, supplier);
	}
	/**
	 * Log at WARN level
	 * @param methodName the method name
	 * @param supplier the message supplier
	 * @param t the throwable
	 */
	public void warn(String methodName, Supplier<Object> supplier, Throwable t) {
		log(methodName, Level.WARN, supplier, t);
	}
	/**
	 * Log at ERROR level
	 * @param methodName the method name
	 * @param supplier the message supplier
	 */
	public void error(String methodName, Supplier<Object> supplier) {
		log(methodName, Level.ERROR, supplier, null);
	}
	/**
	 * Log at ERROR level
	 * @param methodName the method name
	 * @param supplier the message supplier
	 * @param t the throwable
	 */
	public void error(String methodName, Supplier<Object> supplier, Throwable t) {
		log(methodName, Level.ERROR, supplier, t);
	}
	/**
	 * Log at FATAL level
	 * @param methodName the method name
	 * @param supplier the message supplier
	 */
	public void fatal(String methodName, Supplier<Object> supplier) {
		log(methodName, Level.FATAL, supplier, null);
	}
	/**
	 * Log at FATAL level
	 * @param methodName the method name
	 * @param supplier the message supplier
	 * @param t the throwable
	 */
	public void fatal(String methodName, Supplier<Object> supplier, Throwable t) {
		log(methodName, Level.FATAL, supplier, t);
	}
	/**
	 * Log at the given level
	 * @param methodName the method name
	 * @param level the log level
	 * @param supplier the message supplier
	 */
	public void log(String methodName, Level level, Supplier<Object> supplier) {
		log(methodName, level, supplier, null);
	}
	/**
	 * Log at the given level
	 * @param methodName the method name
	 * @param level the log level
	 * @param supplier the message supplier
	 * @param t the throwable
	 */
	public void log(String methodName, Level level, Supplier<Object> supplier, Throwable t) {
		if(isLevelEnabled(level)) {
			logger.log(level, formatMessage(methodName, supplier.get()), t);
		}
	}

	/**
	 * Formats the message
	 * @param methodName the method name
	 * @param message the message
	 * @return the formatted message
	 */
	private String formatMessage(String methodName, Object message) {
		return String.format(PATTERN, category, methodName, message);
	}

	/**
	 * Logs a complex @XmlType object.
	 *
	 * @param methodName the method calling this log
	 * @param msg the message to log
	 * @param obj the @XmlType object to log
	 */
	public void logXmlTypeObject(String methodName, String msg, Object obj) {
		if (!isDebugEnabled()) {
			// Exit early
			return;
		}
		String result;
		try {
			result = JAXBUtility.marshall(obj);
		} catch (RuntimeException e) {
			result = e.getMessage();
			warn(methodName, "Cannot log " + msg + ": " + result, e);
			return;
		}
		debug(methodName, msg + ": " + result);
	}

}
