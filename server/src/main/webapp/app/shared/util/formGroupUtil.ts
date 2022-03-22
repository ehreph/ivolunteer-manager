import { GoalAward, IGoalAward } from '../model/goal-award.model';
import { GoalRequirements, IGoalRequirements } from '../model/goal-requirements.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { formatDate } from '@angular/common';

export class FormGroupUtil {
  //-------------------- create entities from form group ------------
  static createFromAwardFormGroup(object: any, goalId: number): IGoalAward {
    const award = object as GoalAward;
    award.goalId = goalId;
    return award;
  }

  static createFromRequirementsFormGroup(object: any, goalId: number): IGoalRequirements {
    const requirement = object as GoalRequirements;
    requirement.goalId = goalId;
    requirement.actStartDate = object.actStartDate ? new Date(object.actStartDate) : object.actStartDate!;
    requirement.actEndDate = object.actEndDate ? new Date(object.actEndDate) : object.actEndDate!;
    return requirement;
  }

  //-------------------- create form groups ------------
  static createAdminGoalFormGroup(fb: FormBuilder): FormGroup {
    return fb.group({
      id: [],
      name: [null, Validators.required],
      info: [],
      institutionId: [null, Validators.required],
      requirements: fb.array([]),
      goalAwards: fb.array([]),
    });
  }

  static createPersonalGoalFormGroup(fb: FormBuilder): FormGroup {
    return fb.group({
      id: [],
      name: [null, Validators.required],
      info: [],
      requirements: fb.array([]),
    });
  }

  static createRequirementFormGroup(fb: FormBuilder): FormGroup {
    return fb.group({
      id: [],
      type: [null, Validators.required],
      generalId: [null, Validators.required],
      operator: [null, Validators.required],
      value: 0,
      attributesId: '',
      goalId: '',
      actStartDate: '',
      actEndDate: '',
    });
  }

  static createActivityProgressFormGroup(fb: FormBuilder): FormGroup {
    return fb.group({
      activityId: [null, Validators.required],
      value: [1, [Validators.required, Validators.min(1)]],
      date: [null, Validators.required],
    });
  }

  static createAwardFormGroup(fb: FormBuilder): FormGroup {
    return fb.group({
      id: [],
      awardType: [null, Validators.required],
      generalId: [null, Validators.required],
      increaseLevel: [1, Validators.min(1)],
      goalId: [],
    });
  }

  //-------------------- patch form groups ------------

  static patchAwardFormGroup(fg: FormGroup, goalAward: IGoalAward): void {
    fg.patchValue({
      id: goalAward.id,
      awardType: goalAward.awardType,
      generalId: goalAward.generalId,
      increaseLevel: goalAward.increaseLevel,
      goalId: goalAward.goalId,
    });
  }

  static patchRequirementFormGroup(fg: FormGroup, req: IGoalRequirements): void {
    //TODO make locale dynamic
    fg.patchValue({
      id: req.id,
      type: req.type,
      generalId: req.generalId,
      operator: req.operator,
      value: req.value,
      attributesId: req.attributesId,
      goalId: req.goalId,
      actStartDate: formatDate(req.actStartDate, 'yyyy-MM-dd', 'en'),
      actEndDate: formatDate(req.actEndDate, 'yyyy-MM-dd', 'en'),
    });
  }
}
