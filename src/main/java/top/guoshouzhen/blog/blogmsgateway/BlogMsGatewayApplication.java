package top.guoshouzhen.blog.blogmsgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.DependsOn;

@SpringBootApplication
@EnableDiscoveryClient
@DependsOn("staticPropertiesUtil")
public class BlogMsGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogMsGatewayApplication.class, args);
    }

}
