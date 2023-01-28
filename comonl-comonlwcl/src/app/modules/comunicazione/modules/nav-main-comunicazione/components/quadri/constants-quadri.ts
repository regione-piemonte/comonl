/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { SediLavoratoriVardatoriComponent } from './../../../../components/sedi-lavoratori-vardatori/sedi-lavoratori-vardatori.component';
import { VariazioneDatoreLavoroComponent } from './../../../../components/variazione-datore-lavoro/variazione-datore-lavoro.component';
import { VariazioneRagioneSocialeComponent } from './../../../../components/variazione-ragione-sociale/variazione-ragione-sociale.component';
import { DatiGeneraliVardatoriComponent } from './../../../../components/dati-generali-vardatori/dati-generali-vardatori.component';
import { AdQuadro } from '../../models/ad-quadro';
import { AziendaUtilizzatriceComponent } from './azienda-utilizzatrice/azienda-utilizzatrice.component';
import { DatiAziendaComponent } from './dati-azienda/dati-azienda.component';
import { DatiGeneraliUrgComponent } from './dati-generali-urg/dati-generali-urg.component';
import { DatiGeneraliComponent } from './dati-generali/dati-generali.component';
import { DatiMissioneComponent } from './dati-missione/dati-missione.component';
import { DatiRapportoComponent } from './dati-rapporto/dati-rapporto.component';
import { LavoratoreComponent } from './lavoratore/lavoratore.component';
import { RiepilogoUrgComponent } from './riepilogo-urg/riepilogo-urg.component';
import { RiepilogoComponent } from './riepilogo/riepilogo.component';
import { TutoreComponent } from './tutore/tutore.component';
import { DatiCessazioneComponent } from './dati-cessazione/dati-cessazione.component';
import { DatiTrasformazioneComponent } from './dati-trasformazione/dati-trasformazione.component';
import { TrasferimentoDistaccoComponent } from './trasferimento-distacco/trasferimento-distacco.component';
import { ProrogaComponent } from './proroga/proroga.component';

export const QUADRO_COMPONENT = {
    DATI_GENERALI: new AdQuadro(DatiGeneraliComponent, { title: 'Dati generali'}),
    IMPRESA:  new AdQuadro(DatiAziendaComponent, { title: 'Dati Azienda'}),
    AZIENDA_UTILIZZATRICE: new AdQuadro(AziendaUtilizzatriceComponent, { title: 'Azienda utilizzatrice'}),
    LAVORATORE: new AdQuadro(LavoratoreComponent, { title: 'Lavoratore'}),
    RAPPORTO:  new AdQuadro(DatiRapportoComponent, { title: 'Dati rapporto'}),
    PROROGA:  new AdQuadro(ProrogaComponent, { title: 'Proroga'}),
    TUTORE: new AdQuadro(TutoreComponent, { title: 'Tutore', flgTutore: true}),
    CESSAZIONE: new AdQuadro(DatiCessazioneComponent, { title: 'Dati Cessazione'}),
    DATI_TRASFORMAZIONE: new AdQuadro(DatiTrasformazioneComponent, { title: 'Dati Trasformazione'}),
    DATI_TRASFERIMENTO_DISTACCO: new AdQuadro(TrasferimentoDistaccoComponent, { title: 'Trasferimento / Distacco'}),
    DATI_MISSIONE: new AdQuadro(DatiMissioneComponent, { title: 'Dati Missione'}),
    RIEPILOGO: new AdQuadro(RiepilogoComponent, { title: 'Riepilogo'}),
    DATI_GENERALI_URG: new AdQuadro(DatiGeneraliUrgComponent, {title: 'Dati generali'}),
    RIEPILOGO_URG: new AdQuadro(RiepilogoUrgComponent, {title: 'Riepilogo'}),
    DATI_GENERALI_VARDATORI: new AdQuadro(DatiGeneraliVardatoriComponent, {title: 'Dati generali'}),
    VAR_RAGIONE_SOCIALE: new AdQuadro(VariazioneRagioneSocialeComponent, {title: 'Variazione ragione sociale'}),
    VAR_DATORE_LAVORO: new AdQuadro(VariazioneDatoreLavoroComponent, {title: 'Dati Azienda precedente'}),
    VAR_SEDI_LAVORATORI: new AdQuadro(SediLavoratoriVardatoriComponent, {title: 'Sedi e lavoratori'}),
};

