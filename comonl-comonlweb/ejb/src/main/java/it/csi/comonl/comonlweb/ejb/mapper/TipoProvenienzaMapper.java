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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoProvenienza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoProvenienza;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoProvenienza and ComTTipoProvenienza
 */
@Mapper
public interface TipoProvenienzaMapper extends BaseMapperInterface<TipoProvenienza, ComTTipoProvenienza> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoProvenienza toModel(ComTTipoProvenienza entity);

	@Override
	@IterableMapping(elementTargetType = TipoProvenienza.class)
	List<TipoProvenienza> toModels(Collection<ComTTipoProvenienza> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoProvenienza.class)
	List<ComTTipoProvenienza> toEntities(Collection<TipoProvenienza> models);

}
