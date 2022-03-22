import { MyGoal } from '../../../shared/model/user-goals.model';
import { GlobalType } from '../../../shared/model/enumerations/global-type.constants';
import { RuleOperator } from '../../../shared/model/enumerations/rule-operator.constants';

export class RequirementRulesChecker {
  //TODO add checks for
  static canGoalBeFinished(myGoal: MyGoal): boolean {
    const req = myGoal.goal.requirements;
    const unfinished = req.filter(r => !this.checkRequirementCompleted(myGoal, r.generalId, r.type)).length;
    console.log(unfinished);
    if (unfinished > 0) {
      return false;
    } else {
      return true;
    }
  }

  static checkRequirementCompleted(myGoal: MyGoal, id: number, type: GlobalType): boolean {
    if (type === GlobalType.GOAL) {
      return this.userHasGoalCompleted(myGoal, id);
    }
    if (type === GlobalType.ACTIVITY) {
      return this.userHasActivityCompleted(myGoal, id);
    }
    if (type === GlobalType.COMPETENCE) {
      return this.userHasCompetenceCompleted(myGoal, id);
    }
    if (type === GlobalType.BADGE) {
      return this.userHasBadgeCompleted(myGoal, id);
    } else {
      return false;
    }
  }

  static userHasBadgeCompleted(myGoal: MyGoal, badgeId: number): boolean {
    return myGoal.userBadges.some(value => value.badgeId === badgeId);
  }

  static userHasGoalCompleted(myGoal: MyGoal, goalId: number): boolean {
    return myGoal.userGoals.some(value => value.goalId === goalId && value.finished);
  }

  private static userHasActivityCompleted(myGoal: MyGoal, activityId: number): boolean {
    const reqActivity = myGoal.goal.requirements
      .filter(value => value.type === GlobalType.ACTIVITY)
      .find(value => value.generalId === activityId);
    const activity = myGoal.userActivities.find(value => value.activityId === activityId);
    if (reqActivity != null && activity != null) {
      return activity.progressAmount >= reqActivity.value;
    }
    return false;
  }

  private static userHasCompetenceCompleted(myGoal: MyGoal, compId: number): boolean {
    const reqComp = myGoal.goal.requirements
      .filter(value => value.type === GlobalType.COMPETENCE)
      .find(value => value.generalId === compId);
    const comp = myGoal.userCompetences.find(value => value.compId === compId);
    if (reqComp != null && comp != null) {
      if (reqComp.operator === RuleOperator.BIGGER_THAN) {
        return comp.userLevel >= reqComp.value;
      }
      if (reqComp.operator === RuleOperator.BIGGER) {
        return comp.userLevel > reqComp.value;
      }
    }
    return false;
  }
}
