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

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Partition of a given list
 * @param <T> the type
 */
public class Partition<T> extends AbstractList<List<T>> {

	private final List<T> list;
	private final int chunkSize;

	/**
	 * Partition a list to the given size
	 * @param <T> the type
	 * @param list the list
	 * @param chunkSize the size
	 * @return the partition
	 */
	public static <T> Partition<T> ofSize(List<T> list, int chunkSize) {
		return new Partition<>(list, chunkSize);
	}

	/**
	 * Partition for a given list and chunk size
	 * @param list the list
	 * @param chunkSize the chunk size
	 */
	public Partition(List<T> list, int chunkSize) {
		this.list = new ArrayList<>(list);
		this.chunkSize = chunkSize;
	}

	@Override
	public List<T> get(int index) {
		int start = index * chunkSize;
		int end = Math.min(start + chunkSize, list.size());
		if (start > end) {
			throw new IndexOutOfBoundsException("Index " + index + " is out of the list range <0," + (size() - 1) + ">");
		}
		return new ArrayList<>(list.subList(start, end));
	}

	@Override
	public int size() {
		return (int) Math.ceil((double) list.size() / (double) chunkSize);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Partition)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Partition<T> other = (Partition<T>) obj;
		return chunkSize == other.chunkSize && Objects.equals(list, other.list);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + chunkSize;
		result = prime * result + Objects.hash(list);
		return result;
	}
}
