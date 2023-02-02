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
import org.mapstruct.Mapping;

import it.csi.comonl.comonlweb.ejb.entity.ComRDepCommaxComunic;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepCommaxComunic;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between DepCommaxComunic and ComRDepCommaxComunic
 */
@Mapper
public interface DepCommaxComunicMapper extends BaseMapperInterface<DepCommaxComunic, ComRDepCommaxComunic> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comDComunicazione", target = "comunicazione")
	@Mapping(source = "comDUplDocumenti", target = "uplDocumenti")
	DepCommaxComunic toModel(ComRDepCommaxComunic entity);

	@Override
	@IterableMapping(elementTargetType = DepCommaxComunic.class)
	List<DepCommaxComunic> toModels(Collection<ComRDepCommaxComunic> entities);

	@Override
	@IterableMapping(elementTargetType = ComRDepCommaxComunic.class)
	List<ComRDepCommaxComunic> toEntities(Collection<DepCommaxComunic> models);

}
