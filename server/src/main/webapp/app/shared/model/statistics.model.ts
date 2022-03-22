export interface IUserStatistics {
  userid?: number;
  firstName?: string;
  lastName?: string;
  finishedGoals?: number;
  openGoals?: number;
  goals?: number;
  finishedGoalsPerMonth?: Map<Date, number>;
  finishedGoalsPerDate?: Map<Date, number>;
  levelPerCompetence?: Map<string, number>;
}
