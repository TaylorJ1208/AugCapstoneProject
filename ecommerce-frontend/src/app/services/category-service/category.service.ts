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
    return this.http.get<Category[]>("https://e-frontend.azurewebsites.net/category/");
  }
  
  getCategoryById(id: any): Observable<any> {
    return this.http.get<any>(`https://e-frontend.azurewebsites.net/category/${id}`);
  }

  deleteCategory(id: any): Observable<any> {
    return this.http.delete(`https://e-frontend.azurewebsites.net/category/delete/${id}`);
  }

  updateCategory(data:any):Observable<any>{
    return this.http.put(`https://e-frontend.azurewebsites.net/category/update`,data);
  }

  addCategory(data:any):Observable<any>{
    return this.http.post(`https://e-frontend.azurewebsites.net/category/admin/add`, data);
  }

}
