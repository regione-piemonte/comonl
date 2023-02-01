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

import it.csi.comonl.comonlweb.ejb.entity.ComTComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between ComonlsParametri and ComTComonlsParametri
 */
@Mapper
public interface ComonlsParametriMapper extends BaseMapperInterface<ComonlsParametri, ComTComonlsParametri> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	ComonlsParametri toModel(ComTComonlsParametri entity);

	@Override
	@IterableMapping(elementTargetType = ComonlsParametri.class)
	List<ComonlsParametri> toModels(Collection<ComTComonlsParametri> entities);

	@Override
	@IterableMapping(elementTargetType = ComTComonlsParametri.class)
	List<ComTComonlsParametri> toEntities(Collection<ComonlsParametri> models);

}
