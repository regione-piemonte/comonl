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

import java.util.Date;


public class RicercaComunicazione {
	
	private Long annoProtCom;

	private String codiceComunicazioneReg;

	private String codiceFiscaleDatore;

	private String codiceFiscaleLavoratore;

	private String codiceFiscaleSoggetto;

	private String cognomeLavoratore;

	private String dsComTStatoComunicazione;

	private String dsComTTipoComunicazione;

	private String dsDenominazioneDatore;

	private Date dtEvento;

	private Date dtInizioRapporto;

	private Date dtInsert;

	private Date dtInvio;
	
	private Long idComDComunicazione;

	private Long idComDDatore;

	private Long idComDLavoratore;

	private Long idComTProvincia;

	private Long idComTStatoComunicazione;

	private String idComTTipoComunicazione;

	private Long idComTTipoComunicazioneTu;

	private String nomeLavoratore;

	private Long numProtCom;
	
	private String idTipoTracciato;

	//private Long idComDLavoratorePrec;
	
	private String dsDenominazioneDatorePrec;
	
	private String codiceFiscaleDatorePrec;
	
	private Long idComVariazioneSomm;
	
	private Long idComTTipoSomministrazione;
	
	private Long idComTTipoTrasferimento;
	
	private String flgCurrentRecord;
	
	public Long getAnnoProtCom() {
		return this.annoProtCom;
	}

	public void setAnnoProtCom(Long annoProtCom) {
		this.annoProtCom = annoProtCom;
	}

	public String getCodiceComunicazioneReg() {
		return this.codiceComunicazioneReg;
	}

	public void setCodiceComunicazioneReg(String codiceComunicazioneReg) {
		this.codiceComunicazioneReg = codiceComunicazioneReg;
	}

	public String getCodiceFiscaleDatore() {
		return this.codiceFiscaleDatore;
	}

	public void setCodiceFiscaleDatore(String codiceFiscaleDatore) {
		this.codiceFiscaleDatore = codiceFiscaleDatore;
	}

	public String getCodiceFiscaleLavoratore() {
		return this.codiceFiscaleLavoratore;
	}

	public void setCodiceFiscaleLavoratore(String codiceFiscaleLavoratore) {
		this.codiceFiscaleLavoratore = codiceFiscaleLavoratore;
	}

