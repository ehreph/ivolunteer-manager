import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IGoal } from 'app/shared/model/goal.model';

type EntityResponseType = HttpResponse<IGoal>;

@Injectable({ providedIn: 'root' })
export class PersonalGoalService {
  public resourceUrl = SERVER_API_URL + 'api/personal-goals';

  constructor(protected http: HttpClient) {}

  create(goal: IGoal): Observable<EntityResponseType> {
    return this.http.post<IGoal>(this.resourceUrl, goal, { observe: 'response' });
  }

  update(goal: IGoal): Observable<EntityResponseType> {
    return this.http.put<IGoal>(this.resourceUrl, goal, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGoal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
