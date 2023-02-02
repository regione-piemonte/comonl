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

import it.csi.comonl.comonlweb.ejb.entity.ComDAnagraficaSedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaSedeLavoro;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between AnagraficaSedeLavoro and ComDAnagraficaSedeLavoro
 */
@Mapper
public interface AnagraficaSedeLavoroMapper extends BaseMapperInterface<AnagraficaSedeLavoro, ComDAnagraficaSedeLavoro> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	AnagraficaSedeLavoro toModel(ComDAnagraficaSedeLavoro entity);

	@Override
	@IterableMapping(elementTargetType = AnagraficaSedeLavoro.class)
	List<AnagraficaSedeLavoro> toModels(Collection<ComDAnagraficaSedeLavoro> entities);

	@Override
	@IterableMapping(elementTargetType = ComDAnagraficaSedeLavoro.class)
	List<ComDAnagraficaSedeLavoro> toEntities(Collection<AnagraficaSedeLavoro> models);

}
