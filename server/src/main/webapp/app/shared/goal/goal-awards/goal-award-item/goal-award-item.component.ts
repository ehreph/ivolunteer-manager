import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormItemBaseComponent, SelectableEntityArray } from 'app/shared/goal/form-item-base.component';
import { IBadge } from 'app/shared/model/badge.model';
import { ICompetence } from 'app/shared/model/competence.model';
import { FormlyFieldConfig, FormlyFormOptions } from '@ngx-formly/core';
import { TranslateService } from '@ngx-translate/core';
import { GoalAward } from 'app/shared/model/goal-award.model';
import { GlobalType } from 'app/shared/model/enumerations/global-type.constants';

@Component({
  selector: 'jhi-goal-award-item',
  templateUrl: './goal-award-item.component.html',
  styleUrls: ['./goal-award-item.component.scss'],
})
export class GoalAwardItemComponent extends FormItemBaseComponent implements OnInit {
  @Input()
  existingBadges: IBadge[] = [];

  @Input()
  existingCompetences: ICompetence[] = [];

  @Input()
  model: any;
  _model: GoalAward = new GoalAward();

  @Output()
  removeClicked: EventEmitter<void> = new EventEmitter<void>();

  options: FormlyFormOptions = {};

  fields: FormlyFieldConfig[];

  public globalTypes: string[];

  constructor(protected translate: TranslateService) {
    super(translate);
    this.globalTypes = Object.keys(GlobalType);
    this.globalTypes = this.globalTypes.filter(value => value !== GlobalType.GOAL && value !== GlobalType.ACTIVITY);
  }

  ngOnInit(): void {
    this._model = this.model as GoalAward;
    this.initForm();
  }

  private initForm(): void {
    this.fields = [
      {
        fieldGroupClassName: 'display-flex row',
        fieldGroup: [
          {
            className: 'col-auto flex-1',
            key: 'awardType',
            type: 'select',
            modelOptions: {
              updateOn: 'change',
            },
            templateOptions: {
              label: 'awardType',
              required: true,
              options: this.enumToSelectionArray('ivolunteerManagerApp.GlobalType.', this.globalTypes),
              valueProp: 'value',
              labelProp: 'name',
            },
            expressionProperties: {
              'templateOptions.label': this.translate.stream('ivolunteerManagerApp.goalAward.awardType'),
            },
          },
          {
            className: 'col-auto flex-1',
            key: 'generalId',
            type: 'select',

            templateOptions: {
              label: 'instance',
              required: true,
              options: this.getSelectableEntities(this._model.awardType),
              valueProp: 'id',
              labelProp: 'name',
            },
            expressionProperties: {
              'templateOptions.label': this.translate.stream('ivolunteerManagerApp.goalAward.instance'),
            },
            hooks: {
              onInit: field => {
                const typeControl = field.form.get('awardType');
                typeControl.valueChanges.subscribe(() => (field.templateOptions.options = this.getSelectableEntities(typeControl.value)));
              },
            },
            hideExpression: () => this._model.awardType === null,
          },
          {
            className: 'col-auto flex-1',
            key: 'increaseLevel',
            type: 'input',
            templateOptions: {
              type: 'number',
              required: true,
              label: 'increaseLevel',
            },
            expressionProperties: {
              'templateOptions.label': this.translate.stream('ivolunteerManagerApp.goalAward.increaseLevel'),
            },
            hideExpression: () => this._model.awardType !== GlobalType.COMPETENCE,
          },
        ],
      },
    ];
  }

  private getSelectableEntities(type: string): SelectableEntityArray {
    switch (type) {
      case GlobalType.GOAL:
        return [];
        break;
      case GlobalType.ACTIVITY:
        return [];
        break;
      case GlobalType.COMPETENCE:
        return this.existingCompetences;
        break;
      case GlobalType.BADGE:
        return this.existingBadges;
        break;
      default:
        return [];
    }
  }
}
