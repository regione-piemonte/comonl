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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.base;

import javax.ws.rs.core.Response;

import it.csi.comonl.comonlweb.ejb.util.mime.MimeTypeContainer;

/**
 * Base response for export file
 */
public abstract class BaseExportResponse extends BaseResponse {

	private byte[] bytes;
	private String fileName;
	private MimeTypeContainer mimeTypeContainer;
	/**
	 * @return the bytes
	 */
	public byte[] getBytes() {
		return bytes;
	}

	/**
	 * @param bytes the bytes to set
	 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	/**
	 * @return the mimeTypeContainer
	 */
	public MimeTypeContainer getMimeTypeContainer() {
		return mimeTypeContainer;
	}

	/**
	 * @param mimeTypeContainer the mimeTypeContainer to set
	 */
	public void setMimeTypeContainer(MimeTypeContainer mimeTypeContainer) {
		this.mimeTypeContainer = mimeTypeContainer;
	}

	@Override
	protected Response composeOwnResponse() {
		return Response
			.ok(bytes, getMimeTypeContainer().getMimeType())
			.header("Content-Disposition", "attachment;filename=" + fileName + "." + getMimeTypeContainer().getExtension())
			.build();
	}

}
