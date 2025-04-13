// import { Injectable } from '@angular/core';
// import { KeycloakService } from 'keycloak-angular';

// @Injectable({
//   providedIn: 'root'
// })
// export class KeycloakInitService {

//   constructor(private keycloak: KeycloakService) {}

//   init(): Promise<boolean> {
//     return this.keycloak.init({
//       config: {
//         url: 'http://localhost:8081',
//         realm: 'e-commerce',
//         clientId: 'frontend',
//       },
//       initOptions: {
//         onLoad: 'login-required',
//         checkLoginIframe: false,
//       },
//     });
//   }
// }
