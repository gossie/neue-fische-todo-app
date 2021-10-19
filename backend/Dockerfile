FROM maven:3.8.2-openjdk-17
VOLUME /tmp
ARG JAR_FILE
WORKDIR /app

COPY pom.xml pom.xml
COPY src src

RUN mvn clean package -DskipTests

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker","-jar","target/todo-app-0.0.1-SNAPSHOT.jar"]