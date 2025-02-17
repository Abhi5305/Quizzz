# Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
# Copy only pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .
# Download dependencies offline to improve caching between builds
RUN mvn dependency:go-offline

# Copy source code and build the application
COPY src ./src
# Run compilation and packaging, skipping tests and javadoc generation
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/quizzz-0.0.1-SNAPSHOT.jar abhi-quiz.jar

# Expose the application port
EXPOSE 8181

# Run the application
ENTRYPOINT ["java", "-jar", "abhi-quiz.jar"]
