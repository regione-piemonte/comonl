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

import it.csi.comonl.comonlweb.ejb.entity.ComDAnagraficaAziAccent;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between AnagraficaAziAccent and ComDAnagraficaAziAccent
 */
@Mapper
public interface AnagraficaAziAccentMapper extends BaseMapperInterface<AnagraficaAziAccent, ComDAnagraficaAziAccent> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	AnagraficaAziAccent toModel(ComDAnagraficaAziAccent entity);

	@Override
	@IterableMapping(elementTargetType = AnagraficaAziAccent.class)
	List<AnagraficaAziAccent> toModels(Collection<ComDAnagraficaAziAccent> entities);

	@Override
	@IterableMapping(elementTargetType = ComDAnagraficaAziAccent.class)
	List<ComDAnagraficaAziAccent> toEntities(Collection<AnagraficaAziAccent> models);

}
