FROM openjdk:17-jdk-oracle
COPY ./target/MethodsExecTimeTracking-1.0.jar MethodsExecTimeTracking-1.0.jar
ENTRYPOINT ["java", "-jar", "MethodsExecTimeTracking-1.0.jar"]