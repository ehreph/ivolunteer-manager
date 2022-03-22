import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserStatisticsComponent } from './user-statistics.component';
import { ChartsModule } from 'ng2-charts';

const routes: Routes = [{ path: '', component: UserStatisticsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes), ChartsModule],
  exports: [RouterModule],
})
export class UserStatisticsRoutingModule {}
