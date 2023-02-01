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
import java.util.Date;

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

import it.csi.comonl.comonlweb.ejb.entity.base.BaseAuditedEntity;

/**
 * The persistent class for the COM_D_COMUNICAZIONE database table.
 * 
 */
@Entity
@Table(name = "COM_D_COMUNICAZIONE")
@NamedQuery(name = "ComDComunicazione.findAll", query = "SELECT c FROM ComDComunicazione c")
@SequenceGenerator(name = "SEQUENCE_COMUNICAZIONE", sequenceName = "SEQ_ID_COM_D_COMUNICAZIONE", initialValue = 1, allocationSize = 1)
public class ComDComunicazione extends BaseAuditedEntity<Long> implements Serializable {

	@Override
	public Long getId() {
		return idComDComunicazione;
	}

	@Override
	public void setId(Long id) {
		idComDComunicazione = id;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_COM_D_COMUNICAZIONE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_COMUNICAZIONE")
	private Long idComDComunicazione;

	@Column(name = "ANNO_DATA_ORA_REGIONALE")
	private String annoDataOraRegionale;

	@Column(name = "ANNO_PROT_COM")
	private Long annoProtCom;

	@Column(name = "ANNO_PROT_MSG_POSTA")
	private Long annoProtMsgPosta;

	@Column(name = "ANNO_PROT_RETTIFICA")
	private Long annoProtRettifica;

	@Column(name = "ANNO_PROT_URGENZA")
	private Long annoProtUrgenza;



	@Column(name = "CF_IMPRESA")
	private String cfImpresa;

	@Column(name = "CF_LAV_C_ASSUNZIONE")
	private String cfLavCAssunzione;

	@Column(name = "CODICE_COMUN_REG_PREC")
	private String codiceComunRegPrec;

	@Column(name = "CODICE_COMUNICAZIONE_REG")
	private String codiceComunicazioneReg;

	@Column(name = "CODICE_ENTE")
	private String codiceEnte;

	@Column(name = "CODICE_FISCALE_SOGGETTO")
	private String codiceFiscaleSoggetto;

	@Column(name = "DS_CAUSA_FORZAMAGGIORE")
	private String dsCausaForzamaggiore;

	@Column(name = "DS_MOTIVO_URGENZA")
	private String dsMotivoUrgenza;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ASS_C_ASSUNZIONE")
	private Date dtAssCAssunzione;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FINE_AFFITTO_RAMO")
	private Date dtFineAffittoRamo;

//	@Temporal(TemporalType.DATE)
//	@Column(name="DT_INSERT")
//	private Date dtInsert;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INVIO")
	private Date dtInvio;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INVIO_MINISTERO")
	private Date dtInvioMinistero;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_PROTOCOLLO")
	private Date dtProtocollo;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_TIMBRO_POSTALE")
	private Date dtTimbroPostale;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_TRASFERIMENTO_VAR_DATORI")
	private Date dtTrasferimentoVarDatori;

//	@Temporal(TemporalType.DATE)
//	@Column(name="DT_ULT_MOD")
//	private Date dtUltMod;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_VERIFICA")
	private Date dtVerifica;

	/* campi aggiunti */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_RIFERIMENTO")
	private Date dataRiferimento;

	@Column(name = "ID_RAPPORTO_DI_LAVORO")
	private Long idRapportoLavoro;

	@Column(name = "FLG_CURRENT_RECORD")
	private String flgCurrentRecord;

	private String email;

	private String emailsoggetto;

	@Column(name = "FLG_CAUSA_FORZAMAGGIORE")
	private String flgCausaForzamaggiore;

	@Column(name = "FLG_COM_DAT_LAV")
	private String flgComDatLav;

	@Column(name = "FLG_COMUN_SEG_URGENZA")
	private String flgComunSegUrgenza;

	@Column(name = "FLG_INVIO_MINISTERO")
	private String flgInvioMinistero;

	@Column(name = "FLG_MULTI_LAV")
	private String flgMultiLav;

	@Column(name = "FLG_RETTIFICA")
	private String flgRettifica;

	@Column(name = "FLG_SOMMIN")
	private String flgSommin;

	@Column(name = "ID_COM_D_COMUNIC_PRECEDENTE")
	private Long idComDComunicPrecedente;

//	@Column(name="ID_USER_INSERT")
//	private String idUserInsert;
//
//	@Column(name="ID_USER_ULT_MOD")
//	private String idUserUltMod;

	@Column(name = "NUM_AGENZ_SOMMINISTR")
	private Long numAgenzSomministr;

	@Column(name = "NUM_PROT_COM")
	private Long numProtCom;

	@Column(name = "NUM_PROT_COM_RETTIFICA")
	private Long numProtComRettifica;

	@Column(name = "NUM_PROT_COM_URGENZA")
	private Long numProtComUrgenza;

	@Column(name = "NUM_PROT_MSG_POSTA")
	private Long numProtMsgPosta;

	private String verifica;

	// bi-directional many-to-one association to ComDComunicazione
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="ID_COM_D_COMUNICAZ_GRUPPO") private ComDComunicazione
	 * comDComunicazioneGruppo;
	 */
	@Column(name = "ID_COM_D_COMUNICAZ_GRUPPO")
	private Long idComDComunicazGruppo;

	// bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comDComunicazione1")
//	private List<ComDComunicazione> comDComunicaziones1;

	// bi-directional many-to-one association to ComDComunicazione
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="ID_COM_TU_PRECEDENTE_ANNULLO") private ComDComunicazione
	 * comDComunicazionePrecedenteAnnullo;
	 */

	@Column(name = "ID_COM_TU_PRECEDENTE_ANNULLO")
	private Long idComTuPrecedenteAnnullo;

	// bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comDComunicazione2")
//	private List<ComDComunicazione> comDComunicaziones2;

	// bi-directional many-to-one association to ComDComunicazione
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="ID_COM_TU_DA_RETTIFICARE") private ComDComunicazione
	 * comDComunicazioneDaRettificare;
	 */

	@Column(name = "ID_COM_TU_DA_RETTIFICARE")
	private Long idComTuDaRettificare;

	// bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comDComunicazione3")
//	private List<ComDComunicazione> comDComunicaziones3;

	// bi-directional many-to-one association to ComDDelegato
	@ManyToOne
	@JoinColumn(name = "ID_COM_D_DELEGATO")
	private ComDDelegato comDDelegato;

	// bi-directional many-to-one association to ComTCpi
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_CPI")
	private ComTCpi comTCpi;

	// bi-directional many-to-one association to ComTProvincia
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_PROVINCIA")
	private ComTProvincia comTProvincia;

	// bi-directional many-to-one association to ComTStatoComunicazione
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_STATO_COMUNICAZIONE")
	private ComTStatoComunicazione comTStatoComunicazione;

	// bi-directional many-to-one association to ComTTipoAcquisizione
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_TIPO_ACQUISIZIONE")
	private ComTTipoAcquisizione comTTipoAcquisizione;

	// bi-directional many-to-one association to ComTTipoComunicazione
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_TIPO_COMUNICAZIONE")
	private ComTTipoComunicazione comTTipoComunicazione;

	// bi-directional many-to-one association to ComTTipoComunicazioneTu
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_TIPO_COMUNICAZIONE_TU")
	private ComTTipoComunicazioneTu comTTipoComunicazioneTu;

	// bi-directional many-to-one association to ComTTipoProvenienza
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_TIPO_PROVENIENZA")
	private ComTTipoProvenienza comTTipoProvenienza;

	// bi-directional many-to-one association to ComTTipoSoggettoAbilitato
	@ManyToOne
	@JoinColumn(name = "ID_COM_TIPO_SOGGETTO_ABILITATO")
	private ComTTipoSoggettoAbilitato comTTipoSoggettoAbilitato;

	// bi-directional many-to-one association to ComTTipoSomministrazione
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_TIPO_SOMMINISTRAZIONE")
	private ComTTipoSomministrazione comTTipoSomministrazione;

	// bi-directional many-to-one association to ComTTipoTracciato
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_TIPO_TRACCIATO")
	private ComTTipoTracciato comTTipoTracciato;

	// bi-directional many-to-one association to ComTTipoTrasferimento
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_TIPO_TRASFERIMENTO")
	private ComTTipoTrasferimento comTTipoTrasferimento;

	// bi-directional many-to-many association to ComTTipoDestinatario
