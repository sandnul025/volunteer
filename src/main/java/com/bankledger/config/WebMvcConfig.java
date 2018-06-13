package com.bankledger.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.bankledger.interceptor.LoginInterceptor;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2017年12月4日 下午5:11:00
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 重写 spring Boot默认返回的json格式
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        // message转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // fastJson config
        FastJsonConfig config = new FastJsonConfig();
        // 时间格式
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // null 转化为空字符串
        config.setSerializeFilters((ValueFilter) (object, name, value) -> {
            if (value == null)
                return "";
            return value;
        });
        // 创建可支持的媒体类型
        List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
        // 添加媒体类型 application/json;charset=UTF-8
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        // 在转换器中添加 支持的媒体类型
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(config);

        // 添加到转换器中
        converters.add(fastConverter);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/img/**","/js/**","/videos/**","/editormd/**","/layui/**","/*.ico","/index/**","/fonts/**");
    }
   
    /**
     * CORS跨域访问
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// 别跨域的路径
                .allowedMethods("*")// 允许POST、GET、PUT、DELETE等。
                .allowedOrigins("*")// 请求域名 http://www.baidu.com
                .allowedHeaders("*");// 请求头
    }
    
 
}
