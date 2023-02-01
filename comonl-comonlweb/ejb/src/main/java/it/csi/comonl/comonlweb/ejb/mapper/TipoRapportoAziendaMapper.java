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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoRapportoAzienda;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoRapportoAzienda;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoRapportoAzienda and ComTTipoRapportoAzienda
 */
@Mapper
public interface TipoRapportoAziendaMapper extends BaseMapperInterface<TipoRapportoAzienda, ComTTipoRapportoAzienda> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoRapportoAzienda toModel(ComTTipoRapportoAzienda entity);

	@Override
	@IterableMapping(elementTargetType = TipoRapportoAzienda.class)
	List<TipoRapportoAzienda> toModels(Collection<ComTTipoRapportoAzienda> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoRapportoAzienda.class)
	List<ComTTipoRapportoAzienda> toEntities(Collection<TipoRapportoAzienda> models);

}
