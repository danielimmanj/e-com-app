import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { User } from '../../model/user.model';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css'],
})
export class SignUpComponent implements OnInit {
  selectedImage = '';
  currentCategory = 'signup'; // Default category
  signUpForm!: FormGroup;

  // Unsplash API endpoint and your access key
  private readonly unsplashApiUrl = 'https://api.unsplash.com/photos/random';
  private readonly accessKey = 'TjJd1mUVt47jLR_5AydA8y0vgaExLT-PhvZtwS3gIb8'; // Replace with your Unsplash API access key

  constructor(
    private http: HttpClient,
    private userService: UserService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.getRandomImage();
    this.initForm(); // Initialize the form
  }

  // Initialize the form with validators
  initForm() {
    this.signUpForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
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
        return 'register'; // Keywords for signup
      case 'login':
        return 'login'; // Keywords for login
      case 'shopping':
        return 'shopping'; // Keywords for shopping
      default:
        return 'nature'; // Default category
    }
  }

  // Optionally, you can add a method to change the category
  changeCategory(newCategory: string) {
    this.currentCategory = newCategory;
    this.getRandomImage(); // Fetch a new image for the selected category
  }

  onSubmit() {
    if (this.signUpForm.valid) {
      const user: User = this.signUpForm.value;
      user.role = 'CUSTOMER';
      this.userService.signUp(user).subscribe(
        (response) => {
          console.log('User registered successfully:', response);
        },
        (error) => {
          console.error('Error during signup:', error);
        }
      );
    }
  }
}
