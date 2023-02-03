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
package it.csi.comonl.comonlweb.lib.util.filesystem;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

public class FileSystemUtil {

	private final static String COMMAX_FILE_DIR = "commax";
	public static final String EMPTY = "";
	private static final LogUtil LOG = new LogUtil(FileSystemUtil.class);

	public static File getCommaxBaseDir(String basePath) {
		File dataDir = new File(basePath);
		dataDir.mkdirs();
		
		File commaxDir = new File(dataDir, COMMAX_FILE_DIR);
		commaxDir.mkdirs();
		
		return commaxDir;
	}


	public void writeFile(byte[] data, File baseDir, String fileName) throws Exception {
		File file = new File(baseDir, fileName);
		BufferedOutputStream bos1 = new BufferedOutputStream(new FileOutputStream(file));
		bos1.write(data);
		bos1.flush();
		bos1.close();
	}

	public boolean isFileAmmessoPerDimensioniEPerNumeroDiXMLContenuti(File file, int maxDimFileDB, int maxNumFilesFileDB) {
		return isFileAmmessoPerDimensioni(file, maxDimFileDB) && isFileAmmessoPerNumeroFile(file, maxNumFilesFileDB);
	}

	private boolean isFileAmmessoPerDimensioni(File file, int maxDimFileDB) {
		boolean flag = false;
		// Ottengo il numero di byte e lo divido per 1024 per aver i KB
		long peso = file.length()/1024;
		if (peso <= maxDimFileDB) {
			flag = true;
		}
		return flag;
	}

	private boolean isFileAmmessoPerNumeroFile(File file, int maxNumFilesFileDB) {
		FileInputStream file_input = null;
		ZipEntry entry = null;
		int quantiFile = 0;
		try {
			file_input = new FileInputStream (file);
			ZipInputStream Zin = new ZipInputStream (file_input);
			while ( (entry = Zin.getNextEntry() ) != null) {
				quantiFile++;

			}
		} catch (FileNotFoundException fnf) {
			LOG.error("FileSystemUtil::isFileAmmessoPerNumeroFile", fnf.getMessage());
		} catch (Exception e) {
			LOG.error("FileSystemUtil::isFileAmmessoPerNumeroFile", e.getMessage());
		}
		if (quantiFile > maxNumFilesFileDB) {
			return false;
		}
		return true;
	}
	public ListaEsitiEstrazioneFilesDaZip estraiElencoFilesDaZip(File file) {
		ListaEsitiEstrazioneFilesDaZip lista = new ListaEsitiEstrazioneFilesDaZip();

		ZipFile zipFile;
		try {
			zipFile = new ZipFile(file);

			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while(entries.hasMoreElements()){
				ZipEntry entry = entries.nextElement();
				EsitoEstrazioneFilesDaZip esito = new EsitoEstrazioneFilesDaZip();
				esito.setNomeFile(entry.getName());
				esito.setEstensioneFile(substringAfterLast(esito.getNomeFile(), "."));
				if (!esito.getEstensioneFile().equalsIgnoreCase("xml")) {
					esito.setEsitoEstrazione(EsitoEstrazioneFilesDaZip.ESITO_ESTRAZIONE_KO);
					lista.addEsitoPerKO(esito);
				} else {
					esito.setEsitoEstrazione(EsitoEstrazioneFilesDaZip.ESITO_ESTRAZIONE_OK);
					InputStream stream = zipFile.getInputStream(entry);
					StringBuffer buffer = addSpaceAtTheEndOfAllLines(stream);
					esito.setContenutoDelFileInFormatoXML(removeInvalidXMLCharacters(buffer.toString()));
					lista.addEsitoPerOK(esito);
				}
			}

		} catch (ZipException e1) {
			LOG.error("FileSystemUtil::estraiElencoFilesDaZip", e1.getMessage());
		} catch (IOException e1) {
			LOG.error("FileSystemUtil::estraiElencoFilesDaZip", e1.getMessage());
		}

		return lista;
	}

	public static String substringAfterLast(String str, String separator) {
		if (str == null || str.length() == 0) {
			return str;
		}
		if (separator == null || separator.length() == 0) {
			return EMPTY;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1 || pos == (str.length() - separator.length())) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	public static  String removeInvalidXMLCharacters(String xml) {

		char current;

		int l = xml.length();

		StringBuffer out = new StringBuffer(l); 

		if (xml == null || ("".equals(xml))){
			return "";
		}

		for (int i = 0; i < l; i++) {
			current = xml.charAt(i);
			if (current >= 32 && current <= 127  ||
					current == 224 ||
					current == 232 ||
					current == 233 ||
					current == 236 ||
					current == 242 ||
					current == 249){
				out.append(current);
			}
		}		

		return out.toString();
	}
	public static StringBuffer addSpaceAtTheEndOfAllLines(InputStream is ) throws IOException {

		StringBuffer sb = new StringBuffer();

		if (is != null) {
			sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			loopAndAdd(sb, br);
		}
		return sb;
	}
	private static void loopAndAdd(StringBuffer sb, BufferedReader br) throws IOException {
		String line;
		while((line=br.readLine())!= null){
			String lineToAdd = line.trim();
			lineToAdd += "  ";
			sb.append(lineToAdd);
		}
	}
	public static String addSpaceAtTheEndOfAllLines(String str ) throws IOException {
		StringBuffer sb = null;
		
		if (str != null) {
			sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new StringReader(str));
			
			loopAndAdd(sb, br);
		}
		
		if (sb != null) {
			return sb.toString();
		}
		return str;
	}
	
	public static String getNomeFileNelloZip(ListaEsitiEstrazioneFilesDaZip lista, int indiceDelFileNelloZip)
	{
		String nomeFile = null;
		try {
			EsitoEstrazioneFilesDaZip esito  = (EsitoEstrazioneFilesDaZip) lista.getListaEsitiTotale().get(indiceDelFileNelloZip);
			nomeFile  = esito.getNomeFile();
		} catch (RuntimeException e) {
			LOG.error("FileSystemUtil::getNomeFileNelloZip", e.getMessage());
		}
		return nomeFile;
	} 
}
