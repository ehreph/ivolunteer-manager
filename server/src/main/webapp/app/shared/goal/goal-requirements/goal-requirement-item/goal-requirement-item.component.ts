import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormGroup } from '@angular/forms';
import { FormItemBaseComponent, SelectableEntityArray } from 'app/shared/goal/form-item-base.component';
import { IBadge } from 'app/shared/model/badge.model';
import { ICompetence } from 'app/shared/model/competence.model';
import { FormlyFieldConfig, FormlyFormOptions } from '@ngx-formly/core';
import { TranslateService } from '@ngx-translate/core';
import { GoalRequirements } from 'app/shared/model/goal-requirements.model';
import { IGoal } from 'app/shared/model/goal.model';
import { IActivity } from 'app/shared/model/activity.model';
import { RuleOperatorUtil } from 'app/shared/util/RuleOperatorUtil';
import { GlobalType } from 'app/shared/model/enumerations/global-type.constants';
import { RuleOperator } from 'app/shared/model/enumerations/rule-operator.constants';

@Component({
  selector: 'jhi-goal-requirement-item',
  templateUrl: './goal-requirement-item.component.html',
  styleUrls: ['./goal-requirement-item.component.scss'],
})
export class GoalRequirementItemComponent extends FormItemBaseComponent implements OnInit {
  @Input()
  existingBadges: IBadge[];

  @Input()
  existingCompetences: ICompetence[];
  @Input()
  existingGoals: IGoal[];
  @Input()
  existingActivities: IActivity[];

  @Input()
  model: any;
  _model: GoalRequirements = new GoalRequirements();

  @Output()
  removeClicked: EventEmitter<void> = new EventEmitter<void>();

  options: FormlyFormOptions = {};

  fields: FormlyFieldConfig[];

  public globalTypes: string[];
  public operators: string[];

  constructor(protected translate: TranslateService) {
    super(translate);
    this.globalTypes = Object.keys(GlobalType);
    this.operators = Object.keys(RuleOperator);
  }

  ngOnInit(): void {
    this._model = this.model as GoalRequirements;
    this.initForm();
  }

  private initForm(): void {
    this.fields = [
      {
        fieldGroupClassName: 'display-flex row',
        fieldGroup: [
          {
            className: 'col-auto flex-1',
            key: 'type',
            type: 'select',
            modelOptions: {
              updateOn: 'change',
            },
            templateOptions: {
              label: 'type',
              required: true,
              options: this.enumToSelectionArray('ivolunteerManagerApp.GlobalType.', this.globalTypes),
              valueProp: 'value',
              labelProp: 'name',
              change: field => {
                const typeControl = field.form.get('type');
                this.resetForm(typeControl.value, field.form);
                this.updateOperator(field.form, 'type');
              },
            },
            expressionProperties: {
              'templateOptions.label': this.translate.stream('ivolunteerManagerApp.goalRequirements.ruleType'),
            },
          },
          {
            className: 'col-auto flex-1',
            key: 'generalId',
            type: 'select',
            templateOptions: {
              label: 'instance',
              required: true,
              options: this.getSelectableEntities(this._model.type),
              valueProp: 'id',
              labelProp: 'name',
            },
            expressionProperties: {
              'templateOptions.label': this.translate.stream('ivolunteerManagerApp.goal.goalRequirements.instance'),
            },
            hooks: {
              onInit: field => {
                const typeControl = field.form.get('type');
                const generalIdControl = field.form.get('generalId');

                typeControl.valueChanges.subscribe(() => {
                  field.templateOptions.options = this.getSelectableEntities(typeControl.value);
                });
                generalIdControl.valueChanges.subscribe(() => {
                  this.patchFormAfterObjectSelection(this._model.type, field.form, generalIdControl.value);
                });
              },
            },
            hideExpression: () => this._model.type === null,
          },
          {
            className: 'col-auto flex-1',
            key: 'operator',
            type: 'select',
            modelOptions: {
              updateOn: 'change',
            },
            templateOptions: {
              label: 'operator',
              required: true,
              options: this.enumToSelectionArray('ivolunteerManagerApp.RuleOperator.', this.filterRuleOperatorAfterType(this._model.type)),
              valueProp: 'value',
              labelProp: 'name',
            },
            expressionProperties: {
              'templateOptions.label': this.translate.stream('ivolunteerManagerApp.goalRequirements.operator'),
            },
            hooks: {
              onInit: field => {
                const typeControl = field.form.get('type');
                typeControl.valueChanges.subscribe(
                  () =>
                    (field.templateOptions.options = this.enumToSelectionArray(
                      'ivolunteerManagerApp.RuleOperator.',
                      this.filterRuleOperatorAfterType(typeControl.value)
                    ))
                );
              },
            },
            hideExpression: () => this._model.type === null || this._model.type !== GlobalType.COMPETENCE,
          },
          {
            className: 'col-auto flex-1',
            key: 'value',
            type: 'input',
            templateOptions: {
              type: 'number',
              required: true,
              addonRight: {},
            },
            expressionProperties: {
              'templateOptions.label': this.translate.stream('ivolunteerManagerApp.goalRequirements.value'),
            },
            hooks: {
              onInit: field => {
                const generalIdControl = field.form.get('generalId');
                generalIdControl.valueChanges.subscribe(value => {
                  const typeControl = field.form.get('type');
                  if (typeControl.value === GlobalType.ACTIVITY && value !== null) {
                    console.log(value);
                    const activity = this.existingActivities.find(act => act.id === value);
                    const addonText = activity ? activity.attributesUnitName : '';
                    field.templateOptions.addonRight = { text: addonText };
                  } else {
                    field.templateOptions.addonRight = null;
                  }
                });
                // setMeasuringAttributeName.call(this, field);
              },
            },
            //TODO ADD DISPLAY MEASUREMENT if ACTIVITY not for competences
            hideExpression: () => this._model.type !== GlobalType.COMPETENCE && this._model.type !== GlobalType.ACTIVITY,
          },
        ],
      },
      {
        //TODO fix date selection
        fieldGroupClassName: 'display-flex row',
        fieldGroup: [
          {
            className: 'col-auto flex-1',
            key: 'actStartDate',
            type: 'input',
            defaultValue: Date.now(),
            templateOptions: {
              label: 'actStartDate',
              type: 'date',
              required: true,
            },
            expressionProperties: {
              'templateOptions.label': this.translate.stream('ivolunteerManagerApp.goal.goalRequirements.startDate'),
            },
          },
          {
            className: 'col-auto flex-1',
            key: 'actEndDate',
            type: 'input',
            defaultValue: Date.now(),
            templateOptions: {
              label: 'actEndDate',
              type: 'date',
              required: true,
            },
            expressionProperties: {
              'templateOptions.label': this.translate.stream('ivolunteerManagerApp.goal.goalRequirements.endDate'),
            },
          },
        ],
        hideExpression: () => this._model.type !== GlobalType.ACTIVITY,
      },
    ];
  }

