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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoLavoratore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoLavoratore;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoLavoratore and ComTTipoLavoratore
 */
@Mapper
public interface TipoLavoratoreMapper extends BaseMapperInterface<TipoLavoratore, ComTTipoLavoratore> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoLavoratore toModel(ComTTipoLavoratore entity);

	@Override
	@IterableMapping(elementTargetType = TipoLavoratore.class)
	List<TipoLavoratore> toModels(Collection<ComTTipoLavoratore> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoLavoratore.class)
	List<ComTTipoLavoratore> toEntities(Collection<TipoLavoratore> models);

}
