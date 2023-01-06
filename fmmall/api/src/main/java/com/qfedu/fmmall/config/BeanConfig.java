package com.qfedu.fmmall.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2023-01-05  21:07
 */
@Configuration
public class BeanConfig {

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
