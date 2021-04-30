FROM openjdk:latest
COPY ./target/seMethods-final-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "seMethods-final-jar-with-dependencies.jar"]