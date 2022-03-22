import { Component, Input, OnInit } from '@angular/core';
import { IMyGoal } from '../../../../shared/model/user-goals.model';
import { GlobalType } from '../../../../shared/model/enumerations/global-type.constants';
import { Observable } from 'rxjs';

@Component({
  selector: 'jhi-goal-requirements-progress',
  templateUrl: './goal-requirements-progress.component.html',
  styleUrls: ['./goal-requirements-progress.component.scss'],
})
export class GoalRequirementsProgressComponent implements OnInit {
  @Input()
  myGoal: IMyGoal;
  @Input()
  showRelative: Observable<boolean>;

  GlobalType = GlobalType;

  constructor() {}

  ngOnInit(): void {}
}
