# 使用 Eclipse Temurin 的 Java 8 镜像
FROM eclipse-temurin:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制 Maven 构建文件
COPY pom.xml .
COPY src ./src

# 安装 Maven 并配置阿里云镜像，然后构建项目
RUN apk add --no-cache maven && \
    mkdir -p /root/.m2 && \
    echo '<?xml version="1.0" encoding="UTF-8"?>' > /root/.m2/settings.xml && \
    echo '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"' >> /root/.m2/settings.xml && \
    echo '          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"' >> /root/.m2/settings.xml && \
    echo '          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">' >> /root/.m2/settings.xml && \
    echo '    <mirrors>' >> /root/.m2/settings.xml && \
    echo '        <mirror>' >> /root/.m2/settings.xml && \
    echo '            <id>aliyunmaven</id>' >> /root/.m2/settings.xml && \
    echo '            <mirrorOf>central</mirrorOf>' >> /root/.m2/settings.xml && \
    echo '            <name>阿里云公共仓库</name>' >> /root/.m2/settings.xml && \
    echo '            <url>https://maven.aliyun.com/repository/public</url>' >> /root/.m2/settings.xml && \
    echo '        </mirror>' >> /root/.m2/settings.xml && \
    echo '    </mirrors>' >> /root/.m2/settings.xml && \
    echo '</settings>' >> /root/.m2/settings.xml && \
    mvn clean package -DskipTests

# 暴露端口
EXPOSE 9999

# 启动应用
CMD ["java", "-Dserver.port=9999", "-Dserver.address=0.0.0.0", "-jar", "target/*.jar"]