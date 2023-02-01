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
package it.csi.comonl.comonlweb.ejb.util.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Implementor for a query Page
 * @param <T> the retrieved type
 */
public class PageImpl<T> implements Page<T> {

	/** The total elements */
	private final long totalElements;
	/** The underlying list */
	private final List<T> list;

	/**
	 * Constructor
	 * @param totalElements the element number
	 */
	public PageImpl(long totalElements) {
		this(totalElements, new ArrayList<>());
	}
	/**
	 * Constructor
	 * @param totalElements the element number
	 * @param list the list
	 */
	public PageImpl(long totalElements, List<T> list) {
		this.totalElements = totalElements;
		this.list = list;
	}
	@Override
	public long getTotalElements() {
		return totalElements;
	}
	@Override
	public Collection<T> getContent() {
		return list;
	}
	@Override
	public Stream<T> stream() {
		return list.stream();
	}
	@Override
	public Stream<T> parallelStream() {
		return list.parallelStream();
	}

}
