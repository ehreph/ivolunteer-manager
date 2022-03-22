import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserCompetence } from 'app/shared/model/user-competence.model';

type EntityResponseType = HttpResponse<IUserCompetence>;
type EntityArrayResponseType = HttpResponse<IUserCompetence[]>;

@Injectable({ providedIn: 'root' })
export class UserCompetenceService {
  public resourceUrl = SERVER_API_URL + 'api/user-competences';

  constructor(protected http: HttpClient) {}

  create(userCompetence: IUserCompetence): Observable<EntityResponseType> {
    return this.http.post<IUserCompetence>(this.resourceUrl, userCompetence, { observe: 'response' });
  }

  update(userCompetence: IUserCompetence): Observable<EntityResponseType> {
    return this.http.put<IUserCompetence>(this.resourceUrl, userCompetence, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserCompetence>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserCompetence[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryByUserId(userId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserCompetence[]>(`${this.resourceUrl}/user/${userId}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
