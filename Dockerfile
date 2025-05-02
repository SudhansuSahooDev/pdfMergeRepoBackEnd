FROM tomcat:9.0-jdk11

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR into Tomcat
COPY target/pdf-merge-backend.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
