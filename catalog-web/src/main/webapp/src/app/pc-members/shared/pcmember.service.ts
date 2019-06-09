import {Injectable} from '@angular/core';

import {HttpClient} from '@angular/common/http';

import {Observable} from 'rxjs';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {PCMember} from './pcmember.model';
import {Paper} from '../../shared/paper/paper-model';
import {Abstract} from '../../shared/abstract/abstract-model';
import {ReviewerAbstract} from "../../shared/reviewerAbstract/reviewerabstract.model";
import {Reviewer} from "./reviewer.model";
import {ReviewerPaper} from "../../shared/reviewerPaper/reviewerpaper.model";

@Injectable()
export class MemberService {
  private membersUrl = 'http://localhost:8080/api/members';
  private papersUrl = 'http://localhost:8080/api/papers';
  private abstractsUrl = 'http://localhost:8080/api/abstracts';
  private bidUrl = 'http://localhost:8080/api/bid';
  private reviewUrl = 'http://localhost:8080/api/review';
  private assignUrl = 'http://localhost:8080/api/assign';
  private reviewersUrl = 'http://localhost:8080/api/reviewers';
  members: PCMember[];

  constructor(private httpClient: HttpClient) {
  }
  getMembers(): Observable<PCMember[]> {
    return this.httpClient
      .get<Array<PCMember>>(this.membersUrl);

  }

  getPapers(): Observable<Paper[]> {
    return this.httpClient
      .get<Array<Paper>>(this.papersUrl);

  }

  getPapersByReviewerId(id: number): Observable<Paper[]> {
    return this.httpClient.get<Array<Paper>>(this.papersUrl + '/' + id);
  }

  getAbstracts(): Observable<Abstract[]> {
    return this.httpClient
      .get<Array<Abstract>>(this.abstractsUrl);

  }

  getReviewers(): Observable<Reviewer[]> {
    return this.httpClient
      .get<Array<Reviewer>>(this.reviewersUrl);

  }

  getAll() {
    return this.httpClient.get<PCMember[]>(this.membersUrl);
  }

  getMember(id: number): Observable<PCMember> {
    const url = `${this.membersUrl}/${id}`;
    return this.httpClient.get<PCMember>(url);
  }
  signUp(username: string, password: string, name: string, affiliation: string, email: string,
         webpage: string, status: string): Observable<Response> {
    const member = {username, password, name, affiliation, email, webpage, status};
    console.log(member.status);
    return this.httpClient
      .post<Response>(this.membersUrl + '/' + status, member);
  }

  login(username: string, password: string): Observable<PCMember> {
    return this.httpClient
      .get<PCMember>(this.membersUrl + '/' + username + '/' + password);
  }


  review(paper: Paper, reviewer: PCMember, status: string): Observable<Response> {
    console.log('aici e', paper.title);
    console.log('aici e user', reviewer.id);
    console.log('aici e result', status);
    console.log('face bid');
    const paperId = paper.id;
    const revId = reviewer.id;
    const review = {paperId, revId, status};
    console.log(review);
    return this.httpClient
      .post<Response>(this.reviewUrl, review);
  }

  assignPaper(paperId: number, reviewerId: number): Observable<ReviewerPaper> {
    console.log('Assign paper');
    const review = {reviewerId, paperId};
    return this.httpClient.post<ReviewerPaper>(this.assignUrl, review);
  }

  getReviewerByUsername(username: string): Observable<Response> {
    return this.httpClient.get<Response>(this.reviewersUrl + 'get/' + username);
  }

  acceptPapers(): Observable<Response> {
    return this.httpClient.get<Response>(this.papersUrl + '/accept');
  }
}
