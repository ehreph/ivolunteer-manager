import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ActivityProgress, IActivityProgress } from '../../../shared/model/activity-progress.model';

type EntityResponseType = HttpResponse<IActivityProgress>;
type EntityArrayResponseType = HttpResponse<IActivityProgress[]>;

@Injectable({ providedIn: 'root' })
export class ActivityProgressService {
  public resourceUrl = SERVER_API_URL + 'api/activity/progress';

  constructor(protected http: HttpClient) {}

  create(activityProgress: ActivityProgress): Observable<EntityResponseType> {
    return this.http.post<IActivityProgress>(this.resourceUrl, activityProgress, { observe: 'response' });
  }

  update(activityProgress: ActivityProgress): Observable<EntityResponseType> {
    return this.http.put<IActivityProgress>(this.resourceUrl, activityProgress, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActivityProgress[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
