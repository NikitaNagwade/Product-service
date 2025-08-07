# Product Service

This is a Spring Boot microservice for managing products. It provides a RESTful API to perform CRUD operations on product entities.

## Features

- Create, Read, Update, and Delete (CRUD) operations for products.
- Simple and clean architecture using Spring Boot.
- Uses JPA for database interactions.

## Technologies Used

- Spring Boot
- Spring Data JPA
- H2 Database (or any other database of your choice)
- Maven

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven

### Installation

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/product-service.git
   ```
2. Navigate to the project directory:
   ```
   cd product-service
   ```
3. Build the project using Maven:
   ```
   mvn clean install
   ```

### Running the Application

To run the application, use the following command:
```
mvn spring-boot:run
```

The application will start on the default port 8080. You can access the API at `http://localhost:8080/products`.

### API Endpoints

- `GET /products` - Retrieve all products
- `GET /products/{id}` - Retrieve a product by ID
- `POST /products` - Create a new product
- `PUT /products/{id}` - Update an existing product
- `DELETE /products/{id}` - Delete a product by ID

## Testing

To run the tests, use the following command:
```
mvn test
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.