import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBadge, Badge } from 'app/shared/model/badge.model';
import { BadgeComponent } from './badge.component';
import { BadgeDetailComponent } from './badge-detail.component';
import { BadgeUpdateComponent } from './badge-update.component';
import { BadgeService } from '../../../shared/services/badge.service';

@Injectable({ providedIn: 'root' })
export class BadgeResolve implements Resolve<IBadge> {
  constructor(private service: BadgeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBadge> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((badge: HttpResponse<Badge>) => {
          if (badge.body) {
            return of(badge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Badge());
  }
}

export const badgeRoute: Routes = [
  {
    path: '',
    component: BadgeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ivolunteerManagerApp.badge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BadgeDetailComponent,
    resolve: {
      badge: BadgeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ivolunteerManagerApp.badge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BadgeUpdateComponent,
    resolve: {
      badge: BadgeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ivolunteerManagerApp.badge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BadgeUpdateComponent,
    resolve: {
      badge: BadgeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ivolunteerManagerApp.badge.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
