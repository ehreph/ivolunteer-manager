import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Authority } from '../shared/constants/authority.constants';

import { UserRouteAccessService } from '../core/auth/user-route-access-service';
import { VisibleGoalsComponent } from './visible-goals/visible-goals.component';
import { MyGoalsComponent } from './my-goals/my-goals.component';
import { UserStatisticsComponent } from './user-statistics/user-statistics.component';
import { PersonalGoalComponent } from './my-goals/personal-goal/personal-goal.component';
import { ActivityProgressComponent } from './my-goals/activity-progress/activity-progress.component';

const routes: Routes = [
  {
    path: '',
    component: VisibleGoalsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'global.menu.visibleGoals.title',
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'my-goals',
    component: MyGoalsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'global.menu.myGoals.title',
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'my-goals/personal-goal/new',
    component: PersonalGoalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'global.menu.personalGoal.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'my-goals/activity-progress/track',
    component: ActivityProgressComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'global.menu.activityProgress.title',
    },
    canActivate: [UserRouteAccessService],
  },
  { path: 'statistics', loadChildren: () => import('./user-statistics/user-statistics.module').then(m => m.UserStatisticsModule) },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
