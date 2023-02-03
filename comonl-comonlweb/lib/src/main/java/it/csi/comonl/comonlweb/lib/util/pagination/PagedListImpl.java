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
package it.csi.comonl.comonlweb.lib.util.pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

import javax.json.bind.annotation.JsonbTransient;

/**
 * Implementation of a PagedList
 * @param <T> the underlying type
 */
public class PagedListImpl<T> implements PagedList<T> {

	/** The containing list */
	private final List<T> list;
	/** Ther current page */
	private long currentPage;
	/** The total pages */
	private long totalPages;
	/** The total elements */
	private long totalElements;

	/**
	 * Constructor
	 */
	public PagedListImpl() {
		super();
		this.list = new ArrayList<>();
		this.currentPage = 0;
		this.totalPages = 1;
		this.totalElements = 0;
	}
	/**
	 * Wrap constructor
	 * @param c the case collection
	 */
	public PagedListImpl(Collection<? extends T> c) {
		this.list = new ArrayList<>(c);
		this.currentPage = 0;
		this.totalPages = 1;
		this.totalElements = c.size();
	}
	@Override
	public long getCurrentPage() {
		return currentPage;
	}
	@Override
	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}
	@Override
	public long getTotalPages() {
		return totalPages;
	}
	@Override
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	@Override
	public long getTotalElements() {
		return totalElements;
	}
	@Override
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	@Override
	public List<T> getList() {
		return list;
	}
	@Override
	@JsonbTransient
	public boolean isEmpty() {
		return list.isEmpty() || totalElements == 0L;
	}
	@Override
	public T get(int index) {
		return list.get(index);
	}
	@Override
	public boolean add(T e) {
		return list.add(e);
	}
	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}
	@Override
	public Spliterator<T> spliterator() {
		return list.spliterator();
	}
	@Override
	public Stream<T> stream() {
		return list.stream();
	}
	@Override
	public Stream<T> paralleltream() {
		return list.parallelStream();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PagedListImpl [currentPage=").append(currentPage).append(", totalPages=").append(totalPages)
				.append(", totalElements=").append(totalElements).append(", content=").append(list)
				.append("]");
		return builder.toString();
	}
}
