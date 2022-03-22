import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { VisibleGoalsComponent } from './visible-goals/visible-goals.component';
import { NgJhipsterModule } from 'ng-jhipster';
import { NgbCollapseModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { IvolunteerManagerSharedModule } from '../shared/shared.module';
import { MyGoalsComponent } from './my-goals/my-goals.component';
import { VisibleGoalCardComponent } from './visible-goals/visible-goal-card/visible-goal-card.component';
import { GoalProgressCardComponent } from './my-goals/goal-progress-card/goal-progress-card.component';
import { GoalAwardInformationComponent } from './my-goals/goal-progress-card/goal-award-information/goal-award-information.component';
import { GoalRequirementsProgressComponent } from './my-goals/goal-progress-card/goal-requirements-progress/goal-requirements-progress.component';
import { GoalRequirementInformationComponent } from './visible-goals/visible-goal-card/goal-requirement-information/goal-requirement-information.component';
import { RequirementProgressComponent } from './my-goals/goal-progress-card/goal-requirements-progress/requirement-progress/requirement-progress.component';
import { MyGoalNavigationComponent } from './my-goals/my-goal-navigation/my-goal-navigation.component';
import { ProgressCircleComponent } from './my-goals/goal-progress-card/goal-requirements-progress/requirement-progress/progress-circle/progress-circle.component';
import { NgCircleProgressModule } from 'ng-circle-progress';
import { CompletedExtensionComponent } from './my-goals/goal-progress-card/goal-requirements-progress/requirement-progress/completed-extension/completed-extension.component';
import { NoGoalInformationComponent } from './visible-goals/no-goal-information/no-goal-information.component';
import { PersonalGoalComponent } from './my-goals/personal-goal/personal-goal.component';
import { ActivityProgressComponent } from './my-goals/activity-progress/activity-progress.component';

@NgModule({
  declarations: [
    VisibleGoalsComponent,
    MyGoalsComponent,
    GoalProgressCardComponent,
    VisibleGoalCardComponent,
    PersonalGoalComponent,
    ActivityProgressComponent,
    GoalAwardInformationComponent,
    GoalRequirementsProgressComponent,
    GoalRequirementInformationComponent,
    RequirementProgressComponent,
    MyGoalNavigationComponent,
    ProgressCircleComponent,
    CompletedExtensionComponent,
    NoGoalInformationComponent,
  ],
  imports: [
    UserRoutingModule,
    CommonModule,
    NgJhipsterModule,
    NgbPaginationModule,
    FontAwesomeModule,
    IvolunteerManagerSharedModule,
    NgbCollapseModule,
    NgCircleProgressModule.forRoot({}),
  ],
})
export class UserModule {}
