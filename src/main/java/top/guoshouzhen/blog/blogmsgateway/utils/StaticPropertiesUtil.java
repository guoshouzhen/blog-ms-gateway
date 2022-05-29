package top.guoshouzhen.blog.blogmsgateway.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author shouzhen.guo
 * @version V1.0
 * @description
 * @date 2022/5/29 16:13
 */
@Component
public class StaticPropertiesUtil {
    /**
     * nacos中路由配置的dataid
     */
    public static String NACOS_DATA_ID;
    @Value("${gateway.dynamicRoute.dataId}")
    public void setNacosDataId(String val){
        NACOS_DATA_ID = val;
    }

    /**
     * nacos中路由配置的group
     */
    public static String NACOS_GROUP;
    @Value("${gateway.dynamicRoute.group}")
    public void setNacosGroup(String val){
        NACOS_GROUP = val;
    }
}
