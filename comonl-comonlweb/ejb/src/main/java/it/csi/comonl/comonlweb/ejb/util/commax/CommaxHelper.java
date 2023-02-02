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
package it.csi.comonl.comonlweb.ejb.util.commax;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

import javax.ejb.Stateless;

import org.apache.commons.io.FileUtils;

import it.csi.comonl.comonlweb.lib.dto.decodifiche.CommaxParametri;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.lib.util.filesystem.EsitoEstrazioneFilesDaZip;
import it.csi.comonl.comonlweb.lib.util.filesystem.FileSystemUtil;
import it.csi.comonl.comonlweb.lib.util.filesystem.ListaEsitiEstrazioneFilesDaZip;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.spicom.helper.SpicomIntegrator;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;
import it.csi.spicom.dto.EsitoValidazioneComunicazione;
import it.csi.spicom.interf.RisultatoConversioneTracciato;
import it.csi.spicom.interf.ServizioTraduzioneTracciati;
import it.csi.spicom.util.ComunicazioneTUValidator;

/**
 * Helper for the configuration handling
 */
@Stateless
public class CommaxHelper {
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static Pattern pattern;
	private static Matcher matcher;

	private static final LogUtil LOG = new LogUtil(CommaxHelper.class);
	
	public static boolean isValidMail(String input) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		if (ComonlUtility.isNotVoid(input)) {
			input = input.trim();
			matcher = pattern.matcher(input);
			return matcher.matches();
		}
		return true;
	}

	public static String restituisciEstensioneFileUpload(String fileName) {

		if (fileName.toLowerCase().endsWith(".zip")) {
			return "zip";
		} else if (fileName.toLowerCase().endsWith(".xml")) {
			return "xml";
		}
		return null;
	}

	public static boolean isControlloSuCrcDelFileUploadatoAbilitato(CommaxParametri parametro) {
		if ("S".equalsIgnoreCase(parametro.getValoreParametro())) {
			return true;
		}
		return false;
	}

	public String salvaSingoloFile(String basePath, Timestamp timestamp, byte[] fileInByte, String nomeFile) {
		String ts = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(timestamp);
		String nomeFileDaSalvare = "COM-" + ts + "-" + nomeFile;
		nomeFileDaSalvare = nomeFileDaSalvare.replaceAll("[^\\x00-\\x7F]", "");
		try {
			FileSystemUtil fs = new FileSystemUtil();
			fs.writeFile(fileInByte, FileSystemUtil.getCommaxBaseDir(basePath), nomeFileDaSalvare);
		} catch (FileNotFoundException fnfe) {
			nomeFileDaSalvare = null;
			fnfe.printStackTrace();
		} catch (SecurityException se) {
			se.printStackTrace();
			nomeFileDaSalvare = null;
		} catch (IOException ie) {
			ie.printStackTrace();
			nomeFileDaSalvare = null;
		} catch (Exception e) {
			e.printStackTrace();
			nomeFileDaSalvare = null;
		}
		return nomeFileDaSalvare;
	}

	public Long calcolaCRC(File fileUploadato) throws IOException {
		byte[] array = FileUtils.readFileToByteArray(fileUploadato);
		return calcolaCRC(array);
	}

	private Long calcolaCRC(byte[] array) {
		CRC32 crc32 = new CRC32();
		crc32.update(array);
		long valoreRecordCRC32 = crc32.getValue();
		crc32.reset();
		return Long.valueOf(valoreRecordCRC32);
	}
	public Long calcolaCRC(String xmlSingolo)
    {
            byte[] array = xmlSingolo.getBytes();
            return calcolaCRC(array);
    }

	public void validaXML(ListaEsitiEstrazioneFilesDaZip lista, StringBuffer esitoConversioneDaSPicom, ComunicazioneTracciatoUnicoDTO[] tracciatiRisultanti) throws Exception{
		String[] arrayXML = lista.getArrayXMLDaConvertireTramiteSpicom();
		LOG.info("CommaxHelper::validaXML", "Chiamo SPICOM per farmi validare XML");
		List<RisultatoConversioneTracciato> risult = SpicomIntegrator.getInstance().convertiInFormatoUnico(arrayXML);

		for (int i=0;i<risult.size();i++) {
			if (risult.get(i).isConversioneEseguitaConSuccesso()==false)  // il file iesimo e sbagliato
			{
				if (risult.size() ==1){
					esitoConversioneDaSPicom.append("Formato del file non corretto");  // Caso di xml singolo
				}
				else{

					esitoConversioneDaSPicom.append(" File numero ").append(i+1).append(" con nome = ").append(FileSystemUtil.getNomeFileNelloZip(lista, i)).append(" non e' corretto ").append("\n\r");
				}

				tracciatiRisultanti[i]=null;
			}
			else
			{
				tracciatiRisultanti[i]=risult.get(i).getTraccciatoConvertito();

			}
		}
	}
	public void validaXMLTipoComunicazione(ListaEsitiEstrazioneFilesDaZip lista, StringBuffer esitoConversioneDaSPicom, ComunicazioneTracciatoUnicoDTO[] tracciatiRisultanti) {
		EsitoValidazioneComunicazione esito = null;


		for (int i=0;i<tracciatiRisultanti.length;i++)
		{

			if(tracciatiRisultanti[i]!= null){

				esito = ComunicazioneTUValidator.validaComunicazione(tracciatiRisultanti[i]);

				if (!esito.isComunicazioneValida()) 
				{

					if (tracciatiRisultanti.length==1){
						esitoConversioneDaSPicom.append("File non corretto ").append(esito.getEsitoValidazione());  // Caso di xml singolo
					}
					else{
						esitoConversioneDaSPicom.append("File numero ").append(i+1).append(" con nome = ").append(getNomeFileNelloZip(lista, i)).append(" non e' corretto:  ").append(esito.getEsitoValidazione()).append("\r\n");
					}

				}
				else
				{
				}
			}

		}
	}
	public String getNomeFileNelloZip(ListaEsitiEstrazioneFilesDaZip lista, int indiceDelFileNelloZip)
	{
		String nomeFile = null;
		try {
			EsitoEstrazioneFilesDaZip esito  = (EsitoEstrazioneFilesDaZip) lista.getListaEsitiTotale().get(indiceDelFileNelloZip);
			nomeFile  = esito.getNomeFile();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nomeFile;
	}
	
}
