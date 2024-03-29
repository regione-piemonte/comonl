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

import it.csi.comonl.comonlweb.ejb.entity.ComTAgevolazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Agevolazionerl;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Agevolazionerl and ComTAgevolazionerl
 */
@Mapper
public interface AgevolazionerlMapper extends BaseMapperInterface<Agevolazionerl, ComTAgevolazionerl> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	Agevolazionerl toModel(ComTAgevolazionerl entity);

	@Override
	@IterableMapping(elementTargetType = Agevolazionerl.class)
	List<Agevolazionerl> toModels(Collection<ComTAgevolazionerl> entities);

	@Override
	@IterableMapping(elementTargetType = ComTAgevolazionerl.class)
	List<ComTAgevolazionerl> toEntities(Collection<Agevolazionerl> models);

}
