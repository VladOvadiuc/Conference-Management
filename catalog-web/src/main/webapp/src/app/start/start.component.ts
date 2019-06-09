import { Component, OnInit } from '@angular/core';
import {User} from '../users/shared/user.model';
import {PCMember} from '../pc-members/shared/pcmember.model';
import {UserService} from '../users/shared/user.service';
import {MemberService} from '../pc-members/shared/pcmember.service';
import {Router} from '@angular/router';
import {ConferenceService} from '../shared/conference/conference.service';

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {
  title = 'Conference';
  users: User[];
  members: PCMember[];
  nrOfParticipants: number;
  constructor(private userService: UserService,
              private router: Router,
              private conferenceService: ConferenceService) { }

  ngOnInit() {
    this.getUsers();
    this.getConference();

  }


  private getUsers() {
    this.userService.getUsers()
      .subscribe((users) => {
        this.users = users['users'];
        this.nrOfParticipants = this.users.length;
      }, (error) => {
        console.log(error);
      });

  }




  private getConference() {
    this.conferenceService.getConference()
      .subscribe((c) => {
        localStorage.setItem('conference', JSON.stringify(c));
      }, (err) => {console.log(err); });
  }

  showDetails() {
    this.router.navigate(['conference/details'])
  }
}
