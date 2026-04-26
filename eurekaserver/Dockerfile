FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy maven wrapper
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies first (layer caching)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the jar
RUN ./mvnw package -DskipTests

# Create log directory
RUN mkdir -p /var/log/eurekaserver

EXPOSE 8761

# Safe wildcard alternative - works regardless of version
ENTRYPOINT ["sh", "-c", "java -jar target/*.jar"]