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

import it.csi.comonl.comonlweb.ejb.entity.EntityTipoDelega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.TipoDelega;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoDelega and EntityTipoDelega
 */
@Mapper
public interface TipoDelegaMapper extends BaseMapperInterface<TipoDelega, EntityTipoDelega> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoDelega toModel(EntityTipoDelega entity);

	@Override
	@IterableMapping(elementTargetType = TipoDelega.class)
	List<TipoDelega> toModels(Collection<EntityTipoDelega> entities);

	@Override
	@IterableMapping(elementTargetType = EntityTipoDelega.class)
	List<EntityTipoDelega> toEntities(Collection<TipoDelega> models);

}
