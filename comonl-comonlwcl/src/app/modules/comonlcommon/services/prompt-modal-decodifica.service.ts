/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
// import { PromptModalComponent } from '../components';

import { Injectable } from '@angular/core';

import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ListaDecodificaModalComponent } from '../components';


@Injectable()
export class PromptModalDecodificaService {

    constructor(private modalService: NgbModal) {
    }

    openPrompt(pTitle: string) {
        const modalRef = this.modalService.open(ListaDecodificaModalComponent, {size: 'xl', scrollable: true});

        modalRef.componentInstance.title = pTitle;
        // modalRef.componentInstance.message = pMessage;
        // modalRef.componentInstance.yesLabel = pYes;
        // modalRef.componentInstance.noLabel = pNo;
        modalRef.componentInstance.callback = this.callback;
        modalRef.componentInstance.modal = modalRef;
        //modalRef.componentInstance.type = type;
        return modalRef.result;
    }

    callback(modal: NgbModalRef, val?: boolean) {
        modal.close(val);
    }

}
