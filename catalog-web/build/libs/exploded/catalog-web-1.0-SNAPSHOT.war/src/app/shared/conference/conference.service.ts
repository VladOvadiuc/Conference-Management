import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Conference} from './conference-model';
import {map} from 'rxjs/operators';

@Injectable()
export class ConferenceService {
  private conferenceUrl = 'http://localhost:8080/api/conferences';
  constructor(private httpClient: HttpClient) {
  }
  getConference(): Observable<Conference> {
    return this.httpClient.get<Conference>(this.conferenceUrl)
      .pipe(map(conference => {
        // login successful if there's a jwt token in the response
        return conference;
      }));
  }

  updateConference(conf: Conference): Observable<Conference> {
    return this.httpClient.put<Conference>(this.conferenceUrl + '/' + conf.id, conf);
  }

  getListenersNumber(): Observable<number> {
    return this.httpClient.get<number>(this.conferenceUrl + '/participantsNr');
  }

}