//	@ManyToMany(mappedBy="comDComunicaziones")
//	private List<ComTTipoDestinatario> comTTipoDestinatarios;

	// bi-directional one-to-one association to ComDDichiarazioniUd
//	@OneToOne(mappedBy="comDComunicazione")
//	private ComDDichiarazioniUd comDDichiarazioniUd;

	// bi-directional many-to-one association to ComDDuplicaGruppoLog
//	@OneToMany(mappedBy="comDComunicazione")
//	private List<ComDDuplicaGruppoLog> comDDuplicaGruppoLogs;

	// bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comDComunicazione")
//	private List<ComDRapporto> comDRapportos;

	// bi-directional many-to-one association to ComRComunicazioneDatore
//	@OneToMany(mappedBy="comDComunicazione")
//	private List<ComRComunicazioneDatore> comRComunicazioneDatores;

	// bi-directional many-to-one association to ComRDepCommaxComunic
//	@OneToMany(mappedBy="comDComunicazione")
//	private List<ComRDepCommaxComunic> comRDepCommaxComunics;

	// bi-directional one-to-one association to PdfComunicazione
//	@OneToOne(mappedBy="comDComunicazione")
//	private PdfComunicazione pdfComunicazione;

	public ComDComunicazione() {
	}

	public Long getIdComDComunicazione() {
		return this.idComDComunicazione;
	}

	public void setIdComDComunicazione(Long idComDComunicazione) {
		this.idComDComunicazione = idComDComunicazione;
	}

	public String getAnnoDataOraRegionale() {
		return this.annoDataOraRegionale;
	}

	public void setAnnoDataOraRegionale(String annoDataOraRegionale) {
		this.annoDataOraRegionale = annoDataOraRegionale;
	}

	public Long getAnnoProtCom() {
		return this.annoProtCom;
	}

	public void setAnnoProtCom(Long annoProtCom) {
		this.annoProtCom = annoProtCom;
	}

	public Long getAnnoProtMsgPosta() {
		return this.annoProtMsgPosta;
	}

	public void setAnnoProtMsgPosta(Long annoProtMsgPosta) {
		this.annoProtMsgPosta = annoProtMsgPosta;
	}

	public Long getAnnoProtRettifica() {
		return this.annoProtRettifica;
	}

	public void setAnnoProtRettifica(Long annoProtRettifica) {
		this.annoProtRettifica = annoProtRettifica;
	}

	public Long getAnnoProtUrgenza() {
		return this.annoProtUrgenza;
	}

	public void setAnnoProtUrgenza(Long annoProtUrgenza) {
		this.annoProtUrgenza = annoProtUrgenza;
	}

	public String getCfImpresa() {
		return this.cfImpresa;
	}

	public void setCfImpresa(String cfImpresa) {
		this.cfImpresa = cfImpresa;
	}

	public String getCfLavCAssunzione() {
		return this.cfLavCAssunzione;
	}

	public void setCfLavCAssunzione(String cfLavCAssunzione) {
		this.cfLavCAssunzione = cfLavCAssunzione;
	}

	public String getCodiceComunRegPrec() {
		return this.codiceComunRegPrec;
	}

	public void setCodiceComunRegPrec(String codiceComunRegPrec) {
		this.codiceComunRegPrec = codiceComunRegPrec;
	}

	public String getCodiceComunicazioneReg() {
		return this.codiceComunicazioneReg;
	}

	public void setCodiceComunicazioneReg(String codiceComunicazioneReg) {
		this.codiceComunicazioneReg = codiceComunicazioneReg;
	}

	public String getCodiceEnte() {
		return this.codiceEnte;
	}

	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}

	public String getCodiceFiscaleSoggetto() {
		return this.codiceFiscaleSoggetto;
	}

	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}

	public String getDsCausaForzamaggiore() {
		return this.dsCausaForzamaggiore;
	}

	public void setDsCausaForzamaggiore(String dsCausaForzamaggiore) {
		this.dsCausaForzamaggiore = dsCausaForzamaggiore;
	}

	public String getDsMotivoUrgenza() {
		return this.dsMotivoUrgenza;
	}

	public void setDsMotivoUrgenza(String dsMotivoUrgenza) {
		this.dsMotivoUrgenza = dsMotivoUrgenza;
	}

	public Date getDtAssCAssunzione() {
		return this.dtAssCAssunzione;
	}

	public void setDtAssCAssunzione(Date dtAssCAssunzione) {
		this.dtAssCAssunzione = dtAssCAssunzione;
	}

	public Date getDtFineAffittoRamo() {
		return this.dtFineAffittoRamo;
	}

	public void setDtFineAffittoRamo(Date dtFineAffittoRamo) {
		this.dtFineAffittoRamo = dtFineAffittoRamo;
	}

