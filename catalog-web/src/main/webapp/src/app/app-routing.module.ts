import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {UsersComponent} from './users/users.component';
import {UserDetailsComponent} from './users/user-details/user-details.component';
import {SignUpUserComponent} from './users/sign-up-user/sign-up-user.component';
import {SignUpPcComponent} from './pc-members/sign-up-pc/sign-up-pc.component';
import {LogInPcComponent} from './pc-members/log-in-pc/log-in-pc.component';
import {UserListComponent} from './users/user-list/user-list.component';
import {LogInUserComponent} from './users/log-in-user/log-in-user.component';
import {HomeComponent} from './users/home/home.component';
import {HomePcmembersComponent} from './pc-members/home-pcmembers/home-pcmembers.component';
import {AddAbstractComponent} from './users/add-abstract/add-abstract.component';
import {StartComponent} from './start/start.component';
import {HomeListenerComponent} from './users/home-listener/home-listener.component';
import {HomeAuthorComponent} from "./users/home-author/home-author.component";
import {ConferenceDetailsComponent} from './conference-details/conference-details.component';
import {HomePcmembersChairComponent} from "./pc-members/home-pcmembers-chair/home-pcmembers-chair.component";
import {HomePcmembersReviewerComponent} from "./pc-members/home-pcmembers-reviewer/home-pcmembers-reviewer.component";
import {HomeSpeakerComponent} from "./users/home-speaker/home-speaker.component";


const routes: Routes = [

  {path: 'members', component: UsersComponent},
  {path: 'users/user-list', component: UserListComponent},
  {path: 'users/user-details', component: UserDetailsComponent},
  {path: 'listener/add-abstract', component: AddAbstractComponent},
  {path: 'signUpUser', component: SignUpUserComponent},
  {path: 'signUpPC', component: SignUpPcComponent},
  {path: 'logInUser', component: LogInUserComponent},
  {path: 'logInPC', component: LogInPcComponent},
  {path: 'users', component: UserListComponent},
  {path: 'users/home', component: HomeComponent},
  {path: 'pc-members/home-pcmembers/:id', component: HomePcmembersComponent},
  {path: 'conference', component: StartComponent},
  {path: 'listeners/home', component: HomeListenerComponent},
  {path: 'authors/home', component: HomeAuthorComponent},
  {path: 'conference/details', component: ConferenceDetailsComponent},
  {path: 'chair/home', component: HomePcmembersChairComponent},
  {path: 'reviewer/home', component: HomePcmembersComponent},
  {path: 'speakers/home', component: HomeSpeakerComponent}



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
