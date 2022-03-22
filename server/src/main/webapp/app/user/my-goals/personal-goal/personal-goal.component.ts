import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { forkJoin, Observable } from 'rxjs';
import { PersonalGoalService } from './personal-goal.service';
import { Goal, IGoal } from '../../../shared/model/goal.model';
import { ICompetence } from '../../../shared/model/competence.model';
import { IBadge } from '../../../shared/model/badge.model';
import { IActivity } from '../../../shared/model/activity.model';
import { GoalService } from '../../../shared/services/goal.service';
import { InstitutionService } from '../../../shared/services/institution.service';
import { CompetenceService } from '../../../shared/services/competence.service';
import { ActivityService } from '../../../shared/services/activity.service';
import { BadgeService } from '../../../shared/services/badge.service';
import { FormGroupUtil } from '../../../shared/util/formGroupUtil';

@Component({
  selector: 'jhi-personal-goal',
  templateUrl: './personal-goal.component.html',
  styleUrls: ['./personal-goal.component.scss'],
})
export class PersonalGoalComponent implements OnInit {
  isSaving = false;
  existingGoals: IGoal[] = [];
  existingCompetences: ICompetence[] = [];
  existingBadges: IBadge[] = [];
  existingActivities: IActivity[] = [];

  editForm: FormGroup;

  constructor(
    protected goalService: GoalService,
    protected personalGoalService: PersonalGoalService,
    protected institutionService: InstitutionService,
    protected competenceService: CompetenceService,
    protected activityService: ActivityService,
    protected badgeService: BadgeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {
    this.editForm = FormGroupUtil.createPersonalGoalFormGroup(fb);
  }

  ngOnInit(): void {
    // load existing types (might be slow if a lot of entities exist)

    const getBadges = this.badgeService.query();
    const getGoals = this.goalService.query();
    const getCompetences = this.competenceService.query();
    const getActivities = this.activityService.query();

    forkJoin([getBadges, getGoals, getCompetences, getActivities]).subscribe(results => {
      this.existingBadges = results[0].body || [];
      this.existingGoals = results[1].body || [];
      this.existingCompetences = results[2].body || [];
      this.existingActivities = results[3].body || [];

      //init form
      this.activatedRoute.data.subscribe(({ goal }) => {
        if (goal) {
          this.updateForm(goal);
        }
      });
    });
  }

  updateForm(goal: IGoal): void {
    if (goal.id) {
      this.editForm.patchValue({
        id: goal.id,
        name: goal.name,
        info: goal.info,
      });

      if (goal && goal.requirements !== undefined) {
        const fa = this.editForm.get('requirements') as FormArray;
        goal.requirements.forEach(req => {
          const fg = FormGroupUtil.createRequirementFormGroup(this.fb);
          FormGroupUtil.patchRequirementFormGroup(fg, req);
          fa.push(fg);
        });
      }
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const goal = this.createFromForm();
    if (goal.id !== undefined && goal.id !== null) {
      this.subscribeToSaveResponse(this.personalGoalService.update(goal));
    } else {
      this.subscribeToSaveResponse(this.personalGoalService.create(goal));
    }
  }

  private createFromForm(): IGoal {
    const goal = this.editForm.value as Goal;
    goal.requirements = this.editForm.get('requirements')!.value.map((r: any) => {
      return FormGroupUtil.createFromRequirementsFormGroup(r, this.editForm.get(['id'])!.value);
    });
    return goal;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoal>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
