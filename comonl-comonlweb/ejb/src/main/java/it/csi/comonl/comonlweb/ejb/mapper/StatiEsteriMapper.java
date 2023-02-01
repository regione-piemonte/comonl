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
import org.mapstruct.Mapping;

import it.csi.comonl.comonlweb.ejb.entity.ComTStatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between StatiEsteri and ComTStatiEsteri
 */
@Mapper
public interface StatiEsteriMapper extends BaseMapperInterface<StatiEsteri, ComTStatiEsteri> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "dsComTStatiEsteri", target = "dsStatiEsteri")
	StatiEsteri toModel(ComTStatiEsteri entity);

	@Override
	@IterableMapping(elementTargetType = StatiEsteri.class)
	List<StatiEsteri> toModels(Collection<ComTStatiEsteri> entities);

	@Override
	@IterableMapping(elementTargetType = ComTStatiEsteri.class)
	List<ComTStatiEsteri> toEntities(Collection<StatiEsteri> models);

}
