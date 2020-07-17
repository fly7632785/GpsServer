package com.jafir.springboot.config;

/**
 * Created by jafir on 2020/6/4.
 */

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.jafir.springboot.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebAppConfig implements WebMvcConfigurer{
//    这里不推荐用 support，因为它会是spring本身的自动配置失效 影响较大
//public class WebAppConfig extends WebMvcConfigurationSupport{


//    这里等价于 .yml 的
//      static-path-pattern: /res/**
//    resources:
//      static-locations: classpath:/static/ , file:/Users/jafir/Downloads/ #静态资源配置
//

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/res/**")
//                //本地磁盘下面的文件 会映射 为/路径下
//                //比如/Users/jafir/Downloads/a.jpg 可以用 localhost:xx/res/a.jpg来访问
//                .addResourceLocations("file:/Users/jafir/Downloads/")   //媒体资源
//                //比如/static/img/a3.jpg 可以用 localhost:xx/res/img/a3.jpg来访问
//                //比如/static/test.html 可以用 localhost:xx/res/test.html来访问
//                .addResourceLocations("classpath:/static/");
//    }

    /**
     * 页面跨域访问Controller过滤
     *
     * @return
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
//                .allowedOrigins("http://localhost:8083","http://localhost:8080");
                .allowedOrigins("*");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/**").
                excludePathPatterns("/error","/login","/create_user",
                        "/test",
                        "/getAllUrl","/test1","/test.html","/res/**");
    }

    /**
     * 自定义消息转换器
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 清除默认 Json 转换器
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);

        // 配置 FastJson
        FastJsonConfig config = new FastJsonConfig();


        config.setSerializerFeatures(
                //List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullListAsEmpty,
                //是否输出值为null的字段,默认为false
                SerializerFeature.WriteMapNullValue,
                //字符串null返回空字符串
                SerializerFeature.WriteNullStringAsEmpty,
                //空布尔值返回false
                SerializerFeature.WriteNullBooleanAsFalse,
                //结果是否格式化,默认为false
                SerializerFeature.PrettyFormat);


        // 添加 FastJsonHttpMessageConverter
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setFastJsonConfig(config);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        converters.add(fastJsonHttpMessageConverter);

        // 添加 StringHttpMessageConverter，解决中文乱码问题
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(stringHttpMessageConverter);
    }

}