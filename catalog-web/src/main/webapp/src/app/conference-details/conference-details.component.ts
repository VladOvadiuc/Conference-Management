import { Component, OnInit } from '@angular/core';
import {Conference} from '../shared/conference/conference-model';
import {MemberService} from '../pc-members/shared/pcmember.service';
import {SessionService} from '../shared/session/session.service';
import {PCMember} from '../pc-members/shared/pcmember.model';
import {Session} from '../shared/session/session-model';

@Component({
  selector: 'app-conference-details',
  templateUrl: './conference-details.component.html',
  styleUrls: ['./conference-details.component.css']
})
export class ConferenceDetailsComponent implements OnInit {
  conference: Conference;
  members: PCMember[];
  sessions: Session[];
  constructor( private memberService: MemberService,
               private sessionService: SessionService) {
    this.conference = JSON.parse(localStorage.getItem('conference'));
    console.log(this.conference);
  }

  ngOnInit() {
    this.getMembers();
  }

  getMembers() {
    this.memberService.getMembers()
      .subscribe((members) => {
        this.members = members['members'];

      }, (error) => {
        console.log(error);
      });

  }
  getSessions() {
    this.sessionService.getAllSessions()
      .subscribe((s) => {
        this.sessions = s;
      }, (err) => {console.log(err)});
  }
}
