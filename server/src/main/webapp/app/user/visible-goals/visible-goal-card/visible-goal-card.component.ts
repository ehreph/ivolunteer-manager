import { Component, Input, OnInit } from '@angular/core';
import { IVisibleGoal } from '../../../shared/model/goal.model';
import { JhiEventManager } from 'ng-jhipster';
import { GlobalType } from '../../../shared/model/enumerations/global-type.constants';
import { UserGoalsService } from '../../../shared/services/user-goals.service';

@Component({
  selector: 'jhi-visible-goal-card',
  templateUrl: './visible-goal-card.component.html',
  styleUrls: ['./visible-goal-card.component.scss'],
})
export class VisibleGoalCardComponent implements OnInit {
  @Input() goal: IVisibleGoal | undefined;
  GlobalType = GlobalType;

  constructor(protected userGoalsService: UserGoalsService, protected eventManager: JhiEventManager) {}

  ngOnInit(): void {}

  subscribeToGoal(): void {
    this.userGoalsService.subscribeUserToGoal(this.goal.id).subscribe(() => {
      this.eventManager.broadcast('visibleGoalListModification');
    });
  }
}
