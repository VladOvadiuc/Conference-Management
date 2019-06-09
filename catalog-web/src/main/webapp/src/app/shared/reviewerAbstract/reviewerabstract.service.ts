import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ReviewerAbstract} from './reviewerabstract.model';

@Injectable()
export class ReviewerAbstractService{
  private bidUrl = 'http://localhost:8080/api/bid';
  constructor(private httpClient: HttpClient) {
  }
  bid(abstractId: number, reviewerId: number, result: string): Observable<ReviewerAbstract> {
    console.log('aici e', abstractId);
    console.log('aici e user', reviewerId);
    console.log('aici e result', status);
    console.log('face bid');
    const review = {reviewerId, abstractId, result};
    console.log(review);
    console.log(this.bidUrl);
    return this.httpClient
      .post<ReviewerAbstract>(this.bidUrl, review);
  }
}
