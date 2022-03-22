import { IGoal } from 'app/shared/model/goal.model';

export interface IInstitution {
  id?: number;
  name?: string;
  description?: string;
  institutionGoals?: IGoal[];
}

export class Institution implements IInstitution {
  constructor(public id?: number, public name?: string, public description?: string, public institutionGoals?: IGoal[]) {}
}
