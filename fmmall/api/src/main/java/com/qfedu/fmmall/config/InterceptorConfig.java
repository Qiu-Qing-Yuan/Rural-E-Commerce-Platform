package com.qfedu.fmmall.config;

import com.qfedu.fmmall.interceptor.CheckTokenInterceptor;
import com.qfedu.fmmall.interceptor.SetTimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author QiuQingyuan
 * @version 1.0
 * @Description: TODO
 * @date 2022-09-21  11:12 AM
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private CheckTokenInterceptor checkTokenInterceptor;
    @Resource
    private SetTimeInterceptor setTimeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInterceptor)//注册拦截器
                .addPathPatterns("/shopcart/**")
                .addPathPatterns("/orders/**")
                .addPathPatterns("/useraddr/**")
                .excludePathPatterns("/user/**")
                .addPathPatterns("/user/check");
        registry.addInterceptor(setTimeInterceptor).addPathPatterns("/**").excludePathPatterns("/user/login");
    }
}
