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

import it.csi.comonl.comonlweb.ejb.entity.ComTTooltip;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Tooltip;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Tooltip and ComTTooltip
 */
@Mapper
public interface TooltipMapper extends BaseMapperInterface<Tooltip, ComTTooltip> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	Tooltip toModel(ComTTooltip entity);

	@Override
	@IterableMapping(elementTargetType = Tooltip.class)
	List<Tooltip> toModels(Collection<ComTTooltip> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTooltip.class)
	List<ComTTooltip> toEntities(Collection<Tooltip> models);

}
