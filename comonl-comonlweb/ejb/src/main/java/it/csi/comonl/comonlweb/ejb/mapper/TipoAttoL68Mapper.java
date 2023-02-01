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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoAttoL68;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoAttoL68;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoAttoL68 and ComTTipoAttoL68
 */
@Mapper
public interface TipoAttoL68Mapper extends BaseMapperInterface<TipoAttoL68, ComTTipoAttoL68> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoAttoL68 toModel(ComTTipoAttoL68 entity);

	@Override
	@IterableMapping(elementTargetType = TipoAttoL68.class)
	List<TipoAttoL68> toModels(Collection<ComTTipoAttoL68> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoAttoL68.class)
	List<ComTTipoAttoL68> toEntities(Collection<TipoAttoL68> models);

}
