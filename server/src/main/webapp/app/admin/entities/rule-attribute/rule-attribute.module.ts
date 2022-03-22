import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IvolunteerManagerSharedModule } from 'app/shared/shared.module';
import { RuleAttributeComponent } from './rule-attribute.component';
import { RuleAttributeDetailComponent } from './rule-attribute-detail.component';
import { RuleAttributeUpdateComponent } from './rule-attribute-update.component';
import { RuleAttributeDeleteDialogComponent } from './rule-attribute-delete-dialog.component';
import { ruleAttributeRoute } from './rule-attribute.route';

@NgModule({
  imports: [IvolunteerManagerSharedModule, RouterModule.forChild(ruleAttributeRoute)],
  declarations: [RuleAttributeComponent, RuleAttributeDetailComponent, RuleAttributeUpdateComponent, RuleAttributeDeleteDialogComponent],
  entryComponents: [RuleAttributeDeleteDialogComponent],
})
export class IvolunteerManagerRuleAttributeModule {}
