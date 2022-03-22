import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'institution',
        loadChildren: () => import('./institution/institution.module').then(m => m.IvolunteerManagerInstitutionModule),
      },
      {
        path: 'badge',
        loadChildren: () => import('./badge/badge.module').then(m => m.IvolunteerManagerBadgeModule),
      },
      {
        path: 'competence',
        loadChildren: () => import('./competence/competence.module').then(m => m.IvolunteerManagerCompetenceModule),
      },
      {
        path: 'activity',
        loadChildren: () => import('./activity/activity.module').then(m => m.IvolunteerManagerActivityModule),
      },
      {
        path: 'goal',
        loadChildren: () => import('./goal/goal.module').then(m => m.IvolunteerManagerGoalModule),
      },
      {
        path: 'rule-attribute',
        loadChildren: () => import('./rule-attribute/rule-attribute.module').then(m => m.IvolunteerManagerRuleAttributeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class IvolunteerManagerEntityModule {}
