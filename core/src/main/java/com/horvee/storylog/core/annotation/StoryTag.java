package com.horvee.storylog.core.annotation;

import java.lang.annotation.*;

/**
 * Use story log tag,log data will be mark tag info
 *
 * */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StoryTag {

    /**
    * Sample: 0000000
    * modeCode*2 + business*2 + version*2
    * */
    int code();
    String title();

}
