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
package it.csi.comonl.comonlweb.lib.dto.error;

/**
 * COMONL error types
 */
public enum MsgComonl implements ErrorCreator {

	// modulo-oggetto - Errore/Avviso/Prompt

	
	COMCFE0001("COM-CF-E-0001", "Attenzione il codice fiscale non è coerente con i dati anagrafici inseriti!"),
	
	
	COMCOMP0001("COM-COM-P-0001", "Aggiornamento/Inserimento del prospetto in bozza avvenuto correttamente"),
	COMCOMP0002("COM-COM-P-0002", "Nessun risultato trovato"),
	COMCOMP0003("COM-COM-P-0003", "Servizio Momentaneamente non disponibile."),
	COMCOMP0004("COM-COM-P-0004", "Silp service messagge errors."),

	COMCOME0001("COM-COM-E-0001", "Attenzione! Non e' stato possibile aggiornare il sistema."),
	COMCOME0002("COM-COM-E-0002", "Attenzione! idAzienda non indicato."),

	COMCOME0003("COM-COM-E-0003", "Attenzione! Il campo {{parameter}} puo' valere SI o NO"),
	COMCOME0006("COM-COM-E-0006",
			"Attenzione! Il campo {{parameter}} non puo' essere rettificato in quanto dato essenziale."),

	COMCOME0004("COM-COM-E-0004",
			"Attenzione! Le Comunicazioni trovate superano il massimo consentito. Inserire criteri di ricerca più restrittivi."),
	COMCOME0005("COM-COM-E-0005", "Attenzione: Codice fiscale non valido."),
	COMCOME0007("COM-COM-E-0007", "Attenzione: Uno tra il comune di nascita e lo stato estero è obbligatorio."),
	COMCOME0008("COM-COM-E-0008", "Attenzione: Codice fiscale Studio professionale già esistente"),
	COMCOME0009("COM-COM-E-0009", "Attenzione: Codice fiscale consulente non trovato"),
	COMCOME0010("COM-COM-E-0010",
			"Il Consulente ricercato è già associato a un altro studio professionale\n, si vuole procedere ad associarlo al nuovo studio?"),
	COMCOME0011("COM-COM-E-0011", "Il Consulente ricercato è già associato"),
	COMCOME0012("COM-COM-E-0012", "Anagrafica non presente a sistema"),
	COMCOME0013("COM-COM-E-0013", "Operazione non valida per questa Anagrafica"),
	COMCOME0014("COM-COM-E-0014", "Attenzione: Non e' possibile inserire sia il comune che lo stato estero."),
	COMCOME0015("COM-COM-E-0015",
			"E' possibile rettificare solo l'ultima comunicazione del rapporto di lavoro. L'ultima comunicazione e' {{tipoComunicazione}} del {{dtRiferimento}} ({{codiceRegionale}})"),
	COMCOME0016("COM-COM-E-0016",
			"E' possibile annullare solo l'ultima comunicazione del rapporto di lavoro. L'ultima comunicazione e' {{tipoComunicazione}} del {{dtRiferimento}} ({{codiceRegionale}})"),
	COMCOME0017("COM-COM-E-0017", "Non e' possibile cancellare una comunicazione in stato {{statoComunicazione}}"),
	COMCOME0018("COM-COM-E-0018",
			"E' possibile inserire una comunicazione di aggiornamento solo sull'ultima comunicazione del rapporto di lavoro in stato VALIDATA."),
	COMCOME0019("COM-COM-E-0019",
			"Attenzione! {{parameter}}: campo non valido."),
	COMCOME0020("COM-COM-E-0020",
			"Attenzione! Non è possibile modificare la data inizio rapporto, il codice fiscale azienda e il codice fiscale lavoratore in quanto si sta inserendo una comunicazione di aggiornamento sul rapporto di lavoro."),
	COMCOME0021("COM-COM-E-0021",
			"Attenzione! L'annullamento della comunicazione risulta oltre il termine massimo stabilito dalla vigente normativa. E' necessario procedere con una cessazione."),
	COMCOME0022("COM-COM-E-0022",
			"Attenzione! Il campo {{field}} contiene un valore non più valido: {{code}} - {{desc}}. E' necessario aggiornare il dato per proseguire."),
	COMCOME0023("COM-COM-E-0023",
			"Attenzione! I dati trovati superano il massimo consentito. Inserire criteri di ricerca più restrittivi."),

