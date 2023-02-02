/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { ModalInvioComunicazioneComponent } from './modules/comunicazione/components/modal-invio-comunicazione/modal-invio-comunicazione.component';
import { ModalNuovaVardatoriComponent } from './modules/comunicazione/components/modal-nuova-vardatori/modal-nuova-vardatori.component';
import { APP_BASE_HREF, CommonModule, registerLocaleData } from '@angular/common';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import localeIt from '@angular/common/locales/it';
import { APP_INITIALIZER, Injector, NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { NgbTabsetModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { SidebarModule } from 'ng-sidebar';
import { NgxMaskModule } from 'ngx-mask';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AppComponent } from 'src/app/app.component';
import {
  ConfigurationService, ErrorHandlerInterceptorService, JsonDateInterceptorService, LogService,
  SessionStorageService, SidebarService, TranslationAppInitializerFactory, TranslationHttpLoaderFactory, UtilitiesService
} from 'src/app/services';
import { ErrorHandlerComponent, ErrorPageComponent, FooterComponent, HeaderComponent } from './components';
import { HomePageComponent } from './components/home-page/home-page.component';
import { SceltaRuoloComponent } from './components/scelta-ruolo/scelta-ruolo.component';
import { AnagraficaModule } from './modules/anagrafica/anagrafica.module';
import { ApiModule } from './modules/comonlapi/api.module';
import { BASE_PATH } from './modules/comonlapi/variables';
import { ComonlcommonModule } from './modules/comonlcommon/comonlcommon.module';
import { LOGOUT_URL, USER_MANUAL_URL } from './modules/comonlcommon/variables';
import { ModalNuovaComunicazioneComponent } from './modules/comunicazione/components/modal-nuova-comunicazione/modal-nuova-comunicazione.component';
import { ModalRicercaAziendaComponent } from './modules/comunicazione/components/modal-ricerca-azienda/modal-ricerca-azienda.component';
import { ComunicazioneModule } from './modules/comunicazione/comunicazione.module';
import { ComonlStorageService } from './services/storage/comonl-storage.service';


registerLocaleData(localeIt);

@NgModule({
  declarations: [
    SceltaRuoloComponent,
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ErrorPageComponent,
    ErrorHandlerComponent,
    HomePageComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ComonlcommonModule,
    ComunicazioneModule,
    AnagraficaModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    SidebarModule.forRoot(),
    NgxMaskModule.forRoot(),
    TranslateModule.forRoot({
      loader: { provide: TranslateLoader, useFactory: TranslationHttpLoaderFactory, deps: [HttpClient] }
    }),
    NgxSpinnerModule,
    HttpClientModule,
    ApiModule,
    AppRoutingModule,
    NgbTabsetModule
  ],
  entryComponents: [ModalNuovaComunicazioneComponent, ModalRicercaAziendaComponent, ModalNuovaVardatoriComponent, ModalInvioComunicazioneComponent],
  providers: [
    UtilitiesService,
    SidebarService,
    ComonlStorageService,
    { provide: BASE_PATH, useFactory: ConfigurationService.getBERootUrl },
    { provide: LOGOUT_URL, useFactory: ConfigurationService.getSSOLogoutURL },
    { provide: APP_BASE_HREF, useValue: ConfigurationService.getBaseHref() },
    { provide: USER_MANUAL_URL, useValue: ConfigurationService.getUserManualUrl() },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorHandlerInterceptorService, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: JsonDateInterceptorService, multi: true },
    // { provide: HTTP_INTERCEPTORS, useClass: SpinnerLoaderInterceptor, multi: true },
    {
      provide: APP_INITIALIZER,
      useFactory: TranslationAppInitializerFactory,
      deps: [ TranslateService, LogService, SessionStorageService, Injector ],
      multi: true,
    }
    // , RouteService 
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
