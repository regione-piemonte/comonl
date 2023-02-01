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

import it.csi.comonl.comonlweb.ejb.entity.ComDAnagraficaDatore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDatore;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between AnagraficaDatore and ComDAnagraficaDatore
 */
@Mapper
public interface AnagraficaDatoreMapper extends BaseMapperInterface<AnagraficaDatore, ComDAnagraficaDatore> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	AnagraficaDatore toModel(ComDAnagraficaDatore entity);

	@Override
	@IterableMapping(elementTargetType = AnagraficaDatore.class)
	List<AnagraficaDatore> toModels(Collection<ComDAnagraficaDatore> entities);

	@Override
	@IterableMapping(elementTargetType = ComDAnagraficaDatore.class)
	List<ComDAnagraficaDatore> toEntities(Collection<AnagraficaDatore> models);

}
