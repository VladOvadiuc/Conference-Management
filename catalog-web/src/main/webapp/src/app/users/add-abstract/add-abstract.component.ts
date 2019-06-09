import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../shared/user.service';
import {User} from '../shared/user.model';
import {AbstractService} from '../../shared/abstract/abstract.service';




@Component({
  selector: 'app-add-abstract',
  templateUrl: './add-abstract.component.html',
  styleUrls: ['./add-abstract.component.css']
})
export class AddAbstractComponent implements OnInit {
 currentUser: User;
 savedAuthor: User;
 abstractPath: string;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService, private router: Router,
    private abstractService: AbstractService
  ) {

    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    console.log(this.currentUser.name);

  }

  ngOnInit() {
  }

  add(proposalName: string, keywords: string, topics: string, authors: string ) {
    console.log('add abstract clicked');

    this.abstractService.addAbstract(this.abstractPath, proposalName, keywords, topics,
          authors, this.currentUser.id, 'waiting for review')
          .subscribe((abs) => {
            console.log('Abstract added');
            console.log(abs.authorId);
            localStorage.setItem('userStatus', 'author');
            this.router.navigate(['authors/home']);
            // this.abstractService.getAbstractById(abs.id)
            //   .subscribe((abst) => {
            //   }, (error) => {console.log(error); } );
          }, (err) => {console.log(err); });


  }


  abstractChange($fileInput: Event) {
    const file = (<HTMLInputElement>$fileInput.target).files[0];
    this.abstractPath = file.name;
    console.log(this.abstractPath);

  }


}
