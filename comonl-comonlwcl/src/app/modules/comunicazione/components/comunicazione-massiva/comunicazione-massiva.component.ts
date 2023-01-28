/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { CommaxParametri } from './../../../comonlapi/model/commaxParametri';
import { Router } from '@angular/router';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { UtilitiesService } from 'src/app/services';
import { ComunicazioneService, Ruolo, CommonService } from 'src/app/modules/comonlapi';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';

@Component({
  selector: 'comonl-comunicazione-massiva',
  templateUrl: './comunicazione-massiva.component.html',
  styleUrls: ['./comunicazione-massiva.component.scss'],
})
export class ComunicazioneMassivaComponent implements OnInit {

  @ViewChild('validatedXmlFile', { static: false }) xmlFile: ElementRef;

  formUploadXML: FormGroup;
  ruolo: Ruolo;

  parametro: CommaxParametri;

  get f() {
    return this.formUploadXML.controls as any;
  }

  public fileXML: File;

  constructor(
    private utilitiesService: UtilitiesService,
    private comunicazioneService: ComunicazioneService,
    private commonService: CommonService,
    private alertMessageService: AlertMessageService,
    private comonlStorageService: ComonlStorageService
    ) {}

  async ngOnInit() {
    this.comonlStorageService.ruolo$.subscribe((el: Ruolo) => {
      this.ruolo = el;
    });
    this.initForm();
    this.parametro = await this.commonService.getParametroCommaxById(31).toPromise(); // PER VISUALIZZAZIONE MESSAGGIO IN MASCHERA
    console.log('parametro: ' + JSON.stringify(this.parametro));
    console.log('valore parametro: ' + this.parametro.valoreParametro);
    // this.f.parametro.patchValue(this.parametro.valoreParametro);
  }

  private initForm() {
    this.formUploadXML = new FormGroup({
      email: new FormControl(),
      xmlFile: new FormControl()
    });
  }

  onClickReset() {
    this.formUploadXML.reset();
    this.formUploadXML.enable();
    this.fileXML = null;
  }

  async onClickSendFile(isVerifica: boolean) {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    console.log(this.fileXML);
    try {
       const res = await this.comunicazioneService.uploadComunicazioni(this.fileXML, this.f.email.value, this.fileXML.name, isVerifica,
                                                                       this.ruolo.ilRuolo, this.ruolo.codiceFiscaleUtente, this.ruolo.dsCognome,
                                                                       this.ruolo.dsNome, this.ruolo.codiceFiscaleAzienda, this.ruolo.consulenteRespo,
                                                                       this.ruolo.amministratore,
                                                                       this.ruolo.operatoreProvinciale,this.ruolo.delegatoRespo,this.ruolo.legaleRappresentante,this.ruolo.personaAutorizzata, this.ruolo.email,
                                                                        'response').toPromise();
       // const fileName = Utils.extractFileNameFromContentDisposition(res.headers.get('Content-Disposition'));
       // this.utilitiesService.downloadBlobFile(fileName, res.body);
       if (res) {
         const msg = res.body;
         this.alertMessageService.setSuccessSingleMsg(msg);
       }
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setErrorMsg(e.error);
      } else {
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  emptyFile() {
    this.fileXML = undefined;
    this.xmlFile.nativeElement = undefined;
  }

  chooseFile(event) {
    this.fileXML = event.target.files[0];
  }

  validateFile(nomeFile: string) {
    if (nomeFile === 'fileXml' && this.fileXML) {
      if (this.fileXML.name.toLowerCase().endsWith('.xml') || this.fileXML.name.toLowerCase().endsWith('.zip')) {
        return false;
      } else {
        return true;
      }
    }
  }
}