	COMCOME1120("COM-COM-E-1140", "Attenzione! Qualifica istat non trovata"),
	COMCOME1140("COM-COM-E-1140", "Attenzione! Inquadramento non trovato"),
	COMCOME1160("COM-COM-E-1160", "Attenzione! Ccnl non trovato"),
//	COMCOME0007 ("COM-COM-E-0007", "E' necessario valorizzare il comune per la sede legale."),
//	COMCOME0008 ("COM-COM-E-0008", "Attenzione! Dati inerenti il comune referente non congruenti."),
//	COMCOME0009 ("COM-COM-E-0009", "Attenzione! Dati inerenti il comune sede legale non congruenti."),
//	COMCOME0010 ("COM-COM-E-0010", "Attenzione! Comune referente scaduto."),
//	COMCOME0011 ("COM-COM-E-0011", "Attenzione! Comune sede legale scaduto."),
//	COMCOME0012 ("COM-COM-E-0012", "Il campo Ateco (codice e descrizione) e' obbligatorio."),
//	COMCOME0013 ("COM-COM-E-0013", "Attenzione! Dati inerenti l'Atecofin  non congruenti."),
//	COMCOME0014 ("COM-COM-E-0014", "Attenzione! Atecofin scaduto."),
//	COMCOME0015 ("COM-COM-E-0015", "Il campo CCNL (codice e descrizione) e' obbligatorio."),
//	COMCOME0016 ("COM-COM-E-0016", "Attenzione! Dati inerenti il CCNL non congruenti."),
//	COMCOME0017 ("COM-COM-E-0017", "Attenzione! CCNL scaduto."),
	COMCOMW002("COM-COM-W-0002", "Attenzione! Indirizzo e-mail non corretto."),
	COMCOMW005("COM-COM-W-0005",
			"Attenzione! Se non si effettua la comunicazione per il datore di lavoro, il soggetto è obbligatorio."),
	COMCOMW009("COM-COM-W-0009", "Attenzione! Il campo 'comune sede operativa' è un dato obbligatorio."),
	COMCOMW010("COM-COM-W-0010", "Attenzione! Il campo 'codice fiscale' del datore di lavoro non è valido."),
	COMCOMW011("COM-COM-W-0011",
			"Attenzione! Numero caratteri codice fiscale o partita IVA del datore di lavoro errato.\r\n"),
	COMCOMW012("COM-COM-W-0012", "Attenzione! L'impresa deve avere la sede in Piemonte o fra le aziende accentrate."),
	COMCOMW013("COM-COM-W-0013", "Attenzione! Inserire un comune per la sede operativa valido."),
	COMCOMW014("COM-COM-W-0014", "Attenzione! Numero caratteri codice fiscale o partita IVA del lavoratore errato."),
	COMCOMW015("COM-COM-W-0015", "Attenzione! Il campo 'codice fiscale' del lavoratore non è valido."),
	COMCOMW016("COM-COM-W-0016", "Attenzione: verificare la correttezza delle date poiché la comunicazione potrebbe non rientrare nei termini previsti dalla legge."),
	COMCOMW017("COM-COM-W-0017", "WARNING: Si ricorda che per i rapporti di tirocinio e' necessario completare il processo amministrativo compilando il	Progetto Formativo sul Portale dei Tirocini."),

	COMCOMW095("COM-COM-W-0095", "Stato estero non trovato"),
	COMCOMW096("COM-COM-W-0096", "Qualifica Istat non trovata"),
	COMCOMW097("COM-COM-W-0097", "Inquadramento non trovato"), COMCOMW098("COM-COM-W-0098", "Ccnl non trovato"),
	COMCOMW099("COM-COM-W-0099", "Comune non trovato"),
	COMCOMW100("COM-COM-W-0100", "Attenzione: Nessuna azienda/studio professionale è stato trovato"),
	COMCOMW101("COM-COM-W-0101", "Ateco non trovato"),

	COMCOMW1024("COM-COM-W-1024",
			"La delega associata risulta revocata. Inserire una nuova delega per completare l’accreditamento."),
	COMCOMW1027("COM-COM-W-1027",
			"Non risulta associata nessuna delega. Inserire una nuova delega per completare l’accreditamento."),
	COMCOMW1201("COM-COM-W-1201",
			"Attenzione:lo studio/azienda risulta associata ad almeno un consulente/ delegato/persona autorizzata  . Annullare anche il consulente/ delegato/persona autorizzata"),

	COMCOMW1202("COM-COM-W-1202",
			"Attenzione: Il salvataggio dell'azienda e della sede sono andati a buon fine. Per l'inserimento del Legale rappresentante riprovare più tardi."),

	COMCOME1021("COM-COM-E-1021",
			"Attenzione: Codice fiscale esiste già all’interno dell’anagrafica consulente/delegato/persona autorizzata. Verificare se il soggetto non è stato Annullato."),
	COMCOME1023("COM-COM-E-1023",
			"Non è possibile eliminare il delegato in quanto presente una delega valida , revocare prima la delega."),
	COMCOME1025("COM-COM-E-1025", "Attenzione: Nessuna azienda/studio professionale è stato trovato."),

	COMCOME1026("COM-COM-E-1026", "Il codice fiscale azienda è gia presente per il delegato/persona autorizzata."),
	
	COMCOME1027("COM-COM-E-1027",
			"Attenzione: Comnbinazione di profili non ammessa. Esiste già un profilo come {{tipo}}."), 
	
	COMCOME1823("COM-COM-E-1823", "Attenzione! Delegato/persona autorizzata non trovato"),
	COMCOME1824("COM-COM-E-1824", "Attenzione! Delegato non trovato"),

	COMCOME1300("COM-COM-E-1300", "Attenzione! Data timbro postale non valida"),
	COMCOME1302("COM-COM-E-1302", "Attenzione! codice regionale da annullare / rettificare non valido"),
	COMCOME1303("COM-COM-E-1303", "Attenzione! Indicare il codice regionale della comunicazione d'urgenza."),
	COMCOME1602("COM-COM-E-1302", "Attenzione! il cap della sede non può essere valorizzato, se lo stato estero della sede è valorizzato."),

