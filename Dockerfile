FROM maven
WORKDIR /app
ADD . /app
RUN mvn package
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5015","-jar", "target/rehearsal-bases-api-1.0.0.jar"]