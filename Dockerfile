FROM openjdk:17-jdk
ARG JAR_FILE=target/order-flow*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]