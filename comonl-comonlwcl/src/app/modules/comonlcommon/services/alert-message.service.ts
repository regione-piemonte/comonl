/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { TYPE_ALERT } from 'src/app/constants';
import { ApiError } from '../../comonlapi';
import { ModelMessage } from '../components/alert-message/alert-message.component';

@Injectable({
  providedIn: 'root'
})
export class AlertMessageService {

  private modelMsg: BehaviorSubject<ModelMessage> = new BehaviorSubject(null);

  get modelMsg$(): Observable<ModelMessage> { return this.modelMsg.asObservable(); }


  constructor() { }

  public setmodelMsg(model: ModelMessage){
    this.modelMsg.next(model);
  }

  public unsubscribeMsg(): void{
    this.modelMsg.unsubscribe();
  }

  public setSuccessMsg(listError: ApiError[]){

      const model: ModelMessage = {
        typeMessage: TYPE_ALERT.SUCCESS,
        listMessages: listError
      }
      this.modelMsg.next(model);
  }

  public setErrorMsg(listError: ApiError[]){
      const model: ModelMessage = {
        typeMessage: TYPE_ALERT.DANGER,
        listMessages: listError
      }
      this.modelMsg.next(model);
  }

  public setInfoMsg(listError: ApiError[]){

      const model: ModelMessage = {
        typeMessage: TYPE_ALERT.INFO,
        listMessages: listError
      }
      this.modelMsg.next(model);
  }

  public setWarningMsg(listError: ApiError[]){
      const model: ModelMessage = {
        typeMessage: TYPE_ALERT.WARNING,
        listMessages: listError
      }
      this.modelMsg.next(model);
  }

  public setSuccessSingleMsg(msg: string){
    const apiError: ApiError = {
      errorMessage: msg
    }
    const listMessages: ApiError[] = [apiError];
    const model: ModelMessage = {
      typeMessage: TYPE_ALERT.SUCCESS,
      listMessages: listMessages
    }
    this.modelMsg.next(model);
  }

  public setSingleErrorMsg(msg: string){
    const apiError: ApiError = {
      errorMessage: msg
    }
    const listMessages: ApiError[] = [apiError];
    const model: ModelMessage = {
      typeMessage: TYPE_ALERT.DANGER,
      listMessages: listMessages
    }
    this.modelMsg.next(model);
  }

  public setSingleInfoMsg(msg: string){
    const apiError: ApiError = {
      errorMessage: msg
    }
    const listMessages: ApiError[] = [apiError];
    const model: ModelMessage = {
      typeMessage: TYPE_ALERT.INFO,
      listMessages: listMessages
    }
    this.modelMsg.next(model);
  }

  public setSingleWarningMsg(msg: string){
    const apiError: ApiError = {
      errorMessage: msg
    }
    const listMessages: ApiError[] = [apiError];
    const model: ModelMessage = {
      typeMessage: TYPE_ALERT.WARNING,
      listMessages: listMessages
    }
    this.modelMsg.next(model);
  }

  public emptyMessages(){
    const modelMsg: ModelMessage = {
      typeMessage: "",
      listMessages: [] = []
    }
    this.modelMsg.next(modelMsg);
  }

}
