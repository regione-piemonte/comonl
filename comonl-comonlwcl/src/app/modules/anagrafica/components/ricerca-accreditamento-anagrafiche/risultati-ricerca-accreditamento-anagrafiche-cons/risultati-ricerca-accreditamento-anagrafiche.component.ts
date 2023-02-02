/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';
import { TranslateService } from '@ngx-translate/core';
import { PaginationDataChange } from 'src/app/models';
import { AnagraficaAzienda, AnagraficaDelegato, AnagraficaDelegatoService, DelegatoImpresaService, FormRicercaAccreditamentoAnagrafiche, PagedResponseRicercaAccreditamentoAzienda, PagedResponseRicercaAccreditamentoConsulente, SetDataAnnullamentoSoggettoAbilitatoRes, SoggettoAbilitatoService } from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { UtilitiesService } from 'src/app/services';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-risultati-ricerca-accreditamento-anagrafiche',
  templateUrl: './risultati-ricerca-accreditamento-anagrafiche.component.html',
  styleUrls: ['./risultati-ricerca-accreditamento-anagrafiche.component.scss']
})
export class RisultatiRicercaAccreditamentoAnagraficheComponent implements OnInit {

  @Input() tipoRicerca: string;
  @Input() pagedResponseConsulente: PagedResponseRicercaAccreditamentoConsulente;
  @Input() pagedResponseAzienda: PagedResponseRicercaAccreditamentoAzienda;
  @Input() currentPaginationData: PaginationDataChange;
  @Input() formRicerca: FormRicercaAccreditamentoAnagrafiche;

  @Output() readonly changePaginationData = new EventEmitter<PaginationDataChange>();
  @Output() readonly ripristinaAnnulla = new EventEmitter<PaginationDataChange>();

  constructor(
    private router: Router,
    private utilitiesService: UtilitiesService,
    private anagraficaDelegatoService: AnagraficaDelegatoService,
    private promptModalService: PromptModalService,
    private translateService: TranslateService,
    private soggettoAbilitatoService: SoggettoAbilitatoService,
    private alertMessaggeService: AlertMessageService,
    private delegatoImpresaService: DelegatoImpresaService
  ) { }

  ngOnInit() {
    const locale = this.translateService.currentLang;
  }

  onChangePaginationData(event: PaginationDataChange,flgAnnulla?: boolean) {
    this.currentPaginationData = event;
    this.changePaginationData.emit(event);
  }

  goToDettaglioCons(anagraficaDelegato: AnagraficaDelegato,mode){
      this.router.navigate(['./accreditamento-anagrafiche-consulente'], { state: { mode: mode, anagraficaDelegato: anagraficaDelegato } });
  }

  goToDettaglioStudioPAzienda(el: AnagraficaAzienda,mode){
    const tipologia = el.tipologia;
    if(tipologia === "AZIENDA"){
      this.router.navigate(['./anagrafica-dati-azienda'], { state: { mode: mode, cfImpresa: el.codiceFiscale } });
    }else{
      this.router.navigate(['./anagrafica-studio-professionale'], { state: { mode: mode, idSoggettoAbilitato: Number(el.idChiave) } });
    }
  }


  async onClickAnnullaRipristinaAnagraficaDelegato(cfDelegato:string, tipoAnagrafica: string,typeMessage: string){
    const title = this.translate(marker('SIDEBAR.ACCREDITAMENTO_ANAGRAFICHE'));
    const message = this.translate(marker(typeMessage));
    const pYes = this.translate(marker('APP.YES'));
    const pNo = this.translate(marker('APP.NO'));

    const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');

    if(userChoice){
      this.setDataAnnullamentoAnagraficaDelegato(cfDelegato,tipoAnagrafica);
    }

  }


  async onClickAnnullaRipristinaSoggAbilitatoDelegatoImp(el: AnagraficaAzienda,typeMessage: string){
    const title = this.translate(marker('SIDEBAR.ACCREDITAMENTO_ANAGRAFICHE'));
    const message = this.translate(marker(typeMessage));
    const pYes = this.translate(marker('APP.YES'));
    const pNo = this.translate(marker('APP.NO'));

    const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');

    if(userChoice){
      const tipologia = el.tipologia;
      if(tipologia === "STUDIO_PROFESSIONALE"){
        this.setDataAnnullamentoSoggettoAbilitato(Number(el.idChiave),!Utils.isNullOrUndefined(el.dataAnnullamento));
      }else{
        this.setDataAnnullamentoDelegatoImpresa(el.codiceFiscale,Utils.isNullOrUndefined(el.dataAnnullamento));
      }
    }
  }

  private translate(key: string) {
    return this.translateService.instant(key);
  }

