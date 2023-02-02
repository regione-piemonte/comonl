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

import it.csi.comonl.comonlweb.ejb.entity.ComDDatore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Datore and ComDDatore
 */
@Mapper( uses = {AtecofinMapper.class, NaturaGiuridicaMapper.class})
public interface DatoreMapper extends BaseMapperInterface<Datore, ComDDatore> {
	
	@BeforeMapping
	default
	void checkPropertyNullBefore(Datore model,  @MappingTarget ComDDatore entity) {
        if (model.getNaturaGiuridica() != null && model.getNaturaGiuridica().getId() == null) {
        	model.setNaturaGiuridica(null);
        }
    }
	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comTAtecofin", target = "atecofin")
	@Mapping(source = "naturaGiuridica", target = "naturaGiuridica")
	Datore toModel(ComDDatore entity);

	@Override
	@IterableMapping(elementTargetType = Datore.class)
	List<Datore> toModels(Collection<ComDDatore> entities);

	@Override
	@IterableMapping(elementTargetType = ComDDatore.class)
	List<ComDDatore> toEntities(Collection<Datore> models);

}
