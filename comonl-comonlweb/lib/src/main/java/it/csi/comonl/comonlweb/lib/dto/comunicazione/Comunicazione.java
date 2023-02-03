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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.BaseAuditedDto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cpi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Provincia;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoAcquisizione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazioneTu;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoProvenienza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSomministrazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTracciato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;

/**
 * The Class Comunicazione.
 */
public class Comunicazione extends BaseAuditedDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private String annoDataOraRegionale;
	private Long annoProtCom;
	private Long annoProtMsgPosta;
	private Long annoProtRettifica;
	private Long annoProtUrgenza;
	private String cfImpresa;
	private String cfLavCAssunzione;
	private String codiceComunRegPrec;
	private String codiceComunicazioneReg;
	private String codiceEnte;
	private String codiceFiscaleSoggetto;
	private String dsCausaForzamaggiore;
	private String dsMotivoUrgenza;
	private Date dtAssCAssunzione;
	private Date dtFineAffittoRamo;
	private Date dtInvio;
	private Date dtInvioMinistero;
	private Date dtProtocollo;
	private Date dtTimbroPostale;
	private Date dtTrasferimentoVarDatori;
	private Date dtVerifica;
	private String email;
	private String emailsoggetto;
	private String flgCausaForzamaggiore;
	private String flgComDatLav;
	private String flgComunSegUrgenza;
	private String flgInvioMinistero;
	private String flgMultiLav;
	private String flgRettifica;
	private String flgSommin;
	private Long idComDComunicPrecedente;
	private Long numAgenzSomministr;
	private Long numProtCom;
	private Long numProtComRettifica;
	private Long numProtComUrgenza;
	private Long numProtMsgPosta;
	private String verifica;
	// private Comunicazione comunicazioneGruppo;
	private Long idComDComunicazGruppo;
	// private Comunicazione comunicazionePrecedenteAnnullo;
	// private Comunicazione comunicazioneDaRettificare;
	private Long idComTuPrecedenteAnnullo;
	private Long idComTuDaRettificare;
	private Delegato delegato;
	private Cpi cpi;
	private Provincia provincia;
	private StatoComunicazione statoComunicazione;
	private TipoAcquisizione tipoAcquisizione;
	private TipoComunicazione tipoComunicazione;
	private TipoComunicazioneTu tipoComunicazioneTu;
	private TipoProvenienza tipoProvenienza;
	private TipoSoggettoAbilitato tipoSoggettoAbilitato;
	private TipoSomministrazione tipoSomministrazione;
	private TipoTracciato tipoTracciato;
	private TipoTrasferimento tipoTrasferimento;

	/* campi aggiunti */
	private Date dataRiferimento;
	private Long idRapportoLavoro;
	private String flgCurrentRecord;
	
	/* oggetti aggiuntivi */
	private Datore datoreAttuale;
	private Datore datorePrecedente;
	
	private List<Lavoratore> lavoratori; // possono essere più di uno se la CO è in stato bozza ed + gestito a S il flg_multilav
	private Lavoratore lavoratoreCoObbligato;
	
	private Rapporto rapporto;
	private Rapporto missione;
	
	private List<RapportiLavoratoriSediInteressateVD> rapLavSedeVD;
	
	
	private Date dtProtocolloPrec;
	private Long annoProtComPrec;
	private Long numProtComPrec;
	private Provincia provinciaPrec;
	
	/**
	 * @return the annoDataOraRegionale
	 */
	public String getAnnoDataOraRegionale() {
		return annoDataOraRegionale;
	}

	/**
	 * @param annoDataOraRegionale the annoDataOraRegionale to set
	 */
	public void setAnnoDataOraRegionale(String annoDataOraRegionale) {
		this.annoDataOraRegionale = annoDataOraRegionale;
	}

	/**
	 * @return the annoProtCom
	 */
	public Long getAnnoProtCom() {
		return annoProtCom;
	}

	/**
	 * @param annoProtCom the annoProtCom to set
	 */
	public void setAnnoProtCom(Long annoProtCom) {
		this.annoProtCom = annoProtCom;
	}

	/**
	 * @return the annoProtMsgPosta
	 */
	public Long getAnnoProtMsgPosta() {
		return annoProtMsgPosta;
	}

	/**
	 * @param annoProtMsgPosta the annoProtMsgPosta to set
	 */
	public void setAnnoProtMsgPosta(Long annoProtMsgPosta) {
		this.annoProtMsgPosta = annoProtMsgPosta;
	}

	/**
	 * @return the annoProtRettifica
	 */
	public Long getAnnoProtRettifica() {
		return annoProtRettifica;
	}

	/**
	 * @param annoProtRettifica the annoProtRettifica to set
	 */
	public void setAnnoProtRettifica(Long annoProtRettifica) {
		this.annoProtRettifica = annoProtRettifica;
	}

	/**
	 * @return the annoProtUrgenza
	 */
	public Long getAnnoProtUrgenza() {
		return annoProtUrgenza;
	}

	/**
	 * @param annoProtUrgenza the annoProtUrgenza to set
	 */
	public void setAnnoProtUrgenza(Long annoProtUrgenza) {
		this.annoProtUrgenza = annoProtUrgenza;
	}

	/**
	 * @return the cfImpresa
	 */
	public String getCfImpresa() {
		return cfImpresa;
	}

	/**
	 * @param cfImpresa the cfImpresa to set
	 */
	public void setCfImpresa(String cfImpresa) {
		this.cfImpresa = cfImpresa;
	}

	/**
	 * @return the cfLavCAssunzione
	 */
	public String getCfLavCAssunzione() {
		return cfLavCAssunzione;
	}

	/**
	 * @param cfLavCAssunzione the cfLavCAssunzione to set
	 */
	public void setCfLavCAssunzione(String cfLavCAssunzione) {
		this.cfLavCAssunzione = cfLavCAssunzione;
	}

	/**
	 * @return the codiceComunRegPrec
	 */
	public String getCodiceComunRegPrec() {
		return codiceComunRegPrec;
	}

	/**
	 * @param codiceComunRegPrec the codiceComunRegPrec to set
	 */
	public void setCodiceComunRegPrec(String codiceComunRegPrec) {
		this.codiceComunRegPrec = codiceComunRegPrec;
	}

	/**
	 * @return the codiceComunicazioneReg
	 */
	public String getCodiceComunicazioneReg() {
		return codiceComunicazioneReg;
	}

	/**
	 * @param codiceComunicazioneReg the codiceComunicazioneReg to set
	 */
	public void setCodiceComunicazioneReg(String codiceComunicazioneReg) {
		this.codiceComunicazioneReg = codiceComunicazioneReg;
	}

	/**
	 * @return the codiceEnte
	 */
	public String getCodiceEnte() {
		return codiceEnte;
	}

	/**
	 * @param codiceEnte the codiceEnte to set
	 */
	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}

	/**
	 * @return the codiceFiscaleSoggetto
	 */
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}

	/**
	 * @param codiceFiscaleSoggetto the codiceFiscaleSoggetto to set
	 */
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}

	/**
	 * @return the dsCausaForzamaggiore
	 */
	public String getDsCausaForzamaggiore() {
		return dsCausaForzamaggiore;
	}

	/**
	 * @param dsCausaForzamaggiore the dsCausaForzamaggiore to set
	 */
	public void setDsCausaForzamaggiore(String dsCausaForzamaggiore) {
		this.dsCausaForzamaggiore = dsCausaForzamaggiore;
	}

	/**
	 * @return the dsMotivoUrgenza
	 */
	public String getDsMotivoUrgenza() {
		return dsMotivoUrgenza;
	}

	/**
	 * @param dsMotivoUrgenza the dsMotivoUrgenza to set
	 */
	public void setDsMotivoUrgenza(String dsMotivoUrgenza) {
		this.dsMotivoUrgenza = dsMotivoUrgenza;
	}

	/**
	 * @return the dtAssCAssunzione
	 */
	public Date getDtAssCAssunzione() {
		return dtAssCAssunzione;
	}

	/**
	 * @param dtAssCAssunzione the dtAssCAssunzione to set
	 */
	public void setDtAssCAssunzione(Date dtAssCAssunzione) {
		this.dtAssCAssunzione = dtAssCAssunzione;
	}

	/**
	 * @return the dtFineAffittoRamo
	 */
	public Date getDtFineAffittoRamo() {
		return dtFineAffittoRamo;
	}

	/**
	 * @param dtFineAffittoRamo the dtFineAffittoRamo to set
	 */
	public void setDtFineAffittoRamo(Date dtFineAffittoRamo) {
		this.dtFineAffittoRamo = dtFineAffittoRamo;
	}

	/**
	 * @return the dtInsert
	 */
	public Date getDtInsert() {
		return dtInsert;
	}

	/**
	 * @param dtInsert the dtInsert to set
	 */
	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	/**
	 * @return the dtInvio
	 */
	public Date getDtInvio() {
		return dtInvio;
	}

	/**
	 * @param dtInvio the dtInvio to set
	 */
	public void setDtInvio(Date dtInvio) {
		this.dtInvio = dtInvio;
	}

	/**
	 * @return the dtInvioMinistero
	 */
	public Date getDtInvioMinistero() {
		return dtInvioMinistero;
	}

	/**
	 * @param dtInvioMinistero the dtInvioMinistero to set
	 */
	public void setDtInvioMinistero(Date dtInvioMinistero) {
		this.dtInvioMinistero = dtInvioMinistero;
	}

	/**
	 * @return the dtProtocollo
	 */
	public Date getDtProtocollo() {
		return dtProtocollo;
	}

	/**
	 * @param dtProtocollo the dtProtocollo to set
	 */
	public void setDtProtocollo(Date dtProtocollo) {
		this.dtProtocollo = dtProtocollo;
	}

	/**
	 * @return the dtTimbroPostale
	 */
	public Date getDtTimbroPostale() {
		return dtTimbroPostale;
	}

	/**
	 * @param dtTimbroPostale the dtTimbroPostale to set
	 */
	public void setDtTimbroPostale(Date dtTimbroPostale) {
		this.dtTimbroPostale = dtTimbroPostale;
	}

	/**
	 * @return the dtTrasferimentoVarDatori
	 */
	public Date getDtTrasferimentoVarDatori() {
		return dtTrasferimentoVarDatori;
	}

	/**
	 * @param dtTrasferimentoVarDatori the dtTrasferimentoVarDatori to set
	 */
	public void setDtTrasferimentoVarDatori(Date dtTrasferimentoVarDatori) {
		this.dtTrasferimentoVarDatori = dtTrasferimentoVarDatori;
	}

	/**
	 * @return the dtUltMod
	 */
	public Date getDtUltMod() {
		return dtUltMod;
	}

	/**
	 * @param dtUltMod the dtUltMod to set
	 */
	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	/**
	 * @return the dtVerifica
	 */
	public Date getDtVerifica() {
		return dtVerifica;
	}

	/**
	 * @param dtVerifica the dtVerifica to set
	 */
	public void setDtVerifica(Date dtVerifica) {
		this.dtVerifica = dtVerifica;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the emailsoggetto
	 */
	public String getEmailsoggetto() {
		return emailsoggetto;
	}

	/**
	 * @param emailsoggetto the emailsoggetto to set
	 */
	public void setEmailsoggetto(String emailsoggetto) {
		this.emailsoggetto = emailsoggetto;
	}

	/**
	 * @return the flgCausaForzamaggiore
	 */
	public String getFlgCausaForzamaggiore() {
		return flgCausaForzamaggiore;
	}

	/**
	 * @param flgCausaForzamaggiore the flgCausaForzamaggiore to set
	 */
	public void setFlgCausaForzamaggiore(String flgCausaForzamaggiore) {
		this.flgCausaForzamaggiore = flgCausaForzamaggiore;
	}

	/**
	 * @return the flgComDatLav
	 */
	public String getFlgComDatLav() {
		return flgComDatLav;
	}

	/**
	 * @param flgComDatLav the flgComDatLav to set
	 */
	public void setFlgComDatLav(String flgComDatLav) {
		this.flgComDatLav = flgComDatLav;
	}

	/**
	 * @return the flgComunSegUrgenza
	 */
	public String getFlgComunSegUrgenza() {
		return flgComunSegUrgenza;
	}

	/**
	 * @param flgComunSegUrgenza the flgComunSegUrgenza to set
	 */
	public void setFlgComunSegUrgenza(String flgComunSegUrgenza) {
		this.flgComunSegUrgenza = flgComunSegUrgenza;
	}

	/**
	 * @return the flgInvioMinistero
	 */
	public String getFlgInvioMinistero() {
		return flgInvioMinistero;
	}

	/**
	 * @param flgInvioMinistero the flgInvioMinistero to set
	 */
	public void setFlgInvioMinistero(String flgInvioMinistero) {
		this.flgInvioMinistero = flgInvioMinistero;
	}

	/**
	 * @return the flgMultiLav
	 */
	public String getFlgMultiLav() {
		return flgMultiLav;
	}

	/**
	 * @param flgMultiLav the flgMultiLav to set
	 */
	public void setFlgMultiLav(String flgMultiLav) {
		this.flgMultiLav = flgMultiLav;
	}

	/**
	 * @return the flgRettifica
	 */
	public String getFlgRettifica() {
		return flgRettifica;
	}

	/**
	 * @param flgRettifica the flgRettifica to set
	 */
	public void setFlgRettifica(String flgRettifica) {
		this.flgRettifica = flgRettifica;
	}

	/**
	 * @return the flgSommin
	 */
	public String getFlgSommin() {
		return flgSommin;
	}

	/**
	 * @param flgSommin the flgSommin to set
	 */
	public void setFlgSommin(String flgSommin) {
		this.flgSommin = flgSommin;
	}

	/**
	 * @return the idComDComunicPrecedente
	 */
	public Long getIdComDComunicPrecedente() {
		return idComDComunicPrecedente;
	}

	/**
	 * @param idComDComunicPrecedente the idComDComunicPrecedente to set
	 */
	public void setIdComDComunicPrecedente(Long idComDComunicPrecedente) {
		this.idComDComunicPrecedente = idComDComunicPrecedente;
	}

	/**
	 * @return the idUserInsert
	 */
	public String getIdUserInsert() {
		return idUserInsert;
	}

	/**
	 * @param idUserInsert the idUserInsert to set
	 */
	public void setIdUserInsert(String idUserInsert) {
		this.idUserInsert = idUserInsert;
	}

	/**
	 * @return the idUserUltMod
	 */
	public String getIdUserUltMod() {
		return idUserUltMod;
	}

	/**
	 * @param idUserUltMod the idUserUltMod to set
	 */
	public void setIdUserUltMod(String idUserUltMod) {
		this.idUserUltMod = idUserUltMod;
	}

	/**
	 * @return the numAgenzSomministr
	 */
	public Long getNumAgenzSomministr() {
		return numAgenzSomministr;
	}

	/**
	 * @param numAgenzSomministr the numAgenzSomministr to set
	 */
	public void setNumAgenzSomministr(Long numAgenzSomministr) {
		this.numAgenzSomministr = numAgenzSomministr;
	}

	/**
	 * @return the numProtCom
	 */
	public Long getNumProtCom() {
		return numProtCom;
	}

	/**
	 * @param numProtCom the numProtCom to set
	 */
	public void setNumProtCom(Long numProtCom) {
		this.numProtCom = numProtCom;
	}

	/**
	 * @return the numProtComRettifica
	 */
	public Long getNumProtComRettifica() {
		return numProtComRettifica;
	}

	/**
	 * @param numProtComRettifica the numProtComRettifica to set
	 */
	public void setNumProtComRettifica(Long numProtComRettifica) {
		this.numProtComRettifica = numProtComRettifica;
	}

	/**
	 * @return the numProtComUrgenza
	 */
	public Long getNumProtComUrgenza() {
		return numProtComUrgenza;
	}

	/**
	 * @param numProtComUrgenza the numProtComUrgenza to set
	 */
	public void setNumProtComUrgenza(Long numProtComUrgenza) {
		this.numProtComUrgenza = numProtComUrgenza;
	}

	/**
	 * @return the numProtMsgPosta
	 */
	public Long getNumProtMsgPosta() {
		return numProtMsgPosta;
	}

	/**
	 * @param numProtMsgPosta the numProtMsgPosta to set
	 */
	public void setNumProtMsgPosta(Long numProtMsgPosta) {
		this.numProtMsgPosta = numProtMsgPosta;
	}

	/**
	 * @return the verifica
	 */
	public String getVerifica() {
		return verifica;
	}

	/**
	 * @param verifica the verifica to set
	 */
	public void setVerifica(String verifica) {
		this.verifica = verifica;
	}

	/**
	 * @return the comunicazioneGruppo
	 */
	/*
	 * public Comunicazione getComunicazioneGruppo() { return comunicazioneGruppo; }
	 */

	/**
	 * @param comunicazioneGruppo the comunicazioneGruppo to set
	 */
	/*
	 * public void setComunicazioneGruppo(Comunicazione comunicazioneGruppo) {
	 * this.comunicazioneGruppo = comunicazioneGruppo; }
	 */

	/**
	 * @return the comunicazionePrecedenteAnnullo
	 */
	/*
	 * public Comunicazione getComunicazionePrecedenteAnnullo() { return
	 * comunicazionePrecedenteAnnullo; }
	 */

	public Long getIdComDComunicazGruppo() {
		return idComDComunicazGruppo;
	}

	public void setIdComDComunicazGruppo(Long idComDComunicazGruppo) {
		this.idComDComunicazGruppo = idComDComunicazGruppo;
	}

	/**
	 * @param comunicazionePrecedenteAnnullo the comunicazionePrecedenteAnnullo to
	 *                                       set
	 */
	/*
	 * public void setComunicazionePrecedenteAnnullo(Comunicazione
	 * comunicazionePrecedenteAnnullo) { this.comunicazionePrecedenteAnnullo =
	 * comunicazionePrecedenteAnnullo; }
	 */

	/**
	 * @return the comunicazioneDaRettificare
	 */
	/*
	 * public Comunicazione getComunicazioneDaRettificare() { return
	 * comunicazioneDaRettificare; }
	 */

	/**
	 * @param comunicazioneDaRettificare the comunicazioneDaRettificare to set
	 */
	/*
	 * public void setComunicazioneDaRettificare(Comunicazione
	 * comunicazioneDaRettificare) { this.comunicazioneDaRettificare =
	 * comunicazioneDaRettificare; }
	 */

	/**
	 * @return the delegato
	 */
	public Delegato getDelegato() {
		return delegato;
	}

	/**
	 * @param delegato the delegato to set
	 */
	public void setDelegato(Delegato delegato) {
		this.delegato = delegato;
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
	 * @return the provincia
	 */
	public Provincia getProvincia() {
		return provincia;
	}

	/**
	 * @param provincia the provincia to set
	 */
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	/**
	 * @return the statoComunicazione
	 */
	public StatoComunicazione getStatoComunicazione() {
		return statoComunicazione;
	}

	/**
	 * @param statoComunicazione the statoComunicazione to set
	 */
	public void setStatoComunicazione(StatoComunicazione statoComunicazione) {
		this.statoComunicazione = statoComunicazione;
	}

	/**
	 * @return the tipoAcquisizione
	 */
	public TipoAcquisizione getTipoAcquisizione() {
		return tipoAcquisizione;
	}

	/**
	 * @param tipoAcquisizione the tipoAcquisizione to set
	 */
	public void setTipoAcquisizione(TipoAcquisizione tipoAcquisizione) {
		this.tipoAcquisizione = tipoAcquisizione;
	}

	/**
	 * @return the tipoComunicazione
	 */
	public TipoComunicazione getTipoComunicazione() {
		return tipoComunicazione;
	}

	/**
	 * @param tipoComunicazione the tipoComunicazione to set
	 */
	public void setTipoComunicazione(TipoComunicazione tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}

	/**
	 * @return the tipoComunicazioneTu
	 */
	public TipoComunicazioneTu getTipoComunicazioneTu() {
		return tipoComunicazioneTu;
	}

	/**
	 * @param tipoComunicazioneTu the tipoComunicazioneTu to set
	 */
	public void setTipoComunicazioneTu(TipoComunicazioneTu tipoComunicazioneTu) {
		this.tipoComunicazioneTu = tipoComunicazioneTu;
	}

	/**
	 * @return the tipoProvenienza
	 */
	public TipoProvenienza getTipoProvenienza() {
		return tipoProvenienza;
	}

	/**
	 * @param tipoProvenienza the tipoProvenienza to set
	 */
	public void setTipoProvenienza(TipoProvenienza tipoProvenienza) {
		this.tipoProvenienza = tipoProvenienza;
	}

	/**
	 * @return the tipoSoggettoAbilitato
	 */
	public TipoSoggettoAbilitato getTipoSoggettoAbilitato() {
		return tipoSoggettoAbilitato;
	}

	/**
	 * @param tipoSoggettoAbilitato the tipoSoggettoAbilitato to set
	 */
	public void setTipoSoggettoAbilitato(TipoSoggettoAbilitato tipoSoggettoAbilitato) {
		this.tipoSoggettoAbilitato = tipoSoggettoAbilitato;
	}

	/**
	 * @return the tipoSomministrazione
	 */
	public TipoSomministrazione getTipoSomministrazione() {
		return tipoSomministrazione;
	}

	/**
	 * @param tipoSomministrazione the tipoSomministrazione to set
	 */
	public void setTipoSomministrazione(TipoSomministrazione tipoSomministrazione) {
		this.tipoSomministrazione = tipoSomministrazione;
	}

	/**
	 * @return the tipoTracciato
	 */
	public TipoTracciato getTipoTracciato() {
		return tipoTracciato;
	}

	/**
	 * @param tipoTracciato the tipoTracciato to set
	 */
	public void setTipoTracciato(TipoTracciato tipoTracciato) {
		this.tipoTracciato = tipoTracciato;
	}

	/**
	 * @return the tipoTrasferimento
	 */
	public TipoTrasferimento getTipoTrasferimento() {
		return tipoTrasferimento;
	}

	/**
	 * @param tipoTrasferimento the tipoTrasferimento to set
	 */
	public void setTipoTrasferimento(TipoTrasferimento tipoTrasferimento) {
		this.tipoTrasferimento = tipoTrasferimento;
	}

	public Long getIdComTuPrecedenteAnnullo() {
		return idComTuPrecedenteAnnullo;
	}

	public void setIdComTuPrecedenteAnnullo(Long idComTuPrecedenteAnnullo) {
		this.idComTuPrecedenteAnnullo = idComTuPrecedenteAnnullo;
	}

	public Long getIdComTuDaRettificare() {
		return idComTuDaRettificare;
	}

	public void setIdComTuDaRettificare(Long idComTuDaRettificare) {
		this.idComTuDaRettificare = idComTuDaRettificare;
	}

	public Date getDataRiferimento() {
		return dataRiferimento;
	}

	public void setDataRiferimento(Date dataRiferimento) {
		this.dataRiferimento = dataRiferimento;
	}

	public Long getIdRapportoLavoro() {
		return idRapportoLavoro;
	}

	public void setIdRapportoLavoro(Long idRapportoLavoro) {
		this.idRapportoLavoro = idRapportoLavoro;
	}

	public String getFlgCurrentRecord() {
		return flgCurrentRecord;
	}

	public void setFlgCurrentRecord(String flgCurrentRecord) {
		this.flgCurrentRecord = flgCurrentRecord;
	}

	public Datore getDatoreAttuale() {
		return datoreAttuale;
	}

	public void setDatoreAttuale(Datore datoreAttuale) {
		this.datoreAttuale = datoreAttuale;
	}

	public Datore getDatorePrecedente() {
		return datorePrecedente;
	}

	public void setDatorePrecedente(Datore datorePrecedente) {
		this.datorePrecedente = datorePrecedente;
	}

	public List<Lavoratore> getLavoratori() {
		return lavoratori;
	}

	public void setLavoratori(List<Lavoratore> lavoratori) {
		this.lavoratori = lavoratori;
	}

	public Lavoratore getLavoratoreCoObbligato() {
		return lavoratoreCoObbligato;
	}

	public void setLavoratoreCoObbligato(Lavoratore lavoratoreCoObbligato) {
		this.lavoratoreCoObbligato = lavoratoreCoObbligato;
	}

	public Rapporto getRapporto() {
		return rapporto;
	}

	public void setRapporto(Rapporto rapporto) {
		this.rapporto = rapporto;
	}

	public Rapporto getMissione() {
		return missione;
	}

	public void setMissione(Rapporto missione) {
		this.missione = missione;
	}

	public List<RapportiLavoratoriSediInteressateVD> getRapLavSedeVD() {
		return rapLavSedeVD;
	}

	public void setRapLavSedeVD(List<RapportiLavoratoriSediInteressateVD> rapLavSedeVD) {
		this.rapLavSedeVD = rapLavSedeVD;
	}

	public Date getDtProtocolloPrec() {
		return dtProtocolloPrec;
	}

	public void setDtProtocolloPrec(Date dtProtocolloPrec) {
		this.dtProtocolloPrec = dtProtocolloPrec;
	}

	public Long getAnnoProtComPrec() {
		return annoProtComPrec;
	}

	public void setAnnoProtComPrec(Long annoProtComPrec) {
		this.annoProtComPrec = annoProtComPrec;
	}

	public Long getNumProtComPrec() {
		return numProtComPrec;
	}

	public void setNumProtComPrec(Long numProtComPrec) {
		this.numProtComPrec = numProtComPrec;
	}

	public Provincia getProvinciaPrec() {
		return provinciaPrec;
	}

	public void setProvinciaPrec(Provincia provinciaPrec) {
		this.provinciaPrec = provinciaPrec;
	}
}
