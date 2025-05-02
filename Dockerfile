FROM maven:3.8.5-openjdk-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM tomcat:9.0-jdk11
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/pdf-merge-backend-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
