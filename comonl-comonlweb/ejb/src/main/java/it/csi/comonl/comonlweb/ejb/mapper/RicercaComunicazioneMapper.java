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

import it.csi.comonl.comonlweb.ejb.entity.ComVRicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RicercaComunicazione;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between RicercaComunicazione and ComVRicercaComunicazione
 */
@Mapper
public interface RicercaComunicazioneMapper extends BaseMapperInterface<RicercaComunicazione, ComVRicercaComunicazione> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	RicercaComunicazione toModel(ComVRicercaComunicazione entity);

	@Override
	@IterableMapping(elementTargetType = RicercaComunicazione.class)
	List<RicercaComunicazione> toModels(Collection<ComVRicercaComunicazione> entities);

	@Override
	@IterableMapping(elementTargetType = ComVRicercaComunicazione.class)
	List<ComVRicercaComunicazione> toEntities(Collection<RicercaComunicazione> models);

}