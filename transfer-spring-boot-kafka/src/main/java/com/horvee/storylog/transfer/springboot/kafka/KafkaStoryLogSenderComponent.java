package com.horvee.storylog.transfer.springboot.kafka;

import com.horvee.storylog.core.SenderProtectPool;
import com.horvee.storylog.core.StoryLogData;
import com.horvee.storylog.core.StoryLogSender;
import com.horvee.storylog.core.model.dto.LogDataPackage;
import com.horvee.storylog.core.model.dto.LogInfo;
import com.horvee.storylog.core.model.dto.TaskInfo;
import com.horvee.storylog.springboot.configuration.config.transfer.StoryLogKafkaProperties;
import com.horvee.storylog.springboot.configuration.util.StoryLogJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Component
public class KafkaStoryLogSenderComponent implements StoryLogSender {

    private final static Logger log = LoggerFactory.getLogger(KafkaStoryLogSenderComponent.class);

    private final StoryLogKafkaProperties properties;
    private final SenderProtectPool senderProtectPool;
    private final KafkaTemplate<String,Object> kafkaTemplate;

    public KafkaStoryLogSenderComponent(StoryLogKafkaProperties properties, SenderProtectPool senderProtectPool, KafkaTemplate<String, Object> kafkaTemplate) {
        this.properties = properties;
        this.senderProtectPool = senderProtectPool;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void init() {
        // register bean
        SpringBootKafkaStoryLogSenderBinder.registerService(this);
    }

    @Override
    public void send(TaskInfo taskInfo, Collection<LogInfo> logInfoList) {
        senderProtectPool.doSend(taskInfo, logInfoList, () -> {
            try {
                LogDataPackage logDataPackage = new LogDataPackage.LogDataPackageBuilder()
                        .orderInfo(taskInfo)
                        .logInfoList(logInfoList)
                        .build();
                String message = StoryLogJsonUtil.defaultToJson(logDataPackage);

                try {
                    kafkaTemplate.send(properties.getTopic(), message);
                } catch (Exception e) {
                    log.error("Send story log message fail! content:{}",message);
                    log.error("Kafka send fail",e);
                }

                // remove local temp log
                StoryLogData.tryRemoveToOrderLogLinked(taskInfo.getId());

            } catch (Exception e) {
                log.error("Send message fail stack", e);
                log.error("Send story log message fail! \ntaskInfo:{} \nlogInfoList:{}",
                        StoryLogJsonUtil.defaultToJson(taskInfo),StoryLogJsonUtil.defaultToJson(logInfoList));
            }
        });
    }
}
