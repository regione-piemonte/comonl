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

package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostStampaElencoAnagraficheRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PdfResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.mime.MimeTypeContainer;
import it.csi.comonl.comonlweb.ejb.util.mime.MimeTypeContainer.Extension;
import it.csi.comonl.comonlweb.ejb.util.report.ReportUtilities;
import it.csi.comonl.comonlweb.ejb.util.report.ReportAnagrafiche;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaAccreditamentoAnagrafiche;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.Sort;

public class StampaAnagraficheDelegatiService
		extends BaseAnagraficaDelegatoService<PostStampaElencoAnagraficheRequest, PdfResponse> {

	private AnagraficaDelegatoDad anagraficaDelegatoDad;
	private FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche;

	public StampaAnagraficheDelegatiService(ConfigurationHelper configurationHelper,
			AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, anagraficaDelegatoDad);
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
	}

	@Override
	protected void checkServiceParams() {
		checkNotNull(request.getTipoFormato(), "tipoFormato");
		ricercaAccreditamentoAnagrafiche = request.getRicercaAccreditamentoAnagrafiche();
		checkNotNull(ricercaAccreditamentoAnagrafiche, "ricercaAccreditamentoAzienda", true);
	}

	@Override
	protected void execute() {

		PagedList<AnagraficaDelegato> res = anagraficaDelegatoDad.getRicercaConsulente(0, Integer.MAX_VALUE,
				new Sort("", "asc"), ricercaAccreditamentoAnagrafiche);

		if (res != null && res.getList() != null) {
			if ("PDF".equalsIgnoreCase(request.getTipoFormato())) {
				createPdf(res.getList());
			} else if ("XSL".equalsIgnoreCase(request.getTipoFormato())) {
				createXsl(res.getList());
			}
		}
	}

	private void createPdf(List<AnagraficaDelegato> res) {
		
		ReportAnagrafiche reportAnagrafiche = new ReportAnagrafiche("Anagrafiche delegati");
		reportAnagrafiche.setLandscape(true);
		
		reportAnagrafiche.addNomiColonne("Tipo");
		reportAnagrafiche.addNomiColonne("Codice Fiscale");
		reportAnagrafiche.addNomiColonne("Cognome");
		reportAnagrafiche.addNomiColonne("Nome");
		reportAnagrafiche.addNomiColonne("Comune domicilio");
		reportAnagrafiche.addNomiColonne("Provincia domicilio");
		reportAnagrafiche.addNomiColonne("Comune residenza");
		reportAnagrafiche.addNomiColonne("Provincia residenza");
		reportAnagrafiche.addNomiColonne("Data annullamento");
		
		
		for (AnagraficaDelegato ana : res) {
			List<String> cols = new ArrayList<String>();
			cols.add(ReportUtilities.formatTipo(ana.getId().getTipoAnagrafica()));
			cols.add(ana.getId().getCfDelegato());
			cols.add(ana.getCognome());
			cols.add(ana.getNome());
			cols.add(ReportUtilities.formatComune(ana.getComuneDom()));
			cols.add(ReportUtilities.formatProvincia(ana.getComuneDom()));
			cols.add(ReportUtilities.formatComune(ana.getComuneRes()));
			cols.add(ReportUtilities.formatProvincia(ana.getComuneRes()));
			cols.add(ReportUtilities.formatDate(ana.getDataAnnullamento()));
			reportAnagrafiche.addValori(cols);
		}
		
		String htmlContent = ReportUtilities.createReportAnagrafiche(reportAnagrafiche);
		
		try {
			ReportUtilities.makePdfResponse(response, htmlContent, "AnagraficheDelegati");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			log.info("StampaAnagraficheDelegatiService", "Anagrafiche delegati - fine");
		}
	}
	
	

	private void createXsl(List<AnagraficaDelegato> res) {

		ByteArrayOutputStream bos = null;
		XSSFWorkbook workbookOutput = null;
		try {
			
			workbookOutput = new XSSFWorkbook();
			XSSFSheet sheet = workbookOutput.createSheet("Anagrafiche delegati");

			int rowCount = 0;
			Row row = null;
			Cell cell = null;
			
			
			// TITOLI
			row = sheet.createRow(rowCount);
			XSSFFont titleFont= workbookOutput.createFont();
			titleFont.setBold(true);
			XSSFCellStyle titleStyle = workbookOutput.createCellStyle();           
			titleStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
			titleStyle.setFont(titleFont);

			
			cell = row.createCell(0);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Tipo");
			
			cell = row.createCell(1);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Codice Fiscale");
			
			cell = row.createCell(2);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Cognome");
			
			cell = row.createCell(3);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Nome");
			
			cell = row.createCell(4);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Comune domicilio");
			
			cell = row.createCell(5);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Provincia domicilio");
			
			cell = row.createCell(6);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Comune residenza");
			
			cell = row.createCell(7);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Provincia residenza");
			
			cell = row.createCell(8);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Data annullamento");
			
			
			// STAMPA
			for (AnagraficaDelegato ana : res) {
				row = sheet.createRow(++rowCount);
				
				
				cell = row.createCell(0);
				cell.setCellValue(ReportUtilities.formatTipo(ana.getId().getTipoAnagrafica()));
				
				cell = row.createCell(1);
				cell.setCellValue(ana.getId().getCfDelegato());
				
				cell = row.createCell(2);
				cell.setCellValue(ana.getCognome());
				
				cell = row.createCell(3);
				cell.setCellValue(ana.getNome());
				
				cell = row.createCell(4);
				cell.setCellValue(ReportUtilities.formatComune(ana.getComuneDom()));
				
				cell = row.createCell(5);
				cell.setCellValue(ReportUtilities.formatProvincia(ana.getComuneDom()));
				
				cell = row.createCell(6);
				cell.setCellValue(ReportUtilities.formatComune(ana.getComuneRes()));
				
				cell = row.createCell(7);
				cell.setCellValue(ReportUtilities.formatProvincia(ana.getComuneRes()));
				
				cell = row.createCell(8);
				cell.setCellValue(ReportUtilities.formatDate(ana.getDataAnnullamento()));
			}
			
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			sheet.autoSizeColumn(6);
			sheet.autoSizeColumn(7);
			sheet.autoSizeColumn(8);
			
			
			bos = new ByteArrayOutputStream();
			workbookOutput.write(bos);
			response.setBytes(bos.toByteArray());
			response.setFileNameTemplate("AnagraficheDelegati");
			response.setMimeTypeContainer(MimeTypeContainer.byExtension(Extension.EXCEL_WORKBOOK));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (workbookOutput != null)
				try {
					workbookOutput.close();
				} catch (IOException e1) {
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
				}
			log.info("StampaAnagraficheDelegatiService", "XSL - fine");
		}
		
	}
	

}
