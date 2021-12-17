# StoryLog! You business log other choice.
>故事日志！你业务日志输出的其他选择

#### 故事源于扰人的日志输出的问题！
有些业务涉及大量的日志输出，譬如出于以下考虑：
1. 以便于系统后期问题异常，问题排查处理
2. 多步骤数据计算错误问题排查
3. 问题追溯（例如：第三方服务、平台提供错误的数据，引致计算错误进而导致经济损失）需要明确的证据举证
大量日志打印将会引发一些问题，如：一个请求将会打印大量的计算过程的日志，请求处理时间长但打印大于一条日志
如果使用ELK日志架构，你可能除了需要极其规范的日志，以及编写极其精密的分析算法,才有可能来将每个请求的日志聚合到一条日志中
但为了以上原因，为项目做大量的日志输出规范要求以及分析算法，其实是不经济的，开发成本以及效率会因此受到影响！

#### 为此故事日志（StoryLog）诞生了！
- **你可以针对一个请求**所有的日志归纳到一个标签（Tag），该标签会指向你在代码中标记的业务，同时也会记录该请求执行输出操作的所有日志
- **你可以针对一个接口**每次执行该接口的函数，以其为故事日志的开始和结束

#### 其他特性
- 你仍然可以使用Slf4j日志框架，在保持原有的日志打印同时把日志输出到StoryLog
- 你可以为你自己的日志存储方案做选择或者自定义，来迎合你现有系统所配备的环境
- 可以做到请求处理链整合展示

#### 优点：
1. 解耦架构，且有成熟的组件，便于你集成到基于SpringBoot的项目
2. 配置简单，仅需极小的代码量即可集成该故事日志到系统中，降低使用的成本

#### 需要注意点：
1. 为了减少网络的IO开销，以及提高可用性，每个任务的处理中的日志将会暂存到内存中，请不要在日志打印二进制等大量数据
2. Ta并不是Slf4j日志框架的新实现。而是一个补充，你可以利用故事日志更好的管理你每一个请求所产生的业务日志！他会帮你更好的归并日志，以务求将日志打印大or处理耗时长打印分散的日志，聚合至一个文档中，提高内容审阅、问题排查的效率


