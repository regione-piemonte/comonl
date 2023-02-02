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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoAcquisizione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoAcquisizione;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoAcquisizione and ComTTipoAcquisizione
 */
@Mapper
public interface TipoAcquisizioneMapper extends BaseMapperInterface<TipoAcquisizione, ComTTipoAcquisizione> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoAcquisizione toModel(ComTTipoAcquisizione entity);

	@Override
	@IterableMapping(elementTargetType = TipoAcquisizione.class)
	List<TipoAcquisizione> toModels(Collection<ComTTipoAcquisizione> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoAcquisizione.class)
	List<ComTTipoAcquisizione> toEntities(Collection<TipoAcquisizione> models);

}