  async setDataAnnullamentoAnagraficaDelegato(cfDelegato:string, tipoAnagrafica: string){
    
    this.utilitiesService.showSpinner();
    this.alertMessaggeService.emptyMessages();
    try{
      const res = await this.anagraficaDelegatoService.setDataAnnullamentoCDE(cfDelegato,tipoAnagrafica).toPromise();
      this.ripristinaAnnulla.emit(this.currentPaginationData);
      console.log('success: '+res);
    }catch(e){
      if(e.error && e.error.length > 0){
        this.alertMessaggeService.setErrorMsg(e.error);
      }else{
        this.alertMessaggeService.setSingleErrorMsg(e);
      }
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }

  async setDataAnnullamentoSoggettoAbilitato(idSoggettoAbilitato: number,autorizzazione: boolean){
    
    this.alertMessaggeService.emptyMessages();
    try{
      const res: SetDataAnnullamentoSoggettoAbilitatoRes = await this.soggettoAbilitatoService.setDataAnnullamento(idSoggettoAbilitato,autorizzazione).toPromise();
      console.log(res);
      if(res){
        if(res.warnings && res.warnings.length > 0){
          const warning: string = res.warnings[0].errorMessage;
          const title = this.translate(marker('SIDEBAR.ACCREDITAMENTO_ANAGRAFICHE'));
          const message = this.translate(marker(warning));
          const pYes = this.translate(marker('APP.YES'));
          const pNo = this.translate(marker('APP.NO'));
          const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');
          if(userChoice){
            this.utilitiesService.showSpinner();
            const res = await this.soggettoAbilitatoService.setDataAnnullamento(idSoggettoAbilitato,true).toPromise();
            if(res){
              this.ripristinaAnnulla.emit(this.currentPaginationData);
            }
          }else{
            return;
          }
        }
        
        this.ripristinaAnnulla.emit(this.currentPaginationData);
      }
    }catch(e){
      if(e.error && e.error.length > 0){
        this.alertMessaggeService.setErrorMsg(e.error);
      }else{
        this.alertMessaggeService.setSingleErrorMsg(e);
      }
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }

  async setDataAnnullamentoDelegatoImpresa(cfImpresa: string,flgAnnullamento: boolean){
    this.alertMessaggeService.emptyMessages();
    try{
      let res
      if(flgAnnullamento){
        res = await this.delegatoImpresaService.setDataAnnullamentoAllCfImpresa(cfImpresa,false,flgAnnullamento).toPromise();
      }else{
        res = await this.delegatoImpresaService.setDataAnnullamentoAllCfImpresa(cfImpresa,true,flgAnnullamento).toPromise();
        this.ripristinaAnnulla.emit(this.currentPaginationData);
      }
      console.log(res);
      if(res){
        if(res.warning && res.warning.length > 0){
          const warning: string = res.warning;
          const title = this.translate(marker('SIDEBAR.ACCREDITAMENTO_ANAGRAFICHE'));
          const message = this.translate(marker(warning));
          const pYes = this.translate(marker('APP.YES'));
          const pNo = this.translate(marker('APP.NO'));
          const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');
          if(userChoice){
            this.utilitiesService.showSpinner();
            await this.delegatoImpresaService.setDataAnnullamentoAllCfImpresa(cfImpresa,true,flgAnnullamento).toPromise();
            this.ripristinaAnnulla.emit(this.currentPaginationData);
          
          }else{
            return;
          }
        }
        
        this.ripristinaAnnulla.emit(this.currentPaginationData);
      }
    }catch(e){
      this.alertMessaggeService.setErrorMsg(e.error);
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }


  async onClickEsportAzienda(formatoFile: string){
    this.utilitiesService.showSpinner();
    this.alertMessaggeService.emptyMessages();
    try{
      const res = await this.anagraficaDelegatoService.stampaElencoStudiAziende(formatoFile,this.formRicerca,'response').toPromise();
      const fileName = Utils.extractFileNameFromContentDisposition(res.headers.get('Content-Disposition'));
      this.utilitiesService.downloadBlobFile(fileName, res.body);
    }catch(e){
      this.alertMessaggeService.setSingleErrorMsg(e);
      if(e.error && e.error.length){
        this.alertMessaggeService.setErrorMsg(e.error);
      }
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }

  async onClickEsportaAnagraficaDelegato(formatoFile: string){
    this.utilitiesService.showSpinner();
    this.alertMessaggeService.emptyMessages();
    try{
      const res = await this.anagraficaDelegatoService.stampaElencoAnagraficheDelegato(formatoFile,this.formRicerca,'response').toPromise();
      const fileName = Utils.extractFileNameFromContentDisposition(res.headers.get('Content-Disposition'));
      this.utilitiesService.downloadBlobFile(fileName, res.body);
    }catch(e){
      this.alertMessaggeService.setSingleErrorMsg(e);
      if(e.error && e.error.length){
        this.alertMessaggeService.setErrorMsg(e.error);
      }
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }

}
