import { Component, OnInit } from '@angular/core';
import {RequestOptions} from '@angular/http';
import {Observable, Subscription} from 'rxjs';
import {User} from '../shared/user.model';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../shared/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {

  currentUser: User;
  error: Error;
  id: number;
  subscriptions: Subscription[] = [];

  constructor(
    private route: ActivatedRoute,
    private userService: UserService, private router: Router
  ) {

    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    console.log(this.currentUser.name);
    // const status = localStorage.getItem('userStatus');
    // if (status === 'speaker') {
    //   this.router.navigate(['speakers/home']);
    // } else if (status === 'author') {
    //   this.router.navigate(['authors/home']);
    // } else if (status === 'listener') {
    //   this.router.navigate(['listeners/home']);
    // } else if (status !== 'user') {
    //   this.router.navigate(['conference']);
    // }

  }

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    console.log(this.id);

  }





  gotoDetail(): void {
    this.router.navigate(['users/user-details/', this.currentUser.id]);
  }

  payRegistration(): void {
    console.log('pay registration clicked');
    this.userService.becomeListener(this.currentUser).subscribe(
      (us) => {
        console.log(us['user']);
        localStorage.setItem('userStatus', 'listener');
        this.router.navigate(['listeners/home']);

      }, () => {}
    );



  }

  fileChange($event: Event) {

  }

  logout() {
    this.userService.logout();
    this.router.navigate(['conference']);
  }
}
