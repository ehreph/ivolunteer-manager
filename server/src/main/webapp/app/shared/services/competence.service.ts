import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompetence } from 'app/shared/model/competence.model';

type EntityResponseType = HttpResponse<ICompetence>;
type EntityArrayResponseType = HttpResponse<ICompetence[]>;

@Injectable({ providedIn: 'root' })
export class CompetenceService {
  public resourceUrl = SERVER_API_URL + 'api/competences';

  constructor(protected http: HttpClient) {}

  create(competence: ICompetence): Observable<EntityResponseType> {
    return this.http.post<ICompetence>(this.resourceUrl, competence, { observe: 'response' });
  }

  update(competence: ICompetence): Observable<EntityResponseType> {
    return this.http.put<ICompetence>(this.resourceUrl, competence, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompetence>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetence[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findNewCompetencesForUser(userId: number): Observable<EntityArrayResponseType> {
    return this.http.get<ICompetence[]>(`${this.resourceUrl}/user/${userId}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
