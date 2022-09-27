import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VenderRequestService {

 
  constructor(private http: HttpClient) { }

  getAllRequests(): Observable<any> {
    return this.http.get<any>("http://localhost:8080/vendor/request/");
  }

  getRequestById(id: string): Observable<any> {
    return this.http.get<any>(`http://localhost:8080/vendor/request/${id}`);
  }


  deleteRequestById(id: any): Observable<any> {
    return this.http.delete(`http://localhost:8080/vendor/request/delete/${id}`);
  }

  updateStatus(data:any):Observable<any>{
    return this.http.put(`http://localhost:8080/vendor/request/update`,data);
  }

  addRequest(data:any):Observable<any>{
    return this.http.post(`http://localhost:8080/vendor/request/add`,data);
  }

}