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
package it.csi.comonl.comonlweb.ejb.util.commax.vo;

import java.math.*;
import java.util.*;

public class ProtocolloVO {
        private int annoProtocollo;
        private BigDecimal numeroProtocollo;
        private String tipoProtocollo;
        private Date dataProtocollo; 
        private String pv;
        private long progressivo;
        private boolean extraPiemonte = false;

    public int getAnnoProtocollo()
        {
                return annoProtocollo;
        }
        public BigDecimal getNumeroProtocollo()
        {
                return numeroProtocollo;
        }

        public String getNumeroProtocolloString()
        {
            if (extraPiemonte)
                return "-";

            return numeroProtocollo == null ? "" : numeroProtocollo.toString();
        }


        public String getTipoProtocollo()
        {
                return tipoProtocollo;
        }
        public Date getDataProtocollo() {
			return dataProtocollo;
		}
		public void setDataProtocollo(Date dataProtocollo) {
			this.dataProtocollo = dataProtocollo;
		}
		public String getPv()
        {
                return pv;
        }
        public long getProgressivo()
        {
                return progressivo;
        }

    public boolean isExtraPiemonte()
    {
        return extraPiemonte;
    }

    public void setAnnoProtocollo(int annoProtocollo)
        {
                this.annoProtocollo=annoProtocollo;
        }
        public void setNumeroProtocollo(BigDecimal numeroProtocollo)
        {
                this.numeroProtocollo=numeroProtocollo;
        }
        public void setTipoProtocollo(String tipoProtocollo)
        {
                this.tipoProtocollo=tipoProtocollo;
        }
        
        public void setPv(String pv)
        {
                this.pv=pv;
        }
        public void setProgressivo(long progressivo) {
                this.progressivo=progressivo;
        }




        public String toString() {
                String risultato = "[MiniComunicazioneVO= ";
                risultato = "annoProtocollo="+this.annoProtocollo+"| ";
                risultato += "numeroProtocollo="+this.numeroProtocollo+"| ";
                risultato += "tipoProtocollo="+this.tipoProtocollo+"| ";
                risultato += "dataProtocollo="+this.dataProtocollo+"| ";
                risultato += "pv="+this.pv+"| ";
                risultato += "progressivo="+this.progressivo+"| ]";
                return risultato;
        }//toString

        public void setExtraPiemonte(boolean b)
        {
            this.extraPiemonte = b;
        }

}//classe
