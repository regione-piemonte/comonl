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
package it.csi.comonl.comonlweb.lib.external.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.ws.Service;

import org.apache.logging.log4j.Level;

import it.csi.comonl.comonlweb.lib.external.itf.BaseHelper;
import it.csi.comonl.comonlweb.lib.external.itf.ConfigurationParam;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedListImpl;
import it.csi.comonl.comonlweb.lib.util.soap.DefaultHandlerResolver;

/**
 * Base external services helper implementation
 */
public abstract class BaseHelperImpl implements BaseHelper {
	
	/** The logger */
	protected final LogUtil log = new LogUtil(getClass());

	/**
	 * Checks whether all the required parameters have been set
	 * @param params the params
	 * @param keys the keys to check
	 */
	protected void checkParameters(Map<String, String> params, ConfigurationParam... keys) {
		for(ConfigurationParam key : keys) {
			if(!params.containsKey(key.getParamName())) {
				throw new IllegalStateException("Key not found: " + key);
			}
		}
	}
	
	/**
	 * Checks whether all the required parameters have been set
	 * @param params the params
	 * @param keys the keys to check
	 */
	protected void checkParameters(Map<String, String> params, String... keys) {
		for(String key : keys) {
			if(!params.containsKey(key)) {
				throw new IllegalStateException("Key not found: " + key);
			}
		}
	}
	
	/**
	 * Gets the parameter
	 * @param params the params
	 * @param param the parameter to get
	 * @return the parameter value
	 */
	protected String getParameter(Map<String, String> params, ConfigurationParam param) {
		return params.get(param.getParamName());
	}
	
	/**
	 * Adds the handler resolvers
	 * @param params the params
	 * @param service the service
	 */
	protected void addHandlerResolver(Map<String, String> params, Service service) {
		final Level level = Level.INFO;
		service.setHandlerResolver(new DefaultHandlerResolver(level));
	}
	
	/**
	 * Transforms the paged list SIAC-like to paged list COMONL-like
	 * @param <A> the SIAC type
	 * @param <B> the COMONL type
	 * @param list the list
	 * @param total the total results
	 * @param page the page requested
	 * @param size the size requested
	 * @param mapper the mapper function
	 * @return the paged list
	 */
	protected <A, B> PagedList<B> toPagedList(List<A> list, Integer total, int page, int size, Function<A, B> mapper) {
		PagedList<B> content = new PagedListImpl<>(
			list
				.stream()
				.map(mapper)
				.collect(Collectors.toList())
		);
		content.setCurrentPage(page);
		content.setTotalElements(total.longValue());
		content.setTotalPages((long) Math.ceil((double)content.getTotalElements() / size));
		return content;
	}
	
	protected <A> PagedList<A> toPagedList(List<A> list, Integer total, int page, int size) {
		PagedList<A> content = new PagedListImpl<>(list);
		content.setCurrentPage(page);
		content.setTotalElements(total.longValue());
		content.setTotalPages((long) Math.ceil((double) content.getTotalElements() / size));
		return content;
	}
	
}
