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

import it.csi.comonl.comonlweb.ejb.entity.EntityPdfComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.PdfComunicazione;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between PdfComunicazione and EntityPdfComunicazione
 */
@Mapper
public interface PdfComunicazioneMapper extends BaseMapperInterface<PdfComunicazione, EntityPdfComunicazione> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	PdfComunicazione toModel(EntityPdfComunicazione entity);

	@Override
	@IterableMapping(elementTargetType = PdfComunicazione.class)
	List<PdfComunicazione> toModels(Collection<EntityPdfComunicazione> entities);

	@Override
	@IterableMapping(elementTargetType = EntityPdfComunicazione.class)
	List<EntityPdfComunicazione> toEntities(Collection<PdfComunicazione> models);

}