	// RAFFAELLA MESSAGGI PER RIPORTARE TUTTTI QUELLI DEI CONTROLLI PRESENTI SUL
	// VECCHIO
	COMDATE001("COM-DAT-E-001", "Attenzione! Ci sono valori inseriti di lunghezza troppo elevata."),
	COMDATE002("COM-DAT-E-002", "Attenzione! Il codice fiscale non e' valido."),
	COMDATE003("COM-DAT-E-003", "Attenzione! Partita IVA non valida."),
	COMDATE004("COM-DAT-E-004", "Attenzione! Valorizzare il campo 'agenzia di somministrazione estera'."),
	COMDATE005("COM-DAT-E-005",
			"Attenzione! In caso di selezione di 'azienda di somministrazione estera' non deve essere specificato il 'codice fiscale'."),
	COMDATE006("COM-DAT-E-006", "Attenzione! Il campo 'cognome / denominazione' e' un dato obbligatorio."),
	COMDATE007("COM-DAT-E-007", "Attenzione! Il campo 'cognome / denominazione' e' troppo lungo."),
	COMDATE008("COM-DAT-E-008", "Attenzione! Il campo 'pubblica amministrazione' e' un dato obbligatorio."),
	COMDATE009("COM-DAT-E-009", "Attenzione! Valorizzare il campo 'Extra-comunitario'."),
	COMDATE010("COM-DAT-E-010", "Attenzione! I dati del legale rappresentante non sono completi."),
	COMDATE011("COM-DAT-E-011",
			"Attenzione! Se il legale rappresentante e' extracomunitario i suoi dati anagrafici sono obbligatori."),
	COMDATE012("COM-DAT-E-012",
			"Attenzione! I dati anagrafici del legale rappresentante non devono essere valorizzati."),
	COMDATE013("COM-DAT-E-013",
			"Attenzione! Attenzione! Valorizzare solo uno dei campi tra 'comune di nascita' e 'stato estero di nascita'."),
	COMDATE014("COM-DAT-E-014",
			"Attenzione! Se il legale rappresentante non e' extra-comunitario la cittadinanza deve essere nell'Unione Europea."),
	COMDATE015("COM-DAT-E-015",
			"Attenzione! Se il legale rappresentante e' extra-comunitario la cittadinanza non deve essere nell'Unione Europea."),
	COMDATE016("COM-DAT-E-016", "Attenzione! Inserire uno stato estero di nascita del legale rappresentante valido."),
	COMDATE017("COM-DAT-E-017", "Attenzione! Inserire un comune di nascita del legale rappresentante valido."),
	COMDATE018("COM-DAT-E-018", "Attenzione! Inserire una data di nascita del legale rappresentante valida."),
	COMDATE019("COM-DAT-E-019",
			"Attenzione! I dati del titolo di soggiorno del legale rappresentante non devono essere compilati se 'Soggiornante in Italia' e' 'NO'"),
	COMDATE020("COM-DAT-E-020", "Attenzione! Valorizzare il campo 'Soggiornante in Italia'"),
	COMDATE021("COM-DAT-E-021", "Attenzione! La data di scadenza del Titolo di soggiorno non e' valida."),
	COMDATE022("COM-DAT-E-022",
			"Attenzione! La data di scadenza del Titolo di soggiorno e' una data inesistente nel calendario, oppure ha l'anno superiore al 2200."),
	COMDATE023("COM-DAT-E-023",
			"Attenzione! Se il tipo documento e' diverso da 'in attesa di permesso' il campo 'data di scadenza del Titolo di soggiorno / data rilascio' deve essere maggiore o uguale a 01/01/2000."),
	COMDATE024("COM-DAT-E-024",
			"Attenzione! I dati del titolo di soggiorno del legale rappresentante non devono essere compilati se 'Soggiornante in Italia' e' 'NO'"),
	COMDATE025("COM-DAT-E-025",
			"Attenzione! La 'Questura rilascio titolo di soggiorno' del legale rappresentante e' un dato obbligatorio."),
	COMDATE026("COM-DAT-E-026",
			"Attenzione! La Questura di rilascio deve essere valorizzata solo in caso di titolo di soggiorno diverso da 'in attesa di permesso', 'in rinnovo' e da 'altro provvedimento'."),
	COMDATE027("COM-DAT-E-027", "Attenzione! La data scadenza del titolo di soggiorno specificata e' errata."),
	COMDATE028("COM-DAT-E-028",
			"Attenzione! La data scadenza del titolo di soggiorno specificata e' minore della data di riferimento '01/01/2000'."),
	COMDATE029("COM-DAT-E-029",
			"Attenzione! La data scadenza del titolo di soggiorno specificata e' diversa rispetto la data convenzionale '01/01/1900'."),
	COMDATE030("COM-DAT-E-030",
			"Attenzione! La data scadenza del titolo di soggiorno specificata e' diversa rispetto la data convenzionale '31/12/2099'."),
	COMDATE031("COM-DAT-E-031",
			"Attenzione! Il numero iscrizione all'albo non e' nel formato corretto. Il campo deve essere di 10 caratteri piu' la data espressa come gg/mm/aaaa."),
	COMDATE032("COM-DAT-E-032", "Attenzione! Non e' ammesso selezionare una sede operativa con stato estero."),
	COMDATE033("COM-DAT-E-033",
			"Attenzione! Non e' consentito valorizzare insieme lo stato estero ed il gruppo provincia - comune - CAP per la sede operativa."),
	COMDATE034("COM-DAT-E-034",
			"Attenzione! Per poter inserire i dati relativi alla sede operativa e' necessario inserire lo stato estero, oppure la provincia il comune ed il CAP."),
	COMDATE035("COM-DAT-E-035", "Attenzione! L'indirizzo della sede operativa e' troppo lungo."),
	COMDATE036("COM-DAT-E-036", "Attenzione! Il campo cap della sede operativa e' un dato obbligatorio."),
	COMDATE037("COM-DAT-E-037", "Attenzione! Il campo CAP della sede operativa deve essere un dato numerico."),
	COMDATE038("COM-DAT-E-038",
			"Attenzione! Il campo CAP della sede operativa inserito deve essere un dato numerico di 5 cifre."),
	COMDATE039("COM-DAT-E-039", "Attenzione! Il campo cap della sede legale e' un dato obbligatorio."),
	COMDATE040("COM-DAT-E-040", "Attenzione! Il campo CAP della sede legale deve essere un dato numerico."),
	COMDATE041("COM-DAT-E-041",
			"Attenzione! Il campo CAP della sede legale inserito deve essere un dato numerico di 5 cifre."),
	COMDATE042("COM-DAT-E-042", "Attenzione! Il campo cap della sede operativa non deve essere valorizzato."),
	COMDATE043("COM-DAT-E-043",
			"Attenzione! Lo stato estero della sede operativa inserito risulta essere scaduto oppure non valido oppure non appartenente alla UE."),
	COMDATE044("COM-DAT-E-044",
			"Attenzione! Il telefono della sede operativa deve essere un numero minore o uguale a 15 cifre. I soli caratteri non numerici consentiti sono lo spazio, '-' e '/' per separare il prefisso dal numero."),
	COMDATE045("COM-DAT-E-045",
			"Attenzione! Il fax della sede operativa deve essere un numero minore o uguale a 15 cifre. I soli caratteri non numerici consentiti sono lo spazio, '-' e '/' per separare il prefisso dal numero."),
	COMDATE046("COM-DAT-E-046", "Attenzione! Indirizzo e-mail della sede operativa non corretto."),
	COMDATE047("COM-DAT-E-047",
			"Attenzione! E' obbligatorio inserire almeno uno dei campi telefono, telefax, e-mail della sede operativa."),
	COMDATE048("COM-DAT-E-048", "Attenzione! Inserire uno stato estero per la sede operativa valido."),
	COMDATE049("COM-DAT-E-049", "Attenzione! Inserire una provincia per la sede operativa valida."),
	COMDATE050("COM-DAT-E-050", "Attenzione! Inserire un comune per la sede operativa valido."),
	COMDATE051("COM-DAT-E-051", "Attenzione! Se si inserisce un comune per la sede operativa e' necessario inserire anche la provincia relativa."),
	COMDATE052("COM-DAT-E-052", "Attenzione! L'azienda o il soggetto abilitato per cui si inserisce la comunicazione non sono accentrati."),
	COMDATE053("COM-DAT-E-053", "Attenzione! Non e' ammesso selezionare una sede legale con stato estero."),
	COMDATE1600("COM-DAT-E-1600", "Attenzione il codice fiscale del datore precedente deve essere diverso da quello del datore attuale."),
	COMDATE1601("COM-DAT-E-1601", "Attenzione! La data fine affitto ramo deve essere maggiore o uguale alla data inizio"),
	// controlli CDU-10 (QUADRO DATI RAPPORTO)
	COMRAPE0109("COM-RAP-E-0109",
			"Attenzione! La data fine rapporto è obbligatoria per il tipo di rapporto selezionato."),
	COMRAPE0110("COM-RAP-E-0110",
			"Attenzione! La data fine rapporto non può essere antecedente alla data inizio rapporto."),
	COMRAPE0164("COM-RAP-E-0164", "Attenzione! La data fine è obbligatoria in caso di lavoro stagionale."),
	COMRAPE0165("COM-RAP-E-0165", "Attenzione! La data fine rapporto non può essere valorizzata."),
	COMRAPE0111("COM-RAP-E-0111",
			"Attenzione! La data fine periodo formativo è obbligatoria per il tipo di rapporto selezionato."),
	COMRAPE0112("COM-RAP-E-0111",
			"Attenzione! La data fine periodo formativo deve essere maggiore della data inizio rapporto."),
	COMRAPE0113("COM-RAP-E-0113", "Attenzione! La data fine e la data fine periodo formativo devono essere uguali."),
	COMRAPE0120("COM-RAP-E-0120",
			"Attenzione! Il contratto di FORMAZIONE LAVORO è ammesso solo per la pubblica amministrazione, il CCNL e il livello devono appartenere al settore 'Enti Pubblici'."),
	COMRAPE0121("COM-RAP-E-0121",
			"Attenzione! La modifica sul tipo orario non è consentita; renderebbe incongruente la scelta fatta sul tipo di trasformazione."),
	COMRAPE0122("COM-RAP-E-0122",
			"Attenzione! La modifica sul tipo orario non è consentita; renderebbe incongruente la scelta fatta sul tipo di trasformazione."),
	COMRAPE0123("COM-RAP-E-0123",
			"Attenzione! La modifica sul tipo orario non è consentita; renderebbe incongruente la scelta fatta sul tipo di trasformazione."),
	COMRAPE0124("COM-RAP-E-0124",
			"Attenzione! Se si popola uno tra i campi: art.legge 68, legge 68 tipo atto, legge 68 data nulla osta/convenzione, legge 68 num. atto, sarà obbligatorio popolarli tutti."),
	COMRAPE0128("COM-RAP-E-0128",
			"Attenzione! Per la tipologia di contratto selezionata è necessario indicare il Codice fiscale ente promotore tirocinio."),
	COMRAPE0129("COM-RAP-E-0129", "Attenzione! Il campo cpi come ente promotore tirocinio è obbligatorio."),
	COMRAPE0130("COM-RAP-E-0130",
			"Attenzione! La tipologia tirocinio selezionata non è compatibile con la categoria tirocinante selezionata."),
	COMRAPE0131("COM-RAP-E-0131",
			"Attenzione! Il campo ore settimanali è obbligatorio se il campo tipo orario è un tempo parziale."),
	COMRAPE0136("COM-RAP-E-0136",
			"Attenzione! La data fine del contratto di somministrazione è obbligatoria per il tipo di contratto indicato."),
	COMRAPE0137("COM-RAP-E-0137",
			"Attenzione! L'età del lavoratore non può essere superiore a 100 o inferiore a 0 anni."),
	COMRAPE0138("COM-RAP-E-0138", "Attenzione! Il tipo orario 'NON DEFINITO' non è ammesso per il contratto scelto."),
	COMRAPE0139("COM-RAP-E-0139",
			"Attenzione! Il contratto selezionato non è valido per la data di inizio rapporto indicata."),
	COMRAPE0140("COM-RAP-E-0140",
			"Attenzione! La comunicazione si riferisce a più lavoratori. Pertanto non è possibile selezionare le tipologie di rapporto apprendistato, tirocinio."),
	COMRAPE0141("COM-RAP-E-0141",
			"Attenzione! La data di nascita del lavoratore deve essere minore della data di inizio del rapporto di lavoro. (lavoratore/i errati: {parameter})"),
	COMRAPE0142("COM-RAP-E-0142", "Attenzione! La data fine rapporto è obbligatoria in caso di lavoro stagionale."),
	COMRAPE0145("COM-RAP-E-0145",
			"Attenzione! Il campo 'Categoria Lavoratore Assunzione Obbligatoria' è un dato obbligatorio."),
	COMRAPE0166("COM-RAP-E-0166",
			"Attenzione! L'intervallo tra la data inizio e la data fine non può superare i 50 anni."),
	COMRAPE0167("COM-RAP-E-0167",
			"Attenzione! Il Codice fiscale del soggetto promotore tirocinio non può essere uguale al Codice fiscale del Datore di lavoro."),
	COMRAPE0168("COM-RAP-E-0168", "Attenzione! La retribuzione inserita non puo' essere 0."),
	COMRAPE0169("COM-RAP-E-0169", "Attenzione! Il tipo contratto non è valido per il tracciato inserito."),
	COMRAPE0170("COM-RAP-E-0170", "Attenzione! Inserire il lavoratore coobbligato"),
	COMRAPE0171("COM-RAP-E-0171", "Attenzione! La forma del contratto scelto non e’ determinata."),
	COMRAPE0172("COM-RAP-E-0172",
			"Attenzione! Il tipo di trasformazione scelto e' previsto solo per il contratto di apprendistato."),
	COMRAPE0173("COM-RAP-E-0173", "Attenzione! La trasformazione scelta non e’ ammessa per I contratti stagionali."),
	COMRAPE0174("COM-RAP-E-0174",
			"Attenzione! Data periodo fine formativo non deve essere compilato per tipologie contrattuali diverse da apprendistato."),
	COMRAPE0175("COM-RAP-E-0175",
			"Attenzione! Non e’ possibile selezionare il lavoro stagionale per il contratto scelto."),
	COMRAPE0177("COM-RAP-E-0177", "Attenzione! Il livello scelto non è tra quelli previsti per il ccnl selezionato."),
	COMRAPE0178("COM-RAP-E-0178", "Attenzione! Il codice ente previdenziale non deve essere valorizzato."),
	COMRAPE0179("COM-RAP-E-0179",
			"Attenzione! Indicare le giornate lavorative previste solo se si e’ indicato il lavoro in agricoltura."),
	COMRAPE0180("COM-RAP-E-0180",
			"Attenzione! Indicare il tipo lavorazione solo se si e’ indicato il lavoro in agricoltura."),
	COMRAPE0181("COM-RAP-E-0181", "Attenzione! Il contratto di somministrazione a tempo indeterminato non e' valido."),
	COMRAPE0182("COM-RAP-E-0182", "Attenzione! Il codice fiscale del lavoratore: {{parameter}}, non può essere uguale a quello del datore."),
	COMRAPE0183("COM-RAP-E-0183", "Attenzione! Per il tipo contratto selezionato è possibile inserire solo il ccnl ND."),
	COMRAPE0184("COM-RAP-E-0184", "Attenzione! Codice fiscale ente promotore tirocinio non valido."),
	COMRAPE0185("COM-RAP-E-0185", "Attenzione! Per il tipo contratto selezionato non è possibile inserire il ccnl ND."),
	COMRAPE0186("COM-RAP-E-0186", "Attenzione! Il valore di 'Categoria Lavoratore Assunzione Obbligatoria' non è ammesso per la missione."),
	
