import { Component, Input, OnInit } from '@angular/core';
import { Goal } from '../../../../shared/model/goal.model';
import { GlobalType } from '../../../../shared/model/enumerations/global-type.constants';

@Component({
  selector: 'jhi-goal-award-information',
  templateUrl: './goal-award-information.component.html',
  styleUrls: ['./goal-award-information.component.scss'],
})
export class GoalAwardInformationComponent implements OnInit {
  public showAwards = false;
  @Input() goal: Goal;

  GlobalType = GlobalType;

  constructor() {}

  ngOnInit(): void {}

  toggleAwards(): void {
    this.showAwards = !this.showAwards;
  }
}
