import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserGoals } from 'app/shared/model/user-goals.model';
import { IUserCompetence } from '../../shared/model/user-competence.model';

type EntityResponseType = HttpResponse<IUserGoals>;
type EntityArrayResponseType = HttpResponse<IUserGoals[]>;

@Injectable({ providedIn: 'root' })
export class UserGoalsService {
  public resourceUrl = SERVER_API_URL + 'api/user-goals';

  constructor(protected http: HttpClient) {}

  create(userGoals: IUserGoals): Observable<EntityResponseType> {
    return this.http.post<IUserGoals>(this.resourceUrl, userGoals, { observe: 'response' });
  }

  update(userGoals: IUserGoals): Observable<EntityResponseType> {
    return this.http.put<IUserGoals>(this.resourceUrl, userGoals, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserGoals>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserGoals[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryByUserId(userId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserCompetence[]>(`${this.resourceUrl}/user/${userId}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  subscribeUserToGoal(goalId: number): Observable<EntityResponseType> {
    return this.http.post<IUserGoals>(`${this.resourceUrl}/subscribe/goal/${goalId}`, null, { observe: 'response' });
  }

  finishUserGoal(userGoalId: number): Observable<EntityResponseType> {
    return this.http.post<IUserGoals>(`${this.resourceUrl}/${userGoalId}/finish`, null, { observe: 'response' });
  }
}
