# Prodotto - Componente
COMONL - COMONLDB

# Descrizione della componente
COMONLDB è la componente DB del prodotto COMONL.\
Il DBMS di riferimento è Oracle.\
Tramite gli script messi a disposizione si crea lo schema dati usato dalle altre componenti. Per il popolamento iniziale delle tabelle possono essere forniti degli script "DML" su richiesta.\
Questa componente include quindi:
- script DDL per la creazione delle tabelle dello schema dati;
- script DDL per la definizione delle sequence e dei vincoli;
- script per la definizione delle viste;

# Configurazioni iniziali
Definire utente "comonl" su una istanza DBMS Oracle (versione 19) proprietario dello schema, ed un utente "comonl_rw" per accedere ai dati da applicativo (questo utente non ha la possibilità di modificare lo schema dati).

# Getting Started
Una volta prelevato ("git clone") e portato in locale dal repository il progetto che include la componente DB, predisporsi per poter eseguire gli script nella sequenza indicata nel seguito.

# Prerequisiti di sistema
DBMS Oracle versione 19, utente con permessi adeguati ad eseguire istruzioni di creazione tabelle.

# Installazione
Lanciare tutti gli script nella sequenza indicata dal prefisso del nome del file :

    01-sequences.sql
    02-tables.sql
    03-foreign_keys.sql
    04-views.sql

# Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

# Authors
Fare riferimento a quanto riportato nel file AUTHORS.txt.

# Copyrights

© Copyright Regione Piemonte – 2022

Vedere anche il file Copyrights.txt .

# License
Il prodotto software è sottoposto alla licenza EUPL-1.2 o versioni successive.\
SPDX-License-Identifier: EUPL-1.2-or-later.\

Vedere il file EUPL v1_2 IT-LICENSE.txt per i dettagli.

