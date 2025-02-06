# 1. Alap image Maven és OpenJDK 21 használatával
FROM maven:3.8.6-openjdk-21 AS build

# 2. Munka könyvtár létrehozása
WORKDIR /app

# 3. Maven dependency-k letöltése
COPY pom.xml .
RUN mvn dependency:go-offline

# 4. Alkalmazás forráskódjának másolása
COPY src ./src

# 5. Alkalmazás lefordítása
RUN mvn clean package -DskipTests

# 6. Második szakasz a futtatásra OpenJDK 21-tel
FROM openjdk:21-jdk-slim

WORKDIR /app

# Másoljuk a lefordított .jar fájlt
COPY --from=build /app/target/financetracker-0.0.1-SNAPSHOT.jar /app/financetracker.jar

# 7. Alkalmazás indítása
ENTRYPOINT ["java", "-jar", "/app/financetracker.jar"]
