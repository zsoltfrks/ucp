# Deployment Guide - UCP Game Server

This guide covers deploying the UCP application to production.

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Database Setup](#database-setup)
3. [Backend Deployment](#backend-deployment)
4. [Frontend Deployment](#frontend-deployment)
5. [Environment Configuration](#environment-configuration)
6. [Health Checks](#health-checks)

## Prerequisites

### Required Software
- Java 17 or higher (for backend)
- Node.js 18 or higher (for frontend)
- PostgreSQL 13 or higher
- Maven 3.6+ (for building backend)
- Nginx or similar reverse proxy (recommended)

### Recommended Infrastructure
- 2 CPU cores minimum
- 4GB RAM minimum
- 20GB storage minimum
- SSL/TLS certificate (Let's Encrypt recommended)

## Database Setup

### 1. Create Database

```sql
-- Connect to PostgreSQL as admin
psql -U postgres

-- Create database and user
CREATE DATABASE ucpdb;
CREATE USER ucpuser WITH ENCRYPTED PASSWORD 'your-secure-password';
GRANT ALL PRIVILEGES ON DATABASE ucpdb TO ucpuser;
```

### 2. Configure Database Connection

Set environment variables:
```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/ucpdb
export DATABASE_USERNAME=ucpuser
export DATABASE_PASSWORD=your-secure-password
```

## Backend Deployment

### 1. Build the Backend

```bash
cd backend
mvn clean package -DskipTests
```

This creates `target/backend-0.0.1-SNAPSHOT.jar`

### 2. Configure Environment Variables

Create a production configuration file or set environment variables:

```bash
export DATABASE_URL=jdbc:postgresql://your-db-host:5432/ucpdb
export DATABASE_USERNAME=ucpuser
export DATABASE_PASSWORD=your-secure-password
export JWT_SECRET=$(openssl rand -base64 32)
export JWT_EXPIRATION=86400000
export CORS_ALLOWED_ORIGINS=https://yourdomain.com
```

### 3. Run the Backend

#### Option A: Direct Java Execution
```bash
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

#### Option B: Systemd Service (Linux)

Create `/etc/systemd/system/ucp-backend.service`:

```ini
[Unit]
Description=UCP Backend Service
After=network.target postgresql.service

[Service]
Type=simple
User=ucp
WorkingDirectory=/opt/ucp/backend
EnvironmentFile=/opt/ucp/backend/.env
ExecStart=/usr/bin/java -jar /opt/ucp/backend/backend-0.0.1-SNAPSHOT.jar
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

Enable and start:
```bash
sudo systemctl enable ucp-backend
sudo systemctl start ucp-backend
```

#### Option C: Docker

Create `Dockerfile` in backend directory:

```dockerfile
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
docker build -t ucp-backend .
docker run -d \
  -p 8080:8080 \
  -e DATABASE_URL=jdbc:postgresql://db:5432/ucpdb \
  -e DATABASE_USERNAME=ucpuser \
  -e DATABASE_PASSWORD=password \
  -e JWT_SECRET=your-secret \
  --name ucp-backend \
  ucp-backend
```

## Frontend Deployment

### 1. Build the Frontend

```bash
cd frontend
npm install
npm run build
```

### 2. Configure Environment

Create `.env.production`:
```bash
NEXT_PUBLIC_API_URL=https://api.yourdomain.com/api
```

### 3. Deploy Frontend

#### Option A: Standalone Next.js Server

```bash
npm start
```

Runs on port 3000 by default.

#### Option B: Static Export (if not using SSR features)

Update `next.config.ts`:
```typescript
const nextConfig = {
  output: 'export',
};
```

Build and serve:
```bash
npm run build
# Serve the 'out' directory with any static file server
```

#### Option C: Docker

Create `Dockerfile` in frontend directory:

```dockerfile
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM node:18-alpine
WORKDIR /app
COPY --from=builder /app/package*.json ./
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/public ./public
RUN npm ci --production

EXPOSE 3000
CMD ["npm", "start"]
```

## Nginx Reverse Proxy Configuration

### Configuration File (`/etc/nginx/sites-available/ucp`)

```nginx
# Backend API
server {
    listen 80;
    server_name api.yourdomain.com;
    
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}

# Frontend
server {
    listen 80;
    server_name yourdomain.com www.yourdomain.com;
    
    location / {
        proxy_pass http://localhost:3000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

Enable and restart:
```bash
sudo ln -s /etc/nginx/sites-available/ucp /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

### Add SSL with Let's Encrypt

```bash
sudo apt install certbot python3-certbot-nginx
sudo certbot --nginx -d yourdomain.com -d www.yourdomain.com -d api.yourdomain.com
```

## Environment Configuration

### Backend Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `DATABASE_URL` | PostgreSQL JDBC URL | `jdbc:postgresql://localhost:5432/ucpdb` |
| `DATABASE_USERNAME` | Database username | `ucpuser` |
| `DATABASE_PASSWORD` | Database password | `secure-password` |
| `JWT_SECRET` | Secret key for JWT signing (256-bit min) | Generated with `openssl rand -base64 32` |
| `JWT_EXPIRATION` | Token expiration in milliseconds | `86400000` (24 hours) |
| `CORS_ALLOWED_ORIGINS` | Comma-separated allowed origins | `https://yourdomain.com` |

### Frontend Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `NEXT_PUBLIC_API_URL` | Backend API URL | `https://api.yourdomain.com/api` |

## Health Checks

### Backend Health Check
```bash
curl http://localhost:8080/actuator/health
```

### Database Connection Test
```bash
psql -h localhost -U ucpuser -d ucpdb -c "SELECT 1"
```

### Frontend Health Check
```bash
curl http://localhost:3000
```

## Security Checklist

- [ ] SSL/TLS enabled (HTTPS)
- [ ] Strong JWT secret generated
- [ ] Database credentials secured
- [ ] CORS restricted to specific domains
- [ ] Firewall configured (only ports 80, 443 open)
- [ ] Database backups configured
- [ ] Application logs configured
- [ ] Monitoring/alerting set up
- [ ] Rate limiting configured (optional but recommended)

## Monitoring

### Application Logs

Backend logs location: 
- Systemd: `journalctl -u ucp-backend -f`
- Docker: `docker logs -f ucp-backend`

### Database Monitoring

```sql
-- Check active connections
SELECT count(*) FROM pg_stat_activity WHERE datname = 'ucpdb';

-- Check table sizes
SELECT schemaname, tablename, pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename))
FROM pg_tables WHERE schemaname = 'public';
```

## Backup Strategy

### Database Backup
```bash
# Daily backup
pg_dump -U ucpuser ucpdb > backup-$(date +%Y%m%d).sql

# Automated backup script
0 2 * * * /usr/local/bin/backup-ucp-db.sh
```

### Application Backup
```bash
# Backup uploaded files, logs, etc.
tar -czf ucp-backup-$(date +%Y%m%d).tar.gz /opt/ucp
```

## Scaling Considerations

### Horizontal Scaling
- Use load balancer (e.g., Nginx, HAProxy)
- Ensure database can handle multiple connections
- Consider Redis for session management if needed

### Database Scaling
- Enable connection pooling
- Add read replicas for read-heavy workloads
- Consider PostgreSQL tuning for production

## Troubleshooting

### Backend Won't Start
1. Check database connectivity
2. Verify environment variables
3. Check logs for errors
4. Ensure port 8080 is available

### Frontend Can't Connect to Backend
1. Verify CORS settings
2. Check API URL in frontend config
3. Verify backend is running
4. Check network/firewall rules

### Database Connection Issues
1. Verify PostgreSQL is running
2. Check connection credentials
3. Ensure database exists
4. Check firewall rules

## Support

For issues and questions:
- GitHub Issues: [Create an issue](https://github.com/zsoltfrks/ucp/issues)
- Documentation: See README.md
- Security: See SECURITY.md
