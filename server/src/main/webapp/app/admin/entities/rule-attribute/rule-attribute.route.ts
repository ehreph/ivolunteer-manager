import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRuleAttribute, RuleAttribute } from 'app/shared/model/rule-attribute.model';
import { RuleAttributeDetailComponent } from './rule-attribute-detail.component';
import { RuleAttributeUpdateComponent } from './rule-attribute-update.component';
import { RuleAttributeService } from '../../../shared/services/rule-attribute.service';

@Injectable({ providedIn: 'root' })
export class RuleAttributeResolve implements Resolve<IRuleAttribute> {
  constructor(private service: RuleAttributeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRuleAttribute> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ruleAttribute: HttpResponse<RuleAttribute>) => {
          if (ruleAttribute.body) {
            return of(ruleAttribute.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RuleAttribute());
  }
}

export const ruleAttributeRoute: Routes = [
  // {
  //   path: '',
  //   component: RuleAttributeComponent,
  //   data: {
  //     authorities: [Authority.USER],
  //     defaultSort: 'id,asc',
  //     pageTitle: 'ivolunteerManagerApp.ruleAttribute.home.title',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
  {
    path: ':id/view',
    component: RuleAttributeDetailComponent,
    resolve: {
      ruleAttribute: RuleAttributeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ivolunteerManagerApp.ruleAttribute.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RuleAttributeUpdateComponent,
    resolve: {
      ruleAttribute: RuleAttributeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ivolunteerManagerApp.ruleAttribute.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RuleAttributeUpdateComponent,
    resolve: {
      ruleAttribute: RuleAttributeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ivolunteerManagerApp.ruleAttribute.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
