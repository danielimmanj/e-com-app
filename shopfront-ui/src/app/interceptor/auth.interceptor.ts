import {
  HttpEvent,
  HttpHandlerFn,
  HttpInterceptorFn,
  HttpRequest,
  HttpErrorResponse,
  HttpClient,
} from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { AuthService } from '../services/auth/auth.service';
import { inject } from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn,
): Observable<HttpEvent<unknown>> => {
  const token = localStorage.getItem('authToken');
  const refreshToken = localStorage.getItem('refreshToken'); // Assuming you store the refresh token
  // Clone the request and add the token to the headers if it exists
  const authReq = token
    ? req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      })
    : req;

  // Pass the cloned request to the next handler
  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401 && refreshToken) {
        // If unauthorized, attempt to refresh the token
        return refreshAccessToken(refreshToken).pipe(
          switchMap((newToken: string) => {
            // Store the new token
            localStorage.setItem('authToken', newToken);

            // Clone the request again with the new token
            const newAuthReq = req.clone({
              setHeaders: {
                Authorization: `Bearer ${newToken}`,
              },
            });

            // Retry the original request with the new token
            return next(newAuthReq);
          }),
          catchError((refreshError) => {
            // If refreshing fails, log out or handle it accordingly
            console.error('Refresh token failed', refreshError);
            // Optionally, you can redirect to login or clear tokens
            localStorage.removeItem('authToken');
            localStorage.removeItem('refreshToken');
            return throwError(refreshError); // Re-throw the error for further handling
          }),
        );
      }
      // If it's not a 401 error or no refresh token is available, throw the error
      return throwError(error);
    }),
  );
};

// Helper function to refresh the access token
function refreshAccessToken(refreshToken: string): Observable<string> {
  // Create an instance of AuthService to call the refresh token API
  const authService = new AuthService(inject(HttpClient)); // Ensure you pass HttpClient properly
  return authService.refreshToken(refreshToken).pipe(
    // Map the response to get the new access token
    switchMap((response: any) => {
      return of(response.token); // Adjust based on the response structure
    })
  );
}