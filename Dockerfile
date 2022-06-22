FROM 192.168.0.178:8088/hongwang-centos
WORKDIR /
ADD target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-Xms32m","-Xmx512m","-Duser.timezone=GMT+8","-jar","/app.jar"]