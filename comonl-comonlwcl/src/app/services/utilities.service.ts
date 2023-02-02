/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { DOCUMENT, formatNumber } from '@angular/common';
import { Inject, Injectable, LOCALE_ID } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';

import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Utils } from 'src/app/utils';
import { isArray, isNullOrUndefined } from 'util';
import { CONTROL_STATE } from '../constants';
import { ApiError, CommonService, Ruolo } from '../modules/comonlapi';
import { ErrorModalComponent } from '../modules/comonlcommon/components/error-modal/error-modal.component';



@Injectable({
  providedIn: 'root'
})
export class UtilitiesService {



  private static readonly FILE_DOWNLOAD_TIMEOUT = 60000;

  private static readonly TOASTR_OPTION = {
    timeOut: 10000,
    positionClass: 'toast-top-full-width',
    enableHtml: true,
    closeButton: true
  };
  private spinnerCount = {};

  constructor(
    private toastrService: ToastrService,
    private translateService: TranslateService,
    private ngxSpinnerService: NgxSpinnerService,
    private modalService: NgbModal,
    private commonService: CommonService,
    @Inject(DOCUMENT) private document: Document

  ) { }

  showToastrInfoMessage(message: string, title = '') {
    this.toastrService.info(message, title, UtilitiesService.TOASTR_OPTION);
  }

  showToastrErrorMessage(message: string, title = '') {
    this.toastrService.error(message, title, UtilitiesService.TOASTR_OPTION);
    // this.promptModalService.openPrompt(title, message, 'si', 'no', 'ERROR');
    // this.openError(title, message);
  }

  openError(pTitle: string, pMessage: string) {
    const modalRef = this.modalService.open(ErrorModalComponent, {size: 'lg', scrollable: true, windowClass: 'dark-modal'});

    modalRef.componentInstance.title = pTitle;
    modalRef.componentInstance.message = pMessage;

    return modalRef.result;
  }

  handleApiErrors(e: any, title: string, wrapperKey: string = 'errori') {
    if (!e || !e.error) {
      return;
    }
    if (Utils.isApiErrorLike(e.error)) {
      e.error = [e.error];
    }
    if (!isArray(e.error) || !Utils.areApiErrorLike(e.error)) {
      return;
    }
    const groupedByGroup = Utils.groupBy(e.error as ApiError[], 'group');

    Object.entries(groupedByGroup)
      .forEach(([key, value]) => {
        const translated = value.map(ae => ({code: ae.code, msg: this.translateService.instant(`MESSAGES.${ae.code}`, ae.params)}));
        if (!key) {
          return this.showToastrErrorMessage(
            translated.map(el => `${el.code} - ${el.msg}`).join('<br>'),
            this.translateService.instant(title)
          );
        }

        const groupParams = value[0].groupParams;
        // aggiungo "wrapperKey" (errori)
        groupParams[wrapperKey] = `<ul><li>${translated.map(el => el.msg).join('</li><li>')}</li></ul>`;

        const message = `${key} - ${this.translateService.instant(`MESSAGES.${key}`, groupParams)}`;
        // message = `${key} - ${this.translateService.instant(`MESSAGES.${key}`, { [wrapperKey]: `<ul><li>${translated.map(el => el.msg).join('</li><li>')}</li></ul>` })}`;

        return this.showToastrErrorMessage(
          message,
          this.translateService.instant(title)
        );
      });
  }

  handleApiInfos(e: any, title: string, wrapperKey: string = 'errori') {
    if (!e || !e.error) {
      return;
    }
    if (Utils.isApiErrorLike(e.error)) {
      e.error = [e.error];
    }
    if (!isArray(e.error) || !Utils.areApiErrorLike(e.error)) {
      return;
    }
    const groupedByGroup = Utils.groupBy(e.error as ApiError[], 'group');

    Object.entries(groupedByGroup)
      .forEach(([key, value]) => {
        const translated = value.map(ae => ({code: ae.code, msg: this.translateService.instant(`MESSAGES.${ae.code}`, ae.params)}));
        if (!key) {
          return this.showToastrInfoMessage(
            translated.map(el => `${el.code} - ${el.msg}`).join('<br>'),
            this.translateService.instant(title)
          );
        }

        const groupParams = value[0].groupParams;
        // aggiungo "wrapperKey" (errori)
        groupParams[wrapperKey] = `<ul><li>${translated.map(el => el.msg).join('</li><li>')}</li></ul>`;

        const message = `${key} - ${this.translateService.instant(`MESSAGES.${key}`, groupParams)}`;
        // message = `${key} - ${this.translateService.instant(`MESSAGES.${key}`, { [wrapperKey]: `<ul><li>${translated.map(el => el.msg).join('</li><li>')}</li></ul>` })}`;

        return this.showToastrInfoMessage(
          message,
          this.translateService.instant(title)
        );
      });
  }

