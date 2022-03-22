import { GlobalType } from 'app/shared/model/enumerations/global-type.constants';

export interface IRuleAttribute {
  id?: number;
  ruleType?: GlobalType;
  name?: string;
  unitName?: string;
}

export class RuleAttribute implements IRuleAttribute {
  constructor(public id?: number, public ruleType?: GlobalType, public name?: string, public unitName?: string) {}
}
