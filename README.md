# blog-ms-gateway

#### 介绍
基于spring boot及spring cloud gateway搭建的微服务，作为博客项目的微服务网关，所有请求的统一入口，根据请求路径中的服务名来转发请求到不同的服务。该网关使用动态路由，从nacos配置中心加载路由配置，好处是后续新增服务或者变更路由配置时，可自动加载最新路由配置，避免网关服务的停机。  

关键词：java，spring boot，spring cloud gateway，nacos

#### 程序结构说明
```
blog-ms-gateway
│  .gitignore
│  Dockerfile
│  pom.xml
│  README.md
│  
└─src
    └─main
        ├─java
        │  └─top
        │      └─guoshouzhen
        │          └─blog
        │              └─blogmsgateway
        │                  │  BlogMsGatewayApplication.java -----------------程序主类
        │                  │  
        │                  ├─config
        │                  │      DynamicRouteConfig.java -------------------动态路由配置，注入自定义RouteDefinitionRepository Bean
        │                  │      
        │                  ├─route
        │                  │      NacosRouteDefinitionRepository.java -------使用nacos配置中心作为路由配置源，实现RouteDefinitionRepository接口，并监听路由变化。当gateway监听到路由变化时，会自动调用相应方法加载路由配置
        │                  │      
        │                  └─utils ------------------------------------------一些工具类
        │                          JacksonUtil.java
        │                          StaticPropertiesUtil.java
        │                          StringUtil.java
        │                          
        └─resources
                blog-ms-gateway-routes --------------------------------------路由配置，根据请求路径Path转发，GatewayFilter使用StripPrefix，即：截断去除Path中的服务名，再结合配置的uri进行转发
                bootstrap-dev.yml -------------------------------------------开发环境配置
                bootstrap-prod.yml ------------------------------------------生产环境配置
                bootstrap.yml -----------------------------------------------公共配置（包括nacos多环境配置）
                logback-custom.xml ------------------------------------------日志配置
```

#### 部署说明
* docker容器部署
    * 使用maven插件打包
    * 使用dockerfile-maven build，并push镜像到远程仓库
    * 在服务器上拉取镜像,并运行
    * 运行示例
    ```
  docker run --name blog-gateway  \
  -p 8021:8021 \
  --net commonnetwork \
  --ip 172.18.4.1 \
  -v /root/appdata/blog/logs:/root/appdata/blog/logs \
  -d registry.cn-hangzhou.aliyuncs.com/blog-regs/blog-ms-gateway
  ```