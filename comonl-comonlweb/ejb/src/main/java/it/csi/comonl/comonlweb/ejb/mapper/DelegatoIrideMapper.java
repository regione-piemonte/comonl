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

import it.csi.comonl.comonlweb.ejb.entity.ComDDelegatoIride;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoIride;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between DelegatoIride and ComDDelegatoIride
 */
@Mapper
public interface DelegatoIrideMapper extends BaseMapperInterface<DelegatoIride, ComDDelegatoIride> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	DelegatoIride toModel(ComDDelegatoIride entity);

	@Override
	@IterableMapping(elementTargetType = DelegatoIride.class)
	List<DelegatoIride> toModels(Collection<ComDDelegatoIride> entities);

	@Override
	@IterableMapping(elementTargetType = ComDDelegatoIride.class)
	List<ComDDelegatoIride> toEntities(Collection<DelegatoIride> models);

}