	COMRAPW0055("COM-RAP-W-0055",
			"Attenzione! Sulla base del Contratto collettivo e del livello inseriti, la retribuzione risulta inferiore al minimo ammesso calcolato: {{parameter}}. Si consiglia di verificare. Con la conferma, i dati vengono comunque accettati dal sistema sotto la responsabilita' di chi compila la comunicazione."),
	
	COMRAPW0146("COM-RAP-W-0146","WARNING: l'età minima del lavoratore per il contratto di Apprendistato per la qualifica professionale e per il diploma professionale è di 15 anni compiuti.(C.F. {{parameter}})"),
	COMRAPW0147("COM-RAP-W-0147","WARNING: l'età massima del lavoratore per il contratto di Apprendistato per la qualifica professionale e per il diploma professionale è di 26 anni non compiuti.(C.F. {{parameter}})"),
	COMRAPW0148("COM-RAP-W-0148","WARNING: l'età minima del lavoratore per il contratto di Apprendistato professionale o contratto di mestiere (anche per lavoratori stagionali) è di 17 anni compiuti.(C.F. {{parameter}})"),
	COMRAPW0149("COM-RAP-W-0149","WARNING: l'età massima del lavoratore per il contratto di Apprendistato professionale o contratto di mestiere (anche per lavoratori stagionali) è di 30 anni non compiuti.(C.F. {{parameter}})"),
	COMRAPW0150("COM-RAP-W-0150","WARNING: l'età minima del lavoratore per il contratto di Apprendistato di alta formazione e ricerca è di 18 anni compiuti.(C.F. {{parameter}})"),
	COMRAPW0151("COM-RAP-W-0151","WARNING: l'età massima del lavoratore per il contratto di Apprendistato di alta formazione e ricerca è di 30 anni non compiuti.(C.F. {{parameter}})"),
	COMRAPW0152("COM-RAP-W-0152","WARNING: il lavoratore non ha raggiunto l'età minima per il lavoro.(C.F. {{parameter}})"),
	COMRAPW0153("COM-RAP-W-0153","WARNING: il lavoratore ha superato l'età massima per il lavoro.(C.F. {{parameter}})"),
	COMRAPW0154("COM-RAP-W-0154","WARNING: verificare la correttezza della scelta 'Socio lavoratore' per la tipologia contrattuale selezionata."),
	COMRAPW0155("COM-RAP-W-0155","WARNING: la durata minima per un 'Contratto di inserimento' è di 9 mesi."),
	COMRAPW0156("COM-RAP-W-0156","WARNING: la durata massima per un 'Contratto di inserimento' è di 18 mesi."),
	COMRAPW0157("COM-RAP-W-0157",
			"WARNING: ai sensi del Decreto Legislativo 368/2001, e sue modificazioni, il rapporto di lavoro si considera a tempo indeterminato qualora abbia superato, a parità di mansioni, i 36 mesi di durata, comprensivi di proroghe e rinnovi."),
	COMRAPW0158("COM-RAP-W-0158","WARNING: per la categoria tirocinante scelta, la durata massima del rapporto è 6 mesi a meno di eventuali sospensioni già pianificate in fase di instaurazione del rapporto di tirocinio."),
	COMRAPW0159("COM-RAP-W-0159","WARNING: per la categoria tirocinante scelta, la durata massima del rapporto è 12 mesi a meno di eventuali sospensioni già pianificate in fase di instaurazione del rapporto di tirocinio."),
	COMRAPW0160("COM-RAP-W-0160","WARNING: per la categoria tirocinante scelta, la durata massima del rapporto è 24 mesi a meno di eventuali sospensioni già pianificate in fase di instaurazione del rapporto di tirocinio."),
	COMRAPW0161("COM-RAP-W-0161","WARNING: per la categoria tirocinante scelta, la durata massima del rapporto è 3 mesi a meno di eventuali sospensioni già pianificate in fase di instaurazione del rapporto di tirocinio."),
	COMRAPW0162("COM-RAP-W-0162","WARNING: Il codice fiscale del Soggetto Promotore del tirocinio è uguale al codice fiscale del Datore di lavoro."),
	
	
	
	
	// controlli CDU-12 (QUADRO TUTORE)
	COMTUTE0065("COM-TUT-E-0065", "Attenzione! La qualifica professionale è un campo obbligatorio."),
	COMTUTE0067("COM-TUT-E-0067", "Attenzione! E'necessario valorizzare il campo accettazione."),
	COMTUTE0069("COM-TUT-E-0069", "Attenzione! Il codice fiscale non è corretto."),
	COMTUTE0070("COM-TUT-E-0070", "Il codice fiscale del tutore deve essere diverso da quello del lavoratore."),
	COMTUTE0071("COM-TUT-E-0071",
			"Attenzione! Se si inserisce il codice della qualifica professionale, è necessaria anche la descrizione relativa."),
	COMTUTE0072("COM-TUT-E-0072",
			"Attenzione! Se si inserisce la descrizione della qualifica professionale, è necessario anche il codice relativo."),
	COMTUTE0073("COM-TUT-E-0073", "Attenzione! Il codice della qualifica non corrisponde alla descrizione inserita."),
	COMTUTE0074("COM-TUT-E-0074", "La qualifica inserita non è più valida"),
	COMTUTE0075("COM-TUT-E-0075", "Il grado contrattuale non è più valido"),

