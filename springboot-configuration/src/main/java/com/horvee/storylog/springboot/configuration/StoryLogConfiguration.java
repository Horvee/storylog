package com.horvee.storylog.springboot.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Work by 'EnableStoryLog' annotation configuration
 *
 * */
@SuppressWarnings("All")
@Configuration
@ComponentScan({"com.horvee.storylog.springboot","com.horvee.storylog.transfer.springboot","com.horvee.storylog.save.springboot"})
public class StoryLogConfiguration {

}
