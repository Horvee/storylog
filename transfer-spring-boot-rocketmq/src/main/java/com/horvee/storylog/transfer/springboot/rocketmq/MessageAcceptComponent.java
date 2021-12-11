package com.horvee.storylog.transfer.springboot.rocketmq;

import com.horvee.storylog.core.ResultDataHandler;
import com.horvee.storylog.core.StoryLogSaver;
import com.horvee.storylog.core.StoryLogSaverDriver;
import com.horvee.storylog.core.model.dto.LogDataPackage;
import com.horvee.storylog.core.model.dto.storydetail.StoryInfo;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@RocketMQMessageListener(consumerGroup = "default", topic = "${storylog.transfer.rocketmq.topic:story-log}")
@ConditionalOnProperty(prefix = "storylog", name = "transfer-accept-message", havingValue = "true", matchIfMissing = false)
@Component
public class MessageAcceptComponent implements RocketMQListener<LogDataPackage> {

    private final static Logger log = LoggerFactory.getLogger(MessageAcceptComponent.class);
    private final static StoryLogSaver storyLogSaver =  StoryLogSaverDriver.getDrive();
//    private final static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private final ResultDataHandler resultDataHandler;

    public MessageAcceptComponent(ResultDataHandler resultDataHandler) {
        this.resultDataHandler = resultDataHandler;
    }

    @Override
    public void onMessage(LogDataPackage logDataPackage) {
        log.debug("Handler msg:{}",logDataPackage);

        try {
            StoryInfo storyInfo = resultDataHandler.handlerLogData(logDataPackage.getTaskInfo(),logDataPackage.getLogInfoList());
            storyLogSaver.saveLog(storyInfo);
        } catch (RuntimeException e) {
            log.error("Story log data handler fail!",e);
            log.error("msg:{}",logDataPackage);
        }
    }
}
