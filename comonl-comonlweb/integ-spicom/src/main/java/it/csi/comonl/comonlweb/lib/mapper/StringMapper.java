/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION SPICOM submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;

import it.csi.comonl.comonlweb.lib.mapper.annotation.TrimmedString;

/**
 * Mapper between Strings
 */
@Mapper
public interface StringMapper {

	/**
	 * Maps a string
	 * 
	 * @param input the input string
	 * @return the mapped value
	 */
	@TrimmedString
	default String mapString(String input) {
		return StringUtils.trimToNull(input);
	}

}
