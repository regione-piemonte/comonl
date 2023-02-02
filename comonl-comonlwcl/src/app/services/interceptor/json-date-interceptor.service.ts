/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Utils } from 'src/app/utils';

@Injectable()
export class JsonDateInterceptorService implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      map((val: HttpEvent<any>) => this.mapJsonDate(val))
    );
  }

  private mapJsonDate(val: HttpEvent<any>) {
    if (val instanceof HttpResponse) {
      Utils.convertHandlingDate(val.body);
    }
    return val;
  }
}
