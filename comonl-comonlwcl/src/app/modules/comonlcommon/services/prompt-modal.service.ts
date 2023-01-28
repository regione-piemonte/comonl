// import { PromptModalComponent } from '../components';

import { Injectable } from '@angular/core';
import { ListaDecodificaModalComponent, PromptModalComponent } from '../components';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Comunicazione, DecodificaGenerica, LavoratoreSilpEspanso } from '../../comonlapi';
import { FormAnagraficaLavoratoreModalComponent } from '../components/form-anagrafica-lavoratore-modal/form-anagrafica-lavoratore-modal.component';
import { FormModificaLavoratoreVardatoriModalComponent } from '../../comunicazione/components/sedi-lavoratori-vardatori/form-modifica-lavoratore-vardatori-modal/form-modifica-lavoratore-vardatori-modal.component';

@Injectable()
export class PromptModalService {

    constructor(private modalService: NgbModal) {
    }

    openPrompt(pTitle: string, pMessage: string, pYes: string, pNo: string, type: string) {
        const modalRef = this.modalService.open(PromptModalComponent, {size: 'xl', scrollable: true});

        modalRef.componentInstance.title = pTitle;
        modalRef.componentInstance.message = pMessage;
        modalRef.componentInstance.yesLabel = pYes;
        modalRef.componentInstance.noLabel = pNo;
        modalRef.componentInstance.callback = this.callback;
        modalRef.componentInstance.modal = modalRef;
        modalRef.componentInstance.type = type;
        return modalRef.result;
    }

    openDecodificaPrompt(pTitle: string, list: DecodificaGenerica[], typeSearch: string, filtroDiPartenza: DecodificaGenerica) {
        const modalRef = this.modalService.open(ListaDecodificaModalComponent, {size: 'xl', scrollable: true});

        modalRef.componentInstance.title = pTitle;

        modalRef.componentInstance.list = list;
        modalRef.componentInstance.filtroDiPartenza = filtroDiPartenza;
        modalRef.componentInstance.typeSearch = typeSearch;

        modalRef.componentInstance.callback = this.callbackDecodifica;

        modalRef.componentInstance.modal = modalRef;

        return modalRef.result;
    }

    openModalAnagraficaLavoratore(mode: string,comunicazione: Comunicazione, lavoratoreSilp?: LavoratoreSilpEspanso, checkDatiEssenziali?: boolean){
        const modalRef = this.modalService.open(FormAnagraficaLavoratoreModalComponent, {size: 'xl', scrollable: true, backdrop: 'static'});
        modalRef.componentInstance.lavoratoreSilp = lavoratoreSilp;
        modalRef.componentInstance.mode = mode;
        modalRef.componentInstance.modal = modalRef;
        modalRef.componentInstance.comunicazione = comunicazione;
        modalRef.componentInstance.checkDatiEssenziali = checkDatiEssenziali;
        modalRef.componentInstance.callback = this.callbackAnagraficaLAvoratoreModal;

        return modalRef.result;
    }

    /*vardatori*/
    openModalNodificaLavoratoreVardatori(idComunicazione: number, lavoratoreSilp?: LavoratoreSilpEspanso){
        const modalRef = this.modalService.open(FormModificaLavoratoreVardatoriModalComponent, {size: 'xl', scrollable: true, backdrop: 'static'});
        modalRef.componentInstance.lavoratoreSilp = lavoratoreSilp;
        modalRef.componentInstance.modal = modalRef;
        modalRef.componentInstance.idComunicazione = idComunicazione;
        modalRef.componentInstance.callback = this.callbackAnagraficaLAvoratoreModal;

        return modalRef.result;
    }


    callback(modal: NgbModalRef, val: boolean) {
        modal.close(val);
    }

    callbackDecodifica(modal: NgbModalRef, val?: DecodificaGenerica) {
        modal.close(val);
    }

    callbackAnagraficaLAvoratoreModal(modal: NgbModalRef, val?: string) {
        modal.close(val);
    }

}
