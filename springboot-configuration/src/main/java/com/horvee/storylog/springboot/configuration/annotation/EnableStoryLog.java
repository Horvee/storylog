package com.horvee.storylog.springboot.configuration.annotation;

import java.lang.annotation.*;

import com.horvee.storylog.springboot.configuration.StoryLogConfiguration;
import org.springframework.context.annotation.Import;

/**
 * SpringBoot project must be use this annotation in application
 *
 * */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(StoryLogConfiguration.class)
public @interface EnableStoryLog {
}
