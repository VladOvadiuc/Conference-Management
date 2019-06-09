import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Session} from './session-model';
import {PCMember} from '../../pc-members/shared/pcmember.model';
import {User} from '../../users/shared/user.model';

@Injectable()
export class SessionService {
  private sessionsUrl = 'http://localhost:8080/api/users';
  constructor(private httpClient: HttpClient) {
  }
  getAllSessions(): Observable<Session[]> {
    return this.httpClient.get<Session[]>(this.sessionsUrl);
  }

  getSession(id: number): Observable<Session> {
    return this.httpClient.get<Session>(this.sessionsUrl + '/' + id);
  }

  // tslint:disable-next-line:max-line-length
  addSession(title: string, location: string, dateTime: string, supervisorId: number, participantrNr: number, maxNr: number): Observable<Session> {
    const s = {location, dateTime, supervisorId, participantrNr, maxNr};
    return this.httpClient.post<Session>(this.sessionsUrl, s);
  }

  addSupervisor(supervisor: PCMember, sessionId: number): Observable<Response> {
    return this.httpClient.put<Response>(this.httpClient + '/supervisor', supervisor);
  }
  addSpeaker(speaker: PCMember, sessionId: number): Observable<Response> {
    return this.httpClient.put<Response>(this.httpClient + '/speaker', speaker);
  }
  addListener(listener: User, sessionId: number): Observable<Response> {
    return this.httpClient.put<Response>(this.httpClient + '/listener', listener);
  }

  getSpeakers(sessionId: number): Observable<User> {
    return this.httpClient.get<User>(this.sessionsUrl + '/speakers/' + sessionId);
  }
  getListeners(sessionId: number): Observable<User> {
    return this.httpClient.get<User>(this.sessionsUrl + '/listeners/' + sessionId);
  }

  getSessionsByListener(listenerId: number): Observable<Session> {
    return this.httpClient.get<Session>(this.sessionsUrl + '/byListenerId/' + listenerId);
  }
}