	// controlli CDU-13 (QUADRO TRASFORMAZIONE)
	COMTRAE1380("COM-TRA-E-1381",
			"Attenzione! La trasformazione DP (DA CONTRATTO DI APPRENDISTATO A CONTRATTO DI APPRENDISTATO PROFESSIONALIZZANTE) è ammessa solo per contratto in A.03.09 (APPRENDISTATO PROFESSIONALIZZANTE O CONTRATTO DI MESTIERE)."),
	COMTRAE1381("COM-TRA-E-1381",
			"Attenzione! La data trasformazione non può essere precedente alla data inizio rapporto e successiva alla data fine rapporto"),
	COMTRAE1382("COM-TRA-E-1381", "Attenzione! Il tipo trasformazione non è ammesso in base ai dati del rapporto"),
	COMTRAE1383("COM-TRA-E-1383",
			"Attenzione! Il motivo di trasformazione indicato è ammesso solo se la comunicazione si riferisce ad un Ente Pubblico."),
	COMTRAE1384("COM-TRA-E-1384",
			"Attenzione! La trasformazione 'AI' è ammessa solo per rapporti di apprendistato instaurati prima del 26/10/2011."),
	COMTRAE1385("COM-TRA-E-1385",
			"Attenzione! La trasformazione 'FF' è ammessa solo per rapporti di apprendistato instaurati dopo il 26/10/2011, ad esclusione di quelli stagionali."),
	COMTRAE1386("COM-TRA-E-1386",
			"Attenzione! La data di inizio missione deve essere superiore alla data inizio rapporto e inferiore alla data di trasformazione."),
	COMTRAE1387("COM-TRA-E-1387",
			"Attenzione! La data di trasformazione non può essere superiore alla data fine contratto di somministrazione."),
	COMTRAE1388("COM-TRA-E-1388",
			"Attenzione! Trasformazione da TD a TI: verificare che il contratto di lavoro trasformato sia uno dei seguenti contratti: A.01.00, A.04.02,A.08.02,A.07.02, A.05.02,M.02.00,N.01.00"),
	// controlli CDU-13 (QUADRO PROROGA)
	COMPROE0021("COM-PRO-E-0021", "Attenzione! La data fine proroga deve essere maggiore o uguale alla data inizio rapporto."),
//	COMPROE0022("COM-PRO-E-0022", "Attenzione! La data fine proroga deve essere successiva alla data fine rapporto."),
	COMPROE0023("COM-PRO-E-0023", "Attenzione! La data fine proroga deve essere maggiore o uguale alla data inizio missione."),
	COMPROW0024("COM-PRO-W-0024", "WARNING! La proroga della missione supera i termini del contratto con l'agenzia di somministrazione: pertanto, verrà automaticamente prorogato anche il contratto di somministrazione."),
	COMPROE0025("COM-PRO-E-0025", "Attenzione! La data fine proroga deve essere minore o uguale alla data fine rapporto."),
	
