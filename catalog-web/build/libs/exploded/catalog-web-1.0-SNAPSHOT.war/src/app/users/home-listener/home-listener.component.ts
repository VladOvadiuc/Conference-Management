import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../shared/user.service';
import {User} from '../shared/user.model';
import {Session} from '../../shared/session/session-model';
import {SessionService} from '../../shared/session/session.service';

@Component({
  selector: 'app-home-listener',
  templateUrl: './home-listener.component.html',
  styleUrls: ['./home-listener.component.css']
})
export class HomeListenerComponent implements OnInit {
  currentUser: User;
  sessions: Session[];
  constructor(
    private route: ActivatedRoute,
    private userService: UserService, private router: Router,
    private sessionService: SessionService
  ) {

    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    console.log(this.currentUser.name);
    // const status = localStorage.get['userStatus'];
    // if (status === 'speaker') {
    //   this.router.navigate(['speakers/home']);
    // } else if (status === 'author') {
    //   this.router.navigate(['authors/home']);
    // } else if (status === 'user') {
    //   this.router.navigate(['users/home']);
    // } if (status !== 'listener') {
    //   this.router.navigate(['conference']);
    // }

  }

  ngOnInit() {
  }
  addAbstract() {
    this.router.navigate(['listener/add-abstract']);
  }

  addPaper() {
    console.log('paper added');
  }

  fileChange($event: Event) {

  }

  logout() {
    this.userService.logout();
    localStorage.removeItem('authorId');
    this.router.navigate(['conference']);
  }

  showSessions() {
    const listeners: User[] = [];
    listeners.push({id: 100, name: 'Claudiu', username: 'clau', password: 'asdf', email: 'eefdsf', affiliation: 'sfdsf'});
    const speakers: User[] = [];
    speakers.push({id: 100, name: 'Claudiu', username: 'clau', password: 'asdf', email: 'eefdsf', affiliation: 'sfdsf'});
    this.sessions = [];
    const s: Session = {title: 'fdgfd', location: 'here', when: 'Tuesday',
      supervisorId: 12, participantsNr: 43, maxParticipants: 56,
      listeners: listeners, id: 14, speakers: speakers };
    this.sessions.push(s);

  }

  chooseSession(id: number) {
    this.sessionService.addListener(this.currentUser, id)
      .subscribe((response) => {
        if (response.status === 400) {
          window.alert(response['context']['entity']);
        }
      }, (err) => {console.log(err); });
  }
}
