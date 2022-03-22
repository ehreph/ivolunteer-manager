import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Observable } from 'rxjs';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivityProgressService } from './activity-progress.service';
import { IActivity } from '../../../shared/model/activity.model';
import { ActivityService } from '../../../shared/services/activity.service';
import { FormGroupUtil } from '../../../shared/util/formGroupUtil';
import { IUserGoals } from '../../../shared/model/user-goals.model';
import { ActivityProgress } from '../../../shared/model/activity-progress.model';

@Component({
  selector: 'jhi-activity-progress',
  templateUrl: './activity-progress.component.html',
  styleUrls: ['./activity-progress.component.scss'],
})
export class ActivityProgressComponent implements OnInit {
  activities?: IActivity[];
  isSaving = false;
  selectedActivity: IActivity;

  editForm: FormGroup;

  constructor(
    protected activityService: ActivityService,
    protected activityProgressService: ActivityProgressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {
    this.editForm = FormGroupUtil.createActivityProgressFormGroup(fb);
  }

  ngOnInit(): void {
    // TODO query all if to much entities
    const getActivities = this.activityService.query();

    forkJoin([getActivities]).subscribe(results => {
      this.activities = results[0].body || [];
    });
  }

  trackId(index: number, item: IActivity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id;
  }

  save(): void {
    this.isSaving = true;
    const activityProgress = this.createFromForm();
    if (activityProgress.id !== undefined) {
      this.subscribeToSaveResponse(this.activityProgressService.update(activityProgress));
    } else {
      this.subscribeToSaveResponse(this.activityProgressService.create(activityProgress));
    }
  }

  private createFromForm(): ActivityProgress {
    const object = this.editForm.value;
    const activityProgress = object as ActivityProgress;
    activityProgress.activityId = this.selectedActivity.id;
    activityProgress.attributesId = this.selectedActivity.attributesId;
    activityProgress.date = object.date ? new Date(object.date) : object.date!;
    activityProgress.userId = null;
    return activityProgress;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserGoals>>): void {
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

  previousState(): void {
    window.history.back();
  }
}
