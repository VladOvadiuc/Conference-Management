import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import { UsersComponent } from './users/users.component';
import { UserDetailsComponent } from './users/user-details/user-details.component';
import { UserListComponent } from './users/user-list/user-list.component';
import {UserService} from './users/shared/user.service';
import { SignUpUserComponent } from './users/sign-up-user/sign-up-user.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { LoginComponent } from './login/login.component';
import { PcMembersComponent } from './pc-members/pc-members.component';
import { SignUpPcComponent } from './pc-members/sign-up-pc/sign-up-pc.component';
import { LogInPcComponent } from './pc-members/log-in-pc/log-in-pc.component';
import {LogInUserComponent} from './users/log-in-user/log-in-user.component';
import {MemberService} from './pc-members/shared/pcmember.service';
import { HomeComponent } from './users/home/home.component';
import { HomePcmembersComponent } from './pc-members/home-pcmembers/home-pcmembers.component';
import { AddAbstractComponent } from './users/add-abstract/add-abstract.component';
import { StartComponent } from './start/start.component';
import {HomeListenerComponent} from './users/home-listener/home-listener.component';
import {SessionService} from './shared/session/session.service';
import { HomeAuthorComponent } from './users/home-author/home-author.component';
import {AbstractService} from './shared/abstract/abstract.service';
import {PaperService} from './shared/paper/paper.service';
import {ConferenceService} from './shared/conference/conference.service';
import {PresentationService} from './shared/presentation/presentation.service';
import { HomeSpeakerComponent } from './users/home-speaker/home-speaker.component';
import { ConferenceDetailsComponent } from './conference-details/conference-details.component';
import {HomePcmembersReviewerComponent} from './pc-members/home-pcmembers-reviewer/home-pcmembers-reviewer.component';
import {ReviewerAbstractService} from './shared/reviewerAbstract/reviewerabstract.service';
import {ReviewerPaperService} from "./shared/reviewerPaper/reviewerpaper.service";
import {HomePcmembersChairComponent} from "./pc-members/home-pcmembers-chair/home-pcmembers-chair.component";

@NgModule({
  declarations: [
    AppComponent,
    UsersComponent,
    UserDetailsComponent,
    UserListComponent,
    SignUpUserComponent,
    SignUpComponent,
    LoginComponent,
    PcMembersComponent,
    SignUpPcComponent,
    LogInPcComponent,
    LogInUserComponent,
    HomeComponent,
    HomePcmembersComponent,
    AddAbstractComponent,
    StartComponent,
    HomeListenerComponent,
    HomeAuthorComponent,
    HomeSpeakerComponent,
    ConferenceDetailsComponent,
    HomePcmembersComponent,
    HomePcmembersReviewerComponent,
    HomePcmembersChairComponent

  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
  ],
  providers: [UserService, MemberService, SessionService, AbstractService, PaperService, ConferenceService, PresentationService, ReviewerAbstractService, ReviewerPaperService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
