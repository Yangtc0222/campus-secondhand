# 使用 Eclipse Temurin 的 Java 8 镜像（官方推荐）
FROM eclipse-temurin:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制 Maven 构建文件
COPY pom.xml .
COPY src ./src

# 安装 Maven 并构建项目
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests

# 暴露端口
EXPOSE 9999

# 启动应用
CMD ["java", "-Dserver.port=9999", "-Dserver.address=0.0.0.0", "-jar", "target/*.jar"]