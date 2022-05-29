package top.guoshouzhen.blog.blogmsgateway.route;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.guoshouzhen.blog.blogmsgateway.utils.JacksonUtil;
import top.guoshouzhen.blog.blogmsgateway.utils.StaticPropertiesUtil;
import top.guoshouzhen.blog.blogmsgateway.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author shouzhen.guo
 * @version V1.0
 * @description 实现动态路由数据的加载（使用动态路由，避免需要添加新服务时，由于路由更新导致的网关重启）
 * @date 2022/5/29 0:47
 */
@Slf4j
public class NacosRouteDefinitionRepository implements RouteDefinitionRepository {
    /**
     * nacos中路由配置的dataid
     */
    private final String nacosDataId = StaticPropertiesUtil.NACOS_DATA_ID;
    /**
     * nacos中路由配置的group
     */
    private final String nacosGroup = StaticPropertiesUtil.NACOS_GROUP;
    /**
     * 更新路由信息用
     */
    private final ApplicationEventPublisher publisher;

    /**
     * nacos配置信息
     */
    private final NacosConfigManager nacosConfigManager;


    /**
     * 构造器
     *
     * @param publisher          publisher
     * @param nacosConfigManager nacosConfigManager
     * @author shouzhen.guo
     * @date 2022/5/29 13:50
     */
    public NacosRouteDefinitionRepository(ApplicationEventPublisher publisher, NacosConfigManager nacosConfigManager) {
        this.publisher = publisher;
        this.nacosConfigManager = nacosConfigManager;
        //配置Nacos监听，监听路由信息变化
        addListener();
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> lstRouteDefinitions = new ArrayList<>(0);
        try {
            String content = this.nacosConfigManager.getConfigService().getConfig(this.nacosDataId, this.nacosGroup, 5000);
            lstRouteDefinitions = getListByStr(content);
        } catch (Exception ex) {
            log.error("获取Nacos中的路由信息时，发生异常", ex);
        }
        return Flux.fromIterable(lstRouteDefinitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

    /**
     * 监听路由信息变化
     *
     * @author shouzhen.guo
     * @date 2022/5/29 13:52
     */
    private void addListener() {
        try{
            this.nacosConfigManager.getConfigService()
                    .addListener(this.nacosDataId, this.nacosGroup, new Listener() {
                        @Override
                        public Executor getExecutor() {
                            return null;
                        }

                        /**
                         * 路由变化只需向ApplicationEventPublisher推送一个RefreshRoutesEvent事件即可，
                         * gateway会自动监听该事件，并调用getRouteDefinitions方法更新路由信息
                         * @author shouzhen.guo
                         * @date 2022/5/29 14:57
                         * @param s 路由信息
                         */
                        @Override
                        public void receiveConfigInfo(String s) {
                            log.info("路由配置更新..." + s);
                            publisher.publishEvent(new RefreshRoutesEvent(this));
                        }
                    });
        }catch (Exception ex){
            log.error("监听路由信息变化时发生异常，异常信息：", ex);
        }
    }

    /**
     * 处理Nacos中的路由信息
     * @author shouzhen.guo
     * @date 2022/5/29 14:07
     * @param content 路由信息
     * @return java.util.List<org.springframework.cloud.gateway.route.RouteDefinition>
     */
    private List<RouteDefinition> getListByStr(String content) {
        List<RouteDefinition> lstResult = new ArrayList<>(0);
        if (!StringUtil.isNullOrWhiteSpace(content)) {
            try {
                lstResult = JacksonUtil.json2ListObj(content, RouteDefinition.class);
            } catch (Exception ex) {
                log.error("转换nacos中的路由信息时发生异常，异常信息：", ex);
            }
        }
        return lstResult;
    }
}
