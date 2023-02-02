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
import org.mapstruct.Mapping;

import it.csi.comonl.comonlweb.ejb.entity.ComDFzErrori;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FzErrori;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between FzErrori and ComDFzErrori
 */
@Mapper
public interface FzErroriMapper extends BaseMapperInterface<FzErrori, ComDFzErrori> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comDUplDocumenti", target = "uplDocumenti")
	FzErrori toModel(ComDFzErrori entity);

	@Override
	@IterableMapping(elementTargetType = FzErrori.class)
	List<FzErrori> toModels(Collection<ComDFzErrori> entities);

	@Override
	@IterableMapping(elementTargetType = ComDFzErrori.class)
	List<ComDFzErrori> toEntities(Collection<FzErrori> models);

}
