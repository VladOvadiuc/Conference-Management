import { Component, OnInit } from '@angular/core';
import {User} from '../../users/shared/user.model';
import {UserService} from '../../users/shared/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-log-in-pc',
  templateUrl: './log-in-pc.component.html',
  styleUrls: ['./log-in-pc.component.css']
})
export class LogInPcComponent implements OnInit {

  resultedUser: User;
  errorMessage: String;
  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit() {
  }

  login(value: string, value2: string) {
    this.userService.login(value,value2)
      .subscribe(u=> {
        if (u['status']==200){
          this.resultedUser=u['context']['entity'];
          console.log(this.resultedUser);
          //this.router.navigate(['pc-members/home-pcmembers', this.resultedUser.id]);

          this.userService.getPcType(value).subscribe(o => {
            if (o['status'] === 200) {
              console.log('succes1');
              const stringResult =  o['context']['entity'];
              console.log(stringResult);
              localStorage.setItem('userStatus', stringResult);
              console.log(stringResult);
              if (stringResult === 'chair') {
                this.router.navigate(['chair/home']);
              } else if (stringResult === 'cochair') {
                this.router.navigate(['chair/home']);
              } else if (stringResult === 'reviewer') {
                this.router.navigate(['reviewer/home']);
              } else {
                localStorage.setItem('userStatus', 'pcmember');
                //      this.router.navigate(['users/home']);
              }

            }});
        }else{
          window.alert(u['context']['entity']);
        }
      },err =>{
        this.errorMessage=err;
      });
  }
}
