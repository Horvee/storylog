package com.horvee.storylog.springboot.interceptor;

import com.horvee.storylog.springboot.configuration.config.interceptor.StoryLogHandlerInterceptorProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ConditionalOnProperty(prefix = "storylog.interceptor", name = "enable-request-interceptor", havingValue = "true", matchIfMissing = true)
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final StoryLogHandlerInterceptorProperties configProperties;

    public InterceptorConfiguration(StoryLogHandlerInterceptorProperties configProperties) {
        this.configProperties = configProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new StoryLogInterceptor(configProperties));

        if (configProperties.getHandlerInterceptorPathPatterns() != null) {
            registration.addPathPatterns(configProperties.getHandlerInterceptorPathPatterns());
        }
        if (configProperties.getHandlerInterceptorExcludePathPatterns() != null) {
            registration.excludePathPatterns(configProperties.getHandlerInterceptorExcludePathPatterns());
        }
    }

}
