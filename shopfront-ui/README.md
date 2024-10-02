# ShopfrontUi

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 18.2.6.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.

## Folder Structure

Here’s the recommended folder structure for the project:

```plaintext
src/
├── app/
│   ├── core/                      # Core module (singleton services)
│   │   ├── services/              # Core services
│   │   ├── guards/                # Route guards
│   │   ├── interceptors/          # HTTP interceptors
│   │   ├── models/                # Interfaces and models
│   │   └── core.module.ts         # Core module definition
│   ├── shared/                    # Shared module (reusable components, directives, pipes)
│   │   ├── components/            # Shared components
│   │   ├── directives/            # Shared directives
│   │   ├── pipes/                 # Shared pipes
│   │   └── shared.module.ts       # Shared module definition
│   ├── features/                  # Feature modules (lazy-loaded)
│   │   ├── feature1/              # Feature 1
│   │   │   ├── components/        # Feature 1 components
│   │   │   ├── services/          # Feature 1 services
│   │   │   ├── feature1-routing.module.ts
│   │   │   └── feature1.module.ts
│   │   └── feature2/              # Feature 2
│   │       ├── components/
│   │       ├── services/
│   │       ├── feature2-routing.module.ts
│   │       └── feature2.module.ts
│   ├── layouts/                   # Layout components
│   │   ├── header/
│   │   ├── footer/
│   │   └── sidebar/
│   ├── pages/                     # Pages (views) of the application
│   │   ├── home/
│   │   ├── login/
│   │   └── sign-up/
│   ├── environments/              # Environment configurations
│   │   ├── environment.ts         # Development environment
│   │   └── environment.prod.ts    # Production environment
│   ├── app-routing.module.ts      # Main application routing
│   ├── app.module.ts              # Main application module
│   └── main.ts                    # Main entry point
├── assets/                        # Static assets (images, fonts, etc.)
├── styles/                        # Global styles (CSS or SCSS)
├── index.html                     # Main HTML file
├── polyfills.ts                   # Polyfills for browser compatibility
└── angular.json                   # Angular CLI configuration
```

### Explanation of Folders

- **`core/`**: Contains singleton services used throughout the application, including route guards and HTTP interceptors. This module is typically imported only once in the `AppModule`.

- **`shared/`**: Holds components, directives, and pipes that can be reused across different modules, promoting code reuse and modularity.

- **`features/`**: Each feature of your application can have its own module, including its own routing, components, and services. This modular approach allows for lazy loading, improving performance.

- **`layouts/`**: Groups consistent components like headers, footers, and sidebars to keep them separate from feature-specific components.

- **`pages/`**: Contains top-level components that represent different pages in your application.

- **`environments/`**: Contains environment-specific configurations. You can have different environment files for development, production, and testing.

- **`assets/`**: A place for static assets like images, fonts, or other files needed by your application.

- **`styles/`**: Contains global styles and can also include theme stylesheets.

## Further Help

To get more help on the Angular CLI, use `ng help` or check out the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.
