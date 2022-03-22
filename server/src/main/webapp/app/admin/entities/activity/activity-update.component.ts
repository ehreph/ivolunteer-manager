import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRuleAttribute } from '../../../shared/model/rule-attribute.model';
import { ActivityService } from '../../../shared/services/activity.service';
import { RuleAttributeService } from '../../../shared/services/rule-attribute.service';
import { GlobalType } from '../../../shared/model/enumerations/global-type.constants';
import { Activity, IActivity } from '../../../shared/model/activity.model';

@Component({
  selector: 'jhi-activity-update',
  templateUrl: './activity-update.component.html',
})
export class ActivityUpdateComponent implements OnInit {
  isSaving = false;
  existingRuleAttributes: IRuleAttribute[] = [];
  editForm = this.fb.group({
    id: [],
    name: [],
    attributesId: [],
  });

  constructor(
    protected activityService: ActivityService,
    protected ruleAttributeService: RuleAttributeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activity: activity }) => {
      this.updateForm(activity);
    });

    this.ruleAttributeService
      .query({ filter: GlobalType.ACTIVITY })
      .subscribe((res: HttpResponse<IRuleAttribute[]>) => (this.existingRuleAttributes = res.body || []));
  }

  updateForm(activity: IActivity): void {
    this.editForm.patchValue({
      id: activity.id,
      name: activity.name,
      attributesId: activity.attributesId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activity = this.createFromForm();
    if (activity.id !== undefined) {
      this.subscribeToSaveResponse(this.activityService.update(activity));
    } else {
      this.subscribeToSaveResponse(this.activityService.create(activity));
    }
  }

  private createFromForm(): IActivity {
    return {
      ...new Activity(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      attributesId: this.editForm.get(['attributesId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivity>>): void {
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

  findRuleAttributesOfActivity(): IRuleAttribute[] {
    return this.existingRuleAttributes.filter(at => at.ruleType === GlobalType.ACTIVITY.valueOf());
  }
}