	// controlli CDU-13 (QUADRO CESSAZIONE)
	COMCESE0042("COM-CES-E-0042",
			"Attenzione! La data fine cessazione deve essere successiva o uguale alla data inizio rapporto."),
//	COMCESE0043("COM-CES-E-0043",
//			"Attenzione! La data fine cessazione deve essere successiva alla data fine rapporto."),
	COMCESE0044("COM-CES-E-0044",
			"Attenzione! La data fine cessazione deve essere successiva o uguale alla data inizio missione."),
	COMCESE0045("COM-CES-E-0045",
			"Attenzione! La data fine cessazione deve essere inferiore o uguale alla data fine contratto somministrazione nella sezione ditta utilizzatrice"),
	COMCESE0046("COM-CES-E-0046",
			"Attenzione! L'intervallo tra la data cessazione e la data inizio rapporto non può superare i 50 anni."),

	// controlli CDU-14 (QUADRO TRASFERIMENTO/DISTACCO)
	COMTDIE1402("COM-TDI-E-1402", "Attenzione! La data fine distacco non è valorizzata."),
	COMTDIE1403("COM-TDI-E-1403", "Attenzione! Codice fiscale non valido."),
	COMTDIE1404("COM-TDI-E-1404", "Attenzione! Il campo PAT INAIL è obbligatorio."),
	COMTDIE1405("COM-TDI-E-1405", "Attenzione! È necessario inserire la provincia, il comune ed il CAP. "),
	COMTDIE1406("COM-TDI-E-1406", "Attenzione! Il campo denominazione datore di lavoro distaccatario è obbligatorio."),
	COMTDIE1410("COM-TDI-E-1410", "Attenzione! È necessario inserire lo stato estero oppure la provincia e il comune."),
	COMTDIE1412("COM-TDI-E-1412",
			"Attenzione! La data fine distacco deve essere superiore o uguale alla data trasferimento distacco e alla data inizio rapporto."),
	COMTDIE1414("COM-TDI-E-1414",
			"Attenzione! La data trasferimento distacco deve essere uguale o successiva alla data inizio rapporto."),
	COMTDIE1415("COM-TDI-E-1415", "Attenzione! Id azienda SILP non valorizzato."),
	COMTDIE1416("COM-TDI-E-1416", "Attenzione! Popolare almeno uno dei campi: telefono, telefax, e-mail."),
	COMTDIW1413("COM-TDI-W-1413",
			"Warning! il codice fiscale datore distaccatario è uguale a quello dell’azienda di assunzione del lavoratore."),
	COMTDIE1417("COM-TDI-E-1417", "Attenzione! La data trasferimento distacco deve essere maggiore della data inizio missione."),

