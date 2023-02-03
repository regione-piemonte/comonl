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
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class Comune.
 */
public class DecodificaGenerica extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codDecodifica;
	private String dsDecodifica;
	private Long idDecodifica;
	private Long idFiltroFacoltativo;
	private String idComTipoTracciato;
	private String idComTipoComunicazione; // realtivo a ComTTipoComunicazione
	private String flgForma; // relativo a ProTContratti
	private String flgStatiUe; // relativo a ProTStatiEsteri
	private Date dataValidita; // se impostato cerca le decodifiche validi per qualunque decodifica
	private String siglaNazione; // serve per visualizzare la sigla della nazione ad esempio IT - ITALIA
	private String idCcnl;

	/**
	 * ambitoDecodifica -- utilizzato per categoriaEscluse decodifica
	 */
	private String ambitoDecodifica;

	/**
	 * flgAncheNonValidi -- se impostato a true non esegue il filtro su data inizio
	 * e fine
	 */
	private boolean flgAncheNonValidi = false;

	/**
	 * @return the codDecodifica
	 */
	public String getCodDecodifica() {
		return codDecodifica;
	}

	/**
	 * @param codDecodifica the codDecodifica to set
	 */
	public void setCodDecodifica(String codDecodifica) {
		this.codDecodifica = codDecodifica;
	}

	/**
	 * @return the dsDecodifica
	 */
	public String getDsDecodifica() {
		return dsDecodifica;
	}

	/**
	 * @param dsDecodifica the dsDecodifica to set
	 */
	public void setDsDecodifica(String dsDecodifica) {
		this.dsDecodifica = dsDecodifica;
	}

	/**
	 * @return the idDecodifica
	 */
	public Long getIdDecodifica() {
		return idDecodifica;
	}

	/**
	 * @param idDecodifica the idDecodifica to set
	 */
	public void setIdDecodifica(Long idDecodifica) {
		this.idDecodifica = idDecodifica;
	}

	/**
	 * @return the ambitoDecodifica
	 */
	public String getAmbitoDecodifica() {
		return ambitoDecodifica;
	}

	/**
	 * @param ambitoDecodifica the ambitoDecodifica to set
	 */
	public void setAmbitoDecodifica(String ambitoDecodifica) {
		this.ambitoDecodifica = ambitoDecodifica;
	}

	/**
	 * @return the flgAncheNonValidi
	 */
	public boolean isFlgAncheNonValidi() {
		return flgAncheNonValidi;
	}

	/**
	 * @param flgAncheNonValidi the flgAncheNonValidi to set
	 */
	public void setFlgAncheNonValidi(boolean flgAncheNonValidi) {
		this.flgAncheNonValidi = flgAncheNonValidi;
	}

	/**
	 * @return the idFiltroFacoltativo
	 */
	public Long getIdFiltroFacoltativo() {
		return idFiltroFacoltativo;
	}

	/**
	 * @param idFiltroFacoltativo the id optional to set
	 */
	public void setIdFiltroFacoltativo(Long idFiltroFacoltativo) {
		this.idFiltroFacoltativo = idFiltroFacoltativo;
	}

	public String getFlgForma() {
		return flgForma;
	}

	public void setFlgForma(String flgForma) {
		this.flgForma = flgForma;
	}

	public String getFlgStatiUe() {
		return flgStatiUe;
	}

	public void setFlgStatiUe(String flgStatiUe) {
		this.flgStatiUe = flgStatiUe;
	}

	public Date getDataValidita() {
		return dataValidita;
	}

	public void setDataValidita(Date dataValidita) {
		this.dataValidita = dataValidita;
	}

	public String getSiglaNazione() {
		return siglaNazione;
	}

	public void setSiglaNazione(String siglaNazione) {
		this.siglaNazione = siglaNazione;
	}

	public String getIdComTipoTracciato() {
		return idComTipoTracciato;
	}

	public void setIdComTipoTracciato(String idComTipoTracciato) {
		this.idComTipoTracciato = idComTipoTracciato;
	}

	public String getIdComTipoComunicazione() {
		return idComTipoComunicazione;
	}

	public void setIdComTipoComunicazione(String idComTipoComunicazione) {
		this.idComTipoComunicazione = idComTipoComunicazione;
	}

	public String getIdCcnl() {
		return idCcnl;
	}

	public void setIdCcnl(String idCcnl) {
		this.idCcnl = idCcnl;
	}

}
