package com.horvee.storylog.core.test;

import com.horvee.storylog.core.ProxyObjectInterceptor;
import com.horvee.storylog.core.StoryLogger;
import com.horvee.storylog.core.annotation.StoryTag;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class test2 {

  private Logger log = LoggerFactory.getLogger(test2.class);

  @Test
  public void t1() throws NoSuchMethodException {
    TestInterface testInterface = new TestInterface() {

      @Override
      @StoryTag(code = 100101, title = "test2")
      public void sayHello() {
        StoryLogger.log("haha");
      }
    };

    log.warn(Arrays.asList(testInterface.getClass().getDeclaredMethod("sayHello").getAnnotations()).toString());


//    TestInterface t = (TestInterface) ProxyObjectInterceptor.create(testInterface,TestInterface.class);
//    TestInterface t = ProxyObjectInterceptor.create(testInterface,TestInterface.class);

    TestInterface t = ProxyObjectInterceptor.fullProxy(100101,"TestInterface",testInterface,TestInterface.class);

    t.sayHello();

//    test2 t = (test2) ProxyObjectInterceptor.create(this);
//    t.t2();
  }

  public interface TestInterface {
    void sayHello();
  }

  @StoryTag(code = 100101, title = "test2")
  private void t2() {
    StoryLogger.log("haha");
  }


}
