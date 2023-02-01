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

import it.csi.comonl.comonlweb.ejb.entity.ComRPersonalizProvince;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.PersonalizProvince;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between PersonalizProvince and ComRPersonalizProvince
 */
@Mapper
public interface PersonalizProvinceMapper extends BaseMapperInterface<PersonalizProvince, ComRPersonalizProvince> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	PersonalizProvince toModel(ComRPersonalizProvince entity);

	@Override
	@IterableMapping(elementTargetType = PersonalizProvince.class)
	List<PersonalizProvince> toModels(Collection<ComRPersonalizProvince> entities);

	@Override
	@IterableMapping(elementTargetType = ComRPersonalizProvince.class)
	List<ComRPersonalizProvince> toEntities(Collection<PersonalizProvince> models);

}
