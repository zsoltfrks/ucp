# UCP - Serious Game Server Dashboard

A comprehensive User Control Panel (UCP) for managing serious game servers with role-based access control.

## Features

### Backend (Spring Boot + PostgreSQL)
- **Authentication & Authorization**
  - JWT-based authentication
  - Role-based access control (PLAYER, ADMIN, LEAD_ADMIN)
  - Secure password encryption with BCrypt

- **User Management**
  - User registration and login
  - Profile management
  - Role assignment (LEAD_ADMIN only)
  - Account activation/deactivation

- **Player Features**
  - Player profiles with level, money, and playtime tracking
  - House ownership management
  - Vehicle ownership management
  - View personal assets

- **Admin Features**
  - User management
  - Asset management (houses and vehicles)
  - Ban management
  - Warning system
  - Admin notes on players

### Frontend (Next.js + React + TypeScript + Tailwind CSS)
- **Authentication Pages**
  - Login page
  - Registration page
  - JWT token management

- **Player Dashboard**
  - View profile statistics
  - View owned houses
  - View owned vehicles

- **Admin Panel**
  - Manage all users
  - View and manage all assets
  - Manage bans and warnings
  - Role-based UI rendering

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Maven

### Frontend
- Next.js 16
- React 19
- TypeScript
- Tailwind CSS
- ESLint

## Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 18 or higher
- PostgreSQL 13 or higher
- Maven 3.6 or higher

### Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE ucpdb;
CREATE USER ucpuser WITH PASSWORD 'ucppass';
GRANT ALL PRIVILEGES ON DATABASE ucpdb TO ucpuser;
```

2. The application will automatically create tables on first run.

### Backend Setup

1. Navigate to the backend directory:
```bash
cd backend
```

2. Update `src/main/resources/application.properties` with your database credentials if needed.

3. Build and run the backend:
```bash
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`.

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Create a `.env.local` file (already created):
```
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

4. Run the development server:
```bash
npm run dev
```

The frontend will start on `http://localhost:3000`.

5. Build for production:
```bash
npm run build
npm start
```

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Users
- `GET /api/users/me` - Get current user
- `GET /api/users` - Get all users (Admin only)
- `GET /api/users/{id}` - Get user by ID (Admin only)
- `PUT /api/users/{id}/role` - Update user role (Lead Admin only)
- `PUT /api/users/{id}/toggle-active` - Toggle user active status (Admin only)

### Player Profiles
- `GET /api/profiles/me` - Get current user's profile
- `GET /api/profiles` - Get all profiles (Admin only)
- `GET /api/profiles/{id}` - Get profile by ID (Admin only)
- `PUT /api/profiles/{id}` - Update profile (Admin only)

### Houses
- `GET /api/houses/me` - Get current user's houses
- `GET /api/houses` - Get all houses (Admin only)
- `POST /api/houses` - Create house (Admin only)
- `PUT /api/houses/{id}` - Update house (Admin only)
- `DELETE /api/houses/{id}` - Delete house (Admin only)

### Vehicles
- `GET /api/vehicles/me` - Get current user's vehicles
- `GET /api/vehicles` - Get all vehicles (Admin only)
- `POST /api/vehicles` - Create vehicle (Admin only)
- `PUT /api/vehicles/{id}` - Update vehicle (Admin only)
- `DELETE /api/vehicles/{id}` - Delete vehicle (Admin only)

### Bans (Admin only)
- `GET /api/admin/bans` - Get all bans
- `POST /api/admin/bans` - Create ban
- `PUT /api/admin/bans/{id}/deactivate` - Deactivate ban
- `GET /api/admin/bans/user/{userId}` - Get bans for user

### Warnings (Admin only)
- `GET /api/admin/warnings` - Get all warnings
- `POST /api/admin/warnings` - Create warning
- `PUT /api/admin/warnings/{id}/acknowledge` - Acknowledge warning
- `GET /api/admin/warnings/user/{userId}` - Get warnings for user

### Admin Notes (Admin only)
- `GET /api/admin/notes` - Get all notes
- `POST /api/admin/notes` - Create note
- `GET /api/admin/notes/user/{userId}` - Get notes for user

## Default Roles

- **PLAYER**: Default role for registered users
  - View own profile and assets
  - Access player dashboard

- **ADMIN**: Administrative role
  - All PLAYER permissions
  - Manage users (activate/deactivate)
  - Manage assets (houses, vehicles)
  - Create bans and warnings
  - Add admin notes

- **LEAD_ADMIN**: Super admin role
  - All ADMIN permissions
  - Change user roles
  - Delete bans, warnings, and notes

## Project Structure

```
ucp/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ucp/backend/
│   │   │   │   ├── config/         # Security and app configuration
│   │   │   │   ├── controller/     # REST API controllers
│   │   │   │   ├── dto/            # Data Transfer Objects
│   │   │   │   ├── model/          # JPA entities
│   │   │   │   ├── repository/     # JPA repositories
│   │   │   │   ├── security/       # JWT and security classes
│   │   │   │   └── service/        # Business logic
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
└── frontend/
    ├── app/
    │   ├── admin/              # Admin panel page
    │   ├── dashboard/          # Player dashboard page
    │   ├── login/              # Login page
    │   ├── register/           # Register page
    │   ├── layout.tsx          # Root layout
    │   └── page.tsx            # Home page (redirects to login)
    ├── components/
    │   ├── layout/             # Layout components
    │   └── ui/                 # Reusable UI components
    ├── lib/
    │   ├── api.ts              # API client
    │   └── auth.ts             # Auth utilities
    └── package.json
```

## Development

### Backend Development
The backend uses Spring Boot DevTools for hot reloading during development.

### Frontend Development
The frontend uses Next.js Fast Refresh for instant feedback during development.

### Testing
Run backend tests:
```bash
cd backend
mvn test
```

## Security Features

- JWT token-based authentication
- Password encryption with BCrypt
- Role-based access control
- CORS configuration for frontend
- Secure HTTP headers
- Input validation

## License

MIT License - See LICENSE file for details
