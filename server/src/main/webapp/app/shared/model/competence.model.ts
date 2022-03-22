export interface ICompetence {
  id?: number;
  name?: string;
  maxLevel?: number;
}

export class Competence implements ICompetence {
  constructor(public id?: number, public name?: string, public maxLevel?: number) {}
}
