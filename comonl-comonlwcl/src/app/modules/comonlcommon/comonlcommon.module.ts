/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import {
  HasValueClassDirective,
  CustomCheckboxDirective,
  DigitOnlyDirective,
  PercDigitOnlyDirective,
  IsInvalidClassDirective,
  SortableDirective,
  FormatAmountDirective,
  NgSelectHasValueDirective,
  PaginationHeadDirective,
  PaginationBodyDirective,
  ScrollListenerDirective
} from './directives';
import {
  SidebarLeftComponent
} from 'src/app/components';
import { RouterModule } from '@angular/router';
import { UnsafeHtmlPipe, CodeDescPipe, SiNoPipe, EscapeHtmlPipe, MaschioFemminaPipe } from 'src/app/pipes';
import { NgbModalModule, NgbPaginationModule, NgbAlertModule, NgbDropdownModule,
  NgbPopoverModule, NgbDateAdapter, NgbDateNativeAdapter, NgbDateParserFormatter,
  NgbDatepickerI18n, NgbTooltip, NgbDatepickerModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxMaskModule } from 'ngx-mask';
import { NgxCurrencyModule } from 'ngx-currency';
import { NestableModule } from 'ngx-nestable';
import { NgbCustomDateParserFormatterService, NgbCustomI18nService, PromptModalService } from './services';
import {
  BackButtonComponent,
  PaginatedTableComponent,
  PaginatedArrayTableComponent,
  TableComponent,
  HeadDirective,
  BodyDirective,
  TreeModalComponent,
  PromptModalComponent
} from './components';
import { FormatNumAmountDirective } from './directives/format-num-amount.directive';
import { PreventDoubleClickDirective } from './directives/prevent-double-click.directive';
import { ComonlDisabledTooltipDirective } from './directives/comonl-disabled-tooltip.directive';
import { ErrorModalComponent } from './components/error-modal/error-modal.component';
import { AlertMsgComponent } from './components/alert-msg/alert-msg.component';
import { ListaDecodificaModalComponent } from './components/lista-decodifica-modal/lista-decodifica-modal.component';
import { IdOnlyDirective } from './directives/list-id-only.directive - Copia';
import { ComonlTooltipDirective } from './directives/comonl-tooltip.directive';
import { AlertMessageComponent } from './components/alert-message/alert-message.component';
import { DecodAnagraficaPipe } from 'src/app/pipes/decod-anagrafica.pipe';
import { FormAnagraficaLavoratoreModalComponent } from './components/form-anagrafica-lavoratore-modal/form-anagrafica-lavoratore-modal.component';

@NgModule({
  declarations: [
    SidebarLeftComponent,
    BackButtonComponent,
    TreeModalComponent,
    PromptModalComponent,
    ErrorModalComponent,

    HasValueClassDirective,
    CustomCheckboxDirective,
    DigitOnlyDirective,
    PercDigitOnlyDirective,
    IdOnlyDirective,
    IsInvalidClassDirective,
    SortableDirective,
    NgSelectHasValueDirective,
    ComonlTooltipDirective,
    ComonlDisabledTooltipDirective,
    PreventDoubleClickDirective,
    CodeDescPipe,
    UnsafeHtmlPipe,
    SiNoPipe,
    DecodAnagraficaPipe,
    EscapeHtmlPipe,
    MaschioFemminaPipe,

    PaginatedTableComponent,
    PaginatedArrayTableComponent,
    PaginationHeadDirective,
    PaginationBodyDirective,
    FormatAmountDirective,
    FormatNumAmountDirective,
    TableComponent,
    HeadDirective,
    BodyDirective,
    ScrollListenerDirective,
    AlertMsgComponent,
    ListaDecodificaModalComponent,
    AlertMessageComponent,
    FormAnagraficaLavoratoreModalComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    TranslateModule,
    NgSelectModule,
    NgbModalModule,
    NgbPaginationModule,
    NgbAlertModule,
    NgxMaskModule,
    FormsModule,
    NgbDropdownModule,
    NgbPopoverModule,
    RouterModule,
    NgxCurrencyModule,
    NestableModule,
    NgbDatepickerModule
  ],
  exports: [
    CommonModule,
    NgSelectModule,
    NgbModalModule,
    NgbPaginationModule,
    NgbAlertModule,
    TranslateModule,
    ReactiveFormsModule,
    NgbDropdownModule,
    NgbPopoverModule,
    NgxMaskModule,
    FormsModule,
    NestableModule,

    SidebarLeftComponent,
    BackButtonComponent,

    HasValueClassDirective,
    CustomCheckboxDirective,
    DigitOnlyDirective,
    PercDigitOnlyDirective,
    IdOnlyDirective,
    IsInvalidClassDirective,
    SortableDirective,
    NgSelectHasValueDirective,
    ComonlTooltipDirective,
    ComonlDisabledTooltipDirective,
    PreventDoubleClickDirective,

    CodeDescPipe,
    UnsafeHtmlPipe,
    SiNoPipe,
    DecodAnagraficaPipe,
    EscapeHtmlPipe,
    MaschioFemminaPipe,

    PaginatedTableComponent,
    PaginatedArrayTableComponent,
    PaginationHeadDirective,
    PaginationBodyDirective,
    ScrollListenerDirective,
    FormatNumAmountDirective,
    AlertMsgComponent,
    AlertMessageComponent,

    TableComponent,
    HeadDirective,
    BodyDirective,
    NgxCurrencyModule,
    FormAnagraficaLavoratoreModalComponent

  ],
  providers: [
    { provide: NgbDateAdapter, useClass: NgbDateNativeAdapter },
    { provide: NgbDateParserFormatter, useClass: NgbCustomDateParserFormatterService },
    { provide: NgbDatepickerI18n, useClass: NgbCustomI18nService, deps: [ TranslateService ] },
    PromptModalService
  ],
  entryComponents: [
    TreeModalComponent,
    PromptModalComponent,
    ErrorModalComponent,
    ListaDecodificaModalComponent,
    FormAnagraficaLavoratoreModalComponent
  ]
})
export class ComonlcommonModule { }
