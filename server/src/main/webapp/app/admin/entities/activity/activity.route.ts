import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ActivityComponent } from './activity.component';
import { ActivityDetailComponent } from './activity-detail.component';
import { ActivityUpdateComponent } from './activity-update.component';
import { Activity, IActivity } from '../../../shared/model/activity.model';
import { ActivityService } from '../../../shared/services/activity.service';

@Injectable({ providedIn: 'root' })
export class ActivityResolve implements Resolve<IActivity> {
  constructor(private service: ActivityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActivity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((activity: HttpResponse<Activity>) => {
          if (activity.body) {
            return of(activity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Activity());
  }
}

export const activityRoute: Routes = [
  {
    path: '',
    component: ActivityComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'ivolunteerManagerApp.activity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ActivityDetailComponent,
    resolve: {
      activity: ActivityResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'ivolunteerManagerApp.activity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ActivityUpdateComponent,
    resolve: {
      activity: ActivityResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'ivolunteerManagerApp.activity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ActivityUpdateComponent,
    resolve: {
      activity: ActivityResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'ivolunteerManagerApp.activity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
