import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Review } from 'src/app/Models/Review';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient) { }

  getAllReviews(): Observable<Review[]> {
    return this.http.get<Review[]>(`https://e-backend.azurewebsites.net/review`);
  }

  getReviewByName(name: string): Observable<Review[]> {
    return this.http.get<Review[]>(`https://e-backend.azurewebsites.net/review/name/${name}`);
  }

  getReviewById(id: number): Observable<Review> {
    return this.http.get<Review>(`https://e-backend.azurewebsites.net/review/${id}`);
  }

  addReview(review: Review): Observable<any> {
    return this.http.post<Review>(`https://e-backend.azurewebsites.net/review/add`, review);
  }

  updateReview(review: Review): Observable<any> {
    return this.http.put<Review>(`https://e-backend.azurewebsites.net/review/update`, review);
  }

  deleteReview(id: number): Observable<any> {
    return this.http.delete<Review>(`https://e-backend.azurewebsites.net/review/delete/${id}`);
  }
}
