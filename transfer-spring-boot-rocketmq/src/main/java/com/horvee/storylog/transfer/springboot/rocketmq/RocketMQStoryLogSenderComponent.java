package com.horvee.storylog.transfer.springboot.rocketmq;

import com.horvee.storylog.core.SenderProtectPool;
import com.horvee.storylog.core.StoryLogData;
import com.horvee.storylog.core.StoryLogSender;
import com.horvee.storylog.core.model.dto.LogDataPackage;
import com.horvee.storylog.core.model.dto.LogInfo;
import com.horvee.storylog.core.model.dto.TaskInfo;
import com.horvee.storylog.springboot.configuration.config.transfer.StoryLogRocketMQProperties;
import com.horvee.storylog.springboot.configuration.util.StoryLogJsonUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Component
public class RocketMQStoryLogSenderComponent implements StoryLogSender {

    private final static Logger log = LoggerFactory.getLogger(RocketMQStoryLogSenderComponent.class);

    private final RocketMQTemplate rocketMQTemplate;
    private final SenderProtectPool senderProtectPool;
    private final StoryLogRocketMQProperties properties;

    public RocketMQStoryLogSenderComponent(StoryLogRocketMQProperties properties, SenderProtectPool senderProtectPool, RocketMQTemplate rocketMQTemplate) {
        this.properties = properties;
        this.senderProtectPool = senderProtectPool;
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @PostConstruct
    public void init() {
        // register bean
        SpringBootRocketMQStoryLogSenderBinder.registerService(this);
    }

    @Override
    public void send(TaskInfo taskInfo, Collection<LogInfo> logInfoList) {
        senderProtectPool.doSend(taskInfo, logInfoList, () -> {
            try {
                LogDataPackage logDataPackage = new LogDataPackage.LogDataPackageBuilder()
                        .orderInfo(taskInfo)
                        .logInfoList(logInfoList)
                        .build();

                try {
                    rocketMQTemplate.syncSend(properties.getTopic(),logDataPackage);
                } catch (Exception e) {
                    String message = StoryLogJsonUtil.defaultToJson(logDataPackage);
                    log.error("Send story log message fail! content:{}",message);
                    log.error("RocketMQ send fail",e);
                }

                // remove local temp log
                StoryLogData.tryRemoveToOrderLogLinked(taskInfo.getId());

            } catch (Exception e) {
                log.error("Send story log message fail! \ntaskInfo:{} \nlogInfoList:{}",
                        StoryLogJsonUtil.defaultToJson(taskInfo),StoryLogJsonUtil.defaultToJson(logInfoList));
            }
        });
    }
}
