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
package it.csi.comonl.comonlweb.ejb.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.csi.comonl.comonlweb.ejb.entity.ComTComune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Comune and ComTComune
 */
@Mapper(uses = {CpiMapper.class, ProvinciaMapper.class})
public interface ComuneMapper extends BaseMapperInterface<Comune, ComTComune> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comTCpi", target = "cpi")
	@Mapping(source = "comTProvincia", target = "provincia")
	Comune toModel(ComTComune entity);

	@Override
	@IterableMapping(elementTargetType = Comune.class)
	List<Comune> toModels(Collection<ComTComune> entities);

	@Override
	@IterableMapping(elementTargetType = ComTComune.class)
	List<ComTComune> toEntities(Collection<Comune> models);

}
