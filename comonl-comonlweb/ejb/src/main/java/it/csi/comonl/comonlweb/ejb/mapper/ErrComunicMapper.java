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

import it.csi.comonl.comonlweb.ejb.entity.ComDErrComunic;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ErrComunic;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between ErrComunic and ComDErrComunic
 */
@Mapper
public interface ErrComunicMapper extends BaseMapperInterface<ErrComunic, ComDErrComunic> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	ErrComunic toModel(ComDErrComunic entity);

	@Override
	@IterableMapping(elementTargetType = ErrComunic.class)
	List<ErrComunic> toModels(Collection<ComDErrComunic> entities);

	@Override
	@IterableMapping(elementTargetType = ComDErrComunic.class)
	List<ComDErrComunic> toEntities(Collection<ErrComunic> models);

}
