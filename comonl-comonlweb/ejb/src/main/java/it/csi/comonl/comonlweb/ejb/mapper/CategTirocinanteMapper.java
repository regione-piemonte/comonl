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

import it.csi.comonl.comonlweb.ejb.entity.ComTCategTirocinante;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategTirocinante;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between CategTirocinante and ComTCategTirocinante
 */
@Mapper
public interface CategTirocinanteMapper extends BaseMapperInterface<CategTirocinante, ComTCategTirocinante> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	CategTirocinante toModel(ComTCategTirocinante entity);

	@Override
	@IterableMapping(elementTargetType = CategTirocinante.class)
	List<CategTirocinante> toModels(Collection<ComTCategTirocinante> entities);

	@Override
	@IterableMapping(elementTargetType = ComTCategTirocinante.class)
	List<ComTCategTirocinante> toEntities(Collection<CategTirocinante> models);

}
