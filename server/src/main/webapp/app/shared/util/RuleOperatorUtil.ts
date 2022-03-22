import { RuleOperator } from 'app/shared/model/enumerations/rule-operator.constants';

export class RuleOperatorUtil {
  static get competenceOperators(): RuleOperator[] {
    return [RuleOperator.BIGGER, RuleOperator.BIGGER_THAN];
  }

  static get goalOperators(): RuleOperator[] {
    return [RuleOperator.FINISHED];
  }

  static get activityOperators(): RuleOperator[] {
    return [RuleOperator.FINISHED];
  }

  static get badgeOperators(): RuleOperator[] {
    return [RuleOperator.AWARDED];
  }
}
