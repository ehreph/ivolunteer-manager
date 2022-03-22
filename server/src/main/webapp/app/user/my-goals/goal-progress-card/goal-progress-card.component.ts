import { ChangeDetectionStrategy, Component, Input, OnChanges, OnInit } from '@angular/core';
import { faTrashAlt } from '@fortawesome/free-solid-svg-icons/faTrashAlt';
import { faCheckCircle } from '@fortawesome/free-solid-svg-icons/faCheckCircle';
import { JhiEventManager } from 'ng-jhipster';
import { IMyGoal } from '../../../shared/model/user-goals.model';
import { RequirementRulesChecker } from './requirement-rules-checker';
import { UserGoalsService } from '../../../shared/services/user-goals.service';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'jhi-goal-progress-card',
  templateUrl: './goal-progress-card.component.html',
  styleUrls: ['./goal-progress-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GoalProgressCardComponent implements OnInit, OnChanges {
  ruleChecker = RequirementRulesChecker;

  @Input()
  myGoal: IMyGoal | undefined;
  @Input()
  showRelative: Observable<boolean>;

  public showRequirements = false;

  goalFinished = false;

  faCheckCircle = faCheckCircle;
  faTrashAlt = faTrashAlt;

  constructor(protected userGoalsService: UserGoalsService, protected eventManager: JhiEventManager) {}

  ngOnInit(): void {
  }

  ngOnChanges(): void {
    this.goalFinished = RequirementRulesChecker.canGoalBeFinished(this.myGoal);
  }


  toggleRequirements(): void {
    this.showRequirements = !this.showRequirements;
  }

  finishGoal(): void {
    this.userGoalsService.finishUserGoal(this.myGoal.id).subscribe(() => {
      this.eventManager.broadcast('userGoalListModification');
    });
  }

  removeGoal(): void {
    this.userGoalsService.delete(this.myGoal.id).subscribe(() => {
      this.eventManager.broadcast('userGoalListModification');
    });
  }

}
