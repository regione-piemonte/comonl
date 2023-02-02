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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipologiaRapporto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaRapporto;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipologiaRapporto and ComTTipologiaRapporto
 */
@Mapper
public interface TipologiaRapportoMapper extends BaseMapperInterface<TipologiaRapporto, ComTTipologiaRapporto> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipologiaRapporto toModel(ComTTipologiaRapporto entity);

	@Override
	@IterableMapping(elementTargetType = TipologiaRapporto.class)
	List<TipologiaRapporto> toModels(Collection<ComTTipologiaRapporto> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipologiaRapporto.class)
	List<ComTTipologiaRapporto> toEntities(Collection<TipologiaRapporto> models);

}
