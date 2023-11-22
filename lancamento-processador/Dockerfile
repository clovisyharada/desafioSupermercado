FROM openjdk:21
VOLUME /tmp
ARG JAR_FILE=target/desafio-lancamento-processador-0.0.1-SNAPSHOT.jar 
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
