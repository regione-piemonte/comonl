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

import it.csi.comonl.comonlweb.ejb.entity.ComDUplDocumenti;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between UplDocumenti and ComDUplDocumenti
 */
@Mapper
public interface UplDocumentiMapper extends BaseMapperInterface<UplDocumenti, ComDUplDocumenti> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	UplDocumenti toModel(ComDUplDocumenti entity);

	@Override
	@IterableMapping(elementTargetType = UplDocumenti.class)
	List<UplDocumenti> toModels(Collection<ComDUplDocumenti> entities);

	@Override
	@IterableMapping(elementTargetType = ComDUplDocumenti.class)
	List<ComDUplDocumenti> toEntities(Collection<UplDocumenti> models);

}