	// controlli CDU-11 (QUADRO RAPPORTO MISSIONE)
	COMMISEXXXX("COM-TDI-W-1413", "Messaggio da definire"),
	COMMISE0092("COM-MIS-E-0092",
			"Attenzione! La data fine missione deve essere successiva alla data inizio missione."),
	COMMISE0098("COM-MIS-E-0098",
			"Attenzione! La data inizio missione deve essere uguale o successiva alla data inizio contratto somministrazione nella sezione azienda utilizzatrice"),
	COMMISE0099("COM-MIS-E-0099",
			"Attenzione! La data fine missione non può essere superiore alla data fine contratto di somministrazione."),
	COMMISE0100("COM-MIS-E-0100",
			"Attenzione! Indicare la descrizione attivita' agricoltura solo se si e’ indicato il lavoro in agricoltura."),
	COMMISE0101("COM-MIS-E-0101",
			"Attenzione! La data inizio missione deve essere uguale o successiva alla data inizio rapporto"),
	COMMISE0102("COM-MIS-E-0102",
			"Attenzione! La data fine missione non può essere superiore alla data fine rapporto."),

	// controlli CDU-08 (QUADRO AZIENDA UTILIZZATRICE)
	COMIMPE1344("COM-IMP-E-1344",
			"Attenzione! Il codice fiscale dell'azienda utilizzatrice deve essere diverso da quello del datore."),
	COMIMPE1345("COM-IMP-E-1345",
			"Attenzione! Selezionare per la sede legale uno stato estero appartenente alla UE."),

