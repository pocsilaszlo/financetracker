# 1. Alap image
FROM maven:3.8.6-openjdk-21 AS build

# 2. Munka könyvtár létrehozása
WORKDIR /app

# 3. Projekt fájlok másolása
COPY pom.xml .

# 4. Maven függőségek letöltése (csak a pom.xml alapján)
RUN mvn dependency:go-offline

# 5. Az összes projekt fájl másolása
COPY src ./src

# 6. Maven build (csak a jar fájl létrehozása)
RUN mvn clean package -DskipTests

# 7. Futtatható image létrehozása
FROM openjdk:21-jdk-slim

# 8. Munka könyvtár beállítása
WORKDIR /app

# 9. A jar fájl másolása az előző lépésből
COPY --from=build /app/target/financetracker-0.0.1-SNAPSHOT.jar /app/financetracker.jar

# 10. Alkalmazás indítása
ENTRYPOINT ["java", "-jar", "/app/financetracker.jar"]
