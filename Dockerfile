FROM openjdk:24
ADD target/todo-app.jar todo-app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","todo-app.jar"]