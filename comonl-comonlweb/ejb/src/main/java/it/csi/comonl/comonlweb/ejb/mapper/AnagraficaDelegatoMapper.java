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

import org.mapstruct.BeforeMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import it.csi.comonl.comonlweb.ejb.entity.EntityAnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between AnagraficaDelegato and EntityAnagraficaDelegato
 */
@Mapper(uses = { ComuneMapper.class, SoggettoAbilitatoMapper.class, PersonalizzazioneMapper.class,
		StatiEsteriMapper.class })
public interface AnagraficaDelegatoMapper extends BaseMapperInterface<AnagraficaDelegato, EntityAnagraficaDelegato> {

	@BeforeMapping
	default void checkPropertyNullBefore(AnagraficaDelegato model, @MappingTarget EntityAnagraficaDelegato entity) {
		if (model.getComuneDom() != null && model.getComuneDom().getId() == null) {
			model.setComuneDom(null);
		}
	}

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comDSoggettoAbilitato", target = "soggettoAbilitato")
	@Mapping(source = "comTComuneRes", target = "comuneRes")
	@Mapping(source = "comTComuneDom", target = "comuneDom")
	@Mapping(source = "comTComuneNasc", target = "comuneNasc")
	@Mapping(source = "personalizzazione", target = "personalizzazione")
	@Mapping(source = "comTStatiEsteri", target = "statiEsteri")
	@Mapping(source = "comTStatiEsteriDom", target = "statiEsteriDom")
	@Mapping(source = "comTStatiEsteriRes", target = "statiEsteriRes")
	AnagraficaDelegato toModel(EntityAnagraficaDelegato entity);

	@Override
	@IterableMapping(elementTargetType = AnagraficaDelegato.class)
	List<AnagraficaDelegato> toModels(Collection<EntityAnagraficaDelegato> entities);

	@Override
	@IterableMapping(elementTargetType = EntityAnagraficaDelegato.class)
	List<EntityAnagraficaDelegato> toEntities(Collection<AnagraficaDelegato> models);

}
