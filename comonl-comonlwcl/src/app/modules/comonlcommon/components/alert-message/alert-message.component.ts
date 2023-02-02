/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { TYPE_ALERT } from 'src/app/constants';
import { ApiError } from 'src/app/modules/comonlapi';
import { UtilitiesService } from 'src/app/services';
import { AlertMessageService } from '../../services/alert-message.service';

@Component({
  selector: 'comonl-alert-message',
  templateUrl: './alert-message.component.html',
  styleUrls: ['./alert-message.component.scss']
})
export class AlertMessageComponent implements OnInit,OnDestroy {

  
  show: boolean;
  typeMesage: string;
  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="top"]';
  private static readonly MODAL_SCROLL_TARGET = 'em[data-scroll-marker="modal-top"]';

  @Input() flgModalTarget: boolean;
  listDangerMsg: ApiError[] = [];
  listSuccessMsg: ApiError[] = [];
  listWarningMsg: ApiError[] = [];
  listInfoMsg: ApiError[] = [];
  singleSuccessMessage: string;
  singleErrorMessage: string;

  constructor(
    private alertMessageService: AlertMessageService,
    private utilitiesService: UtilitiesService,
    private translateService: TranslateService,
  ) { }

  ngOnInit() {
    this.alertMessageService.modelMsg$.subscribe(item => {
      if(item){
        this.typeMesage = item.typeMessage;
        switch(this.typeMesage){
          case TYPE_ALERT.SUCCESS: {
            this.listSuccessMsg = item.listMessages;
            this.show = true;
            this.scrollTo();
            break;
          }
          case TYPE_ALERT.DANGER: {
            this.listDangerMsg = item.listMessages;
            this.show = true;
            this.scrollTo();
            break;
          }
          case TYPE_ALERT.INFO: {
            this.listInfoMsg = item.listMessages;
            this.show = true;
            this.scrollTo();
            break;
          }
          case TYPE_ALERT.WARNING: {
            this.listWarningMsg = item.listMessages;
            this.show = true;
            this.scrollTo();
            break;
          }
          default: {
            this.reset();
            break;
          }
        }
      }
    });
  }

  private scrollTo(){
    this.utilitiesService.scrollTo(!this.flgModalTarget ? AlertMessageComponent.SCROLL_TARGET : AlertMessageComponent.MODAL_SCROLL_TARGET);
  }

  getMessage(error: ApiError): string{
    if(error.code === "SYS-SYS-E-0012"){
      return this.translateService.instant('MESSAGES.SYS-SYS-E-0012',{
        parameter: error.params.parameter
      });
    }else if(error.code === "SYS-SYS-E-0003"){
      return this.translateService.instant('MESSAGES.SYS-SYS-E-0003',{
        parameter: error.params.parameter
      });
    
    }else if(error.code === "SYS-SYS-E-0002"){
      return this.translateService.instant('MESSAGES.SYS-SYS-E-0002',{
        error: error.params.error
      });
    }else if(error.code === "SYS-SYS-E-0005"){
      return this.translateService.instant('MESSAGES.SYS-SYS-E-0005',{
        fieldname: error.params.fieldname,
        operator: error.params.operator,
        value: error.params.value
      });
    }else if(error.code === "COM-COM-E-0003"){
      return this.translateService.instant('MESSAGES.COM-COM-E-0003',{
        parameter: error.params.parameter
      });
    }else if(error.code ==="COM-COM-E-0006"){
      return this.translateService.instant('MESSAGES.COM-COM-E-0006',{
        parameter: error.params.parameter
      });
    }else if(error.code === "COM-COM-E-0015"){
      return this.translateService.instant('MESSAGES.COM-COM-E-0015',{
        tipoComunicazione: error.params.tipoComunicazione,
        dtRiferimento: error.params.dtRiferimento,
        codiceRegionale: error.params.codiceRegionale
      });
    }else if(error.code === "COM-COM-E-0017"){
      return this.translateService.instant('MESSAGES.COM-COM-E-0017',{
        statoComunicazione: error.params.statoComunicazione
      });
    }else if(error.code === "COM-RAP-E-0182"){
      return this.translateService.instant('MESSAGES.COM-RAP-E-0182',{
        parameter: error.params.parameter
      });
    }else if(error.code === "COM-RAP-W-0055"){
      return this.translateService.instant('MESSAGES.COM-RAP-W-0055',{
        parameter: error.params.parameter
      });
    }else if(error.code === "COM-RAP-W-0153"){
      return this.translateService.instant('MESSAGES.COM-RAP-W-0153',{
        parameter: error.params.parameter
      });
    }else if(error.code === "COM-RAP-W-0146"){
      return this.translateService.instant('MESSAGES.COM-RAP-W-0146',{
        parameter: error.params.parameter
      });
    }else if(error.code === "COM-RAP-W-0147"){
      return this.translateService.instant('MESSAGES.COM-RAP-W-0147',{
        parameter: error.params.parameter
      });
    }else if(error.code === "COM-RAP-W-0148"){
      return this.translateService.instant('MESSAGES.COM-RAP-W-0148',{
        parameter: error.params.parameter
      });
    }else if(error.code === "COM-RAP-W-0149"){
      return this.translateService.instant('MESSAGES.COM-RAP-W-0149',{
        parameter: error.params.parameter
      });
    }else if(error.code === "COM-RAP-W-0150"){
      return this.translateService.instant('MESSAGES.COM-RAP-W-0150',{
        parameter: error.params.parameter
      });
    }else if(error.code === "COM-RAP-W-0151"){
      return this.translateService.instant('MESSAGES.COM-RAP-W-0151',{
        parameter: error.params.parameter
      });
    }else if(error.code === "COM-RAP-W-0152"){
      return this.translateService.instant('MESSAGES.COM-RAP-W-0152',{
        parameter: error.params.parameter
      });
    }else if(error.code === "COM-RAP-W-0153"){
      return this.translateService.instant('MESSAGES.COM-RAP-W-0153',{
        parameter: error.params.parameter
      });
    }else if(error.code === "COM-COM-E-0022"){
      return this.translateService.instant('MESSAGES.COM-COM-E-0022',{
        field: error.params.field,
        code: error.params.code,
        desc: error.params.desc
      });
    }else if(error.code === "SYS-SYS-E-0001"){
      return this.translateService.instant('MESSAGES.SYS-SYS-E-0001',{
        error: error.params.error
      });
    }else if(error.code === "SYS-SYS-E-0002"){
      return this.translateService.instant('MESSAGES.SYS-SYS-E-0002',{
        error: error.params.error
      });
    }else if(error.code === "COM-COM-E-1027"){
      return this.translateService.instant('MESSAGES.COM-COM-E-1027',{
        tipo: error.params.tipo
      });
    }else{
      return error.errorMessage;
    }
  }

  ngOnDestroy(): void {
    this.reset();
    this.alertMessageService.setmodelMsg(null);
  }

  private reset(){
    this.show = false;
    this.listDangerMsg = [];
    this.listSuccessMsg = [];
    this.listWarningMsg = [];
    this.listInfoMsg = [];
    this.typeMesage = null;
  }

}

export interface ModelMessage {
  typeMessage: string;
  listMessages: ApiError[];
  scrollTarget?: string
}
