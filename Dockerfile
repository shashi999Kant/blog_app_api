FROM openjdk:17-alpine
ADD target/blogging-app-backend.jar blogging-app-backend.jar
ENTRYPOINT [ "java" , "-jar" , "blogging-app-backend.jar"]
