# Banking App - Spring Boot
A full-featured banking application built with Spring Boot and Thymeleaf.

## Features
- User Registration and Authentication
- Account Management (Create, View Multiple Accounts)
- Balance Tracking
- Transaction Operations:
  - Deposits
  - Withdrawals
  - Transfers between accounts
- Transaction History
- Responsive Web Interface
- Spring Security with BCrypt Password Encoding

## Tech Stack
- **Backend**: Spring Boot 3.2
- **Database**: H2 (In-Memory)
- **ORM**: Spring Data JPA
- **Security**: Spring Security
- **Frontend**: Thymeleaf Templates
- **Build Tool**: Maven
- **Java Version**: 17

## Project Structure
```
banking-app/
├── src/main/java/com/bankingapp/
│   ├── controller/      # REST Controllers
│   ├── model/          # JPA Entity Classes
│   ├── repository/     # Data Access Layer
│   ├── service/        # Business Logic Layer
│   └── config/         # Configuration Classes
├── src/main/resources/
│   ├── templates/      # Thymeleaf HTML Templates
│   ├── static/css/     # CSS Stylesheets
│   └── application.properties  # App Configuration
└── pom.xml            # Maven Dependencies
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Installation
1. Navigate to the project directory:
   ```bash
   cd banking-app
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application:
   - Open browser and go to `http://localhost:8080`

### Default User (Optional for testing)
The application uses H2 in-memory database. You can:
1. Register a new user
2. Login with your credentials
3. Create accounts and perform transactions

## Usage

### User Registration
- Click "Register" on the homepage
- Fill in your details
- Login with your credentials

### Managing Accounts
- Create a new account from dashboard
- Choose account type (Savings, Checking, Money Market)
- Each account gets a unique account number

### Performing Transactions
- **Deposit**: Add funds to your account
- **Withdraw**: Remove funds (with balance validation)
- **Transfer**: Send money to another account (by account number)

### Viewing Transaction History
- Click on any account to view details
- See all transactions with dates and balances

## API Endpoints

### Authentication
- `GET /` - Home page
- `GET /auth/login` - Login page
- `POST /login` - Process login
- `GET /auth/register` - Registration page
- `POST /auth/register` - Process registration
- `GET /logout` - Logout

### Dashboard
- `GET /dashboard` - User dashboard

### Accounts
- `GET /accounts` - List user accounts
- `GET /accounts/create` - Create account form
- `POST /accounts/create` - Create new account
- `GET /accounts/{id}` - View account details

### Transactions
- `GET /transactions/deposit/{accountId}` - Deposit form
- `POST /transactions/deposit` - Process deposit
- `GET /transactions/withdraw/{accountId}` - Withdraw form
- `POST /transactions/withdraw` - Process withdrawal
- `GET /transactions/transfer/{accountId}` - Transfer form
- `POST /transactions/transfer` - Process transfer

## Database Access
- H2 Console available at: `http://localhost:8080/h2-console`
- Use JDBC URL: `jdbc:h2:mem:bankingdb`

## Security Features
- Password encryption using BCrypt
- Spring Security for authentication/authorization
- CSRF protection
- Secured endpoints (login required)

## Future Enhancements
- API endpoints (REST API)
- Transaction scheduling
- Bill payments
- Account statements (PDF export)
- Notification system
- Two-factor authentication
- Mobile app
- Database persistence (MySQL/PostgreSQL)

## License
MIT License

## Author
Banking App Development Team
