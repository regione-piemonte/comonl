/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.util.conf;

/**
 * The Enum ConfigurationValue.
 */
public enum ConfigurationValue {

	// MAIL
	/** The mail smtp auth. */
	MAIL_SMTP_AUTH("mail.smtp.auth"),
	/** The mail smtp starttls enable. */
	MAIL_SMTP_STARTTLS_ENABLE("mail.smtp.starttls.enable"),
	/** The mail smtp host. */
	MAIL_SMTP_HOST("mail.smtp.host"),
	/** The mail smtp port. */
	MAIL_SMTP_PORT("mail.smtp.port"),
	/** The mail username. */
	MAIL_USERNAME("mail.username"),
	/** The mail password. */
	MAIL_PASSWORD("mail.password"),
	/** The mail from. */
	MAIL_FROM("mail.from"),
	/** The mail from name. */
	MAIL_FROM_NAME("mail.from.name"),
	/** The path of commax response (if null read parameter frm DB)*/
	MAIL_COMMAX_FILEPATH_REPORT("mail.report.path"),


	// APPLICATION
	/** The application-wide debug mode */
	DEBUG_MODE("application.debug-mode"),
	/** CORS enable */
	CORSFILTER_ENABLECORS("corsfilter.enableCors"),
	/** AUTH - dev mode */
	AUTHFILTER_DEVMODE("authfilter.devMode"),
	/** AUTH - Adapter name */
	AUTHFILTER_AUTHADAPTERNAME("authfilter.authAdapterName"),
	/** AUTH - IRIDE cookie name */
	AUTHFILTER_IRIDE_COOKIENAME("authfilter.iride.cookieName"),
	FILE_SYSTEM("filesystem.path"),
	FILE_SYSTEM_EMAIL_ALLEGATI("filesystem.allegatimail.path"),
	FILE_SYSTEM_PDF("filesystem.pdf.path"),
	APPLICATION_RUNTIME("application.runtime"),
	;

	/** The property name. */
	private final String propertyName;

	/**
	 * Constructor.
	 *
	 * @param propertyName the property name
	 */
	private ConfigurationValue(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * Gets the property name.
	 *
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

}
