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

import it.csi.comonl.comonlweb.ejb.entity.ComDElaboratiOk;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ElaboratiOk;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between ElaboratiOk and ComDElaboratiOk
 */
@Mapper
public interface ElaboratiOkMapper extends BaseMapperInterface<ElaboratiOk, ComDElaboratiOk> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comDUplDocumenti", target = "uplDocumenti")
	ElaboratiOk toModel(ComDElaboratiOk entity);

	@Override
	@IterableMapping(elementTargetType = ElaboratiOk.class)
	List<ElaboratiOk> toModels(Collection<ComDElaboratiOk> entities);

	@Override
	@IterableMapping(elementTargetType = ComDElaboratiOk.class)
	List<ComDElaboratiOk> toEntities(Collection<ElaboratiOk> models);

}
