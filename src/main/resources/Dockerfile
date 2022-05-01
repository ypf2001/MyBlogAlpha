FROM openjdk:8-jdk-alpine

#镜像的制作人
MAINTAINER 365384722@qq.com

#工作目录
WORKDIR /app/

RUN set -x && \
echo "#!/bin/bash" > entrypoint.sh && \
echo "java \$JAVA_OPTS \$JAVA_AGENT -Djava.awt.headless=true -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom -jar app.jar" >> entrypoint.sh && \
chmod +x entrypoint.sh

#声明了容器应该打开的端口并没有实际上将它打开
EXPOSE 8081

#拷贝本地文件到镜像中
COPY target/*-exec.jar app.jar

ENTRYPOINT ["./entrypoint.sh"]

