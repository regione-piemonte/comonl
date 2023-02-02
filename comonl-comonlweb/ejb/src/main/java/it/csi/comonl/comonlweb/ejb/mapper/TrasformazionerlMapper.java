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

import it.csi.comonl.comonlweb.ejb.entity.ComTTrasformazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Trasformazionerl and ComTTrasformazionerl
 */
@Mapper
public interface TrasformazionerlMapper extends BaseMapperInterface<Trasformazionerl, ComTTrasformazionerl> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	Trasformazionerl toModel(ComTTrasformazionerl entity);

	@Override
	@IterableMapping(elementTargetType = Trasformazionerl.class)
	List<Trasformazionerl> toModels(Collection<ComTTrasformazionerl> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTrasformazionerl.class)
	List<ComTTrasformazionerl> toEntities(Collection<Trasformazionerl> models);

}
