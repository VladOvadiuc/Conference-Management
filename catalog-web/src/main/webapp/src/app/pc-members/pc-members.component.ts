import { Component, OnInit } from '@angular/core';
import {User} from "../users/shared/user.model";
import {UserService} from "../users/shared/user.service";
import {first} from "rxjs/operator/first";

@Component({
  selector: 'app-pc-members',
  templateUrl: './pc-members.component.html',
  styleUrls: ['./pc-members.component.css']
})
export class PcMembersComponent implements OnInit {

  users: User[] = [];
  constructor(private userService: UserService) { }

  ngOnInit() {

  }

}
