import { Component, OnInit } from '@angular/core';
import {User} from '../shared/user.model';
import {UserService} from '../shared/user.service';
import {Router} from '@angular/router';
import {equal} from 'assert';

@Component({
  selector: 'app-log-in-user',
  templateUrl: './log-in-user.component.html',
  styleUrls: ['./log-in-user.component.css']
})
export class LogInUserComponent implements OnInit {

  resultedUser: User;
  errorMessage: String;
  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit() {
  }

  login(value: string, value2: string) {
    console.log('login');
    this.userService.login(value, value2)
      .subscribe(u => {
        if (u['status'] === 200) {
          this.resultedUser = u['context']['entity'];
          console.log(this.resultedUser);
          console.log('succes');
          this.router.navigate(['users/home']);


          this.userService.getUserType(value).subscribe(o => {
            if (o['status'] === 200) {
              console.log('succes1');
              const stringResult =  o['context']['entity'];
              console.log(stringResult);
              localStorage.setItem('userStatus', stringResult);
              if (stringResult === 'speaker') {
                this.router.navigate(['speakers/home']);
              } else if (stringResult === 'author') {
                this.router.navigate(['authors/home']);
              } else if (stringResult === 'listener') {
                this.router.navigate(['listeners/home']);
              } else {
                localStorage.setItem('userStatus', 'user');
                this.router.navigate(['users/home']);
              }

            }});

        } else {
          window.alert(u['context']['entity']);
        }
      }, err => {
        this.errorMessage = err;
      });
  }
}
