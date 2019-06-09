import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Paper} from './paper-model';

@Injectable()
export class PaperService {
  private papersUrl = 'http://localhost:8080/api/papers';
  constructor(private httpClient: HttpClient) {
  }

  getPaperByAuthorId(id: number): Observable<Paper> {
    return this.httpClient.get<Paper>(this.papersUrl + '/byAuthorId/' + id);
  }

  addPaper(title: string, filePath: string, authorId: number): Observable<Paper> {
    const p = {title, filePath, authorId};
    return this.httpClient.post<Paper>(this.papersUrl, p);
  }

  updatePaper(paper: Paper): Observable<Paper> {
    return this.httpClient.put<Paper>(this.papersUrl + '/' + paper.id, paper);
  }

  getAll(): Observable<Paper[]> {
    return this.httpClient.get<Paper[]>(this.papersUrl);
  }
}
