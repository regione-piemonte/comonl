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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoTracciato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTracciato;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoTracciato and ComTTipoTracciato
 */
@Mapper
public interface TipoTracciatoMapper extends BaseMapperInterface<TipoTracciato, ComTTipoTracciato> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoTracciato toModel(ComTTipoTracciato entity);

	@Override
	@IterableMapping(elementTargetType = TipoTracciato.class)
	List<TipoTracciato> toModels(Collection<ComTTipoTracciato> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoTracciato.class)
	List<ComTTipoTracciato> toEntities(Collection<TipoTracciato> models);

}
