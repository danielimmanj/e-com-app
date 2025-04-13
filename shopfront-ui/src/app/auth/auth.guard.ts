import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

export const authGuard: CanActivateFn = async (route, state) => {
  const keycloak = inject(KeycloakService);
  if (!keycloak.isLoggedIn()) {
    await keycloak.login({
      redirectUri: window.location.origin + state.url,
    });
    return false;
  }
  if (keycloak.isTokenExpired()) {
    await keycloak.updateToken();
  }
  return keycloak.isLoggedIn();
};
