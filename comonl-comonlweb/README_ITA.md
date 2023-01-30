# Prodotto
COMONL componenti COMONLWEB e COMONLSRV

# Descrizione della componente
Questa cartella rappresenta una "unità di installazione" che racchiude in sè due distinte componenti "logiche", ovvero COMONLWEB e COMONLSRV.

COMONLWEB
 è una web application che segue il paradigma "Single Page Application (SPA)", espone servizi REST alla componente COMONLWCL (Angular 14) e si connette al DB (COMONLDB) per le operazioni CRUD.
Si collega al servizio di profilazione utenti trasversale (IRIDE nel contesto di Regione Piemonte) e ad altri servizi del Sistema Informativo Regionale Lavoro di Regione Piemonte (IUP, SILP e SPICOM), oltre che a servizi di altri sistemi informativi di Regione Piemonte, come il servizio "Anagrafe delle Aziende e delle Attività Produttive (AAEP)".
I servizi esterni sono fruiti tramite protocollo HTTP (protetto tramite uno schema di "basic authentication") o HTTPS, e sono per lo più REST API.

COMONLSRV 
 è la componente di esposizione di servizi verso altri applicativi dei sistemi informativi regionali del lavoro e della formazione professionale; in particolare, per quanto riguarda invio e ricezione di "comunicazioni" da e verso il Ministero del Lavoro, l'interazione avviene con il prodotto SPICOM.\
I servizi sono esposti come REST API.\
Questa componente si connette al DB (DBMS ORACLE, vedi componente comonldb) utilizzando il DataSource definito a livello del container (WildFly).

# Configurazioni iniziali
Da un punto di vista generale, nella fase iniziale occorre adattare i file di properties nella directory buildfiles alla propria configurazione.
Una delle cose principali da configurare è il datasource con i riferimenti del DB che si intende utilizzare (JNDI name).\
Per quanto riguarda le properties da configurare, sono analoghe per entrambe le componenti (comonlweb, comonlsrv) incluse in questa directory, ovvero in questa unità di installazione.

Entrando più in dettaglio, nel seguito si riportano le principali properties da configurare (in alcuni casi si è mantenuta la descrizione in inglese perchè più diretta):
- jpa.dataSource: il JNDI name del datasource impostato;
- jpa.showSql: whether the SQL should be shown
- jpa.formatSql: whether the shown SQL should be formatted (only used if `jpa.showSql=true`)
- corsfilter.enableCors: whether to enable CORS usage
- authfilter.devMode: whether to apply the dev-mode to the authentication filter, effectively setting a custom user to the application
- authfilter.authAdapterName: the fully qualified name for the authentication adapter
- authfilter.iride.cookieName: the cookie name to check for IRIDE authentication
- xsrffilter.disabled: whether to disable the XSRF filter
- angularfilter.indexUrl: the URL page for the angular filter
- application.debug-mode: whether the application should be started in debug mode
- report.endpoint: the report engine entrypoint
- mail.smtp.auth: mail configurations (non usato)
- mail.smtp.starttls.enable: mail configurations (non usato)
- mail.smtp.host: mail configurations (non usato)
- mail.smtp.port: mail configurations (non usato)
- mail.username: mail configurations (non usato)
- mail.password: mail configurations (non usato)
- mail.from: mail configurations (non usato)
- mail.from.name: mail configurations (non usato)


# Getting Started
Una volta prelevata e portata in locale dal repository la componente ("git clone"), procedere con la modifica dei file di configurazione in base al proprio ambiente di deploy e quindi procedere al build.

Le indicazioni di maggior dettaglio riportate nel seguito sono state mantenute in inglese poichè più "dirette".
***************************************************************************************************************

Each third-party integration MUST be split into a separate `/integ-<name>` module, to be able to swap them as required. The implementations MAY be either POJOs, CDIs or EJBs, given the complexity of the integration; the simplest type of implementation SHOULD be used at all times.
Whenever a new integration is added, it MUST be added to the `/ear` and `/tar` modules to allow their usage.

The separation of concerns regarding the modules MUST be respected:
- `/lib` contains the libraries, to be used across multiple modules
- `/web` contains the REST entrypoints to the application
- `/ejb` contains the business logic
- `/integ-<name>` contains the logic for the integration to a third-party software
- `/ear` contains the packaging logic for an EAR archive
- `/tar` contains the packaging logic for an TAR archive, for automated distribution
- `/angular` contains the packaging for the front-end component in case the automation cannot deal with a full front-end distribution.

The REST endpoints are documented via [Swagger](https://swagger.io/specification/v2/) in YAML format. The YAML entrypoint is the `/docs/comonl.yml` file, and the definitions are split into partial files in the `/docs` subfolders.
The YAML generation is manual and not mediated by the Swagger tools.
********************************************************************


# Prerequisiti di sistema
Occorre per prima cosa predisporre il DB Schema utilizzato da questa componente, e popolarlo con i dati iniziali: si deve quindi prima aver completato installazione e configurazione della componente comonldb.

Nella directory "csi-lib" sono disponibili le librerie sviluppate da CSI e rese disponibili con le licenze indicate nel BOM.csv .

Occorre inoltre prevedere le opportune sostituzioni dei servizi esterni richiamati.

Per il "build" si è previsto di utilizzare Apache Maven. Nel seguito alcune indicazioni di dettaglio:

Compilazione: mvn clean package .

Nel caso si aggiunga un nuovo profile, esso deve essere referenziato nella sezione `<profiles>` del `pom.xml`, ed il file di `properties` corrispondente deve essere aggiunto nella directory `/profiles`.


# Installazione - Deployment

Installare il file "ear" generato con il build sul proprio ambiente WildFly: questo file "ear" contiene 2 file "war", uno per ciascuna componente "logica" (ovvero qualcosa di simile a "comonlweb-web.war" e "comonlweb-srv.war") oltre ad un "jar file" (EJB) trasversale rispetto alle 2 componenti logiche.

# Esecuzione dei test

Questa componente è stata sottoposta a vulnerability assessment.

# Versioning

Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

# Copyrights

© Copyright Regione Piemonte – 2022\
© Copyright CSI-Piemonte – 2022\

Questo stesso elenco dei titolari del software è anche riportato in Copyrights.txt .

# License
Il prodotto software è sottoposto alla licenza EUPL-1.2 o versioni successive.
SPDX-License-Identifier: EUPL-1.2-or-later

