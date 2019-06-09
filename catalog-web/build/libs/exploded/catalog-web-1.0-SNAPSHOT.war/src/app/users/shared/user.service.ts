import {Injectable} from '@angular/core';

import {HttpClient} from '@angular/common/http';

import {Observable} from 'rxjs';
import {_throw} from 'rxjs/observable/throw';
import {User} from './user.model';
import {retry, catchError, map} from 'rxjs/operators';
import {Abstract} from '../../shared/abstract/abstract-model';


@Injectable()
export class UserService {
  private usersUrl = 'http://localhost:8080/api/users';
  private listenersUrl = 'http://localhost:8080/api/listeners';
  private authorsUrl = 'http://localhost:8080/api/authors';


  constructor(private httpClient: HttpClient) {
  }


  getUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(this.usersUrl);

  }

  getUser(id: number): Observable<User> {
    const url = `${this.usersUrl}/${id}`;
    return this.httpClient.get<User>(url);
  }


  update(user): Observable<User> {
    const url = `${this.usersUrl}/${user.id}`;
    return this.httpClient
      .put<User>(url, user);
  }

  signUp(username: string, password: string, name: string, affiliation: string, email: string): Observable<Response> {
    const user = {username, password, name, affiliation, email};
    console.log(user);
    return this.httpClient
      .post<Response>(this.usersUrl, user);

  }

  login(username: string, password: string): Observable<Response> {
    console.log('do this');
    return this.httpClient
      .get<Response>(this.usersUrl + '/' + username + '/' + password)
      .pipe(map(user => {
        // login successful if there's a jwt token in the response
       const resultedUser = user['context']['entity'];
        if (resultedUser ) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(resultedUser));
        }

        return user;
      }));
  }

  getUserType(username: string): Observable<string> {
    return this.httpClient.get<string>('http://localhost:8080/api/userStatus/' + username);
  }

  save( name: string, email: string, affiliation: string, username: string,  password: string) {
    const user = {name, email, affiliation, username, password};
    return this.httpClient
      .post<User>(this.usersUrl, user);
  }
  delete(id: number): Observable<User> {
    return this.httpClient.delete<User>(this.usersUrl + '/' + id);
  }

  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return _throw(errorMessage);
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }

  becomeListener(myUser: User): Observable<User> {
    // add the current user to the table of listeners
    return this.httpClient.post<User>(this.listenersUrl, myUser);
  }

  becomeAuthor(myListener: User): Observable<User> {
    return this.httpClient.put<User>(this.authorsUrl, myListener);

  }

  addProposal(filepath: string, proposalName: string, keywords: string, topics: string, authors: string, authorId: number): Observable<Abstract> {
    const a = {filepath, proposalName, keywords, topics, authors, authorId};
    return this.httpClient.put<Abstract>(this.authorsUrl, a);
  }

  getAuthorByUsername(username: string): Observable<Response> {
    return this.httpClient.get<Response>(this.authorsUrl + 'get/' + username);
  }

  getPcType(username: string): Observable<string> {
    return this.httpClient.get<string>('http://localhost:8080/api/pcMemberStatus/' + username);
  }

}
