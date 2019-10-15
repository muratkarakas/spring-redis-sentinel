FROM openjdk:8-jdk-alpine

ADD maven/${project.build.finalName}.jar app.jar

ENTRYPOINT [ "java","-jar","app.jar" ]