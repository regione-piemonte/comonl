# Prodotto

COMONL (GECO): COMunicazioni ONLine Aziende (GEstione online Comunicazioni Obbligatorie) - Servizi Lavoro Piemonte

# Descrizione del prodotto
Il prodotto COMONL è l’applicativo della Regione Piemonte  attraverso il quale i datori di lavoro pubblici e privati, inviano in modalità telematica le comunicazioni obbligatorie sui rapporti di lavoro: assunzioni, cessazioni, proroghe, trasformazioni, trasferimenti, ecc.).

Il servizio corrispondente, erogato con il nome di GECO, è disponibile all'indirizzo https://servizi.regione.piemonte.it/catalogo/gestione-online-comunicazioni-obbligatorie-geco

Il servizio si rivolge a imprese, intermediari, Pubblica Amministrazione e Centri per l’Impiego.

Le leggi 608/96 e 264/49 stabiliscono l'obbligo per i datori di lavoro di comunicare avviamenti, trasformazioni o cessazioni di rapporti di lavoro ai Centri per l'impiego provinciali.

La legge 27 dicembre 2006 n° 296, art. 1, commi 1180-1185, estende gli obblighi della comunicazione anche a:

-    rapporti di lavoro a progetto e di associato in partecipazione
-    tirocini di formazione e di orientamento e le altre esperienze lavorative assimilabili
-    trasferimento e distacco del lavoratore
-    modifica della ragione sociale
-    trasferimento o fusione di azienda o ramo di essa.

Gli obblighi della comunicazione sono estesi anche ai datori di lavoro agricoli, agli enti pubblici ed alle Pubbliche Amministrazioni.
Viene inoltre esteso l’effetto della comunicazione all’assolvimento degli obblighi nei confronti delle direzioni regionali e provinciali del lavoro, dell'Istituto nazionale della previdenza sociale, dell'Istituto nazionale per l'assicurazione contro gli infortuni sul lavoro, o di altre forme previdenziali sostitutive o esclusive, e inoltre nei confronti della Prefettura - Ufficio territoriale del Governo.
Il rapporto di lavoro è l’insieme delle comunicazioni obbligatorie di un lavoratore che opera presso un’impresa a partire da una determinata data; inizia con la comunicazione di assunzione, termina con la comunicazione di cessazione e contiene le possibili comunicazioni di aggiornamento trasferimenti, trasformazioni, proroghe).

L’applicativo permette alle aziende o ai propri delegati di gestire tutte le tipologie di comunicazione previste.

Il prodotto "Comonl" è stato sviluppato negli anni nel completo rispetto dei Modelli e Regole definiti dal Ministero.

Il prodotto è strutturato nelle seguenti componenti specifiche:
- [comonldb]( https://github.com/regione-piemonte/comonl/tree/main/comonl-comonldb ) : script DDL/DML per la creazione ed il popolamento iniziale del DB (istanza DBMS Oracle), procedure PL/SQL di elaborazione dati;
- [comonlwcl]( https://github.com/regione-piemonte/comonl/tree/main/comonl-comonlwcl ) : Client Web (Angular8), front-end applicativo;
- [comonlweb]( https://github.com/regione-piemonte/comonl/tree/main/comonl-comonlweb ) : Componente SPA con servizi REST per comonlwcl;					;
- [comonlsrv]( https://github.com/regione-piemonte/comonl/tree/main/comonl-comonlweb ) : Componente di esposizione servizi (REST API) verso altri applicativi del Sistema Informativo Regionale.				;

La componente comonlsrv è "logicamente" distinta da comonlweb, ma inclusa nella stessa unità di installazione (file "ear"), che contiene quindi 2 distinti "war": comonlweb-web.war e comonlweb-srv.war .

A ciascuna componente del prodotto elencata sopra corisponde una sotto-directory denominata comonl-<nome_componente>.\
In ciascuna di queste cartelle di componente si trovano ulteriori informazioni specifiche, incluso il BOM della componente di prodotto.

Nella directory [csi-lib]( https://github.com/regione-piemonte/comonl/tree/main/csi-lib ) si trovano le librerie sviluppate da CSI-Piemonte con licenza OSS, come indicato nei BOM delle singole componenti, ed usate trasversalmente nel prodotto.
	

# Prerequisiti di sistema

Una istanza DBMS Oracle (v. 12 o sup. - consigliata la v.19, eventualmente la "Enterprise Edition" per carichi di lavoro elevati) con utenza avente privilegi per la creazione tabelle ed altri oggetti DB (tramite le istruzioni DDL messe a disposizione nella componente comonldb), ed una ulteriore utenza separata non proprietaria dello schama, per l'esecuzione di istruzioni DML di Create, Readd, Update e Delete sui dati.

Una istanza di application server J2EE, consigliato WildFly 23 ( https://www.wildfly.org/downloads/ ).\
Una istanza di web server, consigliato apache web server ( https://httpd.apache.org/ ).\
Per il build è previsto l'uso di Apache Maven ( https://maven.apache.org/ ).\
Per la compilazione/build delle componenti comonlweb e comonlrv sono rese disponibili nella directory "csi-lib" una serie di librerie predisposte da CSI Piemonte per un uso trasversale nei prodotti realizzati, o per uso specifico in altri prodotti con cui COMONL si interfaccia. Indicazioni più specifiche sono disponibili nella documentazione di ciascuna componente.

Il prodotto COMONL è integrato nei servizi del sistema informativo di Regione Piemonte "Lavoro": alcune sue funzionalità sono quindi strettamente legate alla possibilità di accedere a servizi esposti da altre componenti dell'ecosistema "Lavoro" di Regione Piemonte.

Infine, anche per quanto concerne l'autenticazione e la profilazione degli utenti del sistema, COMONL è integrato con servizi trasversali del sistema informativo regionale ("Shibboleth", "IRIDE"), di conseguenza per un utilizzo in un altro contesto occorre avere a disposizione servizi analoghi o integrare moduli opportuni che svolgano analoghe funzionalità.
 

# Installazione

Creare lo schema del DB e popolarlo, tramite gli script della componente comonldb. Installare anche i package e le procedure PL/SQL presenti nella stessa directory.
 
Configurare il datasource nell'application server, utilizzato in comonlweb e comonlsrv.

Configurare i web server e definire gli opportuni Virtual Host e "location" - per utilizzare il protocollo https occorre munirsi di adeguati certificati SSL.

Nel caso si vogliano sfruttare le funzionalità di invio mail, occorre anche configurare un mail-server.


# Deployment

Dopo aver seguito le indicazioni del paragrafo relativo all'installazione, si può procedere al build dei pacchetti ed al deploy su application server (WildFly).


# Versioning
Per la gestione del codice sorgente viene utilizzato Git, ma non vi sono vincoli per l'utilizzo di altri strumenti analoghi.\
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).


# Copyrights
© Copyright Regione Piemonte – 2022\
© Copyright CSI-Piemonte – 2022


# License

SPDX-License-Identifier: EUPL-1.2-or-later .\
Questo software è distribuito con licenza EUPL-1.2 .\
Consultare il file LICENSE.txt per i dettagli sulla licenza.


