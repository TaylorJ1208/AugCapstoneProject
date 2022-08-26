import { Injectable } from '@angular/core';
import { User } from 'src/app/Models/user';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  updateUser(user: User): Observable<User> {
    return this.http.put<User>(`http://localhost:8081/user/customer/update`, user);
  }
}
