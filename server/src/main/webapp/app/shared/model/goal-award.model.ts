import { GlobalType } from 'app/shared/model/enumerations/global-type.constants';

export interface IGoalAward {
  id?: number;
  awardType?: GlobalType;
  generalId?: number;
  increaseLevel?: number;
  goalId?: number;
  entityName?: string;
}

export class GoalAward implements IGoalAward {
  constructor(
    public id?: number,
    public awardType?: GlobalType,
    public generalId?: number,
    public increaseLevel?: number,
    public goalId?: number,
    public entityName?: string
  ) {}
}