#### 依赖关系图
![](https://github.com/Horvee/storylog/blob/master/DependencyPhoto.jpg?raw=true)

#### How can use
由于该项目的架构设计,允许使用不同组件的搭配实现故事式日志! --- 以下说明尚未包含读取以及解析故事式日志的数据 
推荐搭配的中间件: Kafka,ElasticSearch
假设目前如下预设条件: 项目基于SpringBoot,使用中间件Kafka,ElasticSearch


- 增加依赖
```xml
<dependencys>
    <!-- 核心包依赖 -->
    <dependency>
      <groupId>io.github.horvee.storylog</groupId>
      <artifactId>core</artifactId>
      <version>1.0.0.RELEASE</version>
    </dependency>
    
    <!-- SpringBoot拦截器依赖(如果不需要处理网络请求，可取消该依赖) -->
    <dependency>
      <groupId>io.github.horvee.storylog</groupId>
      <artifactId>spring-boot-interceptor-configuration</artifactId>
      <version>1.0.0.RELEASE</version>
    </dependency>

    <!-- Kafka插件依赖[使用什么转换组件，就等同使用什么中间件进行数据中转处理] -->
    <dependency>
      <groupId>io.github.horvee.storylog</groupId>
      <artifactId>transfer-spring-boot-kafka</artifactId>
      <version>1.0.0.RELEASE</version>
    </dependency>
</dependencys>
```

- SpringBoot项目中配置Kafka,ElasticSearch相关配置(如下仅供参考)
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      retries: 1
      batch-size: 16384
      buffer-memory: 33554432
  elasticsearch: # 如果为发送给端,用作保存的中间件则不需要进行配置以及相关依赖
    rest:
      uris:
        - http://localhost:9200
```

- 全局拦截配置(默认全局URL路径拦截,SpringBoot所支持的配置方式都可以,如application.yml)
```yaml
storylog:
  interceptor:
    enable-request-interceptor: true # 默认启用拦截器,如自定义实现也不使用相关组件建议直接移除依赖便可
    handler-interceptor-path-patterns: # 默认全部URL拦截
      - /**
    handler-interceptoriexclude-path-patterns: # 默认排除拦截的URL为空 
      - /rejecturl
    try-load-parent-info: false # 是否启用尝试加载父级信息(微服务请求链路使用,需要可信网络环境)
     
```

- 你可以增加这些可能会用到配置项,来定义一些行为
```yaml
storylog:
  print-logger-fail-stack: false # 是否打印输出时无法追溯请求源的堆栈,一般用于调试时排除遗漏为设定代理线程池使用
  transfer-accept-message: false # 一般用于是否启用接收并处理信息日志信息(可以另外启用KEApplication,也可以启用新的微服务接收队列中的日志信息)
```

- 此外需要在Application中添加'@EnableStoryLog'注解,以启用StoryLog功能
```java
import com.horvee.storylog.springboot.configuration.annotation.EnableStoryLog;
@EnableStoryLog
@SpringBootApplication
public class SampleApplication {}
```

###### 至此，几乎所有配置项都有默认值，你可以轻松的运行你的项目了！接下来就有些劳力活
#### 代码修改

- 线程池需要增加代理类(OR'JVM配置'[不建议])
```java
/**
* 涉及到子线程的日志打印时，需要配置相应的代理线程池，否则将无法追溯子线程对应的父级线程，进而导致无法得知对应的请求或者接口的任务    
*/
class Test {
     // 线程池代理方法
     private ExecutorService executorService = TtlExecutors.getTtlExecutorService(Executors.newCachedThreadPool());
     
     // Runnable 代理方法
     private Runnable runnable = TtlRunnable.get(() -> {
         // do something
     });
}
```

- 增加注解
故事日志的代号以及接口基本信息,在启用MVC进行拦截处理or部分接口代理的执行,都将依赖该注解决定是否启用故事日志记录功能
```java
import StoryTag;
class TestController {
    @StoryTag(code = 100101, title = "test-api")
    @GetMapping("/test")
    public String test() {
        // do something
        return "success";
    }
}
```
也可以对对应的接口创建专有的代理(一般适用于MQ的Listener)，在接口调用包含时作为单个日志开始，执行完毕后作为日志的结束边界
```
TestService testService = ProxyObjectInterceptor.create(testServiceImpl,TestService.class);
```

- 日志打印调整
有2中方式打印日志到StoryLog，可以根据实际情况调整输出的方式，以及混合使用！
1. 使用专有Logger，日志数据只会流转到StoryLog中，并不会打印到控制台或者通过Slf4j框架进行输出！
适合输出冗余的日志信息，为后面通过StoryLog进行问题排查提供更多参考信息，无论如何都会输出到StoryLog
```java
import StoryLogger;
class Print {
    public void test() {
        StoryLogger.log("Hi,This is a log!");
        StoryLogger.log("Now time {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
```

2. 代理Logger来实现Slf4j框架进行输出，将会通过Slf4j实现的框架输出日志信息，同时输出到StoryLog，减少日志双重输出的开发负担！
也将依据当前的打印日志级别决定是否输出到StoryLog
```java
import StoryLogger;
class Print {
    private final static Logger log = StoryLogger.createLog(LoggerFactory.getLogger(Print.class));    
    public void test() {
        log.warn("Hi,This is a log!");
        log.warn("Now time {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
```

KEApplication 可以直接运行Jar包，该服务承载对日志数据进行拉取、解析、分组、保存等工作（也可以自行通过相关的组件搭建服务）
如果对使用的方式还有疑问，不妨看看Sample项目，这是一个实例来展示如何使用StoryLog这个项目（作为一个Sender）

- 注意事项?
1. 如果使用代理的方式实现故事日志，需要注意一个线程及其子线程，同一时间内有且只有一个故事日志事件！
SO在调用一个接口中，如果内部涉及多个故事日志的代理时，受最外层的接口事件已创建，后面的执行的接口会继续原有的事件，而不再创建新的事件


