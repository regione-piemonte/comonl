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

import it.csi.comonl.comonlweb.ejb.entity.ComDSoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between SoggettoAbilitato and ComDSoggettoAbilitato
 */
@Mapper(uses = {ComuneMapper.class, TipoSoggettoAbilitatoMapper.class})
public interface SoggettoAbilitatoMapper extends BaseMapperInterface<SoggettoAbilitato, ComDSoggettoAbilitato> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comTComune", target = "comune")
	@Mapping(source = "comTTipoSoggettoAbilitato", target = "tipoSoggettoAbilitato")
	SoggettoAbilitato toModel(ComDSoggettoAbilitato entity);

	@Override
	@IterableMapping(elementTargetType = SoggettoAbilitato.class)
	List<SoggettoAbilitato> toModels(Collection<ComDSoggettoAbilitato> entities);

	@Override
	@IterableMapping(elementTargetType = ComDSoggettoAbilitato.class)
	List<ComDSoggettoAbilitato> toEntities(Collection<SoggettoAbilitato> models);

}
