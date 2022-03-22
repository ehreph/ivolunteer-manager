import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IvolunteerManagerSharedModule } from 'app/shared/shared.module';
import { BadgeComponent } from './badge.component';
import { BadgeDetailComponent } from './badge-detail.component';
import { BadgeUpdateComponent } from './badge-update.component';
import { BadgeDeleteDialogComponent } from './badge-delete-dialog.component';
import { badgeRoute } from './badge.route';

@NgModule({
  imports: [IvolunteerManagerSharedModule, RouterModule.forChild(badgeRoute)],
  declarations: [BadgeComponent, BadgeDetailComponent, BadgeUpdateComponent, BadgeDeleteDialogComponent],
  entryComponents: [BadgeDeleteDialogComponent],
})
export class IvolunteerManagerBadgeModule {}
