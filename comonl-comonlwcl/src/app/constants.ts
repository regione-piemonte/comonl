/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { AnagraficaDelegato } from './modules/comonlapi';

export const CONTROL_STATE = {
    DISABLE: 'disable',
    ENABLE: 'enable'
};

export const CONSTANTS_MODE = {
    VIEW: 'view',
    EDIT: 'edit',
    INS: 'ins',
    RETTIFICA: 'rettifica',
    ANNULLO: 'annullo',
    AGGIORNAMENTO: 'aggiornamento'
};

export const DEFAULT_CODICE_REGIONALE = '1300000000000000';

export const MESI = { A: '01', B: '02', C: '03', D: '04', E: '05', H: '06', L: '07', M: '08', P: '09', R: '10', S: '11', T: '12' };


export const TYPE_DECODIFICA_GENERICA = {
    ATECO: 'ATE',
    CCNL: 'CCNL',
    COMUNE: 'COM',
    QUAL_ISTAT: 'QUAL_ISTAT',
    STATI_ESTERI: 'STATI_ES',
    LIV_RETR: 'LIV_RETR'
};

export const PARAMETRO_COMONL_ABILITATO = 'comonl.web.abilitato';
export const PARAMETRO_COMONL_INTESTAZINE_FORMALE_DS = 'Comunicazione.intestazioneComunicazioneFormale';
export const PARAMETRO_COMONL_TESTO_FORMALE_DS = 'Comunicazione.testoComunicazioneFormale';
export const PARAMETRO_COMONL_INTESTAZIONE_ACCETTA = 'Comunicazione.intestazioneAccettazione';
export const PARAMETRO_COMONL_TESTO_ACCETTAZZIONE = 'Comunicazione.testoAccettazione';
export const PARAMETRO_COMONL_ABILITAZIONE_LAVORO_STAGIONALE = 'Vincoli.ListaTipiContratto.Flg_stg';

export const REGIONE = {
    PIEMONTE: {
        COD_MIN: '13',
        DS: 'PIEMONTE'
    }
};

export const TIPO_SOMMINISTRAZIONE = {
    SOMMINISTRAZIONE: {
        ID: 0,
        DS: 'SOMMINISTRAZIONE'
    },
    MISSIONE: {
        ID: 1,
        DS: 'MISSIONE'
    },
    SOMMINISTRAZIONE_E_MISSIONE: {
        ID: 2,
        DS: 'SOMMINISTRAZIONE E MISSIONE'
    }
};
export const TIPO_PROVENIENZA = {
    COMONL_VER_4:{
        ID: 3,
        DS: "COMONL VER 4",
        COD: "GE"
    }
}
export const TIPO_LAVORATORE = {
    PRINCIPALE: {
        ID: "P",
        DS: "PRINCIPALE"
    },
    CO_OBBLIGATO:{
        ID: "C",
        DS: "CO-OBBLIGATO"
    }
}
export const TIPO_ACQUISIZIONE = {
    comunicazione_completa: {
        id: 1,
        DS: "Comunicazione completa"
    }
}
export const TIPO_TRACCIATO = {
    UNIDOM: {
        ID: 'UD',
        DS: 'UNIDOM'
    },
    UNILAV: {
        ID: 'UL',
        DS: 'UNILAV'
    },
    UNISOMM: {
        ID: 'US',
        DS: 'UNISOMM'
    },
    VARDATORI: {
        ID: 'VD',
        DS: 'VARDATORI'
    },
    URGENZA: {
        ID: 'UG',
        DS: 'URGENZA'
    }
};


export const CITTADINANZA = {
    ITALIA: {
        ID: 13,
        COD_MF: '0000',
        DS_NAZIONE: 'ITALIA',
        DS_CITTADINANZA: 'ITALIANA',
        FLG_UE: 'S'
    }
};

