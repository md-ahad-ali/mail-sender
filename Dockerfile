# ---- STAGE 1: Build JAR ----
FROM eclipse-temurin:21 AS build

WORKDIR /app

# Copy Maven wrapper and pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Build the Spring Boot jar
RUN ./mvnw clean package -DskipTests

# ---- STAGE 2: Runtime ----
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# Copy only the JAR from the build stage
COPY --from=build /app/target/mail-sender-0.0.1-SNAPSHOT.jar app.jar

# Expose port (change if your app runs on another port)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
