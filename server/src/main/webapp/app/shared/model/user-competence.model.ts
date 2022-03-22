export interface IUserCompetence {
  id?: number;
  userId?: number;
  userLevel?: number;
  compId?: number;
  compName?: string;
}

export class UserCompetence implements IUserCompetence {
  constructor(public id?: number, public userId?: number, public userLevel?: number, public compId?: number, public compName?: string) {}
}
