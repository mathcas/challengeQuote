FROM maven:3.8.1-jdk-8-slim AS build
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean install 

FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build /usr/src/app/target/*.jar app.jar
EXPOSE 8081  
ENTRYPOINT ["java","-jar","app.jar"]