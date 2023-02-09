/* -------------------------------------------------------------------- */
/* Copyright Regione Piemonte - 2022									*/
/* SPDX-License-Identifier: EUPL-1.2-or-later                           */
/* Target DBMS:           Oracle 19c                                    */
/* Project name:          COMONL                                        */
/* Author:                CSI Piemonte                                  */
/* Script type:           Database creation script                      */
/* Created on:            2022-11-23		                            */
/* -------------------------------------------------------------------- */

CREATE OR REPLACE VIEW COM_V_RICERCA_COMUNICAZIONE
(id_com_d_datore, codice_fiscale_datore, ds_denominazione_datore, id_com_d_datore_prec, codice_fiscale_datore_prec, ds_denominazione_datore_prec, id_com_d_lavoratore, codice_fiscale_lavoratore, cognome_lavoratore, nome_lavoratore, id_com_d_comunicazione, dt_invio, dt_insert, id_com_t_stato_comunicazione, codice_comunicazione_reg, id_com_t_tipo_comunicazione, id_com_t_tipo_comunicazione_tu, codice_fiscale_soggetto, id_com_t_provincia, anno_prot_com, num_prot_com, dt_inizio_rapporto, dt_evento, ds_com_t_tipo_comunicazione, ds_com_t_stato_comunicazione, id_tipo_tracciato, id_com_t_variazione_somm, id_com_t_tipo_somministrazione, id_com_t_tipo_trasferimento, flg_current_record)
AS
SELECT DISTINCT d.ID_COM_D_DATORE,d.CODICE_FISCALE,d.DS_DENOMINAZIONE_DATORE,
	d2.ID_COM_D_DATORE,d2.codice_fiscale,d2.ds_denominazione_datore,
	--vm_concat(l.ID_COM_D_LAVORATORE) as id_lav2,
	decode(c.flg_multi_lav,'S', null, l.ID_COM_D_LAVORATORE) id_lav,
	decode(c.flg_multi_lav,'S', 'Multi-lavoratore', l.CODICE_FISCALE) cf_lav ,
	decode(c.flg_multi_lav,'S', '-',l.COGNOME) cognome_lav,
	decode(c.flg_multi_lav,'S', '-',l.NOME) nome_lav,
	c.ID_COM_D_COMUNICAZIONE,c.DT_INVIO,c.DT_INSERT,
	c.ID_COM_T_STATO_COMUNICAZIONE, c.CODICE_COMUNICAZIONE_REG,
	c.ID_COM_T_TIPO_COMUNICAZIONE,c.ID_COM_T_TIPO_COMUNICAZIONE_TU,c.CODICE_FISCALE_SOGGETTO,
	c.ID_COM_T_PROVINCIA, c.ANNO_PROT_COM, c.NUM_PROT_COM, r.DT_INIZIO_RAPPORTO,
	r.DT_EVENTO,cttc.DS_COM_T_TIPO_COMUNICAZIONE, ctsc.DS_COM_T_STATO_COMUNICAZIONE,
	c.ID_COM_T_TIPO_TRACCIATO,
	vsomm.ID_COM_T_VARIAZIONE_SOMM,
	c.ID_COM_T_TIPO_SOMMINISTRAZIONE, c.ID_COM_T_TIPO_TRASFERIMENTO, c.FLG_CURRENT_RECORD
    FROM COM_D_COMUNICAZIONE c
        join COM_R_COMUNICAZIONE_DATORE cd on cd.ID_COM_D_COMUNICAZIONE = c.ID_COM_D_COMUNICAZIONE
                                          and cd.ID_COM_T_TIPO_DATORE = 'A'
        join COM_D_DATORE d on d.ID_COM_D_DATORE = cd.ID_COM_D_DATORE
        join COM_D_RAPPORTO r on r.ID_COM_D_COMUNICAZIONE = c.ID_COM_D_COMUNICAZIONE
                             and r.dt_inizio_missione IS NULL
        join COM_R_RAPPORTO_LAVORATORE rl on rl.ID_COM_D_RAPPORTO = r.ID_COM_D_RAPPORTO
        join COM_D_LAVORATORE l on l.ID_COM_D_LAVORATORE = rl.ID_COM_D_LAVORATORE
        join COM_T_TIPO_COMUNICAZIONE cttc on cttc.ID_COM_T_TIPO_COMUNICAZIONE = c.ID_COM_T_TIPO_COMUNICAZIONE
        join COM_T_STATO_COMUNICAZIONE ctsc on ctsc.ID_COM_T_STATO_COMUNICAZIONE = c.ID_COM_T_STATO_COMUNICAZIONE
        left join COM_R_COMUNICAZIONE_DATORE rd2 on rd2.id_com_d_comunicazione = c.id_com_d_comunicazione
                                                and rd2.id_com_t_tipo_datore = 'P'
        left join com_d_datore d2 on d2.id_com_d_datore = rd2.id_com_d_Datore
        LEFT OUTER JOIN COM_T_VARIAZIONE_SOMM vsomm ON vsomm.ID_COM_T_TIPO_SOMMINISTRAZIONE = c.ID_COM_T_TIPO_SOMMINISTRAZIONE AND vsomm.ID_COM_T_TIPO_COMUNICAZIONE = c.ID_COM_T_TIPO_COMUNICAZIONE;

