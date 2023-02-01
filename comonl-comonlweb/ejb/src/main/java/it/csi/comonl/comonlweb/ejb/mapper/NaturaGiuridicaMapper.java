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

import it.csi.comonl.comonlweb.ejb.entity.EntityNaturaGiuridica;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.NaturaGiuridica;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between NaturaGiuridica and EntityNaturaGiuridica
 */
@Mapper
public interface NaturaGiuridicaMapper extends BaseMapperInterface<NaturaGiuridica, EntityNaturaGiuridica> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	NaturaGiuridica toModel(EntityNaturaGiuridica entity);

	@Override
	@IterableMapping(elementTargetType = NaturaGiuridica.class)
	List<NaturaGiuridica> toModels(Collection<EntityNaturaGiuridica> entities);

	@Override
	@IterableMapping(elementTargetType = EntityNaturaGiuridica.class)
	List<EntityNaturaGiuridica> toEntities(Collection<NaturaGiuridica> models);

}
