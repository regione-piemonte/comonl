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

import java.util.List;
import java.util.stream.Stream;

/**
 * Paginated list
 * @param <T> the contained type
 */
public interface PagedList<T> extends Iterable<T> {

	/**
	 * @return the current page
	 */
	long getCurrentPage();
	/**
	 * @return the total pages
	 */
	long getTotalPages();
	/**
	 * @return the total elements
	 */
	long getTotalElements();
	/**
	 * @return the content
	 */
	List<T> getList();

	/**
	 * @param currentPage the current page to set
	 */
	void setCurrentPage(long currentPage);
	/**
	 * @param totalPages the total pages to set
	 */
	void setTotalPages(long totalPages);
	/**
	 * @param totalElements the total elements to set
	 */
	void setTotalElements(long totalElements);
	/**
	 * @return whether the list is empty
	 */
	boolean isEmpty();

	/**
	 * @param index the index
	 * @return the element at the index
	 */
	T get(int index);
	/**
	 * @param e the element to add
	 * @return whether the element was added
	 */
	boolean add(T e);
	/**
	 * @return the stream
	 */
	Stream<T> stream();
	/**
	 * @return the parallel stream
	 */
	Stream<T> paralleltream();
}
