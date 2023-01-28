/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService, UtilitiesService } from 'src/app/services';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class MayActivateByPermessoGuard implements CanActivate {

  constructor(
    private translateService: TranslateService,
    private utilitiesService: UtilitiesService,
    private userService: UserService
  ) {}

  canActivate(next: ActivatedRouteSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const requiredPermessi = next.data.permessi as string[];
    
    const res = this.userService.hasPermessi(requiredPermessi);
    if (!res) {
      this.utilitiesService.showToastrErrorMessage(
        `SYS-SYS-E-0007 - ${this.translateService.instant('MESSAGES.SYS-SYS-E-0007')}`
      );
      // TODO: redirect to /home?
    }
    return res;
  }

}
