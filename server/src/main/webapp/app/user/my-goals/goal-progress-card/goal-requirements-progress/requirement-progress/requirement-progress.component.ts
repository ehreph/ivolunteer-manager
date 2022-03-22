import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { IGoalRequirements } from '../../../../../shared/model/goal-requirements.model';
import { RequirementRulesChecker } from '../../requirement-rules-checker';
import { IMyGoal } from '../../../../../shared/model/user-goals.model';
import { Progress } from './progress-circle/progress-circle.component';
import { TranslateService } from '@ngx-translate/core';
import { GlobalType } from '../../../../../shared/model/enumerations/global-type.constants';
import { RuleOperator } from '../../../../../shared/model/enumerations/rule-operator.constants';
import { Observable } from 'rxjs';

@Component({
  selector: 'jhi-requirement-progress',
  templateUrl: './requirement-progress.component.html',
  styleUrls: ['./requirement-progress.component.scss'],
})
export class RequirementProgressComponent implements OnInit, OnChanges {
  @Input()
  myGoal: IMyGoal;

  @Input()
  requirement: IGoalRequirements;
  @Input()
  showRelative: Observable<boolean>;

  GlobalType = GlobalType;

  data: Progress;

  unitName: string;

  completed: boolean;

  constructor(private translateService: TranslateService) {}

  ngOnInit(): void {
    this.completed = RequirementRulesChecker.checkRequirementCompleted(this.myGoal, this.requirement.generalId, this.requirement.type);
  }

  ngOnChanges(changes: SimpleChanges): void {
    const req = this.requirement;
    switch (req.type) {
      case GlobalType.COMPETENCE: {
        this.calculateCompetenceData(this.requirement);
        break;
      }
      case GlobalType.ACTIVITY: {
        this.calculateActivityData(req);
        break;
      }
      default:
        console.debug('nothing to calculate for type {}', req.type);
    }
  }

  private calculateActivityData(req: IGoalRequirements): void {
    const activity = this.myGoal.userActivities.find(value => value.activityId === req.generalId);
    let progressAmount = 0;
    if (activity) {
      progressAmount = activity.progressAmount;
    }

    if (this.showRelative) {
      this.data = {
        title: '%',
        unit: this.requirement.attributesUnitName,
        subTitle: this.requirement.entityName,
        percentage: this.toPercent(progressAmount, req.value),
        amount: progressAmount,
      };
    } else {
      this.data = {
        title: progressAmount.toFixed(0).toString() + '/' + req.value.toFixed(0).toString(),
        unit: this.requirement.attributesUnitName,
        subTitle: this.requirement.entityName,
        percentage: this.toPercent(progressAmount, req.value),
        amount: progressAmount,
      };
    }
    console.debug('activity data: ', activity, this.data);
  }

  private calculateCompetenceData(req: IGoalRequirements): void {
    const competence = this.myGoal.userCompetences.find(value => value.compId === req.generalId);

    let requiredValue: number;
    let level = 0;
    if (competence) {
      level = competence.userLevel;
    }

    if (req.operator === RuleOperator.BIGGER_THAN) {
      requiredValue = req.value;
    } else if (req.operator === RuleOperator.BIGGER) {
      requiredValue = req.value + 1;
    }

    if (this.showRelative) {
      this.data = {
        title: '%',
        unit: this.requirement.attributesUnitName,
        subTitle: this.requirement.entityName,
        percentage: this.toPercent(level, requiredValue),
        amount: level,
      };
    } else {
      const operator = req.operator === RuleOperator.BIGGER_THAN ? '>=' : '>';
      const title = level.toFixed(0).toString() + operator + req.value.toFixed(0).toString();

      this.data = {
        title: title,
        unit: this.translateService.instant('level'), //TODO TRANSLATE on lanuage change
        subTitle: this.requirement.entityName,
        percentage: this.toPercent(level, requiredValue),
        amount: level,
      };
    }
    console.debug('comptence data: ', competence, this.data);
  }

  private toPercent(userProgress: number, neededProgress: number): number {
    if (neededProgress === 0) {
      return 100;
    }

    return (userProgress / neededProgress) * 100;
  }
}
