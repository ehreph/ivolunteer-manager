import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInstitution, Institution } from 'app/shared/model/institution.model';
import { InstitutionComponent } from './institution.component';
import { InstitutionDetailComponent } from './institution-detail.component';
import { InstitutionUpdateComponent } from './institution-update.component';
import { InstitutionService } from '../../../shared/services/institution.service';

@Injectable({ providedIn: 'root' })
export class InstitutionResolve implements Resolve<IInstitution> {
  constructor(private service: InstitutionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInstitution> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((institution: HttpResponse<Institution>) => {
          if (institution.body) {
            return of(institution.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Institution());
  }
}

export const institutionRoute: Routes = [
  {
    path: '',
    component: InstitutionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ivolunteerManagerApp.institution.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InstitutionDetailComponent,
    resolve: {
      institution: InstitutionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ivolunteerManagerApp.institution.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InstitutionUpdateComponent,
    resolve: {
      institution: InstitutionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ivolunteerManagerApp.institution.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InstitutionUpdateComponent,
    resolve: {
      institution: InstitutionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ivolunteerManagerApp.institution.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
