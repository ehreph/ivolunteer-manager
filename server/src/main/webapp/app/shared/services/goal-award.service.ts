import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGoalAward } from 'app/shared/model/goal-award.model';

type EntityResponseType = HttpResponse<IGoalAward>;
type EntityArrayResponseType = HttpResponse<IGoalAward[]>;

@Injectable({ providedIn: 'root' })
export class GoalAwardService {
  public resourceUrl = SERVER_API_URL + 'api/goal-awards';

  constructor(protected http: HttpClient) {}

  create(goalAward: IGoalAward): Observable<EntityResponseType> {
    return this.http.post<IGoalAward>(this.resourceUrl, goalAward, { observe: 'response' });
  }

  update(goalAward: IGoalAward): Observable<EntityResponseType> {
    return this.http.put<IGoalAward>(this.resourceUrl, goalAward, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGoalAward>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGoalAward[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
