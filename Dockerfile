# Image
FROM eclipse-temurin:23-jdk-ubi9-minimal

# Metadata
LABEL maintainer="angel.gamaza@gmail.com"

# Volumes
VOLUME /digimon

# Jars
COPY target/digimon-1.0.0.jar digimon-1.0.0.jar

# Port
EXPOSE 8081

# Entrypoint
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/digimon-1.0.0.jar"]