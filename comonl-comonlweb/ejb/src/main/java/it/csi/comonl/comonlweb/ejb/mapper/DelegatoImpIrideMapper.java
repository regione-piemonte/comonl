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

import it.csi.comonl.comonlweb.ejb.entity.ComDDelegatoImpIride;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpIride;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between DelegatoImpIride and ComDDelegatoImpIride
 */
@Mapper
public interface DelegatoImpIrideMapper extends BaseMapperInterface<DelegatoImpIride, ComDDelegatoImpIride> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	DelegatoImpIride toModel(ComDDelegatoImpIride entity);

	@Override
	@IterableMapping(elementTargetType = DelegatoImpIride.class)
	List<DelegatoImpIride> toModels(Collection<ComDDelegatoImpIride> entities);

	@Override
	@IterableMapping(elementTargetType = ComDDelegatoImpIride.class)
	List<ComDDelegatoImpIride> toEntities(Collection<DelegatoImpIride> models);

}
