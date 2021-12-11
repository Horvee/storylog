package com.horvee.storylog.transfer.springboot.kafka;

import com.horvee.storylog.core.ResultDataHandler;
import com.horvee.storylog.core.StoryLogSaver;
import com.horvee.storylog.core.StoryLogSaverDriver;
import com.horvee.storylog.core.model.dto.LogDataPackage;
import com.horvee.storylog.core.model.dto.storydetail.StoryInfo;
import com.horvee.storylog.springboot.configuration.util.StoryLogJsonUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(prefix = "storylog", name = "transfer-accept-message", havingValue = "true", matchIfMissing = false)
@Component
public class MessageAcceptComponent {

    private final static Logger log = LoggerFactory.getLogger(MessageAcceptComponent.class);
    private final static StoryLogSaver storyLogSaver =  StoryLogSaverDriver.getDrive();
//    private final static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private final ResultDataHandler resultDataHandler;

    public MessageAcceptComponent(ResultDataHandler resultDataHandler) {
        this.resultDataHandler = resultDataHandler;
    }

    @KafkaListener(id = "consumer-storylog-id", groupId = "default",topics = "${storylog.transfer.kafka.topic:story-log}")
    public void consumerMessage(ConsumerRecord<Integer, String> msg) {
        log.debug("Handler msg:{}",msg.toString());
//        log.warn("Handler msg:{}",msg.toString());
        LogDataPackage logDataPackage;
        try {
            logDataPackage = StoryLogJsonUtil.defaultFormJson(msg.value(), LogDataPackage.class);
        } catch (RuntimeException e) {
            log.error("Story log deserialization data fail",e);
            log.error(msg.value());
            return;
        }

        try {
            StoryInfo storyInfo = resultDataHandler.handlerLogData(logDataPackage.getTaskInfo(),logDataPackage.getLogInfoList());
            storyLogSaver.saveLog(storyInfo);
        } catch (RuntimeException e) {
            log.error("Story log data handler fail!",e);
            log.error("Message data:{}",msg.value());
        }
    }

}
