package com.yangls.miaosha.config;


import com.yangls.miaosha.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import java.util.List;
/**
 * @description:
 * @author: yangLs
 * @create: 2020-06-03 10:07
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    UserMethodArgumentResolver userArgumentResolver;

    /**
     * 注入自定义拦截器到该配置类中
     */
    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 添加自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")//拦截的访问路径，拦截所有
                .excludePathPatterns("/static/*");//排除的请求路径，排除静态资源路径
//        super.addInterceptors(registry);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }
    /**
     * 添加静态资源映射路径，css、js等都放在classpath下的static中，
     */
//    @Override
  /*  public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }*/


}
