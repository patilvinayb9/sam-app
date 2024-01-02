package com.edge;

import com.hazelcast.config.*;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.hazelcast.web.SessionListener;
import com.hazelcast.web.spring.SpringAwareWebFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import javax.servlet.DispatcherType;

@Configuration
public class CacheConfig {

    @Value("${cache.discovery:kubernetes}")
    private String cacheDiscovery;

    @Value("${cache.port:5701}")
    private int cachePort;

    /**
     * Create a Hazelcast {@code Config} object as a bean. Spring Boot will use
     * the presence of this to determine that a {@code HazelcastInstance} should
     * be created with this configuration.
     * <p>
     * As a simple side-step to possible networking issues, turn off multicast
     * in favour of TCP connection to the local host.
     *
     * @return Configuration for the Hazelcast instance
     */
    @Bean
    public Config hazelcastConfig() {
        if (cacheDiscovery.equals("kubernetes")) {
            return hazelcastConfigKubernetes();
        } else {
            return hazelcastConfigLocal();
        }
    }

    private Config hazelcastConfigKubernetes() {
        Config cfg = new Config();
        //cfg.setProperty("hazelcast.rest.enabled", "true");
        //cfg.setProperty("hazelcast.discovery.enabled", "true");

        /*GroupConfig groupConfig = cfg.getGroupConfig();
        groupConfig.setName("meetmate");
        groupConfig.setPassword("meetmate");*/

        NetworkConfig networkConfig = cfg.getNetworkConfig();
        networkConfig.setPort(cachePort);
        networkConfig.setPortAutoIncrement(false);

        JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(false);
        joinConfig.getKubernetesConfig().setEnabled(true);

        DiscoveryConfig discoveryConfig = joinConfig.getDiscoveryConfig();
        HazelcastKubernetesDiscoveryStrategyFactory factory = new HazelcastKubernetesDiscoveryStrategyFactory();
        DiscoveryStrategyConfig strategyConfig = new DiscoveryStrategyConfig(factory);
        strategyConfig.addProperty("service-dns", "meetmate.default.svc.cluster.local");
        discoveryConfig.addDiscoveryStrategyConfig(strategyConfig);

        return cfg;
    }

    private Config hazelcastConfigLocal() {
        Config cfg = new Config();

        NetworkConfig networkConfig = cfg.getNetworkConfig();
        networkConfig.setPort(cachePort);
        networkConfig.setPortAutoIncrement(false);

        JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(true);
        joinConfig.getKubernetesConfig().setEnabled(false);
        return cfg;

    }

    /**
     * Create a web filter. Parameterize this with two properties,
     *
     * <ol>
     * <li><i>instance-name</i>
     * Direct the web filter to use the existing Hazelcast instance rather than
     * to create a new one.</li>
     * <li><i>sticky-session</i>
     * As the HTTP session will be accessed from multiple processes, deactivate
     * the optimization that assumes each user's traffic is routed to the same
     * process for that user.</li>
     * </ol>
     * <p>
     * <p>
     * Spring will assume dispatcher types of {@code FORWARD}, {@code INCLUDE}
     * and {@code REQUEST}, and a context pattern of "{@code /*}".
     *
     * @return The web filter for Tomcat
     */
    /*@Bean
    public WebFilter webFilter(HazelcastInstance hazelcastInstance) {

        Properties properties = new Properties();
        properties.put("instance-name", hazelcastInstance.getName());
        properties.put("sticky-session", "false");

        return new WebFilter(properties);
    }*/
    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        // The Stormpath SDK knows to use the Spring CacheManager automatically
        return new HazelcastCacheManager(hazelcastInstance);
    }


    @Bean
    public FilterRegistrationBean hazelcastFilter(HazelcastInstance hazelcastInstance) {
        FilterRegistrationBean registration = new FilterRegistrationBean(new SpringAwareWebFilter());
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registration.addUrlPatterns("/*");
        registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE);
        registration.addInitParameter("instance-name", hazelcastInstance.getName());
        registration.addInitParameter("sticky-session", "false");
        return registration;
    }

    @Bean
    public ServletListenerRegistrationBean<SessionListener> hazelcastSessionListener() {
        return new ServletListenerRegistrationBean<>(new SessionListener());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


}