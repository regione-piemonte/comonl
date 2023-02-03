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
package it.csi.comonl.comonlweb.lib.util.collection;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Utilities for collections
 */
public final class CollectionUtils {
	
	/** Private constructor */
	private CollectionUtils() {
		// Prevent instantiation
	}

	/**
	 * Maps a collection to a stream, null-save
	 * @param <T> the collection type
	 * @param collection the collection
	 * @return the stream
	 */
	public static <T> Stream<T> collectionToStream(Collection<T> collection) {
		return Optional.ofNullable(collection)
			.map(Collection::stream)
			.orElseGet(Stream::empty);
	}
}
