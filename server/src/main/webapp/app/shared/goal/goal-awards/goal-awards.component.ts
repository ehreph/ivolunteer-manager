import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IBadge } from 'app/shared/model/badge.model';
import { ICompetence } from 'app/shared/model/competence.model';

@Component({
  selector: 'jhi-goal-awards',
  templateUrl: './goal-awards.component.html',
  styleUrls: ['./goal-awards.component.scss'],
})
export class GoalAwardsComponent implements OnInit {
  @Input()
  editForm!: FormGroup;

  @Input()
  existingBadges: IBadge[] = [];

  @Input()
  existingCompetences: ICompetence[] = [];

  constructor(protected fb: FormBuilder) {}

  ngOnInit(): void {}

  get goalAwards(): FormArray {
    return this.editForm.get('goalAwards') as FormArray;
  }

  addGoalAward(): void {
    this.goalAwards.push(this.newGoalAward());
  }

  remove(index: number): void {
    this.goalAwards.removeAt(index);
  }

  newGoalAward(): FormGroup {
    return this.fb.group({
      id: [],
      awardType: [null, Validators.required],
      generalId: [null, Validators.required],
      increaseLevel: [0, Validators.min(0)],
      goalId: [],
    });
  }
}
