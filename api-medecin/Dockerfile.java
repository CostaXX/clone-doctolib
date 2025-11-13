# # Étape 1 : Construction avec Maven Wrapper
# FROM eclipse-temurin:24-jdk AS builder
# WORKDIR /app

# # Copier tout ce qu'il faut au wrapper
# COPY .mvn .mvn
# COPY mvnw .
# COPY pom.xml .

# # Donner les droits d’exécution au wrapper
# RUN chmod +x mvnw

# # Télécharger les dépendances
# RUN ./mvnw dependency:go-offline -B

# # Copier le code source
# COPY src ./src

# # Compiler le projet
# RUN ./mvnw package -DskipTests

# # Étape 2 : Exécution du JAR
# FROM eclipse-temurin:24-jdk
# WORKDIR /app
# COPY --from=builder /app/target/api-medecin-0.0.1-SNAPSHOT.jar api-medecin-0.0.1-SNAPSHOT.jar
# EXPOSE 8080
# # ENTRYPOINT ["java", "-jar", "api-medecin-0.0.1-SNAPSHOT.jar"]