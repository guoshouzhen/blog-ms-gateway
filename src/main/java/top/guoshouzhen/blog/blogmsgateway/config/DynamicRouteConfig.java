package top.guoshouzhen.blog.blogmsgateway.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.guoshouzhen.blog.blogmsgateway.route.NacosRouteDefinitionRepository;

/**
 * @author shouzhen.guo
 * @version V1.0
 * @description 动态路由配置
 * @date 2022/5/29 15:00
 */
@Configuration
@ConditionalOnProperty(prefix = "gateway.dynamicRoute", name = "enabled", havingValue = "true")
public class DynamicRouteConfig {
    /**
     * 注入ApplicationEventPublisher对象
     */
    private ApplicationEventPublisher publisher;
    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher){
        this.publisher = publisher;
    }

    /**
     * nacos方式实现
     */
    @Configuration
    @ConditionalOnProperty(prefix = "gateway.dynamicRoute", name = "dataType", havingValue = "nacos")
    public class NacosDnyRoute{
        /**
         * 注入NacosConfigManager对象
         */
        private NacosConfigManager nacosConfigManager;
        @Autowired
        public void setNacosConfigManager(NacosConfigManager nacosConfigManager){
            this.nacosConfigManager = nacosConfigManager;
        }

        /**
         * 自定义 RouteDefinitionRepository 的实现类来实现动态路由
         */
        @Bean
        public NacosRouteDefinitionRepository createNacosRouteRepository(){
            return new NacosRouteDefinitionRepository(publisher, nacosConfigManager);
        }
    }
}
