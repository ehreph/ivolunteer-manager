import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormGroup } from '@angular/forms';
import { IBadge } from '../../shared/model/badge.model';
import { IGoal } from '../../shared/model/goal.model';
import { ICompetence } from '../../shared/model/competence.model';
import { TranslateService } from '@ngx-translate/core';
import { IActivity } from 'app/shared/model/activity.model';

export type SelectableEntityArray = IGoal[] | ICompetence[] | IBadge[] | IActivity[];

@Component({
  selector: 'jhi-form-item-base',
  template: '',
})
export class FormItemBaseComponent implements OnInit {
  @Input()
  parentForm!: FormGroup;

  @Input()
  index: any;

  @Input()
  formArrayName: string;

  @Output()
  removeClicked: EventEmitter<void> = new EventEmitter<void>();

  constructor(protected translate: TranslateService) {}

  ngOnInit(): void {}

  public getFormGroupByIndex(): FormGroup {
    const fa = this.parentForm.get(this.formArrayName) as FormArray;
    const fg = fa.get(this.index.toString()) as FormGroup;
    return fg;
  }

  protected enumToSelectionArray(translationPrefix: string, e: any[]): any[] {
    const array = e.map(option => {
      return { name: option, value: option };
    });
    array.forEach(option => (option.name = this.translate.instant(translationPrefix + option.name)));
    return array;
  }

  public remove(): void {
    this.removeClicked.emit();
  }
}
