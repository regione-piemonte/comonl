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
package it.csi.comonl.comonlweb.ejb.util.commax.mail;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Hashtable;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class GestoreArchivioZip {
    String srcPath= this.getClass().getName();

        public Hashtable hash = new Hashtable();
        public Vector fileError = new Vector();

        String[][] gestoreXml = null;



        public static final void copyInputStream(InputStream in, OutputStream out)
        throws IOException
        {
                byte[] buffer = new byte[1024];
                int len;

                while((len = in.read(buffer)) >= 0)
                        out.write(buffer, 0, len);

                in.close();
                out.close();
        }

        public File creaZip(File[]files, String dir, String fileNameAllegatoZip) throws IOException{

                //Creazione del path
                String directory=dir;

                //se DIR � vuoto, ci troviamo nella directory root
                if (dir!=null && !dir.equals(""))
                        directory+=File.separator+dir;

                //File name: root/dir/user.zip
                String filename=dir+File.separator+fileNameAllegatoZip;
//                Debug.printDebug("filename" + filename, srcPath);

                //Creazione dello stream di scrittura
                File file=new File(filename);
                FileOutputStream fileZip=new FileOutputStream(file);

                //Decoro lo stream di output con il filtro di Zip
                ZipOutputStream zip=new ZipOutputStream(fileZip);

                //Ogni entry della collection rappresenta un file, che aggiungiamo allo zip
                for(int i=0;i<files.length;i++){
                        if (files[i] != null) {
                                File f=(File)files[i];

                                aggiungiFileNelloZip(f,zip);
                        }

                }

                //Chiusura dei flussi aperti
                zip.finish();
                fileZip.close();

                /**
                 * Se � andato tutto bene restituiamo il path dove scaricare il file fisico
                 * creato lato server.
                 */

                return file;
        }

        /**
         * Aggiunge un file allo stream ZIP
         */

        private static void aggiungiFileNelloZip(File file, ZipOutputStream zip) throws IOException {
                ZipEntry item=new ZipEntry( file.getName() );

                /**
                 * RandomAccessFile � una virtualizzazione per accedere ad un file,
                 * in questo caso accediamo in sola lettura: attraverso questa classe si
                 * possono recuperare propriet� "ad accesso casuale" come la lunghezza del file
                 */

                if (!file.isDirectory()){
                        RandomAccessFile f=new RandomAccessFile(file,"r");

                        //Lettura del contenuto del file e salvataggio come item nello zip
                        byte []buffer=new byte[(int) f.length()];
                        f.read(buffer);
                        zip.putNextEntry(item);

                        //Scrittura del contenuto del file
                        zip.write(buffer,0,(int) f.length());
                        zip.closeEntry();
                }
        }
}
