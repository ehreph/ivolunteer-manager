import { IGoalAward } from 'app/shared/model/goal-award.model';
import { IGoalRequirements } from 'app/shared/model/goal-requirements.model';
import { IInstitution } from './institution.model';

export interface IGoal {
  id?: number;
  name?: string;
  info?: string;
  awardedCompetences?: IGoalAward[];
  requirements?: IGoalRequirements[];
  institutionId?: number;
  institutionName?: string;
  isPersonal?: boolean;
}

export interface IVisibleGoal {
  id?: number;
  name?: string;
  info?: string;
  awardedCompetences?: IGoalAward[];
  requirements?: IGoalRequirements[];
  institution?: IInstitution;
  isPersonal?: boolean;
}

export class VisibleGoal implements IVisibleGoal {
  constructor(
    public id?: number,
    public name?: string,
    public info?: string,
    public awardedCompetences?: IGoalAward[],
    public requirements?: IGoalRequirements[],
    public institution?: IInstitution,
    public isPersonal?: boolean
  ) {}
}

export class Goal implements IGoal {
  constructor(
    public id?: number,
    public name?: string,
    public info?: string,
    public awardedCompetences?: IGoalAward[],
    public requirements?: IGoalRequirements[],
    public institutionId?: number,
    public institutionName?: string,
    public isPersonal?: boolean
  ) {}
}
