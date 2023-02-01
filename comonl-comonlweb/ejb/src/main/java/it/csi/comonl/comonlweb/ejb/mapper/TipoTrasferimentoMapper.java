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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoTrasferimento;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoTrasferimento and ComTTipoTrasferimento
 */
@Mapper
public interface TipoTrasferimentoMapper extends BaseMapperInterface<TipoTrasferimento, ComTTipoTrasferimento> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoTrasferimento toModel(ComTTipoTrasferimento entity);

	@Override
	@IterableMapping(elementTargetType = TipoTrasferimento.class)
	List<TipoTrasferimento> toModels(Collection<ComTTipoTrasferimento> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoTrasferimento.class)
	List<ComTTipoTrasferimento> toEntities(Collection<TipoTrasferimento> models);

}
