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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoDestinatario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoDestinatario;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoDestinatario and ComTTipoDestinatario
 */
@Mapper
public interface TipoDestinatarioMapper extends BaseMapperInterface<TipoDestinatario, ComTTipoDestinatario> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoDestinatario toModel(ComTTipoDestinatario entity);

	@Override
	@IterableMapping(elementTargetType = TipoDestinatario.class)
	List<TipoDestinatario> toModels(Collection<ComTTipoDestinatario> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoDestinatario.class)
	List<ComTTipoDestinatario> toEntities(Collection<TipoDestinatario> models);

}
