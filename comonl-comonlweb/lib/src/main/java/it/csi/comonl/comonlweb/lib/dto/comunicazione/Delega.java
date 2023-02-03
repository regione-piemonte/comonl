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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;

/**
 * The Class Delega.
 */
public class Delega extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private BigDecimal annoProtocollo;
	private String cEnte;
	private String capDelegante;
	private String capDelegato;
	private String carica;
	private String cfDelegante;
	private String cfDelegato;
	private String cfImpresa;
	private String cognomeDelegante;
	private String cognomeDelegato;
	private String comuneDelegante;
	private String comuneDelegato;
	private String denominazioneImpresa;
	private Date dtInsert;
	private Date dtProtocollo;
	private Date dtRevoca;
	private Date dtUltMod;
	private Date dtVerifica;
	private String faxDelegato;
	private String idUserInsert;
	private String idUserUltMod;
	private String indirizzoDelegante;
	private String indirizzoDelegato;
	private String mailDelegante;
	private String mailDelegato;
	private Long numeroProtocollo;
	private String nomeDelegante;
	private String nomeDelegato;
	private String pivaDelegato;
	private String pivaImpresa;
	private String pvDelegante;
	private String pvDelegato;
	private String telefonoDelegante;
	private String telefonoDelegato;
	private String verifica;
	private Comune comDelegato;
	private Comune comDelegante;
	private StatiEsteri statiEsteriDelegato;
	private StatiEsteri statiEsteriDelegante;
	private Personalizzazione personalizzazione;
	private StatoDelega statoDelega;
	private TipoDelega tipoDelega;

	/**
	 * @return the annoProtocollo
	 */
	public BigDecimal getAnnoProtocollo() {
		return annoProtocollo;
	}
	
	/**
	 * @param annoProtocollo the annoProtocollo to set
	 */
	public void setAnnoProtocollo(BigDecimal annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}

	/**
	 * @return the cEnte
	 */
	public String getCEnte() {
		return cEnte;
	}
	
	/**
	 * @param cEnte the cEnte to set
	 */
	public void setCEnte(String cEnte) {
		this.cEnte = cEnte;
	}

	/**
	 * @return the capDelegante
	 */
	public String getCapDelegante() {
		return capDelegante;
	}
	
	/**
	 * @param capDelegante the capDelegante to set
	 */
	public void setCapDelegante(String capDelegante) {
		this.capDelegante = capDelegante;
	}

	/**
	 * @return the capDelegato
	 */
	public String getCapDelegato() {
		return capDelegato;
	}
	
	/**
	 * @param capDelegato the capDelegato to set
	 */
	public void setCapDelegato(String capDelegato) {
		this.capDelegato = capDelegato;
	}

	/**
	 * @return the carica
	 */
	public String getCarica() {
		return carica;
	}
	
	/**
	 * @param carica the carica to set
	 */
	public void setCarica(String carica) {
		this.carica = carica;
	}

	/**
	 * @return the cfDelegante
	 */
	public String getCfDelegante() {
		return cfDelegante;
	}
	
	/**
	 * @param cfDelegante the cfDelegante to set
	 */
	public void setCfDelegante(String cfDelegante) {
		this.cfDelegante = cfDelegante;
	}

	/**
	 * @return the cfDelegato
	 */
	public String getCfDelegato() {
		return cfDelegato;
	}
	
	/**
	 * @param cfDelegato the cfDelegato to set
	 */
	public void setCfDelegato(String cfDelegato) {
		this.cfDelegato = cfDelegato;
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
	 * @return the cognomeDelegante
	 */
	public String getCognomeDelegante() {
		return cognomeDelegante;
	}
	
	/**
	 * @param cognomeDelegante the cognomeDelegante to set
	 */
	public void setCognomeDelegante(String cognomeDelegante) {
		this.cognomeDelegante = cognomeDelegante;
	}

	/**
	 * @return the cognomeDelegato
	 */
	public String getCognomeDelegato() {
		return cognomeDelegato;
	}
	
	/**
	 * @param cognomeDelegato the cognomeDelegato to set
	 */
	public void setCognomeDelegato(String cognomeDelegato) {
		this.cognomeDelegato = cognomeDelegato;
	}

	/**
	 * @return the comuneDelegante
	 */
	public String getComuneDelegante() {
		return comuneDelegante;
	}
	
	/**
	 * @param comuneDelegante the comuneDelegante to set
	 */
	public void setComuneDelegante(String comuneDelegante) {
		this.comuneDelegante = comuneDelegante;
	}

	/**
	 * @return the comuneDelegato
	 */
	public String getComuneDelegato() {
		return comuneDelegato;
	}
	
	/**
	 * @param comuneDelegato the comuneDelegato to set
	 */
	public void setComuneDelegato(String comuneDelegato) {
		this.comuneDelegato = comuneDelegato;
	}

	/**
	 * @return the denominazioneImpresa
	 */
	public String getDenominazioneImpresa() {
		return denominazioneImpresa;
	}
	
	/**
	 * @param denominazioneImpresa the denominazioneImpresa to set
	 */
	public void setDenominazioneImpresa(String denominazioneImpresa) {
		this.denominazioneImpresa = denominazioneImpresa;
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
	 * @return the dtRevoca
	 */
	public Date getDtRevoca() {
		return dtRevoca;
	}
	
	/**
	 * @param dtRevoca the dtRevoca to set
	 */
	public void setDtRevoca(Date dtRevoca) {
		this.dtRevoca = dtRevoca;
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
	 * @return the faxDelegato
	 */
	public String getFaxDelegato() {
		return faxDelegato;
	}
	
	/**
	 * @param faxDelegato the faxDelegato to set
	 */
	public void setFaxDelegato(String faxDelegato) {
		this.faxDelegato = faxDelegato;
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
	 * @return the indirizzoDelegante
	 */
	public String getIndirizzoDelegante() {
		return indirizzoDelegante;
	}
	
	/**
	 * @param indirizzoDelegante the indirizzoDelegante to set
	 */
	public void setIndirizzoDelegante(String indirizzoDelegante) {
		this.indirizzoDelegante = indirizzoDelegante;
	}

	/**
	 * @return the indirizzoDelegato
	 */
	public String getIndirizzoDelegato() {
		return indirizzoDelegato;
	}
	
	/**
	 * @param indirizzoDelegato the indirizzoDelegato to set
	 */
	public void setIndirizzoDelegato(String indirizzoDelegato) {
		this.indirizzoDelegato = indirizzoDelegato;
	}

	/**
	 * @return the mailDelegante
	 */
	public String getMailDelegante() {
		return mailDelegante;
	}
	
	/**
	 * @param mailDelegante the mailDelegante to set
	 */
	public void setMailDelegante(String mailDelegante) {
		this.mailDelegante = mailDelegante;
	}

	/**
	 * @return the mailDelegato
	 */
	public String getMailDelegato() {
		return mailDelegato;
	}
	
	/**
	 * @param mailDelegato the mailDelegato to set
	 */
	public void setMailDelegato(String mailDelegato) {
		this.mailDelegato = mailDelegato;
	}

	public Long getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(Long numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	/**
	 * @return the nomeDelegante
	 */
	public String getNomeDelegante() {
		return nomeDelegante;
	}
	
	/**
	 * @param nomeDelegante the nomeDelegante to set
	 */
	public void setNomeDelegante(String nomeDelegante) {
		this.nomeDelegante = nomeDelegante;
	}

	/**
	 * @return the nomeDelegato
	 */
	public String getNomeDelegato() {
		return nomeDelegato;
	}
	
	/**
	 * @param nomeDelegato the nomeDelegato to set
	 */
	public void setNomeDelegato(String nomeDelegato) {
		this.nomeDelegato = nomeDelegato;
	}

	/**
	 * @return the pivaDelegato
	 */
	public String getPivaDelegato() {
		return pivaDelegato;
	}
	
	/**
	 * @param pivaDelegato the pivaDelegato to set
	 */
	public void setPivaDelegato(String pivaDelegato) {
		this.pivaDelegato = pivaDelegato;
	}

	/**
	 * @return the pivaImpresa
	 */
	public String getPivaImpresa() {
		return pivaImpresa;
	}
	
	/**
	 * @param pivaImpresa the pivaImpresa to set
	 */
	public void setPivaImpresa(String pivaImpresa) {
		this.pivaImpresa = pivaImpresa;
	}

	/**
	 * @return the pvDelegante
	 */
	public String getPvDelegante() {
		return pvDelegante;
	}
	
	/**
	 * @param pvDelegante the pvDelegante to set
	 */
	public void setPvDelegante(String pvDelegante) {
		this.pvDelegante = pvDelegante;
	}

	/**
	 * @return the pvDelegato
	 */
	public String getPvDelegato() {
		return pvDelegato;
	}
	
	/**
	 * @param pvDelegato the pvDelegato to set
	 */
	public void setPvDelegato(String pvDelegato) {
		this.pvDelegato = pvDelegato;
	}

	/**
	 * @return the telefonoDelegante
	 */
	public String getTelefonoDelegante() {
		return telefonoDelegante;
	}
	
	/**
	 * @param telefonoDelegante the telefonoDelegante to set
	 */
	public void setTelefonoDelegante(String telefonoDelegante) {
		this.telefonoDelegante = telefonoDelegante;
	}

	/**
	 * @return the telefonoDelegato
	 */
	public String getTelefonoDelegato() {
		return telefonoDelegato;
	}
	
	/**
	 * @param telefonoDelegato the telefonoDelegato to set
	 */
	public void setTelefonoDelegato(String telefonoDelegato) {
		this.telefonoDelegato = telefonoDelegato;
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

	public Comune getComDelegato() {
		return comDelegato;
	}

	public void setComDelegato(Comune comDelegato) {
		this.comDelegato = comDelegato;
	}

	public Comune getComDelegante() {
		return comDelegante;
	}

	public void setComDelegante(Comune comDelegante) {
		this.comDelegante = comDelegante;
	}

	public StatiEsteri getStatiEsteriDelegato() {
		return statiEsteriDelegato;
	}

	public void setStatiEsteriDelegato(StatiEsteri statiEsteriDelegato) {
		this.statiEsteriDelegato = statiEsteriDelegato;
	}

	public StatiEsteri getStatiEsteriDelegante() {
		return statiEsteriDelegante;
	}

	public void setStatiEsteriDelegante(StatiEsteri statiEsteriDelegante) {
		this.statiEsteriDelegante = statiEsteriDelegante;
	}

	/**
	 * @return the personalizzazione
	 */
	public Personalizzazione getPersonalizzazione() {
		return personalizzazione;
	}
	
	/**
	 * @param personalizzazione the personalizzazione to set
	 */
	public void setPersonalizzazione(Personalizzazione personalizzazione) {
		this.personalizzazione = personalizzazione;
	}

	/**
	 * @return the statoDelega
	 */
	public StatoDelega getStatoDelega() {
		return statoDelega;
	}
	
	/**
	 * @param statoDelega the statoDelega to set
	 */
	public void setStatoDelega(StatoDelega statoDelega) {
		this.statoDelega = statoDelega;
	}

	/**
	 * @return the tipoDelega
	 */
	public TipoDelega getTipoDelega() {
		return tipoDelega;
	}
	
	/**
	 * @param tipoDelega the tipoDelega to set
	 */
	public void setTipoDelega(TipoDelega tipoDelega) {
		this.tipoDelega = tipoDelega;
	}

}
