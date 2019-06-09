import { Component, OnInit } from '@angular/core';
import {User} from '../shared/user.model';
import {Session} from '../../shared/session/session-model';
import {Abstract} from '../../shared/abstract/abstract-model';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../shared/user.service';
import {SessionService} from '../../shared/session/session.service';
import {AbstractService} from '../../shared/abstract/abstract.service';
import {PaperService} from "../../shared/paper/paper.service";
import {Paper} from "../../shared/paper/paper-model";

@Component({
  selector: 'app-home-speaker',
  templateUrl: './home-speaker.component.html',
  styleUrls: ['./home-speaker.component.css']
})
export class HomeSpeakerComponent implements OnInit {

  currentUser: User;
  sessions: Session[];
  mySessions: Session[] = [];
  seeMySessionsClicked: boolean;
  myWorkClicked = false;
  seeSessionsClicked = false;
  myPaper: Paper;
  myAbstract: Abstract;
  constructor(
    private route: ActivatedRoute,
    private userService: UserService, private router: Router,
    private sessionService: SessionService,
    private abstractService: AbstractService,
    private paperService: PaperService
  ) {

    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    console.log(this.currentUser.name);

  }

  ngOnInit() {
    this.getAuthor(this.currentUser.username);
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
  }

  showSessions() {
    this.seeSessionsClicked = true;
    this.seeMySessionsClicked = false;
    this.myWorkClicked = false;
    // mocked sessions
    const listeners: User[] = [];
    listeners.push({id: 100, name: 'Claudiu', username: 'clau', password: 'asdf', email: 'eefdsf', affiliation: 'sfdsf'});
    const speakers: User[] = [];
    speakers.push({id: 100, name: 'Claudiu', username: 'clau', password: 'asdf', email: 'eefdsf', affiliation: 'sfdsf'});
    this.sessions = [];
    const s: Session = {title: 'parc', location: 'here', when: 'Tuesday',
      supervisorId: 12, participantsNr: 43, maxParticipants: 56,
      listeners: listeners, id: 14, speakers: speakers };
    this.sessions.push(s);

    // // real fct call
    // this.sessionService.getAllSessions()
    //   .subscribe((ss) => {
    //     this.sessions = ss;
    //   }, (err) => {console.log(err); });

  }

  chooseSession(session: Session) {
    console.log('user ' + this.currentUser.username + ' wants to go to session ' + session.title );
    this.sessionService.addListener(this.currentUser, session.id)
      .subscribe((response) => {
        if (response.status === 200) {
          console.log('session correctly chosen');
        } else {
          console.log('some error in choosing session');
          const errorMessage = response['context']['entity'];
          window.alert(errorMessage);
        }
      }, (err) => {console.log(err); });
  }

  seeWork() {
    this.seeSessionsClicked = false;
    this.seeMySessionsClicked = false;
    this.myWorkClicked = true;
    this.abstractService.getAbstractByAuthorId(Number(localStorage.getItem('authorId')))
      .subscribe((abs) => {
        this.myAbstract = abs;
        this.paperService.getPaperByAuthorId(Number(localStorage.getItem('authorId')))
          .subscribe((pp) => {
            console.log('get paper');
            console.log(pp);
            this.myPaper = pp;

          }, (err) => { console.log(err); });
      }, (err) => { console.log(err); });
    /*
    this.seeSessionsClicked = false;
    this.seeMySessionsClicked = false;
    this.myWorkClicked = true;
    this.abstractService.getAbstractByAuthorId(this.currentUser.id)
      .subscribe((abs) => {
        this.myAbstract = abs;
      }, (err) => { console.log(err); });*/
  }

  seeSessions() {
    this.seeSessionsClicked = false;
    this.seeMySessionsClicked = true;
    this.myWorkClicked = false;
    this.sessionService.getSessionsByListener(this.currentUser.id)
      .subscribe((sessions) => {
        this.mySessions = sessions['sessions'];
        console.log(this.mySessions);
      }, ( err) => console.log(err));
  }

  private getAuthor(username: string) {
    this.userService.getAuthorByUsername(username)
      .subscribe((response) => {
        localStorage.setItem('authorId', response['context']['entity']);
        console.log(response['context']['entity']);
      }, (err) => {console.log(err); });
  }

}
