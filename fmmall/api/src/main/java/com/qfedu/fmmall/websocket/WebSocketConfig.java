package com.qfedu.fmmall.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-11-24  22:38
 */
//@Configuration
public class WebSocketConfig {

    @Bean //配置到Spring容器
    public ServerEndpointExporter getServerEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
