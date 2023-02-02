/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { delay, finalize } from 'rxjs/operators';
import { UtilitiesService } from '../utilities.service';

@Injectable({
    providedIn: 'root'
})
export class SpinnerLoaderInterceptor implements HttpInterceptor {

    constructor(private utilitiesService: UtilitiesService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.utilitiesService.showSpinner();
        return next.handle(req).pipe(
            delay(1200),
            finalize(() => { this.utilitiesService.hideSpinner(); })
        );
    }
}
