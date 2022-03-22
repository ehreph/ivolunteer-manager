export interface IBadge {
  id?: number;
  name?: string;
}

export class Badge implements IBadge {
  constructor(public id?: number, public name?: string) {}
}
