# Product Frontend

Angular frontend application for the Product Management System.

## Features

- User authentication (login/register)
- Product listing
- Product creation (admin only)
- Product deletion (admin only)
- JWT token-based authentication
- Responsive design

## Setup

1. Install dependencies:
```bash
npm install
```

2. Start the development server:
```bash
ng serve
```

3. Open http://localhost:4200

## Usage

1. Register a new account or login with existing credentials
2. View products in the product list
3. Admin users can add/delete products
4. Regular users can only view products

## Components

- **LoginComponent**: User authentication
- **RegisterComponent**: User registration
- **ProductListComponent**: Display all products
- **ProductFormComponent**: Create new products (admin only)

## Services

- **AuthService**: Handle authentication and JWT tokens
- **ProductService**: Manage product CRUD operations

## Guards

- **AuthGuard**: Protect routes requiring authentication