import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ReviewerPaper} from './reviewerpaper.model';

@Injectable()
export class ReviewerPaperService {
  private reviewUrl = 'http://localhost:8080/api/review';
  constructor(private httpClient: HttpClient) {
  }
  review(paperId: number, reviewerId: number, result: string): Observable<ReviewerPaper> {
    console.log('aici e', paperId);
    console.log('aici e user', reviewerId);
    console.log('aici e result', result);
    console.log('face bid');
    const review = {reviewerId, paperId, result};
    console.log(review);
    console.log(this.reviewUrl);
    return this.httpClient
      .post<ReviewerPaper>(this.reviewUrl, review);
  }
}
