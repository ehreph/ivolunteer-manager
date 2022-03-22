import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserBadge } from 'app/shared/model/user-badge.model';

type EntityResponseType = HttpResponse<IUserBadge>;
type EntityArrayResponseType = HttpResponse<IUserBadge[]>;

@Injectable({ providedIn: 'root' })
export class UserBadgeService {
  public resourceUrl = SERVER_API_URL + 'api/user-badges';

  constructor(protected http: HttpClient) {}

  create(userBadge: IUserBadge): Observable<EntityResponseType> {
    return this.http.post<IUserBadge>(this.resourceUrl, userBadge, { observe: 'response' });
  }

  update(userBadge: IUserBadge): Observable<EntityResponseType> {
    return this.http.put<IUserBadge>(this.resourceUrl, userBadge, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserBadge>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserBadge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  queryByUserId(userId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserBadge[]>(`${this.resourceUrl}/user/${userId}`, { params: options, observe: 'response' });
  }
}
