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

import it.csi.comonl.comonlweb.ejb.entity.ComDAnagraficaLavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaLavoratore;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between AnagraficaLavoratore and ComDAnagraficaLavoratore
 */
@Mapper
public interface AnagraficaLavoratoreMapper extends BaseMapperInterface<AnagraficaLavoratore, ComDAnagraficaLavoratore> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	AnagraficaLavoratore toModel(ComDAnagraficaLavoratore entity);

	@Override
	@IterableMapping(elementTargetType = AnagraficaLavoratore.class)
	List<AnagraficaLavoratore> toModels(Collection<ComDAnagraficaLavoratore> entities);

	@Override
	@IterableMapping(elementTargetType = ComDAnagraficaLavoratore.class)
	List<ComDAnagraficaLavoratore> toEntities(Collection<AnagraficaLavoratore> models);

}
