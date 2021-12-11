package com.horvee.storylog.springboot.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Work by 'EnableStoryLog' annotation configuration
 *
 * */
@SuppressWarnings("All")
@Configuration
@ComponentScan({"com.mh.storylog.springboot","com.mh.storylog.transfer.springboot","com.mh.storylog.save.springboot"})
public class StoryLogConfiguration {

}
