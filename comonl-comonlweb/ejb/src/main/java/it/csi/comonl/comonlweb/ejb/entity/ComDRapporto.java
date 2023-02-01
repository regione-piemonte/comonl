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
package it.csi.comonl.comonlweb.ejb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_RAPPORTO database table.
 * 
 */
@Entity
@Table(name="COM_D_RAPPORTO")
@NamedQuery(name="ComDRapporto.findAll", query="SELECT c FROM ComDRapporto c")
@SequenceGenerator(name = "SEQUENCE_RAPPORTO", sequenceName = "SEQ_ID_COM_D_RAPPORTO", initialValue = 1, allocationSize = 1)
public class ComDRapporto implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDRapporto;
	}

	@Override
	public void setId(Long id) {
		idComDRapporto = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_RAPPORTO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_RAPPORTO")
	private Long idComDRapporto;

	@Column(name="CF_SOGG_PROMOTORE_TIROCINIO")
	private String cfSoggPromotoreTirocinio;

	@Column(name="CODICE_ENTE_PREV")
	private String codiceEntePrev;

	private String crt;

	@Column(name="DS_ATTIVITA")
	private String dsAttivita;

	@Column(name="DS_ATTIVITA_AGRICOLTURA")
	private String dsAttivitaAgricoltura;

	@Column(name="DS_SOGG_PROM_TIROCINIO")
	private String dsSoggPromTirocinio;

	@Column(name="DS_VOCE_TARIFFA1")
	private String dsVoceTariffa1;

	@Column(name="DS_VOCE_TARIFFA2")
	private String dsVoceTariffa2;

	@Column(name="DS_VOCE_TARIFFA3")
	private String dsVoceTariffa3;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_APPROVAZIONE_CRT")
	private Date dtApprovazioneCrt;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_CESSAZIONE")
	private Date dtCessazione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_CRT")
	private Date dtCrt;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_EVENTO")
	private Date dtEvento;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_MISSIONE")
	private Date dtFineMissione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_PERIODO_FORMATIVO")
	private Date dtFinePeriodoFormativo;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_PROROGA")
	private Date dtFineProroga;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_RAPPORTO")
	private Date dtFineRapporto;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_MISSIONE")
	private Date dtInizioMissione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_RAPPORTO")
	private Date dtInizioRapporto;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_LEGGE68")
	private Date dtLegge68;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TRASFORMAZIONE")
	private Date dtTrasformazione;

	@Column(name="FLG_ASS_LEGGE_68_99")
	private String flgAssLegge6899;

	@Column(name="FLG_ASSUNZIONE_OBBLIGATORIA")
	private String flgAssunzioneObbligatoria;

	@Column(name="FLG_CCNL_APPLICATO")
	private String flgCcnlApplicato;

	@Column(name="FLG_DISTACCO_PARZIALE")
	private String flgDistaccoParziale;

	@Column(name="FLG_DISTACCO_SU_AZI_ESTERA")
	private String flgDistaccoSuAziEstera;

	@Column(name="FLG_LAV_IN_MOBILITA")
	private String flgLavInMobilita;

	@Column(name="FLG_LAV_STAGIONALE")
	private String flgLavStagionale;

	@Column(name="FLG_LAVORO_AGRICOLTURA")
	private String flgLavoroAgricoltura;

	@Column(name="FLG_PROSECUZIONE_DI_FATTO")
	private String flgProsecuzioneDiFatto;

	@Column(name="FLG_RISCHIO_SILIC_ASBE")
	private String flgRischioSilicAsbe;

	@Column(name="FLG_SOCIO_LAVORATORE")
	private String flgSocioLavoratore;

	@Column(name="FLG_SOMM_TEMPO_DET")
	private String flgSommTempoDet;

	@Column(name="FLG_TIROCINIO")
	private String flgTirocinio;

	@Column(name="GIORNATE_LAV_PREVISTE")
	private Long giornateLavPreviste;

	@Column(name="ID_STORICO_RAPPORTO_SILP")
	private Long idStoricoRapportoSilp;

	@Column(name="LIVELLO_INQUADRAMENTO")
	private String livelloInquadramento;

	@Column(name="NUM_AGENZ_SOMMINISTR")
	private BigDecimal numAgenzSomministr;

	@Column(name="NUM_INDENNITA_SOMM")
	private BigDecimal numIndennitaSomm;

	@Column(name="NUM_MATRICOLA_LAV")
	private String numMatricolaLav;

	@Column(name="NUM_ORE_SETT_MED")
	private BigDecimal numOreSettMed;

	@Column(name="NUMERO_ATTO_LEGGE68")
	private String numeroAttoLegge68;

	@Column(name="PAT_INAIL")
	private String patInail;

	@Column(name="RETRIBUZIONE_COMPENSO")
	private BigDecimal retribuzioneCompenso;

	@Column(name="TIPO_LAVORAZIONE")
	private String tipoLavorazione;

	//bi-directional many-to-one association to ComDComunicazione
	@ManyToOne
	@JoinColumn(name="ID_COM_D_COMUNICAZIONE")
	private ComDComunicazione comDComunicazione;

	//bi-directional many-to-one association to ComDDatore
	@ManyToOne
	@JoinColumn(name="ID_DATORE_DISTACCATARIO")
	private ComDDatore comDDatoreDistaccatario;

	//bi-directional many-to-one association to ComDDatore
	@ManyToOne
	@JoinColumn(name="ID_AZI_UTILIZZATRICE")
	private ComDDatore comDAziUtilizzatrice;

	//bi-directional many-to-one association to ComDSedeLavoro
	@ManyToOne
	@JoinColumn(name="ID_COM_SEDE_LAVORO_PRECEDENTE")
	private ComDSedeLavoro comDSedeLavoroPrecedente;

	//bi-directional many-to-one association to ComDTutore
	@ManyToOne
	@JoinColumn(name="ID_COM_D_TUTORE")
	private ComDTutore comDTutore;

	//bi-directional many-to-one association to ComTCategLavAssObbl
	@ManyToOne
	@JoinColumn(name="ID_COM_T_CATEG_LAV_ASS_OBBL")
	private ComTCategLavAssObbl comTCategLavAssObbl;

	//bi-directional many-to-one association to ComTCategTirocinante
	@ManyToOne
	@JoinColumn(name="ID_COM_T_CATEG_TIROCINANTE")
	private ComTCategTirocinante comTCategTirocinante;

	//bi-directional many-to-one association to ComTCcnl
	@ManyToOne
	@JoinColumn(name="ID_COM_T_CCNL")
	private ComTCcnl comTCcnl;

	//bi-directional many-to-one association to ComTCessazionerl
	@ManyToOne
	@JoinColumn(name="ID_COM_T_CESSAZIONERL")
	private ComTCessazionerl comTCessazionerl;

	//bi-directional many-to-one association to ComTCpi
	@ManyToOne
	@JoinColumn(name="ID_COM_T_CPI")
	private ComTCpi comTCpi;

	//bi-directional many-to-one association to ComTEntePrevidenziale
	@ManyToOne
	@JoinColumn(name="ID_COM_T_ENTE_PREVIDENZIALE")
	private ComTEntePrevidenziale comTEntePrevidenziale;

	//bi-directional many-to-one association to ComTIstat2001livello5
	@ManyToOne
	@JoinColumn(name="ID_COM_ISTAT2001LIVELLO5")
	private ComTIstat2001livello5 comTIstat2001livello5;

	//bi-directional many-to-one association to ComTLivelloRetribuzione
	@ManyToOne
	@JoinColumn(name="ID_COM_T_LIVELLO_RETRIBUZIONE")
	private ComTLivelloRetribuzione comTLivelloRetribuzione;

	//bi-directional many-to-one association to ComTTipoAttoL68
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_ATTO_L68")
	private ComTTipoAttoL68 comTTipoAttoL68;

	//bi-directional many-to-one association to ComTTipoContratti
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_CONTRATTO")
	private ComTTipoContratti comTTipoContratti;

	//bi-directional many-to-one association to ComTTipoEntePromTirocinio
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_ENTE_PROM_TIR")
	private ComTTipoEntePromTirocinio comTTipoEntePromTirocinio;

	//bi-directional many-to-one association to ComTTipologiaTirocinio
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPOLOGIA_TIROCINIO")
	private ComTTipologiaTirocinio comTTipologiaTirocinio;

	//bi-directional many-to-one association to ComTTipoOrario
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_ORARIO")
	private ComTTipoOrario comTTipoOrario;

	//bi-directional many-to-one association to ComTTrasformazionerl
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TRASFORMAZIONERL")
	private ComTTrasformazionerl comTTrasformazionerl;

	//bi-directional many-to-many association to ComTAgevolazionerl
//	@ManyToMany(mappedBy="comDRapportos")
//	private List<ComTAgevolazionerl> comTAgevolazionerls;

	//bi-directional many-to-one association to ComRRapportoLavoratore
//	@OneToMany(mappedBy="comDRapporto")
//	private List<ComRRapportoLavoratore> comRRapportoLavoratores;

	public ComDRapporto() {
	}

	public long getIdComDRapporto() {
		return this.idComDRapporto;
	}

	public void setIdComDRapporto(long idComDRapporto) {
		this.idComDRapporto = idComDRapporto;
	}

	public String getCfSoggPromotoreTirocinio() {
		return this.cfSoggPromotoreTirocinio;
	}

	public void setCfSoggPromotoreTirocinio(String cfSoggPromotoreTirocinio) {
		this.cfSoggPromotoreTirocinio = cfSoggPromotoreTirocinio;
	}

	public String getCodiceEntePrev() {
		return this.codiceEntePrev;
	}

	public void setCodiceEntePrev(String codiceEntePrev) {
		this.codiceEntePrev = codiceEntePrev;
	}

	public String getCrt() {
		return this.crt;
	}

	public void setCrt(String crt) {
		this.crt = crt;
	}

	public String getDsAttivita() {
		return this.dsAttivita;
	}

	public void setDsAttivita(String dsAttivita) {
		this.dsAttivita = dsAttivita;
	}

	public String getDsAttivitaAgricoltura() {
		return this.dsAttivitaAgricoltura;
	}

	public void setDsAttivitaAgricoltura(String dsAttivitaAgricoltura) {
		this.dsAttivitaAgricoltura = dsAttivitaAgricoltura;
	}

	public String getDsSoggPromTirocinio() {
		return this.dsSoggPromTirocinio;
	}

	public void setDsSoggPromTirocinio(String dsSoggPromTirocinio) {
		this.dsSoggPromTirocinio = dsSoggPromTirocinio;
	}

	public String getDsVoceTariffa1() {
		return this.dsVoceTariffa1;
	}

	public void setDsVoceTariffa1(String dsVoceTariffa1) {
		this.dsVoceTariffa1 = dsVoceTariffa1;
	}

	public String getDsVoceTariffa2() {
		return this.dsVoceTariffa2;
	}

	public void setDsVoceTariffa2(String dsVoceTariffa2) {
		this.dsVoceTariffa2 = dsVoceTariffa2;
	}

	public String getDsVoceTariffa3() {
		return this.dsVoceTariffa3;
	}

	public void setDsVoceTariffa3(String dsVoceTariffa3) {
		this.dsVoceTariffa3 = dsVoceTariffa3;
	}

	public Date getDtApprovazioneCrt() {
		return this.dtApprovazioneCrt;
	}

	public void setDtApprovazioneCrt(Date dtApprovazioneCrt) {
		this.dtApprovazioneCrt = dtApprovazioneCrt;
	}

	public Date getDtCessazione() {
		return this.dtCessazione;
	}

	public void setDtCessazione(Date dtCessazione) {
		this.dtCessazione = dtCessazione;
	}

	public Date getDtCrt() {
		return this.dtCrt;
	}

	public void setDtCrt(Date dtCrt) {
		this.dtCrt = dtCrt;
	}

	public Date getDtEvento() {
		return this.dtEvento;
	}

	public void setDtEvento(Date dtEvento) {
		this.dtEvento = dtEvento;
	}

	public Date getDtFineMissione() {
		return this.dtFineMissione;
	}

	public void setDtFineMissione(Date dtFineMissione) {
		this.dtFineMissione = dtFineMissione;
	}

	public Date getDtFinePeriodoFormativo() {
		return this.dtFinePeriodoFormativo;
	}

	public void setDtFinePeriodoFormativo(Date dtFinePeriodoFormativo) {
		this.dtFinePeriodoFormativo = dtFinePeriodoFormativo;
	}

	public Date getDtFineProroga() {
		return this.dtFineProroga;
	}

	public void setDtFineProroga(Date dtFineProroga) {
		this.dtFineProroga = dtFineProroga;
	}

	public Date getDtFineRapporto() {
		return this.dtFineRapporto;
	}

	public void setDtFineRapporto(Date dtFineRapporto) {
		this.dtFineRapporto = dtFineRapporto;
	}

	public Date getDtInizioMissione() {
		return this.dtInizioMissione;
	}

	public void setDtInizioMissione(Date dtInizioMissione) {
		this.dtInizioMissione = dtInizioMissione;
	}

	public Date getDtInizioRapporto() {
		return this.dtInizioRapporto;
	}

	public void setDtInizioRapporto(Date dtInizioRapporto) {
		this.dtInizioRapporto = dtInizioRapporto;
	}

	public Date getDtLegge68() {
		return this.dtLegge68;
	}

	public void setDtLegge68(Date dtLegge68) {
		this.dtLegge68 = dtLegge68;
	}

	public Date getDtTrasformazione() {
		return this.dtTrasformazione;
	}

	public void setDtTrasformazione(Date dtTrasformazione) {
		this.dtTrasformazione = dtTrasformazione;
	}

	public String getFlgAssLegge6899() {
		return this.flgAssLegge6899;
	}

	public void setFlgAssLegge6899(String flgAssLegge6899) {
		this.flgAssLegge6899 = flgAssLegge6899;
	}

	public String getFlgAssunzioneObbligatoria() {
		return this.flgAssunzioneObbligatoria;
	}

	public void setFlgAssunzioneObbligatoria(String flgAssunzioneObbligatoria) {
		this.flgAssunzioneObbligatoria = flgAssunzioneObbligatoria;
	}

	public String getFlgCcnlApplicato() {
		return this.flgCcnlApplicato;
	}

	public void setFlgCcnlApplicato(String flgCcnlApplicato) {
		this.flgCcnlApplicato = flgCcnlApplicato;
	}

	public String getFlgDistaccoParziale() {
		return this.flgDistaccoParziale;
	}

	public void setFlgDistaccoParziale(String flgDistaccoParziale) {
		this.flgDistaccoParziale = flgDistaccoParziale;
	}

	public String getFlgDistaccoSuAziEstera() {
		return this.flgDistaccoSuAziEstera;
	}

	public void setFlgDistaccoSuAziEstera(String flgDistaccoSuAziEstera) {
		this.flgDistaccoSuAziEstera = flgDistaccoSuAziEstera;
	}

	public String getFlgLavInMobilita() {
		return this.flgLavInMobilita;
	}

	public void setFlgLavInMobilita(String flgLavInMobilita) {
		this.flgLavInMobilita = flgLavInMobilita;
	}

	public String getFlgLavStagionale() {
		return this.flgLavStagionale;
	}

	public void setFlgLavStagionale(String flgLavStagionale) {
		this.flgLavStagionale = flgLavStagionale;
	}

	public String getFlgLavoroAgricoltura() {
		return this.flgLavoroAgricoltura;
	}

	public void setFlgLavoroAgricoltura(String flgLavoroAgricoltura) {
		this.flgLavoroAgricoltura = flgLavoroAgricoltura;
	}

	public String getFlgProsecuzioneDiFatto() {
		return this.flgProsecuzioneDiFatto;
	}

	public void setFlgProsecuzioneDiFatto(String flgProsecuzioneDiFatto) {
		this.flgProsecuzioneDiFatto = flgProsecuzioneDiFatto;
	}

	public String getFlgRischioSilicAsbe() {
		return this.flgRischioSilicAsbe;
	}

	public void setFlgRischioSilicAsbe(String flgRischioSilicAsbe) {
		this.flgRischioSilicAsbe = flgRischioSilicAsbe;
	}

	public String getFlgSocioLavoratore() {
		return this.flgSocioLavoratore;
	}

	public void setFlgSocioLavoratore(String flgSocioLavoratore) {
		this.flgSocioLavoratore = flgSocioLavoratore;
	}

	public String getFlgSommTempoDet() {
		return this.flgSommTempoDet;
	}

	public void setFlgSommTempoDet(String flgSommTempoDet) {
		this.flgSommTempoDet = flgSommTempoDet;
	}

	public String getFlgTirocinio() {
		return this.flgTirocinio;
	}

	public void setFlgTirocinio(String flgTirocinio) {
		this.flgTirocinio = flgTirocinio;
	}

	public Long getGiornateLavPreviste() {
		return this.giornateLavPreviste;
	}

	public void setGiornateLavPreviste(long giornateLavPreviste) {
		this.giornateLavPreviste = giornateLavPreviste;
	}

	public Long getIdStoricoRapportoSilp() {
		return this.idStoricoRapportoSilp;
	}

	public void setIdStoricoRapportoSilp(Long idStoricoRapportoSilp) {
		this.idStoricoRapportoSilp = idStoricoRapportoSilp;
	}

	public String getLivelloInquadramento() {
		return this.livelloInquadramento;
	}

	public void setLivelloInquadramento(String livelloInquadramento) {
		this.livelloInquadramento = livelloInquadramento;
	}

	public BigDecimal getNumAgenzSomministr() {
		return this.numAgenzSomministr;
	}

	public void setNumAgenzSomministr(BigDecimal numAgenzSomministr) {
		this.numAgenzSomministr = numAgenzSomministr;
	}

	public BigDecimal getNumIndennitaSomm() {
		return this.numIndennitaSomm;
	}

	public void setNumIndennitaSomm(BigDecimal numIndennitaSomm) {
		this.numIndennitaSomm = numIndennitaSomm;
	}

	public String getNumMatricolaLav() {
		return this.numMatricolaLav;
	}

	public void setNumMatricolaLav(String numMatricolaLav) {
		this.numMatricolaLav = numMatricolaLav;
	}

	public BigDecimal getNumOreSettMed() {
		return this.numOreSettMed;
	}

	public void setNumOreSettMed(BigDecimal numOreSettMed) {
		this.numOreSettMed = numOreSettMed;
	}

	public String getNumeroAttoLegge68() {
		return this.numeroAttoLegge68;
	}

	public void setNumeroAttoLegge68(String numeroAttoLegge68) {
		this.numeroAttoLegge68 = numeroAttoLegge68;
	}

	public String getPatInail() {
		return this.patInail;
	}

	public void setPatInail(String patInail) {
		this.patInail = patInail;
	}

	public BigDecimal getRetribuzioneCompenso() {
		return this.retribuzioneCompenso;
	}

	public void setRetribuzioneCompenso(BigDecimal retribuzioneCompenso) {
		this.retribuzioneCompenso = retribuzioneCompenso;
	}

	public String getTipoLavorazione() {
		return this.tipoLavorazione;
	}

	public void setTipoLavorazione(String tipoLavorazione) {
		this.tipoLavorazione = tipoLavorazione;
	}

	public ComDComunicazione getComDComunicazione() {
		return this.comDComunicazione;
	}

	public void setComDComunicazione(ComDComunicazione comDComunicazione) {
		this.comDComunicazione = comDComunicazione;
	}

	public ComDTutore getComDTutore() {
		return this.comDTutore;
	}

	public void setComDTutore(ComDTutore comDTutore) {
		this.comDTutore = comDTutore;
	}

	public ComTCategLavAssObbl getComTCategLavAssObbl() {
		return this.comTCategLavAssObbl;
	}

	public void setComTCategLavAssObbl(ComTCategLavAssObbl comTCategLavAssObbl) {
		this.comTCategLavAssObbl = comTCategLavAssObbl;
	}

	public ComTCategTirocinante getComTCategTirocinante() {
		return this.comTCategTirocinante;
	}

	public void setComTCategTirocinante(ComTCategTirocinante comTCategTirocinante) {
		this.comTCategTirocinante = comTCategTirocinante;
	}

	public ComTCcnl getComTCcnl() {
		return this.comTCcnl;
	}

	public void setComTCcnl(ComTCcnl comTCcnl) {
		this.comTCcnl = comTCcnl;
	}

	public ComTCessazionerl getComTCessazionerl() {
		return this.comTCessazionerl;
	}

	public void setComTCessazionerl(ComTCessazionerl comTCessazionerl) {
		this.comTCessazionerl = comTCessazionerl;
	}

	public ComTCpi getComTCpi() {
		return this.comTCpi;
	}

	public void setComTCpi(ComTCpi comTCpi) {
		this.comTCpi = comTCpi;
	}

	public ComTEntePrevidenziale getComTEntePrevidenziale() {
		return this.comTEntePrevidenziale;
	}

	public void setComTEntePrevidenziale(ComTEntePrevidenziale comTEntePrevidenziale) {
		this.comTEntePrevidenziale = comTEntePrevidenziale;
	}

	public ComTIstat2001livello5 getComTIstat2001livello5() {
		return this.comTIstat2001livello5;
	}

	public void setComTIstat2001livello5(ComTIstat2001livello5 comTIstat2001livello5) {
		this.comTIstat2001livello5 = comTIstat2001livello5;
	}

	public ComTLivelloRetribuzione getComTLivelloRetribuzione() {
		return this.comTLivelloRetribuzione;
	}

	public void setComTLivelloRetribuzione(ComTLivelloRetribuzione comTLivelloRetribuzione) {
		this.comTLivelloRetribuzione = comTLivelloRetribuzione;
	}

	public ComTTipoAttoL68 getComTTipoAttoL68() {
		return this.comTTipoAttoL68;
	}

	public void setComTTipoAttoL68(ComTTipoAttoL68 comTTipoAttoL68) {
		this.comTTipoAttoL68 = comTTipoAttoL68;
	}

	public ComTTipoContratti getComTTipoContratti() {
		return this.comTTipoContratti;
	}

	public void setComTTipoContratti(ComTTipoContratti comTTipoContratti) {
		this.comTTipoContratti = comTTipoContratti;
	}

	public ComTTipoEntePromTirocinio getComTTipoEntePromTirocinio() {
		return this.comTTipoEntePromTirocinio;
	}

	public void setComTTipoEntePromTirocinio(ComTTipoEntePromTirocinio comTTipoEntePromTirocinio) {
		this.comTTipoEntePromTirocinio = comTTipoEntePromTirocinio;
	}

	public ComTTipologiaTirocinio getComTTipologiaTirocinio() {
		return this.comTTipologiaTirocinio;
	}

	public void setComTTipologiaTirocinio(ComTTipologiaTirocinio comTTipologiaTirocinio) {
		this.comTTipologiaTirocinio = comTTipologiaTirocinio;
	}

	public ComTTipoOrario getComTTipoOrario() {
		return this.comTTipoOrario;
	}

	public void setComTTipoOrario(ComTTipoOrario comTTipoOrario) {
		this.comTTipoOrario = comTTipoOrario;
	}

	public ComTTrasformazionerl getComTTrasformazionerl() {
		return this.comTTrasformazionerl;
	}

	public void setComTTrasformazionerl(ComTTrasformazionerl comTTrasformazionerl) {
		this.comTTrasformazionerl = comTTrasformazionerl;
	}

	public ComDDatore getComDDatoreDistaccatario() {
		return comDDatoreDistaccatario;
	}

	public void setComDDatoreDistaccatario(ComDDatore comDDatoreDistaccatario) {
		this.comDDatoreDistaccatario = comDDatoreDistaccatario;
	}

	public ComDDatore getComDAziUtilizzatrice() {
		return comDAziUtilizzatrice;
	}

	public void setComDAziUtilizzatrice(ComDDatore comDAziUtilizzatrice) {
		this.comDAziUtilizzatrice = comDAziUtilizzatrice;
	}

	public ComDSedeLavoro getComDSedeLavoroPrecedente() {
		return comDSedeLavoroPrecedente;
	}

	public void setComDSedeLavoroPrecedente(ComDSedeLavoro comDSedeLavoroPrecedente) {
		this.comDSedeLavoroPrecedente = comDSedeLavoroPrecedente;
	}

	public void setGiornateLavPreviste(Long giornateLavPreviste) {
		this.giornateLavPreviste = giornateLavPreviste;
	}

//	public List<ComTAgevolazionerl> getComTAgevolazionerls() {
//		return this.comTAgevolazionerls;
//	}
//
//	public void setComTAgevolazionerls(List<ComTAgevolazionerl> comTAgevolazionerls) {
//		this.comTAgevolazionerls = comTAgevolazionerls;
//	}

//	public List<ComRRapportoLavoratore> getComRRapportoLavoratores() {
//		return this.comRRapportoLavoratores;
//	}
//
//	public void setComRRapportoLavoratores(List<ComRRapportoLavoratore> comRRapportoLavoratores) {
//		this.comRRapportoLavoratores = comRRapportoLavoratores;
//	}
//
//	public ComRRapportoLavoratore addComRRapportoLavoratore(ComRRapportoLavoratore comRRapportoLavoratore) {
//		getComRRapportoLavoratores().add(comRRapportoLavoratore);
//		comRRapportoLavoratore.setComDRapporto(this);
//
//		return comRRapportoLavoratore;
//	}
//
//	public ComRRapportoLavoratore removeComRRapportoLavoratore(ComRRapportoLavoratore comRRapportoLavoratore) {
//		getComRRapportoLavoratores().remove(comRRapportoLavoratore);
//		comRRapportoLavoratore.setComDRapporto(null);
//
//		return comRRapportoLavoratore;
//	}
	
	

}
