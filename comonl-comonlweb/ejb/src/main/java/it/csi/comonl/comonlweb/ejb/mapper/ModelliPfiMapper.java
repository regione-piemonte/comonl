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

import it.csi.comonl.comonlweb.ejb.entity.ComRModelliPfi;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ModelliPfi;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between ModelliPfi and ComRModelliPfi
 */
@Mapper
public interface ModelliPfiMapper extends BaseMapperInterface<ModelliPfi, ComRModelliPfi> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	ModelliPfi toModel(ComRModelliPfi entity);

	@Override
	@IterableMapping(elementTargetType = ModelliPfi.class)
	List<ModelliPfi> toModels(Collection<ComRModelliPfi> entities);

	@Override
	@IterableMapping(elementTargetType = ComRModelliPfi.class)
	List<ComRModelliPfi> toEntities(Collection<ModelliPfi> models);

}
