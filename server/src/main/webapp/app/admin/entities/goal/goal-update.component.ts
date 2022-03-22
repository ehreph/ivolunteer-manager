import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Observable } from 'rxjs';
import { IInstitution } from '../../../shared/model/institution.model';
import { ICompetence } from '../../../shared/model/competence.model';
import { IBadge } from '../../../shared/model/badge.model';
import { IActivity } from '../../../shared/model/activity.model';
import { FormGroupUtil } from '../../../shared/util/formGroupUtil';
import { GoalService } from '../../../shared/services/goal.service';
import { InstitutionService } from '../../../shared/services/institution.service';
import { CompetenceService } from '../../../shared/services/competence.service';
import { ActivityService } from '../../../shared/services/activity.service';
import { BadgeService } from '../../../shared/services/badge.service';
import { Goal, IGoal } from '../../../shared/model/goal.model';

@Component({
  selector: 'jhi-goal-update',
  styleUrls: ['./goal-update.component.scss'],
  templateUrl: './goal-update.component.html',
})
export class GoalUpdateComponent implements OnInit {
  isInitComplete = false;
  isSaving = false;
  institutions: IInstitution[] = [];
  existingGoals: IGoal[] = [];
  existingCompetences: ICompetence[] = [];
  existingBadges: IBadge[] = [];
  existingActivities: IActivity[] = [];

  editForm: FormGroup;

  constructor(
    protected goalService: GoalService,
    protected institutionService: InstitutionService,
    protected competenceService: CompetenceService,
    protected activityService: ActivityService,
    protected badgeService: BadgeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {
    this.editForm = FormGroupUtil.createAdminGoalFormGroup(this.fb);
  }

  ngOnInit(): void {
    // load existing types (might be slow if a lot of entities exist)
    const getInstitutions = this.institutionService.query();
    const getBadges = this.badgeService.query();
    const getGoals = this.goalService.query();
    const getCompetences = this.competenceService.query();
    const getActivities = this.activityService.query();

    forkJoin([getInstitutions, getBadges, getGoals, getCompetences, getActivities]).subscribe(results => {
      this.institutions = results[0].body || [];
      this.existingBadges = results[1].body || [];
      this.existingGoals = results[2].body || [];
      this.existingCompetences = results[3].body || [];
      this.existingActivities = results[4].body || [];

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
        institutionId: goal.institutionId,
      });

      if (goal && goal.requirements !== undefined) {
        const fa = this.editForm.get('requirements') as FormArray;
        goal.requirements.forEach(req => {
          const fg = FormGroupUtil.createRequirementFormGroup(this.fb);
          FormGroupUtil.patchRequirementFormGroup(fg, req);
          console.debug('goal update patched formgroup: ', fg);
          fa.push(fg);
        });
      }
      if (goal && goal.awardedCompetences !== undefined) {
        const fa = this.editForm.get('goalAwards') as FormArray;
        goal.awardedCompetences.forEach(goalAward => {
          const fg = FormGroupUtil.createAwardFormGroup(this.fb);
          FormGroupUtil.patchAwardFormGroup(fg, goalAward);
          fa.push(fg);
        });
      }
    }
    this.isInitComplete = true;
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    console.debug('goal update form result: ', this.editForm.value);
    const goal = this.createFromForm();
    if (goal.id !== undefined && goal.id !== null) {
      this.subscribeToSaveResponse(this.goalService.update(goal));
    } else {
      this.subscribeToSaveResponse(this.goalService.create(goal));
    }
  }

  private createFromForm(): IGoal {
    const goal = this.editForm.value as Goal;
    goal.awardedCompetences = this.editForm.get('goalAwards')!.value.map((goalAward: any) => {
      return FormGroupUtil.createFromAwardFormGroup(goalAward, this.editForm.get(['id'])!.value);
    });
    goal.requirements = this.editForm.get('requirements')!.value.map((r: any) => {
      return FormGroupUtil.createFromRequirementsFormGroup(r, this.editForm.get(['id'])!.value);
    });
    console.debug(goal);
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

  trackById(index: number, item: IInstitution): any {
    return item.id;
  }

  get goalAwards(): FormArray {
    return this.editForm.get('goalAwards') as FormArray;
  }
}
