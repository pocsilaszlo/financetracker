# 1. Alap image OpenJDK 21 használatával
FROM openjdk:21-jdk AS build

# 2. Maven telepítése
RUN apt-get update && apt-get install -y maven

# 3. Munka könyvtár létrehozása
WORKDIR /app

# 4. Maven dependency-k letöltése
COPY pom.xml .
RUN mvn dependency:go-offline

# 5. Alkalmazás forráskódjának másolása
COPY src ./src

# 6. Alkalmazás lefordítása
RUN mvn clean package -DskipTests

# 7. Második szakasz a futtatásra OpenJDK 21-tel
FROM openjdk:21-jdk-slim

WORKDIR /app

# Másoljuk a lefordított .jar fájlt
COPY --from=build /app/target/financetracker-0.0.1-SNAPSHOT.jar /app/financetracker.jar

# 8. Alkalmazás indítása
ENTRYPOINT ["java", "-jar", "/app/financetracker.jar"]
