/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - SRV submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.srv.util.interceptor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.specimpl.BuiltResponse;

import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.lib.util.serialization.JsonUtility;
import it.csi.comonl.comonlweb.srv.util.annotation.Logged;

/**
 * Interceptor that logs an invocation
 */
@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {

	/** For serialization */
	private static final long serialVersionUID = -3961060405619482358L;
	private static final AtomicLong COUNTER = new AtomicLong();

	/**
	 * Checks the permissions
	 * 
	 * @param invocationContext the invocation context
	 * @return the invocation result
	 * @throws Exception in case of any exception
	 */
	@AroundInvoke
	public Object checkPermissions(InvocationContext invocationContext) throws Exception {
		// Create a logger with the correct name
		long requestId = COUNTER.incrementAndGet();
		Class<?> targetClass = InterceptorUtils.deProxy(invocationContext);
		String methodName = invocationContext.getMethod().getName();
		LogUtil log = new LogUtil(targetClass);
		long initTime = System.currentTimeMillis();
		long endTime = initTime;

		try {
			log.info(methodName, () -> "[REQ " + requestId + "] Start");
			Object response = invocationContext.proceed();
			// Before the log instruction so as not to steal time
			endTime = System.currentTimeMillis();

			if (log.isInfoEnabled()) {

				if (response instanceof BuiltResponse) {
					BuiltResponse builtResponse = (BuiltResponse) response;
					log.info(methodName,
							() -> "[REQ " + requestId + "] End. InputParameters: "
									+ getInputParameters(invocationContext) + ". Response: "
									+ JsonUtility.serializePerLog(builtResponse.getEntity()));
				} else {

					log.info(methodName, () -> "[REQ " + requestId + "] End. InputParameters: "
							+ getInputParameters(invocationContext) + ". Response: " + JsonUtility.serializePerLog(response));
					}

			}
			return response;

		} catch (Exception e) {
			// Before the log instruction so as not to steal time
			endTime = System.currentTimeMillis();
			log.error(methodName, () -> "[REQ " + requestId + "] End. InputParameters: "
					+ getInputParameters(invocationContext) + ". Error: " + e.getMessage(), e);
			throw e;

		} finally {
			// Workaround for lambda problems with non-effectively final data
			long finalEndTime = endTime;
			log.info(methodName, () -> "[REQ " + requestId + "] Elapsed time: " + (finalEndTime - initTime) + " ms");
		}

	}

	/**
	 * Retrieves and formats the input parameters
	 * 
	 * @param invocationContext the invocation context
	 * @return the parameters
	 */
	private String getInputParameters(InvocationContext invocationContext) {
		return Arrays.stream(invocationContext.getParameters()).filter(
				p -> !(p instanceof SecurityContext || p instanceof HttpHeaders || p instanceof HttpServletRequest))
				.map(JsonUtility::serialize).collect(Collectors.joining(";"));
	}

}
