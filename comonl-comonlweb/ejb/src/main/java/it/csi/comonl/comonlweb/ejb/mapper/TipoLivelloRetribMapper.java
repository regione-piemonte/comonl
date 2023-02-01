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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoLivelloRetrib;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoLivelloRetrib;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoLivelloRetrib and ComTTipoLivelloRetrib
 */
@Mapper
public interface TipoLivelloRetribMapper extends BaseMapperInterface<TipoLivelloRetrib, ComTTipoLivelloRetrib> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoLivelloRetrib toModel(ComTTipoLivelloRetrib entity);

	@Override
	@IterableMapping(elementTargetType = TipoLivelloRetrib.class)
	List<TipoLivelloRetrib> toModels(Collection<ComTTipoLivelloRetrib> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoLivelloRetrib.class)
	List<ComTTipoLivelloRetrib> toEntities(Collection<TipoLivelloRetrib> models);

}
