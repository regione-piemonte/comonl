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

import it.csi.comonl.comonlweb.ejb.entity.ComTCessazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cessazionerl;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Cessazionerl and ComTCessazionerl
 */
@Mapper
public interface CessazionerlMapper extends BaseMapperInterface<Cessazionerl, ComTCessazionerl> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	Cessazionerl toModel(ComTCessazionerl entity);

	@Override
	@IterableMapping(elementTargetType = Cessazionerl.class)
	List<Cessazionerl> toModels(Collection<ComTCessazionerl> entities);

	@Override
	@IterableMapping(elementTargetType = ComTCessazionerl.class)
	List<ComTCessazionerl> toEntities(Collection<Cessazionerl> models);

}