	// controlli per quadri
	COMLAVE001("COM-LAV-E-001", "Attenzione! Nessun lavoratore presente."),
	COMLAVE002("COM-LAV-E-002", "I seguenti campi sono obbligatori: "),
	COMLAVE003("COM-LAV-E-003",
			"Attenzione! Per un cittadino Extra UE i flag inerenti la sezione del 'modello Q' devono essere settati entrambi a 'si' o entrambi a 'non applicabile'."),
	COMLAVE004("COM-LAV-E-004",
			"Attenzione! Per un cittadino UE i flag inerenti la sezione del 'modello Q' devono essere settati entrambi a 'non applicabile'."),
	COMLAVE005("COM-LAV-E-005",
			"Attenzione! Non &egrave; consentito valorizzare insieme il comune e lo stato estero di nascita."),
	COMLAVE006("COM-LAV-E-006", "Attenzione! Il codice fiscale non &egrave; corretto."),
	COMLAVE007("COM-LAV-E-007",
			"Attenzione! Nel caso di cittadinanza appartenente all' unione europea non bisogna valorizzare il campo tipo documento."),
	COMLAVE008("COM-LAV-E-008",
			"Attenzione! Nel caso di cittadinanza appartenente all' unione europea non bisogna valorizzare il campo num. documento."),
	COMLAVE009("COM-LAV-E-009",
			"Attenzione! Per cittadini comunitari &egrave; consentito solo selezionare i valori 'non appicabile' per entrambi i campi sussistenza della sistemazione alloggiativa e impegno del datore di lavoro al pagamento delle spese per il rimpatrio."),
	COMLAVE010("COM-LAV-E-010",
			"Attenzione! Nel caso di cittadinanza appartenente all' unione europea non bisogna valorizzare il campo Questura rilascio Titolo di soggiorno."),
	COMLAVE011("COM-LAV-E-011", "Attenzione! Il campo Titolo di soggiorno &egrave obbligatorio."),
	COMLAVE012("COM-LAV-E-012", "Attenzione! Il campo Motivo Titolo di soggiorno &egrave obbligatorio."),
	COMLAVE013("COM-LAV-E-013", "Attenzione! Il campo scadenza del Titolo di soggiorno &egrave obbligatorio."),
	COMLAVE014("COM-LAV-E-014",
			"Attenzione! Se il tipo documento &egrave; diverso da \"in attesa di permesso\" il campo \"data di scadenza del Titolo di soggiorno / data rilascio\" deve essere maggiore o uguale a 01/01/2000."),
	COMLAVE015("COM-LAV-E-015",
			"Attenzione! Nel caso di cittadinanza appartenente all' unione europea non bisogna valorizzare il campo tipo documento."),
	COMLAVE016("COM-LAV-E-016",
			"Attenzione! Nel caso di cittadinanza appartenente all' unione europea non bisogna valorizzare il campo Motivo Titolo di soggiorno."),
	COMLAVE017("COM-LAV-E-017",
			"Attenzione! Nel caso di cittadinanza appartenente all' unione europea non bisogna valorizzare il campo scadenza del Titolo di soggiorno."),
	COMLAVE018("COM-LAV-E-018",
			"Attenzione! Nel caso di cittadinanza appartenente all' unione europea non bisogna valorizzare il campo Questura rilascio Titolo di soggiorno."),
	COMLAVE019("COM-LAV-E-019",
			"Attenzione! La 'Questura rilascio titolo di soggiorno' del legale rappresentante &egrave; un dato obbligatorio."),
	COMLAVE020("COM-LAV-E-020",
			"Attenzione! La data scadenza del titolo di soggiorno specificata &egrave; minore della data di riferimento '01/01/2000'."),
	COMLAVE021("COM-LAV-E-021",
			"Attenzione! La data scadenza del titolo di soggiorno specificata &egrave; diversa rispetto la data convenzionale '01/01/1900'."),
	COMLAVE022("COM-LAV-E-022",
			"Attenzione! La data scadenza del titolo di soggiorno specificata &egrave; diversa rispetto la data convenzionale '31/12/2099'."),
	COMLAVE023("COM-LAV-E-023",
			"Attenzione! La 'Questura rilascio titolo di soggiorno' del lavoratore &egrave; un dato obbligatorio.\r\n"),
	COMLAVE024("COM-LAV-E-024",
			"Attenzione! La 'Questura rilascio titolo di soggiorno' del lavoratore deve essere specificata solo in caso di lavoratore extra UE con tipo di motivo permesso diverso da 'in attesa di permesso',  da 'in rinnovo' e da 'altro provvedimento'."),
	COMLAVE025("COM-LAV-E-025", "Attenzione! Non sono stati valorizzati i dati anagrafici del legale rappresentante nella pagina del datore."),
	
	COMLAVE026("COM-LAV-E-026", "Attenzione! Motivo del permesso/provvedimento è obbbligatorio."),
	COMLAVE027("COM-LAV-E-027", "Attenzione! La cittadinanaza è obbligatoria."),

	COMCOME1851("COM-COM-E-1851", "Comune di nascita non valido"),
	COMCOME1852("COM-COM-E-1852", "Comune di residenza non valido"),
	COMCOME1853("COM-COM-E-1853", "Comune di domicilio non valido"),
	COMCOME1854("COM-COM-E-1854", "Comune di nascita è obbligatorio"),
	COMCOME1855("COM-COM-E-1855", "Comune di residenza è obbligatorio"),
	COMCOME1856("COM-COM-E-1856", "Comune di domicilio è obbligatorio"),
	COMCOME1857("COM-COM-E-1857", "Comune non valido"), COMCOME1858("COM-COM-E-1858", "Comune è obbligatorio"),
	COMCOME1859("COM-COM-E-1859", "Stato estero non valido"),
	COMCOME1860("COM-COM-E-1860", "Il Cognome e' obbligatorio"),
	COMCOME1861("COM-COM-E-1861", "Il Nome e' obbligatorio"),
	COMCOME1862("COM-COM-E-1862", "L'indirizzo di residenza e' obbligatorio"),

	COMIMP0001("COM-IMP-E-0001", "Comunicazione gia' presente in Comonl"),
	COMIMP0002("COM-IMP-E-0002", "Errore tecnico, comunicazione senza codice regionale"),
	COMIMP0003("COM-IMP-E-0003", "Errore validazione, controllare la COM_D_IMPORT_ERRORE"),
	COMIMP0004("COM-IMP-E-0004", "Esiste piu' di una comunicazione con codice regionale precendete uguale al codice regionale"),

	COMURGE0001("COM-URG-E-0001", "Attenzione il codice fiscale del soggetto non è valido"),
	COMURGE0002("COM-URG-E-0002", "Attenzione il codice fiscale del datore di lavoro non è valido"),
	COMURGE0003("COM-URG-E-0003", "Attenzione il codice fiscale del lavoratore non è valido"),
	
	// WARNING generati in fase di salvataggio della comunicazione
	COMCOMW0200("COM-COM-W-0200", "Attenzione il codice fiscale del lavoratore non è valido"),
	
	CHKESSE0001("CHK-ESS-E-0001", "Attenzione Comunicazione da controllare non trovata"),
	;

	private final String code;
	private final String type;
	private final String msgGroup;
	private final String message;

	/**
	 * Private constructor
	 * 
	 * @param code    the code
	 * @param message the message
	 */
	private MsgComonl(String code, String message) {
		this(code, null, message);
	}

	/**
	 * Private constructor
	 * 
	 * @param code    the code
	 * @param group   the group
	 * @param message the message
	 */
	private MsgComonl(String code, String msgGroup, String message) {
		this(code, "ERROR", msgGroup, message);
	}

	private MsgComonl(String code, String type, String msgGroup, String message) {
		this.code = code;
		this.type = type;
		this.msgGroup = msgGroup;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getGroup() {
		return msgGroup;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
