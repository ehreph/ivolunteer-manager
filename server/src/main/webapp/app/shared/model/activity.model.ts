export interface IActivity {
  id?: number;
  name?: string;
  attributesId?: number;
  attributesName?: string;
  attributesUnitName?: string;
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public name?: string,
    public attributesId?: number,
    public attributesName?: string,
    public attributesUnitName?: string
  ) {}
}