//	public Date getDtInsert() {
//		return this.dtInsert;
//	}
//
//	public void setDtInsert(Date dtInsert) {
//		this.dtInsert = dtInsert;
//	}

	public Date getDtInvio() {
		return this.dtInvio;
	}

	public void setDtInvio(Date dtInvio) {
		this.dtInvio = dtInvio;
	}

	public Date getDtInvioMinistero() {
		return this.dtInvioMinistero;
	}

	public void setDtInvioMinistero(Date dtInvioMinistero) {
		this.dtInvioMinistero = dtInvioMinistero;
	}

	public Date getDtProtocollo() {
		return this.dtProtocollo;
	}

	public void setDtProtocollo(Date dtProtocollo) {
		this.dtProtocollo = dtProtocollo;
	}

	public Date getDtTimbroPostale() {
		return this.dtTimbroPostale;
	}

	public void setDtTimbroPostale(Date dtTimbroPostale) {
		this.dtTimbroPostale = dtTimbroPostale;
	}

	public Date getDtTrasferimentoVarDatori() {
		return this.dtTrasferimentoVarDatori;
	}

	public void setDtTrasferimentoVarDatori(Date dtTrasferimentoVarDatori) {
		this.dtTrasferimentoVarDatori = dtTrasferimentoVarDatori;
	}

