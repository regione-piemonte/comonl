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
import org.mapstruct.Mapping;

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContrAmmPerCom;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrAmmPerCom;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoContrAmmPerCom and ComTTipoContrAmmPerCom
 */
@Mapper
public interface TipoContrAmmPerComMapper extends BaseMapperInterface<TipoContrAmmPerCom, ComTTipoContrAmmPerCom> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comTTipoContratti", target = "tipoContratti")
	TipoContrAmmPerCom toModel(ComTTipoContrAmmPerCom entity);

	@Override
	@IterableMapping(elementTargetType = TipoContrAmmPerCom.class)
	List<TipoContrAmmPerCom> toModels(Collection<ComTTipoContrAmmPerCom> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoContrAmmPerCom.class)
	List<ComTTipoContrAmmPerCom> toEntities(Collection<TipoContrAmmPerCom> models);

}
