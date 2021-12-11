package com.horvee.storylog.transfer.springboot.kafka;

import com.horvee.storylog.core.StoryLogSender;
import com.horvee.storylog.core.spi.StoryLogSenderBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpringBootKafkaStoryLogSenderBinder implements StoryLogSenderBinder {

    private final static Logger log = LoggerFactory.getLogger(SpringBootKafkaStoryLogSenderBinder.class);

    private static volatile boolean initState = false;
    private static StoryLogSender sender = (orderInfo, logInfoList) -> {
        log.warn("KafkaStoryLogSenderComponent not be init");
    };

    @Override
    public StoryLogSender getSender() {
        return sender;
    }

    protected static void registerService(StoryLogSender senderBean) {
        initState = true;
        sender = senderBean;
    }

    public static boolean getInitState() {
        return initState;
    }

    @Override
    public boolean isDynamicSender() {
        return true;
    }
}
