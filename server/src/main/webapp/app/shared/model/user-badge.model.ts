export interface IUserBadge {
  id?: number;
  userId?: number;
  badgeId?: number;
  badgeName?: string;
}

export class UserBadge implements IUserBadge {
  constructor(public id?: number, public userId?: number, public badgeId?: number, public badgeName?: string) {}
}