  private getSelectableEntities(type: string): SelectableEntityArray {
    switch (type) {
      case GlobalType.GOAL:
        return this.existingGoals;
        break;
      case GlobalType.ACTIVITY:
        return this.existingActivities;
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

  private updateOperator(ruleControl: AbstractControl, selector: string): void {
    const type = ruleControl.get(selector)?.value;
    switch (type) {
      case GlobalType.GOAL:
      case GlobalType.ACTIVITY:
        ruleControl.get('operator')?.patchValue(RuleOperator.FINISHED);
        break;
      case GlobalType.COMPETENCE:
        ruleControl.get('operator')?.patchValue(RuleOperator.BIGGER);
        break;
      case GlobalType.BADGE:
        ruleControl.get('operator')?.patchValue(RuleOperator.AWARDED);
        break;
      default:
        break;
    }
  }

  private filterRuleOperatorAfterType(type: string): string[] {
    // this.patchFormFields(ruleControl,selector);

    switch (type) {
      case GlobalType.GOAL:
        return RuleOperatorUtil.goalOperators;
        break;
      case GlobalType.ACTIVITY:
        return RuleOperatorUtil.activityOperators;
        break;
      case GlobalType.COMPETENCE:
        return RuleOperatorUtil.competenceOperators;
        break;
      case GlobalType.BADGE:
        return RuleOperatorUtil.badgeOperators;
        break;
      default:
        return [];
        break;
    }
  }

  private resetForm(type: string, form: FormGroup): void {
    switch (type) {
      case GlobalType.GOAL:
      case GlobalType.BADGE:
        form.patchValue({
          generalId: null,
          attributesId: null,
          value: null,
          actStartDate: null,
          actEndDate: null,
        });
        break;
      case GlobalType.COMPETENCE:
        form.patchValue({
          generalId: null,
          attributesId: null,
          actStartDate: null,
          actEndDate: null,
        });
        break;
      case GlobalType.ACTIVITY:
        form.patchValue({
          generalId: null,
        });
        break;
      default:
        break;
    }
  }

  private patchFormAfterObjectSelection(type: string, form: FormGroup, generalId: number): void {
    switch (type) {
      case GlobalType.GOAL:
      case GlobalType.BADGE:
        console.debug('patch GOAL BADGE');
        this.patchGoalAndBadge(form);
        break;
      case GlobalType.COMPETENCE:
        console.debug('patch COMPETENCE');
        this.patchCompetence(form);
        break;
      case GlobalType.ACTIVITY:
        console.debug('patch ACTIVITY');
        this.patchActivity(form, generalId);
        break;
      default:
        console.debug('patch NOTHING');
        break;
    }
  }

  private patchGoalAndBadge(form: FormGroup): void {
    form.patchValue({
      attributesId: null,
      value: null,
      actStartDate: null,
      actEndDate: null,
    });
  }

  private patchCompetence(form: FormGroup): void {
    form.patchValue({
      actStartDate: null,
      actEndDate: null,
    });
  }

  private patchActivity(form: FormGroup, generalId: number): void {
    try {
      console.debug('generalId', generalId, this.existingActivities);
      const activity = this.existingActivities.find(value => value.id === generalId);
      form.patchValue({
        attributesId: activity.attributesId,
      });
    } catch (e) {
      console.debug(e);
    }
  }
}
