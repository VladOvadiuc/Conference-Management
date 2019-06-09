import { Component, OnInit } from '@angular/core';

import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";
import {User} from "../shared/user.model";
import {UserService} from "../shared/user.service";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  user: User;
  error: Error;
  id: number;
  subscriptions: Subscription[] = [];

  constructor(
    private route: ActivatedRoute,
    private userService: UserService
  ) {
    console.log('user-detail: construct');

  }

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    console.log(this.id);
    this.loadUser();
  }

  ngOnDestroy() {
    console.log('user-detail: destroy');
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
  loadUser() {
    this.subscriptions.push(this.userService.getUser(this.id)
      .subscribe(user => {
        this.user = user;

      }, error => {
        console.log(error);
        this.error = error;
      }));
  }

}
