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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoSoggettoAbilitato and ComTTipoSoggettoAbilitato
 */
@Mapper
public interface TipoSoggettoAbilitatoMapper extends BaseMapperInterface<TipoSoggettoAbilitato, ComTTipoSoggettoAbilitato> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoSoggettoAbilitato toModel(ComTTipoSoggettoAbilitato entity);

	@Override
	@IterableMapping(elementTargetType = TipoSoggettoAbilitato.class)
	List<TipoSoggettoAbilitato> toModels(Collection<ComTTipoSoggettoAbilitato> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoSoggettoAbilitato.class)
	List<ComTTipoSoggettoAbilitato> toEntities(Collection<TipoSoggettoAbilitato> models);

}
