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

import it.csi.comonl.comonlweb.ejb.entity.ComTEntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between EntePrevidenziale and ComTEntePrevidenziale
 */
@Mapper
public interface EntePrevidenzialeMapper extends BaseMapperInterface<EntePrevidenziale, ComTEntePrevidenziale> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	EntePrevidenziale toModel(ComTEntePrevidenziale entity);

	@Override
	@IterableMapping(elementTargetType = EntePrevidenziale.class)
	List<EntePrevidenziale> toModels(Collection<ComTEntePrevidenziale> entities);

	@Override
	@IterableMapping(elementTargetType = ComTEntePrevidenziale.class)
	List<ComTEntePrevidenziale> toEntities(Collection<EntePrevidenziale> models);

}
