import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../users/shared/user.service';
import {User} from '../../users/shared/user.model';
import {Subscription} from 'rxjs';
import {first} from 'rxjs/operator/first';
import {MemberService} from '../shared/pcmember.service';
import {PCMember} from '../shared/pcmember.model';
import {Paper} from '../../shared/paper/paper-model';
import {Abstract} from '../../shared/abstract/abstract-model';
import {ReviewerAbstractService} from '../../shared/reviewerAbstract/reviewerabstract.service';
import {ReviewerPaperService} from "../../shared/reviewerPaper/reviewerpaper.service";

@Component({
  selector: 'app-home-pcmembers',
  templateUrl: './home-pcmembers.component.html',
  styleUrls: ['./home-pcmembers.component.css']
})
export class HomePcmembersComponent implements OnInit {

  users: Array<User>;
  errorMessage: string;
  subscriptions: Subscription[] = [];
  members: Array<PCMember>;
  member: PCMember;
  papers: Array<Paper>;
  abstracts: Array<Abstract>;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private memberService: MemberService,
    private reviewerAbstractService: ReviewerAbstractService,
    private reviewerPaperService: ReviewerPaperService) {

    this.member = JSON.parse(localStorage.getItem('currentUser'));

  }


  ngOnInit() {
    console.log('pcmember component');
    this.getReviewer(this.member.username);
  }

  getReviewer(username: string) {
    this.memberService.getReviewerByUsername(username)
      .subscribe((response) => {
        localStorage.setItem('reviewerId', response['context']['entity']);
        console.log(response['context']['entity']);
      }, (err) => {console.log(err); });
  }

  loadPapers() {
    this.memberService.getPapersByReviewerId(Number(localStorage.getItem('reviewerId')))
      .subscribe(
        papers => {
          this.papers = papers['papers'];
        },
        error => this.errorMessage = <any>error
      );
  }

  loadAbstracts() {
    console.log('load abstracts');
    this.memberService.getAbstracts()
      .subscribe(
        abstracts => {
          this.abstracts = abstracts['abstracts'];
        },
        error => this.errorMessage = <any>error
      );
  }

  loadUsers() {
    this.userService.getUsers()
      .subscribe(
        users => {
          this.users = users['users'];
        },
        error => this.errorMessage = <any>error
      );
  }

  loadMembers() {
    this.memberService.getMembers()
      .subscribe(
        members => {
          this.members = members['members'];
        },
        error => this.errorMessage = <any>error
      );
  }


  assignPaper(paperId: number, reviewerId: number) {
      this.memberService.assignPaper(paperId, reviewerId);

  }
  bid(abstract: Abstract) {
    let status: string;
    status = (<HTMLInputElement>document.getElementById('bidResult')).value;
    console.log(status);
    if (status === 'Pleased to review') {
      status = 'please';
    }
    if (status === 'Refuse to review') {
      status = 'refuse';
    }
    if (status === 'Indiferent') {
      status = 'indiferent';
    }
    const result = status;
    this.reviewerAbstractService.bid(abstract.id, Number(localStorage.getItem('reviewerId')), result)
      .subscribe((revAbs) => console.log('Abstract review added'));
  }

  review(paper: Paper) {
    let status: string;
    status = (<HTMLInputElement>document.getElementById('reviewResult')).value;
    if (status === 'Strong accept') {
      status = 'strongaccept';
    }
    if (status === 'Accept') {
      status = 'accept';
    }
    if (status === 'Weak accept') {
      status = 'weakaccept';
    }
    if (status === 'Borderline paper') {
      status = 'borderline';
    }
    if (status === 'Weak reject') {
      status = 'weakreject';
    }
    if (status === 'Reject') {
      status = 'reject';
    }
    if (status === 'Strong reject') {
      status = 'strongreject';
    }
    const result = status;
    this.reviewerPaperService.review(paper.id, Number(localStorage.getItem('reviewerId')), result)
      .subscribe((revP) => console.log('Paper review added'));
  }


}
