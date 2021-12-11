package com.horvee.storylog.springboot.configuration;

import com.horvee.storylog.core.DefaultResultDataHandler;
import com.horvee.storylog.core.ResultDataHandler;
import com.horvee.storylog.core.SenderProtectPool;
import com.horvee.storylog.springboot.configuration.config.StoryLogConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentConfigurationInitialization {

    @Bean
    public SenderProtectPool initSenderProtectPool(StoryLogConfigProperties storyLogConfigProperties) {
        return new SenderProtectPool(storyLogConfigProperties.getSenderProtectNum());
    }

    /**
     * If 'ResultDataHandler' bean is not be create,then use default handler
     * */
    @ConditionalOnMissingBean(ResultDataHandler.class)
    @Bean
    public ResultDataHandler initResultDataHandler() {
        return new DefaultResultDataHandler();
    }

}
