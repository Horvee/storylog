package test;

import com.horvee.storylog.core.ProxyObjectInterceptor;
import com.horvee.storylog.core.StoryLogger;
import com.horvee.storylog.core.annotation.StoryTag;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class test2 {

  private final Logger log = LoggerFactory.getLogger(test2.class);

  @Test
  public void t1() throws NoSuchMethodException {
    TestInterface testInterface = new TestInterface() {

//      @StoryTag(code = 100101, title = "test2")
      @Override
      public void sayHello() {
        StoryLogger.log("haha");
      }
    };

    log.warn(Arrays.asList(testInterface.getClass().getDeclaredMethod("sayHello").getAnnotations()).toString());


//    TestInterface t = (TestInterface) ProxyObjectInterceptor.create(testInterface);
    TestInterface t = ProxyObjectInterceptor.fullProxy(100101,"TestInterface",testInterface,TestInterface.class);

    t.sayHello();
  }

  public interface TestInterface {
    @StoryTag(code = 100101, title = "test2")
    void sayHello();
  }

}
