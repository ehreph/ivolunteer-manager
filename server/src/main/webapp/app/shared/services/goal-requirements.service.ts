import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGoalRequirements } from 'app/shared/model/goal-requirements.model';

type EntityResponseType = HttpResponse<IGoalRequirements>;
type EntityArrayResponseType = HttpResponse<IGoalRequirements[]>;

@Injectable({ providedIn: 'root' })
export class GoalRequirementsService {
  public resourceUrl = SERVER_API_URL + 'api/goal-requirements';

  constructor(protected http: HttpClient) {}

  create(goalRequirements: IGoalRequirements): Observable<EntityResponseType> {
    return this.http.post<IGoalRequirements>(this.resourceUrl, goalRequirements, { observe: 'response' });
  }

  update(goalRequirements: IGoalRequirements): Observable<EntityResponseType> {
    return this.http.put<IGoalRequirements>(this.resourceUrl, goalRequirements, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGoalRequirements>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGoalRequirements[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
