import { Component, OnInit } from '@angular/core';

import {Router} from "@angular/router";
import {User} from "../shared/user.model";
import {UserService} from "../shared/user.service";


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  errorMessage: string;
  users: Array<User>;
  selectedUser: User;
  constructor(private userService : UserService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers() {
    this.userService.getUsers()
      .subscribe(
        users => { this.users=users['users'];},
        error => this.errorMessage = <any>error
      );
  }

  onSelect(user: User): void {
    this.selectedUser = user;
  }

  getUpdatedValues($event: boolean) {
    console.log("List of members affected!");
    this.getUsers();
  }

}
