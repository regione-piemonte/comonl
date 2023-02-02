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
package it.csi.comonl.comonlweb.ejb.util.commax.dto;


import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepCommaxComunic;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepositoCommax;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;

public class RaggruppamentoDati {
	
	DepCommaxComunic   	  	comRDepCommaxComunic;
    DepositoCommax 			depositoCommax   ;
    ComunicazioneTracciatoUnicoDTO	comunicazioneTracciatoUnicoDTO;


     public DepCommaxComunic getComRDepCommaxComunic() {
		return comRDepCommaxComunic;
	}
	public void setComRDepCommaxComunic(DepCommaxComunic comRDepCommaxComunic) {
		this.comRDepCommaxComunic = comRDepCommaxComunic;
	}
	public DepositoCommax getDepositoCommax() {
		return depositoCommax;
	}
	public void setDepositoCommax(DepositoCommax depositoCommax) {
		this.depositoCommax = depositoCommax;
	}
	public ComunicazioneTracciatoUnicoDTO getComunicazioneTracciatoUnicoDTO() {
             return comunicazioneTracciatoUnicoDTO;
     }
     public void setComunicazioneTracciatoUnicoDTO(ComunicazioneTracciatoUnicoDTO comunicazioneTracciatoUnicoDTO) {
             this.comunicazioneTracciatoUnicoDTO = comunicazioneTracciatoUnicoDTO;
     }

     
     public static RaggruppamentoDati[] creaRaggruppamentoDatiAPartireDaListeDiElementi(List<DepCommaxComunic> uplDoc, List<DepositoCommax> dep, ComunicazioneTracciatoUnicoDTO[] ctulist)
     {
                  // Controllo di sicurezza. Verifico che gli array elencoComunicazioniValide
                 // ed elencoDepositi commax abbiano gli stessi dati.
             if (!(uplDoc.size()==dep.size() && ctulist.length==dep.size()))
                     throw new UnsupportedOperationException("Attenzione non ho congruenza tra i dati. I tre array non sono di uguale lunghezza Qualcosa si � rotto");

             RaggruppamentoDati result[]=new RaggruppamentoDati[dep.size()];
             for (int z=0;z<dep.size();z++)
             {
                     RaggruppamentoDati r=new RaggruppamentoDati();
                     r.setComRDepCommaxComunic(uplDoc.get(z));
                     r.setComunicazioneTracciatoUnicoDTO(ctulist[z]);
                     r.setDepositoCommax(dep.get(z));
                     result[z]=r;
             }


             // In teoria la query da gli stessi dati, ma per essere sicuri
             // che in futuro qualcuno mettendo mano non rompa niente.

             return result;
     }
}
