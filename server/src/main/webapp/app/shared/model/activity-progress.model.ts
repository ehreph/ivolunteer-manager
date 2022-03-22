export class IActivityProgress {
  id?: number;
  activityId?: number;
  value?: number;
  date?: Date;
  attributesId?: number;
  userId?: number;
}

export class ActivityProgress implements IActivityProgress {
  constructor(
    public id?: number,
    public activityId?: number,
    public value?: number,
    public date?: Date,
    public attributesId?: number,
    public userId?: number
  ) {}
}
