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

import it.csi.comonl.comonlweb.ejb.entity.ComDCapacitaFormativaModa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.CapacitaFormativaModa;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between CapacitaFormativaModa and ComDCapacitaFormativaModa
 */
@Mapper
public interface CapacitaFormativaModaMapper extends BaseMapperInterface<CapacitaFormativaModa, ComDCapacitaFormativaModa> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	CapacitaFormativaModa toModel(ComDCapacitaFormativaModa entity);

	@Override
	@IterableMapping(elementTargetType = CapacitaFormativaModa.class)
	List<CapacitaFormativaModa> toModels(Collection<ComDCapacitaFormativaModa> entities);

	@Override
	@IterableMapping(elementTargetType = ComDCapacitaFormativaModa.class)
	List<ComDCapacitaFormativaModa> toEntities(Collection<CapacitaFormativaModa> models);

}
