import { Component, OnInit } from '@angular/core';
import {User} from '../../users/shared/user.model';
import {UserService} from '../../users/shared/user.service';
import {Router} from '@angular/router';
import {MemberService} from '../shared/pcmember.service';

@Component({
  selector: 'app-sign-up-pc',
  templateUrl: './sign-up-pc.component.html',
  styleUrls: ['./sign-up-pc.component.css']
})
export class SignUpPcComponent implements OnInit {
  resultedMember: User;
  errorMessage: String;
  constructor(private memberService: MemberService,
              private router: Router) {
  }

  ngOnInit() {
  }
  signUp(value: string, value2: string, value3: string, value4: string, value5: string, value6: string) {
    let status: string;
    status = (<HTMLInputElement>document.getElementById('test')).value;
    if (status === 'CoChair')
    {
      status = 'coChair';
    }
    if (status === 'Chair')
    {
      status = 'chair';
    }
    if (status === 'Pc Member')
    {
      status = 'normal';
    }
    this.memberService.signUp(value, value2, value3, value4, value5, value6, status)
      .subscribe(u => {
        if (u['status'] === 200) {
          this.resultedMember = u['context']['entity'];
        } else {
          window.alert(u['context']['entity']);
        }
      }, err => {
        this.errorMessage = err;
      });
  }

}
