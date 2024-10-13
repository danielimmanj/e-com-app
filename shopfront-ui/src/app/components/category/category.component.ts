import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [],
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  categories: string[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchCategoriesFromBackend().subscribe(
      (data: string[]) => {
        this.categories = data;
      },
      (error) => {
        console.error('Error fetching categories:', error);
      }
    );
  }

  fetchCategoriesFromBackend(): Observable<string[]> {
    const apiUrl = `${environment.apiUrl}/categories`;
    return this.http.get<string[]>(apiUrl);
  }
}
