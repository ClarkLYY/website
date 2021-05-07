package com.clarklyy.website.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;
@Slf4j
@Configuration
public class StaticConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        ApplicationHome home = new ApplicationHome(getClass());
        String path = "/";
        try{
            path = home.getSource().getParentFile().toString() + "/image/";
        }catch (Exception e){
            log.error(e.getMessage());
        }
        System.out.println(path);
        registry.addResourceHandler("/**")
                .addResourceLocations("file:"+path)
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        super.addResourceHandlers(registry);
    }
}
