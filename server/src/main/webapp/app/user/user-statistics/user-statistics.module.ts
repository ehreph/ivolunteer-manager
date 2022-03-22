import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserStatisticsRoutingModule } from './user-statistics-routing.module';
import { UserStatisticsComponent } from './user-statistics.component';
import { UserStatisticChartsComponent } from './user-statistic-charts/user-statistic-charts.component';
import { UserCompetenceChartComponent } from './user-competence-chart/user-competence-chart.component';
import { RecentActivitiesComponent } from './recent-activities/recent-activities.component';
import { InfoItemComponent } from './info-item/info-item.component';
import { BasicRadarChartComponent } from './basic-radar-chart/basic-radar-chart.component';
import { BasicBarChartComponent } from './basic-bar-chart/basic-bar-chart.component';
import { ChartsModule } from 'ng2-charts';
import { IvolunteerManagerSharedModule } from '../../shared/shared.module';
import { UserRoutingModule } from '../user-routing.module';
import { NgJhipsterModule } from 'ng-jhipster';
import { NgbCollapseModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { IvolunteerManagerSharedLibsModule } from '../../shared/shared-libs.module';

@NgModule({
  declarations: [
    UserStatisticsComponent,
    UserStatisticChartsComponent,
    UserCompetenceChartComponent,
    RecentActivitiesComponent,
    InfoItemComponent,
    BasicRadarChartComponent,
    BasicBarChartComponent,
  ],
  imports: [
    IvolunteerManagerSharedLibsModule,
    CommonModule,
    UserStatisticsRoutingModule,
    ChartsModule,
    UserRoutingModule,
    NgJhipsterModule,
    NgbPaginationModule,
    FontAwesomeModule,
    IvolunteerManagerSharedModule,
    NgbCollapseModule,
  ],
})
export class UserStatisticsModule {}
