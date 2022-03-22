import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRuleAttribute } from 'app/shared/model/rule-attribute.model';

@Component({
  selector: 'jhi-rule-attribute-detail',
  templateUrl: './rule-attribute-detail.component.html',
})
export class RuleAttributeDetailComponent implements OnInit {
  ruleAttribute: IRuleAttribute | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ruleAttribute }) => (this.ruleAttribute = ruleAttribute));
  }

  previousState(): void {
    window.history.back();
  }
}
