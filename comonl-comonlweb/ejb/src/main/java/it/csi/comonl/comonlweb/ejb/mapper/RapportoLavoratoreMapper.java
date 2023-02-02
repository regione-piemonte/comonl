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
import org.mapstruct.Mapping;

import it.csi.comonl.comonlweb.ejb.entity.ComRRapportoLavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportoLavoratore;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between RapportoLavoratore and ComRRapportoLavoratore
 */
@Mapper
public interface RapportoLavoratoreMapper extends BaseMapperInterface<RapportoLavoratore, ComRRapportoLavoratore> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comDLavoratore", target = "lavoratore")
	@Mapping(source = "comDRapporto", target = "rapporto")
	@Mapping(source = "comTTipoLavoratore", target = "tipoLavoratore")
	@Mapping(source = "comTTipoRapportoAzienda", target = "tipoRapportoAzienda")
	RapportoLavoratore toModel(ComRRapportoLavoratore entity);

	@Override
	@IterableMapping(elementTargetType = RapportoLavoratore.class)
	List<RapportoLavoratore> toModels(Collection<ComRRapportoLavoratore> entities);

	@Override
	@IterableMapping(elementTargetType = ComRRapportoLavoratore.class)
	List<ComRRapportoLavoratore> toEntities(Collection<RapportoLavoratore> models);

}
