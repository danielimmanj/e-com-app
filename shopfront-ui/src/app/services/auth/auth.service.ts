import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiGatewayUrl = `${environment.apiUrl}/security`;

  constructor(private http: HttpClient) {}

  // Method to log in and fetch the access token
  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiGatewayUrl}/login`, null, {
      params: {
        username,
        password
      }
    });
  }

  // Method to refresh the access token
  refreshToken(refreshToken: string): Observable<any> {
    return this.http.post(`${this.apiGatewayUrl}/refresh-token`, null, {
      params: {
        refreshToken
      }
    });
  }

  // Method to log out
  logout(refreshToken: string): Observable<any> {
    return this.http.post(`${this.apiGatewayUrl}/logout`, null, {
      params: {
        refreshToken
      }
    });
  }

  // Example method to handle login process
  handleLogin(username: string, password: string): void {
    this.login(username, password).subscribe(
      (response: any) => {
        const token = response; // Adjust based on the actual response structure
        console.log('Token fetched:', token);
        // Store the token in local storage or state management
      },
      (error) => {
        console.error('Error during login:', error);
      }
    );
  }
}
