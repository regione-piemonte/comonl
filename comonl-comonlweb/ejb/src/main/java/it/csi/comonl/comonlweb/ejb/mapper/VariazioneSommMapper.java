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

import it.csi.comonl.comonlweb.ejb.entity.ComDSedeLavoro;
import it.csi.comonl.comonlweb.ejb.entity.ComTVariazioneSomm;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.VariazioneSomm;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between VariazioneSomm and ComTVariazioneSomm
 */
@Mapper
public interface VariazioneSommMapper extends BaseMapperInterface<VariazioneSomm, ComTVariazioneSomm> {

	@Override
	@Mapping(source = "comTTipoSomministrazione", target = "tipoSomministrazione")
	@Mapping(source = "comTTipoComunicazione", target = "tipoComunicazione")
	VariazioneSomm toModel(ComTVariazioneSomm entity);

	@Override
	@Mapping(source = "tipoSomministrazione", target = "comTTipoSomministrazione")
	@Mapping(source = "tipoComunicazione", target = "comTTipoComunicazione")
	ComTVariazioneSomm toEntity(VariazioneSomm entity);

	@Override
	@IterableMapping(elementTargetType = VariazioneSomm.class)
	List<VariazioneSomm> toModels(Collection<ComTVariazioneSomm> entities);

	@Override
	List<ComTVariazioneSomm> toEntities(Collection<VariazioneSomm> models);

}
