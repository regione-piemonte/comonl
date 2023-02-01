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
package it.csi.comonl.comonlweb.ejb.business.be.dad;

import java.util.function.Function;

import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.util.jpa.Page;
import it.csi.comonl.comonlweb.lib.dto.BaseDto;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedListImpl;

/**
 * Base Data Access Delegate (DAD) class: maps between Entities and Models
 */
public abstract class BaseDad {

	/** Logger */
	protected final LogUtil log = new LogUtil(getClass());

	@Inject
	private ComDComunicazioneDao comonlDao;
	
	/**
	 * Flush of the entity manager
	 */
	public void flush() {
		comonlDao.flush();
	}

	/**
	 * Flush And Clear of the entity manager
	 */
	public void flushAndClear() {
		comonlDao.flushAndClear();
	}

	/**
	 * Converts a page of data from the persistence layer to a paged list for the
	 * business layer
	 * 
	 * @param <D>        the Model type
	 * @param <E>        the Entity type
	 * @param elements   the persistence layer elements
	 * @param pageNumber the page number
	 * @param pageSize   the page size
	 * @param converter  the converter function
	 * @return the paged list corresponding to the given page
	 */
	protected <D, E> PagedList<D> toPagedList(Page<E> elements, int pageNumber, int pageSize, Function<E, D> converter) {
		PagedList<D> pagedList = new PagedListImpl<>();
		pagedList.setTotalElements(elements.getTotalElements());
		if (pageSize > 0) {
			pagedList.setCurrentPage(pageNumber);
			pagedList.setTotalPages((long) Math.ceil((double) elements.getTotalElements() / pageSize));
		} else {
			pagedList.setCurrentPage(0);
			pagedList.setTotalPages(1);
		}

		elements.getContent().stream().map(converter::apply).forEach(pagedList::add);
		return pagedList;
	}

	
	protected <E> PagedList<E> toPagedList(Page<E> elements, int pageNumber, int pageSize) {
		PagedList<E> pagedList = new PagedListImpl<>();
		pagedList.setTotalElements(elements.getTotalElements());
		if (pageSize > 0) {
			pagedList.setCurrentPage(pageNumber);
			pagedList.setTotalPages((long) Math.ceil((double) elements.getTotalElements() / pageSize));
		} else {
			pagedList.setCurrentPage(0);
			pagedList.setTotalPages(1);
		}
		elements.getContent().stream().forEach(pagedList::add);		
		return pagedList;
	}
	
	
	/**
	 * Extracts, null-safe, the id from the model
	 * 
	 * @param model the model
	 * @return the id
	 */
	protected <K> K getId(BaseDto<K> model) {
		return model != null ? model.getId() : null;
	}
}
