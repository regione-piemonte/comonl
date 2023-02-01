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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAzienda;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaAccreditamentoAnagrafiche;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.Sort;

public class StampaStudiAziendeService
		extends BaseAnagraficaDelegatoService<PostStampaElencoAnagraficheRequest, PdfResponse> {

	private AnagraficaDelegatoDad anagraficaDelegatoDad;
	private FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche;

	public StampaStudiAziendeService(ConfigurationHelper configurationHelper,
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

		int limMaxRicerca = ComonlConstants.MAX_NUM_RES_RICERCA;
		PagedList<AnagraficaAzienda> res = anagraficaDelegatoDad.getRicercaAzienda(limMaxRicerca, 0, Integer.MAX_VALUE,
				new Sort("", "asc"), request.getRicercaAccreditamentoAnagrafiche());

		if (res != null && res.getList() != null) {
			if ("PDF".equalsIgnoreCase(request.getTipoFormato())) {
				createPdf(res.getList());
			} else if ("XSL".equalsIgnoreCase(request.getTipoFormato())) {
				createXsl(res.getList());
			}
		}
	}

	private void createPdf(List<AnagraficaAzienda> res) {

		ReportAnagrafiche reportAnagrafiche = new ReportAnagrafiche("Anagrafiche aziende");
		
		reportAnagrafiche.addNomiColonne("Codice Fiscale");
		reportAnagrafiche.addNomiColonne("Partiva Iva");
		reportAnagrafiche.addNomiColonne("Denominazione");
		reportAnagrafiche.addNomiColonne("Comune");
		reportAnagrafiche.addNomiColonne("Provincia");
		reportAnagrafiche.addNomiColonne("Tipo studio");
		reportAnagrafiche.addNomiColonne("Data annullamento");
		
		
		for (AnagraficaAzienda ana : res) {
			List<String> cols = new ArrayList<String>();
			cols.add(ana.getCodiceFiscale());
			cols.add(ana.getPartitaIva());
			cols.add(ana.getDenominazione());
			cols.add(ana.getComune());
			cols.add(ana.getProvincia());
			cols.add(ana.getTipoStudio());
			cols.add(ReportUtilities.formatDate(ana.getDataAnnullamento()));
			reportAnagrafiche.addValori(cols);
		}
		
		String htmlContent = ReportUtilities.createReportAnagrafiche(reportAnagrafiche);

		try {
			ReportUtilities.makePdfResponse(response, htmlContent, "AnagraficheAziende");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			log.info("StampaStudiAziendeService", "Anagrafiche aziende - fine");
		}	
	}

	private void createXsl(List<AnagraficaAzienda> res) {

		ByteArrayOutputStream bos = null;
		XSSFWorkbook workbookOutput = null;
		try {
			
			workbookOutput = new XSSFWorkbook();
			XSSFSheet sheet = workbookOutput.createSheet("Anagrafiche aziende");

			int rowCount = 0;
			Row row = null;
			Cell cell = null;
			
			// TITOLI
			row = sheet.createRow(rowCount);
			XSSFFont titleFont= workbookOutput.createFont();
			titleFont.setBold(true);
			XSSFCellStyle titleStyle = workbookOutput.createCellStyle();           
			titleStyle.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
			titleStyle.setFont(titleFont);

			cell = row.createCell(0);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Codice Fiscale ");
			
			cell = row.createCell(1);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Partiva Iva ");
			
			cell = row.createCell(2);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Denominazione");
			
			cell = row.createCell(3);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Comune");
			
			cell = row.createCell(4);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Provincia");
			
			cell = row.createCell(5);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Tipo studio");
			
			cell = row.createCell(6);
			cell.setCellStyle(titleStyle);
			cell.setCellValue("Data annullamento");
			
			// STAMPA
			for (AnagraficaAzienda ana : res) {
				row = sheet.createRow(++rowCount);
				
				cell = row.createCell(0);
				cell.setCellValue(ana.getCodiceFiscale());
				
				cell = row.createCell(1);
				cell.setCellValue(ana.getPartitaIva());
				
				cell = row.createCell(2);
				cell.setCellValue(ana.getDenominazione());
				
				cell = row.createCell(3);
				cell.setCellValue(ana.getComune());
				
				cell = row.createCell(4);
				cell.setCellValue(ana.getProvincia());
				
				cell = row.createCell(5);
				cell.setCellValue(ana.getTipoStudio());
				
				cell = row.createCell(6);
				cell.setCellValue(ReportUtilities.formatDate(ana.getDataAnnullamento()));
				
			}
			
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			sheet.autoSizeColumn(6);
			
			
			bos = new ByteArrayOutputStream();
			workbookOutput.write(bos);
			response.setBytes(bos.toByteArray());
			response.setFileNameTemplate("AnagraficheAziende");
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
			log.info("StampaStudiAziendeService", "XSL - fine");
		}
		
	}
}
