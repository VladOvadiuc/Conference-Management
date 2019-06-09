import { Component, OnInit } from '@angular/core';
import {UserService} from "../shared/user.service";
import {Router} from "@angular/router";
import {User} from "../shared/user.model";

@Component({
  selector: 'app-sign-up-user',
  templateUrl: './sign-up-user.component.html',
  styleUrls: ['./sign-up-user.component.css']
})
export class SignUpUserComponent implements OnInit {
  resultedUser: User;
  errorMessage: String;
  constructor(private userService : UserService,
              private router: Router) {
  }

  ngOnInit() {
  }

  signUp(value: string, value2: string, value3: string, value4: string, value5: string) {
    this.userService.signUp(value,value2,value3,value4,value5)
      .subscribe(u=> {
        if (u['status']==200){
          this.resultedUser=u['context']['entity'];
        }else{
          window.alert(u['context']['entity']);
        }
      },err=>{
        this.errorMessage=err;
      });
  }
}
