import { Component, OnInit } from '@angular/core';
import {User} from "../../users/shared/user.model";
import {Subscription} from "rxjs";
import {PCMember} from "../shared/pcmember.model";
import {Paper} from "../../shared/paper/paper-model";
import {Abstract} from "../../shared/abstract/abstract-model";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../users/shared/user.service";
import {MemberService} from "../shared/pcmember.service";
import {Reviewer} from "../shared/reviewer.model";

@Component({
  selector: 'app-home-pcmembers-chair',
  templateUrl: './home-pcmembers-chair.component.html',
  styleUrls: ['./home-pcmembers-chair.component.css']
})
export class HomePcmembersChairComponent implements OnInit {
  users: Array<User>;
  reviewers: Array<Reviewer>;
  errorMessage: string;
  subscriptions: Subscription[] = [];
  members: Array<PCMember>;
  member: PCMember;
  papers: Array<Paper>;
  abstracts: Array<Abstract>;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private memberService: MemberService) {
this.loadReviewers();
this.loadPapers();
    this.member = JSON.parse(localStorage.getItem('currentUser'));

  }


  ngOnInit() {
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

  loadUsers() {
    this.userService.getUsers()
      .subscribe(
        users => {
          this.users = users['users'];
        },
        error => this.errorMessage = <any>error
      );
  }
  loadReviewers() {
    console.log('sunt aici');
    this.memberService.getReviewers()
      .subscribe(
        revi => {
          this.reviewers = revi['reviewers'];
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


  assignPaper() {

    const reviewerId = Number((<HTMLInputElement>document.getElementById('reviewerTest')).value);
    const paperId = Number((<HTMLInputElement>document.getElementById('paperTest')).value);

    this.memberService.assignPaper(paperId, reviewerId).subscribe((pr) => console.log('assigned paper'));

  }

  acceptPapers() {
    this.memberService.acceptPapers().subscribe((r) => console.log("accepted papers done"));
  }

}
