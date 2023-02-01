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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoContratti and ComTTipoContratti
 */
@Mapper
public interface TipoContrattiMapper extends BaseMapperInterface<TipoContratti, ComTTipoContratti> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoContratti toModel(ComTTipoContratti entity);

	@Override
	@IterableMapping(elementTargetType = TipoContratti.class)
	List<TipoContratti> toModels(Collection<ComTTipoContratti> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoContratti.class)
	List<ComTTipoContratti> toEntities(Collection<TipoContratti> models);

}
