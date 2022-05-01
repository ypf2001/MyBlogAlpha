# 基于openjdk 镜像
FROM openjdk:8
# 将本地文件夹挂在到当前容器
VOLUME /tmp
# 复制文件到容器
ADD centerbusiness.war app.war
# 声明需要暴露的端口
EXPOSE 8081 8443
# 配置容器启动后执行的命令
ENTRYPOINT ["sh", "-c", "java $PARAMS -Djava.security.egd=file:/dev/urandom -jar /app.war"]