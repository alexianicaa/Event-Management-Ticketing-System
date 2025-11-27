# ---- Stage 1: Build ----
<<<<<<< HEAD
FROM eclipse-temurin:17-jdk AS builder
=======
FROM eclipse-temurin:21-jdk AS builder
>>>>>>> 4f479aa (implementation)

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

# ---- Stage 2: Run ----
<<<<<<< HEAD
FROM eclipse-temurin:17-jre
=======
FROM eclipse-temurin:21-jre
>>>>>>> 4f479aa (implementation)

WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8083

<<<<<<< HEAD
ENTRYPOINT ["java", "-jar", "app.jar"]
=======
ENTRYPOINT ["java", "-Dserver.port=8083", "-jar", "app.jar"]
>>>>>>> 4f479aa (implementation)
