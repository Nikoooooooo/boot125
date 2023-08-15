#添加Java 11镜像来源
FROM openjdk:11

#添加参数
ARG JAR_FILE

#添加Spring Boot 包,JAR_FILE参数就是从 Docker Maven 插件中指定的构建参数
ADD target/${JAR_FILE} app.jar

#执行启动命令,  -Djava.security.egd=file/dev/./urandom优化随数生成策略的
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]