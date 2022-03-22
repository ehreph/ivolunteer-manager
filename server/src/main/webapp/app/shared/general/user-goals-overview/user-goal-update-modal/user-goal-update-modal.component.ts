import { Component, OnInit } from '@angular/core';
import { IUserCompetence } from '../../../../shared/model/user-competence.model';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { ICompetence } from '../../../../shared/model/competence.model';
import { IUserGoals, UserGoals } from '../../../../shared/model/user-goals.model';
import { IGoal } from '../../../../shared/model/goal.model';
import { UserGoalsService } from '@services/user-goals.service';
import { GoalService } from '@services/goal.service';

@Component({
  selector: 'jhi-user-goal-update-modal',
  templateUrl: './user-goal-update-modal.component.html',
  styleUrls: ['./user-goal-update-modal.component.scss'],
})
export class UserGoalUpdateModalComponent implements OnInit {
  isSaving = false;
  userGoal: IUserGoals | undefined;

  goals: IGoal[] = [];
  userId: any;

  editForm = this.fb.group({
    id: [],
    userId: [],
    finished: [],
    goalId: [null, Validators.required],
  });

  constructor(
    protected userGoalsService: UserGoalsService,
    protected goalService: GoalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected activeModal: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.userGoal) {
      this.editForm.get('goalId').disable();
      this.goalService.find(this.userGoal.goalId).subscribe(res => {
        if (res) {
          this.goals.push(res.body);
        }
      });
      this.updateForm(this.userGoal);
    } else {
      this.goalService.findNewGoalsForUser(this.userId).subscribe((res: HttpResponse<IGoal[]>) => (this.goals = res.body || []));
      this.editForm.patchValue({
        userId: this.userId,
      });
    }
  }

  updateForm(userGoal: IUserGoals): void {
    this.editForm.patchValue({
      id: userGoal.id,
      userId: this.userId,
      finished: userGoal.finished,
      goalId: userGoal.goalId,
    });
  }

  save(): void {
    this.isSaving = true;
    const userGoal = this.createFromForm();
    console.debug(userGoal);
    if (userGoal.id !== undefined && userGoal.id !== null) {
      this.subscribeToSaveResponse(this.userGoalsService.update(userGoal));
    } else {
      this.subscribeToSaveResponse(this.userGoalsService.create(userGoal));
    }
  }

  private createFromForm(): IUserGoals {
    return {
      ...new UserGoals(),
      id: this.editForm.get(['id'])!.value,
      userId: this.userId,
      finished: this.editForm.get(['finished'])!.value,
      goalId: this.editForm.get(['goalId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserCompetence>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.close();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICompetence): any {
    return item.id;
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  close(): void {
    this.activeModal.close(true);
  }
}
