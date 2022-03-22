import { Component, Input, OnInit } from '@angular/core';
import { IVisibleGoal } from '../../../../shared/model/goal.model';
import { GlobalType } from '../../../../shared/model/enumerations/global-type.constants';

@Component({
  selector: 'jhi-goal-requirement-information',
  templateUrl: './goal-requirement-information.component.html',
  styleUrls: ['./goal-requirement-information.component.scss'],
})
export class GoalRequirementInformationComponent implements OnInit {
  showRequirements = false;
  GlobalType = GlobalType;

  @Input() goal: IVisibleGoal;

  constructor() {}

  ngOnInit(): void {}
  toggleRequirements(): void {
    this.showRequirements = !this.showRequirements;
  }
}
