import { GlobalType } from 'app/shared/model/enumerations/global-type.constants';
import { RuleOperator } from 'app/shared/model/enumerations/rule-operator.constants';

export interface IGoalRequirements {
  id?: number;
  type?: GlobalType;
  generalId?: number;
  operator?: RuleOperator;
  value?: number;
  attributesId?: number;
  attributesUnitName?: string;
  goalId?: number;
  entityName?: string;
  actStartDate?: Date;
  actEndDate?: Date;
}

export class GoalRequirements implements IGoalRequirements {
  constructor(
    public id?: number,
    public type?: GlobalType,
    public generalId?: number,
    public operator?: RuleOperator,
    public value?: number,
    public attributesId?: number,
    public attributesUnitName?: string,
    public goalId?: number,
    public entityName?: string,
    public actStartDate?: Date,
    public actEndDate?: Date
  ) {}
}
