package com.example.weixin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        /* 是否通过请求Url的扩展名来决定media type */
//        configurer.favorPathExtension(true)
//                /* 不检查Accept请求头 */
//                .ignoreAcceptHeader(true)
//                .parameterName("mediaType")
//                /* 设置默认的media yype */
//                .defaultContentType(MediaType.APPLICATION_JSON_UTF8)
//                /* 请求以.xml结尾的会被当成MediaType.APPLICATION_XML*/
//                .mediaType("xml", MediaType.APPLICATION_XML);
        configurer.favorPathExtension(true)
                .ignoreAcceptHeader(true)
                .defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }
}
