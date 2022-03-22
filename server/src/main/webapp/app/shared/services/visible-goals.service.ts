import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from '../../app.constants';
import { IVisibleGoal } from '../model/goal.model';
import { createRequestOption } from '../util/request-util';

@Injectable({
  providedIn: 'root',
})
export class VisibleGoalsService {
  public resourceUrl = SERVER_API_URL + 'api/goals/visible';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<HttpResponse<IVisibleGoal[]>> {
    const options = createRequestOption(req);
    return this.http.get<IVisibleGoal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
