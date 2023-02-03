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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class ListaEsitiEstrazioneFilesDaZip {

        private Vector vct = new Vector();

        public ListaEsitiEstrazioneFilesDaZip(){
                listaEsitiPerKO = new ArrayList();
                listaEsitiPerOK = new ArrayList();
                listaEsitiTotale = new ArrayList();
        }

        private ArrayList listaEsitiPerKO = null;

        private ArrayList listaEsitiPerOK = null;
        
        private ArrayList listaEsitiTotale = null;
        
        
	    public ArrayList getListaEsitiTotale() {
	            return listaEsitiTotale;
	    }
	
	    public void addEsitoTotale(EsitoEstrazioneFilesDaZip esito){
	            this.listaEsitiTotale.add(esito);
	    }
        

        public ArrayList getListaEsitiPerKO() {
                return listaEsitiPerKO;
        }

        public ArrayList getListaEsitiPerOK() {
                return listaEsitiPerOK;
        }

        public void addEsitoPerKO(EsitoEstrazioneFilesDaZip esito){
                this.listaEsitiPerKO.add(esito);
        }
        
        

        public void addEsitoPerOK(EsitoEstrazioneFilesDaZip esito){
                this.listaEsitiPerOK.add(esito);
                vct.add(esito.getContenutoDelFileInFormatoXML());
        }

        public String[] getArrayXMLDaConvertireTramiteSpicom(){
                return (String[])vct.toArray(new String[vct.size()]);
        }

        public Vector getListaXMLDaConvertireTramiteSpicom() {
        	return vct;
        }
        public boolean hasKO()
        {
            return !listaEsitiPerKO.isEmpty();
        }

}
