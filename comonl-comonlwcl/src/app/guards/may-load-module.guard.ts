/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Injectable } from '@angular/core';
import {
  CanLoad,
  Route,
  CanActivate,
  ActivatedRouteSnapshot,
  CanActivateChild,
  Router
} from '@angular/router';
import { UserService, SidebarService } from 'src/app/services';
import { first } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MayLoadModuleGuard implements CanLoad, CanActivate, CanActivateChild {

  constructor(
    private router: Router,
    private sidebarService: SidebarService,
    private userService: UserService
  ) {}


  async canLoad(route: Route): Promise<boolean> {
    return this.mayExecute(route);
  }

  async canActivate(route: ActivatedRouteSnapshot): Promise<boolean> {
    return this.mayExecute(route);
  }

  async canActivateChild(childRoute: ActivatedRouteSnapshot): Promise<boolean> {
    return this.mayExecute(childRoute);
  }

  private async mayExecute(route: ActivatedRouteSnapshot | Route): Promise<boolean> {
    const requiredModuli = route.data.module as string;
    
    // const moduli = await this.userService.moduli$.pipe(first()).toPromise();
    // const res = moduli.some(mod => mod.codice === requiredModuli);
    const res = true;

    if (!res) {
      // Clean sidebar
      this.sidebarService.setContent([]);
      this.router.navigate(['/']);
    }
    this.sidebarService.loadContent(requiredModuli);
    return res;
  }
}
