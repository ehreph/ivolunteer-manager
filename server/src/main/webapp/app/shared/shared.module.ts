import { NgModule } from '@angular/core';
import { IvolunteerManagerSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { FormItemBaseComponent } from 'app/shared/goal/form-item-base.component';
import { GoalRequirementsComponent } from 'app/shared/goal/goal-requirements/goal-requirements.component';
import { GoalAwardsComponent } from 'app/shared/goal/goal-awards/goal-awards.component';
import { RouterModule } from '@angular/router';
import { GoalAwardItemComponent } from './goal/goal-awards/goal-award-item/goal-award-item.component';
import { FormlyModule } from '@ngx-formly/core';
import { FormlyBootstrapModule } from '@ngx-formly/bootstrap';
import { GoalRequirementItemComponent } from 'app/shared/goal/goal-requirements/goal-requirement-item/goal-requirement-item.component';
import { SideBarItemComponent } from 'app/shared/admin/side-bar-item/side-bar-item.component';
import { UserBadgesOverviewComponent } from 'app/shared/general/user-badges-overview/user-badges-overview.component';
import { UserCompetencesOverviewComponent } from 'app/shared/general/user-competences-overview/user-competences-overview.component';
import { UserCompetenceUpdateModalComponent } from 'app/shared/general/user-competences-overview/user-competence-update-modal/user-competence-update-modal.component';
import { UserGoalUpdateModalComponent } from 'app/shared/general/user-goals-overview/user-goal-update-modal/user-goal-update-modal.component';
import { UserBadgeUpdateModalComponent } from 'app/shared/general/user-badges-overview/user-badges-update-modal/user-badge-update-modal.component';
import { UserGoalsOverviewComponent } from 'app/shared/general/user-goals-overview/user-goals-overview.component';
import { UserGoalsDeleteDialogComponent } from 'app/shared/general/user-goals-overview/user-goal-delete-dialog/user-goals-delete-dialog.component';
import { UserCompetenceDeleteDialogComponent } from 'app/shared/general/user-competences-overview/user-competence-delete-dialog/user-competence-delete-dialog.component';
import { UserBadgeDeleteDialogComponent } from 'app/shared/general/user-badges-overview/user-badge-delete-dialog/user-badge-delete-dialog.component';

@NgModule({
  imports: [IvolunteerManagerSharedLibsModule, RouterModule, FormlyModule.forRoot(), FormlyBootstrapModule],
  declarations: [
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    FormItemBaseComponent,
    GoalRequirementsComponent,
    GoalAwardsComponent,
    UserBadgesOverviewComponent,
    UserCompetencesOverviewComponent,
    UserGoalsOverviewComponent,
    UserCompetenceUpdateModalComponent,
    UserGoalUpdateModalComponent,
    UserCompetenceDeleteDialogComponent,
    UserBadgeDeleteDialogComponent,
    UserGoalsDeleteDialogComponent,
    UserBadgeUpdateModalComponent,
    GoalAwardItemComponent,
    GoalRequirementItemComponent,
    SideBarItemComponent,
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    IvolunteerManagerSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    GoalRequirementsComponent,
    GoalAwardsComponent,
    UserBadgesOverviewComponent,
    UserCompetencesOverviewComponent,
    UserGoalsOverviewComponent,
    UserCompetenceUpdateModalComponent,
    UserGoalUpdateModalComponent,
    UserGoalsDeleteDialogComponent,
    UserCompetenceDeleteDialogComponent,
    UserBadgeDeleteDialogComponent,
    UserBadgeUpdateModalComponent,
    SideBarItemComponent,
  ],
})
export class IvolunteerManagerSharedModule {}
