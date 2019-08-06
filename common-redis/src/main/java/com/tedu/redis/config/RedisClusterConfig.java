package com.tedu.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix="redis.cluster")
public class RedisClusterConfig {
    private String nodes;
    private Integer maxTotal;
    private Integer maxIdle;
    private Integer minIdle;

    @Bean
    public JedisCluster initJedisCluster(){
        //1.收集节点信息
        Set<HostAndPort> set = new HashSet<>();
        //2.解析nodes,转化为HostAndPort对象
        String[] nodesArray = nodes.split(",");
        for (String node:nodesArray ) {
            String host = node.split(":")[0];
            int port = Integer.parseInt(node.split(":")[1]);
            set.add(new HostAndPort(host,port));
        }
        //3.生成JedisCluster的配置对象
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        //4.根据config和set构造JedisCluster对象
        JedisCluster jedisCluster = new JedisCluster(set,config);
        return jedisCluster;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }
}
