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

import it.csi.comonl.comonlweb.ejb.entity.ComTStatoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between StatoComunicazione and ComTStatoComunicazione
 */
@Mapper
public interface StatoComunicazioneMapper extends BaseMapperInterface<StatoComunicazione, ComTStatoComunicazione> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	StatoComunicazione toModel(ComTStatoComunicazione entity);

	@Override
	@IterableMapping(elementTargetType = StatoComunicazione.class)
	List<StatoComunicazione> toModels(Collection<ComTStatoComunicazione> entities);

	@Override
	@IterableMapping(elementTargetType = ComTStatoComunicazione.class)
	List<ComTStatoComunicazione> toEntities(Collection<StatoComunicazione> models);

}
