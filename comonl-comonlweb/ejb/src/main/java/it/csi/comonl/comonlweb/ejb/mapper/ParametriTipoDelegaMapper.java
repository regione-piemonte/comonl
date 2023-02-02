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

import it.csi.comonl.comonlweb.ejb.entity.EntityParametriTipoDelega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ParametriTipoDelega;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between ParametriTipoDelega and EntityParametriTipoDelega
 */
@Mapper
public interface ParametriTipoDelegaMapper extends BaseMapperInterface<ParametriTipoDelega, EntityParametriTipoDelega> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	ParametriTipoDelega toModel(EntityParametriTipoDelega entity);

	@Override
	@IterableMapping(elementTargetType = ParametriTipoDelega.class)
	List<ParametriTipoDelega> toModels(Collection<EntityParametriTipoDelega> entities);

	@Override
	@IterableMapping(elementTargetType = EntityParametriTipoDelega.class)
	List<EntityParametriTipoDelega> toEntities(Collection<ParametriTipoDelega> models);

}