	public String getCodiceFiscaleSoggetto() {
		return this.codiceFiscaleSoggetto;
	}

	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}

	public String getCognomeLavoratore() {
		return this.cognomeLavoratore;
	}

	public void setCognomeLavoratore(String cognomeLavoratore) {
		this.cognomeLavoratore = cognomeLavoratore;
	}

	public String getDsComTStatoComunicazione() {
		return this.dsComTStatoComunicazione;
	}

	public void setDsComTStatoComunicazione(String dsComTStatoComunicazione) {
		this.dsComTStatoComunicazione = dsComTStatoComunicazione;
	}

	public String getDsComTTipoComunicazione() {
		return this.dsComTTipoComunicazione;
	}

	public void setDsComTTipoComunicazione(String dsComTTipoComunicazione) {
		this.dsComTTipoComunicazione = dsComTTipoComunicazione;
	}

	public String getDsDenominazioneDatore() {
		return this.dsDenominazioneDatore;
	}

	public void setDsDenominazioneDatore(String dsDenominazioneDatore) {
		this.dsDenominazioneDatore = dsDenominazioneDatore;
	}

	public Date getDtEvento() {
		return this.dtEvento;
	}

	public void setDtEvento(Date dtEvento) {
		this.dtEvento = dtEvento;
	}

	public Date getDtInizioRapporto() {
		return this.dtInizioRapporto;
	}

	public void setDtInizioRapporto(Date dtInizioRapporto) {
		this.dtInizioRapporto = dtInizioRapporto;
	}

	public Date getDtInsert() {
		return this.dtInsert;
	}

	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	public Date getDtInvio() {
		return this.dtInvio;
	}

	public void setDtInvio(Date dtInvio) {
		this.dtInvio = dtInvio;
	}

	public Long getIdComDComunicazione() {
		return this.idComDComunicazione;
	}

	public void setIdComDComunicazione(Long idComDComunicazione) {
		this.idComDComunicazione = idComDComunicazione;
	}

	public Long getIdComDDatore() {
		return this.idComDDatore;
	}

	public void setIdComDDatore(Long idComDDatore) {
		this.idComDDatore = idComDDatore;
	}

	public Long getIdComDLavoratore() {
		return this.idComDLavoratore;
	}

	public void setIdComDLavoratore(Long idComDLavoratore) {
		this.idComDLavoratore = idComDLavoratore;
	}

	public Long getIdComTProvincia() {
		return this.idComTProvincia;
	}

	public void setIdComTProvincia(Long idComTProvincia) {
		this.idComTProvincia = idComTProvincia;
	}

	public Long getIdComTStatoComunicazione() {
		return this.idComTStatoComunicazione;
	}

	public void setIdComTStatoComunicazione(Long idComTStatoComunicazione) {
		this.idComTStatoComunicazione = idComTStatoComunicazione;
	}

	public String getIdComTTipoComunicazione() {
		return this.idComTTipoComunicazione;
	}

	public void setIdComTTipoComunicazione(String idComTTipoComunicazione) {
		this.idComTTipoComunicazione = idComTTipoComunicazione;
	}

	public Long getIdComTTipoComunicazioneTu() {
		return this.idComTTipoComunicazioneTu;
	}

	public void setIdComTTipoComunicazioneTu(Long idComTTipoComunicazioneTu) {
		this.idComTTipoComunicazioneTu = idComTTipoComunicazioneTu;
	}

	public String getNomeLavoratore() {
		return this.nomeLavoratore;
	}

	public void setNomeLavoratore(String nomeLavoratore) {
		this.nomeLavoratore = nomeLavoratore;
	}

	public Long getNumProtCom() {
		return this.numProtCom;
	}

	public void setNumProtCom(Long numProtCom) {
		this.numProtCom = numProtCom;
	}

	public String getIdTipoTracciato() {
		return idTipoTracciato;
	}

	public void setIdTipoTracciato(String idTipoTracciato) {
		this.idTipoTracciato = idTipoTracciato;
	}
	
	/*
	public Long getIdComDLavoratorePrec() {
		return idComDLavoratorePrec;
	}

	public void setIdComDLavoratorePrec(Long idComDLavoratorePrec) {
		this.idComDLavoratorePrec = idComDLavoratorePrec;
	}
	*/
	public String getDsDenominazioneDatorePrec() {
		return dsDenominazioneDatorePrec;
	}

	public void setDsDenominazioneDatorePrec(String dsDenominazioneDatorePrec) {
		this.dsDenominazioneDatorePrec = dsDenominazioneDatorePrec;
	}

	public String getCodiceFiscaleDatorePrec() {
		return codiceFiscaleDatorePrec;
	}

	public void setCodiceFiscaleDatorePrec(String codiceFiscaleDatorePrec) {
		this.codiceFiscaleDatorePrec = codiceFiscaleDatorePrec;
	}

	public Long getIdComVariazioneSomm() {
		return idComVariazioneSomm;
	}

	public void setIdComVariazioneSomm(Long idComVariazioneSomm) {
		this.idComVariazioneSomm = idComVariazioneSomm;
	}

	public Long getIdComTTipoSomministrazione() {
		return idComTTipoSomministrazione;
	}

	public void setIdComTTipoSomministrazione(Long idComTTipoSomministrazione) {
		this.idComTTipoSomministrazione = idComTTipoSomministrazione;
	}

	public Long getIdComTTipoTrasferimento() {
		return idComTTipoTrasferimento;
	}

	public void setIdComTTipoTrasferimento(Long idComTTipoTrasferimento) {
		this.idComTTipoTrasferimento = idComTTipoTrasferimento;
	}

	public String getFlgCurrentRecord() {
		return flgCurrentRecord;
	}

	public void setFlgCurrentRecord(String flgCurrentRecord) {
		this.flgCurrentRecord = flgCurrentRecord;
	}
	
	
	
}
