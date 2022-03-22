import { Goal } from './goal.model';
import { UserCompetence } from './user-competence.model';
import { UserBadge } from './user-badge.model';
import { UserActivity } from './user-activity.model';

export interface IUserGoals {
  id?: number;
  userId?: number;
  goalId?: number;
  goalName?: string;
  finished?: boolean;
}

export class UserGoals implements IUserGoals {
  constructor(public id?: number, public userId?: number, public goalId?: number, public goalName?: string, public finished?: boolean) {}
}

export interface IMyGoal {
  id?: number;
  userId?: number;
  goal?: Goal;
  userCompetences?: UserCompetence[];
  userBadges?: UserBadge[];
  userGoals?: UserGoals[];
  userActivities?: UserActivity[];
}

export class MyGoal implements IMyGoal {
  constructor(
    public id?: number,
    public userId?: number,
    public goal?: Goal,
    public userCompetences?: UserCompetence[],
    public userBadges?: UserBadge[],
    public userGoals?: UserGoals[],
    public userActivities?: UserActivity[]
  ) {}
}
