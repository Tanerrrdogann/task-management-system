package com.gorevyonetimi.config;

import com.gorevyonetimi.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/tasks/**", "/task-form", "/reports/**") // sadece görüntüleme sayfaları
                .excludePathPatterns("/login", "/logout", "/css/**", "/js/**", "/images/**", "/webjars/**", "/error");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
    }
}
