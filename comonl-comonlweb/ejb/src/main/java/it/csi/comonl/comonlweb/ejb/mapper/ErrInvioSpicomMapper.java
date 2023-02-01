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

import it.csi.comonl.comonlweb.ejb.entity.ComDErrInvioSpicom;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ErrInvioSpicom;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between ErrInvioSpicom and ComDErrInvioSpicom
 */
@Mapper
public interface ErrInvioSpicomMapper extends BaseMapperInterface<ErrInvioSpicom, ComDErrInvioSpicom> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	ErrInvioSpicom toModel(ComDErrInvioSpicom entity);

	@Override
	@IterableMapping(elementTargetType = ErrInvioSpicom.class)
	List<ErrInvioSpicom> toModels(Collection<ComDErrInvioSpicom> entities);

	@Override
	@IterableMapping(elementTargetType = ComDErrInvioSpicom.class)
	List<ComDErrInvioSpicom> toEntities(Collection<ErrInvioSpicom> models);

}
