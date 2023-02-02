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

import org.mapstruct.BeforeMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import it.csi.comonl.comonlweb.ejb.entity.ComDSedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between SedeLavoro and ComDSedeLavoro
 */
@Mapper(uses = {ComuneMapper.class, StatiEsteriMapper.class})
public interface SedeLavoroMapper extends BaseMapperInterface<SedeLavoro, ComDSedeLavoro> {
	
	@BeforeMapping
	default
	void checkPropertyNullBefore(SedeLavoro model,  @MappingTarget ComDSedeLavoro entity) {
        if (model.getComune() != null && model.getComune().getId() == null) {
        	model.setComune(null);
        }
        if (model.getStatiEsteri() != null && model.getStatiEsteri().getId() == null) {
        	model.setStatiEsteri(null);
        }
    }

	@Override
	@Mapping(source = "comTComune", target = "comune")
	@Mapping(source = "comTStatiEsteri", target = "statiEsteri")
	SedeLavoro toModel(ComDSedeLavoro entity);

	@Override
	@IterableMapping(elementTargetType = SedeLavoro.class)
	List<SedeLavoro> toModels(Collection<ComDSedeLavoro> entities);

	@Override
	@IterableMapping(elementTargetType = ComDSedeLavoro.class)
	List<ComDSedeLavoro> toEntities(Collection<SedeLavoro> models);

}
