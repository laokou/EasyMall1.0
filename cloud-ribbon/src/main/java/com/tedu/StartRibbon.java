package com.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableZuulProxy
public class StartRibbon {
    public static void main(String[] args) {
        SpringApplication.run(StartRibbon.class,args);
    }

    //restTemplate支持http协议,不能支持服务的调用
    //引入ribbon的拦截注解,默认拦截的负载均衡逻辑是轮询
    @Bean
    @LoadBalanced
    public RestTemplate initRestTemplate(){
        return new RestTemplate();
    }

}
