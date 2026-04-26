FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw package -DskipTests

RUN mkdir -p /var/log/user-service

EXPOSE 8081

# Safe wildcard alternative - works regardless of version
ENTRYPOINT ["sh", "-c", "java -jar target/*.jar"]