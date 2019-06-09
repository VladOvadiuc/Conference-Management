import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Presentation} from './presentation-model';

@Injectable()
export class PresentationService {
  private presentationsUrl = 'http://localhost:8080/api/presentations';
  constructor(private httpClient: HttpClient) {
  }
  getAll(): Observable<Presentation[]> {
    return this.httpClient.get<Presentation[]>(this.presentationsUrl);
  }

  addPresentation(filepath: string, spId: number): Observable<Presentation> {
    const p = {filepath, spId};
    return this.httpClient.post<Presentation>(this.presentationsUrl, p);
  }

}
