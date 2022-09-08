import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from 'src/app/Models/categories';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>("http://localhost:8081/category/");
  }
  
  getCategoryById(id: any): Observable<any> {
    return this.http.get<any>(`http://localhost:8081/category/${id}`);
  }

  deleteCategory(id: any): Observable<any> {
    return this.http.delete(`http://localhost:8081/category/delete/${id}`);
  }

  updateCategory(data:any):Observable<any>{
    return this.http.put(`http://localhost:8081/category/update`,data);
  }

  addCategory(data:any):Observable<any>{
    return this.http.post(`http://localhost:8081/category/admin/add`, data);
  }

}
