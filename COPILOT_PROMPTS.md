<!-- create spring boot microservice product-service
Add Spring Boot dependencies for Web, Data JPA, Security, mysql, JWT, Lombok
define a Product entity with id, name, price, and description
define a User entity with id, username, password, and role
create JPA repository for User with findByUsername
create service to handle CRUD for Product
create authcontroller for login and register,validate user
create authservice -->

Create a Spring Boot microservice project named `product-service`.

### 1. Dependencies:
Add the following dependencies:
- Spring Web
- Spring Data JPA
- Spring Security
- MySQL Driver
- Lombok
- JWT 

### 2. Application Configuration:
- Configure `application.properties` for database connection (MySQL), JPA settings, and JWT secret.

### 3. Entities:
#### a) Product.java
- Fields: 
  - Long id
  - String name
  - BigDecimal price
  - String description

#### b) User.java
- Fields: 
  - Long id
  - String username
  - String password
  - String role

### 4. Repositories:
#### a) ProductRepository extends JpaRepository<Product, Long>

#### b) UserRepository extends JpaRepository<User, Long>
- Custom method: `Optional<User> findByUsername(String username)`

### 5. Services:
#### ProductService
- Implement CRUD operations: createProduct, getAllProducts, getProductById, updateProduct, deleteProduct

### 6. DTOs:
Create DTOs for:
- AuthRequest (username, password)
- AuthResponse (JWT token)

### 7. Security Configuration:
#### JWT Util:
- Methods to generateToken, validateToken, extractUsername

#### JWT Filter:
- Intercept requests, validate token, and set authentication in SecurityContext

#### SecurityConfig:
- Permit `/api/auth/**`
- Authenticate all other endpoints
- Set JWT filter before UsernamePasswordAuthenticationFilter
- Use PasswordEncoder (BCrypt)

### 8. Controllers:
#### AuthController (URL: /api/auth/)
- POST `/register` - Create a new user
- POST `/login` - Validate credentials and return JWT token

#### ProductController (URL: /api/products/)
- Secure with JWT
- Endpoints: GET all, GET by ID, POST, PUT, DELETE

### 9. Other:
- Use Lombok annotations for getters/setters, constructors
- Use `@RestController`, `@RequestMapping`, `@Service`, `@Autowired`, `@Entity`, etc., as appropriate

Ensure all components are properly wired, tested, and protected via JWT-based authentication.
