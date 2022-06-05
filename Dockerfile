# 使用openjdk:8u191-jre-alpine作为基础镜像构建
FROM openjdk:8u191-jre-alpine
# 运行命令
RUN echo "===============博客项目网关镜像开始构建...==============="
# 标记镜像时区
RUN echo "Asia/Shanghai" > /etc/timezone
# 添加环境变量
## profile设置
ENV PROFILE prod
## 编码格式设置
ENV LANG en_US.UTF-8
## JVM参数设置
ENV JVM_OPTS "-Xms128m -Xmx256m"
# 获取传递参数，jar文件名
ARG JAR_FILE
# 根据dockerfile位置将jar包复制到容器内指定位置
COPY target/${JAR_FILE} /home/app.jar
# 执行命令
CMD java $JVM_OPTS -Dspring.profiles.active=${PROFILE} -jar /home/app.jar
# 声明容器默认暴露的端口
EXPOSE 8021
# 运行命令
RUN echo "===============博客项目网关镜像构建完毕!!!==============="
