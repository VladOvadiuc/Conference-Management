import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Abstract} from './abstract-model';


@Injectable()
export class AbstractService {
  private abstractUrl = 'http://localhost:8080/api/abstracts';
  constructor(private httpClient: HttpClient) {
  }

  getAll(): Observable<Abstract> {
    return this.httpClient.get<Abstract>(this.abstractUrl);
  }

  getAbstractByAuthorId(id: number): Observable<Abstract> {
    return this.httpClient.get<Abstract>(this.abstractUrl + '/byAuthorId/' + id);
  }
  addAbstract(abstractPath: string,
  title: string,
  keywords: string,
  topics: string,
  authorNames: string,
  authorId: number, status: string): Observable<Abstract> {
    const a = {abstractPath, title, keywords, topics, authorNames, authorId, status};
    return this.httpClient.post<Abstract>(this.abstractUrl, a);
  }

  updateAbstract(abstract: Abstract): Observable<Abstract> {
    return this.httpClient.put<Abstract>(this.abstractUrl + '/' + abstract.id, abstract);
  }

  getAbstractById(id: number): Observable<Abstract> {
    return this.httpClient.get<Abstract>(this.abstractUrl);
  }
}
