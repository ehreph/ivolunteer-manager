import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGoal } from 'app/shared/model/goal.model';

type EntityResponseType = HttpResponse<IGoal>;
type EntityArrayResponseType = HttpResponse<IGoal[]>;

@Injectable({ providedIn: 'root' })
export class GoalService {
  public resourceUrl = SERVER_API_URL + 'api/goals';

  constructor(protected http: HttpClient) {}

  create(goal: IGoal): Observable<EntityResponseType> {
    return this.http.post<IGoal>(this.resourceUrl, goal, { observe: 'response' });
  }

  update(goal: IGoal): Observable<EntityResponseType> {
    console.debug(goal);
    return this.http.put<IGoal>(this.resourceUrl, goal, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGoal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGoal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findNewGoalsForUser(userId: number): Observable<EntityArrayResponseType> {
    return this.http.get<IGoal[]>(`${this.resourceUrl}/user/${userId}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