CREATE OR REPLACE VIEW COM_V_RICERCA_VARDATORI
(id_com_d_datore, codice_fiscale_datore, ds_denominazione_datore, id_com_d_datore_prec, codice_fiscale_datore_prec, ds_denominazione_datore_prec, id_com_d_lavoratore, codice_fiscale_lavoratore, cognome_lavoratore, nome_lavoratore, id_com_d_comunicazione, dt_invio, dt_insert, id_com_t_stato_comunicazione, codice_comunicazione_reg, id_com_t_tipo_comunicazione, id_com_t_tipo_comunicazione_tu, codice_fiscale_soggetto, id_com_t_provincia, anno_prot_com, num_prot_com, dt_inizio_rapporto, dt_evento, ds_com_t_tipo_comunicazione, ds_com_t_stato_comunicazione, id_tipo_tracciato, id_com_t_tipo_trasferimento, dt_trasferimento)
AS
SELECT DISTINCT d.ID_COM_D_DATORE,d.CODICE_FISCALE,d.DS_DENOMINAZIONE_DATORE,
  d2.ID_COM_D_DATORE,d2.codice_fiscale,d2.ds_denominazione_datore,
  decode(c.flg_multi_lav,'S', null, l.ID_COM_D_LAVORATORE) id_lav,
  decode(c.flg_multi_lav,'S', 'Multi-lavoratore', l.CODICE_FISCALE) cf_lav ,
  decode(c.flg_multi_lav,'S', '-',l.COGNOME) cognome_lav,
  decode(c.flg_multi_lav,'S', '-',l.NOME) nome_lav,
  c.ID_COM_D_COMUNICAZIONE,
  c.DT_INVIO,
  c.DT_INSERT,
  c.ID_COM_T_STATO_COMUNICAZIONE, c.CODICE_COMUNICAZIONE_REG,
  c.ID_COM_T_TIPO_COMUNICAZIONE,c.ID_COM_T_TIPO_COMUNICAZIONE_TU,c.CODICE_FISCALE_SOGGETTO,
  c.ID_COM_T_PROVINCIA, c.ANNO_PROT_COM, c.NUM_PROT_COM, r.DT_INIZIO_RAPPORTO,
  r.DT_EVENTO,cttc.DS_COM_T_TIPO_COMUNICAZIONE, ctsc.DS_COM_T_STATO_COMUNICAZIONE,
  c.ID_COM_T_TIPO_TRACCIATO,
  c.ID_COM_T_TIPO_TRASFERIMENTO,
  c.dt_trasferimento_var_datori
    FROM COM_D_COMUNICAZIONE c
        join COM_R_COMUNICAZIONE_DATORE cd on cd.ID_COM_D_COMUNICAZIONE = c.ID_COM_D_COMUNICAZIONE and cd.ID_COM_T_TIPO_DATORE = 'A'
        join COM_D_DATORE d on d.ID_COM_D_DATORE = cd.ID_COM_D_DATORE
        LEFT join COM_D_RAPPORTO r on r.ID_COM_D_COMUNICAZIONE = c.ID_COM_D_COMUNICAZIONE and r.dt_inizio_missione IS NULL
        LEFT join COM_R_RAPPORTO_LAVORATORE rl on rl.ID_COM_D_RAPPORTO = r.ID_COM_D_RAPPORTO
        LEFT join COM_D_LAVORATORE l on l.ID_COM_D_LAVORATORE = rl.ID_COM_D_LAVORATORE
        join COM_T_TIPO_COMUNICAZIONE cttc on cttc.ID_COM_T_TIPO_COMUNICAZIONE = c.ID_COM_T_TIPO_COMUNICAZIONE
        join COM_T_STATO_COMUNICAZIONE ctsc on ctsc.ID_COM_T_STATO_COMUNICAZIONE = c.ID_COM_T_STATO_COMUNICAZIONE
        left join COM_R_COMUNICAZIONE_DATORE rd2 on rd2.id_com_d_comunicazione = c.id_com_d_comunicazione and rd2.id_com_t_tipo_datore = 'P'
        left join com_d_datore d2 on d2.id_com_d_datore = rd2.id_com_d_Datore;

CREATE OR REPLACE VIEW V_CONSULENTE_LAVORO AS
SELECT CF_DELEGATO,PV
FROM ANAGRAFICA_DELEGATO WHERE TIPO_ANAGRAFICA ='C';

create or replace view v_delega_valida as
(
select "ID_DELEGA","PV_COMPETENZA","C_TIPO_DELEGA","C_STATO","CARICA","N_PROTOCOLLO","DT_PROTOCOLLO","CF_DELEGATO","COGNOME_DELEGATO","NOME_DELEGATO","INDIRIZZO_DELEGATO","C_COM_DELEGATO","CAP_DELEGATO","TELEFONO_DELEGATO","MAIL_DELEGATO","CF_DELEGANTE","PIVA_DELEGATO","COGNOME_DELEGANTE","NOME_DELEGANTE","INDIRIZZO_DELEGANTE","C_COM_DELEGANTE","CAP_DELEGANTE","TELEFONO_DELEGANTE","MAIL_DELEGANTE","DENOMINAZIONE_IMPRESA","CF_IMPRESA","PIVA_IMPRESA","DT_REVOCA","ID_USER_INSERT","DT_INSERT","ID_USER_ULT_MOD","DT_ULT_MOD" from delega
where C_STATO = 3 AND C_TIPO_DELEGA='1'
);

