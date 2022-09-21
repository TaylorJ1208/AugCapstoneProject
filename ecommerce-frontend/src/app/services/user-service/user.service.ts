import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from 'src/app/Models/user';

@Injectable({
    providedIn: 'root'
  })

export class UserService {
    constructor(private http: HttpClient) { }

    getAllUsers(): Observable<[User]> {
        return this.http.get<[User]>("https://e-frontend.azurewebsites.net/user/admin");
      }

    createUser(data: any): Observable<any> {
        return this.http.post("https://e-frontend.azurewebsites.net/user/customer/register", data)
    }
    
    deleteUser(userId: number): Observable<any> {
        return this.http.delete("https://e-frontend.azurewebsites.net/user/customer/delete/" + userId);
    }

    updateUser(data: any): Observable<any> {
        return this.http.put("https://e-frontend.azurewebsites.net/user/customer/update", data);
    }

    getUserById(userId: number): Observable<User> {
        return this.http.get<User>("https://e-frontend.azurewebsites.net/user/customer/" + userId);
    }

    getUserByOktaId(oktaId: string): Observable<any> {
        return this.http.get<any>("https://e-frontend.azurewebsites.net/user/customer/okta/" + oktaId);
    }
}