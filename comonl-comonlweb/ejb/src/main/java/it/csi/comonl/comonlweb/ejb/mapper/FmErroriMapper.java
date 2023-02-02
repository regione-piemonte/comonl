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
package it.csi.comonl.comonlweb.ejb.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import it.csi.comonl.comonlweb.ejb.entity.ComDFmErrori;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FmErrori;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between FmErrori and ComDFmErrori
 */
@Mapper
public interface FmErroriMapper extends BaseMapperInterface<FmErrori, ComDFmErrori> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	FmErrori toModel(ComDFmErrori entity);

	@Override
	@IterableMapping(elementTargetType = FmErrori.class)
	List<FmErrori> toModels(Collection<ComDFmErrori> entities);

	@Override
	@IterableMapping(elementTargetType = ComDFmErrori.class)
	List<ComDFmErrori> toEntities(Collection<FmErrori> models);

}
