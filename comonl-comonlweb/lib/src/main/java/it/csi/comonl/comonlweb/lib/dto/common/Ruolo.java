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
package it.csi.comonl.comonlweb.lib.dto.common;

import java.io.Serializable;
import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSoggettoAbilitato;

/**
 * The Class Ruolo.
 */
public class Ruolo extends BaseDto<Integer> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String denominazioneAzienda;
	private String ilRuolo;
	private String codiceFiscaleUtente;
	private String codiceFiscaleAzienda;
	private String dsNome;
	private String dsCognome;

	private List<String> listaCasiUso;
	private boolean isLegaleRappresentante;
	private boolean isDelegato;
	private boolean isPersonaAutorizzata;
	private boolean isConsulente;
	private boolean isOperatoreAPL;
	private boolean isAmministratore;
	private boolean isMonitoraggio;

	/*
	 * Ulteriori dati che servono al servizio esposto per il recupero dell'elenco
	 * dei ruoli
	 */
	private String codMinSoggettoAbilitato;
	private Long idSoggetti;
	private boolean flgScuolaPubblica;
	private String codiceFiscaleStudioProfessionale;
	private String descrizioneStudioProfessionale;
	private boolean flgConsulenteAccentrato;
	private boolean delegaValida;
	private String tipoAnagrafica;

	private String email;
	private String codiceEnteOperatoreAPL;

	private TipoSoggettoAbilitato tipoSoggettoAbilitato;

	public String getDenominazioneAzienda() {
		return denominazioneAzienda;
	}

	public void setDenominazioneAzienda(String denominazioneAzienda) {
		this.denominazioneAzienda = denominazioneAzienda;
	}

	public List<String> getListaCasiUso() {
		return listaCasiUso;
	}

	public void setListaCasiUso(List<String> listaCasiUso) {
		this.listaCasiUso = listaCasiUso;
	}

	public boolean isLegaleRappresentante() {
		return isLegaleRappresentante;
	}

	public void setLegaleRappresentante(boolean isLegaleRappresentante) {
		this.isLegaleRappresentante = isLegaleRappresentante;
	}

	public boolean isConsulenteRespo() {
		return isConsulente;
	}

	public void setConsulenteRespo(boolean isConsulenteRespo) {
		this.isConsulente = isConsulenteRespo;
	}

	public boolean isDelegatoRespo() {
		return isDelegato;
	}

	public void setDelegatoRespo(boolean isDelegatoRespo) {
		this.isDelegato = isDelegatoRespo;
	}

	public boolean isPersonaAutorizzata() {
		return isPersonaAutorizzata;
	}

	public void setPersonaAutorizzata(boolean isPersonaAutorizzata) {
		this.isPersonaAutorizzata = isPersonaAutorizzata;
	}

	public boolean isOperatoreProvinciale() {
		return isOperatoreAPL;
	}

	public void setOperatoreProvinciale(boolean isOperatoreProvinciale) {
		this.isOperatoreAPL = isOperatoreProvinciale;
	}

	public boolean isAmministratore() {
		return isAmministratore;
	}

	public void setAmministratore(boolean isAmministratore) {
		this.isAmministratore = isAmministratore;
	}

	public boolean isMonitoraggio() {
		return isMonitoraggio;
	}

	public void setMonitoraggio(boolean isMonitoraggio) {
		this.isMonitoraggio = isMonitoraggio;
	}

	public String getCodMinSoggettoAbilitato() {
		return codMinSoggettoAbilitato;
	}

	public void setCodMinSoggettoAbilitato(String codMinSoggettoAbilitato) {
		this.codMinSoggettoAbilitato = codMinSoggettoAbilitato;
	}

	public Long getIdSoggetti() {
		return idSoggetti;
	}

	public void setIdSoggetti(Long idSoggetti) {
		this.idSoggetti = idSoggetti;
	}

	public String getCodiceFiscaleAzienda() {
		return codiceFiscaleAzienda;
	}

	public void setCodiceFiscaleAzienda(String codiceFiscaleAzienda) {
		this.codiceFiscaleAzienda = codiceFiscaleAzienda;
	}

	public String getDsNome() {
		return dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsCognome() {
		return dsCognome;
	}

	public void setDsCognome(String dsCognome) {
		this.dsCognome = dsCognome;
	}

	public String getIlRuolo() {
		return ilRuolo;
	}

	public void setIlRuolo(String ilRuolo) {
		this.ilRuolo = ilRuolo;
	}

	public String getCodiceFiscaleUtente() {
		return codiceFiscaleUtente;
	}

	public void setCodiceFiscaleUtente(String codiceFiscaleUtente) {
		this.codiceFiscaleUtente = codiceFiscaleUtente;
	}

	public boolean isFlgScuolaPubblica() {
		return flgScuolaPubblica;
	}

	public void setFlgScuolaPubblica(boolean flgScuolaPubblica) {
		this.flgScuolaPubblica = flgScuolaPubblica;
	}

	public String getCodiceFiscaleStudioProfessionale() {
		return codiceFiscaleStudioProfessionale;
	}

	public void setCodiceFiscaleStudioProfessionale(String codiceFiscaleStudioProfessionale) {
		this.codiceFiscaleStudioProfessionale = codiceFiscaleStudioProfessionale;
	}

	public String getDescrizioneStudioProfessionale() {
		return descrizioneStudioProfessionale;
	}

	public void setDescrizioneStudioProfessionale(String descrizioneStudioProfessionale) {
		this.descrizioneStudioProfessionale = descrizioneStudioProfessionale;
	}

	public boolean isFlgConsulenteAccentrato() {
		return flgConsulenteAccentrato;
	}

	public void setFlgConsulenteAccentrato(boolean flgConsulenteAccentrato) {
		this.flgConsulenteAccentrato = flgConsulenteAccentrato;
	}

	public boolean isDelegaValida() {
		return delegaValida;
	}

	public void setDelegaValida(boolean delegaValida) {
		this.delegaValida = delegaValida;
	}

	public String getTipoAnagrafica() {
		return tipoAnagrafica;
	}

	public void setTipoAnagrafica(String tipoAnagrafica) {
		this.tipoAnagrafica = tipoAnagrafica;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodiceEnteOperatoreAPL() {
		return codiceEnteOperatoreAPL;
	}

	public void setCodiceEnteOperatoreAPL(String codiceEnteOperatoreAPL) {
		this.codiceEnteOperatoreAPL = codiceEnteOperatoreAPL;
	}

	public TipoSoggettoAbilitato getTipoSoggettoAbilitato() {
		return tipoSoggettoAbilitato;
	}

	public void setTipoSoggettoAbilitato(TipoSoggettoAbilitato tipoSoggettoAbilitato) {
		this.tipoSoggettoAbilitato = tipoSoggettoAbilitato;
	}

	@Override
	public String toString() {
		return "Ruolo [denominazioneAzienda=" + denominazioneAzienda + ", ilRuolo=" + ilRuolo + ", codiceFiscaleUtente="
				+ codiceFiscaleUtente + ", codiceFiscaleAzienda=" + codiceFiscaleAzienda + ", dsNome=" + dsNome
				+ ", dsCognome=" + dsCognome + ", listaCasiUso=" + listaCasiUso + ", isLegaleRappresentante="
				+ isLegaleRappresentante + ", isDelegato=" + isDelegato + ", isPersonaAutorizzata="
				+ isPersonaAutorizzata + ", isConsulente=" + isConsulente + ", isOperatoreAPL=" + isOperatoreAPL
				+ ", isAmministratore=" + isAmministratore + ", isMonitoraggio=" + isMonitoraggio
				+ ", codMinSoggettoAbilitato=" + codMinSoggettoAbilitato + ", idSoggetti=" + idSoggetti
				+ ", flgScuolaPubblica=" + flgScuolaPubblica + ", codiceFiscaleStudioProfessionale="
				+ codiceFiscaleStudioProfessionale + ", descrizioneStudioProfessionale="
				+ descrizioneStudioProfessionale + ", flgConsulenteAccentrato=" + flgConsulenteAccentrato
				+ ", delegaValida=" + delegaValida + ", tipoAnagrafica=" + tipoAnagrafica + ", email=" + email
				+ ", codiceEnteOperatoreAPL=" + codiceEnteOperatoreAPL + ", tipoSoggettoAbilitato="
				+ tipoSoggettoAbilitato + "]";
	}
	
	

}
