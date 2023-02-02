/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { LogService } from 'src/app/services/log.service';
import { isArray } from 'util';
import { Utils } from 'src/app/utils';
import { Router } from '@angular/router';
import { ApiError } from 'src/app/modules/comonlapi';

export interface ComonlHttpErrorResponse {
  error?: ApiError;
}

interface ApiErrorWrapper {
  error: ApiError[];
}

@Injectable()
export class ErrorHandlerInterceptorService implements HttpInterceptor {

  constructor(
    private logService: LogService,
    private _router: Router,
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const interceptor = this;
    return next.handle(req).pipe(
      catchError((err: any) => this.handleError(interceptor, req, err))
    );
  }

  private handleError(interceptor: ErrorHandlerInterceptorService, req: HttpRequest<any>, err: any): Observable<any> {
    this.logService.debug(this.constructor.name, 'ErrorHandlerInterceptorService - handleError', err);
    this.logService.debug(this.constructor.name, 'ErrorHandlerInterceptorService - handleError', "+++ status: " + err.status);
    let errorWrapper: ApiErrorWrapper;

    // Duck typing - Check if quacks!
    // Quacks as an ApiError?
    if (err.error && Utils.isApiErrorLike(err.error)) {
      errorWrapper = {
        ...err,
        error: [
          err.error
        ]
      };
    } else if (isArray(err.error) && Utils.areApiErrorLike(err.error)) {
      // Quacks as an array of ApiErrors
      errorWrapper = err;
    } else {

      if (err.status === 0 || err.status === 401) {
        this.logService.debug(this.constructor.name, 'ErrorHandlerInterceptorService - handleError', "+++ sessione scaduta");
        console.log('sessione scaduta. eseguo il logout locale');

        var apiError401: ApiError = {};
        apiError401.code = "SYS-SYS-E-0007";
        apiError401.params = {};
        interceptor._router.navigate(['/error'], { state: apiError401 });

        // this.logService.debug(this.constructor.name, 'handleError - ClearStorage');
        // this.storageService.clearStorage();
        // this.logService.debug(this.constructor.name, `handleError - Redirect to ${this.logoutUrl}`);
        // this.document.location.href = this.logoutUrl;
        return;
      } else {
        this.logService.debug(this.constructor.name, 'ErrorHandlerInterceptorService - handleError', "+++ errore generico");
        errorWrapper = {
          ...err,
          error: [{
            code: 'SYS-SYS-E-0001',
            params: {
              error: err.message || ''
            }
          }]
        };
      }

    }

    this.logError(req, err, errorWrapper.error);
    return throwError(errorWrapper);
  }

  private logError(req: HttpRequest<any>, httpError: any, apiErrors: ApiError[]) {
    this.logService.error(
      this.constructor.name,
      'handleError',
      'Errore nell\'invocazione del servizio\n',
      `URL INVOCATO: ${req.urlWithParams}\n`,
      `METHOD: ${req.method}\n`,
      req.body ? `BODY: ${JSON.stringify(req.body)}\n` : '\n',
      `STATUS: ${httpError.status || 0}\n`,
      `API ERRORS: ${JSON.stringify(apiErrors)}`);
  }

}
