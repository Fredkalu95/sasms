# Smart Appointment and Service Management System (SASMS)

A comprehensive Spring Boot application for managing appointments, services, bookings, and payments with JWT authentication and Stripe integration.

## 🚀 Features

- **User Management**: Registration, login with JWT authentication
- **Booking System**: Create, view, update, and cancel appointments
- **Payment Processing**: Stripe integration for secure payments
- **Role-Based Access**: User and Admin roles with appropriate permissions
- **Notifications**: Email notifications for booking updates
- **Scheduling**: Automated task scheduling for reminders and cleanup
- **API Documentation**: Swagger/OpenAPI documentation
- **Database**: PostgreSQL with JPA/Hibernate

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.2.5
- **Database**: PostgreSQL
- **Authentication**: JWT (JSON Web Tokens)
- **Payment**: Stripe API
- **Security**: Spring Security
- **Documentation**: SpringDoc OpenAPI
- **Build Tool**: Maven
- **Java Version**: 17

## 📋 Requirements

- Java 17+
- Maven 3.6+
- PostgreSQL 12+
- Stripe Account (for payment processing)

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/Fredkalu95/sasms.git
cd sasms
```

### 2. Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE sasms_db;
CREATE USER postgres WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE sasms_db TO postgres;
```

### 3. Environment Configuration

Copy the environment template and configure your variables:

```bash
cp .env.example .env
```

Update `.env` with your actual values:

```properties
# Database Configuration
DB_PASSWORD=your_database_password_here

# JWT Configuration
JWT_SECRET=MySuperSecretKeyThatIsAtLeast32Chars!

# Stripe Configuration
STRIPE_SECRET_KEY=sk_test_your_stripe_secret_key_here
```

### 4. Run the Application

```bash
# Using Maven Wrapper
./mvnw spring-boot:run

# Or using Maven if installed
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

Once the application is running, you can access the interactive API documentation at:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## 🔗 API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - User login

### Bookings
- `GET /bookings` - Get current user's bookings
- `POST /bookings` - Create a new booking
- `PUT /bookings/{id}` - Update a booking
- `DELETE /bookings/{id}` - Cancel a booking
- `GET /bookings/all` - Get all bookings (Admin only)

### Payments
- `POST /payment/initiate/{bookingId}` - Initiate payment for a booking
- `POST /payment/confirm/{paymentId}` - Confirm payment

### Notifications
- `GET /notifications` - Get user notifications

## 🏗️ Project Structure

```
src/main/java/com/sasms/
├── config/                 # Configuration classes
├── controller/            # REST API controllers
├── model/
│   ├── dto/               # Data Transfer Objects
│   └── entity/            # JPA entities
├── repository/            # JPA repositories
├── security/              # Security configuration
├── service/               # Business logic services
│   └── impl/              # Service implementations
└── scheduler/             # Scheduled tasks
```

## 🔐 Security Features

- JWT-based authentication
- Password encryption with BCrypt
- Role-based authorization
- CORS configuration
- Request validation

## 💳 Payment Integration

The application integrates with Stripe for payment processing:

- Payment initiation for bookings
- Secure payment confirmation
- Payment status tracking
- Webhook support for payment events

## 📧 Email Notifications

Configurable email notifications for:
- Booking confirmations
- Payment status updates
- Appointment reminders

## 🧪 Testing

Run the test suite:

```bash
./mvnw test
```

## 📝 Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_PASSWORD` | PostgreSQL database password | - |
| `JWT_SECRET` | JWT signing secret (min 32 chars) | - |
| `STRIPE_SECRET_KEY` | Stripe API secret key | - |

## 🚀 Deployment

### Docker Deployment

```bash
# Build the application
./mvnw clean package

# Run with Docker (Dockerfile required)
docker build -t sasms .
docker run -p 8080:8080 sasms
```

### Production Considerations

- Use environment variables for all sensitive data
- Configure SSL/HTTPS
- Set up proper database connection pooling
- Configure production-ready logging
- Set up monitoring and health checks

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support, please open an issue in the GitHub repository or contact the development team.

## 🔄 Version History

- **v0.0.1-SNAPSHOT** - Initial release with core functionality
  - User authentication
  - Booking management
  - Payment processing
  - Basic notification system

---

**Built with ❤️ using Spring Boot**