export const TIPO_COMUNICAZIONE_TU = {
    COD_MIN_01: '01',
    COD_MIN_02: '02',
    COD_MIN_03: '03',
    COD_MIN_04: '04',
    COD_MIN_05: '05',
    INSERIMENTO_UFFICIO: {
        ID: 6,
        COD_MIN: '06',
    },
    COD_MIN_07: '07',
    COD_MIN_08: '08',
    COD_MIN_09: '09'
};
export const TIPO_COMUNICAZIONE_TU_ID_RETTIFICA = 3;
export const TIPO_COMUNICAZIONE_TU_ID_ANNULLAMENTO = 4;
export const TIPO_COMUNICAZIONE = {
    ASSUNZIONE: {
        ID: 'ASS',
        DS: 'ASSUNZIONE'
    },
    CESSAZIONE: {
        ID: 'CES',
        DS: 'CESSAZIONE'
    },
    TRASFORMAZIONE: {
        ID: 'TRS',
        DS: 'TRASFORMAZIONE'
    },
    URGENZA: {
        ID: 'URG',
        DS: 'URGENZA'
    },
    TRASFERIMENTO_DISTACCO: {
        ID: 'TRD',
        DS: 'TRASFERIMENTO / DISTACCO'
    },
    PROROGA: {
        ID: 'PRO',
        DS: 'PROROGA'
    },
    VARIAZIONE_DATORE: {
        ID: 'VAR',
        DS: 'VARIAZIONE DATORE'
    }
};
export const STATO_COMUNICAZIONE = {
    DA_FIRMARE: {
        id: 6,
        DS: 'DA FIRMARE'
    },
    INSERITA: {
        ID: 1,
        DS: 'INSERITA'
    },
    CONTROLLATA: {
        ID: 2,
        DS: 'CONTROLLATA'
    },
    VALIDATA: {
        ID: 3,
        DS: 'VALIDATA'
    },
    RETTIFICATA: {
        ID: 9,
        DS: 'RETTIFICATA'
    },
    ANNULLATA: {
        ID: 4,
        DS: 'CONTROLLATA'
    },
    CANCELLATA: {
        ID: 5,
        DS: 'CANCELLATA'
    },
    TRANSITO: {
        ID: 7,
        DS: 'TRANSITO'
    }

};
export const TIPO_CONTRATTI = {
    TIROCINIO_ESTIVO_ORIENTAMENTO: {
        ID: 22,
        DS: 'TIROCINIO ESTIVO DI ORIENTAMENTO',
        COD_TIPO_CONTARTTO_MIN: 'C.02.00'
    },
    APPR_PROF_CONTR_MESTIERE_LAV_STAGIONALI: {
        ID: 43,
        DS: 'APPR. PROF. O CONTR. DI MESTIERE PER LAV. STAGIONALI',
        COD_TIPO_CONTARTTO_MIN: 'A.03.14'
    },
    APPR_ALTA_FORMAZIONE_RICERCA_LAVORATORI_MOBILITA: {
        ID: 42,
        DS: 'APPR. DI ALTA FORMAZIONE E RICERCA PER LAVORATORI IN MOBILITA\'',
        COD_TIPO_CONTARTTO_MIN: 'APPR. DI ALTA FORMAZIONE E RICERCA PER LAVORATORI IN MOBILITA\''
    },
    APPR_PROF_CONTR_MESTIERE_LAV_MOBILITA: {
        ID: 41,
        DS: 'APPR. PROF. O CONTR. DI MESTIERE PER LAV. IN MOBILITA\'',
        COD_TIPO_CONTARTTO_MIN: 'A.03.12'
    },
    APPR_QUAL_PROF_DIPLOMA_PROF_LAV_MOBILITA: {
        ID: 40,
        DS: 'APPR. PER LA QUAL. PROF. E PER IL DIPLOMA PROF. PER LAV. IN MOBILITA\'',
        COD_TIPO_CONTARTTO_MIN: 'A.03.11'
    },
    APPR_ACQUISIZIONE_DIPLOMA_PERCORSI_ALTA_FORMAZIONE: {
        ID: 7,
        DS: 'APPR. PER L\'ACQUISIZIONE DI DIPLOMA O PER PERCORSI DI ALTA FORMAZIONE',
        COD_TIPO_CONTARTTO_MIN: 'A.03.03'
    },
    APPR_PROFESSIONALIZZANTE: {
        ID: 6,
        DS: 'APPR. PROFESSIONALIZZANTE',
        COD_TIPO_CONTARTTO_MIN: 'A.03.02'
    },
    APPR_ESPLETAMENTO_DIRITTO_DOVERE_ISTRUZIONE_FORMAZIONE: {
        ID: 5,
        DS: 'APPR. PER L\'ESPLETAMENTO DEL DIRITTO DOVERE DI ISTRUZIONE FORMAZIONE',
        COD_TIPO_CONTARTTO_MIN: 'A.03.01'
    },
    APPR_EX_ART16_196_97: {
        ID: 4,
        DS: 'APPR. EX ART.16 L. 196/97',
        COD_TIPO_CONTARTTO_MIN: 'A.03.00'
    },
    TIROCINIO: {
        ID: 21,
        DS: 'TIROCINIO',
        COD_TIPO_CONTARTTO_MIN: 'C.01.00'
    },
    LAVORO_RIPARTITO: {
        ID: 45,
        DS: 'LAVORO RIPARTITO',
        COD_TIPO_CONTARTTO_MIN: 'A.07.02'
    },
    LAVORO_T_DETERMINATO_PIATTAFORMA: {
        ID: 52,
        DS: 'LAVORO A TEMPO DETERMINATO CON PIATTAFORMA',
        COD_TIPO_CONTARTTO_MIN: 'N.02.00'
    },
    LAVORO_T_DETERMINATO_SOSTITUZIONE_PIATTAFORMA: {
        ID: 53,
        DS: 'LAVORO A TEMPO DETERMINATO PER SOSTITUZIONE CON PIATTAFORMA',
        COD_TIPO_CONTARTTO_MIN: 'N.03.00'
    },
    T_DETERMINATO: {
        ID: 5,
        DS: 'TEMPO DETERMINATO',
        COD_TIPO_CONTARTTO_MIN: 'A.02.00'
    },
    APPR_QUAL_DIPLOMA_PROF_DIPLOMA_ISTR_SECONDARIA_SUPERIORE_CERT_SPECIALIZ_TECNICA_SUPERIORE: {
        ID: 37,
        DS: 'APPR. PER QUAL. E DIPLOMA PROF., DIPLOMA ISTR. SECONDARIA SUPERIORE, CERT. SPECIALIZ. TECNICA SUPERIORE',
        COD_TIPO_CONTARTTO_MIN: 'A.03.08'
    },
    APPR_PROF_CONTRATTO_MESTIERE: {
        ID: 38,
        DS: 'APPR. PROF. O CONTRATTO DI MESTIERE',
        COD_TIPO_CONTARTTO_MIN: 'A.03.09'
    },
    APPR_ALTA_FORMAZIONE_RICERCA: {
        ID: 39,
        DS: 'APPR. DI ALTA FORMAZIONE E RICERCA',
        COD_TIPO_CONTARTTO_MIN: 'A.03.10'
    }
};

