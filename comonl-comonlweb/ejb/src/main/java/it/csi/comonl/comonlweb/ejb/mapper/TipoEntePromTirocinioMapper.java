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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoEntePromTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoEntePromTirocinio;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoEntePromTirocinio and ComTTipoEntePromTirocinio
 */
@Mapper
public interface TipoEntePromTirocinioMapper extends BaseMapperInterface<TipoEntePromTirocinio, ComTTipoEntePromTirocinio> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoEntePromTirocinio toModel(ComTTipoEntePromTirocinio entity);

	@Override
	@IterableMapping(elementTargetType = TipoEntePromTirocinio.class)
	List<TipoEntePromTirocinio> toModels(Collection<ComTTipoEntePromTirocinio> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoEntePromTirocinio.class)
	List<ComTTipoEntePromTirocinio> toEntities(Collection<TipoEntePromTirocinio> models);

}
