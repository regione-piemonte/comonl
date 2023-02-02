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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoRuolo;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoRuolo;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoRuolo and ComTTipoRuolo
 */
@Mapper
public interface TipoRuoloMapper extends BaseMapperInterface<TipoRuolo, ComTTipoRuolo> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoRuolo toModel(ComTTipoRuolo entity);

	@Override
	@IterableMapping(elementTargetType = TipoRuolo.class)
	List<TipoRuolo> toModels(Collection<ComTTipoRuolo> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoRuolo.class)
	List<ComTTipoRuolo> toEntities(Collection<TipoRuolo> models);

}
