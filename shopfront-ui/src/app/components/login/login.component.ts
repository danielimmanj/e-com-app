import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'], // Corrected the property name from styleUrl to styleUrls
})
export class LoginComponent implements OnInit {
  selectedImage = '';
  currentCategory = 'login'; // Default category

  // Unsplash API endpoint and your access key
  private readonly unsplashApiUrl = 'https://api.unsplash.com/photos/random';
  private readonly accessKey = 'NCPkQ5Qpd__I5zkTUiUMNLlxCsaXcj6JZ8aD9I-KFRY'; // Replace with your Unsplash API access key

  email = ''; // Bind to email input
  password = ''; // Bind to password input

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {}

  ngOnInit() {
    this.getRandomImage(); // Fetch a random image on initialization
  }

  // Fetch a random image based on the current category
  getRandomImage() {
    const query = this.getCategoryQuery(this.currentCategory);
    const url = `${this.unsplashApiUrl}?query=${query}&client_id=${this.accessKey}`;

    // Fetch a random image from Unsplash API based on the category
    this.http.get<any>(url).subscribe(
      (response) => {
        if (response && response.urls) {
          this.selectedImage = response.urls.regular; // Use the regular image URL
        }
      },
      (error) => {
        console.error('Error fetching image:', error);
      },
    );
  }

  // Determine the query string based on the category
  private getCategoryQuery(category: string): string {
    switch (category) {
      case 'signup':
        return 'signup'; // Keywords for signup
      case 'login':
        return 'login'; // Keywords for login
      case 'shopping':
        return 'shopping'; // Keywords for shopping
      default:
        return 'nature'; // Default category
    }
  }

  // Handle login form submission
  onLoginSubmit() {

    this.authService.login(this.email, this.password).subscribe(
      (response) => {
        if (response && response.token) {
          localStorage.setItem('authToken', response.token); // Store access token
          localStorage.setItem('refreshToken', response.refreshToken); // Store refresh token if available
          this.router.navigate(['/dashboard']); // Redirect to the dashboard or desired route
        } else {
          console.error('Login failed', response);
        }
      },
      (error) => {
        console.error('Error during login:', error);
      },
    );
  }

  // Optionally, you can add a method to change the category
  changeCategory(newCategory: string) {
    this.currentCategory = newCategory;
    this.getRandomImage(); // Fetch a new image for the selected category
  }
}
