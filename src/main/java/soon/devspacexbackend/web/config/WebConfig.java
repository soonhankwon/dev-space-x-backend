package soon.devspacexbackend.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import soon.devspacexbackend.web.interceptor.LoginCheckInterceptor;
import soon.devspacexbackend.web.interceptor.ContentAuthCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ContentAuthCheckInterceptor())
                .order(1)
                .addPathPatterns("/contents");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/users/signup", "/contents", "/login", "/logout",
                        "/css/**", "/js/**", "/*.ico", "/error", "/swagger-ui/**", "/v3/api-docs/**");
    }
}
