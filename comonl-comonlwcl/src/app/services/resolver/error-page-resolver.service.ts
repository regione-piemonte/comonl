/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { ApiError } from 'src/app/modules/comonlapi';
import { isArray } from 'util';
import { Utils } from 'src/app/utils';

@Injectable({
  providedIn: 'root'
})
export class ErrorPageResolverService implements Resolve<ApiError[]> {
  private static readonly DEFAULT_MESSAGE = 'SYS-SYS-E-0001';

  constructor(
    private router: Router
  ) { }

  resolve(route: ActivatedRouteSnapshot): ApiError[] {
    const currentNavigation = this.router.getCurrentNavigation();
    if (route.data && route.data.message) {
      return [{ code: route.data.message, params: {} }];
    }
    if (currentNavigation.extras.state && Utils.isApiErrorLike(currentNavigation.extras.state)) {
      return [ currentNavigation.extras.state ];
    }
    // tslint:disable-next-line: max-line-length
    if (currentNavigation.extras.state && isArray(currentNavigation.extras.state) && Utils.areApiErrorLike(currentNavigation.extras.state as any[])) {
      return currentNavigation.extras.state as ApiError[];
    }
    return [{ code: ErrorPageResolverService.DEFAULT_MESSAGE, params: {} }];
  }

}
