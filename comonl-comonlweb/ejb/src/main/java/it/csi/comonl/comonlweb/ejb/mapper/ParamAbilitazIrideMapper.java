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

import it.csi.comonl.comonlweb.ejb.entity.ComTParamAbilitazIride;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ParamAbilitazIride;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between ParamAbilitazIride and ComTParamAbilitazIride
 */
@Mapper
public interface ParamAbilitazIrideMapper extends BaseMapperInterface<ParamAbilitazIride, ComTParamAbilitazIride> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	ParamAbilitazIride toModel(ComTParamAbilitazIride entity);

	@Override
	@IterableMapping(elementTargetType = ParamAbilitazIride.class)
	List<ParamAbilitazIride> toModels(Collection<ComTParamAbilitazIride> entities);

	@Override
	@IterableMapping(elementTargetType = ComTParamAbilitazIride.class)
	List<ComTParamAbilitazIride> toEntities(Collection<ParamAbilitazIride> models);

}
