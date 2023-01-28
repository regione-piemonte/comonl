import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CONTROL_STATE, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { AnagraficaGenerica, Cittadinanza, CommonService, Comune, Comunicazione, DecodificaGenerica, DecodificaService, LavoratoreSilpEspanso, LivelloStudio, MotivoPermesso, Questura, Ruolo, SilpService, StatiEsteri, StatusStraniero, Toponimo, WrapperLavoratoreSilpEspanso } from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-form-anagrafica-lavoratore-modal',
  templateUrl: './form-anagrafica-lavoratore-modal.component.html',
  styleUrls: ['./form-anagrafica-lavoratore-modal.component.scss']
})
export class FormAnagraficaLavoratoreModalComponent implements OnInit,AfterViewInit {

  @ViewChild("dataScadPermSogg",{static: false}) datePicker: any = new Object();

  @Input() callback;
  @Input() modal;
  @Input() mode; string;
  @Input() ruolo: Ruolo;
  @Input() comunicazione: Comunicazione;
  @Input() lavoratoreSilp: LavoratoreSilpEspanso;
  @Input() checkDatiEssenziali: boolean;


  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="pluto"]';

  form: FormGroup = new FormGroup({
    id: new FormGroup({
      codiceFiscale: new FormControl(null, Validators.required),
    }),
    nome: new FormControl(null,Validators.required),
    cognome: new FormControl(null,Validators.required),

    sesso: new FormControl(null,Validators.required),
    dataNascita: new FormControl(null,Validators.required),
    comuneNascita: new FormGroup({
      id: new FormControl(),
      codComuneMin: new FormControl(),
      dsComTComune: new FormControl()
    }),
    statiEsteriNascita: new FormGroup({
      id: new FormControl(),
      codNazioneMin: new FormControl(),
      dsStatiEsteri: new FormControl()
    }),
    cittadinanza: new FormControl({value: null}),
    statusStraniero: new FormControl(),
    descrNumDocPermSogg: new FormControl(),
    motivoPermesso: new FormControl(),
    dataScadPermSogg: new FormControl(),
    questura: new FormControl(),
    statiEsteriResidenza: new FormGroup({
      id: new FormControl(),
      codNazioneMin: new FormControl(),
      dsStatiEsteri: new FormControl()
    }),
    comuneResidenza: new FormGroup({
      id: new FormControl(),
      codComuneMin: new FormControl(),
      dsComTComune: new FormControl()
    }),
    toponimoResidenza: new FormControl(),
    capResidenza: new FormControl(),
    indirizzoResidenza: new FormControl(),
    numCivicoResidenza: new FormControl(),
    domicilioUgualeResidenza: new FormControl(),
    comuneDomicilio: new FormGroup({
      id: new FormControl(),
      codComuneMin: new FormControl(),
      dsComTComune: new FormControl()
    }),
    toponimoDomicilio: new FormControl(),
    capDomicilio: new FormControl(),
    indirizzoDomicilio: new FormControl(),
    numCivicoDomicilio: new FormControl(),
    livelloStudio: new FormControl(),
    idSilLavAnagrafica: new FormControl()
  });;
  showForm = false;


  now: Date = new Date();

  flgUeN = false;

  get INS_MODE():boolean{
    return this.mode === "ins";
  }
  get EDIT_MODE():boolean{
    return this.mode === "edit";
  }
  get RETTIFICA_MODE():boolean{
    return this.mode === "rettifica";
  }
  get f() {
    return this.form.controls as any;
  }


  get STATI_ESTERI_NASCITA_VUOTO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriNascita.get('codNazioneMin').value) || Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriNascita.get('dsStatiEsteri').value));
  }

  get COMUNE_NASCITA_VUOTO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneNascita.get('codComuneMin').value) || Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneNascita.get('dsComTComune').value));
  }

  get COMUNE_RESIDENZA_VUOTO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneResidenza.get('codComuneMin').value) || Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneResidenza.get('dsComTComune').value));
  }

  get STATI_ESTERI_RESIDENZA_VUOTO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriResidenza.get('codNazioneMin').value) || Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriResidenza.get('dsStatiEsteri').value));
  }

  get FLG_DOMICILIO_UGUALE() {
    return this.f.domicilioUgualeResidenza.value === 'S';
  }

  get LAV_STRANIERO(): boolean {
    if (this.lavoratoreSilp) {
      if (this.lavoratoreSilp.cittadinanza) {
        return this.lavoratoreSilp.cittadinanza.codCittadinanzaMin !== '000';
      }
    }
    return false;
  }

  get controlliDisabilitazioneSalvataggio(): boolean {
    if (this.f.dataNascita.value > this.now) {
      return true;
    }
    if (this.flgControlloDataScadenzaPerm && this.f.dataScadPermSogg.value < this.data2000) {
      return true;
    }
    return false;
  }

  flgStraniero = false;
  flgCartaPerm: boolean;
  flgComResVuoto = false;
  flgComNascVuoto = false;
  flgStatiEstNascVuoto = false;
  flgStatiEstResVuoto = false;
  flgDomicilioUguale = false;

  flgCercaComuneNascita = false;
  flgCercaStatiEsteriNascita = false;
  flgCercaComuneResidenza = false;
  flgCercaStatiEsteriResidenza = false;
  flgCercaComuneDomicilio = false;
  flgControlloDataScadenzaPerm = false;

  listaCittadinanza: Cittadinanza[] = [];
  listaTitoloSoggiorno: StatusStraniero[] = [];
  listaMotivoPermesso: MotivoPermesso[] = [];
  listaQuestura: Questura[] = [];
  listaLivelloIstruzione: LivelloStudio[] = [];
  listaToponimi: Toponimo[] = [];

  questuraObbligatoria: boolean;
  data2000: Date = new Date('/01/01/2000');


  

  constructor(
    private utilitiesService: UtilitiesService,
    private alertMessageService: AlertMessageService,
    private commonService: CommonService,
    private decodificaService: DecodificaService,
    private promptModalService: PromptModalService,
    private silpService: SilpService,
    private comonlStorageService: ComonlStorageService
  ) { }
  ngAfterViewInit(): void {
    //throw new Error('Method not implemented.');
  }

  async ngOnInit() {
    this.alertMessageService.emptyMessages();
    this.utilitiesService.showSpinner();
    this.initForm();
    await this.asyncNgOnInit();
    this.showForm = true;
    console.log(JSON.stringify(this.lavoratoreSilp));
    if(!this.INS_MODE){
      this.patchValuesInForm(this.lavoratoreSilp);
    }
    this.comonlStorageService.ruolo$.subscribe(async item => {
      this.ruolo = item;
    });
    if (this.EDIT_MODE || this.RETTIFICA_MODE) {
      if (this.lavoratoreSilp.cittadinanza) {
        if (this.lavoratoreSilp.cittadinanza.codCittadinanzaMin !== '000') {
          this.flgStraniero = true;
        }
        if (this.form.get('cittadinanza').value.flgUe === 'N') {
          this.flgUeN = true;
        }
      }
      if ((Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneResidenza.get('codComuneMin').value) || Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneResidenza.get('dsComTComune').value))) {
        this.flgComResVuoto = true;
        this.f.statiEsteriResidenza.enable();
      }
      if ((Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneNascita.get('codComuneMin').value) || Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneNascita.get('dsComTComune').value))) {
        this.flgComNascVuoto = true;
        this.f.statiEsteriNascita.enable();
      }
      if ((Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriResidenza.get('codNazioneMin').value) || Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriResidenza.get('dsStatiEsteri').value))) {
        this.flgStatiEstResVuoto = true;
        this.f.comuneResidenza.enable();
      }
      if ((Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriNascita.get('codNazioneMin').value) || Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriNascita.get('dsStatiEsteri').value))) {
        this.flgStatiEstNascVuoto = true;
        this.f.comuneNascita.enable();
      }

      if ((Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneResidenza.get('codComuneMin').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneResidenza.get('dsComTComune').value))) {
        this.flgComResVuoto = true;
        this.f.statiEsteriResidenza.enable();
      }

      if ((Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriResidenza.get('codNazioneMin').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriResidenza.get('dsStatiEsteri').value))) {
        this.flgStatiEstResVuoto = true;
        this.f.comuneResidenza.enable();
      }

      // Controlli
      if (!this.COMUNE_NASCITA_VUOTO) {
        this.flgComNascVuoto = false;
        this.f.statiEsteriNascita.disable();
      }
      if (!this.STATI_ESTERI_NASCITA_VUOTO) {
        this.flgStatiEstNascVuoto = false;
        this.f.comuneNascita.disable();
      }
      if (!this.COMUNE_RESIDENZA_VUOTO) {
        this.flgComResVuoto = false;
        this.f.statiEsteriResidenza.disable();
      }
      if (!this.STATI_ESTERI_RESIDENZA_VUOTO) {
        this.flgStatiEstResVuoto = false;
        this.f.comuneResidenza.disable();
      }
      // AGGIUNTO PERCHE' LA DATA SCADENZA PERMESSO AVEVA UN FORMATO DIVERSO
      if (this.lavoratoreSilp.cittadinanza) {
        this.onClickCittadinanza();
      }
      if (this.lavoratoreSilp.statusStraniero) {
        this.onClickSelectStatusStraniero(false);
      }

    }

    if (this.INS_MODE) {
      if (!this.COMUNE_NASCITA_VUOTO) {
        this.flgComNascVuoto = false;
        this.f.statiEsteriNascita.disable();
      }
      if (!this.STATI_ESTERI_NASCITA_VUOTO) {
        this.flgStatiEstNascVuoto = false;
        this.f.comuneNascita.disable();
      }
      if (!this.COMUNE_RESIDENZA_VUOTO) {
        this.flgComResVuoto = false;
        this.f.statiEsteriResidenza.disable();
      }
      if (!this.STATI_ESTERI_RESIDENZA_VUOTO) {
        this.flgStatiEstResVuoto = false;
        this.f.comuneResidenza.disable();
      }
      // SEZIONE TITOLO SOGGIORNO DISABILITATA DI DEFAULT
      this.utilitiesService.changeControlState(
        this.f.statusStraniero,
        CONTROL_STATE.DISABLE,
        true);
      this.utilitiesService.changeControlState(
        this.f.descrNumDocPermSogg,
        CONTROL_STATE.DISABLE,
        true);
      this.utilitiesService.changeControlState(
        this.f.dataScadPermSogg,
        CONTROL_STATE.DISABLE,
        true);
      this.utilitiesService.changeControlState(
        this.f.motivoPermesso,
        CONTROL_STATE.DISABLE,
        true);
      this.utilitiesService.changeControlState(
        this.f.questura,
        CONTROL_STATE.DISABLE,
        true);
    }
    this.utilitiesService.hideSpinner();
  }
  private initForm() {
    const controlState: string =  this.EDIT_MODE || this.RETTIFICA_MODE ? CONTROL_STATE.DISABLE : CONTROL_STATE.ENABLE;
    this.utilitiesService.changeControlState(
      this.f.id.controls.codiceFiscale,
      controlState,
      false
    );

    this.f.domicilioUgualeResidenza.patchValue(!this.INS_MODE && this.lavoratoreSilp.statiEsteriResidenza ? 'N' : this.INS_MODE ? 'N' : null);

    if(this.RETTIFICA_MODE && this.checkDatiEssenziali){

      this.utilitiesService.changeControlState(
        this.f.statusStraniero,
        CONTROL_STATE.DISABLE,
        false
      );

      this.utilitiesService.changeControlState(
        this.f.dataScadPermSogg,
        CONTROL_STATE.DISABLE,
        false
      );
    }
  }

  private async asyncNgOnInit(){
    try {
      const [
        cittadinanza,
        titoloSoggiorno,
        motivoPermesso,
        questura,
        livelloIstruzione,
        toponimo
      ] = await Promise.all([
        this.decodificaService.getCittadinanza().toPromise(),
        this.decodificaService.getTitoloSoggiorno().toPromise(),
        this.decodificaService.getMotivoPermesso().toPromise(),
        this.decodificaService.getQuestura().toPromise(),
        this.decodificaService.getLivelloStudio().toPromise(),
        this.decodificaService.getToponimo().toPromise(),
      ]);
      this.listaCittadinanza = cittadinanza;
      this.listaTitoloSoggiorno = titoloSoggiorno;
      this.listaMotivoPermesso = motivoPermesso;
      this.listaQuestura = questura;
      this.listaLivelloIstruzione = livelloIstruzione;
      this.listaToponimi = toponimo;
    } catch (e) {
      if(e.error && e.error.length > 0){
        this.alertMessageService.setErrorMsg(e.error);
      }else{
        this.alertMessageService.setErrorMsg(e);
      }
    } finally {
      await this.utilitiesService.hideSpinner();
    }
  }

  // initForm() {
    
  // }


  patchValuesInForm(lavoratore: LavoratoreSilpEspanso) {
    if (lavoratore) {
      console.log('lavoratoreSilp: ' + JSON.stringify(lavoratore));
      this.form.patchValue({
        ...lavoratore,
        cognome: lavoratore.cognome.substring(0, 50),
        nome: lavoratore.nome.substring(0, 50),
        statiEsteriNascita: lavoratore.statiEsteriNascita || {},
        statiEsteriResidenza: lavoratore.statiEsteriResidenza || {},
        comuneNascita: lavoratore.comuneNascita || {},
        comuneResidenza: lavoratore.comuneResidenza || {},
        comuneDomicilio: lavoratore.comuneDomicilio || {},
        toponimoResidenza: lavoratore.toponimoResidenza || {},
        toponimoDomicilio: lavoratore.toponimoDomicilio || {}
      });
      if (lavoratore.comuneNascita) {
        this.f.comuneNascita.get('id').patchValue(lavoratore.comuneNascita.id);
        this.f.comuneNascita.get('codComuneMin').patchValue(lavoratore.comuneNascita.codComuneMin);
        this.f.comuneNascita.get('dsComTComune').patchValue(lavoratore.comuneNascita.dsComTComune);
      }
      if (lavoratore.comuneResidenza) {
        this.f.comuneResidenza.get('id').patchValue(lavoratore.comuneResidenza.id);
        this.f.comuneResidenza.get('codComuneMin').patchValue(lavoratore.comuneResidenza.codComuneMin);
        this.f.comuneResidenza.get('dsComTComune').patchValue(lavoratore.comuneResidenza.dsComTComune);
      }
      if (lavoratore.comuneDomicilio) {
        this.f.comuneDomicilio.get('id').patchValue(lavoratore.comuneDomicilio.id);
        this.f.comuneDomicilio.get('codComuneMin').patchValue(lavoratore.comuneDomicilio.codComuneMin);
        this.f.comuneDomicilio.get('dsComTComune').patchValue(lavoratore.comuneDomicilio.dsComTComune);
        this.f.indirizzoDomicilio.patchValue(lavoratore.indirizzoDomicilio);
      }

      if (lavoratore) {
        console.log(this.lavoratoreSilp.dataScadPermSogg);
        this.f.dataScadPermSogg.setValue(new Date(this.lavoratoreSilp.dataScadPermSogg));
      }

      // POPOLAMENTO COMBO
      // Cittadinanza
      if (lavoratore.cittadinanza) {
        const cittadinanza = this.listaCittadinanza.find((el: Cittadinanza) => {
          return el.codCittadinanzaMin === this.lavoratoreSilp.cittadinanza.codCittadinanzaMin;
        });
        this.listaCittadinanza.sort((a, b) => a.dsComTCittadinanza.localeCompare(b.dsComTCittadinanza));
        this.f.cittadinanza.patchValue(cittadinanza);
        JSON.stringify('cittadinanza: '+this.f.cittadinanza.value);
      }
      // StatusStraniero
      if (lavoratore.statusStraniero) {
        const statusStraniero = this.listaTitoloSoggiorno.find((el: StatusStraniero) => {
          return el.id === this.lavoratoreSilp.statusStraniero.id;
        });
        this.listaTitoloSoggiorno.sort((a, b) => a.dsComTStatusStraniero.localeCompare(b.dsComTStatusStraniero));
        this.f.statusStraniero.patchValue(statusStraniero);
      }
      // Motivo permesso
      if (lavoratore.motivoPermesso) {
        const motivoPermesso = this.listaMotivoPermesso.find((el: MotivoPermesso) => {
          return el.id === this.lavoratoreSilp.motivoPermesso.id;
        });
        this.listaMotivoPermesso.sort((a, b) => a.dsComTMotivoPermesso.localeCompare(b.dsComTMotivoPermesso));
        this.f.motivoPermesso.patchValue(motivoPermesso);
      }
      // Questura
      if (lavoratore.questura) {
        const questura = this.listaQuestura.find((el: Questura) => {
          return el.id === this.lavoratoreSilp.questura.id;
        });
        this.listaQuestura.sort((a, b) => a.dsQuestura.localeCompare(b.dsQuestura));
        this.f.questura.patchValue(questura);
      }
      // LivelloStudio
      if (lavoratore.livelloStudio) {
        const livelloStudio = this.listaLivelloIstruzione.find((el: LivelloStudio) => {
          return el.codiceLivelloMin === this.lavoratoreSilp.livelloStudio.codiceLivelloMin;
        });
        this.listaLivelloIstruzione.sort((a, b) => a.titoloDiStudio.localeCompare(b.titoloDiStudio));
        this.f.livelloStudio.patchValue(livelloStudio);
      }
      // Toponimo residenza
      if (lavoratore.toponimoResidenza) {
        const toponimoResidenza = this.listaToponimi.find((el: Toponimo) => {
          return el.idComTToponimo === this.lavoratoreSilp.toponimoResidenza.idComTToponimo;
        });
        this.listaToponimi.sort((a, b) => a.dsComTToponimo.localeCompare(b.dsComTToponimo));
        this.f.toponimoResidenza.patchValue(toponimoResidenza);
      }
      // Toponimo domicilio
      if (lavoratore.toponimoDomicilio) {
        const toponimoDomicilio = this.listaToponimi.find((el: Toponimo) => {
          return el.idComTToponimo === this.lavoratoreSilp.toponimoDomicilio.idComTToponimo;
        });
        this.listaToponimi.sort((a, b) => a.dsComTToponimo.localeCompare(b.dsComTToponimo));
        this.f.toponimoDomicilio.patchValue(toponimoDomicilio);
      }
      // FINE POPOLAMENTO COMBO
    }
  }

  async onClickPreCompila() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
      const cfInput: string = this.f.id.get('codiceFiscale').value;
      if(!Utils.isNullOrUndefinedOrCampoVuoto(cfInput)){
        const res: AnagraficaGenerica = await this.commonService.preCompilaAnagraficaByCf(cfInput).toPromise();
        if (res) {
          this.f.sesso.setValue(res.sesso);
          this.f.dataNascita.setValue(res.dataNascita);
          if(res.comune){
            this.utilitiesService.changeControlState(
              this.f.comuneNascita,
              CONTROL_STATE.ENABLE,
              false
            );

            this.f.comuneNascita.patchValue(res.comune);

            this.utilitiesService.changeControlState(
              this.f.statiEsteriNascita,
              CONTROL_STATE.DISABLE,
              true
            );
            this.f.statiEsteriNascita.updateValueAndValidity();
           
          }else{
            this.utilitiesService.changeControlState(
              this.f.statiEsteriNascita,
              CONTROL_STATE.ENABLE,
              false
            );

            this.f.statiEsteriNascita.patchValue(res.statiEsteri);

            this.utilitiesService.changeControlState(
              this.f.comuneNascita,
              CONTROL_STATE.DISABLE,
              true
            );

            this.f.comuneNascita.updateValueAndValidity();
          }
          
        }
      }
      
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setErrorMsg(e.error);
      }else{
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  checkValue(section) {
    if (Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriResidenza.get('codNazioneMin').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriResidenza.get('dsStatiEsteri').value) &&
      Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneResidenza.get('codComuneMin').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneResidenza.get('dsComTComune').value)) {
      this.f.domicilioUgualeResidenza.enable();
      this.f.domicilioUgualeResidenza.updateValueAndValidity();
    }
    switch (section) {
      case 'comuneNascita':
        this.f.comuneNascita.controls.id.setValue(null);
        if (Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneNascita.get('codComuneMin').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneNascita.get('dsComTComune').value)) {
          this.utilitiesService.changeControlState(
            this.f.statiEsteriNascita,
            CONTROL_STATE.ENABLE,
            true
          );
        } else {
          this.utilitiesService.changeControlState(
            this.f.statiEsteriNascita,
            CONTROL_STATE.DISABLE,
            true
          );
        }
        break;
      case 'statiEsteriNascita':
        this.f.statiEsteriNascita.controls.id.setValue(null);
        if (Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriNascita.get('codNazioneMin').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriNascita.get('dsStatiEsteri').value)) {
          this.utilitiesService.changeControlState(
            this.f.comuneNascita,
            CONTROL_STATE.ENABLE,
            true
          );
        } else {
          this.utilitiesService.changeControlState(
            this.f.comuneNascita,
            CONTROL_STATE.DISABLE,
            true
          );
        }
        break;
      case 'comuneResidenza':
        this.f.comuneResidenza.controls.id.setValue(null);
        if (Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneResidenza.get('codComuneMin').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneResidenza.get('dsComTComune').value)) {
          this.utilitiesService.changeControlState(
            this.f.statiEsteriResidenza,
            CONTROL_STATE.ENABLE,
            true
          );
        } else {
          this.utilitiesService.changeControlState(
            this.f.statiEsteriResidenza,
            CONTROL_STATE.DISABLE,
            true
          );
        }
        break;
      case 'statiEsteriResidenza':
        this.f.statiEsteriResidenza.controls.id.setValue(null);
        if (Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriResidenza.get('codNazioneMin').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteriResidenza.get('dsStatiEsteri').value)) {
          this.utilitiesService.changeControlState(
            this.f.comuneResidenza,
            CONTROL_STATE.ENABLE,
            true
          );
          this.utilitiesService.changeControlState(
            this.f.toponimoResidenza,
            CONTROL_STATE.ENABLE,
            true
          );
          this.utilitiesService.changeControlState(
            this.f.capResidenza,
            CONTROL_STATE.ENABLE,
            true
          );
        } else {
          this.utilitiesService.changeControlState(
            this.f.comuneResidenza,
            CONTROL_STATE.DISABLE,
            true
          );
        }
        break;
    }
  }

  async onClickFindComune(section: string) {
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    if (section === 'nascita') {
      decodifica = {
        codDecodifica: this.f.comuneNascita.get('codComuneMin').value,
        dsDecodifica: this.f.comuneNascita.get('dsComTComune').value,
        flgAncheNonValidi: true
      };
    } else if (section === 'residenza') {
      decodifica = {
        codDecodifica: this.f.comuneResidenza.get('codComuneMin').value,
        dsDecodifica: this.f.comuneResidenza.get('dsComTComune').value
      };
    } else if (section === 'domicilio') {
      decodifica = {
        codDecodifica: this.f.comuneDomicilio.get('codComuneMin').value,
        dsDecodifica: this.f.comuneDomicilio.get('dsComTComune').value
      };
    }
    try {
      const res = await this.decodificaService.postComuneDecodifica(decodifica).toPromise();
      let decodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
        decodificaFinded = res[0];
      } else if (res && res.length > 1) {
        decodificaFinded = await this.promptModalService.openDecodificaPrompt('Selezionare un comune', res, TYPE_DECODIFICA_GENERICA.COMUNE, decodifica);
      }
      if (decodificaFinded) {
        const comune: Comune = {
          id: decodificaFinded.idDecodifica,
          codComuneMin: decodificaFinded.codDecodifica,
          dsComTComune: decodificaFinded.dsDecodifica
        };
        if (section === 'nascita') {
          this.f.comuneNascita.patchValue(comune);
          this.f.statiEsteriNascita.disable();
          this.f.statiEsteriNascita.controls.id.setValue(null);
          this.flgCercaComuneNascita = true;
        } else if (section === 'residenza') {
          this.f.comuneResidenza.patchValue(comune);
          this.f.statiEsteriResidenza.disable();
          this.f.statiEsteriResidenza.controls.id.setValue(null);
          if (this.f.domicilioUgualeResidenza.value === 'S') {
            this.f.comuneDomicilio.patchValue(comune);
          }
          this.flgCercaComuneResidenza = true;
        } else if (section === 'domicilio') {
          this.f.comuneDomicilio.patchValue(comune);
          this.flgCercaComuneDomicilio = true;
        }
      }
    } catch (e) {
      if(e.error && e.error.length > 0){
        this.alertMessageService.setWarningMsg(e.error);
      }else{
        if( e != 0){
          this.alertMessageService.setSingleErrorMsg(e);
        }
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async onClickFindStatoEstero(section: string) {
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    if (section === 'nascita') {
      decodifica = {
        codDecodifica: this.f.statiEsteriNascita.get('codNazioneMin').value,
        dsDecodifica: this.f.statiEsteriNascita.get('dsStatiEsteri').value,
        flgAncheNonValidi: true
      };
    } else if (section === 'residenza') {
      decodifica = {
        codDecodifica: this.f.statiEsteriResidenza.get('codNazioneMin').value,
        dsDecodifica: this.f.statiEsteriResidenza.get('dsStatiEsteri').value
      };
    }
    try {
      const res = await this.decodificaService.postStatiEsteriDecodifica(decodifica).toPromise();
      let decodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
        decodificaFinded = res[0];
      } else if (res && res.length > 1) {
        decodificaFinded = await this.promptModalService.openDecodificaPrompt('Selezionare uno stato estero', res, TYPE_DECODIFICA_GENERICA.STATI_ESTERI, decodifica);
      }
      if (decodificaFinded) {
        const statoEstero: StatiEsteri = {
          id: decodificaFinded.idDecodifica,
          codNazioneMin: decodificaFinded.codDecodifica,
          dsStatiEsteri: decodificaFinded.dsDecodifica
        };
        if (section === 'nascita') {
          this.f.statiEsteriNascita.patchValue(statoEstero);
          this.f.comuneNascita.disable();
          this.f.comuneNascita.controls.id.setValue(null);
          this.flgCercaStatiEsteriNascita = true;
          console.log('id comuneNascita: ' + this.f.comuneNascita.controls.id.value);
        } else if (section === 'residenza') {
          this.f.statiEsteriResidenza.patchValue(statoEstero);
          this.f.comuneResidenza.disable();
          this.flgCercaStatiEsteriResidenza = true;
          this.f.domicilioUgualeResidenza.patchValue('N');
          this.f.domicilioUgualeResidenza.disable();
          this.onClickAbilitaSvuotaCampi();
          this.utilitiesService.changeControlState(
            this.f.toponimoResidenza,
            CONTROL_STATE.DISABLE, true
          );
          this.utilitiesService.changeControlState(
            this.f.capResidenza,
            CONTROL_STATE.DISABLE, true
          );
        }
      }
    } catch (e) {
      if(e.error && e.error.length > 0){
        this.alertMessageService.setWarningMsg(e.error);
      }else{
        if(e !=0)
          this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }


  onClickAbilitaSvuotaCampi() {
    this.utilitiesService.changeControlState(
      this.f.comuneDomicilio,
      CONTROL_STATE.ENABLE,
      true
    );
    this.utilitiesService.changeControlState(
      this.f.toponimoDomicilio,
      CONTROL_STATE.ENABLE,
      true
    );
    this.utilitiesService.changeControlState(
      this.f.indirizzoDomicilio,
      CONTROL_STATE.ENABLE,
      true
    );
    this.utilitiesService.changeControlState(
      this.f.numCivicoDomicilio,
      CONTROL_STATE.ENABLE,
      true
    );
    this.utilitiesService.changeControlState(
      this.f.capDomicilio,
      CONTROL_STATE.ENABLE,
      true
    );
    
  }

  onClickCittadinanza() {
    if (this.form.get('cittadinanza').value !== '' && this.form.get('cittadinanza').value !== null) {
      switch (this.form.get('cittadinanza').value.flgUe) {
        case 'N': // ------------------ FLG UE = N --------------------
          this.flgUeN = true;
          // ABILITO TITOLO SOGGIORNO E DIVENTA REQUIRED
          this.f.statusStraniero.setValidators(Validators.required);
          if(!this.checkDatiEssenziali){
            this.utilitiesService.changeControlState(
              this.f.statusStraniero,
              CONTROL_STATE.ENABLE,
              false
            );
          }
          
          this.f.statusStraniero.updateValueAndValidity();
          // ABILITO NUMERO DOCUMENTO
          this.utilitiesService.changeControlState(
            this.f.descrNumDocPermSogg,
            CONTROL_STATE.ENABLE,
            false
          );
          this.f.descrNumDocPermSogg.updateValueAndValidity();
          // ABILITO DATA SCADENZA PERMESSO SOGGIORNO E DIVENTA REQUIRED
          this.f.dataScadPermSogg.clearValidators();
          this.f.dataScadPermSogg.setValidators([this.datePicker,Validators.required]); //datePicker
          if(!this.checkDatiEssenziali){
            this.utilitiesService.changeControlState(
              this.f.dataScadPermSogg,
              CONTROL_STATE.ENABLE,
              false
            );
          }
          
          this.f.dataScadPermSogg.updateValueAndValidity();
          // ABILITO DATA MOTIVO PERMESSO E DIVENTA REQUIRED
          this.f.motivoPermesso.setValidators(Validators.required);
          this.utilitiesService.changeControlState(
            this.f.motivoPermesso,
            CONTROL_STATE.ENABLE,
            false
          );
          this.f.motivoPermesso.updateValueAndValidity();
          // ABILITO QUESTURA
          this.utilitiesService.changeControlState(
            this.f.questura,
            CONTROL_STATE.ENABLE,
            false
          );
          this.f.questura.updateValueAndValidity();
          break;
        case 'S': // ------------------ FLG UE = S --------------------
          this.flgControlloDataScadenzaPerm = false; // non devo controllare se la data inserita e' minore del 2000
          this.flgUeN = false;
          this.f.statusStraniero.clearValidators();
          this.utilitiesService.changeControlState(
            this.f.statusStraniero,
            CONTROL_STATE.DISABLE,
            true
          );
          this.f.statusStraniero.updateValueAndValidity();
          // DISABILITO NUMERO DOCUMENTO E PULISCO VALIDATORS
          this.f.descrNumDocPermSogg.clearValidators();
          this.utilitiesService.changeControlState(
            this.f.descrNumDocPermSogg,
            CONTROL_STATE.DISABLE,
            true
          );
          this.f.descrNumDocPermSogg.updateValueAndValidity();
          // DISABILITO DATA SCADENZA PERMESSO E PULISCO VALIDATORS
          this.f.dataScadPermSogg.clearValidators();
          this.f.dataScadPermSogg.setValidators([this.datePicker]); //datePicker
           this.utilitiesService.changeControlState(
             this.f.dataScadPermSogg,
             CONTROL_STATE.DISABLE,
             true
           );
          this.f.dataScadPermSogg.updateValueAndValidity();
          // DISABILITO MOTIVO PERMESSO E PULISCO VALIDATORS
          this.f.motivoPermesso.clearValidators();
          this.utilitiesService.changeControlState(
            this.f.motivoPermesso,
            CONTROL_STATE.DISABLE,
            true
          );
          this.f.motivoPermesso.updateValueAndValidity();
          // DISABILITO QUESTURA E PULISCO VALIDATORS
          this.f.questura.clearValidators();
          this.utilitiesService.changeControlState(
            this.f.questura,
            CONTROL_STATE.DISABLE,
            true
          );
          this.f.questura.updateValueAndValidity();
          break;
      }
    } else {
      //perchè non this.f.cittadinanza.reset()?
      this.f.cittadinanza.setValue(null);
      // TUTTO TITOLO SOGGIORNO DISABILITATO
    }
  }

  onClickSelectStatusStraniero(cleanValue: boolean) {
    if (this.form.get('statusStraniero').value !== '' && this.form.get('statusStraniero').value !== null) {
      this.questuraObbligatoria = false;
      this.lockUnlockdataScadPermSogg(cleanValue);
      switch (this.form.get('statusStraniero').value.codStatusMin) {
        case '1': // PERMESSO
        case '2': // CARTA
          this.f.questura.setValidators(Validators.required);
          this.utilitiesService.changeControlState(
            this.f.questura,
            CONTROL_STATE.ENABLE,
            cleanValue
          );
          this.f.questura.updateValueAndValidity();
          this.flgControlloDataScadenzaPerm = true; // DEVO controllare se la data inserita e' minore del 2000
          this.questuraObbligatoria = true;
          break;
        case '3': // IN RINNOVO
        case '6': //ALTRO PROVVEDIMENTO
          this.flgControlloDataScadenzaPerm = true; // DEVO controllare se la data inserita e' minore del 2000
          this.f.questura.clearValidators();
          this.utilitiesService.changeControlState(
            this.f.questura,
            CONTROL_STATE.DISABLE,
            cleanValue
          );
          this.f.questura.updateValueAndValidity();
          break;
          case '7': // PERMESSO DI SOGGIORNO CE PER SOGGIORNANTI DI LUNGO PERIODO
          this.flgControlloDataScadenzaPerm = true;
          this.f.questura.clearValidators();
          this.f.questura.setValidators(Validators.required);
          this.utilitiesService.changeControlState(
            this.f.questura,
            CONTROL_STATE.ENABLE,
            cleanValue
            );
            this.f.dataScadPermSogg.clearValidators();
            this.f.dataScadPermSogg.setValidators([this.datePicker,Validators.required]); //datePicker
            this.f.dataScadPermSogg.updateValueAndValidity();
            this.f.questura.updateValueAndValidity();
            this.questuraObbligatoria = true;
          break;
        case '4': // IN ATTESA DI PERMESSO
          this.flgCartaPerm = false;
          this.f.dataScadPermSogg.setValidators([,Validators.required]);
          this.f.dataScadPermSogg.patchValue(new Date('01/01/1900'));
          this.f.dataScadPermSogg.updateValueAndValidity();
          this.f.motivoPermesso.setValidators(Validators.required);
          this.f.motivoPermesso.updateValueAndValidity();
          this.flgControlloDataScadenzaPerm = false; // non devo controllare se la data inserita e' minore del 2000
          this.lockUnlockdataScadPermSogg(false);
          this.f.questura.clearValidators();
          this.utilitiesService.changeControlState(
            this.f.questura,
            CONTROL_STATE.DISABLE,
            cleanValue
          );
          this.f.questura.updateValueAndValidity();

          break;
        case '5': // CARTA PERMANENTE
          this.flgCartaPerm = true;
          this.f.dataScadPermSogg.clearValidators();
          this.f.dataScadPermSogg.setValidators([this.datePicker]); //datePicker
          this.f.dataScadPermSogg.patchValue(new Date('12/31/2099'));
          this.f.dataScadPermSogg.updateValueAndValidity();
          this.f.motivoPermesso.clearValidators();
          this.f.motivoPermesso.updateValueAndValidity();
          this.flgControlloDataScadenzaPerm = true; // DEVO controllare se la data inserita e' minore del 2000
          this.lockUnlockdataScadPermSogg(false);
          this.f.questura.setValidators(Validators.required);
          this.utilitiesService.changeControlState(
            this.f.questura,
            CONTROL_STATE.ENABLE,
            cleanValue
          );
          this.f.questura.updateValueAndValidity();
          this.questuraObbligatoria = true;
          break;
        default:
          this.flgControlloDataScadenzaPerm = true; // DEVO controllare se la data inserita e' minore del 2000
          break;
      }
    } else {
      this.f.statusStraniero.setValue(null);
      this.flgControlloDataScadenzaPerm = false; // non devo controllare se la data inserita e' minore del 2000
    }
  }

  private lockUnlockdataScadPermSogg(cleanValue: boolean){
    const statusSTraniero: StatusStraniero = this.form.get('statusStraniero').value;
    const state: string = (statusSTraniero && (statusSTraniero.codStatusMin === '4' || statusSTraniero.codStatusMin === '5' )) || !statusSTraniero ? CONTROL_STATE.DISABLE : CONTROL_STATE.ENABLE;
    this.utilitiesService.changeControlState(
      this.f.dataScadPermSogg,
      state,
      cleanValue
    );
  }

  onClickSelectToponimo(toponimoResidenza: Toponimo) {
    if (this.INS_MODE && this.FLG_DOMICILIO_UGUALE) {
      this.f.toponimoDomicilio.patchValue(toponimoResidenza);
    } else {
      console.log(JSON.stringify(toponimoResidenza));
      const toponimoRes = this.listaToponimi.find((el: Toponimo) => {
        return el.dsComTToponimo === toponimoResidenza.dsComTToponimo;
      });
      this.f.toponimoResidenza.patchValue(toponimoRes);
    }
  }

  onClickCompilaDomicilio() {
    this.f.comuneDomicilio.get('codComuneMin').patchValue(this.f.comuneResidenza.get('codComuneMin').value);
    this.f.comuneDomicilio.get('dsComTComune').patchValue(this.f.comuneResidenza.get('dsComTComune').value);
    this.f.indirizzoDomicilio.patchValue(this.f.indirizzoResidenza.value);
    this.f.toponimoDomicilio.patchValue(this.f.toponimoResidenza.value);
    this.f.numCivicoDomicilio.patchValue(this.f.numCivicoResidenza.value);
    this.f.capDomicilio.patchValue(this.f.capResidenza.value);
    // Disabilito i campi
    this.f.comuneDomicilio.disable();
    this.f.toponimoDomicilio.disable();
    this.f.indirizzoDomicilio.disable();
    this.f.toponimoDomicilio.disable();
    this.f.numCivicoDomicilio.disable();
    this.f.capDomicilio.disable();
  }

  async onClickSalvaLavoratore() {
    const lavoratoreToSave = this.form.getRawValue() as LavoratoreSilpEspanso;
    try {
      this.utilitiesService.showSpinner();
      this.alertMessageService.emptyMessages();
      const wrapperLavoratoreSilpEspanso: WrapperLavoratoreSilpEspanso = {
        lavoratoreSilpEspanso: lavoratoreToSave,
        ruolo: this.ruolo,
        comunicazione: this.comunicazione
      };
      const res: string = await this.silpService.putLavoratoreSilpDaCo(this.RETTIFICA_MODE,wrapperLavoratoreSilpEspanso).toPromise();
      if (res) {
        //res è il codice fiscale del lavoratore appena salvato
        this.callback(this.modal, res);
      }
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setErrorMsg(e.error);
      } else {
        this.alertMessageService.setSingleErrorMsg(e);
      }
      this.utilitiesService.scrollTo(FormAnagraficaLavoratoreModalComponent.SCROLL_TARGET);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  onClickResetFields() {
    if (this.EDIT_MODE || this.RETTIFICA_MODE) {
      this.form.reset();
      this.patchValuesInForm(this.lavoratoreSilp);
      this.onClickCittadinanza();
      const dataScadPermSogg: Date = this.lavoratoreSilp.dataScadPermSogg;
      if(!Utils.isNullOrUndefinedOrCampoVuoto(dataScadPermSogg)){
        this.lockUnlockdataScadPermSogg(false);
      }else{
        this.lockUnlockdataScadPermSogg(true);
      }

    }
    if (this.INS_MODE) {
      this.form.reset();
      this.f.domicilioUgualeResidenza.patchValue('N');
      this.onClickAbilitaSvuotaCampi();
      this.lockUnlockdataScadPermSogg(true);
    }
  }
  

}
