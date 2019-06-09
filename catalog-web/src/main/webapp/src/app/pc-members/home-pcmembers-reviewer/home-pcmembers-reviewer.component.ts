import { Component, OnInit } from '@angular/core';
import {User} from '../../users/shared/user.model';
import {Subscription} from 'rxjs';
import {PCMember} from '../shared/pcmember.model';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../users/shared/user.service';
import {MemberService} from '../shared/pcmember.service';
import {Paper} from '../../shared/paper/paper-model';
import {Abstract} from '../../shared/abstract/abstract-model';


@Component({
  selector: 'app-home-pcmembers-reviewer',
  templateUrl: './home-pcmembers-reviewer.component.html',
  styleUrls: ['./home-pcmembers-reviewer.component.css']
})
export class HomePcmembersReviewerComponent implements OnInit {

  errorMessage: string;
  member: PCMember;
  papers: Array<Paper>;
  abstracts: Array<Abstract>;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private memberService: MemberService) {

    this.member = JSON.parse(localStorage.getItem('currentUser'));

  }


  ngOnInit() {
    console.log('reviewer component');
  }


  loadPapers() {
    this.memberService.getPapers()
      .subscribe(
        papers => {
          this.papers = papers['papers'];
        },
        error => this.errorMessage = <any>error
      );
  }

  loadAbstracts() {
    this.memberService.getAbstracts()
      .subscribe(
        abstracts => {
          this.abstracts = abstracts['abstracts'];
        },
        error => this.errorMessage = <any>error
      );
  }

  bid(abstract: Abstract) {
    let status: string;
    status = (<HTMLInputElement>document.getElementById('test1')).value;
    if (status === 'Pleased to review') {
      status = 'please';
    }
    if (status === 'Refuse to review') {
      status = 'refuse';
    }
    if (status === 'Indiferent') {
      status = 'indiferent';
    }
    //this.memberService.bid(abstract, this.member, status);
  }

  review(paper: Paper) {
    let status: string;
    status = (<HTMLInputElement>document.getElementById('test')).value;
    if (status === 'Strong accept') {
      status = 'strongAccept';
    }
    if (status === 'Accept') {
      status = 'accept';
    }
    if (status === 'Weak accept') {
      status = 'weakAccept';
    }
    if (status === 'Borderline paper') {
      status = 'borderline';
    }
    if (status === 'Weak reject') {
      status = 'weakReject';
    }
    if (status === 'Reject') {
      status = 'reject';
    }
    if (status === 'Strong reject') {
      status = 'strongReject';
    }
    this.memberService.review(paper, this.member, status);
  }


}
