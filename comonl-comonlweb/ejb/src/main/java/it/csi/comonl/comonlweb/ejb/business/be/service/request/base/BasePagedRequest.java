/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.business.be.service.request.base;

import it.csi.comonl.comonlweb.lib.util.pagination.Sort;

/**
 * Base paged request class.
 */
public abstract class BasePagedRequest implements BaseRequest {

	/** The size. */
	protected int size;
	/** The page. */
	protected final int page;
	/** The size. */
	protected final Sort sort;

	/**
	 * Instantiates a new base paged request.
	 *
	 * @param size the size
	 * @param page the page
	 */
	protected BasePagedRequest(Integer size, Integer page) {
		this(size, page, null, null);
	}

	/**
	 * Instantiates a new base paged request.
	 *
	 * @param size      the size
	 * @param page      the page
	 * @param sort      the sort
	 * @param direction the direction
	 */
	protected BasePagedRequest(Integer size, Integer page, String sort, String direction) {
		this.size = size != null ? size.intValue() : 0;
		this.page = page != null ? page.intValue() : 0;
		this.sort = new Sort(sort, direction);
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Gets the page.
	 *
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @return the sort
	 */
	public Sort getSort() {
		return sort;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
