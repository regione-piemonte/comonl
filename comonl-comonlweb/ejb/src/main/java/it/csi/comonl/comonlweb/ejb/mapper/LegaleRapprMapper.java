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

import it.csi.comonl.comonlweb.ejb.entity.ComDDatore;
import it.csi.comonl.comonlweb.ejb.entity.ComDLegaleRappr;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.LegaleRappr;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between LegaleRappr and ComDLegaleRappr
 */
@Mapper(uses = {
		CittadinanzaMapper.class,ComuneMapper.class,MotivoPermessoMapper.class,
		QuesturaMapper.class,StatiEsteriMapper.class,StatusStranieroMapper.class
	})
public interface LegaleRapprMapper extends BaseMapperInterface<LegaleRappr, ComDLegaleRappr> {

	@BeforeMapping
	default
	void checkPropertyNullBefore(LegaleRappr model,  @MappingTarget ComDLegaleRappr entity) {
        if (model.getComune() != null && model.getComune().getId() == null) {
        	model.setComune(null);
        }
        if (model.getStatiEsteri() != null && model.getStatiEsteri().getId() == null) {
        	model.setStatiEsteri(null);
        }
        if (model.getCittadinanza() != null && model.getCittadinanza().getId() == null) {
        	model.setCittadinanza(null);
        }
        if (model.getStatusStraniero() != null && model.getStatusStraniero().getId() == null) {
        	model.setStatusStraniero(null);
        }
        if (model.getMotivoPermesso() != null && model.getMotivoPermesso().getId() == null) {
        	model.setMotivoPermesso(null);
        }
        if (model.getQuestura() != null && model.getQuestura().getId() == null) {
        	model.setQuestura(null);
        }
    }
	
	@Override
	@Mapping(source = "comTCittadinanza", target = "cittadinanza")
	@Mapping(source = "comTComune", target = "comune")
	@Mapping(source = "comTMotivoPermesso", target = "motivoPermesso")
	@Mapping(source = "comTQuestura", target = "questura")
	@Mapping(source = "comTStatiEsteri", target = "statiEsteri")
	@Mapping(source = "comTStatusStraniero", target = "statusStraniero")
	LegaleRappr toModel(ComDLegaleRappr entity);

	@Override
	@IterableMapping(elementTargetType = LegaleRappr.class)
	List<LegaleRappr> toModels(Collection<ComDLegaleRappr> entities);

	@Override
	@IterableMapping(elementTargetType = ComDLegaleRappr.class)
	List<ComDLegaleRappr> toEntities(Collection<LegaleRappr> models);

}
