FROM openjdk:21-jdk
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY project-1.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Xmx512m","-jar","/app.jar"]