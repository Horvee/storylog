package com.horvee.storylog.sample.test.controller;

import com.horvee.storylog.core.ProxyObjectInterceptor;
import com.horvee.storylog.core.annotation.StoryTag;
import com.horvee.storylog.sample.test.service.TestRealService;
import com.horvee.storylog.sample.test.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private TestService proxyTestService;
    private TestService testService;
    private TestRealService testRealService;

    public TestController(TestService testService, TestRealService realService) {
        // you can proxy interface object create story log
        this.proxyTestService = ProxyObjectInterceptor.fullProxy(100000,"is|ok",testService,TestService.class);
        this.testService = testService;
        this.testRealService = realService;
    }

    @GetMapping("/1")
    public String t1() {
        proxyTestService.sayHello();
        return "success";
    }

    /**
     * Or you can spring mvc request mapping function add 'StoryTag' work by story log start and end
     *
     * */
    @StoryTag(code = 100101, title = "test2")
    @GetMapping("/2")
    public String t2() {
        testService.sayHello();
        return "success";
    }

    /**
     * Or you can spring mvc request mapping function add 'StoryTag' work by story log start and end
     *
     * */
    @StoryTag(code = 100201, title = "TheIsBusinessA")
    @GetMapping("/businessCode")
    public String businessCode() {
        testRealService.runBusinessCode();
        return "success";
    }


}
