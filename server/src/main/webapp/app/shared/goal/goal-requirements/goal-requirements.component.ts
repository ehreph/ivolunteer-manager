import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IRuleAttribute } from 'app/shared/model/rule-attribute.model';
import { IGoal } from 'app/shared/model/goal.model';
import { IGoalRequirements } from 'app/shared/model/goal-requirements.model';
import { HttpResponse } from '@angular/common/http';
import { IActivity } from 'app/shared/model/activity.model';
import { IBadge } from 'app/shared/model/badge.model';
import { ICompetence } from 'app/shared/model/competence.model';
import { RuleOperator } from 'app/shared/model/enumerations/rule-operator.constants';
import { RuleAttributeService } from '@services/rule-attribute.service';

type SelectableEntity = IGoalRequirements | IRuleAttribute;

@Component({
  selector: 'jhi-goal-requirements',
  templateUrl: './goal-requirements.component.html',
  styleUrls: ['./goal-requirements.component.scss'],
})
export class GoalRequirementsComponent implements OnInit {
  @Input()
  editForm!: FormGroup;
  @Input()
  existingBadges: IBadge[] = [];

  @Input()
  existingCompetences: ICompetence[] = [];

  @Input()
  existingGoals: IGoal[] = [];
  @Input()
  existingActivities: IActivity[] = [];

  existingRuleAttributes: IRuleAttribute[] = [];

  RuleOperator = RuleOperator;

  ruleOperators: string[];

  constructor(protected ruleAttributeService: RuleAttributeService, protected fb: FormBuilder) {
    this.ruleOperators = Object.keys(this.RuleOperator);
  }

  ngOnInit(): void {
    this.ruleAttributeService.query().subscribe((res: HttpResponse<IRuleAttribute[]>) => (this.existingRuleAttributes = res.body || []));
  }

  get requirements(): FormArray {
    return this.editForm.get('requirements') as FormArray;
  }

  newGoalRequirement(): FormGroup {
    return this.fb.group({
      id: [],
      type: [null, Validators.required],
      generalId: ['', Validators.required],
      operator: [null, Validators.required],
      value: 0,
      attributesId: null,
      goalId: '',
      actStartDate: '',
      actEndDate: '',
    });
  }

  addRequirement(): void {
    this.requirements.push(this.newGoalRequirement());
  }

  removeRequirement(index: number): void {
    this.requirements.removeAt(index);
  }

  trackById(index: number, item: any): SelectableEntity {
    return item.id;
  }
}
