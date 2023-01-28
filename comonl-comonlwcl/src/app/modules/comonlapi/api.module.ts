import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { AnagraficaDelegatoService } from './api/anagraficaDelegato.service';
import { CommonService } from './api/common.service';
import { ComunicazioneService } from './api/comunicazione.service';
import { ComunicazioneControlliService } from './api/comunicazioneControlli.service';
import { DecodificaService } from './api/decodifica.service';
import { DelegaService } from './api/delega.service';
import { DelegatoImpresaService } from './api/delegatoImpresa.service';
import { SilpService } from './api/silp.service';
import { SoggettoAbilitatoService } from './api/soggettoAbilitato.service';
import { SystemService } from './api/system.service';
import { UtenteService } from './api/utente.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    AnagraficaDelegatoService,
    CommonService,
    ComunicazioneService,
    ComunicazioneControlliService,
    DecodificaService,
    DelegaService,
    DelegatoImpresaService,
    SilpService,
    SoggettoAbilitatoService,
    SystemService,
    UtenteService ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        };
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule,
                 @Optional() http: HttpClient) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
        }
        if (!http) {
            throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
            'See also https://github.com/angular/angular/issues/20575');
        }
    }
}