export const TIPO_ORARIO = {
    TEMPO_PARZIALE_ORIZZONTALE: {
        ID: 2,
        DS: 'TEMPO PARZIALE ORIZZONTALE',
        COD_TIPO_ORARIO_MIN: 'P'
    },
    TEMPO_PARZIALE_VERTICALE: {
        ID: 3,
        DS: 'TEMPO PARZIALE VERTICALE',
        COD_TIPO_ORARIO_MIN: 'V'
    },
    TEMPO_PARZIALE_MISTO: {
        ID: 4,
        DS: 'TEMPO PARZIALE MISTO',
        COD_TIPO_ORARIO_MIN: 'M'
    },
    TEMPO_PIENO: {
        ID: 1,
        DS: 'TEMPO PIENO',
        COD_TIPO_ORARIO_MIN: 'F'
    }
};

export const TYPE_ALERT = {
    SUCCESS: 'S',
    DANGER: 'D',
    WARNING: 'W',
    INFO: 'I'
};

export const ANAGRAFICA_DELEGATO = {
    TIPO: {
        CONSULENTE: 'C',
        DELEGATO: 'D',
        PERSONA_FISICA: 'E'
    }
};

export const UTILITY = {
    FORMAT_FILE: {
        PDF: 'pdf',
        XSL: 'xsl'
    }
};

export const CATEG_LAV_ASS_OBBL = {
    PERSONA_CON_DISABILITA_NOMINATIVA: {
        ID: 1,
        COD_MIN: 'PD',
    },
    CATEGORIA_PROTETTA: {
        ID: 2,
        COD_MIN: 'CP'
    },
    PERSONA_CON_DISABILITA_NUMERICA: {
        ID: 1,
        COD_MIN: 'PD',
    }
};

export const STATUS_STRANIERO = {
    IN_ATTESA_PERMESSO: {
        ID: 4,
        COD_MIN: '4',
        DS: 'IN ATTESA DI PERMESSO'
    },
    IN_RINNOVO: {
        ID: 3,
        COD_MIN: '3',
        DS: 'IN RINNOVO'
    },
    ALTRO_PROVVEDIMENTO: {
        ID: 6,
        COD_MIN: '6',
        DS: 'ALTRO PROVVEDIMENTO'
    }


    
};


export const DAY_OF_YEAR = 6;



