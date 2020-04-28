package edu.hfut.config;

import edu.hfut.util.comon.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 22:50
 */
@Configuration
@Order(-50)
public class BeanConfiguration {
    @Value("${server.dataCenterId}")
    private long dataCenterId;
    @Value("${server.machineId}")
    private long machineId;
    /**
     * 雪花算法 的 单例
     * @return
     */
    @Bean
    SnowFlakeUtil getSnowFlakeUtil(){
        return new SnowFlakeUtil(dataCenterId,machineId);
    }
}
