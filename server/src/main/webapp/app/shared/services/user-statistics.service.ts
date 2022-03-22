import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from '../../app.constants';
import { IUserStatistics } from '../model/statistics.model';

@Injectable({
  providedIn: 'root',
})
export class UserStatisticsService {
  public resourceUrl = SERVER_API_URL + 'api/statistics';

  constructor(protected http: HttpClient) {}

  getUserStatistics(): Observable<HttpResponse<IUserStatistics>> {
    return this.http.get<IUserStatistics>(this.resourceUrl, { observe: 'response' });
  }

  getOtherUserStatistics(compareUserId: number): Observable<HttpResponse<IUserStatistics>> {
    return this.http.get<IUserStatistics>(`${this.resourceUrl}/${compareUserId}`, { observe: 'response' });
  }
}
