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

import it.csi.comonl.comonlweb.ejb.entity.ComDRapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Rapporto and ComDRapporto
 */
@Mapper(uses = {
			ComunicazioneMapper.class, DatoreMapper.class, SedeLavoroMapper.class, CategLavAssObblMapper.class,
			CategTirocinanteMapper.class, CcnlMapper.class, CpiMapper.class, EntePrevidenzialeMapper.class, Istat2001livello5Mapper.class,
			LivelloRetribuzioneMapper.class, TipoAttoL68Mapper.class, TipoContrattiMapper.class, TipoEntePromTirocinioMapper.class,
			TipologiaTirocinioMapper.class, TipoOrarioMapper.class, TrasformazionerlMapper.class, CessazionerlMapper.class
		})
public interface RapportoMapper extends BaseMapperInterface<Rapporto, ComDRapporto> {
	
	
	@BeforeMapping
	default void checkPropertyNullBefore(Rapporto model, @MappingTarget ComDRapporto entity) {
		if (model.getCcnl() != null && model.getCcnl().getId() == null) {
			model.setCcnl(null);
		}
		if (model.getLivelloRetribuzione() != null && model.getLivelloRetribuzione().getId() == null) {
			model.setLivelloRetribuzione(null);
		}
		if (model.getIstatLivello5() != null && model.getIstatLivello5().getId() == null) {
			model.setIstatLivello5(null);
		}
	}
	

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comDComunicazione", target = "comunicazione")
	@Mapping(source = "comDDatoreDistaccatario", target = "datoreDistaccatario")
	@Mapping(source = "comDAziUtilizzatrice", target = "aziUtilizzatrice")
	@Mapping(source = "comDSedeLavoroPrecedente", target = "sedeLavoroPrecedente")
	@Mapping(source = "comDTutore", target = "tutore")
	@Mapping(source = "comTCategLavAssObbl", target = "categLavAssObbl")
	@Mapping(source = "comTCategTirocinante", target = "categTirocinante")
	@Mapping(source = "comTCcnl", target = "ccnl")
	@Mapping(source = "comTCpi", target = "cpi")
	@Mapping(source = "comTEntePrevidenziale", target = "entePrevidenziale")
	@Mapping(source = "comTIstat2001livello5", target = "istatLivello5")
	@Mapping(source = "comTLivelloRetribuzione", target = "livelloRetribuzione")
	@Mapping(source = "comTTipoAttoL68", target = "tipoAttoL68")
	@Mapping(source = "comTTipoContratti", target = "tipoContratti")
	@Mapping(source = "comTTipoEntePromTirocinio", target = "tipoEntePromTirocinio")
	@Mapping(source = "comTTipologiaTirocinio", target = "tipologiaTirocinio")
	@Mapping(source = "comTTipoOrario", target = "tipoOrario")
	@Mapping(source = "comTTrasformazionerl", target = "trasformazionerl")
	@Mapping(source = "comTCessazionerl", target = "cessazionerl")
	Rapporto toModel(ComDRapporto entity);

	@Override
	@IterableMapping(elementTargetType = Rapporto.class)
	List<Rapporto> toModels(Collection<ComDRapporto> entities);

	@Override
	@IterableMapping(elementTargetType = ComDRapporto.class)
	List<ComDRapporto> toEntities(Collection<Rapporto> models);

}
