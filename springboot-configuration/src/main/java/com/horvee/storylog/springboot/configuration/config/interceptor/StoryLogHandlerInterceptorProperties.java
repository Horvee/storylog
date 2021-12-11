package com.horvee.storylog.springboot.configuration.config.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConfigurationProperties(prefix = "storylog.interceptor")
public class StoryLogHandlerInterceptorProperties {

    private boolean enableRequestInterceptor = true;

    private String[] handlerInterceptorPathPatterns = new String[]{"/**"};

    private String[] handlerInterceptorExcludePathPatterns = new String[]{};

    private boolean tryLoadParentInfo = false;

    public boolean isTryLoadParentInfo() {
        return tryLoadParentInfo;
    }

    public void setTryLoadParentInfo(boolean tryLoadParentInfo) {
        this.tryLoadParentInfo = tryLoadParentInfo;
    }

    public boolean isEnableRequestInterceptor() {
        return enableRequestInterceptor;
    }

    public void setEnableRequestInterceptor(boolean enableRequestInterceptor) {
        this.enableRequestInterceptor = enableRequestInterceptor;
    }

    public String[] getHandlerInterceptorPathPatterns() {
        return handlerInterceptorPathPatterns;
    }

    public void setHandlerInterceptorPathPatterns(String[] handlerInterceptorPathPatterns) {
        this.handlerInterceptorPathPatterns = handlerInterceptorPathPatterns;
    }

    public String[] getHandlerInterceptorExcludePathPatterns() {
        return handlerInterceptorExcludePathPatterns;
    }

    public void setHandlerInterceptorExcludePathPatterns(String[] handlerInterceptorExcludePathPatterns) {
        this.handlerInterceptorExcludePathPatterns = handlerInterceptorExcludePathPatterns;
    }

    @Override
    public String toString() {
        return "HandlerInterceptor{" +
                "enableMVCInterceptor=" + enableRequestInterceptor +
                ", handlerInterceptorPathPatterns=" + Arrays.toString(handlerInterceptorPathPatterns) +
                ", handlerInterceptorExcludePathPatterns=" + Arrays.toString(handlerInterceptorExcludePathPatterns) +
                '}';
    }
}
