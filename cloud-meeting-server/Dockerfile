FROM 0ces01uz.mirror.aliyuncs.com/library/openjdk:8
VOLUME /tmp
ARG docker-demo
# 默认的目录为根路径
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

