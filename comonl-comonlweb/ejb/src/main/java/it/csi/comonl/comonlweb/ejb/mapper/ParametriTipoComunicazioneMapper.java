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

import it.csi.comonl.comonlweb.ejb.entity.EntityParametriTipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ParametriTipoComunicazione;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between ParametriTipoComunicazione and EntityParametriTipoComunicazione
 */
@Mapper
public interface ParametriTipoComunicazioneMapper extends BaseMapperInterface<ParametriTipoComunicazione, EntityParametriTipoComunicazione> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	ParametriTipoComunicazione toModel(EntityParametriTipoComunicazione entity);

	@Override
	@IterableMapping(elementTargetType = ParametriTipoComunicazione.class)
	List<ParametriTipoComunicazione> toModels(Collection<EntityParametriTipoComunicazione> entities);

	@Override
	@IterableMapping(elementTargetType = EntityParametriTipoComunicazione.class)
	List<EntityParametriTipoComunicazione> toEntities(Collection<ParametriTipoComunicazione> models);

}
