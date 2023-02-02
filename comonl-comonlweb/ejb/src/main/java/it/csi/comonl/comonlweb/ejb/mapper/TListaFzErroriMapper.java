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

import it.csi.comonl.comonlweb.ejb.entity.ComRTListaFzErrori;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.TListaFzErrori;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TListaFzErrori and ComRTListaFzErrori
 */
@Mapper
public interface TListaFzErroriMapper extends BaseMapperInterface<TListaFzErrori, ComRTListaFzErrori> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TListaFzErrori toModel(ComRTListaFzErrori entity);

	@Override
	@IterableMapping(elementTargetType = TListaFzErrori.class)
	List<TListaFzErrori> toModels(Collection<ComRTListaFzErrori> entities);

	@Override
	@IterableMapping(elementTargetType = ComRTListaFzErrori.class)
	List<ComRTListaFzErrori> toEntities(Collection<TListaFzErrori> models);

}
