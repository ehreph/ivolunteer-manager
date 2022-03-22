import { ActivityProgress } from '@model/activity-progress.model';

export interface IUserActivity {
  id?: number;
  userId?: number;
  activityId?: number;
  name?: string;
  attributeName?: string;
  attributeUnitName?: string;
  progress?: ActivityProgress[];
  progressAmount?: number;
  finished?: boolean;
}

export class UserActivity implements IUserActivity {
  constructor(
    public id?: number,
    public userId?: number,
    public activityId?: number,
    public name?: string,
    public attributeName?: string,
    public attributeUnitName?: string,
    public progress?: ActivityProgress[],
    public progressAmount?: number,
    public finished?: boolean
  ) {}
}
