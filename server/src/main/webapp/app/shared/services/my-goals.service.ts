import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMyGoal } from 'app/shared/model/user-goals.model';

type MyGoalArrayResponseType = HttpResponse<IMyGoal[]>;

@Injectable({ providedIn: 'root' })
export class MyGoalsService {
  public resourceUrl = SERVER_API_URL + 'api/my-goals';

  constructor(protected http: HttpClient) {}

  queryCurrentUserGoals(req?: any): Observable<MyGoalArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMyGoal[]>(`${this.resourceUrl}`, { params: options, observe: 'response' });
  }
}
