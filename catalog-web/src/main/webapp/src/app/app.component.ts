import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from './users/shared/user.service';
import {MemberService} from './pc-members/shared/pcmember.service';
import {PCMember} from './pc-members/shared/pcmember.model';
import {User} from './users/shared/user.model';
import {ConferenceService} from './shared/conference/conference.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  currentUser: User;
  constructor(
    private route: ActivatedRoute,
    private userService: UserService, private router: Router,
    private conferenceService: ConferenceService
  ) {

    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));

  }


  ngOnInit() {
    this.conferenceService.getConference()
      .subscribe((response) => {
        console.log('Got conference!');
      }, () => {});
  }

}