  showSpinner(name = 'main-spinner') {
    if (!this.spinnerCount[name]) {
      this.spinnerCount[name] = 0;
    }
    if (this.spinnerCount[name] === 0) {
      this.ngxSpinnerService.show(name);
    }
    this.spinnerCount[name]++;
  }

  hideSpinner(name = 'main-spinner') {
    if (!this.spinnerCount[name]) {
      return;
    }
    if (this.spinnerCount[name] === 0) {
      return;
    }
    this.spinnerCount[name]--;
    if (this.spinnerCount[name] === 0) {
      this.ngxSpinnerService.hide(name);
    }
  }

  async wait(ms: number): Promise<void> {
    return new Promise(resolve => setTimeout(() => resolve(), ms));
  }

  downloadFile(fileName: string, data: any, type: string, external = false) {
    const blob = new Blob([data], { type });
    const url = window.URL.createObjectURL(blob);
    if (external) {
      return this.doDownloadExternalFile(url, true);
    }
    this.doDownloadInternalFile(fileName, url, true);
  }

  downloadBlobFile(fileName: string, data: Blob, external = false) {
    const url = window.URL.createObjectURL(data);
    if (external) {
      return this.doDownloadExternalFile(url, true);
    }
    this.doDownloadInternalFile(fileName, url, true);
  }

  /**
   * download di un file in Base64
   * @param data file da scaricare
   */
  downloadBase64File(fileName: string, data: string, type: string, external = false) {
    const url = `data:${type};base64,${data}`;
    if (external) {
      return this.doDownloadExternalFile(url, false);
    }
    this.doDownloadInternalFile(fileName, url, false);
  }

  private doDownloadExternalFile(url: string, cleanupObjectUrl = true) {
    const pwa = window.open(url, '_blank');
    if (!pwa || pwa.closed || typeof pwa.closed === 'undefined') {
      alert(this.translateService.instant('ERROR.POPUP_BLOCKED'));
      return;
    }
    if (cleanupObjectUrl) {
      // Revoke URL
      setTimeout(() => window.URL.revokeObjectURL(url), UtilitiesService.FILE_DOWNLOAD_TIMEOUT);
    }
  }

  private doDownloadInternalFile(fileName: string, url: string, cleanupObjectUrl = true) {
    const downloadLink = document.createElement('a');
    downloadLink.href = url;
    downloadLink.download = fileName;
    downloadLink.click();
    if (cleanupObjectUrl) {
      // Revoke URL
      setTimeout(() => window.URL.revokeObjectURL(url), UtilitiesService.FILE_DOWNLOAD_TIMEOUT);
    }
  }

 

  async getRuoli(): Promise<Ruolo[]> {
    return this.commonService.getRuoli().pipe(
      map((result: Ruolo[]) => {
        return result;
      }
      ),
      catchError(() => of([]))
    ).toPromise();
  }

  scrollTo(selector: string): void {
    // Invokes next tick
    setTimeout(() => {
      const scrollElement = this.document.querySelector(selector);
      if (scrollElement) {
        scrollElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    });
  }

  getTimeFormat(val: number): string {
    return Utils.getTimeFormat(val);
  }

  /*
   * cleanValue vale true se insieme al cambiamento di stato dell' AbstractControl
   * si vuole anche resettare il suo valore
   */
  changeControlState(control: AbstractControl,state: string,cleanValue: boolean){
    control[state]();
    if(cleanValue){
      control.reset();
    }
}

}
