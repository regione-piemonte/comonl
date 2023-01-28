export * from './anagraficaDelegato.service';
import { AnagraficaDelegatoService } from './anagraficaDelegato.service';
export * from './anagraficaDelegato.serviceInterface'
export * from './common.service';
import { CommonService } from './common.service';
export * from './common.serviceInterface'
export * from './comunicazione.service';
import { ComunicazioneService } from './comunicazione.service';
export * from './comunicazione.serviceInterface'
export * from './comunicazioneControlli.service';
import { ComunicazioneControlliService } from './comunicazioneControlli.service';
export * from './comunicazioneControlli.serviceInterface'
export * from './decodifica.service';
import { DecodificaService } from './decodifica.service';
export * from './decodifica.serviceInterface'
export * from './delega.service';
import { DelegaService } from './delega.service';
export * from './delega.serviceInterface'
export * from './delegatoImpresa.service';
import { DelegatoImpresaService } from './delegatoImpresa.service';
export * from './delegatoImpresa.serviceInterface'
export * from './silp.service';
import { SilpService } from './silp.service';
export * from './silp.serviceInterface'
export * from './soggettoAbilitato.service';
import { SoggettoAbilitatoService } from './soggettoAbilitato.service';
export * from './soggettoAbilitato.serviceInterface'
export * from './system.service';
import { SystemService } from './system.service';
export * from './system.serviceInterface'
export * from './utente.service';
import { UtenteService } from './utente.service';
export * from './utente.serviceInterface'
export const APIS = [AnagraficaDelegatoService, CommonService, ComunicazioneService, ComunicazioneControlliService, DecodificaService, DelegaService, DelegatoImpresaService, SilpService, SoggettoAbilitatoService, SystemService, UtenteService];