export const QUADRI_ASSUNZIONE_AND_SOMM: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_ASSUNZIONE_AND_SOMM_CON_TUTORE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.TUTORE,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_CESSAZIONE_AND_CESSAZIONE_RAPP_LAV: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.CESSAZIONE,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_CESSAZIONE_AND_CESSAZIONE_RAPP_LAV_CON_TUTORE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.TUTORE,
    QUADRO_COMPONENT.CESSAZIONE,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_CESSAZIONE_MISSIONE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.AZIENDA_UTILIZZATRICE,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.DATI_MISSIONE,
    QUADRO_COMPONENT.CESSAZIONE,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_CESSAZIONE_MISSIONE_CON_TUTORE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.AZIENDA_UTILIZZATRICE,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.TUTORE,
    QUADRO_COMPONENT.DATI_MISSIONE,
    QUADRO_COMPONENT.CESSAZIONE,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_TRASFERIMENTO_DISTACCO_AND_TRASF_ALTRA_SEDE_OP_AG: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.DATI_TRASFERIMENTO_DISTACCO,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_TRASFERIMENTO_DISTACCO_AND_TRASF_ALTRA_SEDE_OP_AG_CON_TUTORE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.TUTORE,
    QUADRO_COMPONENT.DATI_TRASFERIMENTO_DISTACCO,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_TRASFERIMENTO_ALTRA_SEDE_LAVORO_DITTA_UTILIZZATRICE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.AZIENDA_UTILIZZATRICE,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.DATI_MISSIONE,
    QUADRO_COMPONENT.DATI_TRASFERIMENTO_DISTACCO,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_TRASFERIMENTO_ALTRA_SEDE_LAVORO_DITTA_UTILIZZATRICE_CON_TUTORE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.AZIENDA_UTILIZZATRICE,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.TUTORE,
    QUADRO_COMPONENT.DATI_MISSIONE,
    QUADRO_COMPONENT.DATI_TRASFERIMENTO_DISTACCO,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_TRASFORMAZIONE_IN_COSTANZA_DI_MISSIONE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.AZIENDA_UTILIZZATRICE,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.DATI_MISSIONE,
    QUADRO_COMPONENT.DATI_TRASFORMAZIONE,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_TRASFORMAZIONE_IN_COSTANZA_DI_MISSIONE_CON_TUTORE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.AZIENDA_UTILIZZATRICE,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.TUTORE,
    QUADRO_COMPONENT.DATI_MISSIONE,
    QUADRO_COMPONENT.DATI_TRASFORMAZIONE,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_PROROGA_MISSIONE_RAPPORTO_INDETERMINATO_AND_LAVORO_MISSIONE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.AZIENDA_UTILIZZATRICE,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.DATI_MISSIONE,
    QUADRO_COMPONENT.PROROGA,
    QUADRO_COMPONENT.RIEPILOGO
];


export const QUADRI_PROROGA_MISSIONE_RAPPORTO_INDETERMINATO_AND_LAVORO_MISSIONE_CON_TUTORE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.AZIENDA_UTILIZZATRICE,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.TUTORE,
    QUADRO_COMPONENT.DATI_MISSIONE,
    QUADRO_COMPONENT.PROROGA,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_PROROGA_AND_PROROGA_LAVORO_ASSENZA_DI_MISSIONE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.PROROGA,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_PROROGA_AND_PROROGA_LAVORO_ASSENZA_DI_MISSIONE_CON_TUTORE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.TUTORE,
    QUADRO_COMPONENT.PROROGA,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_SOMM_E_MISSIONE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.AZIENDA_UTILIZZATRICE,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.DATI_MISSIONE,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_SOMM_E_MISSIONE_CON_TUTORE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.AZIENDA_UTILIZZATRICE,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.TUTORE,
    QUADRO_COMPONENT.DATI_MISSIONE,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_URG_SI: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI_URG,
    QUADRO_COMPONENT.RIEPILOGO_URG
];

export const QUADRI_VARIAZIONE_DATORE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI
];

export const QUADRI_TRASFORMAZIONE_AND_TRASFORMAZIONE_RAPPORTO_LAVORO_ASSENZA_MISSIONE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.DATI_TRASFORMAZIONE,
    QUADRO_COMPONENT.RIEPILOGO
];


export const QUADRI_TRASFORMAZIONE_AND_TRASFORMAZIONE_RAPPORTO_LAVORO_ASSENZA_MISSIONE_CON_TUTORE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.LAVORATORE,
    QUADRO_COMPONENT.RAPPORTO,
    QUADRO_COMPONENT.TUTORE,
    QUADRO_COMPONENT.DATI_TRASFORMAZIONE,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_VARIAZIONE_DATORE_RAG_SOCIALE: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI_VARDATORI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.VAR_RAGIONE_SOCIALE,
    QUADRO_COMPONENT.RIEPILOGO
];

export const QUADRI_VARIAZIONE_DATORE_TRASF: AdQuadro[] = [
    QUADRO_COMPONENT.DATI_GENERALI_VARDATORI,
    QUADRO_COMPONENT.IMPRESA,
    QUADRO_COMPONENT.VAR_DATORE_LAVORO,
    QUADRO_COMPONENT.VAR_SEDI_LAVORATORI,
    QUADRO_COMPONENT.RIEPILOGO
];
