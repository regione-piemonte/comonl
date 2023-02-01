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

import it.csi.comonl.comonlweb.ejb.entity.ComDCapacitaFormativaModb;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.CapacitaFormativaModb;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between CapacitaFormativaModb and ComDCapacitaFormativaModb
 */
@Mapper
public interface CapacitaFormativaModbMapper extends BaseMapperInterface<CapacitaFormativaModb, ComDCapacitaFormativaModb> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	CapacitaFormativaModb toModel(ComDCapacitaFormativaModb entity);

	@Override
	@IterableMapping(elementTargetType = CapacitaFormativaModb.class)
	List<CapacitaFormativaModb> toModels(Collection<ComDCapacitaFormativaModb> entities);

	@Override
	@IterableMapping(elementTargetType = ComDCapacitaFormativaModb.class)
	List<ComDCapacitaFormativaModb> toEntities(Collection<CapacitaFormativaModb> models);

}