//	public Date getDtUltMod() {
//		return this.dtUltMod;
//	}
//
//	public void setDtUltMod(Date dtUltMod) {
//		this.dtUltMod = dtUltMod;
//	}

	public Date getDtVerifica() {
		return this.dtVerifica;
	}

	public void setDtVerifica(Date dtVerifica) {
		this.dtVerifica = dtVerifica;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailsoggetto() {
		return this.emailsoggetto;
	}

	public void setEmailsoggetto(String emailsoggetto) {
		this.emailsoggetto = emailsoggetto;
	}

	public String getFlgCausaForzamaggiore() {
		return this.flgCausaForzamaggiore;
	}

	public void setFlgCausaForzamaggiore(String flgCausaForzamaggiore) {
		this.flgCausaForzamaggiore = flgCausaForzamaggiore;
	}

	public String getFlgComDatLav() {
		return this.flgComDatLav;
	}

	public void setFlgComDatLav(String flgComDatLav) {
		this.flgComDatLav = flgComDatLav;
	}

	public String getFlgComunSegUrgenza() {
		return this.flgComunSegUrgenza;
	}

	public void setFlgComunSegUrgenza(String flgComunSegUrgenza) {
		this.flgComunSegUrgenza = flgComunSegUrgenza;
	}

	public String getFlgInvioMinistero() {
		return this.flgInvioMinistero;
	}

	public void setFlgInvioMinistero(String flgInvioMinistero) {
		this.flgInvioMinistero = flgInvioMinistero;
	}

	public String getFlgMultiLav() {
		return this.flgMultiLav;
	}

	public void setFlgMultiLav(String flgMultiLav) {
		this.flgMultiLav = flgMultiLav;
	}

	public String getFlgRettifica() {
		return this.flgRettifica;
	}

	public void setFlgRettifica(String flgRettifica) {
		this.flgRettifica = flgRettifica;
	}

	public String getFlgSommin() {
		return this.flgSommin;
	}

	public void setFlgSommin(String flgSommin) {
		this.flgSommin = flgSommin;
	}

	public Long getIdComDComunicPrecedente() {
		return this.idComDComunicPrecedente;
	}

	public void setIdComDComunicPrecedente(Long idComDComunicPrecedente) {
		this.idComDComunicPrecedente = idComDComunicPrecedente;
	}

//	public String getIdUserInsert() {
//		return this.idUserInsert;
//	}
//
//	public void setIdUserInsert(String idUserInsert) {
//		this.idUserInsert = idUserInsert;
//	}
//
//	public String getIdUserUltMod() {
//		return this.idUserUltMod;
//	}
//
//	public void setIdUserUltMod(String idUserUltMod) {
//		this.idUserUltMod = idUserUltMod;
//	}

	public Long getNumAgenzSomministr() {
		return this.numAgenzSomministr;
	}

	public void setNumAgenzSomministr(Long numAgenzSomministr) {
		this.numAgenzSomministr = numAgenzSomministr;
	}

	public Long getNumProtCom() {
		return this.numProtCom;
	}

	public void setNumProtCom(Long numProtCom) {
		this.numProtCom = numProtCom;
	}

	public Long getNumProtComRettifica() {
		return this.numProtComRettifica;
	}

	public void setNumProtComRettifica(Long numProtComRettifica) {
		this.numProtComRettifica = numProtComRettifica;
	}

	public Long getNumProtComUrgenza() {
		return this.numProtComUrgenza;
	}

	public void setNumProtComUrgenza(Long numProtComUrgenza) {
		this.numProtComUrgenza = numProtComUrgenza;
	}

	public Long getNumProtMsgPosta() {
		return this.numProtMsgPosta;
	}

	public void setNumProtMsgPosta(Long numProtMsgPosta) {
		this.numProtMsgPosta = numProtMsgPosta;
	}

	public String getVerifica() {
		return this.verifica;
	}

	public void setVerifica(String verifica) {
		this.verifica = verifica;
	}
	/*
	 * public ComDComunicazione getComDComunicazioneGruppo() { return
	 * this.comDComunicazioneGruppo; }
	 * 
	 * public void setComDComunicazioneGruppo(ComDComunicazione
	 * comDComunicazioneGruppo) { this.comDComunicazioneGruppo =
	 * comDComunicazioneGruppo; }
	 */
//	public List<ComDComunicazione> getComDComunicaziones1() {
//		return this.comDComunicaziones1;
//	}
//
//	public void setComDComunicaziones1(List<ComDComunicazione> comDComunicaziones1) {
//		this.comDComunicaziones1 = comDComunicaziones1;
//	}
//
//	public ComDComunicazione addComDComunicaziones1(ComDComunicazione comDComunicaziones1) {
//		getComDComunicaziones1().add(comDComunicaziones1);
//		comDComunicaziones1.setComDComunicazione1(this);
//
//		return comDComunicaziones1;
//	}
//
//	public ComDComunicazione removeComDComunicaziones1(ComDComunicazione comDComunicaziones1) {
//		getComDComunicaziones1().remove(comDComunicaziones1);
//		comDComunicaziones1.setComDComunicazione1(null);
//
//		return comDComunicaziones1;
//	}

	/*
	 * public ComDComunicazione getComDComunicazionePrecedenteAnnullo() { return
	 * this.comDComunicazionePrecedenteAnnullo; }
	 * 
	 * public void setComDComunicazionePrecedenteAnnullo(ComDComunicazione
	 * comDComunicazionePrecedenteAnnullo) { this.comDComunicazionePrecedenteAnnullo
	 * = comDComunicazionePrecedenteAnnullo; }
	 */

//	public List<ComDComunicazione> getComDComunicaziones2() {
//		return this.comDComunicaziones2;
//	}
//
//	public void setComDComunicaziones2(List<ComDComunicazione> comDComunicaziones2) {
//		this.comDComunicaziones2 = comDComunicaziones2;
//	}
//
//	public ComDComunicazione addComDComunicaziones2(ComDComunicazione comDComunicaziones2) {
//		getComDComunicaziones2().add(comDComunicaziones2);
//		comDComunicaziones2.setComDComunicazione2(this);
//
//		return comDComunicaziones2;
//	}
//
//	public ComDComunicazione removeComDComunicaziones2(ComDComunicazione comDComunicaziones2) {
//		getComDComunicaziones2().remove(comDComunicaziones2);
//		comDComunicaziones2.setComDComunicazione2(null);
//
//		return comDComunicaziones2;
//	}

	/*
	 * public ComDComunicazione getComDComunicazioneDaRettificare() { return
	 * this.comDComunicazioneDaRettificare; }
	 * 
	 * public void setComDComunicazioneDaRettificare(ComDComunicazione
	 * comDComunicazioneDaRettificare) { this.comDComunicazioneDaRettificare =
	 * comDComunicazioneDaRettificare; }
	 */

//	public List<ComDComunicazione> getComDComunicaziones3() {
//		return this.comDComunicaziones3;
//	}
//
//	public void setComDComunicaziones3(List<ComDComunicazione> comDComunicaziones3) {
//		this.comDComunicaziones3 = comDComunicaziones3;
//	}
//
//	public ComDComunicazione addComDComunicaziones3(ComDComunicazione comDComunicaziones3) {
//		getComDComunicaziones3().add(comDComunicaziones3);
//		comDComunicaziones3.setComDComunicazione3(this);
//
//		return comDComunicaziones3;
//	}
//
//	public ComDComunicazione removeComDComunicaziones3(ComDComunicazione comDComunicaziones3) {
//		getComDComunicaziones3().remove(comDComunicaziones3);
//		comDComunicaziones3.setComDComunicazione3(null);
//
//		return comDComunicaziones3;
//	}

	public ComDDelegato getComDDelegato() {
		return this.comDDelegato;
	}

	public void setComDDelegato(ComDDelegato comDDelegato) {
		this.comDDelegato = comDDelegato;
	}

	public ComTCpi getComTCpi() {
		return this.comTCpi;
	}

	public void setComTCpi(ComTCpi comTCpi) {
		this.comTCpi = comTCpi;
	}

	public ComTProvincia getComTProvincia() {
		return this.comTProvincia;
	}

	public void setComTProvincia(ComTProvincia comTProvincia) {
		this.comTProvincia = comTProvincia;
	}

	public ComTStatoComunicazione getComTStatoComunicazione() {
		return this.comTStatoComunicazione;
	}

	public void setComTStatoComunicazione(ComTStatoComunicazione comTStatoComunicazione) {
		this.comTStatoComunicazione = comTStatoComunicazione;
	}

	public ComTTipoAcquisizione getComTTipoAcquisizione() {
		return this.comTTipoAcquisizione;
	}

	public void setComTTipoAcquisizione(ComTTipoAcquisizione comTTipoAcquisizione) {
		this.comTTipoAcquisizione = comTTipoAcquisizione;
	}

	public ComTTipoComunicazione getComTTipoComunicazione() {
		return this.comTTipoComunicazione;
	}

	public void setComTTipoComunicazione(ComTTipoComunicazione comTTipoComunicazione) {
		this.comTTipoComunicazione = comTTipoComunicazione;
	}

	public ComTTipoComunicazioneTu getComTTipoComunicazioneTu() {
		return this.comTTipoComunicazioneTu;
	}

	public void setComTTipoComunicazioneTu(ComTTipoComunicazioneTu comTTipoComunicazioneTu) {
		this.comTTipoComunicazioneTu = comTTipoComunicazioneTu;
	}

	public ComTTipoProvenienza getComTTipoProvenienza() {
		return this.comTTipoProvenienza;
	}

	public void setComTTipoProvenienza(ComTTipoProvenienza comTTipoProvenienza) {
		this.comTTipoProvenienza = comTTipoProvenienza;
	}

	public ComTTipoSoggettoAbilitato getComTTipoSoggettoAbilitato() {
		return this.comTTipoSoggettoAbilitato;
	}

	public void setComTTipoSoggettoAbilitato(ComTTipoSoggettoAbilitato comTTipoSoggettoAbilitato) {
		this.comTTipoSoggettoAbilitato = comTTipoSoggettoAbilitato;
	}

	public ComTTipoSomministrazione getComTTipoSomministrazione() {
		return this.comTTipoSomministrazione;
	}

	public void setComTTipoSomministrazione(ComTTipoSomministrazione comTTipoSomministrazione) {
		this.comTTipoSomministrazione = comTTipoSomministrazione;
	}

	public ComTTipoTracciato getComTTipoTracciato() {
		return this.comTTipoTracciato;
	}

	public void setComTTipoTracciato(ComTTipoTracciato comTTipoTracciato) {
		this.comTTipoTracciato = comTTipoTracciato;
	}

	public ComTTipoTrasferimento getComTTipoTrasferimento() {
		return this.comTTipoTrasferimento;
	}

	public void setComTTipoTrasferimento(ComTTipoTrasferimento comTTipoTrasferimento) {
		this.comTTipoTrasferimento = comTTipoTrasferimento;
	}

	public Long getIdComDComunicazGruppo() {
		return idComDComunicazGruppo;
	}

	public void setIdComDComunicazGruppo(Long idComDComunicazGruppo) {
		this.idComDComunicazGruppo = idComDComunicazGruppo;
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

//	public List<ComTTipoDestinatario> getComTTipoDestinatarios() {
//		return this.comTTipoDestinatarios;
//	}
//
//	public void setComTTipoDestinatarios(List<ComTTipoDestinatario> comTTipoDestinatarios) {
//		this.comTTipoDestinatarios = comTTipoDestinatarios;
//	}

//	public ComDDichiarazioniUd getComDDichiarazioniUd() {
//		return this.comDDichiarazioniUd;
//	}
//
//	public void setComDDichiarazioniUd(ComDDichiarazioniUd comDDichiarazioniUd) {
//		this.comDDichiarazioniUd = comDDichiarazioniUd;
//	}
//
//	public List<ComDDuplicaGruppoLog> getComDDuplicaGruppoLogs() {
//		return this.comDDuplicaGruppoLogs;
//	}
//
//	public void setComDDuplicaGruppoLogs(List<ComDDuplicaGruppoLog> comDDuplicaGruppoLogs) {
//		this.comDDuplicaGruppoLogs = comDDuplicaGruppoLogs;
//	}

//	public ComDDuplicaGruppoLog addComDDuplicaGruppoLog(ComDDuplicaGruppoLog comDDuplicaGruppoLog) {
//		getComDDuplicaGruppoLogs().add(comDDuplicaGruppoLog);
//		comDDuplicaGruppoLog.setComDComunicazione(this);
//
//		return comDDuplicaGruppoLog;
//	}
//
//	public ComDDuplicaGruppoLog removeComDDuplicaGruppoLog(ComDDuplicaGruppoLog comDDuplicaGruppoLog) {
//		getComDDuplicaGruppoLogs().remove(comDDuplicaGruppoLog);
//		comDDuplicaGruppoLog.setComDComunicazione(null);
//
//		return comDDuplicaGruppoLog;
//	}

//	public List<ComDRapporto> getComDRapportos() {
//		return this.comDRapportos;
//	}
//
//	public void setComDRapportos(List<ComDRapporto> comDRapportos) {
//		this.comDRapportos = comDRapportos;
//	}
//
//	public ComDRapporto addComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().add(comDRapporto);
//		comDRapporto.setComDComunicazione(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComDComunicazione(null);
//
//		return comDRapporto;
//	}

//	public List<ComRComunicazioneDatore> getComRComunicazioneDatores() {
//		return this.comRComunicazioneDatores;
//	}
//
//	public void setComRComunicazioneDatores(List<ComRComunicazioneDatore> comRComunicazioneDatores) {
//		this.comRComunicazioneDatores = comRComunicazioneDatores;
//	}
//
//	public ComRComunicazioneDatore addComRComunicazioneDatore(ComRComunicazioneDatore comRComunicazioneDatore) {
//		getComRComunicazioneDatores().add(comRComunicazioneDatore);
//		comRComunicazioneDatore.setComDComunicazione(this);
//
//		return comRComunicazioneDatore;
//	}
//
//	public ComRComunicazioneDatore removeComRComunicazioneDatore(ComRComunicazioneDatore comRComunicazioneDatore) {
//		getComRComunicazioneDatores().remove(comRComunicazioneDatore);
//		comRComunicazioneDatore.setComDComunicazione(null);
//
//		return comRComunicazioneDatore;
//	}

//	public List<ComRDepCommaxComunic> getComRDepCommaxComunics() {
//		return this.comRDepCommaxComunics;
//	}
//
//	public void setComRDepCommaxComunics(List<ComRDepCommaxComunic> comRDepCommaxComunics) {
//		this.comRDepCommaxComunics = comRDepCommaxComunics;
//	}
//
//	public ComRDepCommaxComunic addComRDepCommaxComunic(ComRDepCommaxComunic comRDepCommaxComunic) {
//		getComRDepCommaxComunics().add(comRDepCommaxComunic);
//		comRDepCommaxComunic.setComDComunicazione(this);
//
//		return comRDepCommaxComunic;
//	}
//
//	public ComRDepCommaxComunic removeComRDepCommaxComunic(ComRDepCommaxComunic comRDepCommaxComunic) {
//		getComRDepCommaxComunics().remove(comRDepCommaxComunic);
//		comRDepCommaxComunic.setComDComunicazione(null);
//
//		return comRDepCommaxComunic;
//	}

//	public PdfComunicazione getPdfComunicazione() {
//		return this.pdfComunicazione;
//	}
//
//	public void setPdfComunicazione(PdfComunicazione pdfComunicazione) {
//		this.pdfComunicazione = pdfComunicazione;
//	}
	@Override
	public String toString() {
		return "ComDComunicazione [idComDComunicazione=" + idComDComunicazione + ", annoDataOraRegionale="
				+ annoDataOraRegionale + ", annoProtCom=" + annoProtCom + ", annoProtMsgPosta=" + annoProtMsgPosta
				+ ", annoProtRettifica=" + annoProtRettifica + ", annoProtUrgenza=" + annoProtUrgenza + ", cfImpresa="
				+ cfImpresa + ", cfLavCAssunzione=" + cfLavCAssunzione + ", codiceComunRegPrec=" + codiceComunRegPrec
				+ ", codiceComunicazioneReg=" + codiceComunicazioneReg + ", codiceEnte=" + codiceEnte
				+ ", codiceFiscaleSoggetto=" + codiceFiscaleSoggetto + ", dsCausaForzamaggiore=" + dsCausaForzamaggiore
				+ ", dsMotivoUrgenza=" + dsMotivoUrgenza + ", dtAssCAssunzione=" + dtAssCAssunzione
				+ ", dtFineAffittoRamo=" + dtFineAffittoRamo + ", dtInvio=" + dtInvio + ", dtInvioMinistero="
				+ dtInvioMinistero + ", dtProtocollo=" + dtProtocollo + ", dtTimbroPostale=" + dtTimbroPostale
				+ ", dtTrasferimentoVarDatori=" + dtTrasferimentoVarDatori + ", dtVerifica=" + dtVerifica
				+ ", dataRiferimento=" + dataRiferimento + ", idRapportoLavoro=" + idRapportoLavoro
				+ ", flgCurrentRecord=" + flgCurrentRecord + ", email=" + email + ", emailsoggetto=" + emailsoggetto
				+ ", flgCausaForzamaggiore=" + flgCausaForzamaggiore + ", flgComDatLav=" + flgComDatLav
				+ ", flgComunSegUrgenza=" + flgComunSegUrgenza + ", flgInvioMinistero=" + flgInvioMinistero
				+ ", flgMultiLav=" + flgMultiLav + ", flgRettifica=" + flgRettifica + ", flgSommin=" + flgSommin
				+ ", idComDComunicPrecedente=" + idComDComunicPrecedente + ", numAgenzSomministr=" + numAgenzSomministr
				+ ", numProtCom=" + numProtCom + ", numProtComRettifica=" + numProtComRettifica + ", numProtComUrgenza="
				+ numProtComUrgenza + ", numProtMsgPosta=" + numProtMsgPosta + ", verifica=" + verifica
				+ ", idComDComunicazGruppo=" + idComDComunicazGruppo + ", idComTuPrecedenteAnnullo="
				+ idComTuPrecedenteAnnullo + ", idComTuDaRettificare=" + idComTuDaRettificare + ", comDDelegato="
				+ comDDelegato + ", comTCpi=" + comTCpi + ", comTProvincia=" + comTProvincia
				+ ", comTStatoComunicazione=" + comTStatoComunicazione + ", comTTipoAcquisizione="
				+ comTTipoAcquisizione + ", comTTipoComunicazione=" + comTTipoComunicazione
				+ ", comTTipoComunicazioneTu=" + comTTipoComunicazioneTu + ", comTTipoProvenienza="
				+ comTTipoProvenienza + ", comTTipoSoggettoAbilitato=" + comTTipoSoggettoAbilitato
				+ ", comTTipoSomministrazione=" + comTTipoSomministrazione + ", comTTipoTracciato=" + comTTipoTracciato
				+ ", comTTipoTrasferimento=" + comTTipoTrasferimento + "]";
	}
}
