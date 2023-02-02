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

import org.mapstruct.factory.Mappers;

/**
 * Mappers for Comonl
 */
public final class ComonlMappers {

	/** Private constructor */
	private ComonlMappers() {
		// Prevent instantiation
	}

	
	public static final AtecofinMapper ATECOFIN = Mappers.getMapper(AtecofinMapper.class);
	public static final AgevolazionerlMapper AGEVOLAZIONERL = Mappers.getMapper(AgevolazionerlMapper.class);
	public static final AzioniSpecificheMapper AZIONI_SPECIFICHE = Mappers.getMapper(AzioniSpecificheMapper.class);
	public static final CategLavAssObblMapper CATEG_LAV_ASS_OBBL = Mappers.getMapper(CategLavAssObblMapper.class);
	public static final CategTirocinanteMapper CATEG_TIROCINANTE = Mappers.getMapper(CategTirocinanteMapper.class);
	public static final CcnlMapper CCNL = Mappers.getMapper(CcnlMapper.class);
	public static final CessazionerlMapper CESSAZIONERL = Mappers.getMapper(CessazionerlMapper.class);
	public static final CittadinanzaMapper CITTADINANZA = Mappers.getMapper(CittadinanzaMapper.class);
	public static final CommaxParametriMapper COMMAX_PARAMETRI = Mappers.getMapper(CommaxParametriMapper.class);
	public static final ComonlsParametriMapper COMONLS_PARAMETRI = Mappers.getMapper(ComonlsParametriMapper.class);
	public static final ComuneMapper COMUNE = Mappers.getMapper(ComuneMapper.class);
	public static final ComunicazioneMapper COMUNICAZIONE = Mappers.getMapper(ComunicazioneMapper.class);
	public static final CpiMapper CPI = Mappers.getMapper(CpiMapper.class);
	public static final DatoreMapper DATORE = Mappers.getMapper(DatoreMapper.class);
	public static final EntePrevidenzialeMapper ENTE_PREVIDENZIALE = Mappers.getMapper(EntePrevidenzialeMapper.class);
	public static final GradoContrattualeMapper GRADO_CONTRATTUALE = Mappers.getMapper(GradoContrattualeMapper.class);
	public static final Istat2001livello5Mapper ISTAT2001LIVELLO5 = Mappers.getMapper(Istat2001livello5Mapper.class);
	public static final LavoratoreMapper LAVORATORE = Mappers.getMapper(LavoratoreMapper.class);
	public static final LivelloRetribuzioneMapper LIVELLO_RETRIBUZIONE = Mappers.getMapper(LivelloRetribuzioneMapper.class);
	public static final LivelloStudioMapper LIVELLO_STUDIO = Mappers.getMapper(LivelloStudioMapper.class);
	public static final MotivoPermessoMapper MOTIVO_PERMESSO = Mappers.getMapper(MotivoPermessoMapper.class);
	public static final ParamAbilitazIrideMapper PARAM_ABILITAZ_IRIDE = Mappers.getMapper(ParamAbilitazIrideMapper.class);
	public static final ProvinciaMapper PROVINCIA = Mappers.getMapper(ProvinciaMapper.class);
	public static final QuesturaMapper QUESTURA = Mappers.getMapper(QuesturaMapper.class);
	public static final RegioneMapper REGIONE = Mappers.getMapper(RegioneMapper.class);
	public static final StatiEsteriMapper STATI_ESTERI = Mappers.getMapper(StatiEsteriMapper.class);
	public static final StatoComunicazioneMapper STATO_COMUNICAZIONE = Mappers.getMapper(StatoComunicazioneMapper.class);
	public static final StatusStranieroMapper STATUS_STRANIERO = Mappers.getMapper(StatusStranieroMapper.class);
	public static final TipoAcquisizioneMapper TIPO_ACQUISIZIONE = Mappers.getMapper(TipoAcquisizioneMapper.class);
	public static final TipoAttoL68Mapper TIPO_ATTO_L68 = Mappers.getMapper(TipoAttoL68Mapper.class);
	public static final TipoComunicazioneMapper TIPO_COMUNICAZIONE = Mappers.getMapper(TipoComunicazioneMapper.class);
	public static final TipoComunicazioneTuMapper TIPO_COMUNICAZIONE_TU = Mappers.getMapper(TipoComunicazioneTuMapper.class);
	public static final TipoContrAmmPerComMapper TIPO_CONTR_AMM_PER_COM = Mappers.getMapper(TipoContrAmmPerComMapper.class);
	public static final TipoContrattiMapper TIPO_CONTRATTI = Mappers.getMapper(TipoContrattiMapper.class);
	public static final TipoContrPeriodiMapper TIPO_CONTR_PERIODI = Mappers.getMapper(TipoContrPeriodiMapper.class);
	public static final TipoDatoreMapper TIPO_DATORE = Mappers.getMapper(TipoDatoreMapper.class);
	public static final TipoDestinatarioMapper TIPO_DESTINATARIO = Mappers.getMapper(TipoDestinatarioMapper.class);
	public static final TipoEntePromTirocinioMapper TIPO_ENTE_PROM_TIROCINIO = Mappers.getMapper(TipoEntePromTirocinioMapper.class);
	public static final TipoLavoratoreMapper TIPO_LAVORATORE = Mappers.getMapper(TipoLavoratoreMapper.class);
	public static final TipoLivelloRetribMapper TIPO_LIVELLO_RETRIB = Mappers.getMapper(TipoLivelloRetribMapper.class);
	public static final TipologiaRapportoMapper TIPOLOGIA_RAPPORTO = Mappers.getMapper(TipologiaRapportoMapper.class);
	public static final TipologiaTirocinioMapper TIPOLOGIA_TIROCINIO = Mappers.getMapper(TipologiaTirocinioMapper.class);
	public static final TipoOrarioMapper TIPO_ORARIO = Mappers.getMapper(TipoOrarioMapper.class);
	public static final TipoProvenienzaMapper TIPO_PROVENIENZA = Mappers.getMapper(TipoProvenienzaMapper.class);
	public static final TipoRapportoAziendaMapper TIPO_RAPPORTO_AZIENDA = Mappers.getMapper(TipoRapportoAziendaMapper.class);
	public static final TipoRuoloMapper TIPO_RUOLO = Mappers.getMapper(TipoRuoloMapper.class);
	public static final TipoSoggettoAbilitatoMapper TIPO_SOGGETTO_ABILITATO = Mappers.getMapper(TipoSoggettoAbilitatoMapper.class);
	public static final TipoSomministrazioneMapper TIPO_SOMMINISTRAZIONE = Mappers.getMapper(TipoSomministrazioneMapper.class);
	public static final TipoTracciatoMapper TIPO_TRACCIATO = Mappers.getMapper(TipoTracciatoMapper.class);
	public static final TipoTrasferimentoMapper TIPO_TRASFERIMENTO = Mappers.getMapper(TipoTrasferimentoMapper.class);
	public static final TitoloStudioMapper TITOLO_STUDIO = Mappers.getMapper(TitoloStudioMapper.class);
	public static final TooltipMapper TOOLTIP = Mappers.getMapper(TooltipMapper.class);
	public static final TrasformazionerlMapper TRASFORMAZIONERL = Mappers.getMapper(TrasformazionerlMapper.class);
	public static final UnitaMisuraDurataMapper UNITA_MISURA_DURATA = Mappers.getMapper(UnitaMisuraDurataMapper.class);
	public static final VariazioneSommMapper VARIAZIONE_SOMM = Mappers.getMapper(VariazioneSommMapper.class);
	public static final AnagraficaAziAccentMapper ANAGRAFICA_AZI_ACCENT = Mappers.getMapper(AnagraficaAziAccentMapper.class);
	public static final AnagraficaDatoreMapper ANAGRAFICA_DATORE = Mappers.getMapper(AnagraficaDatoreMapper.class);
	public static final AnagraficaLavoratoreMapper ANAGRAFICA_LAVORATORE = Mappers.getMapper(AnagraficaLavoratoreMapper.class);
	public static final AnagraficaSedeLavoroMapper ANAGRAFICA_SEDE_LAVORO = Mappers.getMapper(AnagraficaSedeLavoroMapper.class);
	public static final CapacitaFormativaModaMapper CAPACITA_FORMATIVA_MODA = Mappers.getMapper(CapacitaFormativaModaMapper.class);
	public static final CapacitaFormativaModbMapper CAPACITA_FORMATIVA_MODB = Mappers.getMapper(CapacitaFormativaModbMapper.class);
	public static final CommaxAuthMapper COMMAX_AUTH = Mappers.getMapper(CommaxAuthMapper.class);
	public static final DelegatoMapper DELEGATO = Mappers.getMapper(DelegatoMapper.class);
	public static final DelegatoImpIrideMapper DELEGATO_IMP_IRIDE = Mappers.getMapper(DelegatoImpIrideMapper.class);
	public static final DelegatoIrideMapper DELEGATO_IRIDE = Mappers.getMapper(DelegatoIrideMapper.class);
	public static final DichiarazioniUdMapper DICHIARAZIONI_UD = Mappers.getMapper(DichiarazioniUdMapper.class);
	public static final DuplicaGruppoLogMapper DUPLICA_GRUPPO_LOG = Mappers.getMapper(DuplicaGruppoLogMapper.class);
	public static final ElaboratiOkMapper ELABORATI_OK = Mappers.getMapper(ElaboratiOkMapper.class);
	public static final ErrComunicMapper ERR_COMUNIC = Mappers.getMapper(ErrComunicMapper.class);
	public static final ErrInvioSpicomMapper ERR_INVIO_SPICOM = Mappers.getMapper(ErrInvioSpicomMapper.class);
	public static final FmErroriMapper FM_ERRORI = Mappers.getMapper(FmErroriMapper.class);
	public static final FzErroriMapper FZ_ERRORI = Mappers.getMapper(FzErroriMapper.class);
	public static final LegaleRapprMapper LEGALE_RAPPR = Mappers.getMapper(LegaleRapprMapper.class);
	public static final TutoreMapper TUTORE = Mappers.getMapper(TutoreMapper.class);
	public static final UplDocumentiMapper UPL_DOCUMENTI = Mappers.getMapper(UplDocumentiMapper.class);
	public static final UserAccessLogMapper USER_ACCESS_LOG = Mappers.getMapper(UserAccessLogMapper.class);
	public static final AnagraficaDelegatoMapper ANAGRAFICA_DELEGATO = Mappers.getMapper(AnagraficaDelegatoMapper.class);
	public static final CaricaPersonaPvMapper CARICA_PERSONA_PV = Mappers.getMapper(CaricaPersonaPvMapper.class);
	public static final ToponimoMapper TOPONIMO = Mappers.getMapper(ToponimoMapper.class);
	public static final DelegaMapper DELEGA = Mappers.getMapper(DelegaMapper.class);
	public static final DelegatoImpresaMapper DELEGATO_IMPRESA = Mappers.getMapper(DelegatoImpresaMapper.class);
	public static final DelegatoImpresaSpecMapper DELEGATO_IMPRESA_SPEC = Mappers.getMapper(DelegatoImpresaSpecMapper.class);
	public static final DepositoCommaxMapper DEPOSITO_COMMAX = Mappers.getMapper(DepositoCommaxMapper.class);
	public static final ParametriTipoComunicazioneMapper PARAMETRI_TIPO_COMUNICAZIONE = Mappers.getMapper(ParametriTipoComunicazioneMapper.class);
	public static final ParametriTipoDelegaMapper PARAMETRI_TIPO_DELEGA = Mappers.getMapper(ParametriTipoDelegaMapper.class);
	public static final PdfComunicazioneMapper PDF_COMUNICAZIONE = Mappers.getMapper(PdfComunicazioneMapper.class);
	public static final PersonalizzazioneMapper PERSONALIZZAZIONE = Mappers.getMapper(PersonalizzazioneMapper.class);
	public static final StatoDelegaMapper STATO_DELEGA = Mappers.getMapper(StatoDelegaMapper.class);
	public static final ComunicazioneDatoreMapper COMUNICAZIONE_DATORE = Mappers.getMapper(ComunicazioneDatoreMapper.class);
	public static final DepCommaxComunicMapper DEP_COMMAX_COMUNIC = Mappers.getMapper(DepCommaxComunicMapper.class);
	public static final ModelliPfiMapper MODELLI_PFI = Mappers.getMapper(ModelliPfiMapper.class);
	public static final PersonalizProvinceMapper PERSONALIZ_PROVINCE = Mappers.getMapper(PersonalizProvinceMapper.class);
	public static final RapportoMapper RAPPORTO = Mappers.getMapper(RapportoMapper.class);
	public static final RapportoLavoratoreMapper RAPPORTO_LAVORATORE = Mappers.getMapper(RapportoLavoratoreMapper.class);
	public static final RegTracciatoContrattoMapper REG_TRACCIATO_CONTRATTO = Mappers.getMapper(RegTracciatoContrattoMapper.class);
	public static final TipolTirocCatTirocMapper TIPOL_TIROC_CAT_TIROC = Mappers.getMapper(TipolTirocCatTirocMapper.class);
	public static final TListaFzErroriMapper TLISTA_FZ_ERRORI = Mappers.getMapper(TListaFzErroriMapper.class);
	public static final RicercaComunicazioneMapper RICERCA_COMUNICAZIONE = Mappers.getMapper(RicercaComunicazioneMapper.class);
	public static final RicercaVardatoriMapper RICERCA_VARDATORI = Mappers.getMapper(RicercaVardatoriMapper.class);
	public static final DatoreSedeMapper DATORE_SEDE = Mappers.getMapper(DatoreSedeMapper.class);
	public static final SedeLavoroMapper SEDE_LAVORO = Mappers.getMapper(SedeLavoroMapper.class);
	public static final SoggettoAbilitatoMapper SOGGETTO_ABILITATO = Mappers.getMapper(SoggettoAbilitatoMapper.class);
	public static final NaturaGiuridicaMapper NATURA_GIURIDICA = Mappers.getMapper(NaturaGiuridicaMapper.class);
	public static final SedeLavoroLavoratoreMapper SEDE_LAVORO_LAVORATORE = Mappers.getMapper(SedeLavoroLavoratoreMapper.class);
}
