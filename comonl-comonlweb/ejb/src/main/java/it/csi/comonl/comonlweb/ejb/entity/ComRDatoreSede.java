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
import javax.persistence.*;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_R_DATORE_SEDE database table.
 * 
 */
@Entity
@Table(name="COM_R_DATORE_SEDE")
@NamedQuery(name="ComRDatoreSede.findAll", query="SELECT c FROM ComRDatoreSede c")
public class ComRDatoreSede implements Serializable,BaseEntity<ComRDatoreSedePK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ComRDatoreSedePK id;

	public ComRDatoreSede() {
	}

	public ComRDatoreSedePK getId() {
		return this.id;
	}

	public void setId(ComRDatoreSedePK id) {
		this.id = id;
	}
	
	//bi-directional many-to-one association to ComDSedeLavoro
	@ManyToOne
	@JoinColumn(name="ID_COM_D_SEDE_LAVORO", insertable=false, updatable=false)
	private ComDSedeLavoro comDSedeLavoro;

	//bi-directional many-to-one association to ComDDatore
	@ManyToOne
	@JoinColumn(name="ID_COM_D_DATORE", insertable=false, updatable=false)
	private ComDDatore comDDatore;

	public ComDSedeLavoro getComDSedeLavoro() {
		return comDSedeLavoro;
	}

	public void setComDSedeLavoro(ComDSedeLavoro comDSedeLavoro) {
		this.comDSedeLavoro = comDSedeLavoro;
	}

	public ComDDatore getComDDatore() {
		return comDDatore;
	}

	public void setComDDatore(ComDDatore comDDatore) {
		this.comDDatore = comDDatore;
	}
	
}
