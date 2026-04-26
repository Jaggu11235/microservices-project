# Microservices Project

A Spring Boot Microservices application with Docker containerization and centralized logging via the ELK Stack.

---

## Tech Stack

- **Spring Boot 3.2.5** — Microservice framework
- **Spring Cloud 2023.0.1** — Eureka, Gateway, LoadBalancer
- **Docker + Docker Compose** — Containerization
- **ELK Stack** — Elasticsearch, Logstash, Kibana, Filebeat
- **Java 17**

---

## Services

| Service | Port | Description |
|---|---|---|
| Eureka Server | 8761 | Service Registry |
| API Gateway | 8080 | Single entry point for all requests |
| User Service | 8081 | User management |
| Product Service | 8082 | Product management |
| Order Service | 8083 | Order management |
| Elasticsearch | 9200 | Log storage |
| Kibana | 5601 | Log visualization |

---

## Project Structure

```
microservices-project/
├── docker-compose.yml
├── elk/
│   ├── filebeat/
│   │   └── filebeat.yml
│   └── logstash/
│       ├── logstash.conf
│       └── logstash.yml
├── eurekaserver/
│   ├── Dockerfile
│   └── src/
├── api-gateway/
│   ├── Dockerfile
│   └── src/
├── user-service/
│   ├── Dockerfile
│   └── src/
├── product-service/
│   ├── Dockerfile
│   └── src/
└── order-service/
    ├── Dockerfile
    └── src/
```

---

## Prerequisites

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed and running
- Java 17
- Maven

---

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/microservices-project.git
cd microservices-project
```

### 2. Create Logs Folder
Create a `Logs` folder on your Desktop (Windows):
```
C:/Users/<YOUR_USERNAME>/Desktop/Logs/
```
> Update the path in `docker-compose.yml` if your username is different.

### 3. Start All Services
```bash
docker-compose up --build -d
```

### 4. Verify All Containers Are Running
```bash
docker ps
```
You should see 9 containers running.

---

## Accessing the Services

| URL | Description |
|---|---|
| http://localhost:8761 | Eureka Dashboard |
| http://localhost:8080/actuator/health | API Gateway Health |
| http://localhost:8080/users | Users API (via Gateway) |
| http://localhost:8080/products | Products API (via Gateway) |
| http://localhost:8080/orders | Orders API (via Gateway) |
| http://localhost:9200/_cat/indices?v | Elasticsearch Indices |
| http://localhost:5601 | Kibana Dashboard |

---

## Viewing Logs in Kibana

1. Open **http://localhost:5601**
2. Go to **Management → Stack Management → Data Views**
3. Click **Create Data View**
4. Enter pattern: `microservices-*`
5. Select `@timestamp` as time field
6. Go to **Discover** → select `microservices-*` → set time to **Last 1 hour**

### Filter Logs by Service
```
fields.service : "user-service"
fields.service : "order-service" and level : "ERROR"
level : "ERROR"
```

---

## Stopping the Services

```bash
# Stop and keep data
docker-compose down

# Stop and delete all data (fresh start)
docker-compose down -v
```

---

## Useful Commands

```bash
# View logs of a specific service
docker logs user-service --tail=50

# Follow logs live
docker logs order-service -f

# Rebuild a single service
docker-compose up --build user-service -d

# Check Elasticsearch indices
curl http://localhost:9200/_cat/indices?v
```

---

## Architecture

```
Client
  │
  ▼
API Gateway (8080)
  │
  ├──► User Service (8081)
  ├──► Product Service (8082)
  └──► Order Service (8083)
         │
         └── All register with Eureka (8761)

Log Flow:
Spring Boot → /logs/*.log → Filebeat → Logstash → Elasticsearch → Kibana
```

---

## Common Issues

| Issue | Fix |
|---|---|
| Docker daemon error | Open Docker Desktop and wait for it to fully start |
| Services not in Eureka | Ensure `defaultZone: http://eurekaserver:8761/eureka/` in all services |
| No logs in Kibana | Hit the APIs to generate logs, wait 30s, refresh Kibana |
| Elasticsearch fails on restart | Run `docker-compose down -v` then restart |
| Log files created as folders | Ensure `/logs/` Linux path is used in `application.yml` |

---

## Important Notes

- Spring Cloud does **NOT** support Spring Boot 4.x — use **Spring Boot 3.2.5** with **Spring Cloud 2023.0.1**
- Inside Docker, always use **container names** (not `localhost`) for inter-service communication
- Logback is Spring Boot's default — **no extra logging dependencies** needed
- Windows paths cannot be used inside Linux containers — always use Linux paths with Docker volume mounts

---

## License

This project is for learning purposes.
