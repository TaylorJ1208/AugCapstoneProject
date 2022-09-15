import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from 'src/app/Models/categories';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private http: HttpClient) {}

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(
      'https://augustproject.azurewebsites.net/category/'
    );
  }

  getCategoryById(id: any): Observable<any> {
    return this.http.get<any>(
      `https://augustproject.azurewebsites.net/category/${id}`
    );
  }

  deleteCategory(id: any): Observable<any> {
    return this.http.delete(
      `https://augustproject.azurewebsites.net/category/delete/${id}`
    );
  }

  updateCategory(data: any): Observable<any> {
    return this.http.put(
      `https://augustproject.azurewebsites.net/category/update`,
      data
    );
  }

  addCategory(data: any): Observable<any> {
    return this.http.post(
      `https://augustproject.azurewebsites.net/category/admin/add`,
      data
    );
  }
}
