# Quick Start Guide - UCP Game Server

Get your UCP development environment running in 5 minutes!

## Option 1: Quick Start with Docker Compose (Recommended)

### Prerequisites
- Docker and Docker Compose installed
- Git

### Steps

1. **Clone the repository**
```bash
git clone https://github.com/zsoltfrks/ucp.git
cd ucp
```

2. **Start PostgreSQL**
```bash
docker-compose up -d
```

3. **Start the Backend**
```bash
cd backend
mvn spring-boot:run
```
Backend will run on http://localhost:8080

4. **Start the Frontend** (in a new terminal)
```bash
cd frontend
npm install
npm run dev
```
Frontend will run on http://localhost:3000

5. **Access the Application**
- Open http://localhost:3000
- Register a new account
- Login and explore!

## Option 2: Manual Setup

### Prerequisites
- Java 17+
- Node.js 18+
- PostgreSQL 13+
- Maven 3.6+

### Steps

1. **Set up PostgreSQL**
```sql
CREATE DATABASE ucpdb;
CREATE USER ucpuser WITH PASSWORD 'ucppass';
GRANT ALL PRIVILEGES ON DATABASE ucpdb TO ucpuser;
```

2. **Clone and start backend**
```bash
git clone https://github.com/zsoltfrks/ucp.git
cd ucp/backend
mvn spring-boot:run
```

3. **Start frontend** (new terminal)
```bash
cd ucp/frontend
npm install
npm run dev
```

4. **Visit** http://localhost:3000

## First Steps

### 1. Register an Account
- Navigate to http://localhost:3000
- Click "Register"
- Fill in username, email, and password
- Click "Register"

### 2. Explore Player Dashboard
After logging in, you'll see:
- Your player profile (level, money, playtime)
- Your houses (initially empty)
- Your vehicles (initially empty)

### 3. Testing Admin Features

To test admin features, you need to manually upgrade a user to admin role:

1. **Connect to the database**
```bash
docker exec -it ucp-postgres psql -U ucpuser -d ucpdb
```

2. **Update user role**
```sql
-- Replace 'yourusername' with your actual username
UPDATE users SET role = 'ADMIN' WHERE username = 'yourusername';
-- Or for LEAD_ADMIN
UPDATE users SET role = 'LEAD_ADMIN' WHERE username = 'yourusername';
```

3. **Logout and login again** to see the admin panel link in navigation

### 4. Using the Admin Panel
As an admin, you can:
- View all users and toggle their active status
- View all houses and vehicles
- Create bans and warnings
- Add admin notes

## API Testing with cURL

### Register a user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'
```

### Get current user profile (requires token)
```bash
curl -X GET http://localhost:8080/api/profiles/me \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Troubleshooting

### Database Connection Failed
- Ensure PostgreSQL is running
- Check credentials in `backend/src/main/resources/application.properties`
- Verify database exists: `psql -U ucpuser -d ucpdb -c "SELECT 1"`

### Port Already in Use
- Backend (8080): Stop other services or change port in application.properties
- Frontend (3000): Use `npm run dev -- -p 3001` to use different port
- PostgreSQL (5432): Change port in docker-compose.yml

### Frontend Can't Connect to Backend
- Ensure backend is running on port 8080
- Check `frontend/.env.local` has correct API URL
- Check browser console for CORS errors

## Sample Data

Want to test with sample data? Run these SQL commands:

```sql
-- Assuming you have a user with id=1
-- Add some sample houses
INSERT INTO houses (owner_id, address, price, interior_id, locked, purchased_at)
VALUES 
  (1, '123 Grove Street', 50000, 1, false, NOW()),
  (1, '456 Mulholland Drive', 250000, 5, true, NOW());

-- Add some sample vehicles
INSERT INTO vehicles (owner_id, model, color, plate_number, price, health, impounded, purchased_at)
VALUES 
  (1, 'Infernus', 'Red', 'FAST001', 95000, 1000, false, NOW()),
  (1, 'Sultan', 'Blue', 'RACE123', 12500, 950, false, NOW());

-- View the profile_id for your user
SELECT id, user_id FROM player_profiles WHERE user_id = 1;
```

## Next Steps

- Read the full [README.md](README.md) for detailed information
- Check [DEPLOYMENT.md](DEPLOYMENT.md) for production deployment
- Review [SECURITY.md](SECURITY.md) for security information
- Start customizing the application for your needs!

## Common Customizations

### Change Default Ports

**Backend**: Edit `backend/src/main/resources/application.properties`
```properties
server.port=8081
```

**Frontend**: Run with custom port
```bash
npm run dev -- -p 3001
```
And update `NEXT_PUBLIC_API_URL` in `.env.local`

### Add More Fields to Player Profile

1. Add field to `backend/src/main/java/com/ucp/backend/model/PlayerProfile.java`
2. Add to DTO: `backend/src/main/java/com/ucp/backend/dto/PlayerProfileDTO.java`
3. Update service to map the field
4. Update frontend to display it

### Customize Roles

Edit `backend/src/main/java/com/ucp/backend/model/Role.java` to add more roles.

## Development Tips

- Backend auto-reloads with Spring DevTools
- Frontend has Fast Refresh for instant updates
- Use browser DevTools to inspect API calls
- Check backend console for SQL queries (when `spring.jpa.show-sql=true`)

Enjoy using UCP! 🎮
