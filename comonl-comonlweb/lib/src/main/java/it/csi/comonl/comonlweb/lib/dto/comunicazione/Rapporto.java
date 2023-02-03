/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - LIB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.dto.comunicazione;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategTirocinante;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cessazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cpi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloRetribuzione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoAttoL68;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoEntePromTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;

/**
 * The Class Rapporto.
 */
public class Rapporto extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cfSoggPromotoreTirocinio;
	private String codiceEntePrev;
	private String crt;
	private String dsAttivita;
	private String dsAttivitaAgricoltura;
	private String dsSoggPromTirocinio;
	private String dsVoceTariffa1;
	private String dsVoceTariffa2;
	private String dsVoceTariffa3;
	private Date dtApprovazioneCrt;
	private Date dtCessazione;
	private Date dtCrt;
	private Date dtEvento;
	private Date dtFineMissione;
	private Date dtFinePeriodoFormativo;
	private Date dtFineProroga;
	private Date dtFineRapporto;
	private Date dtInizioMissione;
	private Date dtInizioRapporto;
	private Date dtLegge68;
	private Date dtTrasformazione;
	private String flgAssLegge6899;
	private String flgAssunzioneObbligatoria;
	private String flgCcnlApplicato;
	private String flgDistaccoParziale;
	private String flgDistaccoSuAziEstera;
	private String flgLavInMobilita;
	private String flgLavStagionale;
	private String flgLavoroAgricoltura;
	private String flgProsecuzioneDiFatto;
	private String flgRischioSilicAsbe;
	private String flgSocioLavoratore;
	private String flgSommTempoDet;
	private String flgTirocinio;
	private Long giornateLavPreviste;
	private Long idStoricoRapportoSilp;
	private String livelloInquadramento;
	private BigDecimal numAgenzSomministr;
	private BigDecimal numIndennitaSomm;
	private String numMatricolaLav;
	private BigDecimal numOreSettMed;
	private String numeroAttoLegge68;
	private String patInail;
	private BigDecimal retribuzioneCompenso;
	private String tipoLavorazione;
	private Comunicazione comunicazione;
	private Datore datoreDistaccatario;
	private Datore aziUtilizzatrice;
	private SedeLavoro sedeLavoroPrecedente;
	private Tutore tutore;
	private CategLavAssObbl categLavAssObbl;
	private CategTirocinante categTirocinante;
	private Ccnl ccnl;
	private Cessazionerl cessazionerl;
	private Cpi cpi;
	private EntePrevidenziale entePrevidenziale;
	private Istat2001livello5 istatLivello5;
	private LivelloRetribuzione livelloRetribuzione;
	private TipoAttoL68 tipoAttoL68;
	private TipoContratti tipoContratti;
	private TipoEntePromTirocinio tipoEntePromTirocinio;
	private TipologiaTirocinio tipologiaTirocinio;
	private TipoOrario tipoOrario;
	private Trasformazionerl trasformazionerl;

	/**
	 * @return the cfSoggPromotoreTirocinio
	 */
	public String getCfSoggPromotoreTirocinio() {
		return cfSoggPromotoreTirocinio;
	}
	
	/**
	 * @param cfSoggPromotoreTirocinio the cfSoggPromotoreTirocinio to set
	 */
	public void setCfSoggPromotoreTirocinio(String cfSoggPromotoreTirocinio) {
		this.cfSoggPromotoreTirocinio = cfSoggPromotoreTirocinio;
	}

	/**
	 * @return the codiceEntePrev
	 */
	public String getCodiceEntePrev() {
		return codiceEntePrev;
	}
	
	/**
	 * @param codiceEntePrev the codiceEntePrev to set
	 */
	public void setCodiceEntePrev(String codiceEntePrev) {
		this.codiceEntePrev = codiceEntePrev;
	}

	/**
	 * @return the crt
	 */
	public String getCrt() {
		return crt;
	}
	
	/**
	 * @param crt the crt to set
	 */
	public void setCrt(String crt) {
		this.crt = crt;
	}

	/**
	 * @return the dsAttivita
	 */
	public String getDsAttivita() {
		return dsAttivita;
	}
	
	/**
	 * @param dsAttivita the dsAttivita to set
	 */
	public void setDsAttivita(String dsAttivita) {
		this.dsAttivita = dsAttivita;
	}

	/**
	 * @return the dsAttivitaAgricoltura
	 */
	public String getDsAttivitaAgricoltura() {
		return dsAttivitaAgricoltura;
	}
	
	/**
	 * @param dsAttivitaAgricoltura the dsAttivitaAgricoltura to set
	 */
	public void setDsAttivitaAgricoltura(String dsAttivitaAgricoltura) {
		this.dsAttivitaAgricoltura = dsAttivitaAgricoltura;
	}

	/**
	 * @return the dsSoggPromTirocinio
	 */
	public String getDsSoggPromTirocinio() {
		return dsSoggPromTirocinio;
	}
	
	/**
	 * @param dsSoggPromTirocinio the dsSoggPromTirocinio to set
	 */
	public void setDsSoggPromTirocinio(String dsSoggPromTirocinio) {
		this.dsSoggPromTirocinio = dsSoggPromTirocinio;
	}

	/**
	 * @return the dsVoceTariffa1
	 */
	public String getDsVoceTariffa1() {
		return dsVoceTariffa1;
	}
	
	/**
	 * @param dsVoceTariffa1 the dsVoceTariffa1 to set
	 */
	public void setDsVoceTariffa1(String dsVoceTariffa1) {
		this.dsVoceTariffa1 = dsVoceTariffa1;
	}

	/**
	 * @return the dsVoceTariffa2
	 */
	public String getDsVoceTariffa2() {
		return dsVoceTariffa2;
	}
	
	/**
	 * @param dsVoceTariffa2 the dsVoceTariffa2 to set
	 */
	public void setDsVoceTariffa2(String dsVoceTariffa2) {
		this.dsVoceTariffa2 = dsVoceTariffa2;
	}

	/**
	 * @return the dsVoceTariffa3
	 */
	public String getDsVoceTariffa3() {
		return dsVoceTariffa3;
	}
	
	/**
	 * @param dsVoceTariffa3 the dsVoceTariffa3 to set
	 */
	public void setDsVoceTariffa3(String dsVoceTariffa3) {
		this.dsVoceTariffa3 = dsVoceTariffa3;
	}

	/**
	 * @return the dtApprovazioneCrt
	 */
	public Date getDtApprovazioneCrt() {
		return dtApprovazioneCrt;
	}
	
	/**
	 * @param dtApprovazioneCrt the dtApprovazioneCrt to set
	 */
	public void setDtApprovazioneCrt(Date dtApprovazioneCrt) {
		this.dtApprovazioneCrt = dtApprovazioneCrt;
	}

	/**
	 * @return the dtCessazione
	 */
	public Date getDtCessazione() {
		return dtCessazione;
	}
	
	/**
	 * @param dtCessazione the dtCessazione to set
	 */
	public void setDtCessazione(Date dtCessazione) {
		this.dtCessazione = dtCessazione;
	}

	/**
	 * @return the dtCrt
	 */
	public Date getDtCrt() {
		return dtCrt;
	}
	
	/**
	 * @param dtCrt the dtCrt to set
	 */
	public void setDtCrt(Date dtCrt) {
		this.dtCrt = dtCrt;
	}

	/**
	 * @return the dtEvento
	 */
	public Date getDtEvento() {
		return dtEvento;
	}
	
	/**
	 * @param dtEvento the dtEvento to set
	 */
	public void setDtEvento(Date dtEvento) {
		this.dtEvento = dtEvento;
	}

	/**
	 * @return the dtFineMissione
	 */
	public Date getDtFineMissione() {
		return dtFineMissione;
	}
	
	/**
	 * @param dtFineMissione the dtFineMissione to set
	 */
	public void setDtFineMissione(Date dtFineMissione) {
		this.dtFineMissione = dtFineMissione;
	}

	/**
	 * @return the dtFinePeriodoFormativo
	 */
	public Date getDtFinePeriodoFormativo() {
		return dtFinePeriodoFormativo;
	}
	
	/**
	 * @param dtFinePeriodoFormativo the dtFinePeriodoFormativo to set
	 */
	public void setDtFinePeriodoFormativo(Date dtFinePeriodoFormativo) {
		this.dtFinePeriodoFormativo = dtFinePeriodoFormativo;
	}

	/**
	 * @return the dtFineProroga
	 */
	public Date getDtFineProroga() {
		return dtFineProroga;
	}
	
	/**
	 * @param dtFineProroga the dtFineProroga to set
	 */
	public void setDtFineProroga(Date dtFineProroga) {
		this.dtFineProroga = dtFineProroga;
	}

	/**
	 * @return the dtFineRapporto
	 */
	public Date getDtFineRapporto() {
		return dtFineRapporto;
	}
	
	/**
	 * @param dtFineRapporto the dtFineRapporto to set
	 */
	public void setDtFineRapporto(Date dtFineRapporto) {
		this.dtFineRapporto = dtFineRapporto;
	}

	/**
	 * @return the dtInizioMissione
	 */
	public Date getDtInizioMissione() {
		return dtInizioMissione;
	}
	
	/**
	 * @param dtInizioMissione the dtInizioMissione to set
	 */
	public void setDtInizioMissione(Date dtInizioMissione) {
		this.dtInizioMissione = dtInizioMissione;
	}

	/**
	 * @return the dtInizioRapporto
	 */
	public Date getDtInizioRapporto() {
		return dtInizioRapporto;
	}
	
	/**
	 * @param dtInizioRapporto the dtInizioRapporto to set
	 */
	public void setDtInizioRapporto(Date dtInizioRapporto) {
		this.dtInizioRapporto = dtInizioRapporto;
	}

	/**
	 * @return the dtLegge68
	 */
	public Date getDtLegge68() {
		return dtLegge68;
	}
	
	/**
	 * @param dtLegge68 the dtLegge68 to set
	 */
	public void setDtLegge68(Date dtLegge68) {
		this.dtLegge68 = dtLegge68;
	}

	/**
	 * @return the dtTrasformazione
	 */
	public Date getDtTrasformazione() {
		return dtTrasformazione;
	}
	
	/**
	 * @param dtTrasformazione the dtTrasformazione to set
	 */
	public void setDtTrasformazione(Date dtTrasformazione) {
		this.dtTrasformazione = dtTrasformazione;
	}

	/**
	 * @return the flgAssLegge6899
	 */
	public String getFlgAssLegge6899() {
		return flgAssLegge6899;
	}
	
	/**
	 * @param flgAssLegge6899 the flgAssLegge6899 to set
	 */
	public void setFlgAssLegge6899(String flgAssLegge6899) {
		this.flgAssLegge6899 = flgAssLegge6899;
	}

	/**
	 * @return the flgAssunzioneObbligatoria
	 */
	public String getFlgAssunzioneObbligatoria() {
		return flgAssunzioneObbligatoria;
	}
	
	/**
	 * @param flgAssunzioneObbligatoria the flgAssunzioneObbligatoria to set
	 */
	public void setFlgAssunzioneObbligatoria(String flgAssunzioneObbligatoria) {
		this.flgAssunzioneObbligatoria = flgAssunzioneObbligatoria;
	}

	/**
	 * @return the flgCcnlApplicato
	 */
	public String getFlgCcnlApplicato() {
		return flgCcnlApplicato;
	}
	
	/**
	 * @param flgCcnlApplicato the flgCcnlApplicato to set
	 */
	public void setFlgCcnlApplicato(String flgCcnlApplicato) {
		this.flgCcnlApplicato = flgCcnlApplicato;
	}

	/**
	 * @return the flgDistaccoParziale
	 */
	public String getFlgDistaccoParziale() {
		return flgDistaccoParziale;
	}
	
	/**
	 * @param flgDistaccoParziale the flgDistaccoParziale to set
	 */
	public void setFlgDistaccoParziale(String flgDistaccoParziale) {
		this.flgDistaccoParziale = flgDistaccoParziale;
	}

	/**
	 * @return the flgDistaccoSuAziEstera
	 */
	public String getFlgDistaccoSuAziEstera() {
		return flgDistaccoSuAziEstera;
	}
	
	/**
	 * @param flgDistaccoSuAziEstera the flgDistaccoSuAziEstera to set
	 */
	public void setFlgDistaccoSuAziEstera(String flgDistaccoSuAziEstera) {
		this.flgDistaccoSuAziEstera = flgDistaccoSuAziEstera;
	}

	/**
	 * @return the flgLavInMobilita
	 */
	public String getFlgLavInMobilita() {
		return flgLavInMobilita;
	}
	
	/**
	 * @param flgLavInMobilita the flgLavInMobilita to set
	 */
	public void setFlgLavInMobilita(String flgLavInMobilita) {
		this.flgLavInMobilita = flgLavInMobilita;
	}

	/**
	 * @return the flgLavStagionale
	 */
	public String getFlgLavStagionale() {
		return flgLavStagionale;
	}
	
	/**
	 * @param flgLavStagionale the flgLavStagionale to set
	 */
	public void setFlgLavStagionale(String flgLavStagionale) {
		this.flgLavStagionale = flgLavStagionale;
	}

	/**
	 * @return the flgLavoroAgricoltura
	 */
	public String getFlgLavoroAgricoltura() {
		return flgLavoroAgricoltura;
	}
	
	/**
	 * @param flgLavoroAgricoltura the flgLavoroAgricoltura to set
	 */
	public void setFlgLavoroAgricoltura(String flgLavoroAgricoltura) {
		this.flgLavoroAgricoltura = flgLavoroAgricoltura;
	}

	/**
	 * @return the flgProsecuzioneDiFatto
	 */
	public String getFlgProsecuzioneDiFatto() {
		return flgProsecuzioneDiFatto;
	}
	
	/**
	 * @param flgProsecuzioneDiFatto the flgProsecuzioneDiFatto to set
	 */
	public void setFlgProsecuzioneDiFatto(String flgProsecuzioneDiFatto) {
		this.flgProsecuzioneDiFatto = flgProsecuzioneDiFatto;
	}

	/**
	 * @return the flgRischioSilicAsbe
	 */
	public String getFlgRischioSilicAsbe() {
		return flgRischioSilicAsbe;
	}
	
	/**
	 * @param flgRischioSilicAsbe the flgRischioSilicAsbe to set
	 */
	public void setFlgRischioSilicAsbe(String flgRischioSilicAsbe) {
		this.flgRischioSilicAsbe = flgRischioSilicAsbe;
	}

	/**
	 * @return the flgSocioLavoratore
	 */
	public String getFlgSocioLavoratore() {
		return flgSocioLavoratore;
	}
	
	/**
	 * @param flgSocioLavoratore the flgSocioLavoratore to set
	 */
	public void setFlgSocioLavoratore(String flgSocioLavoratore) {
		this.flgSocioLavoratore = flgSocioLavoratore;
	}

	/**
	 * @return the flgSommTempoDet
	 */
	public String getFlgSommTempoDet() {
		return flgSommTempoDet;
	}
	
	/**
	 * @param flgSommTempoDet the flgSommTempoDet to set
	 */
	public void setFlgSommTempoDet(String flgSommTempoDet) {
		this.flgSommTempoDet = flgSommTempoDet;
	}

	/**
	 * @return the flgTirocinio
	 */
	public String getFlgTirocinio() {
		return flgTirocinio;
	}
	
	/**
	 * @param flgTirocinio the flgTirocinio to set
	 */
	public void setFlgTirocinio(String flgTirocinio) {
		this.flgTirocinio = flgTirocinio;
	}

	/**
	 * @return the giornateLavPreviste
	 */
	public Long getGiornateLavPreviste() {
		return giornateLavPreviste;
	}
	
	/**
	 * @param giornateLavPreviste the giornateLavPreviste to set
	 */
	public void setGiornateLavPreviste(Long giornateLavPreviste) {
		this.giornateLavPreviste = giornateLavPreviste;
	}

	/**
	 * @return the idStoricoRapportoSilp
	 */
	public Long getIdStoricoRapportoSilp() {
		return idStoricoRapportoSilp;
	}
	
	/**
	 * @param idStoricoRapportoSilp the idStoricoRapportoSilp to set
	 */
	public void setIdStoricoRapportoSilp(Long idStoricoRapportoSilp) {
		this.idStoricoRapportoSilp = idStoricoRapportoSilp;
	}

	/**
	 * @return the livelloInquadramento
	 */
	public String getLivelloInquadramento() {
		return livelloInquadramento;
	}
	
	/**
	 * @param livelloInquadramento the livelloInquadramento to set
	 */
	public void setLivelloInquadramento(String livelloInquadramento) {
		this.livelloInquadramento = livelloInquadramento;
	}

	/**
	 * @return the numAgenzSomministr
	 */
	public BigDecimal getNumAgenzSomministr() {
		return numAgenzSomministr;
	}
	
	/**
	 * @param numAgenzSomministr the numAgenzSomministr to set
	 */
	public void setNumAgenzSomministr(BigDecimal numAgenzSomministr) {
		this.numAgenzSomministr = numAgenzSomministr;
	}

	/**
	 * @return the numIndennitaSomm
	 */
	public BigDecimal getNumIndennitaSomm() {
		return numIndennitaSomm;
	}
	
	/**
	 * @param numIndennitaSomm the numIndennitaSomm to set
	 */
	public void setNumIndennitaSomm(BigDecimal numIndennitaSomm) {
		this.numIndennitaSomm = numIndennitaSomm;
	}

	/**
	 * @return the numMatricolaLav
	 */
	public String getNumMatricolaLav() {
		return numMatricolaLav;
	}
	
	/**
	 * @param numMatricolaLav the numMatricolaLav to set
	 */
	public void setNumMatricolaLav(String numMatricolaLav) {
		this.numMatricolaLav = numMatricolaLav;
	}

	/**
	 * @return the numOreSettMed
	 */
	public BigDecimal getNumOreSettMed() {
		return numOreSettMed;
	}
	
	/**
	 * @param numOreSettMed the numOreSettMed to set
	 */
	public void setNumOreSettMed(BigDecimal numOreSettMed) {
		this.numOreSettMed = numOreSettMed;
	}

	/**
	 * @return the numeroAttoLegge68
	 */
	public String getNumeroAttoLegge68() {
		return numeroAttoLegge68;
	}
	
	/**
	 * @param numeroAttoLegge68 the numeroAttoLegge68 to set
	 */
	public void setNumeroAttoLegge68(String numeroAttoLegge68) {
		this.numeroAttoLegge68 = numeroAttoLegge68;
	}

	/**
	 * @return the patInail
	 */
	public String getPatInail() {
		if (patInail != null)
			return patInail.trim();
		return patInail;
	}
	
	/**
	 * @param patInail the patInail to set
	 */
	public void setPatInail(String patInail) {
		this.patInail = patInail;
	}

	/**
	 * @return the retribuzioneCompenso
	 */
	public BigDecimal getRetribuzioneCompenso() {
		return retribuzioneCompenso;
	}
	
	/**
	 * @param retribuzioneCompenso the retribuzioneCompenso to set
	 */
	public void setRetribuzioneCompenso(BigDecimal retribuzioneCompenso) {
		this.retribuzioneCompenso = retribuzioneCompenso;
	}

	/**
	 * @return the tipoLavorazione
	 */
	public String getTipoLavorazione() {
		return tipoLavorazione;
	}
	
	/**
	 * @param tipoLavorazione the tipoLavorazione to set
	 */
	public void setTipoLavorazione(String tipoLavorazione) {
		this.tipoLavorazione = tipoLavorazione;
	}

	/**
	 * @return the comunicazione
	 */
	public Comunicazione getComunicazione() {
		return comunicazione;
	}
	
	/**
	 * @param comunicazione the comunicazione to set
	 */
	public void setComunicazione(Comunicazione comunicazione) {
		this.comunicazione = comunicazione;
	}

	/**
	 * @return the tutore
	 */
	public Tutore getTutore() {
		return tutore;
	}
	
	/**
	 * @param tutore the tutore to set
	 */
	public void setTutore(Tutore tutore) {
		this.tutore = tutore;
	}

	/**
	 * @return the categLavAssObbl
	 */
	public CategLavAssObbl getCategLavAssObbl() {
		return categLavAssObbl;
	}
	
	/**
	 * @param categLavAssObbl the categLavAssObbl to set
	 */
	public void setCategLavAssObbl(CategLavAssObbl categLavAssObbl) {
		this.categLavAssObbl = categLavAssObbl;
	}

	/**
	 * @return the categTirocinante
	 */
	public CategTirocinante getCategTirocinante() {
		return categTirocinante;
	}
	
	/**
	 * @param categTirocinante the categTirocinante to set
	 */
	public void setCategTirocinante(CategTirocinante categTirocinante) {
		this.categTirocinante = categTirocinante;
	}

	/**
	 * @return the ccnl
	 */
	public Ccnl getCcnl() {
		return ccnl;
	}
	
	/**
	 * @param ccnl the ccnl to set
	 */
	public void setCcnl(Ccnl ccnl) {
		this.ccnl = ccnl;
	}

	/**
	 * @return the cessazionerl
	 */
	public Cessazionerl getCessazionerl() {
		return cessazionerl;
	}
	
	/**
	 * @param cessazionerl the cessazionerl to set
	 */
	public void setCessazionerl(Cessazionerl cessazionerl) {
		this.cessazionerl = cessazionerl;
	}

	/**
	 * @return the cpi
	 */
	public Cpi getCpi() {
		return cpi;
	}
	
	/**
	 * @param cpi the cpi to set
	 */
	public void setCpi(Cpi cpi) {
		this.cpi = cpi;
	}

	/**
	 * @return the entePrevidenziale
	 */
	public EntePrevidenziale getEntePrevidenziale() {
		return entePrevidenziale;
	}
	
	/**
	 * @param entePrevidenziale the entePrevidenziale to set
	 */
	public void setEntePrevidenziale(EntePrevidenziale entePrevidenziale) {
		this.entePrevidenziale = entePrevidenziale;
	}

	/**
	 * @return the istat2001livello5
	 */
	

	/**
	 * @return the livelloRetribuzione
	 */
	public LivelloRetribuzione getLivelloRetribuzione() {
		return livelloRetribuzione;
	}
	
	public Istat2001livello5 getIstatLivello5() {
		return istatLivello5;
	}

	public void setIstatLivello5(Istat2001livello5 istatLivello5) {
		this.istatLivello5 = istatLivello5;
	}

	/**
	 * @param livelloRetribuzione the livelloRetribuzione to set
	 */
	public void setLivelloRetribuzione(LivelloRetribuzione livelloRetribuzione) {
		this.livelloRetribuzione = livelloRetribuzione;
	}

	/**
	 * @return the tipoAttoL68
	 */
	public TipoAttoL68 getTipoAttoL68() {
		return tipoAttoL68;
	}
	
	/**
	 * @param tipoAttoL68 the tipoAttoL68 to set
	 */
	public void setTipoAttoL68(TipoAttoL68 tipoAttoL68) {
		this.tipoAttoL68 = tipoAttoL68;
	}

	/**
	 * @return the tipoContratti
	 */
	public TipoContratti getTipoContratti() {
		return tipoContratti;
	}
	
	/**
	 * @param tipoContratti the tipoContratti to set
	 */
	public void setTipoContratti(TipoContratti tipoContratti) {
		this.tipoContratti = tipoContratti;
	}

	/**
	 * @return the tipoEntePromTirocinio
	 */
	public TipoEntePromTirocinio getTipoEntePromTirocinio() {
		return tipoEntePromTirocinio;
	}
	
	/**
	 * @param tipoEntePromTirocinio the tipoEntePromTirocinio to set
	 */
	public void setTipoEntePromTirocinio(TipoEntePromTirocinio tipoEntePromTirocinio) {
		this.tipoEntePromTirocinio = tipoEntePromTirocinio;
	}

	/**
	 * @return the tipologiaTirocinio
	 */
	public TipologiaTirocinio getTipologiaTirocinio() {
		return tipologiaTirocinio;
	}
	
	/**
	 * @param tipologiaTirocinio the tipologiaTirocinio to set
	 */
	public void setTipologiaTirocinio(TipologiaTirocinio tipologiaTirocinio) {
		this.tipologiaTirocinio = tipologiaTirocinio;
	}

	/**
	 * @return the tipoOrario
	 */
	public TipoOrario getTipoOrario() {
		return tipoOrario;
	}
	
	/**
	 * @param tipoOrario the tipoOrario to set
	 */
	public void setTipoOrario(TipoOrario tipoOrario) {
		this.tipoOrario = tipoOrario;
	}

	/**
	 * @return the trasformazionerl
	 */
	public Trasformazionerl getTrasformazionerl() {
		return trasformazionerl;
	}
	
	/**
	 * @param trasformazionerl the trasformazionerl to set
	 */
	public void setTrasformazionerl(Trasformazionerl trasformazionerl) {
		this.trasformazionerl = trasformazionerl;
	}

	public Datore getDatoreDistaccatario() {
		return datoreDistaccatario;
	}

	public void setDatoreDistaccatario(Datore datoreDistaccatario) {
		this.datoreDistaccatario = datoreDistaccatario;
	}

	public Datore getAziUtilizzatrice() {
		return aziUtilizzatrice;
	}

	public void setAziUtilizzatrice(Datore aziUtilizzatrice) {
		this.aziUtilizzatrice = aziUtilizzatrice;
	}

	public SedeLavoro getSedeLavoroPrecedente() {
		return sedeLavoroPrecedente;
	}

	public void setSedeLavoroPrecedente(SedeLavoro sedeLavoroPrecedente) {
		this.sedeLavoroPrecedente = sedeLavoroPrecedente;
	}
	

}
